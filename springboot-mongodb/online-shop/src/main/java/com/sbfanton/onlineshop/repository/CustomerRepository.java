package com.sbfanton.onlineshop.repository;

import java.util.Optional;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.repository.custom.CustomerRepositoryCustom;
import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, GeneralRepositoryCustom, CustomerRepositoryCustom {
    
	Optional<Customer> findByEmail(String email);
	
	Optional<Customer> findByNationalDocumentId(String nationalDocumentId);
	
}
