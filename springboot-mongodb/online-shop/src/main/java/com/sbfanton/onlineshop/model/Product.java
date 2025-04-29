package com.sbfanton.onlineshop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products") 
//@JsonInclude(JsonInclude.Include.NON_NULL) //para no incluir campos con valores null
public class Product {

    @Id
    private String id; 
    
    private String name;
    
    private String description;
    
    private Double price;
    
    private String facturerId;
    
    private List<String> features;
    
    private List<String> compatibility;
    
    private List<String> includedItems;
}

