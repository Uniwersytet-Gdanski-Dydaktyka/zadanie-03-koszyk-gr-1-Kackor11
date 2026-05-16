package com.example;

import java.util.List;

public class CouponDiscountPromotion implements Promotion {

    private final String targetCode;

    public CouponDiscountPromotion(String targetCode) {
        this.targetCode = targetCode;
    }

    @Override
    public void apply(Cart cart) {
        // List<Product> newList = new ArrayList<>();
        List<Product> discountedProducts = cart.getProducts().stream()
                .map(p -> p.getCode().equals(targetCode)
                        ? new Product(p.getCode(), p.getName(), p.getPrice(), p.getDiscountPrice() * 0.7)
                        : p)
                .toList();

        cart.updateProducts(discountedProducts);
    }
}
