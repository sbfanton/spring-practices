package com.sbfanton.online_shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "products") 
public class Product {

    @Id
    private String id; 
    private String name;
    private String description;
    private int price;
    private String facturer;
    private List<String> features;
    private List<String> compatibility;
    private List<String> includedItems;
}

