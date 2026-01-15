package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.entity.User;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.AuthRepository;
import com.fatihsengun.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {


    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private IGlobalMapper globalMapper;


    @Override
    public DtoRegister register(DtoRegisterUI dtoRegisterUI) {

        User user = authRepository.save(globalMapper.toUserEntity(dtoRegisterUI));

        return globalMapper.toDtoRegister(user);
    }
}
