/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.ForgotPassword;
import com.portal.constants.AppConstants;
import com.portal.constants.MessageConstants;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.core.AbstractController;
import com.portal.web.util.WebUtil;
import com.util.BaseUtil;
import com.util.PopupBox;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_IDM_FRGT_PWORD)
public class ForgotPasswordController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);

	private static final String JS_FILENAME = "forgot-password";

	private static final String MODULE = "";

	@Autowired
	@Qualifier("forgotPasswordValidator")
	private Validator validator;


	@Override
	protected void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		super.bindingPreparation(binder);
	}


	@GetMapping()
	public ModelAndView forgotPassword(ForgotPassword forgotPassword, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.IDM_FORGOT_PWORD, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.pword.frgt"));
		return mav;
	}


	@PostMapping()
	public ModelAndView forgotPword(@Validated ForgotPassword forgotPassword, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.IDM_FORGOT_PWORD, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.pword.frgt"));
		if (!BaseUtil.isObjNull(forgotPassword) && !result.hasErrors()) {
			try {
				getIdmService().forgotPassword(forgotPassword);
			} catch (IdmException e) {
				if (WebUtil.checkSystemDown(e)) {
					throw e;
				}
				String errorCode = e.getInternalErrorCode();
				LOGGER.error("errorCode Response: {} - {}", errorCode, e.getMessage());
				if (BaseUtil.isEqualsCaseIgnore(errorCode, IdmErrorCodeEnum.I400IDM122.name())) {
					mav.addAllObjects(
							PopupBox.error(errorCode, messageService.getMessage("msg.err.invld.username")));
				} else {
					mav.addAllObjects(PopupBox.error(errorCode, e.getMessage()));
				}

				return mav;
			} catch (Exception e) {
				LOGGER.error("IDM Response Error: {}", e.getMessage());
				mav.addAllObjects(PopupBox.error("GENERAL", e.getMessage()));
				return mav;
			}
			mav.addAllObjects(PopupBox.success(null, null,
					messageService.getMessage(MessageConstants.SUCC_FORGOT_PWORD), PageConstants.PAGE_LOGIN));
		}

		return mav;
	}

}
