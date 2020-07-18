package com.bff.idm.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bff.idm.form.UpdateProfileForm;
import com.bff.util.ValidationUtil;
import com.bff.util.constants.MessageConstants;


@Component("UpdateProfileValidator")
public class UpdateProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateProfileForm.class.equals(clazz);
	}


	@Override
	public void validate(Object object, Errors errors) {

		if (object instanceof UpdateProfileForm) {
			// Validate if fields are empty or a whitespace
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "email", MessageConstants.ERROR_FIELDS_EMAIL);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "fullName", MessageConstants.ERROR_FIELDS_FULLNAME);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "gender", MessageConstants.ERROR_FIELDS_GENDER);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "nationality",
					MessageConstants.ERROR_FIELDS_NATIONALITY);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "contactNo", MessageConstants.ERROR_FIELDS_MOBILE);

		}
	}
}
