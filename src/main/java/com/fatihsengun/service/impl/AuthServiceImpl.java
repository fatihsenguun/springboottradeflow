package com.fatihsengun.service.impl;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.RefreshToken;
import com.fatihsengun.entity.User;
import com.fatihsengun.entity.Wallet;
import com.fatihsengun.enums.RoleType;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.jwt.JwtService;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.AuthRepository;
import com.fatihsengun.repository.RefreshTokenRepository;
import com.fatihsengun.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {


    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private IGlobalMapper globalMapper;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    private IdentityService identityService;


    @Override
    public DtoRegister register(DtoRegisterUI dtoRegisterUI) {

        User user = globalMapper.toUserEntity(dtoRegisterUI);
        user.setPassword(passwordEncoder.encode(dtoRegisterUI.getPassword()));
        user.setRole(RoleType.USER);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("TRY");
        wallet.setUser(user);

        user.setWallet(wallet);


        User savedUser = authRepository.save(user);
        return globalMapper.toDtoRegister(savedUser);
    }

    @Override
    public DtoUser getMyInfo() {
        User currentUser = identityService.getCurrentUser();

        return globalMapper.toDtoUser(currentUser);
    }

    public DtoLogin authenticate(DtoLoginIU dtoLoginIU) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    dtoLoginIU.getEmail(), dtoLoginIU.getPassword());
            authenticationProvider.authenticate(auth);
            User user = authRepository.findByEmail(dtoLoginIU.getEmail()).orElseThrow(
                    () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoLoginIU.getEmail())));
            Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findRefreshTokenByUserId(user.getId());
            log.info("🔐 BAŞARILI GİRİŞ: Kullanıcı '{}' sisteme giriş yaptı.", dtoLoginIU.getEmail());
            if (optionalRefreshToken.isPresent()) {
                refreshTokenRepository.delete(optionalRefreshToken.get());

            }
            DtoLogin dtoLogin = new DtoLogin();

            dtoLogin.setAccessToken(jwtService.generateToken(user));
            dtoLogin.setRefreshToken(refreshTokenService.saveRefreshToken(user).getRefreshToken());
            dtoLogin.setFirstName(user.getFirstName());
            dtoLogin.setLastName(user.getLastName());
            dtoLogin.setRole(user.getRole());
            return dtoLogin;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password");
        } catch (Exception e) {
            log.error("Login error : {}", e.getMessage());
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, dtoLoginIU.getEmail()));

        }

    }
}
