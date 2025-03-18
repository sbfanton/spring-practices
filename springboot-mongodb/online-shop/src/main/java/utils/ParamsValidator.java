package utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import utils.constants.ReqParams;

@Component
public class ParamsValidator {

	public static void validateCustomersParams(Map<String, String> filters) throws Exception {
		
		Set<String> allowedParams = new HashSet<>();
		
		for(ReqParams param: ReqParams.values()) {
			allowedParams.add(param.getParamName());
		}
		
		for(String param: filters.keySet()) {
			if(!allowedParams.contains(param)) 
				throw new Exception("Parámetro no permitido " + param);
		}
	}
}
