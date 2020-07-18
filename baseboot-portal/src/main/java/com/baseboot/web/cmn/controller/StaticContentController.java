/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baseboot.web.constants.AppConstants;
import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.PageTemplate;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.idm.form.ComponentsForm;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Controller
public class StaticContentController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticContentController.class);


	@RequestMapping(value = PageConstants.PAGE_SRC, method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value = "portal", required = false) String portal, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView(PageTemplate.TEMP_DASHBOARD);
		if (portal == null) {
			portal = messageService.getMessage("app.portal.type");
		}

		switch (portal) {
		case "wrkr":
			if (isUserAuthenticated()) {
				return new ModelAndView("redirect:" + PageConstants.PAGE_DASHBOARD);
			} else {
				mav.setViewName(PageTemplate.MAIN_PAGE);
			}
			break;
		default:
			portal = "def";
			if (isUserAuthenticated()) {
				return new ModelAndView("redirect:" + PageConstants.PAGE_DASHBOARD);
			}
			mav.setViewName(PageTemplate.MAIN_PAGE);
			break;
		}
		return mav;
	}


	@RequestMapping(value = PageConstants.PAGE_COMPONENTS, method = RequestMethod.GET)
	public ModelAndView components(ComponentsForm components) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_COMPONENTS, "components", null, null, "components");
		mav.addObject(AppConstants.PAGE_TITLE, "Components");
		if (components == null) {
			components = new ComponentsForm();
		}
		mav.addObject("components", components);
		mav.addObject("page", "components");
		return mav;
	}


	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
	public String favicon() {
		return "forward:/images/favicon.ico";
	}

}