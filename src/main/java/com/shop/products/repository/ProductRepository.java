package com.shop.products.repository;

import com.shop.products.entity.CategoryProjection;
import com.shop.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {

    @Query(value = "SELECT PRODUCTS.CATEGORY AS category, COUNT(PRODUCTS.CATEGORY) AS productsAvailable FROM PRODUCTS GROUP BY PRODUCTS.CATEGORY",
    nativeQuery = true)
    List<CategoryProjection> findAllCategories();
}
