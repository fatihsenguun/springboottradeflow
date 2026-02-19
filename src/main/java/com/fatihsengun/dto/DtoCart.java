package com.fatihsengun.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DtoCart {

    private DtoUser user;

    private List<DtoCartItem> items;
}
