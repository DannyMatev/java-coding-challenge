package com.shop.products.controller;

import com.shop.products.dto.CreateProductDTO;
import com.shop.products.dto.ProductResponse;
import com.shop.products.dto.UpdateProductDTO;
import com.shop.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> fetchProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.fetchById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> fetchAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.fetchAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        ProductResponse response = productService.createProduct(createProductDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") Long id,
                                              @RequestBody UpdateProductDTO updateProductDTO) {
        productService.updateProduct(id, updateProductDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/order/{quantity}")
    public ResponseEntity<Void> orderProduct(@PathVariable("id") Long id,
                                             @PathVariable("quantity") int quantity) {
        productService.orderProduct(id, quantity);

        return ResponseEntity.ok().build();
    }
}
