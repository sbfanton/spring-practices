package utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerReqAllowedParams {

	NAME("name", "name", String.class),
	AGE("age", "age", Integer.class),
	EMAIL("email", "email", String.class),
	PHONE_NUMBER("phone", "phoneNumber", String.class),
	ADDRESS_CITY("addressCity", "address.city", String.class),
	ADDRESS_NUMBER("addressNumber", "address.number", Integer.class),
	ADDRESS_STREET("addressStreet", "address.street", String.class);
	
	private String paramName;
	private String paramRealName;
	private Class<?> paramClass;
}
