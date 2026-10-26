import entity.Cart;
import entity.Customer;
import entity.Product;
import exception.handler.GlobalExceptionHandler;
import service.CartService;
import service.CheckoutService;
import service.ProductService;
import service.impl.CartServiceImpl;
import service.impl.CheckoutServiceImpl;
import service.impl.ProductServiceImpl;
import util.InputUtil;

import java.util.Map;
import java.util.Scanner;

import static util.ProductInitializer.initProducts;

public class Controller {

    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService;
    private static CheckoutService checkoutService;
    private static CartService cartService;

    public static void main(String[] args) {

        Customer customer = new Customer(1, "Mahmoud Gamal", "01008259336", 1300.0);

        Map<String, Product> products = initProducts();

        productService = new ProductServiceImpl(products.values().stream().toList());

        Cart cart = new Cart(customer.getId());

        cartService = new CartServiceImpl(cart, productService);
        checkoutService = new CheckoutServiceImpl(productService, cartService);

        boolean running = true;

        while (running) {
            try {
                InputUtil.displayMainMenu();
                int choiceNumber = scanner.nextInt();

                switch (choiceNumber) {
                    case 1:
                        InputUtil.displayAllProducts(products.values().stream().toList());
                        break;
                    case 2:
                        Object[] data = InputUtil.enterProduct();
                        cartService.add(products.get(data[0]), (Integer) data[1]);
                        break;
                    case 3:
                        InputUtil.displayCart(cart);
                        break;
                    case 4:
                        running = false;
                        System.out.println("Thank you for using E-commerce System");
                        break;
                    default:
                        System.out.println("Please select correct choice");
                        break;
                }
            } catch (Exception e) {
                GlobalExceptionHandler.handle(e);
            }
        }

        try {
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }

    }
}