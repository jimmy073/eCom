package com.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.User;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
}
