package com.fatihsengun.controller.impl;


import com.fatihsengun.controller.IRestAuthController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.*;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.AuthServiceImpl;
import com.fatihsengun.service.impl.RefreshTokenServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RestAuthControllerImpl extends RestRootResponseController implements IRestAuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;


    @Override
    @PostMapping("/register")
    public RootResponseEntity<DtoRegister> register(@Valid @RequestBody DtoRegisterUI dtoRegisterUI) {
        return ok(authService.register(dtoRegisterUI));
    }

    @Override
    @PostMapping("/authenticate")
    public RootResponseEntity<DtoLogin> authenticate(@Valid @RequestBody DtoLoginIU dtoLoginIU) {

        return ok(authService.authenticate(dtoLoginIU));
    }

    @Override
    @PostMapping("/refreshToken")
    public RootResponseEntity<DtoRefreshToken> refreshToken(@Valid @RequestBody DtoRefreshTokenUI dtoRefreshTokenUI) {
        return ok(refreshTokenService.refreshToken(dtoRefreshTokenUI));
    }

    @Override
    @GetMapping("/getUser")
    public RootResponseEntity<DtoUser> getMyInfo() {
        return ok(authService.getMyInfo());
    }

}
