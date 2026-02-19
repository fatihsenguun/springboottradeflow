package com.fatihsengun.repository;

import com.fatihsengun.entity.Cart;
import com.fatihsengun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    public Optional<Cart> findByUser(User user);
}
