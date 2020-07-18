/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dialect.iam.CustomUserDetails;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AuditAction;
import com.idm.sdk.model.ChangePassword;
import com.idm.sdk.model.UserProfile;
import com.portal.config.audit.AuditActionPolicy;
import com.portal.constants.AppConstants;
import com.portal.constants.MessageConstants;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.core.AbstractController;
import com.portal.web.util.WebUtil;
import com.util.BaseUtil;
import com.util.PopupBox;
import com.util.SSHA;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_IDM_USR_CHNG_PWORD)
public class ChangePasswordController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);

	private static final String JS_FILENAME = "change-password";

	private static final String MODULE = "";

	@Autowired
	@Qualifier("changePasswordValidator")
	private Validator validator;


	@Override
	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}


	@GetMapping()
	public ModelAndView view(ChangePassword changePassword, BindingResult result) {
		String profileType = "change_password";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CustomUserDetails cu = (CustomUserDetails) auth.getPrincipal();
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CHNG_PWORD, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.pword.chng"));
		if (cu.getProfile().getStatus().equals("F")) {
			profileType = "new_login";
		}
		mav.addObject("changePassword", new ChangePassword());
		mav.addObject(AppConstants.PORTAL_MODULE, profileType);
		return mav;
	}


	@PostMapping()
	public ModelAndView changePword(@Validated ChangePassword changePassword, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CHNG_PWORD, MODULE, null, null, JS_FILENAME);
		if (!BaseUtil.isObjNull(changePassword) && !result.hasErrors()) {
			UserProfile user = getCurrentUser();
			LOGGER.info("Change Password for Userid: {}", user.getUserId());
			changePassword.setUserId(user.getUserId());

			AuditAction audit = new AuditAction();
			audit.setPage(AuditActionPolicy.CHANGE_PWORD.page());
			audit.setAuditInfo(AuditActionPolicy.CHANGE_PWORD.action());
			try {
				if (BaseUtil.isEqualsCaseIgnore(user.getStatus(), "F")) {
					audit.setAuditInfo(AuditActionPolicy.CHANGE_PWORD_FT.action());
					changePassword.setRecoveryMethod("firstTime");
				}
				ChangePassword newPword = changePassword;
				newPword.setCurrPword(SSHA.getLDAPSSHAHash(changePassword.getCurrPword(), getPwordEkey()));
				newPword.setNewPword(SSHA.getLDAPSSHAHash(changePassword.getNewPword(), getPwordEkey()));
				newPword.setRepNewPword(null);
				getIdmService().changePassword(newPword);
				mav.addAllObjects(PopupBox.success(null, null,
						messageService.getMessage(MessageConstants.SUCC_CNG_PWORD), PageConstants.PAGE_LOGOUT));
			} catch (IdmException e) {
				String errorCode = e.getInternalErrorCode();
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error("IDM Response Error: {} - {}", errorCode, e.getMessage());
				mav.addAllObjects(PopupBox.error(errorCode, e.getMessage()));
				audit.setAuditInfo(audit.getAuditInfo() + " Failed.");
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
				audit.setAuditInfo(audit.getAuditInfo() + " Failed.");
			}
			mav.addObject(AppConstants.PORTAL_MODULE, "change_password");

			try {
				audit.setUserId(user.getUserId());
				getIdmService().createAuditAction(audit);
			} catch (Exception e) {
				LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
			}
		}
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.pword.chng"));
		return mav;

	}

}
