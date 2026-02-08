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

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<DtoCategory> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();
        List<DtoCategory> dtoCategories = new ArrayList<>();
        for (Category category : allCategory) {

            DtoCategory dtoCategory = globalMapper.toDtoCategory(category);
            dtoCategories.add(dtoCategory);
        }
        return dtoCategories;
    }
}
