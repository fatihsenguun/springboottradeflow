package com.fatihsengun.service;

import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartItemUI;
import com.fatihsengun.dto.DtoCartUI;

public interface ICartService {
    public DtoCart createCart(DtoCartUI dtoCartUI);

    public DtoCart getMyCart();

    public DtoCart deleteCartItem(DtoCartItemUI dtoCartItemUI);
}
