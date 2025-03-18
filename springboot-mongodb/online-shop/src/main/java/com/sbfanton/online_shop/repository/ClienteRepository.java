package com.sbfanton.online_shop.repository;

import java.util.List;
import java.util.Optional;

import com.sbfanton.online_shop.model.Cliente;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ClienteRepository extends MongoRepository<Cliente, String>{
    
	Optional<Cliente> findByEmail(String email);
	
	@Query("{'nombre': {'$regex': ?0, '$options': 'i'}}")
    List<Cliente> findByNombreLike(String nombre);
}
