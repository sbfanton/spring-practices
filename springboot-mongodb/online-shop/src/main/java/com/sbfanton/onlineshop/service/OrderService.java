package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;

import com.sbfanton.onlineshop.model.Order;
import com.sbfanton.onlineshop.model.dto.OrderDTO;

public interface OrderService {

	public List<Order> getAllOrders();
    public OrderDTO getOrderDTOById(String id) throws Exception;
    public Order createOrder(Order order);
    public Order updateOrder(String id, Order updatedOrder);
    public void deleteOrderById(String id);
    public List<OrderDTO> getOrderDTOList(Map<String, String> filters) throws Exception;
}
