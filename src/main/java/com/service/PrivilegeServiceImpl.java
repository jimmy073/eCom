package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Privilege;
import com.domain.Role;
import com.repo.PrivilegeRepo;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	private PrivilegeRepo privilegeRepo;
	
	@Autowired
	public void setPrivilegeRepo(PrivilegeRepo privilegeRepo) {
		this.privilegeRepo = privilegeRepo;
	}

	@Override
	public Privilege savePrivilege(Privilege privilege) {
		return privilegeRepo.save(privilege);
	}

	@Override
	public Privilege findPrivilege(Long id) {
		return privilegeRepo.findById(id).orElse(null);
	}

	@Override
	public Privilege findPrivilege(String name) {
		return privilegeRepo.findByPrivilegeName(name);
	}

	@Override
	public List<Privilege> privileges() {
		return privilegeRepo.findAll();
	}

	@Override
	public List<Privilege> privilegesARoleDoesntHave(Role role) {
		Set<Privilege> rolePrivileges = role.getPrivileges();
		List<Privilege> allPrivs = privileges();
		List<Privilege> privs = new ArrayList<>();
		for(Privilege p:allPrivs) {
			if(!rolePrivileges.contains(p)) {
				privs.add(p);
			}
		}
		return privs;
	}

}
