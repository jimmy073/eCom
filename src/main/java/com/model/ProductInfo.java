package com.model;

import com.domain.Product;

public class ProductInfo {

	private Long code;
	private String name;
	private double price;
	
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public ProductInfo() {
		super();
	}
	
	public ProductInfo(Product product) {
		super();
		this.code = product.getId();
		this.name = product.getProductName();
		this.price = product.getPrice();
	}
	public ProductInfo(Long code, String name, double price) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
	}
	
}
