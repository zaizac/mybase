/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;


/**
 * @author Mary Jane Buenaventura
 * @since 01/12/2014
 */
@Controller
public class LoginController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private String viewName;


	public String getViewName() {
		return viewName;
	}


	public void setViewName(String viewName) {
		this.viewName = viewName;
	}


	@GetMapping(value = PageConstants.PAGE_LOGIN_SUC)
	public ModelAndView successLogin() {
		logger.info("----------------------------LOGIN SUCCESS!!!");
		return new ModelAndView("page_home");
	}


	@GetMapping(value = PageConstants.PAGE_LOGIN_ERR)
	public ModelAndView invalidLogin(@RequestParam(value = "error", required = false) Boolean error) {
		logger.info("----------------------------INVALID LOGIN!!!");
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("error", "Invalid username and password!");
		return mav;
	}


	@PostMapping(value = PageConstants.PAGE_LOGOUT)
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("msg", "Logout successfully!");
		return mav;
	}

}