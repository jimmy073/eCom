package com.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.auth.RoleService;
import com.auth.UserService;
import com.domain.Category;
import com.domain.Privilege;
import com.domain.Product;
import com.domain.Role;
import com.domain.User;
import com.service.CategoryService;
import com.service.PrivilegeService;
import com.service.ProductService;

import net.bytebuddy.asm.Advice.Return;

@Controller
public class MainController {

	private UserService userService;
	private RoleService roleService;
	private CategoryService categoryService;
	private ProductService productService;
	private PrivilegeService privilegeService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}



	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping({"/","/home"})
	public String home(Model model) {
		model.addAttribute("categories", categoryService.categories());
		return "home";
	}
	
	@GetMapping("/adminHome")
	public String adminHome(Model model) {
		return registerRole(model);
	}
	
	@GetMapping("/customerHome")
	public String customerHome() {
		return "customerHome";
	}
	
	@GetMapping("/registerUser")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@Valid User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "registration";
		}
		userService.saveUser(user);
		model.addAttribute("user", new User());
		return "/registration";
	}
	
	@GetMapping("/registerRole")
	public String registerRole(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("privilege", new Privilege());
		model.addAttribute("privileges", privilegeService.privileges());
		model.addAttribute("roles", roleService.roles());
		return "roles";
	}
	
	@PostMapping("/saveRole")
	public String saveUser(Model model, Role role) {
		roleService.saveRole(role);
		return registerRole(model);
	}
	
	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.users());
		return "users";
	}

	@GetMapping("/userRoles/{id}")
	public String userRoles(Model model, @PathVariable(value="id") long id) {
		User user = userService.findUser(id);
		model.addAttribute("roles", user.getRole());
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.roles());
		return "userRoles";
	}
	
	@GetMapping("/addRole/{rid}/user/{uid}")
	public String addRole(Model model, @PathVariable(value = "rid") long rid, 
			@PathVariable(value = "uid") long uid) {
		User user = userService.findUser(uid);
		user = userService.addRole(uid, rid);
		model.addAttribute("roles", user.getRole());
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.roles());
		return "userRoles";
	}
//	
//	
//	@GetMapping("/removeRole/{rid}/user/{uid}")
//	public String removeRole(Model model, @PathVariable(value = "rid") long rid, 
//			@PathVariable(value = "uid") long uid) {
//		User user = userService.findUser(uid);
//		user = userService.removeRole(uid, rid);
//		model.addAttribute("roles", user.getRoles());
//		model.addAttribute("user", user);
//		model.addAttribute("allRoles", roleService.rolesAUserDoesntHave(user));
//		return "userRoles";
//	}
	
	@RequestMapping("/deleteRole/{id}")
	public String deleteRole(Model model, @PathVariable(value = "id") long id) {
		Role role= roleService.findRole(id);
		roleService.deleteRole(role);
		model.addAttribute("role", new Role());
		model.addAttribute("roles", roleService.roles());
		return "roles";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	
	@GetMapping("/registerCategory")
	public String registerCategory(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryService.categories());
		return "category";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(Model model, Category category) {
		categoryService.saveCategory(category);
		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryService.categories());
		return "category";
	}
	
	
	@GetMapping("/registerProducts/{id}")
	public String registerProducts(Model model, @PathVariable(value = "id") long id, 
			Product product) {
		Category category = categoryService.findCategory(id);
		model.addAttribute("category", category);
		model.addAttribute("product", new Product());
		model.addAttribute("products", productService.categoryProductsPaginated(category, 1, 10));
		return "categoryProducts";
	}
	
	@PostMapping("/saveProducts/{id}")
	public String saveProducts(Model model, @PathVariable(value = "id") long id, 
			Product product, @RequestParam(name = "foto") MultipartFile foto) throws Exception {
		uploadProduct(foto, product.getProductName());
		Category category = categoryService.findCategory(id);
		product.setImage(product.getProductName()+".jpg");
		product.setCategory(category);
		productService.saveProduct(product);
		model.addAttribute("category", category);
		model.addAttribute("product", new Product());
		model.addAttribute("products", productService.categoryProductsPaginated(category, 1, 10));
		return "categoryProducts";
	}
	
	@GetMapping("/editProduct/{pid}")
	public String editProducts(Model model, @PathVariable(value = "pid") long pid) {
		Product product = productService.findProduct(pid);
		Category category = product.getCategory();
		product.setCategory(category);
		productService.saveProduct(product);
		model.addAttribute("category", category);
		model.addAttribute("product", product);
		model.addAttribute("products", productService.categoryProducts(category));
		return "categoryProducts";
	}
	
	@GetMapping("/deleteProduct/{pid}")
	public String deleteProducts(Model model, @PathVariable(value = "pid") long pid) {
		Product product = productService.findProduct(pid);
		Category category = product.getCategory();
		productService.deleteProduct(pid);
		model.addAttribute("category", category);
		model.addAttribute("product", new Product( ));
		model.addAttribute("products", productService.categoryProducts(category));
		return "categoryProducts";
	}
	
	@GetMapping("/editCategory/{id}")
	public String disableCategory(Model model, @PathVariable(value = "id") long id) {
		Category category = categoryService.findCategory(id);
		model.addAttribute("category", category);
		model.addAttribute("categories", categoryService.categories());
		return "category";
	}
	
	
	public String uploadProduct(MultipartFile foto, String prodName) throws Exception {
		Path currentPath = Paths.get(".");
		Path absolutePath = currentPath.toAbsolutePath();
		String folder = absolutePath+"/src/main/resources/static/img/product/";
		byte [] bytes = foto.getBytes();
		Path path = Paths.get(folder+prodName+".jpg");
		Files.write(path, bytes);
		return folder;
	}
	
	@GetMapping("/showPermissions/{id}")
	public String showPermissions(Model model, @PathVariable(value = "id") long id) {
		Role role = roleService.findRole(id);
		model.addAttribute("role", role);
		model.addAttribute("privileges", role.getPrivileges());
		model.addAttribute("allPrivileges", privilegeService.privilegesARoleDoesntHave(role));
		return "rolePermissions";
	}
	
	@PostMapping("/savePrivilege")
	public String savePrivilege(Model model, Privilege privilege) {
		privilegeService.savePrivilege(privilege);
		return registerRole(model);
	}
	
	@GetMapping("/addPrivilege/{rid}/{pid}")
	public String addPrivilege(Model model, @PathVariable(value = "rid") long rid,
			@PathVariable(value = "pid") long pid) {
		roleService.addPrivilege(rid, pid);
		Role role = roleService.findRole(rid);
		model.addAttribute("role", role);
		model.addAttribute("privileges", role.getPrivileges());
		model.addAttribute("allPrivileges", privilegeService.privilegesARoleDoesntHave(role));
		return "rolePermissions";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
			return "accessDenied";
	}
	
}
