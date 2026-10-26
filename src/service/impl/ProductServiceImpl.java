package service.impl;

import entity.Product;
import exception.ProductNotFoundException;
import service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private List<Product> products;

    public ProductServiceImpl(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Product> findAllProducts() {
        if (!products.isEmpty())
            return products;
        return null;
    }

    @Override
    public boolean isExpired(String productName) {
        Product product = findProductByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product with name: " + productName + "not found"));
        if (!product.isExpirable())
            return false;
        return LocalDateTime.now().isAfter(product.getExpiryDate());
    }

    @Override
    public boolean isAvailable(String productName, int requiredQuantity) {
        Product product = findProductByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product with name: " + productName + "not found"));
        return product.getQuantity() >= requiredQuantity;
    }

    @Override
    public void updateQuantity(String productName, int amount) {
        Product product = findProductByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product with name: " + productName + "not found"));
        product.setQuantity(product.getQuantity() - amount);
    }

    private Optional<Product> findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst();
    }

}
