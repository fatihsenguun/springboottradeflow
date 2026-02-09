package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoCategory;
import com.fatihsengun.dto.DtoCategoryUI;
import com.fatihsengun.entity.Category;
import com.fatihsengun.entity.Product;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CategoryRepository;
import com.fatihsengun.service.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    @Transactional
    public DtoCategory deleteCategory(UUID categoryID) {

        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "No user")));

        for (Product product : category.getProducts()) {
            product.getCategories().remove(category);
        }

        categoryRepository.delete(category);
        return globalMapper.toDtoCategory(category);
    }
}
