package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.domain.Category;
import com.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepo categoryRepo;
	
	@Autowired
	public void setCategoryRepo(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	} 
	
	
	
	@Override
	public Category saveCategory(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public Category findCategory(Long id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Category findCategory(String name) {
		return categoryRepo.findByCategoryName(name);
	}

	@Override
	public List<Category> categories() {
		return categoryRepo.findAll();
	}



	@Override
	public Page<Category> findCategoriPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return categoryRepo.findAll(pageable);
	}

}
