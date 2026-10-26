package util;

import entity.Cart;
import entity.Product;
import exception.CartEmptyException;

import java.util.List;
import java.util.Scanner;

public class InputUtil {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayMainMenu() {

        System.out.println("\t\t\tMAIN MENU");

        System.out.println("-".repeat(50));
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Exit");

        System.out.println("-".repeat(60));
    }

    public static void displayAllProducts(List<Product> products) {
        System.out.println("--- INVENTORY VIEW ---");

        String format = "%-15s | %-10s | %-5s | %-10s | %-10s%n";
        System.out.printf(format, "Product", "Price", "Stock", "Shippable", "Expirable");
        System.out.println("-".repeat(60));

        products.forEach(product ->
                System.out.printf(format, product.getName(), product.getPrice(), product.getQuantity(),
                        product.isShippable(), product.isExpirable()
                )
        );
    }

    public static Object[] enterProduct() {
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int requiredQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Added " + requiredQuantity + " x '" + productName + "' to cart");

        return new Object[]{productName, requiredQuantity};
    }

    public static void displayCart(Cart cart) {
        List<Product> products = cart.getProducts();

        if (products.isEmpty())
            throw new CartEmptyException("Cart is Empty");

        System.out.println("--- CART VIEW ---");

        System.out.println("-".repeat(60));

        products.forEach(product ->
                System.out.println(product.toString())
        );
    }


}
