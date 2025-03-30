package com.sbfanton.onlineshop.utils.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacturerListTypeParamProps {

	public static List<String> facturerListsNames;
	public static Map<String, Class<?>> listAttrsTypesMap;
	
	static {
		facturerListsNames = new ArrayList<>();
		facturerListsNames.add("branches");
		
		listAttrsTypesMap = new HashMap<>();
		listAttrsTypesMap.put("branches.city", String.class);
		listAttrsTypesMap.put("branches.street", String.class);
	}
}
