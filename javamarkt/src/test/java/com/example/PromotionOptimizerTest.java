package com.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PromotionOptimizerTest {

    @Test
    void shouldFindBestPromotionOrderForCustomer() {
        // GIVEN
        Cart cart = new Cart();
        // Suma początkowa = 320 zł
        cart.addProduct(new Product("P001", "Klawiatura", 200.00));
        cart.addProduct(new Product("P002", "Myszka", 120.00));

        Promotion valueDiscount = new ValueDiscountPromotion(); // 5% powyżej 300
        Promotion couponDiscount = new CouponDiscountPromotion("P001"); // 30% na Klawiaturę

        List<Promotion> promotions = List.of(valueDiscount, couponDiscount);
        PromotionOptimizer optimizer = new PromotionOptimizer();

        // WHEN
        Cart bestCart = optimizer.applyOptimalPromotions(cart, promotions);

        // THEN
        // Wariant 1 (Kupon -> ValueDiscount):
        //   Suma = 140 + 120 = 260. ValueDiscount nie działa. Koszt = 260.
        //
        // Wariant 2 (ValueDiscount -> Kupon):
        //   Suma po 5%: P001 = 190, P002 = 114. Suma = 304.
        //   Kupon -30% na P001: 190 -> 133.
        //   Ostateczny koszt = 133 + 114 = 247.
        //
        // Wariant 2 jest lepszy!
        assertEquals(247.00, bestCart.getTotalPrice(), 0.01);
    }
}