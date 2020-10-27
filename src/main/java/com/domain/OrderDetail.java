package com.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1040215535518774978L;

	@Id@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Order_Id", nullable = false)
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Product_Id", nullable = false)
	private Product product;
	
	@Column(nullable = false)
    private int quantity;
 
    @Column(nullable = false)
    private double price;
 
    @Column(nullable = false)
    private double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
    
}
