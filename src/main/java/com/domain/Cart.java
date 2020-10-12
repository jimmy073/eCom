package com.domain;

import java.util.Date;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cart {

	@Id@GeneratedValue
	private Long id;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdOn;
	@ManyToMany
	@JoinTable(
			name="cart_products",
			joinColumns = {@JoinColumn(name="cart_id")},
			inverseJoinColumns = {@JoinColumn(name="product_id")}
			)
	private Set<Product> products;
}
