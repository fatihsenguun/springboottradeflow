package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoLogin;
import com.fatihsengun.dto.DtoLoginIU;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.entity.RootResponseEntity;

public interface IRestAuthController {

    public RootResponseEntity<DtoRegister> register(DtoRegisterUI dtoRegisterUI);
    public RootResponseEntity<DtoLogin> authenticate(DtoLoginIU dtoLoginIU);

}
