/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.validator;


import static com.baseboot.web.constants.MessageConstants.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baseboot.web.idm.form.IdmProfileForm;
import com.baseboot.web.util.ValidationUtil;


/**
 * @author Nur Suhada
 * @since June 11, 2018
 */
@Component("JobSeekerSignUpValidator")
public class JobSeekerSignUpValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return IdmProfileForm.class.equals(clazz);
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		
		if(object instanceof IdmProfileForm) {
			// Validate if fields are empty or a whitespace
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userId", ERROR_FIELDS_EMAIL);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "firstName", ERROR_FIELDS_FIRSTNAME);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "gender", ERROR_FIELDS_GENDER);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "lastName", ERROR_FIELDS_LAST_NAME);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "newPword", ERROR_FIELDS_NEWPASS);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "repNewPword", ERROR_FIELDS_RNEWPASS);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "nationality", ERROR_FIELDS_NATIONALITY);

		}
		// Validate email if format is valid
		ValidationUtil.rejectIfInvalidEmailFormat(errors, "userId", ERROR_EMAIL_FORMAT);
		

		if (!errors.hasErrors()) {
			ValidationUtil.rejectIfLengthIsInvalid(errors, "newPword", ERROR_LENGTH_NEWPASS, 7, 20);
			ValidationUtil.rejectIfLengthIsInvalid(errors, "repNewPword", ERROR_LENGTH_RNEWPASS, 7, 20);
		}

		// Validate password and repeat password if the same
		ValidationUtil.compareTwoStrings(errors, "newPword", "repNewPword", ERROR_FIELDS_PWORD_NOT_MATCH);

	}
}