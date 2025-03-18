package com.sbfanton.online_shop.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbfanton.online_shop.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Product> findByNameLike(String name);
    
    List<Product> findByFacturer(String facturer);
}
