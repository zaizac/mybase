/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.portal.constants.MessageConstants;
import com.portal.core.AbstractController;
import com.portal.web.util.ValidationUtil;


/**
 * @author Zairidha Atiqah
 * @since March 26, 2020
 */
@Component("inquiryPageValidator")
public class InquiryPageValidator extends AbstractController implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}


	@Override
	public void validate(Object object, Errors errors) {

	// Validate if fields are empty or a whitespace
	ValidationUtil.rejectIfEmptyOrWhitespace(errors, "name", MessageConstants.ERROR_FIELDS_NAME);
	ValidationUtil.rejectIfEmptyOrWhitespace(errors, "nationalId", MessageConstants.ERROR_FIELDS_IDENTIFICATION);
	
	}

}