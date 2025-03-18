package com.sbfanton.online_shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.repository.CustomerRepository;
import com.sbfanton.online_shop.service.CustomerService;

import utils.ParamsConverter;
import utils.ParamsValidator;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer Customer) {
        return customerRepository.save(Customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByCustomerId(String id) {
        return customerRepository.findByCustomerId(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(String id, Customer updatedCustomer) {
        return customerRepository.findByCustomerId(id).map(customer -> {
        	customer.setCustomerId(id);
            customer.setName(updatedCustomer.getName());
            customer.setAge(updatedCustomer.getAge());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void deleteCustomerByCustomerId(String id) {
        customerRepository.deleteByCustomerId(id);
    }

	public List<Customer> getCustomersFiltered(Map<String, String> filters) throws Exception {
		ParamsValidator.validateCustomersParams(filters);
		Map<String, Object> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters);
		return customerRepository.searchCustomersFiltered(convertedFilters);
	}
	
}
