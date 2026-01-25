package com.fatihsengun.repository;

import com.fatihsengun.entity.Order;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    public Optional<List<Order>> findByStatus(OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status!='REFUNDED' ")
    public BigDecimal sumTotalSales();



     @Query("SELECT SUM(oi.priceAtPurchase *oi.quantity)"+ "FROM OrderItem oi JOIN oi.order o"+" WHERE oi.product.id = :productId AND o.status!='REFUNDED'")
     public BigDecimal sumTotalEarningByProductId(@Param("productId") UUID productId);
}
