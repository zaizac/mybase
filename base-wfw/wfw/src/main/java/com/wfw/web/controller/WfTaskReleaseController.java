package com.wfw.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.InboxMstr;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_RELEASE)
public class WfTaskReleaseController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfTaskReleaseController.class);


	@GetMapping
	public ModelAndView view(@RequestParam("taskId") String taskId) {
		log.info("taskId = {}", taskId);
		WfwInboxMstr mstr = getCommonService().findWfInboxMstrByRefNo(taskId);
		log.info("Ref No = {}", mstr.getRefNo());
		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_OBJ, mstr);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_RELEASE);
		return mav;
	}


	@PostMapping
	public String submit(@ModelAttribute(value = PageConstants.DATA_OBJ) InboxMstr inboxMstr, BindingResult result) {
		log.info("wfStatus = {}", inboxMstr.getTaskId());
		boolean isReleased = getCommonService().releaseTask(BaseUtil.getStr(inboxMstr.getTaskId()),
				SessionData.getCurrentUserId(), CmnConstants.EMPTY_STRING);
		log.info("isReleased = {}", isReleased);
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_MY_INBOX;
	}


	@PostMapping(params = "cancel")
	public String cancel(@ModelAttribute(value = PageConstants.DATA_OBJ) InboxMstr inboxMstr, BindingResult result) {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_MY_INBOX;
	}

}