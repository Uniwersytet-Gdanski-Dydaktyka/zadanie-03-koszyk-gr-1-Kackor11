package com.example;

import org.junit.jupiter.api.Test;
import  static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void shouldCreateProductWithCorrectBaseValues() {

        // Dane wejsciowe
        String expectedCode = "POO1";
        String expectedName = "Kubek Java";
        double expectedPrice = 49.99;

        // WHEN
        Product product = new Product(expectedCode, expectedName, expectedPrice);

        assertEquals(expectedCode, product.getCode());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedPrice, product.getPrice());

        assertEquals(expectedPrice, product.getDiscountPrice());
    }

}
