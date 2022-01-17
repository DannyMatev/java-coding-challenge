package com.shop.products.controller;

import com.shop.products.dto.ErrorResponse;
import com.shop.products.exception.ProductNotFoundException;
import com.shop.products.exception.ProductQuantityNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleProductNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ProductQuantityNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleProductQuantityNotAvailableException(
            ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(errorResponse);
    }


}
