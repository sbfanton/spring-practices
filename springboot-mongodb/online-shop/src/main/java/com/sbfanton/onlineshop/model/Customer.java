package com.sbfanton.onlineshop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
//@JsonInclude(JsonInclude.Include.NON_NULL) //para no incluir campos con valores null
public class Customer {
    
	@Id
    private String id;
	
	private String nationalDocumentId;
	
    private String name;
    
    private String email;
    
    private String phoneNumber;
    
    private Address address;

}
