package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Order;
import com.domain.OrderDetail;
import com.domain.User;
import com.model.CartInfo;

@Service
public interface OrderService {

	List<Order> orders();
	
	Order createOrder(Order order);
	
	Order createOrder(CartInfo cartInfo, User user);
	
	Order updateOrder(Order order);
	
	Order findOrder(Long id);
	
	List<Order> OrderByStatus(String status);
	
	List<Order> customerOutStandings(User user);
	
	List<OrderDetail> OrdersDetail(Order order);
}
