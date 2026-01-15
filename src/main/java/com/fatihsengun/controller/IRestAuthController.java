package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;

public interface IRestAuthController {

    public DtoRegister register(DtoRegisterUI dtoRegisterUI);

}
