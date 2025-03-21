package com.sbfanton.online_shop.repository.custom.impl;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sbfanton.online_shop.repository.custom.GeneralRepositoryCustom;

public class GeneralRepositoryCustomImpl implements GeneralRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
    public List<?> searchDocumentsFiltered(
    		List<Triple<String, Class<?>, Object>> filters, 
    		Class<?> entityClass, 
    		String collectionName,
    		Function<Query, Object> customCriteriaFunction) {
		
        Query query = new Query();

        for (Triple<String, Class<?>, Object> item : filters) {
            String attr = item.getLeft();
            Class<?> clazz = item.getMiddle();
            Object value = item.getRight();

            if (clazz == String.class) {
                query.addCriteria(Criteria.where(attr).regex((String)value.toString(), "i")); // Búsqueda con regex (ignora mayúsculas)
            } else if (clazz.isAssignableFrom(List.class)) {
            	customCriteriaFunction.apply(query);
            } else {
                query.addCriteria(Criteria.where(attr).is(value)); // Búsqueda exacta para otros tipos de datos
            }
        }

        return mongoTemplate.find(query, entityClass, collectionName);
    }

}
