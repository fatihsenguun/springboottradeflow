package com.fatihsengun.service;

import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.dto.DtoUser;

public interface IAuthService {
    public DtoRegister register(DtoRegisterUI dtoRegisterUI);

    public DtoUser getMyInfo();

}
