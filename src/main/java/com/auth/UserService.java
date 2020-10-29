package com.auth;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.domain.Role;
import com.domain.User;

@Service
public interface UserService extends UserDetailsService {

	User saveUser(User user);
	
	User findUser(String username);
	
	User findUser(Long id);
	
	List<User> users();
	
	List<User> users(Role role);
	
	User addRole(long uid, long rid);
//	
//	User removeRole(long uid, long rid);
}
