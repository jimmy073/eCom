package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.OrderDetail;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {

}
