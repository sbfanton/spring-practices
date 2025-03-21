package utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductReqAllowedParams {

	NAME("name", "name", String.class),
	DESCRIPTION("description", "description", String.class),
	PRICE("price", "price", Double.class),
	FACTURER_ID("facturerId", "facturer.id", Integer.class),
	FACTURER_NAME("facturerName", "facturer.name", String.class),
	FEATURES("features", "features", String.class),
	COMPATIBILITY("compatibility", "compatibility", String.class),
	INCLUDED_ITEMS("includedItems", "includedItems", String.class);
	
	private String paramName;
	private String paramRealName;
	private Class<?> paramClass;
}
