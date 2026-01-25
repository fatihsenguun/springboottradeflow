package com.fatihsengun.repository;

import com.fatihsengun.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByCategories_IdIn(List<UUID> categories, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c " +
            "WHERE c.id IN :categoryIds " +
            "GROUP BY p.id " +
            "HAVING COUNT(DISTINCT c.id) = :listSize")
    Page<Product> findProductsWithAllCategories(
            @Param("categoryIds") List<UUID> categoryIds,
            @Param("listSize") Long listSize,
            Pageable pageable
    );

    List<Product> findTop5ByOrderByTotalSalesCountDesc();

}
