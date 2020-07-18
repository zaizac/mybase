/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.web.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wfw.constant.PageConstants;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwTaskMaster;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.PopupBox;


/**
 * @author michelle.angela
 *
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_SRC_TASK + "/details")
public class TaskController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	@Qualifier(QualifierConstants.TASK_FORM_VALIDATOR)
	private Validator validator;


	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}


	@Autowired
	protected Mapper dozerMapper;


	@GetMapping
	public ModelAndView taskDetails(@RequestParam("taskMasterId") String taskMasterId,
			@RequestParam(name = "listType", defaultValue = "master") String listType,
			@RequestParam(name = "history", defaultValue = "false") boolean history, HttpSession session) {
		log.info("taskMasterId = {}", taskMasterId);

		ModelAndView mav = new ModelAndView();
		boolean isNew = CmnUtil.isObjNull(taskMasterId);
		mav.addObject("showBack", true);
		mav.addObject("showEdit", true);
		mav.addObject("listType", listType);

		TaskMaster taskMaster = null;
		if (isNew) {
			taskMaster = new TaskMaster();
		} else {
			taskMaster = getCmmnService().getTaskMaster(taskMasterId, history);

			if (!CmnUtil.isObjNull(taskMaster.getPrevLevelId())) {
				taskMaster.setPrevLevel(getCmmnService().getRefLevel(taskMaster.getPrevLevelId()));
			}

			if (!CmnUtil.isObjNull(taskMaster.getPrevStatusId())) {
				taskMaster.setPrevStatus(getCmmnService().getRefStatus(taskMaster.getPrevStatusId()));
			}

			if (!CmnUtil.isObjNull(taskMaster.getLevelId())) {
				mav.addObject("statusList", getCmmnService().getRefStatusListByLevel(taskMaster.getLevelId()));
			}
		}

		setObject(mav, isNew);
		mav.addObject("isComplete", (!isNew && CmnUtil.isObjNull(taskMaster.getRefLevel())));
		log.info("-----------isNew--------" + !isNew);
		log.info("-----------LEVEL--------" + CmnUtil.isObjNull(taskMaster.getRefLevel()));
		log.info("-----------isComplete--------" + (!isNew && CmnUtil.isObjNull(taskMaster.getRefLevel())));
		mav.addObject("task", taskMaster);
		mav.setViewName(history ? PageConstants.PAGE_CONST_TASK_HIST : PageConstants.PAGE_CONST_TASK_DTLS);
		return mav;
	}


	@PostMapping(params = "create")
	public ModelAndView create(@Valid @ModelAttribute("task") TaskMaster task, BindingResult result) {

		log.info("-----------CREATE--------");
		ModelAndView mav = new ModelAndView();

		if (!result.hasErrors()) {

			try {
				WfwRefPayload payload = new WfwRefPayload();
				// payload.setRoles(task.getRoles());
				payload.setRoles(new ArrayList<String>());
				payload.getRoles().add(task.getRoles());
				payload.setApplicantId(task.getApplicantId());
				payload.setApplicant(task.getApplicant());
				payload.setAppRefNo(task.getAppRefNo());
				payload.setAppStatus(task.getAppStatus());
				payload.setRemark(task.getRemark());
				payload.setAdditionalInfo(task.getAdditionalInfo());
				payload.setTaskAuthorId(task.getSubmitBy());
				payload.setTaskAuthorName(task.getSubmitByName());
				WfwTaskMaster taskMaster = getCmmnService().startTask(payload);

				task.setTaskMasterId(taskMaster.getTaskMasterId());
				mav.addAllObjects(
						PopupBox.success("TASK", "Task " + task.getTaskMasterId() + " has successfully added."));

			} catch (WfwException e) {

				log.error("Error: Create/Update ", e);
				mav.addAllObjects(PopupBox.error("Error!", "Error!", e.getMessage()));
			}
			// redirect(PageConstants.PAGE_URL_TASK_MASTER);

		} else {
			setObject(mav, CmnUtil.isObjNull(task.getTaskMasterId()));
			mav.addObject("task", task);
			mav.setViewName(PageConstants.PAGE_CONST_TASK_DTLS);
			return mav;
		}

		return taskDetails(task.getTaskMasterId(), "master", false, null);
	}


	@PostMapping(params = "update")
	public ModelAndView update(@ModelAttribute("task") TaskMaster task,
			@RequestParam(name = "listType", defaultValue = "master") String listType, BindingResult result,
			HttpSession session) {
		log.info("update.....");
		ModelAndView mav = new ModelAndView();
		if (!result.hasErrors()) {

			try {
				WfwRefPayload payload = new WfwRefPayload();
				payload.setTaskMasterId(task.getTaskMasterId());
				payload.setStatusCd(task.getRefStatus().getStatusCd());
				payload.setTaskAuthorId(task.getSubmitBy());
				payload.setTaskAuthorName(task.getSubmitByName());
				getCmmnService().updateTask(payload);

				mav.addAllObjects(
						PopupBox.success("TASK", "Task " + task.getTaskMasterId() + " has successfully added."));

			} catch (WfwException e) {

				log.error("Error: Create/Update ", e);
				mav.addAllObjects(PopupBox.error("Error!", "Error!", e.getMessage()));
			}
		} else {
			setObject(mav, CmnUtil.isObjNull(task.getTaskMasterId()));
			mav.addObject("task", task);
			mav.setViewName(PageConstants.PAGE_CONST_TASK_DTLS);
			return mav;
		}

		return taskDetails(task.getTaskMasterId(), "master", false, null);
	}


	@PostMapping(params = "history")
	public ModelAndView viewHistory(@ModelAttribute("task") TaskMaster task,
			@RequestParam(name = "listType", defaultValue = "master") String listType, HttpSession session) {
		return taskDetails(task.getTaskMasterId(), listType, true, session);
	}


	@PostMapping(params = "cancel")
	public String cancel(@RequestParam(name = "listType", defaultValue = "master") String listType) {
		return PageConstants.PAGE_REDIRECT + "/task-" + listType;
	}


	public String redirect(String page) {
		return PageConstants.PAGE_REDIRECT + page;
	}


	private void setObject(ModelAndView mav, boolean isNew) {

		mav.addObject("btnBck", isNew ? CmnConstants.BTN_CANCEL : CmnConstants.BTN_BACK);
		mav.addObject("action", isNew ? CmnConstants.BTN_CREATE : CmnConstants.BTN_UPDATE);
		mav.addObject("disable", !isNew);
		mav.addObject("isNew", isNew);
		mav.addObject("roleList", getCmmnService().getRefLevelRoleList());
	}
}
