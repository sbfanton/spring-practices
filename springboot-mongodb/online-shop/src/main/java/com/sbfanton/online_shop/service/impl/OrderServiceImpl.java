package com.sbfanton.online_shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Customer;
import com.sbfanton.online_shop.model.Order;
import com.sbfanton.online_shop.repository.OrderRepository;
import com.sbfanton.online_shop.service.OrderService;

import utils.EnumUtils;
import utils.ParamsConverter;
import utils.ParamsValidator;
import utils.constants.CustomerReqAllowedParams;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order updatedOrder) {
        return orderRepository.findById(id)
            .map(order -> {
                order.setOrderId(updatedOrder.getOrderId());
                order.setCustomerId(updatedOrder.getCustomerId());
                order.setItems(updatedOrder.getItems());
                order.setTotal(updatedOrder.getTotal());
                order.setStatus(updatedOrder.getStatus());
                return orderRepository.save(order);
            }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
    
    @SuppressWarnings("unchecked")
	public List<Order> getOrdersFiltered(Map<String, String> filters) throws Exception {
		/*ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(CustomerReqAllowedParams.class, "getParamName"));
		*/
		Map<String, Object> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(filters);
		List<Order> orders = (List<Order>) orderRepository
				.searchDocumentsFiltered(convertedFilters, Customer.class);
		
		return orders;
	}

}
