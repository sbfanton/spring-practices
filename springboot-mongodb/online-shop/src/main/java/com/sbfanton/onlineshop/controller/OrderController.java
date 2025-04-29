package com.sbfanton.onlineshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbfanton.onlineshop.model.dto.OrderDTO;
import com.sbfanton.onlineshop.model.dto.OrderDTOExtended;
import com.sbfanton.onlineshop.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping
    public ResponseEntity<List<OrderDTOExtended>> getOrders (
    		@RequestParam Map<String, String> filters) throws Exception {
        
    	return ResponseEntity.ok(orderService.getOrderDTOList(filters));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<OrderDTOExtended> getOrderById(@PathVariable String id) throws Exception {
        OrderDTOExtended order = orderService.getOrderDTOById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO order) throws Exception {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String id, @RequestBody OrderDTO orderDetails) throws Exception {
        try {
            OrderDTO updatedOrder = orderService.updateOrder(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
