/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wfw.constant.QualifierConstants;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.util.ValidationUtil;


/**
 * @author michelle.angela
 *
 */
@Component(QualifierConstants.TASK_FORM_VALIDATOR)
public class TaskFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TaskMaster.class.equals(clazz);
	}


	@Override
	public void validate(Object object, Errors errors) {

		// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "submitBy",
		// "Officer Id cannot be empty.");
		// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "appRefNo", "Ref
		// No. Id cannot be empty.");
		// if (CmnUtil.isObjNull(task.getSubmitBy()) &&
		// CmnUtil.isObjNull(task.getAppRefNo())) {
		// ValidationUtil.rejectIfEmptyOrWhitespace(errors, "submitBy",
		// "Officer ID and Application ID both cannot be blank.");
		// }

		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "appRefNo", "appRefNo Id cannot be empty.");
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "applicantId", "applicantId Id cannot be empty.");
		ValidationUtil.rejectIfEmptyOrWhitespace(errors, "applicant", "applicant Id cannot be empty.");
	}

}
