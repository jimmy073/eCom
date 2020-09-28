package com.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Role;
import com.domain.User;

@Service
public interface RoleService {

	Role saveRole(Role role);
	
	Role findRole(Long id);
	
	Role findRole(String role);
	
	void deleteRole(Role role);
	
	List<Role> roles();
	
	List<Role> rolesAUserDoesntHave(User user);
	
}
