package service.impl;

import entity.Cart;
import entity.Product;
import exception.DuplicateProductException;
import exception.InsufficientQuantityException;
import exception.ProductExpiredException;
import exception.ProductNotFoundException;
import service.CartService;
import service.ProductService;

import java.util.List;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private Cart cart;
    private ProductService productService;

    public CartServiceImpl(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    @Override
    public boolean isEmpty() {
        return cart.getProducts().isEmpty();
    }

    @Override
    public void add(Product product, int quantity) {
        Optional<Product> productExist = productService.findAllProducts().stream()
                .filter(prod -> prod.getName().equals(product.getName()))
                .findFirst();
        if (productExist.isEmpty())
            throw new ProductNotFoundException("Product with name: " + product.getName() + "not found");

        if (!productService.isAvailable(product.getName(), quantity))
            throw new InsufficientQuantityException("Insufficient quantity for " + product.getName());

        if (productService.isExpired(product.getName()))
            throw new ProductExpiredException("Product " + product.getName() + " is expired");

        List<Product> products = cart.getProducts();

        Optional<Product> savedProduct = products.stream()
                .filter(prod -> prod.getName().equals(product.getName()))
                .findFirst();
        if (savedProduct.isPresent())
            throw new DuplicateProductException("Product with name: " + savedProduct.get().getName() + " already exist in cart");

        if (product.isShippable() && product.isExpirable()) {
            products.add(new Product(product.getName(), product.getPrice(),
                    quantity, product.isShippable(), product.getWeight(),
                    true, product.getExpiryDate()));
        } else if (product.isShippable()) {
            products.add(new Product(product.getName(), product.getPrice(),
                    quantity, product.isShippable(), product.getWeight(),
                    false));
        } else if (product.isExpirable()) {
            products.add(new Product(product.getName(), product.getPrice(),
                    quantity, false, 0,
                    true, product.getExpiryDate()));
        } else {
            products.add(new Product(product.getName(), product.getPrice(),
                    quantity, false, 0,
                    false));
        }

        cart.setProducts(products);
    }

    @Override
    public double getSubtotalPrice() {
        return cart.getProducts().stream()
                .mapToDouble(product ->
                        product.getPrice() * product.getQuantity())
                .sum();
    }

    @Override
    public double getTotalWeight() {
        if (!isEmpty()) {
            return cart.getProducts().stream()
                    .filter(Product::isShippable)
                    .mapToDouble(product -> product.getWeight() * product.getQuantity())
                    .sum() / 1000;
        }
        return 0;
    }
}
