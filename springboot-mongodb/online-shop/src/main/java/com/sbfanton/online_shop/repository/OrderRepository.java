package com.sbfanton.online_shop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sbfanton.online_shop.model.Order;
import com.sbfanton.online_shop.repository.custom.GeneralRepositoryCustom;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, GeneralRepositoryCustom {
    
	Optional<Order> findOrderByOrderId(Integer id);
	
	void deleteByorderId(Integer id);
}
