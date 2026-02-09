package com.fatihsengun.service;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.RootResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {

    public DtoCategory addCategory(DtoCategoryUI dtoCategoryUI);

    public List<DtoCategory> getAllCategory();

    public DtoCategory deleteCategory(UUID categoryID);

}
