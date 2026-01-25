package com.fatihsengun.repository;

import com.fatihsengun.entity.Order;
import com.fatihsengun.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {


    public List<OrderItem> findTop10ByOrderByCreatedAtDesc();
}

