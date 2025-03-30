package com.sbfanton.onlineshop.model.dto;

import java.util.List;

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
public class ProductDTO {
	private String id; 
    private String name;
    private String description;
    private Double price;
    private ProductFacturerDTO facturer;
    private List<String> features;
    private List<String> compatibility;
    private List<String> includedItems;
}

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ProductFacturerDTO {
	private String id;
	private String name;
}

