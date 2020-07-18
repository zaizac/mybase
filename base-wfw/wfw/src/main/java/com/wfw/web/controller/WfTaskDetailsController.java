package com.wfw.web.controller;


import javax.servlet.http.HttpSession;

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
import com.wfw.model.WfwInboxMstr;
import com.wfw.model.WfwLevel;
import com.wfw.model.WfwStatus;
import com.wfw.model.WfwType;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_DETAILS)
public class WfTaskDetailsController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfTaskDetailsController.class);


	@GetMapping
	public ModelAndView view(@RequestParam("taskId") String taskId, HttpSession session) {
		log.info("taskId = {}", taskId);
		ModelAndView mav = new ModelAndView();
		WfwInboxMstr twfWfInboxMstr = getCommonService().findWfInboxMstrByRefNo(taskId);

		if (!BaseUtil.isObjNull(twfWfInboxMstr)) {
			WfwLevel wfLevel = getCommonService().findLevelByLevelId(twfWfInboxMstr.getLevelId());
			if (!BaseUtil.isObjNull(wfLevel)) {
				twfWfInboxMstr.setLevelDesc(wfLevel.getDescription());
				WfwType wfType = getCommonService().findWfTypeById(wfLevel.getTypeId());
				if (!BaseUtil.isObjNull(wfType)) {
					twfWfInboxMstr.setTypeDesc(wfType.getDescription());
				}
			}

			WfwStatus wfStatus = getCommonService().findStatusByStatusId(twfWfInboxMstr.getStatusId());
			if (!BaseUtil.isObjNull(wfStatus)) {
				twfWfInboxMstr.setStatusDesc(wfStatus.getDescription());
			}
		}

		mav.addObject(PageConstants.DATA_OBJ, twfWfInboxMstr);
		mav.addObject("readonly", true);
		mav.addObject("showBack", true);
		mav.addObject("showEdit", true);
		mav.addObject("showCopy", true);
		mav.addObject("showEscalate", true);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_DETAILS);
		return mav;
	}


	@PostMapping(params = "edit")
	public String edit(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult result) {

		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_DETAILS_EDIT + "?taskId="
				+ BaseUtil.getStr(searchForm.getTaskId());
	}


	@PostMapping(params = "cancel")
	public ModelAndView cancel(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}
}