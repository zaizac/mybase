package com.bff.cmn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.MessageConstants;
import com.bff.util.constants.PageConstants;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableResults;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;

/**
 * @author michelle.angela
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_INBOX)
public class InboxController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboxController.class);

	@GetMapping(value = "/wfw/myInbox/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<TaskMaster> getWfwMyInbox(WfwRefPayload payload, HttpServletRequest request) {

		LOGGER.info("GET PAGINATED MY INBOX LIST....");
		DataTableResults<TaskMaster> inboxTaskList = new DataTableResults<>();

		if (BaseUtil.isObjNull(payload)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			payload.setDisplay(true);
			if (!BaseUtil.isObjNull(payload.getTaskAuthorId())) {
				payload.setClaimBy(payload.getTaskAuthorId());
			}
			inboxTaskList = getWfwService().getTaskMasterList(payload, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return inboxTaskList;
	}

	@GetMapping(value = "/wfw/myPool/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<TaskMaster> getWfwMyPool(WfwRefPayload payload, HttpServletRequest request) {

		LOGGER.info("GET PAGINATED MY POOL LIST....");
		DataTableResults<TaskMaster> inboxTaskList = new DataTableResults<>();

		if (BaseUtil.isObjNull(payload) || BaseUtil.isObjNull(payload.getRoles())) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			payload.setDisplay(true);
			payload.setPool(true);
			inboxTaskList = getWfwService().getTaskMasterList(payload, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return inboxTaskList;
	}

	@GetMapping(value = "/wfw/masterHistory/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<TaskMaster> getMyMasterHistory(WfwRefPayload payload, HttpServletRequest request) {

		LOGGER.info("GET PAGINATED MASTER HISTORY LIST....");
		DataTableResults<TaskMaster> inboxTaskList = new DataTableResults<>();

		if (BaseUtil.isObjNull(payload) || BaseUtil.isObjNull(payload.getTaskAuthorId())) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			payload.setDisplay(true);
			payload.setSubmitBy(payload.getTaskAuthorId());
			inboxTaskList = getWfwService().getTaskMasterWithHistoryList(payload, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return inboxTaskList;
	}

	@GetMapping(value = "/wfw/myHistory/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<TaskHistory> getMyHistory(WfwRefPayload payload, HttpServletRequest request) {

		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<TaskHistory> inboxTaskList = new DataTableResults<>();

		if (BaseUtil.isObjNull(payload) || BaseUtil.isObjNull(payload.getTaskAuthorId())) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			payload.setSubmitBy(payload.getTaskAuthorId());
			inboxTaskList = getWfwService().getTaskHistoryList(payload, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return inboxTaskList;
	}

	@PostMapping(value = "/wfw/claim", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String wfwClaim(@RequestBody WfwRefPayload payload) {

		LOGGER.info("Claim : The application ID is :{}", payload.getTaskMasterIdList());
		if (BaseUtil.isObjNull(payload) || BaseUtil.isObjNull(payload.getTaskMasterIdList())
				|| BaseUtil.isObjNull(payload.getRoles())) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			getWfwService().claimTasks(payload);
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		}
		return null;
	}

	@PostMapping(value = "/wfw/release", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String wfwRelease(@RequestBody WfwRefPayload payload) {

		LOGGER.info("Release : The application ID is :{}", payload.getTaskMasterIdList());
		if (BaseUtil.isObjNull(payload) || BaseUtil.isObjNull(payload.getTaskMasterIdList())) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}

		try {
			getWfwService().releaseTasks(payload);
		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		}
		return null;
	}

	@PostMapping(value = "/wfw/refNoApplicant", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<TaskMaster> getRefNoApplicant(@RequestBody WfwRefPayload payload, HttpServletRequest request) {
		List<TaskMaster> taskMaster = new ArrayList<>();
		LOGGER.info("RefNoApplicant : The application ID is :{}", payload);
		if (BaseUtil.isObjNull(payload)) {
			LOGGER.info("Payload is null");
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		try {
			if (payload.getInboxType().equals("inbox")) {
				payload.setDisplay(true);
				if (!BaseUtil.isObjNull(payload.getTaskAuthorId())) {
					payload.setClaimBy(payload.getTaskAuthorId());
				}
				taskMaster = getWfwService().getRefNoApplicant(payload, null);
			}
			if (payload.getInboxType().equals("pool")) {
				LOGGER.info("pool>>>>>");
				payload.setDisplay(true);
				payload.setPool(true);
				taskMaster = getWfwService().getRefNoApplicant(payload, null);
			}
			if (payload.getInboxType().equals("history")) {
				LOGGER.info("history>>>");
				payload.setDisplay(true);
				payload.setSubmitBy(payload.getTaskAuthorId());
				
				taskMaster = getWfwService().getRefNoApplicantHistoryList(payload, null);
			}

		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}
		// List<TaskMaster> taskMaster = getWfwService().getRefNoApplicant(payload,
		// null);
		// taskMaster = getWfwService().getRefNoApplicant(payload, null);
		return taskMaster;
	}
}
