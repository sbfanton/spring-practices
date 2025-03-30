package com.sbfanton.onlineshop.utils.constants;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FacturerReqAllowedParams {

	NAME("name", "name", String.class),
	COUNTRY("country", "country", String.class),
	FOUNDED("founded", "founded", Integer.class),
	PRODUCT("product", "products", String.class),
	BRANCHES_CITY("city", "branches.city", List.class),;
	
	private String paramName;
	private String paramRealName;
	private Class<?> paramClass;
}
