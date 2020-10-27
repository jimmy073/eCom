package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Order;
import com.domain.User;

public interface OrderRepo extends JpaRepository<Order, Long> {
	List<Order> findByStatus(String status);
	
	List<Order> findByCustomer(User user);
}
