package com.example;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotalPrice() {
        double res = 0;
        for (Product product : products) {
            res += product.getDiscountPrice();
        }
        return res;
    }

    public Product getCheapestProduct() {
        return products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new IllegalStateException("Cart is empty"));
    }

    public Product getPriciestProduct() {
        return products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new IllegalStateException("Cart is empty"));
    }

    public List<Product> getNCheapestProducts(int n) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(n)
                .toList();
    }

    public List<Product> getNPriciestProducts(int n) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .limit(n)
                .toList();
    }

    public List<Product> getSortedProducts() {
        return products.stream().sorted().toList();
    }

    public List<Product> getSortedProducts(Comparator<Product> comparator) {
        return products.stream().sorted(comparator).toList();
    }

    public void updateProducts(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
    }

    public void applyPromotion(Promotion promotion) {
        promotion.apply(this);
    }
}
