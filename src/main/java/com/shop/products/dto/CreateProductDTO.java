package com.shop.products.dto;

import lombok.Data;

@Data
public class CreateProductDTO {
    private String name;
    private String category;
    private String description;
    private int quantity;
}
