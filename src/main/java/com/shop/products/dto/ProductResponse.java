package com.shop.products.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private int quantity;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

}
