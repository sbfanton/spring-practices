package com.sbfanton.online_shop.model;

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
@Document(collection = "orders") 
public class Order {

	@Id
    private String id;
    private int orderId;
    private String customerId;
    private List<Item> items;
    private int total;
    private String status;
}
