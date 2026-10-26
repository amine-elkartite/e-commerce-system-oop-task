package service;

import entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    boolean isExpired(String productName);

    boolean isAvailable(String productName, int requiredQuantity);

    void updateQuantity(String productName, int amount);

}
