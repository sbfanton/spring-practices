package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import utils.constants.GlobalCache;

@Component
public class ParamsConverter {

	public static List<Triple<String, Class<?>, Object>> convertReqParamsMapToGenericMap(Map<String, String> rawFilters, String className) throws Exception {
		
		Enum<?>[] enumValues = (Enum<?>[]) GlobalCache.get(className);
		
		List<Triple<String, Class<?>, Object>> list = new ArrayList<>();
		
		if (enumValues != null) {
			Method methodParamName = enumValues[0].getClass().getMethod("getParamName");
			Method methodParamRealName = enumValues[0].getClass().getMethod("getParamRealName");
            Method methodParamClass = enumValues[0].getClass().getMethod("getParamClass");
            
            for (Enum<?> value : enumValues) {
                String paramName = (String) methodParamName.invoke(value);
                String paramRealName = (String) methodParamRealName.invoke(value);
                Class<?> paramClass = (Class<?>) methodParamClass.invoke(value);
                
                Object paramValue = rawFilters.get(paramName);
                
                if(paramValue != null) {
                	
                	if(paramClass == Integer.class)
                		paramValue = Integer.parseInt((String) paramValue);
                	if(paramClass == Double.class)
                		paramValue = Double.parseDouble((String) paramValue);
                	
                	Triple<String, Class<?>, Object> triple = Triple.of(
                			paramRealName, paramClass, paramValue);
                	list.add(triple);
                }
            }
        }

	    return list;
	}
}
