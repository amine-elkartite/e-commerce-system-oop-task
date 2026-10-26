package service.impl;

import entity.Customer;
import service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    public double remainingBalance(Customer customer, double totalPrice) {
        customer.setBalance(customer.getBalance() - totalPrice);
        return customer.getBalance();
    }
}
