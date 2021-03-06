package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Order;
import com.domain.OrderDetail;
import com.domain.Product;
import com.domain.User;
import com.model.CartInfo;
import com.model.CartLineInfo;
import com.repo.OrderDetailRepo;
import com.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepo orderRepo;
	private ProductService productServise;
	private OrderDetailRepo detailRepo;
	private NotificationService notificationService;
	
	@Autowired
	public void setDetailRepo(OrderDetailRepo detailRepo) {
		this.detailRepo = detailRepo;
	}

	@Autowired
	public void setProductServise(ProductService productServise) {
		this.productServise = productServise;
	}

	@Autowired
	public void setOrderRepo(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	@Autowired
	public void setNorificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public List<Order> orders() {
		return orderRepo.findAll();
	}

	@Override
	public Order createOrder(Order order) {
		return orderRepo.save(order);
	}

	@Override
	public Order updateOrder(Order order) {
		return orderRepo.save(order);
	}

	@Override
	@Transactional
	public Order createOrder(CartInfo cartInfo, User user) {
		Order order = new Order();
		if(user==null) {
			throw new RuntimeException("USER NOT FOUND");
		}else {
			order.setAmount(cartInfo.getAmountTotal());
			order.setCustomer(user);
			order.setOrderDate(new Date());
			order.setStatus("OutStanding");
			orderRepo.save(order);
			List<CartLineInfo> lines = cartInfo.getCartLines();
			for(CartLineInfo line:lines) {
				OrderDetail detail = new OrderDetail();
				detail.setAmount(line.getAmount());
				detail.setOrder(order);
				detail.setQuantity(line.getQuantity());
				detail.setPrice(line.getProductInfo().getPrice());
				
				Long pcode = line.getProductInfo().getCode();
				Product product = this.productServise.findProduct(pcode);
				
				detail.setProduct(product);
				
				detailRepo.save(detail);
				notificationService.sendNotification(user, "createOrder", order);
			}
		}
		return order;
	}

	@Override
	public List<Order> orderByStatus(String status) {
		return orderRepo.findByStatus(status);
	}

	@Override
	public List<Order> customerOrdersByStatus(User user, String status) {
		List<Order> outStandings = orderByStatus(status);
		List<Order> userOutStandings = new ArrayList<>();
		
		for(Order order:outStandings) {
			if(order.getCustomer().equals(user)) {
				userOutStandings.add(order);
			}
		}
		
		return userOutStandings;
	}

	@Override
	public Order findOrder(Long id) {
		return orderRepo.findById(id).orElse(null);
	}

	@Override
	public List<OrderDetail> OrdersDetail(Order order) {
		List<OrderDetail> allDetails = detailRepo.findAll();
		List<OrderDetail> orderDetails = new ArrayList<>();
		for(OrderDetail detail:allDetails) {
			if(detail.getOrder().equals(order)) {
				orderDetails.add(detail);
			}
		}
		return orderDetails;
	}

	@Override
	public void cancelOrder(Order order) {
		order.setStatus("Canceled");
		orderRepo.save(order);
		notificationService.sendNotification(order.getCustomer(), "cancel", order);
	}

	@Override
	public List<Order> customerOrders(User user) {
		List<Order> orders = orders();
		List<Order> userOrders = new ArrayList<>();
		if(user==null) {
			throw new RuntimeException("UserNotFound");
		}else {
			for(Order order:orders) {
				if(order.getCustomer().getId() == (user.getId())) {
					userOrders.add(order);
				}
			}
		}
		
		return userOrders;
	}


}
