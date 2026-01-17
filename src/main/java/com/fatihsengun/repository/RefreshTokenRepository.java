package com.fatihsengun.repository;

import com.fatihsengun.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    public Optional<RefreshToken> findRefreshTokenByRefreshToken(String refreshToken);
    public Optional<RefreshToken> findRefreshTokenByUserId(UUID id);

}
