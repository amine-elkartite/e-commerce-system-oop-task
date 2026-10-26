package service.impl;

import entity.Cart;
import entity.Customer;
import entity.Product;
import exception.CartEmptyException;
import exception.CustomerNotMatchException;
import exception.InsufficientBalanceException;
import service.CartService;
import service.CheckoutService;
import service.CustomerService;
import service.ProductService;

import java.util.List;

public class CheckoutServiceImpl implements CheckoutService {

    private CustomerService customerService;
    private ProductService productService;
    private CartService cartService;
    private final double SHIPPING_RATE_PER_KG = 30.0;

    public CheckoutServiceImpl(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    public void checkout(Customer customer, Cart cart) {
        if (customer.getId() != cart.getCustomerId())
            throw new CustomerNotMatchException("Customer is not match");

        if (cartService.isEmpty())
            throw new CartEmptyException("Cart is Empty");

        System.out.println("\n" + "-".repeat(50));
        System.out.println(" ** Shipment notice ** ");

        List<Product> shippableProducts =
                cart.getProducts()
                        .stream()
                        .filter(Product::isShippable)
                        .toList();

        if (!shippableProducts.isEmpty()) {
            shippableProducts.forEach(product ->
                    System.out.println(product.getQuantity() + "x " + product.getName() + "\t\t" +
                            (product.getWeight() * product.getQuantity()) + "g")
            );
        } else {
            System.out.println("** No shippable products ** ");
        }

        System.out.println("Total package weight " + cartService.getTotalWeight() + "kg\n");

        System.out.println(" **  Checkout receipt ** ");
        cart.getProducts().forEach(product ->
                System.out.println(
                        product.getQuantity() + "x " +
                                product.getName() + "\t\t" +
                                product.getPrice() * product.getQuantity()
                )
        );

        System.out.println("-".repeat(50));
        double subTotalPrice = cartService.getSubtotalPrice();
        long shipping = Math.round(cartService.getTotalWeight() * SHIPPING_RATE_PER_KG);
        double totalPrice = subTotalPrice + shipping;

        if (customer.getBalance() < totalPrice) {
            throw new InsufficientBalanceException("Customer balance is insufficient");
        }

        cart.getProducts().stream()
                .filter(product -> !productService.isExpired(product.getName()))
                .forEach(product ->
                        productService.updateQuantity(product.getName(), product.getQuantity())
                );

        System.out.println("Subtotal: " + subTotalPrice);
        System.out.println("Shipping: " + shipping);
        System.out.println("Amount: " + totalPrice);

        System.out.println("-".repeat(50));
        customerService = new CustomerServiceImpl();
        System.out.println("Remaining balance: " + customerService.remainingBalance(customer, totalPrice));
        System.out.println("\n" + "-".repeat(50));

    }
}
