package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoLogin;
import com.fatihsengun.dto.DtoLoginIU;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;

public interface IRestAuthController {

    public DtoRegister register(DtoRegisterUI dtoRegisterUI);
    public DtoLogin authenticate(DtoLoginIU dtoLoginIU);

}
