package com.sbfanton.onlineshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.onlineshop.model.Order;

public interface OrderService {

	public List<Order> getAllOrders();
    public Optional<Order> getOrderById(String id);
    public Order createOrder(Order order);
    public Order updateOrder(String id, Order updatedOrder);
    public void deleteOrderById(String id);
    public List<Order> getOrdersFiltered(Map<String, String> filters) throws Exception;
}
