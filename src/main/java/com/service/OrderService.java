package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Order;

@Service
public interface OrderService {

	List<Order> orders();
	
	Order createOrder(Order order);
	
	Order updateOrder(Order order);
	
	
}
