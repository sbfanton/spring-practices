package com.sbfanton.onlineshop.repository.custom;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

public interface ProductRepositoryCustom {

	List<AggregationOperation> generateProductAggregationList(Document initialMatchStage);
}
