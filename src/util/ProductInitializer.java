package util;

import entity.Product;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ProductInitializer {

    public static Map<String, Product> initProducts() {
        Map<String, Product> products = new HashMap<>();
        products.put(
                "Cheese",
                new Product("Cheese", 100.0, 8, true, 400.0, true, LocalDateTime.of(2026, 10, 1, 0, 0))
        );
        products.put(
                "Biscuits",
                new Product("Biscuits", 75.0, 6, true, 200.0, true, LocalDateTime.of(2026, 10, 1, 0, 0))
        );
        products.put(
                "TV",
                new Product("TV", 350.0, 7, true, 1250.0, false)
        );
        products.put(
                "ScratchCard",
                new Product("ScratchCard", 50.0, 5, false, 0.0, false)
        );
        return products;
    }

}
