/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.util.DateUtil;
import com.util.pagination.DataTableRequest;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwRefCustomDao;
import com.wfw.dao.WfwRefStatusDao;
import com.wfw.model.WfwRefStatus;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_REF_STATUS_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_STATUS_SVC)
public class WfwRefStatusService extends AbstractService<WfwRefStatus> {

	@Autowired
	private WfwRefStatusDao wfwRefStatusDao;

	@Autowired
	private WfwRefCustomDao wfwRefCustomDao;


	@Override
	public GenericDao<WfwRefStatus> primaryDao() {
		return wfwRefStatusDao;
	}


	public List<WfwRefStatus> findAllByLevelId(Integer levelId) {
		return wfwRefStatusDao.findAllByLevelId(levelId);
	}


	public List<WfwRefStatus> findActiveListByLevelId(Integer levelId) {
		return wfwRefStatusDao.findActiveByLevelId(levelId);
	}


	public WfwRefStatus findByLevelIdStatusCd(Integer levelId, String statusCd) {
		return wfwRefStatusDao.findByLevelIdStatusCd(levelId, statusCd);
	}


	public Integer getLastStatusSeq(Integer levelId) {
		Integer statusSeq = wfwRefStatusDao.findMaxStatusSequence(levelId);
		return !CmnUtil.isObjNull(statusSeq) ? statusSeq : CmnConstants.ZERO;
	}


	public List<WfwRefStatus> findWfwRefStatusList(RefStatus status, DataTableRequest<RefStatus> dataTableInRQ) {
		return wfwRefCustomDao.searchWfwStatus(status, dataTableInRQ);
	}


	public List<RefStatus> findRefStatusList(RefStatus status, DataTableRequest<RefStatus> dataTableInRQ) {

		List<RefStatus> refStatuList = new ArrayList<>();
		for (WfwRefStatus wfwRefStatus : wfwRefCustomDao.searchWfwStatus(status, dataTableInRQ)) {
			refStatuList.add(dozerMapper.map(wfwRefStatus, RefStatus.class));
		}
		return refStatuList;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwRefStatus createUpdate(RefStatus dto) {

		WfwRefStatus refStatus = new WfwRefStatus();

		if (CmnUtil.isObjNull(dto.getStatusId())) {
			refStatus.setTypeId(dto.getTypeId());
			refStatus.setLevelId(dto.getLevelId());
			refStatus.setStatusSequence(getLastStatusSeq(dto.getLevelId()) + CmnConstants.ONE);
			refStatus.setCreateId(dto.getUserId());
			refStatus.setCreateDt(DateUtil.getSQLTimestamp());
		} else {
			refStatus = find(dto.getStatusId());
			refStatus.setStatusSequence(dto.getStatusSequence());
		}

		refStatus.setStatusCd(dto.getStatusCd());
		refStatus.setStatusDesc(dto.getStatusDesc());
		refStatus.setNextLevelId(CmnUtil.isObjNull(dto.getNextLevelId()) ? CmnConstants.ZERO : dto.getNextLevelId());
		refStatus.setDisplay(dto.getDisplay());
		refStatus.setInitialState(dto.getInitialState());
		refStatus.setSkipApprover(dto.getSkipApprover());
		refStatus.setNoRelease(CmnUtil.isObjNull(dto.getNoRelease()) ? CmnConstants.ZERO : dto.getNoRelease());
		refStatus.setEmailRequired(dto.getEmailRequired());
		refStatus.setLegendColor(dto.getLegendColor());
		refStatus.setStatus(dto.getStatus());
		refStatus.setUpdateId(dto.getUserId());
		refStatus.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refStatus);
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwRefStatus createInitialStatus(Integer typeId, Integer levelId, String userId) {

		WfwRefStatus refStatus = new WfwRefStatus();
		refStatus.setTypeId(typeId);
		refStatus.setLevelId(levelId);
		refStatus.setStatusSequence(CmnConstants.ONE);
		refStatus.setCreateId(userId);
		refStatus.setCreateDt(DateUtil.getSQLTimestamp());
		refStatus.setStatusCd(CmnConstants.NEW);
		refStatus.setStatusDesc(CmnConstants.NEW);
		refStatus.setNextLevelId(CmnConstants.ZERO);
		refStatus.setDisplay(CmnConstants.ZERO);
		refStatus.setInitialState(CmnConstants.ONE);
		refStatus.setSkipApprover(CmnConstants.ZERO);
		refStatus.setNoRelease(CmnConstants.ZERO);
		refStatus.setEmailRequired(CmnConstants.ZERO);
		refStatus.setLegendColor(CmnConstants.LEGEND_COLOR);
		refStatus.setStatus(CmnConstants.ONE);
		refStatus.setUpdateId(userId);
		refStatus.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refStatus);
	}
}
