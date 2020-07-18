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
//@RequestMapping(value = PageConstants.PAGE_FAQ)
public class FaqAndContactController extends AbstractController {

	private static final String FAQ_MODULE = "faq";
	private static final String CONTACT_MODULE = "contact";

	
	@RequestMapping(value = PageConstants.PAGE_FAQ, method = RequestMethod.GET)
	public ModelAndView faq() {
		ModelAndView mav = getDefaultMav(PageTemplate.FAQ, FAQ_MODULE, null, null, null);
		return mav;
	}
	
	@RequestMapping(value = PageConstants.PAGE_CONTACT, method = RequestMethod.GET)
	public ModelAndView contact() {
		ModelAndView mav = getDefaultMav(PageTemplate.CONTACT, CONTACT_MODULE, null, null, null);
		return mav;
	}
}
