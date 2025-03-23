package com.sbfanton.onlineshop.utils;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ParamsValidator {

	public static void validateParams(Map<String, String> filters, Set<String> allowedParams) throws Exception {
		for(String param: filters.keySet()) {
			if(!allowedParams.contains(param)) 
				throw new Exception("Parámetro no permitido " + param);
		}
	}
}
