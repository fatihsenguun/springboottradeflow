package com.fatihsengun.service.impl;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.*;
import com.fatihsengun.enums.OrderStatus;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.kafka.KafkaProducerService;
import com.fatihsengun.kafka.OrderEventModel;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.EmailSenderService;
import com.fatihsengun.service.IOrderService;
import com.fatihsengun.service.IProductService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IGlobalMapper globalMapper;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IProductService productService;


    @Override
    @Transactional
    public DtoOrder createOrder(DtoOrderUI dtoOrderUI) {

        User currentUser = identityService.getCurrentUser();
        Wallet wallet = currentUser.getWallet();
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
        if (wallet.getBalance().compareTo(total) < 0) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "insufficient balance" + total));
        }
        wallet.setBalance(wallet.getBalance().subtract(total));

        order.setUser(currentUser);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.APPROVED);
        order.setOrderItemList(orderItems);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        OrderEventModel event = new OrderEventModel();
        event.setOrderId(savedOrder.getId());
        event.setUserId(currentUser.getId());
        event.setTotalAmount(total);
        event.setOrderDate(LocalDateTime.now());

        kafkaProducerService.sendMessage("order-create", event);

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

        kafkaProducerService.sendMessage("order-status-changed", event);

        return globalMapper.toDtoOrder(savedOrder);
    }

    @Override
    public Page<DtoOrder> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(globalMapper::toDtoOrder);
    }

    public List<DtoOrder> getMyOrders() {

        User currentUser = identityService.getCurrentUser();
        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(currentUser).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "No Order")));
        return orders.stream().map(globalMapper::toDtoOrder).toList();
    }
}


