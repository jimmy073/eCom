package com.form;

import org.springframework.web.multipart.MultipartFile;

import com.domain.Product;

public class ProductForm {

	private Long code;
	private String productName;
	private double price;
	
	private boolean newProduct = false;
	
	//Uploading a file
	private MultipartFile fileData;

	public ProductForm() {
		this.newProduct = true;
	}

	public ProductForm(Product product) {
		this.code = product.getId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
}
