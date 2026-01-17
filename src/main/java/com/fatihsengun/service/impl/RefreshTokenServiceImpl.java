package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoRefreshToken;
import com.fatihsengun.dto.DtoRefreshTokenUI;
import com.fatihsengun.entity.RefreshToken;
import com.fatihsengun.entity.User;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.jwt.JwtService;
import com.fatihsengun.repository.RefreshTokenRepository;
import com.fatihsengun.service.IRefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public RefreshToken saveRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpireDate(LocalDateTime.now().plusDays(10));


        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isRefreshTokenExpired(LocalDateTime expiredDate) {
        return LocalDateTime.now().isAfter(expiredDate);
    }


    @Override
    @Transactional
    public DtoRefreshToken refreshToken(DtoRefreshTokenUI dtoRefreshTokenUI) {

        RefreshToken optional = refreshTokenRepository
                .findRefreshTokenByRefreshToken(dtoRefreshTokenUI.getRefreshToken())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoRefreshTokenUI.getRefreshToken())));

        if (isRefreshTokenExpired(optional.getExpireDate())) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_EXPIRED, dtoRefreshTokenUI.getRefreshToken()));
        }
        String accessToken = jwtService.generateToken(optional.getUser());

        RefreshToken savedRefreshToken = saveRefreshToken(optional.getUser());
        refreshTokenRepository.delete(optional);
        return new DtoRefreshToken(accessToken, savedRefreshToken.getRefreshToken());
    }
}
