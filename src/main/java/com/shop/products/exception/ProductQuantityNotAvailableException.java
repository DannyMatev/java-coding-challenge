package com.shop.products.exception;

public class ProductQuantityNotAvailableException extends RuntimeException{
    public ProductQuantityNotAvailableException(String message) {
        super(message);
    }
}
