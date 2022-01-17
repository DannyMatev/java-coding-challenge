package com.shop.products.dto;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class UpdateProductDTO {
    private String name;
    private String category;
    private String description;
    @Positive
    private int quantity;
}
