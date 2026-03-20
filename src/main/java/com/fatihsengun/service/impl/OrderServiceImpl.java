package com.fatihsengun.service.impl;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.*;
import com.fatihsengun.enums.OrderStatus;
import com.fatihsengun.enums.PaymentType;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.kafka.KafkaProducerService;
import com.fatihsengun.kafka.OrderEventModel;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CartRepository;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.EmailSenderService;
import com.fatihsengun.service.IOrderService;
import com.fatihsengun.service.IProductService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IGlobalMapper globalMapper;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional
    public DtoOrder createOrder(DtoOrderUI dtoOrderUI) {

        User  currentUser = identityService.getCurrentUser();

        if (currentUser == null) {
            if (dtoOrderUI.getPaymentMethod() == PaymentType.WALLET) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Guests cannot use the wallet. Please use a credit card."));
            }
            if (dtoOrderUI.getEmail() == null || dtoOrderUI.getEmail().isEmpty()) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Guests must provide an email address for the receipt."));
            }
        }
        System.out.println(currentUser+"  "+dtoOrderUI.getGuestId());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();

        for (DtoOrderItemUI itemUI : dtoOrderUI.getItems()) {

            Product product = productService.getProductById(itemUI.getProductId());
            if (product.getStock() < itemUI.getQuantity()) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "insufficient stock" + product.getName()));
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemUI.getQuantity()));
            total = total.add(lineTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemUI.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            productService.decreaseStock(product, itemUI.getQuantity());
        }
        if (dtoOrderUI.getPaymentMethod() == PaymentType.WALLET) {
            Wallet wallet = currentUser.getWallet();
            if (wallet.getBalance().compareTo(total) < 0) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "insufficient balance" + total));
            }
            wallet.setBalance(wallet.getBalance().subtract(total));
        } else if (dtoOrderUI.getPaymentMethod() == PaymentType.CREDIT_CART) {
            System.out.println("Processing dummy credit card for amount: " + total);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Invalid payment method."));
        }


        Cart cart = null;
        if (dtoOrderUI.getGuestId() != null && !dtoOrderUI.getGuestId().isEmpty()) {
            cart = cartRepository.findByGuestId(dtoOrderUI.getGuestId()).orElse(null);
        } else if (currentUser != null) {
            cart = cartRepository.findByUser(currentUser).orElse(null);
        }
        if (cart != null) {
            cart.getItems().clear();
            cartRepository.save(cart);
        }

        order.setUser(currentUser);
        order.setAddress(dtoOrderUI.getAddress());
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.APPROVED);
        order.setOrderItemList(orderItems);
        String email = (currentUser != null) ? currentUser.getEmail() : dtoOrderUI.getEmail();
        order.setEmail(email);

        order.setCreatedAt(LocalDateTime.now());
        String generatedOrderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderNumber(generatedOrderNumber);


        Order savedOrder = orderRepository.save(order);

        OrderEventModel event = new OrderEventModel();
        event.setOrderId(savedOrder.getId());
        event.setUserId(currentUser != null ? currentUser.getId() : null);
        event.setTotalAmount(total);
        event.setOrderDate(LocalDateTime.now());
        event.setOrderNumber(generatedOrderNumber);

        applicationEventPublisher.publishEvent(event);

        return globalMapper.toDtoOrder(savedOrder);

    }

    @Override
    public DtoOrder updateStatus(UUID orderId, DtoStatusUI dtoStatusUI) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "orderId: " + orderId)));
        order.setStatus(dtoStatusUI.getOrderStatus());
        Order savedOrder = orderRepository.save(order);

        OrderEventModel event = new OrderEventModel();
        event.setOrderDate(savedOrder.getCreatedAt());
        event.setOrderId(orderId);
        event.setTotalAmount(savedOrder.getTotalAmount());
        event.setUserId(savedOrder.getUser().getId());


        return globalMapper.toDtoOrder(savedOrder);
    }

    @Override
    public Page<DtoOrder> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(globalMapper::toDtoOrder);
    }

    @Override
    public DtoOrder getOrderById(UUID id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "No user")));

        return globalMapper.toDtoOrder(order);
    }

    public List<DtoOrder> getMyOrders() {
        User user = identityService.getCurrentUser();

        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(globalMapper::toDtoOrder)
                .collect(Collectors.toList());

    }
}


