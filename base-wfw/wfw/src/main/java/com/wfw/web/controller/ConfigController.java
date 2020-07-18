/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwRefType;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.PopupBox;
import com.wfw.util.SessionData;


/**
 * @author michelle.angela
 * @since 20 Sep 2019
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_URL_CONFIG)
public class ConfigController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(WfConfigController.class);

	private static final String ERROR_TEXT = "Error!";


	@GetMapping
	public ModelAndView view(@RequestParam(value = "typeId", required = false) Integer typeId) {

		ModelAndView mav = new ModelAndView();
		WfwReference dto = new WfwReference();
		if (BaseUtil.isObjNull(typeId)) {
			List<WfwRefType> list = getRefTypeList();
			if (!BaseUtil.isListNull(list)) {
				dto.setTypeId(list.get(0).getTypeId());
			}
		} else {
			dto.setTypeId(typeId);
		}

		logger.info("typeId = {}", dto.getTypeId());
		dto.setWithLevel(true);
		dto.setWithStatus(true);
		mav.addObject(PageConstants.REFERENCE, getCmmnService().getRefListDetails(dto, null));
		mav.setViewName(PageConstants.PAGE_URL_CONFIG);
		return mav;
	}


	@GetMapping(value = "type")
	public ModelAndView getType(@RequestParam(value = "typeId", required = false) Integer typeId) {

		logger.info("typeId = {}", typeId);

		ModelAndView mav = new ModelAndView();
		WfwReference dto = new WfwReference();

		if (CmnUtil.isObjNull(typeId)) {
			dto.setAutoClaim(CmnConstants.ONE);
			dto.setStatus(CmnConstants.ONE);
			mav.addObject(PageConstants.REFERENCE, dto);
			dto.setAction(CmnConstants.BTN_CREATE);
		} else {
			dto.setTypeId(typeId);
			mav.addObject(PageConstants.REFERENCE, getCmmnService().getType(dto));
		}

		mav.setViewName(PageConstants.PAGE_URL_TYPE);

		return mav;
	}


	@GetMapping(value = "/level")
	public ModelAndView getLevel(@RequestParam(value = "typeId", required = true) Integer typeId,
			@RequestParam("levelId") Integer levelId) {

		ModelAndView mav = new ModelAndView();
		WfwReference dto = new WfwReference();

		dto.setTypeId(typeId);
		dto.setLevelId(levelId);
		dto = getCmmnService().getRefDetails(dto);

		if (CmnUtil.isObjNull(levelId)) {
			dto.setRefLevel(new RefLevel());
			dto.getRefLevel().setTypeId(dto.getTypeId());
			dto.getRefLevel().setStatus(CmnConstants.ONE);
			dto.setAction(CmnConstants.BTN_CREATE);
		}

		dto.setConfig(CmnConstants.REFERENCE_LEVEl);
		mav.addObject(PageConstants.REFERENCE, dto);
		mav.setViewName(PageConstants.PAGE_URL_LEVEL);
		return mav;
	}


	@GetMapping(value = "/status")
	public ModelAndView getStatus(@RequestParam(value = "levelId", required = true) Integer levelId,
			@RequestParam("statusId") Integer statusId, @RequestParam("typeId") Integer typeId) {

		ModelAndView mav = new ModelAndView();
		WfwReference dto = new WfwReference();

		dto.setLevelId(levelId);
		dto.setStatusId(statusId);
		dto.setTypeId(typeId);
		dto = getCmmnService().getRefDetails(dto);

		if (CmnUtil.isObjNull(dto.getRefStatus())) {
			dto.setRefStatus(new RefStatus());
			dto.getRefStatus().setTypeId(dto.getTypeId());
			dto.getRefStatus().setLevelId(dto.getRefLevel().getLevelId());
			dto.getRefStatus().setInitialState(CmnConstants.ONE);
			dto.getRefStatus().setDisplay(CmnConstants.ONE);
			dto.getRefStatus().setEmailRequired(CmnConstants.ONE);
			dto.getRefStatus().setSkipApprover(CmnConstants.ZERO);
			dto.getRefStatus().setStatus(CmnConstants.ONE);
			dto.setAction(CmnConstants.BTN_CREATE);
		}

		dto.setConfig(CmnConstants.REFERENCE_STATUS);
		mav.addObject(PageConstants.REFERENCE, dto);
		mav.addObject("nextLevel", getCmmnService().getRefLevelListByType(typeId));
		mav.setViewName(PageConstants.PAGE_URL_STATUS);

		return mav;
	}


	@PostMapping(params = "update")
	public ModelAndView updateReference(@ModelAttribute("reference") WfwReference reference) {

		ModelAndView mav = new ModelAndView();
		String id = CmnConstants.REFERENCE_TYPE;
		Integer typeId = null;
		try {

			if (CmnUtil.isEqualsAny(reference.getConfig(), CmnConstants.REFERENCE_LEVEl)
					&& !CmnUtil.isObjNull(reference.getRefLevel())) {
				logger.info("has LEVEL");
				typeId = getCmmnService().addUpdateLevel(reference.getRefLevel()).getTypeId();
				id = CmnConstants.REFERENCE_LEVEl;
			}

			else if (CmnUtil.isEqualsAny(reference.getConfig(), CmnConstants.REFERENCE_STATUS)
					&& !CmnUtil.isObjNull(reference.getRefStatus())) {
				logger.info("has STATUS");
				typeId = getCmmnService().addUpdateStatus(reference.getRefStatus()).getTypeId();
				id = CmnConstants.REFERENCE_STATUS;
			}

			else if (!CmnUtil.isObjNull(reference)) {
				logger.info("has TYPE");
				reference.setUserId(SessionData.getCurrentUserProfile().getUsername());
				typeId = getCmmnService().addUpdateType(reference).getTypeId();
			}

			mav.addAllObjects(PopupBox.success(id, "Workflow " + id, id + " has successfully updated."));

		} catch (WfwException e) {
			logger.error("Error: Create/Update ", e);
			mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT, id + " " + e.getMessage()));
		}

		// return view(typeId);
		mav.addObject(PageConstants.REFERENCE,
				getCmmnService().getRefListDetails(new WfwReference(typeId, true, true), null));
		mav.setViewName(PageConstants.PAGE_URL_CONFIG);
		return mav;
	}


	@PostMapping(params = "cancel")
	public String cancel(@ModelAttribute("reference") WfwReference reference) {
		if (CmnUtil.isObjNull(reference.getTypeId())) {
			return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_CONFIG;
		} else {
			return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_CONFIG + "?typeId=" + reference.getTypeId();
		}
	}


	@ModelAttribute("typeList")
	public List<WfwRefType> getRefTypeList() {
		return getCmmnService().getRefTypeList();
	}
}
