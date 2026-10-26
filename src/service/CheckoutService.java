package service;

import entity.Cart;
import entity.Customer;

public interface CheckoutService {

    void checkout(Customer customer, Cart cart);

}
