package com.sbfanton.onlineshop.model.dto;

import java.util.List;

import com.sbfanton.onlineshop.model.OrderItem;

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
public class OrderDTO {

	private String id;
    private String customerId;
    private List<OrderItem> items;
    private String status;
}
