package com.sbfanton.online_shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
//@JsonInclude(JsonInclude.Include.NON_NULL) //para no incluir campos con valores null
public class Customer {
    
	@Id
    private String id;
	
	private String customerId;
	
    private String name;
    
    private Integer age;
    
    private String email;
    
    private String phoneNumber;
    
    private Address address;

}
