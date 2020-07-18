package com.bff.idm.validator;


import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_CURRPASS;
import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_NEWPASS;
import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_PWORD_MATCH;
import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_PWORD_NOT_MATCH;
import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_RNEWPASS;
import static com.bff.util.constants.MessageConstants.ERROR_FIELDS_USERID;
import static com.bff.util.constants.MessageConstants.ERROR_LENGTH_NEWPASS;
import static com.bff.util.constants.MessageConstants.ERROR_LENGTH_RNEWPASS;
import static com.bff.util.constants.MessageConstants.USERNAME_REQ;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bff.util.ValidationUtil;
import com.idm.sdk.model.ChangePassword;


@Component("passwordValidator")
public class PasswordValidator implements Validator {

	private static final String CURRPWORD = "currPword";

	private static final String NEWPWORD = "newPword";

	private static final String REPNEWPWORD = "repNewPword";


	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}


	@Override
	public void validate(Object object, Errors errors) {

		if (object instanceof ChangePassword) {

			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userId", ERROR_FIELDS_USERID);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, CURRPWORD, ERROR_FIELDS_CURRPASS);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, NEWPWORD, ERROR_FIELDS_NEWPASS);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, REPNEWPWORD, ERROR_FIELDS_RNEWPASS);

			if (!errors.hasErrors()) {
				ValidationUtil.rejectIfLengthIsInvalid(errors, NEWPWORD, ERROR_LENGTH_NEWPASS, 7, 100);
				ValidationUtil.rejectIfLengthIsInvalid(errors, REPNEWPWORD, ERROR_LENGTH_RNEWPASS, 7, 100);
			}

			// Validate password and repeat password if the same
			ValidationUtil.compareTwoStrings(errors, NEWPWORD, REPNEWPWORD, ERROR_FIELDS_PWORD_NOT_MATCH);

			// Validate current password and new password not the same
			ValidationUtil.rejectSameTwoStrings(errors, CURRPWORD, NEWPWORD, ERROR_FIELDS_PWORD_MATCH);

		} else {
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userName", USERNAME_REQ);
		}
	}

}
