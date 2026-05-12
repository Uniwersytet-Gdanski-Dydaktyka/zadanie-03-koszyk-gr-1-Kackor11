package com.example;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
