package com.fatihsengun.service;

import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProductService {


    public DtoProduct delete(UUID id);

    public DtoProduct addProduct(DtoProductUI dtoProductUI);

    public Product getProductById(UUID id);

    public void decreaseStock(Product product, Integer quantity);

    public Page<DtoProduct> getProductsWithAllCategories(List<UUID> categoryIds, Pageable pageable);

}
