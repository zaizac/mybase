/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.util.BaseUtil;
import com.util.pagination.DataTableRequest;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwTaskCustomDao;
import com.wfw.dao.WfwTaskHistoryDao;
import com.wfw.model.WfwTaskHistory;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.SeqGen;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_TASK_HISTORY_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_TASK_HISTORY_DAO)
public class WfwTaskHistoryService extends AbstractService<WfwTaskHistory> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwTaskHistoryService.class);

	@Autowired
	private WfwTaskHistoryDao wfwTaskHistoryDao;

	@Autowired
	private WfwTaskCustomDao wfwTaskCustomDao;


	@Override
	public GenericDao<WfwTaskHistory> primaryDao() {
		return wfwTaskHistoryDao;
	}


	public List<TaskHistory> getHistoryByTaskMasterId(String taskMasterId) {

		List<TaskHistory> taskHistList = new ArrayList<>();
		List<WfwTaskHistory> wfwTaskHistList = wfwTaskHistoryDao.findByTaskMasterId(taskMasterId);

		for (WfwTaskHistory wfwTaskHist : wfwTaskHistList) {
			taskHistList.add(dozerMapper.map(wfwTaskHist, TaskHistory.class));
		}

		return taskHistList;
	}


	public List<String> getTaskHistoryMasterIdBySubmitBy(String submitBy) {
		return wfwTaskHistoryDao.findTaskMasterIdBySubmitBy(submitBy);
	}


	public List<WfwTaskHistory> getWfwTaskHistory(WfwRefPayload payload, DataTableRequest<TaskHistory> dataTableInRQ) {
		return wfwTaskCustomDao.searchQueryWfwTaskHistory(payload, dataTableInRQ);
	}


	public List<TaskHistory> getTaskHistory(WfwRefPayload payload, DataTableRequest<TaskHistory> dataTableInRQ) {

		List<TaskHistory> taskHistList = new ArrayList<>();
		List<WfwTaskHistory> wfwTaskHistList = wfwTaskCustomDao.searchQueryWfwTaskHistory(payload, dataTableInRQ);

		for (WfwTaskHistory wfwTaskHist : wfwTaskHistList) {
			taskHistList.add(dozerMapper.map(wfwTaskHist, TaskHistory.class));
		}

		return taskHistList;
	}


	public WfwTaskHistory createUpdate(TaskHistory dto, String action, boolean display, String authorId,
			String authorName) {

		WfwTaskHistory wfwTaskHistory = wfwTaskHistoryDao.findByTaskHistoryId(dto.getTaskHistoryId());

		if (BaseUtil.isObjNull(wfwTaskHistory)) {
			wfwTaskHistory = dozerMapper.map(dto, WfwTaskHistory.class);
			wfwTaskHistory.setTaskHistoryId(SeqGen.getSequenceNo());
			wfwTaskHistory.setDisplay(display);
			wfwTaskHistory.setAction(action);
			wfwTaskHistory.setSubmitBy(authorId);
			wfwTaskHistory.setSubmitByName(authorName);
			wfwTaskHistory.setRemark(dto.getRemark());
			wfwTaskHistory.setCreateId(authorId);
			wfwTaskHistory.setCreateDt(CmnUtil.getCurrentDateSQL());
		}

		wfwTaskHistory.setUpdateId(authorId);
		wfwTaskHistory.setUpdateDt(CmnUtil.getCurrentDateSQL());
		return update(wfwTaskHistory);
	}

}
