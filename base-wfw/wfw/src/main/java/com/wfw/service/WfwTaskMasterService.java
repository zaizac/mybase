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
import com.wfw.dao.WfwRefLevelRoleDao;
import com.wfw.dao.WfwTaskCustomDao;
import com.wfw.dao.WfwTaskMasterDao;
import com.wfw.model.WfwTaskMaster;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.SeqGen;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_TASK_MASTER_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_TASK_MASTER_SVC)
public class WfwTaskMasterService extends AbstractService<WfwTaskMaster> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwTaskHistoryService.class);

	@Autowired
	private WfwTaskMasterDao wfwTaskMasterDao;

	@Autowired
	private WfwTaskCustomDao wfwTaskCustomDao;

	@Autowired
	private WfwRefLevelRoleDao wfwRefLevelRoleDao;

	@Autowired
	private WfwTaskHistoryService wfwTaskHistorySvc;

	@Autowired
	private WfwRefLevelService wfwRefLevelSvc;


	@Override
	public GenericDao<WfwTaskMaster> primaryDao() {
		return wfwTaskMasterDao;
	}


	public WfwTaskMaster createUpdate(WfwRefPayload dto, WfwTaskMaster wfwTaskMaster) {
		wfwTaskMaster = setWfwTaskMaster(dto, wfwTaskMaster);
		return update(wfwTaskMaster);
	}


	public WfwTaskMaster setWfwTaskMaster(WfwRefPayload dto, WfwTaskMaster wfwTaskMaster) {

		if (BaseUtil.isObjNull(wfwTaskMaster)) {
			wfwTaskMaster = dozerMapper.map(dto, WfwTaskMaster.class);
			wfwTaskMaster.setTaskMasterId(SeqGen.getSequenceNo());
			wfwTaskMaster.setAppDate(CmnUtil.getCurrentDateSQL());
			wfwTaskMaster.setSubmitBy(dto.getTaskAuthorId());
			wfwTaskMaster.setSubmitByName(dto.getTaskAuthorName());
			wfwTaskMaster.setCreateId(dto.getTaskAuthorId());
			wfwTaskMaster.setDisplay(dto.isDisplay());
			wfwTaskMaster.setRemark(dto.getRemark());
		}

		if (!CmnUtil.isObjNull(dto.getAppStatus())) {
			wfwTaskMaster.setAppStatus(dto.getAppStatus());
		}

		if (!CmnUtil.isObjNull(dto.getLevelId())) {
			wfwTaskMaster.setLevelId(dto.getLevelId());
		}

		if (!CmnUtil.isObjNull(dto.getStatusId())) {
			wfwTaskMaster.setStatusId(dto.getStatusId());
		}

		if (!CmnUtil.isObjNull(dto.getPrevStatusId())) {
			wfwTaskMaster.setPrevStatusId(dto.getPrevStatusId());
		}

		if (dto.isAutoClaim()) {
			wfwTaskMaster.setClaimBy(dto.getTaskAuthorId());
			wfwTaskMaster.setClaimByName(dto.getTaskAuthorName());
			wfwTaskMaster.setClaimDate(CmnUtil.getCurrentDateSQL());
		} else if (!dto.isAutoClaim()) {
			wfwTaskMaster.setClaimBy(null);
			wfwTaskMaster.setClaimByName(null);
			wfwTaskMaster.setClaimDate(null);
		}
		
		if(!CmnUtil.isObjNull(dto.getAdditionalInfo())) {
			wfwTaskMaster.setAdditionalInfo(dto.getAdditionalInfo());
		}

		wfwTaskMaster.setUpdateId(dto.getTaskAuthorId());
		wfwTaskMaster.setUpdateDt(CmnUtil.getCurrentDateSQL());
		return wfwTaskMaster;
	}


	public WfwTaskMaster findBytTaskMasterId(String taskMasterId) {
		return wfwTaskMasterDao.findBytTaskMasterId(taskMasterId);
	}


	public WfwTaskMaster findByApplicationRefNo(String appRefNo) {
		return wfwTaskMasterDao.findByApplicationRefNo(appRefNo);
	}


	public WfwTaskMaster findByTaskMasterIdAndClaimBy(String taskMasterId, String claimBy) {
		return wfwTaskMasterDao.findByTaskMasterIdAndClaimBy(taskMasterId, claimBy);
	}


	public TaskMaster findTaskByAppRefNo(String appRefNo) {
		WfwTaskMaster wfwTaskMaster = wfwTaskMasterDao.findByApplicationRefNo(appRefNo);
		if (CmnUtil.isObjNull(wfwTaskMaster)) {
			throw new WfwException(WfwErrorCodeEnum.I404WFW001);
		}
		return dozerMapper.map(wfwTaskMaster, TaskMaster.class);
	}


	private WfwRefPayload setPayloadParam(WfwRefPayload payload) {

		if (payload.isHistory()) {
			payload.setTaskMasterIdList(wfwTaskHistorySvc.getTaskHistoryMasterIdBySubmitBy(payload.getTaskAuthorId()));
			payload.setSubmitBy(null);
		} else {
			if (!CmnUtil.isObjNull(payload.getRoles())) {
				payload.setLevelList(wfwRefLevelRoleDao.findLevelListByRoles(payload.getRoles()));
			}
		}

		if (!CmnUtil.isObjNull(payload.getStatuses())) {
			payload.setStatusList(CmnUtil.getList(payload.getStatuses()));
		}

		return payload;
	}


	public List<TaskMaster> getTaskMasterList(WfwRefPayload payload, DataTableRequest<TaskMaster> dataTableInRQ) {

		List<TaskMaster> taskMasterList = new ArrayList<>();
		setPayloadParam(payload);

		if (payload.isHistory() && CmnUtil.isListNull(payload.getTaskMasterIdList())) {
			return taskMasterList;
		} else {
			for (WfwTaskMaster wfwTaskMstr : wfwTaskCustomDao.searchQueryWfwTaskMaster(payload, dataTableInRQ)) {

				TaskMaster taskMaster = dozerMapper.map(wfwTaskMstr, TaskMaster.class);
				taskMaster.setRefLevel(
						dozerMapper.map(wfwRefLevelSvc.findByLevelId(wfwTaskMstr.getLevelId()), RefLevel.class));

				if (payload.isHistory()) {
					payload.setTaskMasterId(wfwTaskMstr.getTaskMasterId());
					if (!CmnUtil.isEqualsCaseIgnore(wfwTaskMstr.getSubmitBy(), payload.getSubmitBy())) {
						payload.setSubmitBy(payload.getSubmitBy());
					}
					taskMaster.setTaskHistoryList(wfwTaskHistorySvc.getTaskHistory(payload, null));
				}

				taskMasterList.add(taskMaster);
			}

			return taskMasterList;
		}

	}

}
