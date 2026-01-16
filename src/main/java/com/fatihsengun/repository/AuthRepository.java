package com.fatihsengun.repository;

import com.fatihsengun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<User, UUID> {

  public Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
