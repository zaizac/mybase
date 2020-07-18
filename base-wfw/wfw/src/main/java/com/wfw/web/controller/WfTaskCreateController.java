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
import com.util.DateUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.SearchForm;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.InboxMstr;
import com.wfw.util.BeanUtil;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_CREATE)
public class WfTaskCreateController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfTaskCreateController.class);


	@GetMapping
	public ModelAndView view(@RequestParam("taskId") String taskId, BindingResult result) {
		WfwInboxMstr twfWfInboxMstr;
		if (!BaseUtil.isEquals(CmnConstants.EMPTY_STRING, BaseUtil.getStr(taskId))) {
			twfWfInboxMstr = getCommonService().findWfInboxMstrByRefNo(taskId);
			twfWfInboxMstr.setTaskId(null);
		} else {
			twfWfInboxMstr = new WfwInboxMstr();
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_OBJ, twfWfInboxMstr);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_CREATE);
		return mav;
	}


	@PostMapping(params = "createtask")
	public ModelAndView createtask(InboxMstr inboxMstr, @ModelAttribute("searchForm") SearchForm searchForm,
			BindingResult result) {
		LOGGER.info("twfWfInboxMstr = {}", inboxMstr.getRefNo());

		WfwInboxMstr twfWfInboxMstr = new WfwInboxMstr();
		BeanUtil.copyProperties(inboxMstr, twfWfInboxMstr);
		twfWfInboxMstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
		twfWfInboxMstr.setCreateId(SessionData.getCurrentUserId());
		twfWfInboxMstr.setCreateDt(DateUtil.getSQLTimestamp());
		twfWfInboxMstr.setUpdateDt(DateUtil.getSQLTimestamp());
		twfWfInboxMstr.setUpdateId(BaseUtil.getStr(SessionData.getCurrentUserId()));

		WfwInboxHist twfWfInboxHist = new WfwInboxHist();
		BeanUtil.copyProperties(twfWfInboxMstr, twfWfInboxHist);

		boolean isCreated = getCommonService().crudWfTask(twfWfInboxMstr, null, twfWfInboxHist, "101",
				SessionData.getCurrentUserId());
		LOGGER.info("isCreated = {}", isCreated);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);

		return mav;

	}


	@GetMapping(params = "reset")
	public ModelAndView reset() {
		LOGGER.info("testing .......... reset");
		List<WfwInboxMstr> twfWfInboxMstr = getCommonService().findAllWfInboxMstr();

		LOGGER.info("inboxList size = {}", twfWfInboxMstr.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, twfWfInboxMstr);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}


	@PostMapping(params = "cancel")
	public ModelAndView cancel(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}
}