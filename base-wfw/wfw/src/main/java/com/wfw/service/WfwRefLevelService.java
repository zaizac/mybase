/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.util.DateUtil;
import com.util.pagination.DataTableRequest;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwRefCustomDao;
import com.wfw.dao.WfwRefLevelDao;
import com.wfw.model.WfwRefLevel;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_REF_LEVEL_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_LEVEL_SVC)
public class WfwRefLevelService extends AbstractService<WfwRefLevel> {

	@Autowired
	private WfwRefLevelDao wfwRefLevelDao;

	@Autowired
	private WfwRefCustomDao wfwRefCustomDao;


	@Override
	public GenericDao<WfwRefLevel> primaryDao() {
		return wfwRefLevelDao;
	}


	public List<WfwRefLevel> findByTypeId(Integer typeId) {
		return wfwRefLevelDao.findByTypeId(typeId);
	}


	public WfwRefLevel findByLevelId(Integer typeId) {
		RefLevel refLevel = new RefLevel();
		refLevel.setLevelId(typeId);
		return wfwRefCustomDao.searchWfwLevel(refLevel, null).get(0);
	}


	public Integer getLastFlowNo(Integer typeId) {
		Integer flowNo = wfwRefLevelDao.findMaxFlowNo(typeId);
		return !CmnUtil.isObjNull(flowNo) ? flowNo : CmnConstants.ZERO;
	}


	public List<WfwRefLevel> getWfwLevelList(RefLevel dto, DataTableRequest<RefLevel> dataTableInRQ) {
		return wfwRefCustomDao.searchWfwLevel(dto, dataTableInRQ);
	}


	public List<WfwRefLevel> getActiveWfwLevelList(WfwRefPayload dto) {
		RefLevel lvl = dozerMapper.map(dto, RefLevel.class);
		lvl.setStatus(CmnConstants.ONE);
		// lvl.setRoles(CmnUtil.getList(dto.getRoles()));
		return wfwRefCustomDao.searchWfwLevel(lvl, null);
	}


	public WfwRefLevel createUpdate(RefLevel dto) {

		WfwRefLevel refLevel = new WfwRefLevel();

		if (CmnUtil.isObjNull(dto.getLevelId())) {
			refLevel.setTypeId(dto.getTypeId());
			refLevel.setFlowNo(getLastFlowNo(dto.getTypeId()) + CmnConstants.ONE);
			refLevel.setCreateId(dto.getUserId());
			refLevel.setCreateDt(DateUtil.getSQLTimestamp());
		} else {
			refLevel = find(dto.getLevelId());
			refLevel.setFlowNo(dto.getFlowNo());
		}

		refLevel.setDescription(dto.getDescription());
		refLevel.setStatus(dto.getStatus());
		refLevel.setUpdateId(dto.getUserId());
		refLevel.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refLevel);
	}
}
