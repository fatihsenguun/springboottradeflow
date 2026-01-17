package com.fatihsengun.controller.impl;


import com.fatihsengun.controller.IRestAuthController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoLogin;
import com.fatihsengun.dto.DtoLoginIU;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RestAuthControllerImpl extends RestRootResponseController implements IRestAuthController  {

    @Autowired
    private AuthServiceImpl authService;


    @Override
    @PostMapping("/register")
    public RootResponseEntity<DtoRegister> register(@Valid @RequestBody DtoRegisterUI dtoRegisterUI) {
        return ok( authService.register(dtoRegisterUI));
    }

    @Override
    @PostMapping("/authenticate")
    public RootResponseEntity<DtoLogin> authenticate(@Valid @RequestBody DtoLoginIU dtoLoginIU) {
       
        return ok(authService.authenticate(dtoLoginIU));
    }
}
