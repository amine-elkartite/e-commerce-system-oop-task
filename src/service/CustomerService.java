package service;

import entity.Customer;

public interface CustomerService {

    double remainingBalance(Customer customer, double totalPrice);

}
