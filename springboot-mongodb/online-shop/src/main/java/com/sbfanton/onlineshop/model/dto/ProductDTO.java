package com.sbfanton.onlineshop.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String id; 
    private String name;
    private String description;
    private Double price;
    private String facturerId;
    private List<String> features;
    private List<String> compatibility;
    private List<String> includedItems;
}
