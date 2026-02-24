package com.fatihsengun.service;

import com.fatihsengun.dto.DtoOrder;
import com.fatihsengun.dto.DtoStatusUI;
import com.fatihsengun.entity.Order;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.OrderStatus;
import com.fatihsengun.enums.RoleType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.UserRepository;
import com.fatihsengun.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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


    private User sharedTestUser;

    @BeforeEach
    void setUp() {
        sharedTestUser = User.builder()
                .firstName("Shared")
                .lastName("User")
                .email("user@gmail.com")
                .password("password123")
                .role(RoleType.USER)
                .build();
        sharedTestUser.setId(UUID.randomUUID());
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
