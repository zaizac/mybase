/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.bff.config.audit.AuditActionPolicy;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AuditAction;
import com.idm.sdk.model.ChangePassword;
import com.idm.sdk.model.ForgotPassword;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.MediaType;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PasswordController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	@Qualifier("passwordValidator")
	private Validator validator;


	@Override
	protected void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		super.bindingPreparation(binder);
	}


	@PostMapping(value = PageConstants.PAGE_IDM_FRGT_PWORD, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public void forgotPword(@Validated @RequestBody ForgotPassword forgotPassword, BindingResult result) {

		if (BaseUtil.isObjNull(forgotPassword) && result.hasErrors()) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			getIdmService().forgotPassword(forgotPassword);
		} catch (IdmException e) {
			String errorCode = e.getInternalErrorCode();
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error("errorCode Response: {} - {}", errorCode, e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("IDM Response Error: {}", e.getMessage());
			throw e;
		}
	}


	@PostMapping(value = PageConstants.PAGE_IDM_USR_CHNG_PWORD, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public void changePword(@Validated @RequestBody ChangePassword changePassword, BindingResult result) {

		if (BaseUtil.isObjNull(changePassword) || result.hasErrors()) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		UserProfile user = new UserProfile();
		AuditAction audit = new AuditAction();

		try {
			user = getIdmService().getUserProfileById(changePassword.getUserId(), false, false);
			LOGGER.info("Change Password for Userid: {}", user.getUserId());
			changePassword.setUserId(user.getUserId());

			audit.setAuditInfo(AuditActionPolicy.CHANGE_PWORD.action());

			if (BaseUtil.isEqualsCaseIgnore(user.getStatus(), "F")) {
				audit.setAuditInfo(AuditActionPolicy.CHANGE_PWORD_FT.action());
				changePassword.setRecoveryMethod("firstTime");
			}
			ChangePassword newPword = changePassword;
			newPword.setCurrPword(changePassword.getCurrPword());
			newPword.setNewPword(changePassword.getNewPword());
			newPword.setRepNewPword(null);
			getIdmService().changePassword(newPword);

		} catch (IdmException e) {
			String errorCode = e.getInternalErrorCode();
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			LOGGER.error("IDM Response Error: {} - {}", errorCode, e.getMessage());
			audit.setAuditInfo(audit.getAuditInfo() + " Failed.");
			throw e;
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			audit.setAuditInfo(audit.getAuditInfo() + " Failed.");
			throw e;
		}

		try {
			audit.setPage(AuditActionPolicy.CHANGE_PWORD.page());
			audit.setUserId(user.getUserId());
			getIdmService().createAuditAction(audit);
		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
			throw e;
		}
	}

}
