package com.sbfanton.online_shop.repository.custom.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.repository.custom.CustomerRepositoryCustom;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
    public List<Customer> searchCustomersFiltered(Map<String, Object> filters) {
        Query query = new Query();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                query.addCriteria(Criteria.where(key).regex(value.toString(), "i")); // Búsqueda con regex (ignora mayúsculas)
            } else {
                query.addCriteria(Criteria.where(key).is(value)); // Búsqueda exacta para otros tipos de datos
            }
        }

        return mongoTemplate.find(query, Customer.class);
    }

}
