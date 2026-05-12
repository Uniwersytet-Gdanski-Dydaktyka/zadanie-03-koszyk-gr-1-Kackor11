package com.example;

import java.util.Comparator;

public class Product implements Comparable<Product> {

    private final String code;
    private final String name;
    private final Double price;
    private final Double discountPrice;

    public Product(String code, String name, Double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    public Product(String code, String name, Double price, Double discountPrice) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
    }

//    @Override
//    public int compareTo(Product other) {
//        if (other.getPrice().equals(this.getPrice())) {
//            return this.name.compareTo(other.getName());
//        }
//        return Double.compare(other.getPrice(), this.getPrice());
//    }

    @Override
    public int compareTo(Product other) {
        return Comparator.comparing(Product::getPrice).reversed()
                .thenComparing(Product::getName)
                .compare(this, other);
    }

    public String getCode() { return code; }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }


}
