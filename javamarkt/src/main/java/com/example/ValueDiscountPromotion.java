package com.example;

import java.util.ArrayList;
import java.util.List;

public class ValueDiscountPromotion implements Promotion {

    @Override
    public void apply(Cart cart) {
         if (cart.getTotalPrice() <= 300 ) {
             return;
         }
         List<Product> newProducts = new ArrayList<>();
         for (Product product : cart.getProducts()) {
             newProducts.add(new Product(product.getCode(), product.getName(), product.getPrice(), product.getDiscountPrice() * 0.95));
         }
         cart.updateProducts(newProducts);
    }
}
