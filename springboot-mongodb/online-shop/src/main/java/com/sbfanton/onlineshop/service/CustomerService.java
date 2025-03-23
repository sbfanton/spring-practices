package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.Customer;

public interface CustomerService {
    public Customer createCustomer(Customer Customer) throws Exception;
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerById(String id);
    public Optional<Customer> getCustomerByEmail(String email);
    public List<Customer> getCustomersFiltered(Map<String, String> filters) throws Exception;
    public Customer updateCustomer(String id, Customer updatedCustomer);
    public void deleteCustomerById(String id);
}
