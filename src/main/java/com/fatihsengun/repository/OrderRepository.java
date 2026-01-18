package com.fatihsengun.repository;

import com.fatihsengun.entity.Order;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    public Optional<List<Order>> findByStatus(OrderStatus status);

     public Optional<List<Order>> findByUserOrderByCreatedAtDesc(User user);
}
