package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwUser;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_USER_LIST)
public class WfUserListController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(WfUserListController.class);


	@GetMapping()
	public ModelAndView view() {
		List<WfwUser> userList = getCommonService().findAllWfUser();

		logger.info("userList size = {}", userList.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, userList);
		mav.setViewName(PageConstants.PAGE_CONST_USER_LIST);
		return mav;
	}
}