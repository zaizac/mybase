package com.wfw.rest.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.MultipleTaskDetails;
import com.wfw.sdk.model.TaskDetails;
import com.wfw.sdk.model.TaskRemark;
import com.wfw.sdk.model.TaskStatus;
import com.wfw.sdk.model.WfwPayload;
import com.wfw.sdk.util.CmnUtil;


@RestController
@RequestMapping(WorkflowConstants.TASKS)
public class WorkflowRestController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@GetMapping(value = WorkflowConstants.TEST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String isRestAlive() {
		logger.info("This is rest service test");
		return "The workfow service is up and running......";
	}


	// return all claimable tasks
	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<TaskDetails> tasks(@RequestParam("isClaimed") String isClaimed,
			@RequestParam(value = "taskIds", required = false) String taskIds,
			@RequestParam(value = "refNo", required = false) String refNo,
			@RequestParam(value = "recruitmentAgent", required = false) String recruitmentAgent,
			@RequestParam(value = "raCmpnyRegNo", required = false) String raCmpnyRegNo,
			@RequestParam(value = "raProfileId", required = false) Integer raProfileId,
			@RequestParam(value = "modules", required = false) String modules,
			@RequestParam(value = "roles", required = false) String roles,
			@RequestParam(value = "taskAuthorId", required = false) String taskAuthorId,
			@RequestParam(value = "appDateFrom", required = false) String appDateFrom,
			@RequestParam(value = "appDateTo", required = false) String appDateTo,
			@RequestParam(value = "cmpnyRegNo", required = false) String cmpnyRegNo,
			@RequestParam(value = "queueInd", required = false) String queueInd,
			@RequestParam(value = "isDisplay", required = false) String isDisplay,
			@RequestParam(value = "apprveDtFrm", required = false) String apprveDtFrm,
			@RequestParam(value = "apprveDtTo", required = false) String apprveDtTo,
			@RequestParam(value = "cmpnyName", required = false) String cmpnyName,
			@RequestParam(value = "pymtInvoiceNo", required = false) String pymtInvoiceNo,
			@RequestParam(value = "applStatusCode", required = false) String applStatusCode,
			@RequestParam(value = "isQuotaSec", required = false) String isQuotaSec,
			@RequestParam(value = "portalType", required = false) String portalType) {

		long start = System.currentTimeMillis();
		logger.info("Calling taskList rest service.");
		logger.info("param isClaimed = {}", isClaimed);
		logger.info("param taskIds = {}", taskIds);
		logger.info("param refNo = {}", refNo);
		logger.info("param recruitmentAgent = {}", recruitmentAgent);
		logger.info("param raCmpnyRegNo = {}", raCmpnyRegNo);
		logger.info("param raProfileId = {}", raProfileId);
		logger.info("param modules = {}", modules);
		logger.info("param roles = {}", roles);
		logger.info("param taskAuthorId = {}", taskAuthorId);
		logger.info("param appDateFrom = {}", appDateFrom);
		logger.info("param appDateTo = {}", appDateTo);
		logger.info("param cmpnyRegNo = {}", cmpnyRegNo);
		logger.info("param queueInd = {}", queueInd);
		logger.info("param isDisplay = {}", isDisplay);
		logger.info("param applStatusCode = {}", applStatusCode);
		logger.info("param apprveDtFrm = {}", apprveDtFrm);
		logger.info("param apprveDtTo = {}", apprveDtTo);
		logger.info("param pymtInvoiceNo = {}", pymtInvoiceNo);
		logger.info("param cmpnyName = {}", cmpnyName);
		logger.info("param isQuotaSec = {}", isQuotaSec);
		logger.info("param portalType = {}", portalType);

		WfwPayload payload = new WfwPayload();

		if (!CmnUtil.isObjNull(taskIds)) {
			payload.setTaskIds(taskIds);
		}

		if (!CmnUtil.isObjNull(refNo)) {
			payload.setRefNo(refNo);
		}
		if (!CmnUtil.isObjNull(recruitmentAgent)) {
			payload.setRecruitmentAgent(recruitmentAgent);
		}
		if (!CmnUtil.isObjNull(raCmpnyRegNo)) {
			payload.setRaCmpnyRegNo(raCmpnyRegNo);
		}
		if (raProfileId != null && raProfileId > 0) {
			payload.setRaProfileId(raProfileId);
		}

		if (!CmnUtil.isObjNull(modules)) {
			payload.setModules(modules);
		}

		if (!CmnUtil.isObjNull(roles)) {
			payload.setRoles(roles);
		}

		if (!CmnUtil.isObjNull(taskAuthorId)) {
			payload.setTaskAuthorId(taskAuthorId);
		}

		if (!CmnUtil.isObjNull(appDateFrom)) {
			payload.setAppDateFrom(CmnUtil.convertDate(appDateFrom, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(appDateTo)) {
			payload.setAppDateTo(CmnUtil.convertDate(appDateTo, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(cmpnyRegNo)) {
			payload.setCmpnyRegNo(cmpnyRegNo);
		}

		if (!CmnUtil.isObjNull(queueInd)) {
			payload.setQueueInd(queueInd);
		}

		if (!CmnUtil.isObjNull(isDisplay)) {
			payload.setIsDisplay(isDisplay);
		}

		if (!CmnUtil.isObjNull(applStatusCode)) {
			payload.setApplStatusCode(applStatusCode);
		}
		if (!CmnUtil.isObjNull(apprveDtFrm)) {
			payload.setApprveDtFrm(CmnUtil.convertDate(apprveDtFrm, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(apprveDtTo)) {
			payload.setApprveDtTo(CmnUtil.convertDate(apprveDtTo, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(cmpnyName)) {
			payload.setCmpnyName(cmpnyName);
		}

		if (!CmnUtil.isObjNull(pymtInvoiceNo)) {
			payload.setInvoiceNo(pymtInvoiceNo);
		}

		if (!CmnUtil.isObjNull(isQuotaSec)) {
			payload.setIsQuotaSec(isQuotaSec);
		}

		List<TaskDetails> taskList = new ArrayList<>();

		if (!CmnUtil.isObjNull(isClaimed)) {
			if (CmnUtil.isEqualsCaseIgnore(isClaimed, "true")) {
				taskList = getCommonService().claimTaskList(payload);

			} else if (CmnUtil.isEqualsCaseIgnore(isClaimed, "false")) {
				taskList = getCommonService().taskList(payload);

			} else if (CmnUtil.isEqualsCaseIgnore(isClaimed, "claimRandom")) {
				int noOfClaimedRandom = getCommonService().claimRandomTask(payload);
				logger.info("Randomly claimed no. of tasks= {}", noOfClaimedRandom);
				payload.setIntvwDate(null);
				taskList = getCommonService().claimTaskList(payload);
			} else if (CmnUtil.isEqualsCaseIgnore(isClaimed, "groupAdmin")) {
				taskList = getCommonService().claimTaskListGroupAdmin(payload);
			}
		} else {
			taskList = getCommonService().taskList(payload);
		}

		logger.info("taskList = {}", taskList.size());
		long end = System.currentTimeMillis();
		long timeTaken = end - start;
		logger.info("timeTaken in seconds>>{}", (timeTaken / 1000) % 60);
		if (CmnUtil.isListNull(taskList)) {
			return new ArrayList<>();
		}
		return taskList;
	}


	@GetMapping(value = WorkflowConstants.APPLICATION_LIST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<TaskDetails> applicationList(@RequestParam(value = "appDateFrom", required = false) String appDateFrom,
			@RequestParam(value = "appDateTo", required = false) String appDateTo,
			@RequestParam(value = "cmpnyRegNo", required = false) String cmpnyRegNo,
			@RequestParam(value = "cmpnyName", required = false) String cmpnyName) {
		long start = System.currentTimeMillis();
		logger.info("Calling applicationList rest service.");
		logger.info("param appDateFrom = {}", appDateFrom);
		logger.info("param appDateTo = {}", appDateTo);
		logger.info("param cmpnyRegNo = {}", cmpnyRegNo);
		logger.info("param cmpnyName = {}", cmpnyName);

		WfwPayload payload = new WfwPayload();

		if (!CmnUtil.isObjNull(appDateFrom)) {
			payload.setAppDateFrom(CmnUtil.convertDate(appDateFrom, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(appDateTo)) {
			payload.setAppDateTo(CmnUtil.convertDate(appDateTo, BaseConstants.DT_DD_MM_YYYY_SLASH));
		}

		if (!CmnUtil.isObjNull(cmpnyRegNo)) {
			payload.setCmpnyRegNo(cmpnyRegNo);
		}

		if (!CmnUtil.isObjNull(cmpnyName)) {
			payload.setCmpnyName(cmpnyName);
		}

		List<TaskDetails> applicationList = getCommonService().applicationList(payload);
		logger.info("applicationList = {}", applicationList.size());
		long end = System.currentTimeMillis();
		long timeTaken = end - start;
		logger.info("timeTaken in seconds>>{}", (timeTaken / 1000) % 60);
		if (CmnUtil.isListNull(applicationList)) {
			return new ArrayList<>();
		}
		return applicationList;
	}


	public List<TaskDetails> taskList(WfwPayload payload) {
		logger.info("Calling taskList rest service.");
		List<TaskDetails> taskList = getCommonService().taskList(payload);
		logger.info("taskList = {}", taskList.size());
		if (CmnUtil.isListNull(taskList)) {
			return new ArrayList<>();
		}
		return taskList;
	}


	public List<TaskDetails> claimTaskList(WfwPayload payload) throws Exception {
		logger.info("Calling claimTaskList rest service.");
		List<TaskDetails> mstrList = new ArrayList<>();
		if (CmnUtil.isObjNull(payload) || CmnUtil.isObjNull(payload.getTaskAuthorId())) {
			return mstrList;
		}
		mstrList = getCommonService().claimTaskList(payload);
		logger.info("mstrList = {}", mstrList.size());
		return mstrList;
	}


	@GetMapping(value = WorkflowConstants.TASK_REMARKS, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<TaskRemark> taskHistory(@RequestParam("range") String range, @RequestParam("refNo") String refNo,
			@RequestParam(value = "applStatusCode", required = false) String applStatusCode) {
		logger.info("Calling taskHistory rest service.");
		logger.info("param range = {}", range);
		logger.info("param refNo = {}", refNo);
		logger.info("param applStatusCode = {}", applStatusCode);

		WfwPayload payload = new WfwPayload();

		if (!CmnUtil.isObjNull(refNo)) {
			payload.setRefNo(refNo);
		}

		if (!CmnUtil.isObjNull(applStatusCode)) {
			payload.setApplStatusCode(applStatusCode);
		}

		List<TaskRemark> taskRemarkList = getCommonService().taskRemarkList(payload, range);

		logger.info("taskRemarkList = {}", taskRemarkList.size());

		return taskRemarkList;
	}


	@GetMapping(value = WorkflowConstants.TASK_REMARKS_TASK_ID, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public TaskRemark getLatestRemarks(@RequestParam("refNo") String refNo,
			@RequestParam(value = "applStatusCode", required = false) String applStatusCode) throws Exception {
		logger.info("Calling getLatestRemarks rest service.");
		logger.info("param refNo = {}", refNo);
		logger.info("param applStatusCode = {}", applStatusCode);

		List<WfwInboxHist> wfwInboxHist = wfwInboxHistDao.findWfInboxHistByTaskidAndStausCode(refNo, applStatusCode);

		TaskRemark taskRemark = null;
		if (!BaseUtil.isListNull(wfwInboxHist)) {
			taskRemark = new TaskRemark();
			taskRemark.setTaskId(wfwInboxHist.get(0).getTaskId());
			taskRemark.setRemarks(wfwInboxHist.get(0).getApplRemark());
			taskRemark.setRemarkByFullName(wfwInboxHist.get(0).getOfficerName());
		}

		return taskRemark;
	}


	// return task details by task Id
	@GetMapping(value = WorkflowConstants.TASK_BY_ID, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TaskDetails taskDetailsById(@RequestParam(value = "taskId", required = true) String taskId) {
		logger.info("Calling  taskDetails rest service with taskId ={}", taskId);
		if (BaseUtil.isObjNull(taskId)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		return getCommonService().taskDetails(taskId);
	}


	// return task details by task Id
	@GetMapping(value = WorkflowConstants.TASK_DETAILS, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TaskDetails taskDetails(@PathVariable String taskId,
			@RequestParam(value = "replaceDash", required = false) String replaceDash) {
		logger.info("Calling  taskDetails rest service with taskId ={}  replaceDash ={}", taskId, replaceDash);
		if (CmnUtil.isEqualsCaseIgnore(replaceDash, CmnConstants.YES_FULL)) {
			taskId = taskId.replaceAll("-", "/");
		}
		if (CmnUtil.isEqualsCaseIgnore(replaceDash, CmnConstants.CUSTOM_FULL)) {
			taskId = taskId.replace("*", ".");
			taskId = taskId.replace("-", "/");
			taskId = taskId.replace("^", "-");
			taskId = taskId.replace("_", " ");
		}

		return getCommonService().taskDetails(taskId);
	}


	// Claimed Date
	@GetMapping(value = WorkflowConstants.CLAIMED_DATE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TaskDetails taskDetails1(@PathVariable String taskId,
			@RequestParam(value = "replaceDash", required = false) String replaceDash) {
		logger.info("Calling  taskDetails rest service with taskId ={}  replaceDash ={}", taskId, replaceDash);

		taskId = CmnUtil.revertQuotaSPPA(taskId);

		return getCommonService().taskDetail(taskId);
	}


	@GetMapping(value = WorkflowConstants.CLAIMED_DATE1, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TaskDetails taskDetails2(@PathVariable String taskId,
			@RequestParam(value = "replaceDash", required = false) String replaceDash) throws Exception {
		logger.info("Calling  taskDetails rest service with taskId ={} replaceDash ={}", taskId, replaceDash);

		taskId = CmnUtil.revertQuotaSPPA(taskId);

		return getCommonService().taskDetail1(taskId);
	}


	@GetMapping(value = WorkflowConstants.STATUS_LIST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<TaskStatus> statusList(@PathVariable String taskId,
			@RequestParam(value = "replaceDash", required = false) String replaceDash) {
		logger.info("Calling statusList rest service with taskId ={} replaceDash ={}", taskId, replaceDash);
		if (CmnUtil.isEqualsCaseIgnore(replaceDash, CmnConstants.YES_FULL)) {
			taskId = taskId.replaceAll("-", "/");
		}
		List<TaskStatus> statusList = getCommonService().statusList(taskId);
		logger.info("statusList = {}", statusList.size());
		return statusList;
	}


	@PostMapping(value = WorkflowConstants.CLAIM_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean claimTasks(@Valid @RequestBody WfwPayload payload) {
		logger.info("Calling claim task rest service for task id = {}",
				payload.getTaskIds() + " user type =" + payload.getUserType());
		boolean isClaimed = getCommonService().claimTasks(payload);
		logger.info("isClaimed = {}", isClaimed);
		return isClaimed;
	}


	@PostMapping(value = WorkflowConstants.RELEASE_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean releaseTasks(@Valid @RequestBody WfwPayload payload) {
		logger.info("Calling release task rest service for task id = {}",
				payload.getTaskIds() + " user type =" + payload.getUserType());
		boolean isReleased = getCommonService().realeaseTasks(payload);
		logger.info("isReleased = {}", isReleased);
		return isReleased;
	}


	@PostMapping(value = WorkflowConstants.RELEASE_TASK_GROUP_ADMIN, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean releaseTasksGroupAdmin(@Valid @RequestBody WfwPayload payload) throws Exception {
		logger.info(
				"Calling release task group admin rest service for task id = {} -  user type ={} - officer Id ={} - action done by :{}",
				payload.getTaskIds(), payload.getUserType(), payload.getTaskAuthorId(), payload.getGroupAdminId());
		boolean isReleased = getCommonService().releaseTaskGroupAdmin(payload);
		logger.info("isReleased = {}", isReleased);
		return isReleased;
	}


	@PostMapping(value = WorkflowConstants.START_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean startTask(@Valid @RequestBody TaskDetails payload) throws Exception {
		logger.info("Calling start task rest service application ref no ={}", payload.getRefNo());
		boolean isTaskCreated = getCommonService().startTask(payload);
		logger.info("isTaskCreated = {}", isTaskCreated);
		return isTaskCreated;
	}


	@PostMapping(value = WorkflowConstants.START_MULTIPLE_TASK, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean startMultipleTask(@Valid @RequestBody MultipleTaskDetails taskDescripton) throws Exception {
		logger.info("Calling start multiple task rest service");
		if (CmnUtil.isObjNull(taskDescripton) || CmnUtil.isObjNull(taskDescripton.getCurrentTask())
				|| CmnUtil.isListNull(taskDescripton.getNewTaskList())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		TaskDetails payload = taskDescripton.getCurrentTask();
		logger.info("ref no ={}", payload.getRefNo());

		boolean isTaskCreated = getCommonService().startTask(payload, taskDescripton.getNewTaskList());
		logger.info("isTaskCreated = {}", isTaskCreated);
		return isTaskCreated;
	}


	@PostMapping(value = WorkflowConstants.AMEND_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean amendTask(@Valid @RequestBody TaskDetails payload) throws Exception {
		logger.info("Calling amend task rest service application ref no ={}", payload.getRefNo());
		boolean isAmendTaskCreated = getCommonService().amendTask(payload);
		logger.info("isAmendTaskCreated = {}", isAmendTaskCreated);
		return isAmendTaskCreated;
	}


	@PostMapping(value = WorkflowConstants.COMPLETE_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean completeTask(@Valid @RequestBody TaskDetails payload) {
		logger.info("Calling complete task rest service taskId ={} -  status = {} -  application status ={}",
				payload.getTaskId(), payload.getWfStatus(), payload.getApplStsCode());
		boolean isTaskCompleted = getCommonService().completeTask(payload);
		logger.info("isTaskCompleted = {}", isTaskCompleted);
		return isTaskCompleted;
	}


	@PostMapping(value = WorkflowConstants.COMPLETE_START_TASK, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean completeAndStartTask(@Valid @RequestBody TaskDetails payload) {
		logger.info("Calling complete task and start rest service taskId ={}",
				payload.getTaskId() + " ref No :" + payload.getRefNo());
		boolean isTaskCompleted = getCommonService().completeAndStartTask(payload);
		logger.info("isTaskCompletedAndCreated = {}", isTaskCompleted);
		return isTaskCompleted;
	}


	@PostMapping(value = WorkflowConstants.UPDATE_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean updateTask(@Valid @RequestBody TaskDetails payload) {
		logger.info("Calling update task rest service taskId ={}", payload.getTaskId());
		boolean isTaskUpdated = false;
		isTaskUpdated = getCommonService().updateTask(payload);
		logger.info("isTaskUpdated = {}", isTaskUpdated);
		return isTaskUpdated;
	}


	@PostMapping(value = WorkflowConstants.UPDATE_TASK_VERIFIER, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean updateTaskKDNVerifier(@Valid @RequestBody TaskDetails payload) {
		logger.info("Calling update task rest service taskId for kdn verifier ={}", payload.getTaskId());
		boolean isTaskUpdated = false;
		isTaskUpdated = getCommonService().updateTaskKDNVerifier(payload);
		logger.info("isTaskUpdated = {}", isTaskUpdated);
		return isTaskUpdated;
	}


	@PostMapping(value = WorkflowConstants.REJECT_TASK, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean rejectTask(@Valid @RequestBody TaskDetails payload) {
		logger.info("Calling reject task rest service taskId ={}", payload.getTaskId());
		boolean isRejectCompleted = getCommonService().rejectTask(payload);
		logger.info("isRejectCompleted = {}", isRejectCompleted);
		return isRejectCompleted;
	}


	@GetMapping(value = WorkflowConstants.DRAFT_APPLICATION_LIST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<TaskDetails> findDraftApplicationsByDate(
			@RequestParam(value = "draftStatus", required = true) String draftStatus,
			@RequestParam(value = "durationDate", required = true) String durationDate) {
		logger.info("param draftStatus = {}", draftStatus);
		logger.info("param durationDate = {}", durationDate);
		Date durationDates = CmnUtil.convertDate(durationDate, BaseConstants.DT_DD_MM_YYYY_SLASH);
		if (CmnUtil.isObjNull(draftStatus)) {
			draftStatus = CmnConstants.EMPTY_STRING;
		}

		List<TaskDetails> kivApplist = getCommonService().draftApplicationList(draftStatus, durationDates);

		logger.info("kivApplist = {}", kivApplist.size());

		return kivApplist;
	}


	@GetMapping(value = WorkflowConstants.HISTORY_BY_TASKID, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<WfwInboxHist> historyByTaskId(@RequestParam(value = "taskId", required = false) String taskId) {
		logger.info("param taskId = {}", taskId);
		return getCommonService().findWfInboxHistByTaskId(taskId);
	}


	@GetMapping(value = WorkflowConstants.UPDATE_TASK_ID, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public boolean updateTaskId(@RequestParam(value = "taskId", required = true) String taskId,
			@RequestParam(value = "appStatus", required = true) String appStatus) {
		logger.info("Calling complete task rest service taskId ={}", taskId);
		WfwPayload wfwPayload = new WfwPayload();
		wfwPayload.setTaskIds(taskId);
		wfwPayload.setApplStatusCode(appStatus);
		boolean isupdateCompleted = getCommonService().updateWfTaskId(wfwPayload);
		logger.info("isupdateCompleted = {}", isupdateCompleted);
		return isupdateCompleted;
	}


	// For pagination
	@PostMapping(value = "{isClaimed}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<TaskDetails> tasks(@PathVariable String isClaimed, @Valid @RequestBody WfwPayload payload,
			HttpServletRequest request) {
		DataTableRequest<TaskDetails> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		int raProfileId = 0;
		if (payload.getRaProfileId() != null && payload.getRaProfileId() > 0) {
			raProfileId = payload.getRaProfileId();
		}
		payload.setRaProfileId(raProfileId);
		logger.info("Calling taskList table rest service.");

		DataTableResults<TaskDetails> taskDetailsTable = new DataTableResults<>();

		if (!CmnUtil.isObjNull(isClaimed)) {
			if (CmnUtil.isEqualsCaseIgnore(isClaimed, "true")) {
				taskDetailsTable = getCommonService().claimTaskListTable(payload, dataTableInRQ);
			} else if (CmnUtil.isEqualsCaseIgnore(isClaimed, "false")) {
				taskDetailsTable = getCommonService().taskListTable(payload, dataTableInRQ);
			} else if (CmnUtil.isEqualsCaseIgnore(isClaimed, "groupAdmin")) {
				taskDetailsTable = getCommonService().claimTaskListGroupAdminTable(payload, dataTableInRQ);
			}
		} else {
			taskDetailsTable = getCommonService().taskListTable(payload, dataTableInRQ);
		}

		return taskDetailsTable;
	}


	@PostMapping(value = WorkflowConstants.APPLICATION_LIST + "/{updateId}", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<TaskDetails> applicationList(@Valid @RequestBody WfwPayload payload,
			HttpServletRequest request, @PathVariable String updateId) {
		DataTableRequest<TaskDetails> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<TaskDetails> taskDetailsTable = getCommonService().applicationListTable(payload,
				dataTableInRQ, updateId);
		logger.info("applicationList = {}", taskDetailsTable.getData().size());
		return taskDetailsTable;
	}


	@PostMapping(value = WorkflowConstants.APPLICATION_LIST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<TaskDetails> applicationList(@Valid @RequestBody WfwPayload payload,
			HttpServletRequest request) {
		DataTableRequest<TaskDetails> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<TaskDetails> taskDetailsTable = getCommonService().applicationListTable(payload,
				dataTableInRQ, null);
		logger.info("applicationList = {}", taskDetailsTable.getData().size());
		return taskDetailsTable;
	}


	@GetMapping(value = WorkflowConstants.UPDATE_INTERVIEW_DATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean updateIvDateByRefNo(@RequestParam(value = "refNo", required = true) String refNo,
			@RequestParam(value = "ivDate", required = true) String ivDate) {
		logger.info("param refNo = {}", refNo);
		DateFormat df = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
		Date newIvDate = null;
		try {
			newIvDate = df.parse(ivDate);
		} catch (java.text.ParseException e) {
			logger.error(e.getMessage());
		}
		WfwInboxMstr wfwInboxMstr = getCommonService().findWfInboxMstrByRefNo(refNo);
		if (!BaseUtil.isObjNull(wfwInboxMstr)) {
			wfwInboxMstr.setInterviewDate(newIvDate);
			boolean isupdateCompleted = getCommonService().editWfInboxMstr(wfwInboxMstr);
			logger.info("update paymentType = {}", isupdateCompleted);
			return true;
		}

		return false;
	}

}