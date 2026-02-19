package com.fatihsengun.dto;

import com.fatihsengun.entity.Cart;
import com.fatihsengun.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCartItem {



    private DtoProduct product;

    private Long quantity;

}
