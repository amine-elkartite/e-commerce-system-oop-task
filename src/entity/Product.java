package entity;

import service.ShippingService;

import java.time.LocalDateTime;

public class Product implements ShippingService {

    private String name;
    private double price;
    private int quantity;
    private boolean shippable;
    private boolean expirable;
    private double weight;
    private LocalDateTime expiryDate;

    public Product(String name, double price, int quantity, boolean shippable, double weight, boolean expirable) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippable = shippable;
        this.weight = weight;
        this.expirable = expirable;
    }

    public Product(String name, double price, int quantity, boolean shippable, double weight, boolean expirable, LocalDateTime expiryDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippable = shippable;
        this.weight = weight;
        this.expirable = expirable;
        this.expiryDate = expiryDate;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isShippable() {
        return shippable;
    }

    public void setShippable(boolean shippable) {
        this.shippable = shippable;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpirable() {
        return expirable;
    }

    public void setExpirable(boolean expirable) {
        this.expirable = expirable;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + (price * quantity) +
                '}';
    }
}
