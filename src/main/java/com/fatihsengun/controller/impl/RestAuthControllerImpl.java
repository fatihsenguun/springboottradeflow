package com.fatihsengun.controller.impl;


import com.fatihsengun.controller.IRestAuthController;
import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private AuthServiceImpl authService;


    @Override
    @PostMapping("/register")
    public DtoRegister register(@RequestBody DtoRegisterUI dtoRegisterUI) {
        return authService.register(dtoRegisterUI);
    }
}
