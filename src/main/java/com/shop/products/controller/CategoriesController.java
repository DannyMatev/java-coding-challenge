package com.shop.products.controller;

import com.shop.products.entity.CategoryProjection;
import com.shop.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<CategoryProjection>> fetchProduct() {
        return ResponseEntity.ok(productService.fetchAllCategories());
    }
}
