package com.sbfanton.onlineshop.model.dto;

import java.util.List;

import com.sbfanton.onlineshop.model.Facturer;

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
    
    private Facturer facturer;
    
    private List<String> features;
    
    private List<String> compatibility;
    
    private List<String> includedItems;
}
