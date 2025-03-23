package com.sbfanton.onlineshop.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, GeneralRepositoryCustom {
	
	@Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Product> findByNameLike(String name);
}
