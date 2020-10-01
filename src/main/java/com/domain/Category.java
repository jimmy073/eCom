package com.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Category {

	@Id@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String categoryName;
	private String available;
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Category() {
		super();
	}

	/**
	 * @return the available
	 */
	public String getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available) {
		this.available = available;
	}

	
	
}
