package com.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Role;
import com.domain.User;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleRepo roleRepo;
	
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
	public List<Role> rolesAUserDoesntHave(User user) {
		Set<Role> userRoles = user.getRoles();
		List<Role> allRoles = roles();
		List<Role> result = new ArrayList<>();
		for(Role ar:allRoles) {
			if(!userRoles.contains(ar)) {
				result.add(ar);
			}
		}
		return result;
	}

}
