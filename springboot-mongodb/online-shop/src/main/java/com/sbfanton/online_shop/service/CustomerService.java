package com.sbfanton.online_shop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.online_shop.model.Customer;

public interface CustomerService {
    public Customer createCustomer(Customer Customer);
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerByCustomerId(String id);
    public Optional<Customer> getCustomerByEmail(String email);
    public List<Customer> getCustomersFiltered(Map<String, String> filters) throws Exception;
    public Customer updateCustomer(String id, Customer updatedCustomer);
    public void deleteCustomerByCustomerId(String id);
}
