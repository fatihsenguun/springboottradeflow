package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.RootResponseEntity;

public interface IRestProductController {
    public RootResponseEntity<DtoProduct> addProduct(DtoProductUI dtoProductUI);
}
