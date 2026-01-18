package com.fatihsengun.service;

import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.Product;

import java.util.UUID;

public interface IProductService {


    public DtoProduct addProduct(DtoProductUI dtoProductUI);

    public Product getProductById(UUID id);

    public void decreaseStock(Product product, Integer quantity);
}
