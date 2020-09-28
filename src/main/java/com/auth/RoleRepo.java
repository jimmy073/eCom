package com.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String role);
	
}
