package com.sbfanton.online_shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products") 
//@JsonInclude(JsonInclude.Include.NON_NULL) //para no incluir campos con valores null
public class Product {

    @Id
    private String id; 
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private ProductFacturer facturer;
    private List<String> features;
    private List<String> compatibility;
    private List<String> includedItems;
}

