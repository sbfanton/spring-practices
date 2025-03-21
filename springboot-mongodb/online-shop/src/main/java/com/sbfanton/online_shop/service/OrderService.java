package com.sbfanton.online_shop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sbfanton.online_shop.model.Order;

public interface OrderService {

	public List<Order> getAllOrders();
    public Optional<Order> getOrderByOrderId(String id);
    public Order createOrder(Order order);
    public Order updateOrder(String id, Order updatedOrder);
    public void deleteOrderByOrderId(String id);
    public List<Order> getOrdersFiltered(Map<String, String> filters) throws Exception;
}
