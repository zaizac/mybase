/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;
import com.wfw.model.WfwAsgnTran;
import com.wfw.model.WfwInbox;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.model.WfwLevel;
import com.wfw.model.WfwStatus;
import com.wfw.model.WfwType;
import com.wfw.model.WfwUser;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.TaskDetails;
import com.wfw.sdk.model.TaskRemark;
import com.wfw.sdk.model.TaskStatus;
import com.wfw.sdk.model.WfwPayload;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Component
public interface CmnDto {

	WfwAsgnRole findWfAsgnRoleById(WfwAsgnRolePk wfwAsgnRolePk);


	// WfAsgnRole

	List<WfwAsgnRole> findWfAsgnRoleByRoleId(String roleId);


	List<WfwAsgnRole> findWfAsgnRoleByLevelId(String levelId);


	List<WfwAsgnRole> findAllWfAsgnRole();


	boolean addWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	boolean addWfAsgnRole(List<WfwAsgnRole> wfwAsgnRoleList);


	boolean editWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	boolean deleteWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	boolean deleteWfAsgnRoleByLevelId(String levelId);


	// WfAsgnTran

	WfwAsgnTran findAsgnTranByTranId(String tranId);


	List<WfwAsgnTran> findAllWfAsgnTran();


	WfwAsgnTran findWfAsgnTranByTypeId(String typeId);


	List<WfwAsgnTran> findWfAsgnTranByModuleId(String moduleId);


	boolean addWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	boolean editWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	boolean deleteWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	boolean deleteWfAsgnTran(Integer tranId);


	// WfInbox

	WfwInbox findWfInboxByTaskId(String taskId);


	WfwInbox findWfInboxByTaskIdAndOfficerId(String taskId, String officerId);


	WfwInbox findWfInboxByRefNoAndOfficerId(String refNo, String officerId);


	List<WfwInbox> findAllWfInbox();


	List<WfwInbox> findWfInboxByRefNo(String refNo);


	boolean addWfInbox(WfwInbox wfwInbox);


	boolean editWfInbox(WfwInbox wfwInbox);


	boolean deleteWfInbox(WfwInbox wfwInbox);


	boolean deleteWfInbox(String taskId);


	List<WfwInbox> searchWfInbox(WfwInbox wfwInbox);


	List<WfwInboxMstr> findInboxByOfficerIdModId(String officerId, String moduleId);


	List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String moduleId, String refNo);


	List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId, String refNo);


	String findOfficerIdWithMinimumWorkLoad(String levelId);


	String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId);


	boolean releaseTask(String taskId, String actionDoneBy, String userType);


	// WfInboxHist

	WfwInboxHist findWfInboxHistByHistoryId(String historyId);


	List<WfwInboxHist> findWfInboxHistByTaskId(String taskId);


	List<WfwInboxHist> findAllWfInboxHist();


	List<WfwInboxHist> findWfInboxHistByRefNo(String refNo);


	boolean addWfInboxHist(WfwInboxHist wfwInboxHist);


	boolean editWfInboxHist(WfwInboxHist wfwInboxHist);


	boolean deleteWfInboxHist(WfwInboxHist wfwInboxHist);


	boolean deleteWfInboxHist(Integer taskId);


	// WfInboxMstr

	WfwInboxMstr findWfInboxMstrByTaskId(String taskId);


	List<WfwInboxMstr> findAllWfInboxMstr();


	WfwInboxMstr findWfInboxMstrByRefNo(String refNo);


	boolean addWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	boolean addWfInboxMstrAndHistory(WfwInboxMstr wfwInboxMstr);


	boolean editWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	boolean deleteWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	boolean deleteWfInboxMstr(Integer taskId);


	List<WfwInboxMstr> searchWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	boolean crudWfTask(WfwInboxMstr wfwInboxMstr, WfwInbox wfwInbox, WfwInboxHist wfwInboxHist, String oparationCode,
			String actionDoneBy);


	List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId);


	boolean claimTask(List<String> taskIdList, String officerId, String officerName, String userType);


	boolean updateWfInboxMstrByWfAdmin(WfwInboxMstr wfwInboxMstr, String actionDoneBy);


	// WfLevel

	WfwLevel findLevelByLevelId(String levelId);


	List<WfwLevel> findAllWfLevel();


	List<WfwLevel> findWfLevelByDescription(String description);


	List<WfwLevel> findWfLevelByTypeId(String typeId);


	boolean addWfLevel(WfwLevel wfwLevel);


	boolean editWfLevel(WfwLevel wfwLevel);


	boolean deleteWfLevel(WfwLevel wfwLevel);


	boolean deleteWfLevel(Integer statusId);


	// WfTyple

	WfwType findWfTypeById(String typeId);


	List<WfwType> findAllWfType();


	List<WfwType> findAllTWfTypeByDecription(String description);


	boolean addWfType(WfwType wfwType);


	boolean editWfType(WfwType wfwType);


	boolean deleteWfType(WfwType wfwType);


	boolean deleteWfType(Integer typeId);


	List<WfwType> generateTypeList();


	List<WfwType> generateTypeList(String typeId);


	// WfStatus

	WfwStatus findStatusByStatusId(String statusId);


	List<WfwStatus> findAllWfStatus();


	List<WfwStatus> findWfStatusByDescription(String description);


	List<WfwStatus> findWfStatusByLevelId(String levelId);


	List<WfwStatus> findAllWfStatusByLevelId(String levelId);


	boolean addWfStatus(WfwStatus wfwStatus);


	boolean editWfStatus(WfwStatus wfwStatus);


	boolean deleteWfStatus(WfwStatus wfwStatus);


	boolean deleteWfStatusByStatusId(Integer statusId);


	// WfUser

	WfwUser findUserByUserId(Integer userId);


	List<WfwUser> findAllWfUser();


	WfwUser findWfUserByUsernamePassword(String username, String password);


	WfwUser findWfUserByUsername(String username);


	boolean addWfUser(WfwUser wfwUser);


	boolean editWfUser(WfwUser wfwUser);


	boolean deleteWfUser(WfwUser wfwUser);


	boolean deleteWfUser(Integer userId);


	// method related to service

	List<TaskDetails> taskList(WfwPayload wfwPayload);


	List<TaskDetails> applicationList(WfwPayload wfwPayload);


	List<TaskDetails> claimTaskList(WfwPayload wfwPayload);


	List<TaskDetails> claimTaskListGroupAdmin(WfwPayload wfwPayload);


	List<TaskRemark> taskRemarkList(WfwPayload wfwPayload, String range);


	List<TaskDetails> appListbyUserActionStatus(String userAction, Date actionDateFrom, Date actionDateTo,
			String modules, String appStatuses);


	List<TaskDetails> draftApplicationList(String draftStatus, Date durationDates);


	TaskDetails taskDetails(String taskId);


	// Claimed Date
	TaskDetails taskDetail(String taskId);


	TaskDetails taskDetail1(String taskId);


	List<TaskStatus> statusList(String taskId);


	// pagination
	@SuppressWarnings("rawtypes")
	DataTableResults<TaskDetails> taskListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	DataTableResults<TaskDetails> applicationListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ,
			String updateId);


	@SuppressWarnings("rawtypes")
	DataTableResults<TaskDetails> claimTaskListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	DataTableResults<TaskDetails> claimTaskListGroupAdminTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ);


	boolean claimTasks(WfwPayload wfwPayload);


	boolean realeaseTasks(WfwPayload wfwPayload);


	boolean releaseTaskGroupAdmin(WfwPayload wfwPayload);


	boolean startTask(TaskDetails payload);


	boolean startTask(TaskDetails payload, List<TaskDetails> newTaskList);


	boolean amendTask(TaskDetails payload);


	boolean completeTask(TaskDetails payload);


	boolean completeAndStartTask(TaskDetails payload);


	boolean updateTask(TaskDetails payload);


	boolean updateTaskKDNVerifier(TaskDetails payload);


	boolean updateTask(TaskDetails payload, boolean isCheckInbox);


	boolean updateTaskForEpay(TaskDetails payload);


	boolean rejectTask(TaskDetails payload);


	int claimRandomTask(WfwPayload payload);


	List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo, String sectorAgency);


	boolean updateWfTaskId(WfwPayload wfwPayload);
}
