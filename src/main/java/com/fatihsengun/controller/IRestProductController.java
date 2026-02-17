package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoPage;
import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.RootResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IRestProductController {
    public RootResponseEntity<DtoProduct> addProduct(DtoProductUI dtoProductUI);

    public RootResponseEntity<DtoPage<DtoProduct>> getProductsByCategories(List<UUID> categoryIds, Pageable pageable);

    public RootResponseEntity<DtoProduct> delete(UUID id);

    public RootResponseEntity<Page<DtoProduct>> getAllProducts(int page, int size);
}
