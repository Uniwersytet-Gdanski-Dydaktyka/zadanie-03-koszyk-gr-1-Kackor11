package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TwoPlusOnePromotion implements Promotion{

    @Override
    public void apply(Cart cart) {
        List<Product> products = cart.getProducts();
        int freeCount = products.size() / 3;

        if (freeCount == 0) {
            return;
        }

        List<Product> sortedByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getDiscountPrice))
                .toList();

        List<Product> result = new ArrayList<>();
        for (int i = 0; i < sortedByPrice.size(); i++) {
            Product p = sortedByPrice.get(i);
            if (i < freeCount) {
                result.add(new Product(p.getCode(), p.getName(), p.getPrice(), 0.00));
            } else {
                result.add(p);
            }
        }
        cart.updateProducts(result);
    }
}
