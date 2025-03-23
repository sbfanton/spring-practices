package com.sbfanton.onlineshop.model.dto;

import java.util.List;

import com.sbfanton.onlineshop.model.Customer;
import com.sbfanton.onlineshop.model.Product;

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
public class OrderDTO {
    private String id;
    private Customer customer;
    //private List<OrderItemDTO> items;
    private String status;
}

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OrderItemDTO {
    private Product product;
    private int quantity;
}
