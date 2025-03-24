package com.sbfanton.onlineshop.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import com.sbfanton.onlineshop.repository.custom.CustomerRepositoryCustom;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

	@Override
	public List<AggregationOperation> generateCustomerAggregationList(Document initialMatchStage) {
		
		List<AggregationOperation> aggregationOperations = new ArrayList<>();
		
		// Filtro de busqueda inicial
		if(initialMatchStage != null)
			aggregationOperations.add(context -> initialMatchStage);
		
		return aggregationOperations;
	}
}
