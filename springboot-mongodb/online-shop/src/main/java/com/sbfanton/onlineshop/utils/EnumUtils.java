package com.sbfanton.onlineshop.utils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class EnumUtils {

	public static <T extends Enum<T>> Set<String> getEnumPropertyValues(Class<T> enumClass, String methodName) {
        Set<String> values = new HashSet<>();
        try {
            Method method = enumClass.getMethod(methodName); // Obtiene el método reflexivamente
            for (T enumConstant : enumClass.getEnumConstants()) {
                values.add((String) method.invoke(enumConstant)); // Llama al método en cada valor del enum
            }
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo valores del enum: " + e.getMessage(), e);
        }
        return values;
    }
}
