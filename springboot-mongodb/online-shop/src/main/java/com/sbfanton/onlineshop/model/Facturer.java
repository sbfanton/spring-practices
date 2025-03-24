package com.sbfanton.onlineshop.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "facturers")
public class Facturer {

	@Id
	private String id;
	
	private String name;
	
	private String country;
	
	private Integer founded;
	
	private List<Address> branches;
	
	private List<String> products;
}
