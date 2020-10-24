package com.validator;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.domain.Product;
import com.form.ProductForm;
import com.repo.ProductRepo;
import com.service.ProductService;

@Component
public class ProductFormValidator implements Validator {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductService productService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == ProductForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductForm productForm = (ProductForm) target;
		
		//checks fields of Product Form
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.ProductForm.code");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "NotEmpty.ProductForm.productName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.ProductForm.price");
	
		Long code = productForm.getCode();
		
		if(code!=null) {
			if(productForm.isNewProduct()) {
				Product product = productService.findProduct(code);
				if(product!=null) {
					errors.rejectValue("code", "Duplicate.productForm.code");
				}
			}
		}
	}
	
	
	
}
