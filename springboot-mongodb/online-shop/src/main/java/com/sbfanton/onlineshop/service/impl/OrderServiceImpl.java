package com.sbfanton.onlineshop.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Triple;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import com.sbfanton.onlineshop.model.Order;
import com.sbfanton.onlineshop.model.dto.OrderDTO;
import com.sbfanton.onlineshop.model.dto.OrderDTOExtended;
import com.sbfanton.onlineshop.model.mappers.OrderMapper;
import com.sbfanton.onlineshop.repository.OrderRepository;
import com.sbfanton.onlineshop.service.OrderService;
import com.sbfanton.onlineshop.utils.EnumUtils;
import com.sbfanton.onlineshop.utils.ParamsConverter;
import com.sbfanton.onlineshop.utils.ParamsValidator;
import com.sbfanton.onlineshop.utils.constants.CollectionsNames;
import com.sbfanton.onlineshop.utils.constants.OrderListTypeParamProps;
import com.sbfanton.onlineshop.utils.constants.OrderReqAllowedParams;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

	public OrderDTOExtended getOrderDTOById(String id) throws Exception {
        Document match = orderRepository.getDocumentMatchById(id);
        List<AggregationOperation> aggOpList = orderRepository.generateOrderAggregationList(match);
    	return (OrderDTOExtended) orderRepository
    			.getCustomModelByIdWithAgg(aggOpList, Order.class, OrderDTOExtended.class)
    	        .orElseThrow(() -> new Exception("Order not found"));
	}


    public OrderDTO createOrder(OrderDTO order) throws Exception {
        Order ord = orderRepository.save(OrderMapper.toEntity(order));
        return OrderMapper.toDTO(ord);
    }

    public OrderDTO updateOrder(String id, OrderDTO updatedOrder) throws Exception {
        Order ord = orderRepository.findById(id)
            .map(order -> {
                order.setCustomerId(updatedOrder.getCustomerId());
                order.setItems(updatedOrder.getItems());
                order.setStatus(updatedOrder.getStatus());
                return orderRepository.save(order);
            }).orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toDTO(ord);
    }

    public void deleteOrderById(String id) {
        orderRepository.deleteById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<OrderDTOExtended> getOrderDTOList(Map<String, String> filters) throws Exception {
		
		ParamsValidator.validateParams(
				filters, 
				EnumUtils.getEnumPropertyValues(OrderReqAllowedParams.class, "getParamName"));
		
		List<Triple<String, Class<?>, Object>> convertedFilters = 
				ParamsConverter.convertReqParamsMapToGenericMap(
						filters, 
						Order.class.getName(),
						OrderListTypeParamProps.listAttrsTypesMap);

		Document matchStage = orderRepository.getDocumentsFilteredMatch(
						convertedFilters, 
						Order.class, 
						CollectionsNames.ORDERS.getName());
		
		List<AggregationOperation> aggOpList = orderRepository.generateOrderAggregationList(matchStage);
		
		return (List<OrderDTOExtended>) orderRepository.
				getCustomModelListWithAgg(
						aggOpList, 
						Order.class,
						OrderDTOExtended.class);
	}

}
