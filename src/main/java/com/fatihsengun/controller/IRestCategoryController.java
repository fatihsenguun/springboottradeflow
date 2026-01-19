package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.RootResponseEntity;
import org.springframework.http.ResponseEntity;

public interface IRestCategoryController {

    public RootResponseEntity<DtoCategory> addCategory(DtoCategoryUI dtoCategoryUI);



}
