package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.model.InboxMstr;
import com.wfw.util.BeanUtil;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_HIST_LIST)
public class WfTaskHistoryListController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfTaskHistoryListController.class);


	@GetMapping
	public ModelAndView view(@RequestParam("taskId") String taskId) {
		List<WfwInboxHist> historyList = getCommonService().findWfInboxHistByTaskId(taskId);
		WfwInboxMstr twfWfInboxMstr = getCommonService().findWfInboxMstrByRefNo(taskId);
		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_OBJ, twfWfInboxMstr);
		mav.addObject(PageConstants.DATA_LIST_OBJ, historyList);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_HIST_LIST);
		if (BaseUtil.isListNull(historyList)) {
			mav.addObject("msg", "No history record found.");
		} else {
			mav.addObject("msg", historyList.size() + " history record found.");
		}
		return mav;
	}


	@PostMapping
	public String newTask(InboxMstr inboxMstr, BindingResult result) {
		WfwInboxMstr twfWfInboxMstr = new WfwInboxMstr();
		BeanUtil.copyProperties(inboxMstr, twfWfInboxMstr);
		LOGGER.info("taskId = {}", twfWfInboxMstr.getTaskId());
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_DETAILS + "?taskId="
				+ BaseUtil.getStr(twfWfInboxMstr.getTaskId());
	}

}