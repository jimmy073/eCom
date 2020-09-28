package com.auth;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.domain.Role;
import com.domain.User;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;
	private RoleService roleService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User findUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public User findUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public List<User> users() {
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUser(username);
		
		if(user==null) {
			throw new RuntimeException("Invalid Username or Password Exception");
		}
		
		return new UserDetailsImpl(user);
		
	}

	@Override
	public User addRole(long uid, long rid) {
		User user = findUser(uid);
		Role role = roleService.findRole(rid);
		Set<Role> currentRoles = user.getRoles();
		currentRoles.add(role);
		user.setRoles(currentRoles);
		saveUser(user);
		return user;
	}

	@Override
	public User removeRole(long uid, long rid) {
		User user = findUser(uid);
		Role role = roleService.findRole(rid);
		Set<Role> currentRoles = user.getRoles();
		currentRoles.remove(role);
		user.setRoles(currentRoles);
		saveUser(user);
		return user;
	}



}
