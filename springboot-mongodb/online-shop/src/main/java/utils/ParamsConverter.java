package utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ParamsConverter {

	public static Map<String, Object> convertReqParamsMapToGenericMap(Map<String, String> rawFilters) {
		Map<String, Object> filters = new HashMap<>();

	    for (Map.Entry<String, String> entry : rawFilters.entrySet()) {
	        String key = entry.getKey().replace('_', '.');
	        String value = entry.getValue();

	        Object convertedValue;
	        if (value.matches("\\d+")) {
	            convertedValue = Integer.parseInt(value); 
	        } else if (value.matches("\\d+\\.\\d+")) {
	            convertedValue = Double.parseDouble(value); 
	        } else {
	            convertedValue = value; 
	        }

	        filters.put(key, convertedValue);
	    }
	    
	    return filters;
	}
}
