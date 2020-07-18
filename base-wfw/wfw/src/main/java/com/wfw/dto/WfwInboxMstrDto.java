/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.WfwPayload;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_MSTR_DTO)
@EnableTransactionManagement
public interface WfwInboxMstrDto {

	public WfwInboxMstr findWfInboxMstrByTaskId(String taskId);


	public List<WfwInboxMstr> findAllWfInboxMstr();


	public WfwInboxMstr findWfInboxMstrByRefNo(String refNo);


	public void addWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	public void editWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	public void deleteWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	public void deleteWfInboxMstr(Integer taskId);


	public List<WfwInboxMstr> searchWfInboxMstr(WfwInboxMstr wfwInboxMstr);


	public List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId, String refNo);


	public List<WfwInboxMstr> searchTaskListInQueue(WfwPayload wfwPayload);


	public List<WfwInboxMstr> searchTaskListInQueueAll(WfwPayload wfwPayload);


	public List<WfwInboxMstr> searchTaskListInInbox(WfwPayload wfwPayload);


	public List<WfwInboxMstr> searchTaskListInInboxGroupAdmin(WfwPayload wfwPayload);


	public List<WfwInboxHist> searchTaskHistoryList(WfwPayload wfwPayload);


	public List<WfwInboxMstr> searchByUserActionUpdateDateStatus(String userAction, Date actionDateFrom,
			Date actionDateTo, String modules, String appStatuses);


	public boolean deleteWfwInboxMstrByTaskId(String taskId);


	public boolean deleteWfwInboxMstrByRefNo(String refNo);


	public List<WfwInboxMstr> draftApplicationsList(String draftStatus, Date durationDates);


	public List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo,
			String sectorAgency);


	// Pagination
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInQueueTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInQueueAllTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ, String updateId);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInInboxTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);


	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInInboxGroupAdminTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ);
}
