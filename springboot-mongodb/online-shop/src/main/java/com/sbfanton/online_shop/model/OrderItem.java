package com.sbfanton.online_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderItem {

	private Integer productId;
	private String name;
    private Integer quantity;
    private Integer price;
}
