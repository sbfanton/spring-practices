package com.sbfanton.onlineshop.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import com.sbfanton.onlineshop.utils.constants.GlobalCache;
import com.sbfanton.onlineshop.utils.constants.Separators;

@Component
public class ParamsConverter {

	public static List<Triple<String, Class<?>, Object>> convertReqParamsMapToGenericMap(
			Map<String, String> rawFilters, 
			String className,
			Map<String, Class<?>> listAttrClassMap) throws Exception {
		
		Enum<?>[] enumValues = (Enum<?>[]) GlobalCache.get(className);
		
		List<Triple<String, Class<?>, Object>> list = new ArrayList<>();
		List<Triple<String, Class<?>, Object>> finalList = new ArrayList<>();
		
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
                	if(paramClass == Long.class) 
                		paramValue = Long.parseLong((String) paramValue);
                	if(paramClass == Double.class)
                		paramValue = Double.parseDouble((String) paramValue);
                	if(paramClass == List.class)
                		paramValue += Separators.ITEM_VALUE_CLASS_SEPARATOR + listAttrClassMap.get(paramRealName).getName();
                	
                	Triple<String, Class<?>, Object> triple = Triple.of(
                			paramRealName, paramClass, paramValue);
                	list.add(triple);
                }
            }
            
            String listNames = "";
            String listValues = "";
            
            for(Triple<String, Class<?>, Object> item: list) {
            	if(item.getMiddle() == List.class) {
            		listNames += item.getLeft() + Separators.ITEM_SEPARATOR;
            		listValues += item.getRight() + Separators.ITEM_SEPARATOR;
            	}
            	else {
            		finalList.add(item);
            	}
            }
            
            if(listNames.length() > 0) {
            	finalList.add(Triple.of(listNames, List.class, listValues));
            }
            
        }

	    return finalList;
	}
	
	public static Object convertParamToType(Class<?> clazz, Object param) {
		if(clazz == Integer.class)
    		return Integer.parseInt((String) param);
		if(clazz == Long.class) 
    		return Long.parseLong((String) param);
    	if(clazz == Double.class)
    		return Double.parseDouble((String) param);
    	return param;
	}
}
