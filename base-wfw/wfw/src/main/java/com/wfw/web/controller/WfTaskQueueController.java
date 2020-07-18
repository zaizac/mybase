package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.SearchForm;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_QUEUE)
public class WfTaskQueueController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfTaskQueueController.class);


	@GetMapping
	public ModelAndView view(SearchForm searchForm, BindingResult result) {
		List<String> userRoles = SessionData.getUserRoles();
		String userRole = CmnConstants.EMPTY_STRING;
		if (!BaseUtil.isListNull(userRoles)) {
			userRole = userRoles.get(0);
		}
		log.info("---------------------------- view: user role = {}", userRole);
		List<WfwInboxMstr> mstrList = getCommonService().findWfInboxMstrQueueByModRole(userRole, "01");
		log.info("mstrList = {}", mstrList.size());
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, mstrList);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_QUEUE);
		return mav;
	}


	@PostMapping
	public ModelAndView search(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {

		List<String> userRoles = SessionData.getUserRoles();
		String userRole = CmnConstants.EMPTY_STRING;
		if (!BaseUtil.isListNull(userRoles)) {
			userRole = userRoles.get(0);
		}
		log.info("---------------------------- search: user role = {}", userRole);

		ModelAndView mav = new ModelAndView();
		log.info("aplication id= {}", searchForm.getApplicationId());

		if (BaseUtil.isObjNull(searchForm.getApplicationId())) {
			searchForm.setMsg("No task found.");
		} else {
			List<WfwInboxMstr> inboxMstrList = getCommonService().findWfInboxMstrQueueByModRole(userRole, "EMB",
					BaseUtil.getStr(searchForm.getApplicationId()));
			mav.addObject(PageConstants.DATA_LIST_OBJ, inboxMstrList);
			if (BaseUtil.isListNull(inboxMstrList)) {
				searchForm.setMsg("No task found.");
			} else {
				searchForm.setMsg(inboxMstrList.size() + " task(s) found.");
			}
		}
		mav.setViewName(PageConstants.PAGE_CONST_TASK_QUEUE);
		return mav;
	}


	@PostMapping(params = "claim")
	public String newTask(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {

		log.info("=============== {}", searchForm.getCheckTaskId().size());

		for (String taskId : searchForm.getCheckTaskId()) {
			log.info("========taskId======= {}", taskId);
		}

		getCommonService().claimTask(searchForm.getCheckTaskId(), SessionData.getCurrentUserId(),
				SessionData.getCurrentUserFullName(), CmnConstants.EMPTY_STRING);
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_QUEUE;
	}


	@PostMapping(params = "reset")
	public ModelAndView reset(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {

		List<String> userRoles = SessionData.getUserRoles();
		String userRole = CmnConstants.EMPTY_STRING;
		if (!BaseUtil.isListNull(userRoles)) {
			userRole = userRoles.get(0);
		}
		log.info("---------------------------- reset: user role = {}", userRole);
		List<WfwInboxMstr> mstrList = getCommonService().findWfInboxMstrQueueByModRole(userRole, "01");
		log.info("mstrList = {}", mstrList.size());
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, mstrList);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_QUEUE);
		return mav;
	}

}