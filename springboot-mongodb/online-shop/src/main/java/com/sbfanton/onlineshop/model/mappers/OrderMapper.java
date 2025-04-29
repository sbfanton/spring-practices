package com.sbfanton.onlineshop.model.mappers;

import com.sbfanton.onlineshop.model.Order;
import com.sbfanton.onlineshop.model.dto.OrderDTO;

public class OrderMapper {

	public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
        		.id(order.getId())
        		.customerId(order.getCustomerId())
        		.status(order.getStatus())
        		.items(order.getItems())
        		.build();
    }
	
	public static Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        return Order.builder()
        		.id(orderDTO.getId())
        		.customerId(orderDTO.getCustomerId())
        		.status(orderDTO.getStatus())
        		.items(orderDTO.getItems())
        		.build();
    }
}
