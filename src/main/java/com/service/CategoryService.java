package com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.domain.Category;

@Service
public interface CategoryService {

	Category saveCategory(Category category);
	
	Category findCategory(Long id);
	
	Category findCategory(String name);
	
	List<Category> categories();
	
	Page<Category> findCategoriPaginated(int pageNo, int pageSize);
}
