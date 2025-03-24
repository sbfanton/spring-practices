package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.repository.CustomerRepository;
import com.sbfanton.onlineshop.service.CustomerService;

import com.sbfanton.onlineshop.utils.EnumUtils;
import com.sbfanton.onlineshop.utils.ParamsConverter;
import com.sbfanton.onlineshop.utils.ParamsValidator;
import com.sbfanton.onlineshop.utils.constants.CollectionsNames;
import com.sbfanton.onlineshop.utils.constants.CustomerReqAllowedParams;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) throws Exception {
    	if(customer.getNationalDocumentId() == null) {
    		throw new Exception("El nuevo cliente debe contar con un id (asociado a su número de documento correspondiente)");
    	}
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(String id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
        	customer.setNationalDocumentId(updatedCustomer.getNationalDocumentId());
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersFiltered(Map<String, String> filters) throws Exception {
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(CustomerReqAllowedParams.class, "getParamName"));
		
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters, Customer.class.getName(), null);
		
		Document initialMatch = customerRepository.getDocumentsFilteredMatch(
				convertedFilters, 
				Customer.class, 
				CollectionsNames.CUSTOMERS.getName());
		
		List<AggregationOperation> aggOpList = customerRepository.generateCustomerAggregationList(initialMatch);
		
		List<Customer> customers = (List<Customer>) customerRepository.getCustomModelListWithAgg(
				aggOpList, 
				Customer.class, 
				Customer.class);
		
		return customers;
	}
	
}
