package service;

import entity.Product;

public interface CartService {

    boolean isEmpty();

    void add(Product product, int quantity);

    double getSubtotalPrice();

    double getTotalWeight();

}
