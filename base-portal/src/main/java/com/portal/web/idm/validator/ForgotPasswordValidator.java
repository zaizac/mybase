/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.validator;


import static com.portal.constants.MessageConstants.USERNAME_REQ;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.idm.sdk.model.ForgotPassword;
import com.portal.web.util.ValidationUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Component("forgotPasswordValidator")
public class ForgotPasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ForgotPassword.class.equals(clazz);
	}


	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userName", USERNAME_REQ);
	}

}