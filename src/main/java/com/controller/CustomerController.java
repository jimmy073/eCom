package com.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.auth.RoleService;
import com.auth.UserService;
import com.domain.Category;
import com.domain.Order;
import com.domain.Product;
import com.domain.Role;
import com.domain.User;
import com.form.CustomerForm;
import com.model.CartInfo;
import com.model.CustomerInfo;
import com.model.ProductInfo;
import com.service.CategoryService;
import com.service.OrderService;
import com.service.ProductService;
import com.utils.Utils;
import com.validator.CustomerFormValidator;

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
	private CustomerFormValidator customerFormValidator;
	
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

	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		
		if(target==null) {
			return;
		}
		System.out.println("Target "+ target.toString());
		
		//Case Update Qty in Cart
		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if(target.getClass()==CartInfo.class) {
			
		}
		
		// Case save customer information.
	    // (@ModelAttribute @Validated CustomerInfo customerForm)
		else if(target.getClass()==CustomerForm.class) {
			dataBinder.setValidator(customerFormValidator);
		}
	}
	
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(Model model,@Valid User user, BindingResult result, 
			HttpServletRequest request ) {
		Role role = roleService.findRole("ROLE_CUSTOMER");
		//roleService.saveRole(role);
		user.setRole(role);
		userService.saveUser(user);
		CartInfo cartInfo = Utils.getCartInSession(request);
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
		
	@GetMapping("/addToCart/{id}")
	public String addToCart(HttpServletRequest request,Model model, 
			@PathVariable(value = "id") long id) {
		Product product = productService.findProduct(id);
		
		if(product!=null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}
		
		model.addAttribute("product", product);
		return "redirect:/customer/shoppingCart";
	}
	
	@GetMapping("/shoppingCart")
	public String shoppingCart(HttpServletRequest request,Model model){
		CartInfo myCart = Utils.getCartInSession(request);
		model.addAttribute("cartForm", myCart);	
		return "shoppingCart";
	}
	
	@PostMapping("/shoppingCart")
	public String shoppingCartUpdateQty(HttpServletRequest request,Model model,
			@ModelAttribute("cartForm") CartInfo cartForm){
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantiy(cartForm);	
		return "redirect:/customer/shoppingCart";
	}
	
	@RequestMapping({"/shoppingCartRemoveProduct"})
	public String shoppingCartRemoveProduct(HttpServletRequest request, Model model, 
			@RequestParam(value = "pcode") long pcode) {
		Product product = productService.findProduct(pcode);
		
		if(product!=null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		
		return "redirect:/customer/shoppingCart";
	}
	
	@RequestMapping({"/checkout"})
	public String checkout(HttpServletRequest request, Model model) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			model.addAttribute("cartForm", cartInfo);
		return "redirect:/login";
	}
	
	@RequestMapping("/submitCart")
	public String submitCart(HttpServletRequest request, Model model, Principal p) {
		User user = activeUser(p);
		CartInfo cartInfo = Utils.getCartInSession(request);
		if(cartInfo==null) {
			return "redirect:/customer/choppingCart";
		}
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setEmail(user.getUsername());
		customerInfo.setName(user.getFirstName()+" "+user.getLastName());
		customerInfo.setAddress("Kigali");
		customerInfo.setPhone(user.getTelephone());;
		cartInfo.setCustomerInfo(customerInfo);
		orderService.createOrder(cartInfo,user);
		HttpSession session = request.getSession();
		session.invalidate();
		return "shoppingCart";
	}
	
	public User activeUser(Principal principal) {
		User activeUser =  userService.findUser(principal.getName());
		 return activeUser;
	}
	
	@RequestMapping("/orderDetail")
	public String viewOrderDetail(Model model, @RequestParam(value = "orderId") long orderId) {
		Order order = orderService.findOrder(orderId);
		model.addAttribute("details", orderService.OrdersDetail(order));
		return "orderDetail";
	}
	
	@RequestMapping("/cancelOrder")
	public String cancelOrder(Model model, @RequestParam(value = "orderId") long orderId) {
		Order order = orderService.findOrder(orderId);
		orderService.cancelOrder(order);
		model.addAttribute("details", orderService.OrdersDetail(order));
		return "orderDetail";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(value = "search", defaultValue = "") String search,
			Model model) {
		return "";
	}
}
