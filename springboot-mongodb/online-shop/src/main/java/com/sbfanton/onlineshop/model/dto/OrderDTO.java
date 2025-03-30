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
public class OrderDTO {
    private String id;
    private OrderCustomerDTO customer;
    private List<OrderItemDTO> items;
    private String status;
}

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OrderCustomerDTO {
    private String id;
    private String name;
}

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OrderItemDTO {
	private String prodId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
