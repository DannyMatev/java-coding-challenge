package com.shop.products.service;

import com.shop.products.dto.CreateProductDTO;
import com.shop.products.dto.ProductResponse;
import com.shop.products.dto.UpdateProductDTO;
import com.shop.products.entity.CategoryProjection;
import com.shop.products.entity.ProductEntity;
import com.shop.products.exception.ProductNotFoundException;
import com.shop.products.exception.ProductQuantityNotAvailableException;
import com.shop.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Page<ProductResponse> fetchAll(Pageable pageable) {
        Page<ProductResponse> pageEntity = productRepository.findAll(pageable)
                .map(entity -> modelMapper.map(entity, ProductResponse.class));

        return pageEntity;
    }

    public ProductResponse fetchById(Long id) {
        ProductEntity entity = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        return modelMapper.map(entity, ProductResponse.class);
    }

    public ProductResponse createProduct(CreateProductDTO createProductDTO) {
        ProductEntity savedEntity = productRepository.save(
                modelMapper.map(createProductDTO, ProductEntity.class));

        return modelMapper.map(savedEntity, ProductResponse.class);
    }

    public void updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        ProductEntity existingEntity = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        ProductEntity updateEntity = modelMapper.map(updateProductDTO, ProductEntity.class);
        updateEntity.setId(existingEntity.getId());

        productRepository.save(updateEntity);
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    public synchronized void orderProduct(Long id, int quantity) {
        ProductEntity product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (product.getQuantity() < quantity) {
            throw new ProductQuantityNotAvailableException("Not enough items in the warehouse");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

    }

    public List<CategoryProjection> fetchAllCategories() {
        return productRepository.findAllCategories();
    }

}
