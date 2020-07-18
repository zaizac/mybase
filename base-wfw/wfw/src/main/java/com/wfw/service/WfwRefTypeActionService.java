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
import com.wfw.dao.WfwRefTypeActionDao;
import com.wfw.model.WfwRefTypeAction;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_REF_TYPE_ACTION_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_TYPE_ACTION_SVC)
public class WfwRefTypeActionService extends AbstractService<WfwRefTypeAction> {

	@Autowired
	private WfwRefTypeActionDao wfwRefTypeActionDao;

	@Autowired
	private WfwRefCustomDao refCustomDao;


	@Override
	public GenericDao<WfwRefTypeAction> primaryDao() {
		return wfwRefTypeActionDao;
	}


	@SuppressWarnings("rawtypes")
	public List<WfwRefTypeAction> findTypeActionList(RefTypeAction dto, DataTableRequest dataTableInRQ) {
		return refCustomDao.searchWfwTypeAction(dto, dataTableInRQ);
	}


	public WfwRefTypeAction addUpdate(RefTypeAction dto, String userId) {

		WfwRefTypeAction refTypeAction = null;

		if (CmnUtil.isObjNull(dto.getTypeActionId())) {
			refTypeAction = new WfwRefTypeAction();
			refTypeAction.setLevelId(dto.getLevelId());
			refTypeAction.setTypeId(dto.getTypeId());
			refTypeAction.setCreateId(userId);
			refTypeAction.setCreateDt(DateUtil.getSQLTimestamp());
		} else {
			refTypeAction = find(dto.getTypeActionId());
		}

		refTypeAction.setActionName(dto.getActionName());
		refTypeAction.setRedirectPath(dto.getRedirectPath());
		refTypeAction.setStatus(dto.getStatus());
		refTypeAction.setUpdateId(userId);
		refTypeAction.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refTypeAction);
	}
}
