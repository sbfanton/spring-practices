package com.sbfanton.onlineshop.utils.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.model.Facturer;
import com.sbfanton.onlineshop.model.Product;
import com.sbfanton.onlineshop.model.Order;

@Component
public class GlobalCache {
	
	private static final Map<String, Object> GLOBAL_MAP = new ConcurrentHashMap<>();

    static {
        GLOBAL_MAP.put(Customer.class.getName(), CustomerReqAllowedParams.values());
        GLOBAL_MAP.put(Product.class.getName(), ProductReqAllowedParams.values());
        GLOBAL_MAP.put(Order.class.getName(), OrderReqAllowedParams.values());
        GLOBAL_MAP.put(Facturer.class.getName(), FacturerReqAllowedParams.values());
    }

    public static Object get(String key) {
        return GLOBAL_MAP.get(key);
    }

    public static Map<String, Object> getAll() {
        return GLOBAL_MAP;
    }
}
