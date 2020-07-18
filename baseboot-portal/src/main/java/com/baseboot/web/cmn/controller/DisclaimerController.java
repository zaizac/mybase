/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.PageTemplate;
import com.baseboot.web.core.AbstractController;


/**
 * @author Nigel Senin
 * @since 20180810
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_DISCLAIMER)
public class DisclaimerController extends AbstractController {

	private static final String TEMPLATE_MODULE = "disclaimer";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView mav = getDefaultMav(PageTemplate.DISCLAIMER, TEMPLATE_MODULE, null, null, null);
		return mav;
	}
}
