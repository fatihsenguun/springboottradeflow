package com.fatihsengun.service;

import com.fatihsengun.dto.DtoOrder;
import com.fatihsengun.dto.DtoOrderItemUI;
import com.fatihsengun.dto.DtoOrderUI;
import com.fatihsengun.dto.DtoStatusUI;
import com.fatihsengun.entity.*;
import com.fatihsengun.enums.OrderStatus;
import com.fatihsengun.enums.RoleType;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.kafka.OrderEventModel;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CartRepository;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.UserRepository;
import com.fatihsengun.service.impl.IdentityService;
import com.fatihsengun.service.impl.OrderServiceImpl;
import com.fatihsengun.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private IGlobalMapper globalMapper;

    @Mock
    private IdentityService identityService;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CartRepository cartRepository;


    private User sharedTestUser;
    private Wallet sharedTestWallet;
    private Product sharedTestProduct;

    @BeforeEach
    void setUp() {
        sharedTestWallet = new Wallet();
        sharedTestWallet.setBalance(new BigDecimal("1000.00"));

        sharedTestUser = User.builder()
                .firstName("Shared")
                .lastName("User")
                .email("user@gmail.com")
                .password("password123")
                .role(RoleType.USER)
                .build();
        sharedTestUser.setId(UUID.randomUUID());
        sharedTestUser.setWallet(sharedTestWallet);

        sharedTestProduct = Product.builder()
                .name("Test Product")
                .price(new BigDecimal("100"))
                .stock(10)
                .build();
        sharedTestProduct.setId(UUID.randomUUID());
    }

    private Order createOrder(User user, OrderStatus status, String orderNumber, BigDecimal amount) {
        Order order = Order.builder()
                .user(user)
                .totalAmount(amount)
                .status(status)
                .orderNumber(orderNumber)
                .build();

        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    @Test
    void createOrder_shouldCreateOrderSuccesfully() {

        DtoOrderItemUI dtoOrderItemUI = new DtoOrderItemUI();
        dtoOrderItemUI.setProductId(sharedTestProduct.getId());
        dtoOrderItemUI.setQuantity(2);

        DtoOrderUI dtoOrderUI = new DtoOrderUI();
        dtoOrderUI.setItems(List.of(dtoOrderItemUI));
        dtoOrderUI.setAddress("Test address");


        //Act
        when(identityService.getCurrentUser()).thenReturn(sharedTestUser);
        when(productService.getProductById(sharedTestProduct.getId())).thenReturn(sharedTestProduct);
        when(cartRepository.findByUser(sharedTestUser)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(UUID.randomUUID());
            return o;
        });
        when(globalMapper.toDtoOrder(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            DtoOrder dynamicDto = new DtoOrder();
            dynamicDto.setStatus(savedOrder.getStatus());
            dynamicDto.setAddress(savedOrder.getAddress());
            dynamicDto.setTotalAmount(savedOrder.getTotalAmount());
            dynamicDto.setOrderNumber(savedOrder.getOrderNumber());
            return dynamicDto;
        });


        DtoOrder result = orderService.createOrder(dtoOrderUI);

        verify(productService).decreaseStock(sharedTestProduct, 2);
        verify(applicationEventPublisher).publishEvent(any(OrderEventModel.class));
        assertThat(result.getStatus()).isEqualTo(OrderStatus.APPROVED);
        assertThat(sharedTestWallet.getBalance()).isEqualByComparingTo("800.00");

    }

    @Test
    public void createOrder_shouldThrowException_whenInsufficientStock() {
        sharedTestProduct.setStock(1);

        DtoOrderItemUI dtoOrderItemUI = new DtoOrderItemUI();
        dtoOrderItemUI.setProductId(sharedTestProduct.getId());
        dtoOrderItemUI.setQuantity(4);

        DtoOrderUI dtoOrderUI = new DtoOrderUI();
        dtoOrderUI.setItems(List.of(dtoOrderItemUI));
        dtoOrderUI.setAddress("Test Address");

        when(identityService.getCurrentUser()).thenReturn(sharedTestUser);
        when(productService.getProductById(sharedTestProduct.getId())).thenReturn(sharedTestProduct);

        assertThatThrownBy(() -> orderService.createOrder(dtoOrderUI))
                .isInstanceOf(BaseException.class);

        verify(orderRepository, never()).save(any());
        verify(applicationEventPublisher,never()).publishEvent(any());

    }


    @Test
    public void updateStatus_shouldReturnUpdatedOrder() {

        UUID fakeOrderId = UUID.randomUUID();
        Order order = createOrder(sharedTestUser, OrderStatus.APPROVED, "ORD-12311", new BigDecimal("150.20"));
        order.setId(fakeOrderId);

        DtoStatusUI dtoStatusUI = new DtoStatusUI();
        dtoStatusUI.setOrderStatus(OrderStatus.DELIVERED);

        when(orderRepository.findById(fakeOrderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(globalMapper.toDtoOrder(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            DtoOrder dynamicDto = new DtoOrder();
            dynamicDto.setStatus(savedOrder.getStatus());
            return dynamicDto;
        });

        DtoOrder result = orderService.updateStatus(fakeOrderId, dtoStatusUI);

        assertThat(result.getStatus())
                .isEqualTo(OrderStatus.DELIVERED);

    }


}
