package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.dto.CustomerDTO;

public interface CustomerService {
    public CustomerDTO createCustomer(CustomerDTO Customer) throws Exception;
    public List<CustomerDTO> getAllCustomers();
    public Optional<CustomerDTO> getCustomerById(String id);
    public Optional<CustomerDTO> getCustomerByEmail(String email);
    public List<CustomerDTO> getCustomersFiltered(Map<String, String> filters) throws Exception;
    public CustomerDTO updateCustomer(String id, CustomerDTO updatedCustomer);
    public void deleteCustomerById(String id);
}
