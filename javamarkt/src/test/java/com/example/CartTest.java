package com.example;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;

class CartTest {

    @Test
    void shouldAddProductToCart() {
        Cart cart = new Cart();
        Product product = new Product("P001", "Laptop", 2500.00);

        cart.addProduct(product);

        assertEquals(1, cart.getProducts().size());
        assertEquals("Laptop", cart.getProducts().getFirst().getName());
    }

    @Test
    void shouldNotAddNullProduct() {
        Cart cart = new Cart();

        cart.addProduct(null);

        assertEquals(0, cart.getProducts().size());
    }

    @Test
    void shouldCalculateTotalPriceOfProducts() {
        Cart cart = new Cart();
        cart.addProduct(new Product("POO1", "Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));

        double totalPrice = cart.getTotalPrice();

        assertEquals(3800.00, totalPrice);
    }

    @Test
    void shouldReturnZeroForEmptyCart() {
        Cart cart = new Cart();

        double totalPrice = cart.getTotalPrice();

        assertEquals(0.0, totalPrice);
    }

    @Test
    void shouldFindCheapestProduct() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001","Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));
        cart.addProduct(new Product("P003", "Charger", 80.00));

        Product cheapest = cart.getCheapestProduct();

        assertEquals("Charger", cheapest.getName());
    }

    @Test
    void shouldFindMostExpensive() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001","Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));
        cart.addProduct(new Product("P003", "Charger", 80.00));

        Product priciest = cart.getPriciestProduct();

        assertEquals("Laptop", priciest.getName());
    }

    @Test
    void shouldThrowExcepttionWhenFindingCheapestInEmptyCart() {
        Cart cart = new Cart();

        assertThrows(IllegalStateException.class, cart::getCheapestProduct);
    }

    @Test
    void shouldFindNCheapestProducts() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));
        cart.addProduct(new Product("P003", "Charger", 80.00));
        cart.addProduct(new Product("P004", "Mouse", 120.00));

        List<Product> cheapest = cart.getNCheapestProducts(2);

        assertEquals(2, cheapest.size());
        assertEquals("Charger", cheapest.get(0).getName());
        assertEquals("Mouse", cheapest.get(1).getName());
    }

    @Test
    void shouldFindNPriciestProducts() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));
        cart.addProduct(new Product("P003", "Charger", 80.00));
        cart.addProduct(new Product("P004", "Mouse", 120.00));

        List<Product> priciest = cart.getNPriciestProducts(2);

        assertEquals(2, priciest.size());
        assertEquals("Laptop", priciest.get(0).getName());
        assertEquals("Smartphone", priciest.get(1).getName());
    }

    @Test
    void shouldReturnAllProductsIfNIsGreaterThanCartSize() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Smartphone", 1300.00));

        List<Product> result = cart.getNCheapestProducts(5);

        assertEquals(2, result.size());
    }

    @Test
    void shouldSortProductsByPriceDescendingThenByNameAscending() {
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Monitor", 800.00));
        cart.addProduct(new Product("P002", "Myszka", 100.00));
        cart.addProduct(new Product("P003", "Klawiatura", 100.00));
        cart.addProduct(new Product("P004", "Laptop", 2500.00));

        List<Product> sortedProducts = cart.getSortedProducts();

        assertEquals("Laptop", sortedProducts.getFirst().getName());
        assertEquals("Monitor", sortedProducts.get(1).getName());
        assertEquals("Klawiatura", sortedProducts.get(2).getName());
        assertEquals("Myszka", sortedProducts.get(3).getName());
    }

    @Test
    void shouldSortProductsByCustomCriteria() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Monitor", 800.00));
        cart.addProduct(new Product("P002", "Myszka", 100.00));
        cart.addProduct(new Product("P003", "Klawiatura", 100.00));

        // WHEN
        // Przekazujemy z zewnątrz własny komparator, który patrzy tylko na nazwę
        Comparator<Product> nameComparator = Comparator.comparing(Product::getName);
        List<Product> sortedByName = cart.getSortedProducts(nameComparator);

        // THEN
        // Kolejność alfabetyczna: Klawiatura, Monitor, Myszka
        assertEquals("Klawiatura", sortedByName.get(0).getName());
        assertEquals("Monitor", sortedByName.get(1).getName());
        assertEquals("Myszka", sortedByName.get(2).getName());
    }

    @Test
    void shouldApplyValueDiscountPromotionIfTotalIsOver300() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("POO1", "Klawiatura", 200.00));
        cart.addProduct(new Product("P002", ",Myszka", 150.00));

        Promotion valuePromotion = new ValueDiscountPromotion();

        // WHEN
        cart.applyPromotion(valuePromotion);

        // THEN
        // 5% znizki od 200 -> 190
        // 5% znizki od 150 -> 142.5
        assertEquals(190.00, cart.getProducts().getFirst().getDiscountPrice());
        assertEquals(142.50, cart.getProducts().get(1).getDiscountPrice());
    }

    @Test
    void shouldNotApplyValueDiscountPromotionIfTotalIsBelow300() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Klawiatura", 100.00));

        Promotion valuePromotion = new ValueDiscountPromotion();

        // WHEN
        cart.applyPromotion(valuePromotion);

        // THEN
        // Cena po znice powinna pozsotac bez zmian
        assertEquals(100.00, cart.getProducts().getFirst().getDiscountPrice());

    }

    @Test
    void shouldAddFreeCupIfTotalIsOver200() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2000.00));

        Promotion cupPromotion = new FreeCupPromotion();

        // WHEN
        cart.applyPromotion(cupPromotion);

        // THEN
        // W koszyku powinny być teraz 2 produkty (Klawiatura + Kubek)
        assertEquals(2, cart.getProducts().size());

        // Ostatni produkt to powinien być nasz kubek za 0 zł
        Product freeCup = cart.getProducts().get(1);
        assertEquals("Firmowy Kubek", freeCup.getName());
        assertEquals(0.00, freeCup.getDiscountPrice());
    }

    @Test
    void shouldNotAddFreeCupIfTotalIs200OrLess() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Myszka", 150.00));

        Promotion cupPromotion = new FreeCupPromotion();

        // WHEN
        cart.applyPromotion(cupPromotion);

        // THEN
        // W koszyku powinien zostać tylko 1 produkt (brak kubka)
        assertEquals(1, cart.getProducts().size());
    }

    @Test
    void shouldApplyDiscountCouponToSpecificProduct() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2500.00));
        cart.addProduct(new Product("P002", "Myszka", 100.00));

        // Tworzymy promocję na produkt o kodzie "P002" (Myszka)
        Promotion couponPromotion = new CouponDiscountPromotion("P002");

        // WHEN
        cart.applyPromotion(couponPromotion);

        // THEN
        // Laptop bez zmian
        assertEquals(2500.00, cart.getProducts().get(0).getDiscountPrice());
        // Myszka tańsza o 30% (100 * 0.70 = 70)
        assertEquals(70.00, cart.getProducts().get(1).getDiscountPrice());
    }

    @Test
    void shouldNotApplyCouponIfProductIsNotInCart() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Laptop", 2500.00));

        // Kupon na produkt, którego nie ma w koszyku
        Promotion couponPromotion = new CouponDiscountPromotion("P999");

        // WHEN
        cart.applyPromotion(couponPromotion);

        // THEN
        // Cena laptopa powinna pozostać bez zmian
        assertEquals(2500.00, cart.getProducts().getFirst().getDiscountPrice());
    }

    @Test
    void shouldApplyTwoPlusOnePromotionAndMakeCheapestProductsFree() {
        // GIVEN
        Cart cart = new Cart();
        // Dodajemy 6 produktów o różnych cenach (6 / 3 = 2 gratisy)
        cart.addProduct(new Product("P001", "Kurtka", 300.00));
        cart.addProduct(new Product("P002", "Buty", 200.00));
        cart.addProduct(new Product("P003", "Bluza", 150.00));
        cart.addProduct(new Product("P004", "Czapka", 60.00));
        cart.addProduct(new Product("P005", "Koszulka", 50.00));
        cart.addProduct(new Product("P006", "Skarpetki", 20.00));

        Promotion twoPlusOne = new TwoPlusOnePromotion();

        // WHEN
        cart.applyPromotion(twoPlusOne);

        // THEN
        // System powinien posortować produkty i wybrać 2 najtańsze jako darmowe.
        // Najtańsze to Skarpetki (20.00) i Koszulka (50.00).

        // Wyciągamy produkty z koszyka, żeby sprawdzić ich ceny po zniżce
        List<Product> products = cart.getProducts();

        // Szukamy konkretnych produktów po nazwie i sprawdzamy ich discountPrice
        Product koszulka = products.stream().filter(p -> p.getName().equals("Koszulka")).findFirst().orElseThrow();
        Product skarpetki = products.stream().filter(p -> p.getName().equals("Skarpetki")).findFirst().orElseThrow();
        Product kurtka = products.stream().filter(p -> p.getName().equals("Kurtka")).findFirst().orElseThrow();

        assertEquals(0.00, skarpetki.getDiscountPrice()); // Najtańszy -> gratis
        assertEquals(0.00, koszulka.getDiscountPrice());   // Drugi najtańszy -> gratis
        assertEquals(300.00, kurtka.getDiscountPrice());  // Drogi produkt -> bez zmian
    }

    @Test
    void shouldNotApplyTwoPlusOnePromotionIfCartHasLessThanThreeProducts() {
        // GIVEN
        Cart cart = new Cart();
        cart.addProduct(new Product("P001", "Kurtka", 300.00));
        cart.addProduct(new Product("P002", "Buty", 200.00)); // Tylko 2 produkty

        Promotion twoPlusOne = new TwoPlusOnePromotion();

        // WHEN
        cart.applyPromotion(twoPlusOne);

        // THEN
        // Żaden produkt nie powinien być darmowy
        List<Product> products = cart.getProducts();
        assertEquals(300.00, products.get(0).getDiscountPrice());
        assertEquals(200.00, products.get(1).getDiscountPrice());
    }
}
