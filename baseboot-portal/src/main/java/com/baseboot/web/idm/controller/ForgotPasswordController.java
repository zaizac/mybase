/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baseboot.web.constants.AppConstants;
import com.baseboot.web.constants.MessageConstants;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.PageTemplate;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.util.WebUtil;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.ForgotPassword;
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

	@Autowired
	@Qualifier("forgotPasswordValidator")
	private Validator validator;


	@Override
	protected void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(this.validator);
		super.bindingPreparation(binder);
	}


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView forgotPassword(ForgotPassword forgotPassword, BindingResult result) {
		ModelAndView mav = new ModelAndView(PageTemplate.IDM_FORGOT_PASSWORD);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.pword.frgt"));
		return mav;
	}


	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView forgotPword(@Validated ForgotPassword forgotPassword, BindingResult result) {
		ModelAndView mav = new ModelAndView(PageTemplate.IDM_FORGOT_PASSWORD);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.pword.frgt"));
		if (!BaseUtil.isObjNull(forgotPassword) && !result.hasErrors()) {
			try {
				getIdmService().forgotPassword(forgotPassword);
			} catch (IdmException e) {
				if (WebUtil.checkSystemDown(e))
					throw e;
				String errorCode = ((IdmException) e).getInternalErrorCode();
				LOGGER.error("errorCode Response: {} - {}", errorCode, e.getMessage());
				mav.addAllObjects(PopupBox.error(errorCode, e.getMessage()));
				return mav;
			} catch (Exception e) {
				String errorCode = null;
				if (e instanceof IdmException) {
					errorCode = ((IdmException) e).getInternalErrorCode();
				}
				LOGGER.error("IDM Response Error: {} - {}", errorCode, e.getMessage());
				mav.addAllObjects(PopupBox.error(errorCode, e.getMessage()));
				return mav;
			}
			mav.addAllObjects(PopupBox.success(null, null,
					messageService.getMessage(MessageConstants.SUCC_FORGOT_PWORD), PageConstants.PAGE_LOGIN));
		}

		return mav;
	}

}
