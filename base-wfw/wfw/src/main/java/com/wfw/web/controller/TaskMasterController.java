/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.web.controller;


import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_MASTER)
public class TaskMasterController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(TaskMasterController.class);


	@GetMapping
	public ModelAndView master(WfwRefPayload task) {
		return searchMaster(new WfwRefPayload());
	}


	@PostMapping
	public ModelAndView searchMaster(@Valid @ModelAttribute(value = "task") WfwRefPayload task) {

		ModelAndView mav = new ModelAndView();
		log.info("officer id={}", task.getSubmitBy());
		log.info("aplication id={}", task.getAppRefNo());

		List<TaskMaster> taskMasterList = getCmmnService().getTaskMasterList(task);
		mav.addObject(PageConstants.DATA_LIST_OBJ, taskMasterList);
		if (CmnUtil.isListNull(taskMasterList)) {
			mav.addObject("msg", "No task found.");
		} else {
			mav.addObject("msg", taskMasterList.size() + " task(s) found.");
		}

		mav.addObject("task", task);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_MASTER);
		return mav;
	}


	@PostMapping(params = "reset")
	public String reset(@ModelAttribute(value = "task") WfwRefPayload task, HttpSession session) {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_MASTER;
	}

}
