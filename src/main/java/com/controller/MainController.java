package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auth.RoleService;
import com.auth.UserService;
import com.domain.Role;
import com.domain.User;

@Controller
public class MainController {

	private UserService userService;
	private RoleService roleService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}



	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/registerUser")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(Model model, User user) {
//		Role role = new Role ("ROLE_SUPER_ADMIN");
//		roleService.saveRole(role);
//		user.setRoles(Arrays.asList(role));
		userService.saveUser(user);
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@GetMapping("/registerRole")
	public String registerRole(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("roles", roleService.roles());
		return "roles";
	}
	
	@PostMapping("/saveRole")
	public String saveUser(Model model, Role role) {
		roleService.saveRole(role);
		model.addAttribute("role", new Role());
		model.addAttribute("roles", roleService.roles());
		return "roles";
	}
	
	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.users());
		return "users";
	}

	@GetMapping("/userRoles/{id}")
	public String userRoles(Model model, @PathVariable(value="id") long id) {
		User user = userService.findUser(id);
		model.addAttribute("roles", user.getRoles());
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.rolesAUserDoesntHave(user));
		return "userRoles";
	}
	
	@GetMapping("/addRole/{rid}/user/{uid}")
	public String addRole(Model model, @PathVariable(value = "rid") long rid, 
			@PathVariable(value = "uid") long uid) {
		User user = userService.findUser(uid);
		user = userService.addRole(uid, rid);
		model.addAttribute("roles", user.getRoles());
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.rolesAUserDoesntHave(user));
		return "userRoles";
	}
	
	
	@GetMapping("/removeRole/{rid}/user/{uid}")
	public String removeRole(Model model, @PathVariable(value = "rid") long rid, 
			@PathVariable(value = "uid") long uid) {
		User user = userService.findUser(uid);
		user = userService.removeRole(uid, rid);
		model.addAttribute("roles", user.getRoles());
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.rolesAUserDoesntHave(user));
		return "userRoles";
	}
	
	@RequestMapping("/deleteRole/{id}")
	public String deleteRole(Model model, @PathVariable(value = "id") long id) {
		Role role= roleService.findRole(id);
		roleService.deleteRole(role);
		model.addAttribute("role", new Role());
		model.addAttribute("roles", roleService.roles());
		return "roles";
	}
	
}
