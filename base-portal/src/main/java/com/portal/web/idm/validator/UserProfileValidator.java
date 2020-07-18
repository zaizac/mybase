/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.validator;


import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.be.sdk.constants.IdmRoleConstants;
import com.portal.constants.MessageConstants;
import com.portal.core.AbstractController;
import com.portal.web.idm.form.IdmUploadProfilePictureForm;
import com.portal.web.util.ValidationUtil;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Component("userProfileValidator")
public class UserProfileValidator extends AbstractController implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}


	@Override
	public void validate(Object object, Errors errors) {
		IdmUploadProfilePictureForm up = (IdmUploadProfilePictureForm) object;
		System.out.println("VALIDATOR: " + up.getUserId());
		if (object instanceof IdmUploadProfilePictureForm) {

			// Validate if fields are empty or a whitespace
			// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "fullName",
			// MessageConstants.ERROR_FIELDS_FULLNAME);
			if (BaseUtil.isEqualsCaseIgnore("new", up.getAction())) {
				ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userId", MessageConstants.ERROR_FIELDS_USERID);
				ValidationUtil.rejectIfEmptyOrWhitespace(errors, "password",
						MessageConstants.ERROR_FIELDS_TEMPPASS);
			}
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "firstName", MessageConstants.ERROR_FIELDS_FIRSTNAME);
			ValidationUtil.rejectIfEmptyOrWhitespace(errors, "email", MessageConstants.ERROR_FIELDS_EMAIL);
			// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "mobPhoneNo",
			// MessageConstants.ERROR_FIELDS_MOBILE);
			// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "gender",
			// MessageConstants.ERROR_FIELDS_GENDER);
			// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "dob",
			// MessageConstants.ERROR_DOB);

			if (up.getDob() != null) {
				Date date = new Date();
				Date date1 = up.getDob();
				long diff = Math.abs(date.getTime() - date1.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);
				if (diffDays < 18) {
					ValidationUtil.rejectIfInvalidAgeLessFormat(errors, "dob", MessageConstants.ERROR_NOT_ADULT);
				}
			}

			if (!BaseUtil.isEqualsCaseIgnore("id", up.getUserId()) && hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) {
				ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userRoleGroupCode",
						MessageConstants.ERROR_USER_ROLE_GROUP);
			}

			// Validate email if format is valid
			if (!BaseUtil.isObjNull(up.getEmail())) {
				ValidationUtil.rejectIfInvalidEmailFormat(errors, "email", MessageConstants.ERROR_EMAIL_FORMAT);
			}

			if(!BaseUtil.isObjNull(up.getUserRoleGroupCode())) {
				// Check District Dropdown
				if(BaseUtil.isEqualsCaseIgnore("DIST_ADMIN", up.getUserRoleGroupCode())) {
					ValidationUtil.rejectIfEmptyOrWhitespace(errors, "userGroupRoleBranchCd", MessageConstants.ERROR_FIELDS_DISTRICT);
				}
				
				// Check State Dropdown
				if(BaseUtil.isEqualsCaseIgnore("STATE_ADMIN", up.getUserRoleGroupCode())) {
					ValidationUtil.rejectIfEmptyOrWhitespace(errors, "stateCd", MessageConstants.ERROR_FIELDS_STATE);
				}
			}
			
		}

		if (!errors.hasErrors()) {
			if (BaseUtil.isEqualsCaseIgnore("new", up.getUserId())) {
				ValidationUtil.rejectIfLengthIsInvalid(errors, "userId", MessageConstants.ERROR_LENGTH_NAME, 8);
			}
		}

	}

}