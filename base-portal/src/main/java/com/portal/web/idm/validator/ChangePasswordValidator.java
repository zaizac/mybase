/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.validator;


import static com.portal.constants.MessageConstants.ERROR_FIELDS_CURRPASS;
import static com.portal.constants.MessageConstants.ERROR_FIELDS_NEWPASS;
import static com.portal.constants.MessageConstants.ERROR_FIELDS_PWORD_MATCH;
import static com.portal.constants.MessageConstants.ERROR_FIELDS_PWORD_NOT_MATCH;
import static com.portal.constants.MessageConstants.ERROR_FIELDS_RNEWPASS;
import static com.portal.constants.MessageConstants.ERROR_LENGTH_NEWPASS;
import static com.portal.constants.MessageConstants.ERROR_LENGTH_RNEWPASS;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.idm.sdk.model.ChangePassword;
import com.portal.web.util.ValidationUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Component("changePasswordValidator")
public class ChangePasswordValidator implements Validator {

	private static final String CURRPWORD = "currPword";

	private static final String NEWPWORD = "newPword";

	private static final String REPNEWPWORD = "repNewPword";


	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePassword.class.equals(clazz);
	}


	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, CURRPWORD, ERROR_FIELDS_CURRPASS);
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, NEWPWORD, ERROR_FIELDS_NEWPASS);
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, REPNEWPWORD, ERROR_FIELDS_RNEWPASS);

		if (!errors.hasErrors()) {
			ValidationUtil.rejectIfLengthIsInvalid(errors, NEWPWORD, ERROR_LENGTH_NEWPASS, 8, 20);
			ValidationUtil.rejectIfLengthIsInvalid(errors, REPNEWPWORD, ERROR_LENGTH_RNEWPASS, 8, 20);
		}

		// Validate password and repeat password if the same
		ValidationUtil.compareTwoStrings(errors, NEWPWORD, REPNEWPWORD, ERROR_FIELDS_PWORD_NOT_MATCH);

		// Validate current password and new password not the same
		ValidationUtil.rejectSameTwoStrings(errors, CURRPWORD, NEWPWORD, ERROR_FIELDS_PWORD_MATCH);
	}

}