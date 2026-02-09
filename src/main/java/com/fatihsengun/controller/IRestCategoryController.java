package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.RootResponseEntity;
import jakarta.persistence.criteria.Root;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IRestCategoryController {

    public RootResponseEntity<DtoCategory> addCategory(DtoCategoryUI dtoCategoryUI);

    public RootResponseEntity<List<DtoCategory>> getAllCategory();

    public RootResponseEntity<DtoCategory> deleteCategory(UUID categoryId);


}
