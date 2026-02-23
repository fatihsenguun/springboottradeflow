package com.fatihsengun.repository;

import com.fatihsengun.entity.Order;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.OrderStatus;
import com.fatihsengun.enums.RoleType;
import com.fatihsengun.repository.OrderRepository;
import com.fatihsengun.repository.UserRepository;
import com.fatihsengun.starter.TradeflowApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class OrderRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
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

        userRepository.save(sharedTestUser);
    }

    private Order createOrder(User user, OrderStatus status, String orderNumber, BigDecimal amount) {
        return Order.builder()
                .user(user)
                .totalAmount(amount)
                .status(status)
                .orderNumber(orderNumber)
                .build();
    }

    @Test
    public void orderRepository_save_returnSaved() {
        //Arrange
        //Act
        Order savedOrder = orderRepository.save(
                createOrder(sharedTestUser, OrderStatus.APPROVED, "ORD-12311", new BigDecimal("150.20"))
        );
        //Assert

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
    }

    @Test
    public void orderRepository_findByStatus_returnOptionalListOrder() {

        orderRepository.save(createOrder(sharedTestUser, OrderStatus.PENDING, "ORD-1111", new BigDecimal("100")));
        orderRepository.save(createOrder(sharedTestUser, OrderStatus.APPROVED, "ORD-2111", new BigDecimal("50")));

        List<Order> pendingOrder = orderRepository.findByStatus(OrderStatus.PENDING).get();
        List<Order> approvedOrder = orderRepository.findByStatus(OrderStatus.APPROVED).get();

        assertThat(pendingOrder).isNotNull();
        assertThat(approvedOrder).isNotNull();

        assertThat(pendingOrder.get(0).getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(approvedOrder.get(0).getStatus()).isEqualTo(OrderStatus.APPROVED);


    }

    @Test
    public void orderRepository_sumTotals_returnSumOfTheOrders() {

        orderRepository.save(createOrder(sharedTestUser, OrderStatus.PENDING, "ORD-1111", new BigDecimal("100")));
        orderRepository.save(createOrder(sharedTestUser, OrderStatus.APPROVED, "ORD-2111", new BigDecimal("50")));

        assertThat(orderRepository.sumTotalSales()).isEqualTo(new BigDecimal("150.00"));
    }

    @Test
    public void orderRepository_getDailyStatistics_shouldReturnGroupedByDate() {
        LocalDateTime now = LocalDateTime.now();

        orderRepository.save(createOrder(sharedTestUser, OrderStatus.PENDING, "ORD-0001", new BigDecimal("100")));
        orderRepository.save(createOrder(sharedTestUser, OrderStatus.APPROVED, "ORD-0002", new BigDecimal("50")));

        List<Object[]> results = orderRepository.getDailyStatistics(
                now.minusDays(1),
                now.plusDays(1)
        );
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)[1]).isEqualTo(new BigDecimal("150.00"));
    }


}
