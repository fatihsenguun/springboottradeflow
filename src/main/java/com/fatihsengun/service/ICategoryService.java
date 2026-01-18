package com.fatihsengun.service;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.RootResponseEntity;

public interface ICategoryService {

    public DtoCategory addCategory(DtoCategoryUI dtoCategoryUI);

}
