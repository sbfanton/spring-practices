package com.sbfanton.online_shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.model.Order;
import com.sbfanton.online_shop.repository.OrderRepository;
import com.sbfanton.online_shop.repository.custom.criteria.OrderCriteria;
import com.sbfanton.online_shop.service.OrderService;

import utils.EnumUtils;
import utils.ParamsConverter;
import utils.ParamsValidator;
import utils.constants.CollectionsNames;
import utils.constants.CustomerReqAllowedParams;
import utils.constants.OrderReqAllowedParams;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderCriteria orderCriteria;
	
	public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderByOrderId(String id) {
        return orderRepository.findOrderByOrderId(Integer.parseInt(id));
    }


    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order updatedOrder) {
        return orderRepository.findOrderByOrderId(Integer.parseInt(id))
            .map(order -> {
                order.setOrderId(updatedOrder.getOrderId());
                order.setCustomerId(updatedOrder.getCustomerId());
                order.setItems(updatedOrder.getItems());
                order.setTotal(updatedOrder.getTotal());
                order.setStatus(updatedOrder.getStatus());
                return orderRepository.save(order);
            }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrderByOrderId(String id) {
        orderRepository.deleteByorderId(Integer.parseInt(id));
    }
    
    @SuppressWarnings("unchecked")
	public List<Order> getOrdersFiltered(Map<String, String> filters) throws Exception {
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(OrderReqAllowedParams.class, "getParamName"));
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters, Order.class.getName());
		
		
		
		List<Order> orders = new ArrayList<>();
				/*(List<Order>) orderRepository
				.searchDocumentsFiltered(
						convertedFilters, 
						Order.class, 
						CollectionsNames.ORDERS.getName(), 
						orderCriteria);*/
		
		return orders;
	}

}
