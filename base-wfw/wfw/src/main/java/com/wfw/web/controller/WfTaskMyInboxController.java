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
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_MY_INBOX)
public class WfTaskMyInboxController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfTaskMyInboxController.class);


	@GetMapping
	public ModelAndView view(SearchForm searchForm, BindingResult result) {
		List<WfwInboxMstr> mstrList = getCommonService().findInboxByOfficerIdModId(SessionData.getCurrentUserId(),
				"01");
		log.info("view: mstrList = {}", mstrList.size());
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, mstrList);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_MY_INBOX);
		return mav;
	}


	@PostMapping
	public ModelAndView search(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {

		ModelAndView mav = new ModelAndView();
		log.info("aplication id = {}", searchForm.getApplicationId());

		if (BaseUtil.isObjNull(searchForm.getApplicationId())) {
			searchForm.setMsg("No task found.");
		} else {
			List<WfwInboxMstr> inboxMstrList = getCommonService().findInboxByOfficerIdModIdRefNo(
					SessionData.getCurrentUserId(), "01", BaseUtil.getStr(searchForm.getApplicationId()));
			mav.addObject(PageConstants.DATA_LIST_OBJ, inboxMstrList);
			if (BaseUtil.isListNull(inboxMstrList)) {
				searchForm.setMsg("No task found.");
			} else {
				searchForm.setMsg(inboxMstrList.size() + " task(s) found.");
			}
		}
		mav.setViewName(PageConstants.PAGE_CONST_TASK_MY_INBOX);
		return mav;
	}


	@PostMapping(params = "reset")
	public ModelAndView reset(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {
		List<WfwInboxMstr> mstrList = getCommonService().findInboxByOfficerIdModId(SessionData.getCurrentUserId(),
				"01");
		log.info("reset: mstrList = {}", mstrList.size());
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, mstrList);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_MY_INBOX);
		return mav;
	}

}