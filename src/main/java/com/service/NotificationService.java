package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.domain.Order;
import com.domain.User;

@Service
public class NotificationService {

	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(User user, String title, Order order) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(sender);
		mail.setTo(user.getUsername());
		mail.setSubject(title);
		if(title.contains("cancel")) {
			mail.setText("You have Canceled your Order with Order Id "+order.getId());
		}else {
			mail.setText("You have Ordered Successfully with Order Id "+order.getId());
		}
		javaMailSender.send(mail);
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSender() {
		return sender;
	}
	
	
	
	
}
