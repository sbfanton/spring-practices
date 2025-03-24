package com.sbfanton.onlineshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sbfanton.onlineshop.model.Order;
import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;
import com.sbfanton.onlineshop.repository.custom.OrderRepositoryCustom;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom, GeneralRepositoryCustom {

}
