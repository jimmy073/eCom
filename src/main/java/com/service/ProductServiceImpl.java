package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.domain.Category;
import com.domain.Product;
import com.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepo productRepo;
	
	@Autowired
	public void setProductRepo(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public Product findProduct(Long id) {
		return productRepo.findById(id).orElse(null);
	}

	@Override
	public Product findProduct(String name) {
		return productRepo.findByProductName(name);
	}

	@Override
	public List<Product> products() {
		return productRepo.findAll();
	}

	@Override
	public List<Product> categoryProducts(Category category) {
		return productRepo.findByCategory(category);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);		
	}

	@Override
	public Page<Product> findProductPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.productRepo.findAll(pageable);
	}

	@Override
	public Page<Product> categoryProductsPaginated(Category category, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return productRepo.findByCategory(category, pageable);
	}

	@Override
	public Page<Product> findProductPaginated(String searchKey, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return productRepo.findByProductName(searchKey, pageable);
	}

		
	
}
