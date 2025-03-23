package com.sbfanton.onlineshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sbfanton.onlineshop.model.Order;
import com.sbfanton.onlineshop.repository.custom.GeneralRepositoryCustom;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, GeneralRepositoryCustom {

}
