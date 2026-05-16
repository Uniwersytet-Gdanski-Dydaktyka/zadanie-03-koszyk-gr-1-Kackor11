package com.example;

import java.util.ArrayList;
import java.util.List;

public class PromotionOptimizer {

    public Cart applyOptimalPromotions(Cart originalCart, List<Promotion> promotions) {
        List<List<Promotion>> allPermutations = generatePermutations(promotions);

        Cart bestCart = null;
        double lowestPrice = Double.MAX_VALUE;

        for (List<Promotion> permutation : allPermutations) {
            Cart tempCart = new Cart();

            for (Product product : originalCart.getProducts()) {
                tempCart.addProduct(new Product(product.getCode(), product.getName(), product.getPrice()));
            }

            for (Promotion promotion : permutation) {
                tempCart.applyPromotion(promotion);
            }

            if (tempCart.getTotalPrice() <= lowestPrice) {
                lowestPrice = tempCart.getTotalPrice();
                bestCart = tempCart;
            }
        }

        return bestCart;
    }

    private List<List<Promotion>> generatePermutations(List<Promotion> list) {
        List<List<Promotion>> result = new ArrayList<>();
        if (list.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }
        Promotion first = list.getFirst();
        List<Promotion> rest = list.subList(1, list.size());
        for (List<Promotion> permutation : generatePermutations(rest)) {
            for (int i = 0; i <= permutation.size(); i++) {
                List<Promotion> temp = new ArrayList<>(permutation);
                temp.add(i, first);
                result.add(temp);
            }
        }
        return result;
    }
}
