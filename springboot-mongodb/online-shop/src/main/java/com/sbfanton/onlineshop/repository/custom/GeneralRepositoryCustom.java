package com.sbfanton.onlineshop.repository.custom;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

public interface GeneralRepositoryCustom {

	Document getDocumentsFilteredMatch(
			List<Triple<String, Class<?>, Object>> filters,
			Class<?> entityClass, 
			String collectionName) throws Exception;
	
	Document getDocumentMatchById(String id) throws Exception;
	
	List<?> getCustomModelListWithAgg(
			List<AggregationOperation> aggregationOperations,
			Class<?> collectionClass,
			Class<?> customReturnClass) throws Exception;
	
	public Optional<?> getCustomModelByIdWithAgg(
			List<AggregationOperation> aggregationOperations,
			Class<?> collectionClass,
			Class<?> customReturnClass) throws Exception;
}
