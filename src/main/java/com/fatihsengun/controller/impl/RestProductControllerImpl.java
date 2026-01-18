package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestProductController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
