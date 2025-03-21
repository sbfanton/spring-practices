package com.sbfanton.online_shop.repository.custom;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.mongodb.core.query.Query;

public interface GeneralRepositoryCustom {

	List<?> searchDocumentsFiltered(
			List<Triple<String, Class<?>, Object>> filters,
			Class<?> entityClass, 
			String collectionName,
			Function<Query, Object> customCriteriaFunction);

	
}
