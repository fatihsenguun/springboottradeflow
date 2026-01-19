package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestProductController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/product")
public class RestProductControllerImpl extends RestRootResponseController implements IRestProductController {


    @Autowired
    private ProductServiceImpl productService;

    @Override
    @PostMapping("/add")
    public RootResponseEntity<DtoProduct> addProduct(@Valid @RequestBody DtoProductUI dtoProductUI) {

        return ok(productService.addProduct(dtoProductUI));
    }

    @Override
    @GetMapping("/filter")
    public RootResponseEntity<Page<DtoProduct>> getProductsByCategories(
            @RequestParam(name = "categories") List<UUID> categoryIds,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ok(productService.getProductsWithAllCategories(categoryIds, pageable));
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public RootResponseEntity<DtoProduct> delete(@PathVariable(name = "id") UUID id) {
        return ok(productService.delete(id));
    }
}
