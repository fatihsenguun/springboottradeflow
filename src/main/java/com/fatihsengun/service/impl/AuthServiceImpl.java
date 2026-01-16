package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoLogin;
import com.fatihsengun.dto.DtoLoginIU;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.entity.User;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.jwt.JwtService;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.AuthRepository;
import com.fatihsengun.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    @Override
    public DtoRegister register(DtoRegisterUI dtoRegisterUI) {

        User user = globalMapper.toUserEntity(dtoRegisterUI);
        user.setPassword(passwordEncoder.encode(dtoRegisterUI.getPassword()));
        user = authRepository.save(user);
        return globalMapper.toDtoRegister(user);
    }

    public DtoLogin authenticate(DtoLoginIU dtoLoginIU) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    dtoLoginIU.getEmail(), dtoLoginIU.getPassword());
            authenticationProvider.authenticate(auth);
            User optional = authRepository.findByEmail(dtoLoginIU.getEmail()).orElseThrow(
                    () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoLoginIU.getEmail())));


            String accessToken = jwtService.generateToken(optional);
            return new DtoLogin(accessToken);

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoLoginIU.getEmail()));
        }


    }
}
