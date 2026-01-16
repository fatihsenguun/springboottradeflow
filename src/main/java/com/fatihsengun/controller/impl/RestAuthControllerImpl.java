package com.fatihsengun.controller.impl;


import com.fatihsengun.controller.IRestAuthController;
import com.fatihsengun.dto.DtoLogin;
import com.fatihsengun.dto.DtoLoginIU;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private AuthServiceImpl authService;


    @Override
    @PostMapping("/register")
    public DtoRegister register(@Valid @RequestBody DtoRegisterUI dtoRegisterUI) {
        return authService.register(dtoRegisterUI);
    }

    @Override
    @PostMapping("/authenticate")
    public DtoLogin authenticate(@Valid @RequestBody DtoLoginIU dtoLoginIU) {
        System.out.println(dtoLoginIU);
        return authService.authenticate(dtoLoginIU);
    }
}
