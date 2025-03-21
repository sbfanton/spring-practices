package utils.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.model.Product;

@Component
public class GlobalCache {
	
	private static final Map<String, Object> GLOBAL_MAP = new ConcurrentHashMap<>();

    static {
        GLOBAL_MAP.put(Customer.class.getName(), CustomerReqAllowedParams.values());
        GLOBAL_MAP.put(Product.class.getName(), ProductReqAllowedParams.values());
    }

    public static Object get(String key) {
        return GLOBAL_MAP.get(key);
    }

    public static Map<String, Object> getAll() {
        return GLOBAL_MAP;
    }
}
