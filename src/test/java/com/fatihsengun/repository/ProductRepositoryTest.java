package com.fatihsengun.repository;

import com.fatihsengun.entity.Category;
import com.fatihsengun.entity.Product;
import com.fatihsengun.entity.User;
import com.fatihsengun.enums.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProductRepositoryTest {


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product sharedProduct1;
    private Product sharedProduct2;
    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setName("Electronics");
        category1 = categoryRepository.save(category1);

        category2 = new Category();
        category2.setName("Gaming");
        category2 = categoryRepository.save(category2);

        sharedProduct1 = Product.builder()
                .name("Test 1")
                .price(new BigDecimal("100.00"))
                .categories(new ArrayList<>(List.of(category1)))
                .stock(5)
                .totalSalesCount(6)
                .build();
        sharedProduct2 = Product.builder()
                .name("Test 2")
                .price(new BigDecimal("100.00"))
                .categories(new ArrayList<>(List.of(category1, category2)))
                .stock(5)
                .totalSalesCount(3)
                .build();


        productRepository.save(sharedProduct1);
        productRepository.save(sharedProduct2);

    }

    @Test
    public void findProductsWithAllCategories_shouldReturnProducts() {

        List<UUID> categoryIds = List.of(category1.getId(), category2.getId());
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> result = productRepository.findProductsWithAllCategories(
                categoryIds,
                (long) categoryIds.size(),
                pageable
        );

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test 2");



    }

}
