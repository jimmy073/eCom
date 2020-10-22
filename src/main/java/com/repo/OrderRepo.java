package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
