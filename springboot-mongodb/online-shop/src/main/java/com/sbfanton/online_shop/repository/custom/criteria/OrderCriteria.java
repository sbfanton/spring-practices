package com.sbfanton.online_shop.repository.custom.criteria;

import java.util.function.Function;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class OrderCriteria {

	public Function<Query, Object> createProductCriteriaFromListItem(String listName, String listObjAttr, String value) {
	    return (query) -> {
	        query.addCriteria(
	        		Criteria.where(listName)
	        				.elemMatch(Criteria.where(listObjAttr).is(value)));  
	        return null;  
	    };
	}
}
