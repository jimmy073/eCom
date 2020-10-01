package com.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.auth.RoleService;
import com.auth.UserService;
import com.domain.Category;
import com.domain.Product;
import com.domain.Role;
import com.domain.User;
import com.service.CategoryService;
import com.service.ProductService;

@Controller
public class CustomerController {

	private UserService userService;
	private RoleService roleService;
	private CategoryService categoryService;
	private ProductService productService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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

	@PostMapping("/saveCustomer")
	public String saveCustomer(Model model,@Valid User user, BindingResult result ) {
		Role role = roleService.findRole("ROLE_CUSTOMER");
		Set<Role> rs = new HashSet<>();
		rs.add(role);
		user.setRoles(rs);
		userService.saveUser(user);
		model.addAttribute("user", new User());
		return "registerCustomer";
	}
	
	@GetMapping("/viewProducts/{id}")
	public String viewProducts(Model model, @PathVariable(value = "id") long id) {
		return prodPaginated(model, 1, id);
	}
	
	@GetMapping("/moreInfo/{id}")
	public String moreInfo(Model model, @PathVariable(value = "id") long id) {
		Product product = productService.findProduct(id);
		model.addAttribute("product", product);
		return "product";
	}
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(Model model, @PathVariable(value = "id") long id) {
		Product product = productService.findProduct(id);
		model.addAttribute("product", product);
		return "product";
	}
	
	@GetMapping("/viewProduct/{id}")
	public String viewProduct(Model model, @PathVariable(value = "id") long id) {
		Product product = productService.findProduct(id);
		model.addAttribute("product", product);
		return "product";
	}
	
	@GetMapping("/paged/{id}/{pageNo}")
	public String prodPaginated(Model model, @PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "id") long id) {
		int pageSize = 5;
		Category category = categoryService.findCategory(id);
		Page<Product> prodPage = productService.categoryProductsPaginated(category, pageNo, pageSize);
		model.addAttribute("category", category);
		model.addAttribute("products", prodPage.getContent());
		model.addAttribute("totalItems", prodPage.getTotalElements());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", prodPage.getTotalPages());
		return "viewProducts";
	}
	
	
}
