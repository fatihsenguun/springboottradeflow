package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartUI;
import com.fatihsengun.entity.RootResponseEntity;

public interface IRestCartController {
    public RootResponseEntity<DtoCart> addToCart(DtoCartUI dtoCartUI);
}
