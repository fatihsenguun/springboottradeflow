package com.fatihsengun.service;

import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartItemUI;
import com.fatihsengun.dto.DtoCartUI;
import com.fatihsengun.entity.Cart;

public interface ICartService {
    public DtoCart createCart(DtoCartUI dtoCartUI);

    public DtoCart getMyCart(String guestId);

    public DtoCart deleteCartItem(DtoCartItemUI dtoCartItemUI, String guestId);

}
