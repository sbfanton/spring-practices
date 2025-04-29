package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;

import com.sbfanton.onlineshop.model.dto.OrderDTO;
import com.sbfanton.onlineshop.model.dto.OrderDTOExtended;

public interface OrderService {

	public OrderDTOExtended getOrderDTOById(String id) throws Exception;
    public OrderDTO createOrder(OrderDTO order) throws Exception;
    public OrderDTO updateOrder(String id, OrderDTO updatedOrder) throws Exception;
    public void deleteOrderById(String id);
    public List<OrderDTOExtended> getOrderDTOList(Map<String, String> filters) throws Exception;
}
