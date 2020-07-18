package com.wfw.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wfw.constant.QualifierConstants;
import com.wfw.form.SearchForm;
import com.wfw.util.ValidationUtil;

@Component(QualifierConstants.SEARCH_FORM_VALIDATOR)
public class SearchFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SearchForm.class.equals(clazz);
	}
 
	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "officerid", "Officer Id cannot be empty.");
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "appid", "Officer Id cannot be empty.");
		
	}

}
