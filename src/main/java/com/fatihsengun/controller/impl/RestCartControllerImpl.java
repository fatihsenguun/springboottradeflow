package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestCartController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartItemUI;
import com.fatihsengun.dto.DtoCartUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @GetMapping
    public RootResponseEntity<DtoCart> getMyCart() {
        return ok(cartService.getMyCart());
    }

    @Override
    @DeleteMapping("/delete")
    public RootResponseEntity<DtoCart> deleteItem(@Valid @RequestBody DtoCartItemUI dtoCartItemUI) {
        return ok(cartService.deleteCartItem(dtoCartItemUI));
    }

}
