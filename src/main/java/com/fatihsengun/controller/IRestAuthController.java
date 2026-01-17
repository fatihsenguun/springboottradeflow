package com.fatihsengun.controller;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.RootResponseEntity;

public interface IRestAuthController {

    public RootResponseEntity<DtoRegister> register(DtoRegisterUI dtoRegisterUI);

    public RootResponseEntity<DtoLogin> authenticate(DtoLoginIU dtoLoginIU);

    public RootResponseEntity<DtoRefreshToken> refreshToken(DtoRefreshTokenUI dtoRefreshTokenUI);

}
