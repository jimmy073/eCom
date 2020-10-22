package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Order;
import com.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepo orderRepo;
	
	@Autowired
	public void setOrderRepo(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

	@Override
	public List<Order> orders() {
		return orderRepo.findAll();
	}

	@Override
	public Order createOrder(Order order) {
		return orderRepo.save(order);
	}

	@Override
	public Order updateOrder(Order order) {
		return orderRepo.save(order);
	}

}
