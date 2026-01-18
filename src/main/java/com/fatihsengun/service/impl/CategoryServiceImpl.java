package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.Category;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CategoryRepository;
import com.fatihsengun.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IGlobalMapper globalMapper;

    @Override
    public DtoCategory addCategory(DtoCategoryUI dtoCategoryUI) {
        Category category = globalMapper.toCategoryEntity(dtoCategoryUI);

        return globalMapper.toDtoCategory(categoryRepository.save(category));
    }
}
