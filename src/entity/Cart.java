package entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private int customerId;
    private List<Product> products;

    public Cart(int customerId) {
        this.customerId = customerId;
        this.products = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
