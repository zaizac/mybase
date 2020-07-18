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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.SearchForm;
import com.wfw.model.WfwInbox;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_INBOX_EDIT)
public class WfInboxEditController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfInboxEditController.class);


	@GetMapping
	public ModelAndView edit(@RequestParam("taskId") String taskId) {
		LOGGER.info("taskId={}", taskId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, null);
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_EDIT);
		return mav;
	}


	@PostMapping
	public ModelAndView search(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {
		LOGGER.info("testing .......... post");

		LOGGER.info("officer id={}", searchForm.getOfficerId());
		LOGGER.info("aplication id={}", searchForm.getApplicationId());

		WfwInbox twfWfInbox = new WfwInbox();
		twfWfInbox.setRefNo(BaseUtil.getStr(searchForm.getApplicationId()));
		twfWfInbox.setOfficerId(BaseUtil.getStr(searchForm.getOfficerId()));
		List<WfwInbox> inboxList = getCommonService().searchWfInbox(twfWfInbox);
		LOGGER.info("inboxList size ={}", inboxList.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, inboxList);
		if (BaseUtil.isListNull(inboxList)) {
			mav.addObject("msg", "No result found.");
		} else {
			mav.addObject("msg", inboxList.size() + " result(s) found.");
		}
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_LIST);
		mav.addObject("error", "Officer ID and Application ID both cannot be blank.");
		return mav;
	}


	@GetMapping(params = "reset")
	public ModelAndView reset() {
		LOGGER.info("testing .......... reset");
		List<WfwInbox> inboxList = getCommonService().findAllWfInbox();

		LOGGER.info("inboxList size = {}", inboxList.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, inboxList);
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_EDIT);
		return mav;
	}
}