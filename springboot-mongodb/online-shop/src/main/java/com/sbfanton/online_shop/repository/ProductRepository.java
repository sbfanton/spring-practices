package com.sbfanton.online_shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbfanton.online_shop.model.Product;
import com.sbfanton.online_shop.repository.custom.GeneralRepositoryCustom;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, GeneralRepositoryCustom {

	Optional<Product> findByProductId(Integer id);
	
	void deleteByProductId(Integer id);
	
	@Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Product> findByNameLike(String name);
    
    List<Product> findByFacturer(String facturer);
}
