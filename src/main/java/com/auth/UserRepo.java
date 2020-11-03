package com.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Role;
import com.domain.User;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	List<User> findByRole(Role role);
	
	List<User> findByUsernameOrFirstNameOrLastName(String username, String firstName, String lastName);
	
}
