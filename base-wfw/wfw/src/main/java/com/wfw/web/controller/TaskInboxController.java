/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.web.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.SessionData;


/**
 * @author michelle.angela
 *
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_INBOX)
public class TaskInboxController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(TaskInboxController.class);


	@GetMapping
	public ModelAndView viewInbox(WfwRefPayload task) {
		return searchInbox(new WfwRefPayload());
	}


	@PostMapping
	public ModelAndView searchInbox(@ModelAttribute(value = "task") WfwRefPayload task) {

		ModelAndView mav = new ModelAndView();
		log.info("aplication id = {}", task.getAppRefNo());

		task.setClaimBy(SessionData.getCurrentUserId());
		task.setType(WorkflowConstants.INBOX);
		List<TaskMaster> taskMasterList = getCmmnService().getTaskMasterList(task);
		mav.addObject(PageConstants.DATA_LIST_OBJ, taskMasterList);
		if (CmnUtil.isListNull(taskMasterList)) {
			mav.addObject("msg", "No task found.");
		} else {
			mav.addObject("msg", taskMasterList.size() + " task(s) found.");
		}
		mav.addObject("task", task);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_INBOX);
		return mav;
	}


	@PostMapping(params = "release")
	public String releaseTasks(@ModelAttribute(value = "task") WfwRefPayload task, HttpSession session) {

		log.info("claimTask =============== {}", task.getTaskMasterIdList().size());
		if (!CmnUtil.isListNull(task.getTaskMasterIdList())) {
			task.setTaskAuthorId(SessionData.getCurrentUserProfile().getUsername());
			task.setTaskAuthorName(SessionData.getCurrentUserProfile().getName());
			getCmmnService().releaseTask(task);
		}
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_INBOX;
	}


	@PostMapping(params = "reset")
	public String reset(@ModelAttribute(value = "task") WfwRefPayload task, HttpSession session) {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_INBOX;
	}
}
