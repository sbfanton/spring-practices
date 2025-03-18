package com.sbfanton.online_shop.repository;

import java.util.Optional;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.repository.custom.CustomerRepositoryCustom;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, CustomerRepositoryCustom {
    
	Optional<Customer> findByCustomerId(String id);
	
	Optional<Customer> findByEmail(String email);
	
	void deleteByCustomerId(String id);
}
