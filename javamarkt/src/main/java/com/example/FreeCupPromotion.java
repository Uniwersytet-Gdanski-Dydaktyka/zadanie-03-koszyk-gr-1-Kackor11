package com.example;

public class FreeCupPromotion implements Promotion {

    @Override
    public void apply(Cart cart) {
        if (cart.getTotalPrice() <= 200) {
            return;
        }
        cart.addProduct(new Product("CUP", "Firmowy Kubek", 20.00, 0.00));
    }
}
