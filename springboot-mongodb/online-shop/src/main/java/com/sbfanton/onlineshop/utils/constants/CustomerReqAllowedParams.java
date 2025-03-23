package com.sbfanton.onlineshop.utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerReqAllowedParams {

	NAME("name", "name", String.class),
	ADDRESS_CITY("addressCity", "address.city", String.class);
	
	private String paramName;
	private String paramRealName;
	private Class<?> paramClass;
}
