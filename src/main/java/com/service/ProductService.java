package com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.domain.Category;
import com.domain.Product;

@Service
public interface ProductService {

	Product saveProduct(Product product);
	
	Product findProduct(Long id);
	
	Product findProduct(String name);
	
	List<Product> products();
	
	List<Product> categoryProducts(Category category);
	
	void deleteProduct(Long id);
	
	Page<Product> findProductPaginated(int pageNo, int pageSize);
	
	Page<Product> categoryProductsPaginated(Category category, int pageNo, int pageSize);
	
}
