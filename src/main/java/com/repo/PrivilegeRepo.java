package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Privilege;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

	Privilege findByPrivilegeName(String name);
	
}
