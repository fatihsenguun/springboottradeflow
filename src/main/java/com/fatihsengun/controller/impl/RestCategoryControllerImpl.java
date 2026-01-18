package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestCategoryController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.ICategoryService;
import com.fatihsengun.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/category")
public class RestCategoryControllerImpl extends RestRootResponseController implements IRestCategoryController {

    @Autowired
    public CategoryServiceImpl categoryService;


    @Override
    @PostMapping("/add")
    public RootResponseEntity<DtoCategory> addCategory(@Valid @RequestBody DtoCategoryUI dtoCategoryUI) {
        return ok(categoryService.addCategory(dtoCategoryUI));
    }
}

