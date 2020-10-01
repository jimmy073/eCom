package com.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Category;
import com.domain.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Product findByProductName(String name);
	
	List<Product> findByCategory(Category category);
	
	Page<Product> findByCategory(Category category, Pageable pageable);
	
}
