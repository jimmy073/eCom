package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Privilege;
import com.domain.Role;

@Service
public interface PrivilegeService {

	Privilege savePrivilege(Privilege privilege);
	
	Privilege findPrivilege(Long id);
	
	Privilege findPrivilege(String name);
	
	List<Privilege> privileges();
	
	List<Privilege> privilegesARoleDoesntHave(Role role);
}
