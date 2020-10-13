package com.auth;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Privilege;
import com.domain.Role;
import com.service.PrivilegeService;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleRepo roleRepo;
	private PrivilegeService privilegeService;
	
	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	@Autowired
	public void setRoleRepo(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}
	
	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public Role findRole(Long id) {
		return roleRepo.findById(id).orElse(null);
	}

	@Override
	public Role findRole(String role) {
		return roleRepo.findByName(role);
	}

	@Override
	public List<Role> roles() {
		return roleRepo.findAll();
	}

	@Override
	public void deleteRole(Role role) {
		roleRepo.delete(role);		
	}

	@Override
	public Role addPrivilege(long rid, long pid) {
		Role role = findRole(rid);
		Privilege privilege = privilegeService.findPrivilege(pid);
		Set<Privilege> privs = role.getPrivileges();
		privs.add(privilege);
		role.setPrivileges(privs);
		roleRepo.save(role);
		return role;
	}

}
