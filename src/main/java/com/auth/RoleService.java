package com.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Role;

@Service
public interface RoleService {

	Role saveRole(Role role);
	
	Role findRole(Long id);
	
	Role findRole(String role);
	
	void deleteRole(Role role);
	
	List<Role> roles();
	
	Role addPrivilege(long rid, long pid);
	
}
