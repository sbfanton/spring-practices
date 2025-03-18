package utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReqParams {

	NAME("name"),
	AGE("age"),
	ADDRESS_CITY("address_city");
	
	private String paramName;
}
