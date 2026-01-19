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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"product_filter", "product_detail"}, allEntries = true)
    public DtoProduct delete(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, id.toString())));
        product.setDeleted(true);
        return globalMapper.toDtoProduct(productRepository.save(product));
    }


    @Override
    @CacheEvict(value = {"product_filter", "product_detail"}, allEntries = true)
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
    @Cacheable(value = "product_detail", key = "#id")
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
    @Cacheable(value = "product_filter", key = "#categoryIds.toString() + ':page:' + #pageable.pageNumber")
    public Page<DtoProduct> getProductsWithAllCategories(List<UUID> categoryIds, Pageable pageable) {

        if (categoryIds == null || categoryIds.isEmpty()) {
            return Page.empty();
        }

        long listSize = categoryIds.size();

        Page<Product> productPage = productRepository.findProductsWithAllCategories(categoryIds, listSize, pageable);

        return productPage.map(globalMapper::toDtoProduct);
    }
}
