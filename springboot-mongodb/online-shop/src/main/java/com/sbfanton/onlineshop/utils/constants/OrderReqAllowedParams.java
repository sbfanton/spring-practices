package com.sbfanton.onlineshop.utils.constants;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderReqAllowedParams {

	CUSTOMER_ID("customerId", "customerId", String.class),
	STATUS("status", "status", String.class),
	ITEMS_PRODUCT_ID("productId", "items.productId", List.class),
	ITEMS_PRODUCT_QUANTITY("productQuantity", "items.quantity", List.class);
	
	private String paramName;
	private String paramRealName;
	private Class<?> paramClass;
}
