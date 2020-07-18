/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.rest.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.model.WfwTaskHistory;
import com.wfw.model.WfwTaskMaster;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.service.CommonService;
import com.wfw.service.WfwTaskHistoryService;
import com.wfw.service.WfwTaskMasterService;


/**
 * @author michelle.angela
 *
 */
@RestController
@RequestMapping(WorkflowConstants.TASK)
public class TaskRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WfwTaskMasterService taskMasterSvc;

	@Autowired
	private WfwTaskHistoryService taskHistorySvc;

	@Autowired
	private CommonService commonSvc;

	@Autowired
	protected Mapper dozerMapper;


	@PostMapping(value = WorkflowConstants.MASTER, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<TaskMaster> taskMaster(@Valid @RequestBody WfwRefPayload payload,
			HttpServletRequest request) {
		logger.info("Calling taskMaster table rest service.");
		DataTableRequest<TaskMaster> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<TaskMaster> all = taskMasterSvc.getTaskMasterList(payload, null);
		List<TaskMaster> filtered = taskMasterSvc.getTaskMasterList(payload, dataTableInRQ);
		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}


	@PostMapping(value = WorkflowConstants.HISTORY, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<TaskHistory> taskHistory(@Valid @RequestBody WfwRefPayload payload,
			HttpServletRequest request) {
		logger.info("Calling taskHistory table rest service.");
		DataTableRequest<TaskHistory> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<TaskHistory> filtered = taskHistorySvc.getTaskHistory(payload, dataTableInRQ);
		List<WfwTaskHistory> all = taskHistorySvc.getWfwTaskHistory(payload, null);
		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}


	@PostMapping(value = WorkflowConstants.START_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean startTask(@Valid @RequestBody WfwRefPayload payload) {

		if (CmnUtil.isObjNull(payload.getAppRefNo())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		logger.info("Calling start task rest service: application Ref No {}", payload.getAppRefNo());
		WfwTaskMaster wfwTaskMaster = commonSvc.startTask(payload);
		logger.info("isTaskCreated = {}", !CmnUtil.isObjNull(wfwTaskMaster));
		return !CmnUtil.isObjNull(wfwTaskMaster);
	}


	@PostMapping(value = WorkflowConstants.COMPLETE_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean completeStartTask(@Valid @RequestBody WfwRefPayload payload) {

		if (CmnUtil.isObjNull(payload.getTaskMasterId())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		logger.info("Calling complete task rest service: application Ref No {}", payload.getTaskMasterId());
		WfwTaskMaster wfwTaskMaster = commonSvc.updateTask(payload);
		logger.info("isTaskCompleted = {}", !CmnUtil.isObjNull(wfwTaskMaster));
		return !CmnUtil.isObjNull(wfwTaskMaster);
	}


	@PostMapping(value = WorkflowConstants.CLAIM_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean claimTask(@Valid @RequestBody WfwRefPayload payload) {

		if (CmnUtil.isListNull(payload.getTaskMasterIdList())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		logger.info("Calling workflow release task service: applications{}", payload.getTaskMasterIdList());
		return commonSvc.claimTask(payload);
	}


	@PostMapping(value = WorkflowConstants.RELEASE_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean releaseTask(@Valid @RequestBody WfwRefPayload payload) {

		if (CmnUtil.isListNull(payload.getTaskMasterIdList())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		logger.info("Calling workflow release task service: applications{}", payload.getTaskMasterIdList());
		return commonSvc.releaseTask(payload);
	}


	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public TaskMaster getTaskMasterByAppRefNo(@RequestParam(value = "appRefNo", required = true) String appRefNo,
			HttpServletRequest request) {
		return taskMasterSvc.findTaskByAppRefNo(appRefNo);
	}
	
	
	@PostMapping(value = WorkflowConstants.REG_NO_APPLICANT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<TaskMaster> getRefNoApplicant(@Valid @RequestBody WfwRefPayload payload,
			HttpServletRequest request) {
		logger.info("Calling taskMaster reference no and applicants.....");
		return taskMasterSvc.getTaskMasterList(payload, null);
	}
}
