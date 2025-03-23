package com.sbfanton.onlineshop.repository.custom;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

public interface GeneralRepositoryCustom {

	List<?> searchDocumentsFiltered(
			List<Triple<String, Class<?>, Object>> filters,
			Class<?> entityClass, 
			String collectionName) throws Exception;
	
	//public long getNextSequence(String seqName, String collectionName, String fieldName, Class<?> entityClass);
}
