/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.JpqlQueryFactory;
import com.wfw.dao.SearchDao;
import com.wfw.dao.WfwInboxHistDao;
import com.wfw.dao.WfwInboxMstrDao;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.WfwPayload;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_INBOX_MSTR_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_MSTR_DTO)
public class WfwInboxMstrDtoImp extends AbstractService<WfwInboxMstr> implements WfwInboxMstrDto {

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_MSTR_DAO)
	private WfwInboxMstrDao wfwInboxMstrDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_HIST_DAO)
	private WfwInboxHistDao wfwInboxHistDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_SEARCH_DAO)
	private SearchDao wfSearchDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_JPQLQF_DAO)
	private JpqlQueryFactory jpqlQueryFactoryDao;


	@Override
	@Transactional(readOnly = true)
	public WfwInboxMstr findWfInboxMstrByRefNo(String refNo) {
		return wfwInboxMstrDao.findWfInboxMstrByRefNo(refNo);
	}


	@Override
	@Transactional(readOnly = true)
	public List<WfwInboxMstr> findAllWfInboxMstr() {
		return wfwInboxMstrDao.findAll();
	}


	@Transactional(readOnly = true)
	public WfwInboxMstr findWfWfInboxMstrByTaskId(Integer taskId) {
		return find(taskId);
	}


	@Override
	public GenericDao<WfwInboxMstr> primaryDao() {
		return wfwInboxMstrDao;
	}


	@Override
	public void addWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		create(wfwInboxMstr);
	}


	@Override
	public void editWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		update(wfwInboxMstr);
	}


	@Override
	public void deleteWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		this.delete(wfwInboxMstr);
	}


	@Override
	public void deleteWfInboxMstr(Integer taskId) {
		this.delete(taskId);
	}


	@Override
	public WfwInboxMstr findWfInboxMstrByTaskId(String taskId) {
		return wfwInboxMstrDao.findWfInboxMstrByTaskId(taskId);
	}


	@Override
	public List<WfwInboxMstr> searchWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		return wfwInboxMstrDao.findAll(wfSearchDao.searchWfInBoxMstr(wfwInboxMstr));
	}


	@Override
	public List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId, String refNo) {
		return jpqlQueryFactoryDao.buildQueryTaskListInQueueByModRole(roleId, modId, refNo);
	}


	@Override
	public List<WfwInboxMstr> searchTaskListInQueue(WfwPayload wfwPayload) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInQueue(wfwPayload);
	}


	@Override
	public List<WfwInboxMstr> searchTaskListInQueueAll(WfwPayload wfwPayload) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInQueueAll(wfwPayload);
	}


	@Override
	public List<WfwInboxMstr> searchTaskListInInbox(WfwPayload wfwPayload) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInInbox(wfwPayload);
	}


	@Override
	public List<WfwInboxMstr> searchTaskListInInboxGroupAdmin(WfwPayload wfwPayload) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInboxGroupAdmin(wfwPayload);
	}


	@Override
	public List<WfwInboxHist> searchTaskHistoryList(WfwPayload wfwPayload) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskHistoryList(wfwPayload);
	}


	@Override
	public List<WfwInboxMstr> searchByUserActionUpdateDateStatus(String userAction, Date actionDateFrom,
			Date actionDateTo, String modules, String appStatuses) {
		return jpqlQueryFactoryDao.buildQueryByUserAction(userAction, actionDateFrom, actionDateTo, modules,
				appStatuses);
	}


	@Override
	public boolean deleteWfwInboxMstrByTaskId(String taskId) {
		return jpqlQueryFactoryDao.deleteWfInboxMstrByTaskId(taskId);
	}


	@Override
	public boolean deleteWfwInboxMstrByRefNo(String refNo) {
		return jpqlQueryFactoryDao.deleteWfInboxMstrByRefNo(refNo);
	}


	@Override
	public List<WfwInboxMstr> draftApplicationsList(String draftStatus, Date durationDates) {
		return wfwInboxMstrDao.findDraftApplicationList(draftStatus, durationDates);
	}


	@Override
	public List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo,
			String sectorAgency) {
		return jpqlQueryFactoryDao.findInterviewForEnquiry(interviewDateFrom, interviewDateTo, sectorAgency);
	}


	// Pagination
	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInQueueTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListTableInQueue(wfwPayload, dataTableInRQ);
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInQueueAllTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ, String updateId) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInQueueAllTable(wfwPayload, dataTableInRQ, updateId);
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInInboxTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListTableInInbox(wfwPayload, dataTableInRQ);
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> searchTaskListInInboxGroupAdminTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {
		return jpqlQueryFactoryDao.buildSearchQueryTaskListInboxGroupAdminTable(wfwPayload, dataTableInRQ);
	}

}
