package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.auth.RoleService;
import com.auth.UserService;
import com.domain.Category;
import com.domain.Order;
import com.domain.Product;
import com.domain.Role;
import com.domain.User;
import com.service.CategoryService;
import com.service.OrderService;
import com.service.ProductService;

@Controller
@RequestMapping("/customer")
@SessionAttributes("cart")
public class CustomerController {

	private UserService userService;
	private RoleService roleService;
	private CategoryService categoryService;
	private ProductService productService;
	private OrderService orderService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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
//		Role role = new Role("ROLE_SUPER_ADMIN");
//		roleService.saveRole(role);
//		user.setRole(role);
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
	
	@GetMapping("/categories")
	public String categories(Model model) {
		
		Order cart = new Order();
		cart = orderService.createOrder(cart);
		model.addAttribute("cart", new Order());
		model.addAttribute("categories", categoryService.categories());
		return "home";
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
	
	@GetMapping("/registerCustomer")
	public String registerCustomer(Model model) {
		model.addAttribute("user",new User());
		return "registerCustomer";
	}
	
	@GetMapping("/productsPaged/{pageNo}")
	public String productsPaged(Model model, @PathVariable(value = "pageNo")int pageNo) {
		int pageSize = 6;
		Page<Product> prodsPage = productService.findProductPaginated(pageNo, pageSize);
		model.addAttribute("totalItems", prodsPage.getTotalElements());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", prodsPage.getTotalPages());
		model.addAttribute("products", prodsPage.getContent());
		return "allProducts";
	}
	
	@GetMapping("/allProducts")
	public String allProducts(Model model) {
		return productsPaged(model, 1);
	}
		
	
}
