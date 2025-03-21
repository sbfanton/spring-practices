package utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectionsNames {

	CUSTOMERS("customers"),
	PRODUCTS("products"),
	FACTURERS("facturers"),
	ORDERS("orders");
	
	private String name;
}
