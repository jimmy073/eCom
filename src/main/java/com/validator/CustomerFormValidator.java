package com.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.form.CustomerForm;

@Component
public class CustomerFormValidator implements Validator{

	private EmailValidator emailValidator;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == CustomerForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerForm custInfo = (CustomerForm) target;
		
		//Checking fields of customer form
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.CustomerForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.CustomerForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.CustomerForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.CustomerForm.phone");
		
		if(!emailValidator.isValid(custInfo.getEmail())) {
			errors.rejectValue("email", "Pattern.CustomerForm.email");
		}
	}

}
