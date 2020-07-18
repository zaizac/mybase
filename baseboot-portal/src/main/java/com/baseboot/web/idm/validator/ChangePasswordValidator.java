/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.validator;


import static com.baseboot.web.constants.MessageConstants.ERROR_FIELDS_CURRPASS;
import static com.baseboot.web.constants.MessageConstants.ERROR_FIELDS_NEWPASS;
import static com.baseboot.web.constants.MessageConstants.ERROR_FIELDS_PWORD_MATCH;
import static com.baseboot.web.constants.MessageConstants.ERROR_FIELDS_PWORD_NOT_MATCH;
import static com.baseboot.web.constants.MessageConstants.ERROR_FIELDS_RNEWPASS;
import static com.baseboot.web.constants.MessageConstants.ERROR_LENGTH_NEWPASS;
import static com.baseboot.web.constants.MessageConstants.ERROR_LENGTH_RNEWPASS;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baseboot.web.util.ValidationUtil;
import com.idm.sdk.model.ChangePassword;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Component("changePasswordValidator")
public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePassword.class.equals(clazz);
	}


	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "currPword", ERROR_FIELDS_CURRPASS);
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "newPword", ERROR_FIELDS_NEWPASS);
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "repNewPword", ERROR_FIELDS_RNEWPASS);

		if (!errors.hasErrors()) {
			ValidationUtil.rejectIfLengthIsInvalid(errors, "newPword", ERROR_LENGTH_NEWPASS, 7, 20);
			ValidationUtil.rejectIfLengthIsInvalid(errors, "repNewPword", ERROR_LENGTH_RNEWPASS, 7, 20);
		}

		// Validate password and repeat password if the same
		ValidationUtil.compareTwoStrings(errors, "newPword", "repNewPword", ERROR_FIELDS_PWORD_NOT_MATCH);

		// Validate current password and new password not the same
		ValidationUtil.rejectSameTwoStrings(errors, "currPword", "newPword", ERROR_FIELDS_PWORD_MATCH);
	}

}