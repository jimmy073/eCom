package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="orders")
public class Order implements Serializable  {
	
	private static final long serialVersionUID = 26454998439748454L;

	@Id@GeneratedValue
	private Long id;
	private Date orderDate;
	private double amount;
	private String customerName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
