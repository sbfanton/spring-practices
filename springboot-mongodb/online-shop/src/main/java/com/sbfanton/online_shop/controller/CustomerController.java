package com.sbfanton.online_shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers (
    		@RequestParam Map<String, String> filters) throws Exception {
        
    	if(filters != null) {
    		return ResponseEntity.ok(customerService.getCustomersFiltered(filters));
    	}
    	return ResponseEntity.ok(customerService.getAllCustomers());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable String id) {
        return customerService.getCustomerByCustomerId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        try {
            return ResponseEntity.ok(customerService.updateCustomer(id, customer));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustoemrByCustomerId(@PathVariable String id) {
        customerService.deleteCustomerByCustomerId(id);
        return ResponseEntity.noContent().build();
    }

}

