package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.dto.DtoProductUI;
import com.fatihsengun.entity.Category;
import com.fatihsengun.entity.Product;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CategoryRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IGlobalMapper globalMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public DtoProduct addProduct(DtoProductUI dtoProductUI) {
        Product product = globalMapper.toProductEntity(dtoProductUI);

        List<UUID> list = dtoProductUI.getCategoryIds();
        List<Category> categories = new ArrayList<>();
        for (UUID categoryId : list) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(
                    () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, categoryId.toString())));
            categories.add(category);
        }
        product.setCategories(categories);


        return globalMapper.toDtoProduct(productRepository.save(product));
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, "id:" + id)));
    }

    @Override
    public void decreaseStock(Product product, Integer quantity) {

        if (product.getStock() < quantity) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Insufficient stock: " + product.getName()));
        }
        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
    }

    @Override
    public Page<DtoProduct> getProductsByCategories(List<UUID> categoryIds, Pageable pageable) {

        Page<Product> productPage = productRepository.findAllByCategories_IdIn(categoryIds, pageable);

        return productPage.map(globalMapper::toDtoProduct);
    }
}
