package com.wfw.web.controller;


import java.util.List;

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
import com.wfw.sdk.model.InboxMstr;
import com.wfw.util.BeanUtil;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_DETAILS_EDIT)
public class WfTaskDetailsEditController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfTaskDetailsEditController.class);


	@GetMapping
	public ModelAndView view(@RequestParam("taskId") String taskId, HttpSession session) {
		LOGGER.info("taskId = {}", taskId);
		ModelAndView mav = new ModelAndView();
		WfwInboxMstr twfWfInboxMstr = getCommonService().findWfInboxMstrByRefNo(taskId);

		if (!BaseUtil.isObjNull(twfWfInboxMstr)) {
			WfwLevel wfLevel = getCommonService().findLevelByLevelId(twfWfInboxMstr.getLevelId());
			if (!BaseUtil.isObjNull(wfLevel)) {
				twfWfInboxMstr.setLevelDesc(wfLevel.getDescription());
				WfwType wfType = getCommonService().findWfTypeById(wfLevel.getTypeId());
				if (!BaseUtil.isObjNull(wfType)) {
					twfWfInboxMstr.setTypeDesc(wfType.getDescription());
					List<WfwLevel> levelList = getCommonService()
							.findWfLevelByTypeId(BaseUtil.getStr(wfType.getTypeId()));
					mav.addObject("levelList", levelList);
				}
			}

			WfwStatus wfStatus = getCommonService().findStatusByStatusId(twfWfInboxMstr.getStatusId());
			if (!BaseUtil.isObjNull(wfStatus)) {
				twfWfInboxMstr.setStatusDesc(wfStatus.getDescription());
			}
		}

		mav.addObject(PageConstants.DATA_OBJ, twfWfInboxMstr);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_DETAILS_EDIT);
		return mav;
	}


	@PostMapping(params = "update")
	public String update(@ModelAttribute("twfWfInboxMstr") InboxMstr inboxMstr, BindingResult result) {
		LOGGER.info("twfWfInboxMstr = {}", inboxMstr.getRefNo());
		WfwInboxMstr twfWfInboxMstr = new WfwInboxMstr();
		BeanUtil.copyProperties(inboxMstr, twfWfInboxMstr);
		WfwInboxMstr mstrData = getCommonService()
				.findWfInboxMstrByRefNo(BaseUtil.getStr(twfWfInboxMstr.getTaskId()));
		mstrData.setApplStsId(twfWfInboxMstr.getApplStsId());
		mstrData.setApplRemark(twfWfInboxMstr.getApplRemark());
		mstrData.setModuleId(twfWfInboxMstr.getModuleId());
		mstrData.setOfficerId(twfWfInboxMstr.getOfficerId());
		mstrData.setBranchId((twfWfInboxMstr.getBranchId()));
		mstrData.setLevelId((twfWfInboxMstr.getLevelId()));
		mstrData.setTranId(twfWfInboxMstr.getTranId());
		mstrData.setQueueInd(twfWfInboxMstr.getQueueInd());
		boolean isUdpated = getCommonService().updateWfInboxMstrByWfAdmin(mstrData, SessionData.getCurrentUserId());
		if (isUdpated) {
			return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_LIST;
		} else {
			return null;
		}
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
	public String cancel(@ModelAttribute("searchForm") SearchForm searchForm, BindingResult result) {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_DETAILS + "?taskId="
				+ BaseUtil.getStr(searchForm.getTaskId());
	}
}