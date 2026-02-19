package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestCartController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/cart")
public class RestCartControllerImpl extends RestRootResponseController implements IRestCartController {

    @Autowired
    private CartServiceImpl cartService;

    @Override
    @PostMapping("/add")
    public RootResponseEntity<DtoCart> addToCart(@Valid @RequestBody DtoCartUI dtoCartUI) {
        return ok(cartService.createCart(dtoCartUI));
    }

}
