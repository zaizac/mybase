/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.WfwPayload;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@Service
@Qualifier(QualifierConstants.WF_NQF_DAO)
public interface JpqlQueryFactory {

	public List<WfwInboxMstr> buildQueryTaskListInQueueByModRole(String roleId, String modId, String refNo);


	public List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String modId, String refNo);


	public boolean deleteWfInboxByTaskId(String taskId);


	public boolean deleteWfInboxMstrByTaskId(String taskId);


	public boolean deleteWfInboxMstrByRefNo(String refNo);


	public boolean deleteWfInboxByInboxId(String inboxId);


	public boolean deleteWfAsgnRoleByLevelId(String levelId);


	public String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId);


	public List<Object[]> findHistoryByTaskId(String taskId);


	public List<WfwInboxMstr> buildSearchQueryTaskListInQueue(WfwPayload wfwPayload);


	public List<WfwInboxMstr> buildSearchQueryTaskListInQueueAll(WfwPayload wfwPayload);


	public List<WfwInboxMstr> buildSearchQueryTaskListInInbox(WfwPayload wfwPayload);


	public List<WfwInboxMstr> buildSearchQueryTaskListInboxGroupAdmin(WfwPayload wfwPayload);


	public List<WfwInboxHist> buildSearchQueryTaskHistoryList(WfwPayload wfwPayload);


	// return all record with user action and last updated date less than or
	// equal actionDate
	public List<WfwInboxMstr> buildQueryByUserAction(String userAction, Date actionDateFrom, Date actionDateTo,
			String modules, String appStatuses);


	public List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo,
			String sectorAgency);


	// pagination
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListTableInQueue(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListInQueueAllTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ, String updateId);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListTableInInbox(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListInboxGroupAdminTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);
}
