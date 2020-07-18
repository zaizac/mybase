/**
 *
 */
package com.wfw.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;


/**
 * @author Mary Jane Buenaventura
 * @since 02/12//2014
 */
@Controller
public class StaticContentController extends AbstractController {

	@GetMapping(value = PageConstants.PAGE_URL_HOME)
	public ModelAndView landingPage() {
		return new ModelAndView("page_home");// [PageConstants.PAGE_HOME]
	}


	@GetMapping(value = "/forgotpassword")
	public String forgotPassword() {
		return PageConstants.PAGE_URL_HOME;
	}


	@GetMapping(value = "/about")
	public String about() {
		return PageConstants.PAGE_ABOUT;
	}


	@GetMapping(value = "/contact")
	public String contact() {
		return PageConstants.PAGE_CONTACT;
	}


	@GetMapping(value = PageConstants.PAGE_ERROR)
	public String timeout() {
		return PageConstants.PAGE_ERROR;
	}

}
