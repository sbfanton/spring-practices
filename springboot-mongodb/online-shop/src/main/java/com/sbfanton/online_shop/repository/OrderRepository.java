package com.sbfanton.online_shop.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sbfanton.online_shop.model.Order;
import com.sbfanton.online_shop.repository.custom.GeneralRepositoryCustom;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, GeneralRepositoryCustom {
    
	List<Order> findByCustomerId(String customerId);
    List<Order> findByStatus(String status);
}
