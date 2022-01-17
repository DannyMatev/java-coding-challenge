package com.shop.products.service;

import com.shop.products.ProductsApplication;
import com.shop.products.exception.ProductQuantityNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(classes = ProductsApplication.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldDecreaseProductQuantityCorrectlyWithConcurrentRequests() throws InterruptedException {
        int amount = productService.fetchById(1002L).getQuantity();
        ExecutorService executor = Executors.newFixedThreadPool(amount);
        List<Callable<Object>> tasks = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            tasks.add(Executors.callable(() -> productService.orderProduct(1002L, 1)));
        }
        executor.invokeAll(tasks);

        Assert.isTrue(productService.fetchById(1002L).getQuantity() == 0);
    }

    @Test
    void shouldThrowAnExceptionWhenRequiredQuantityIsNotAvailable() {
        System.out.println(1);
        int amount = productService.fetchById(1002L).getQuantity() + 1;
        Assertions.assertThrows(
                ProductQuantityNotAvailableException.class,
                () -> productService.orderProduct(1002L, amount));
    }

}
