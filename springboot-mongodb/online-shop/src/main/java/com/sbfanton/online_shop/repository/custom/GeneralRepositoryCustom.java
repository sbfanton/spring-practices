package com.sbfanton.online_shop.repository.custom;

import java.util.List;
import java.util.Map;

public interface GeneralRepositoryCustom {

	List<?> searchDocumentsFiltered(Map<String, Object> filters, Class<?> clazz);

	
}
