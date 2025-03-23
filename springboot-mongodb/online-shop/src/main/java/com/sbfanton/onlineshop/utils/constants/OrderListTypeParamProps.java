package com.sbfanton.onlineshop.utils.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListTypeParamProps {

	public static List<String> orderListsNames;
	public static Map<String, Class<?>> listAttrsTypesMap;
	
	static {
		orderListsNames = new ArrayList<>();
		orderListsNames.add("items");
		
		listAttrsTypesMap = new HashMap<>();
		listAttrsTypesMap.put("items.productId", String.class);
		listAttrsTypesMap.put("items.quantity", Integer.class);
	}
}
