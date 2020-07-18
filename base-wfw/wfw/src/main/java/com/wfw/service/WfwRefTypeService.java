/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.service;


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
import com.wfw.dao.WfwRefTypeDao;
import com.wfw.model.WfwRefType;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_REF_TYPE_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_TYPE_SVC)
public class WfwRefTypeService extends AbstractService<WfwRefType> {

	@Autowired
	private WfwRefTypeDao wfwRefTypeDao;

	@Autowired
	private WfwRefCustomDao wfwRefCustomDao;


	@Override
	public GenericDao<WfwRefType> primaryDao() {
		return wfwRefTypeDao;
	}


	public List<WfwRefType> findAll() {
		return wfwRefTypeDao.findAll();
	}


	public List<WfwRefType> getRefTypeList(WfwReference dto, DataTableRequest<WfwReference> dataTableInRQ) {
		return wfwRefCustomDao.searchWfwType(dto, dataTableInRQ);
	}


	public List<WfwRefType> getActiveRefTypeList(WfwReference dto) {
		dto.setStatus(CmnConstants.ONE);
		return wfwRefCustomDao.searchWfwType(dto, null);
	}


	public List<WfwRefType> getActiveRefTypeList(WfwRefPayload dto) {
		WfwReference ref = dozerMapper.map(dto, WfwReference.class);
		ref.setStatus(CmnConstants.ONE);
		return wfwRefCustomDao.searchWfwType(ref, null);
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwRefType createUpdate(WfwReference dto) {

		WfwRefType refType = new WfwRefType();
		if (CmnUtil.isObjNull(dto.getTypeId())) {
			refType.setCreateId(dto.getUserId());
			refType.setCreateDt(DateUtil.getSQLTimestamp());
		} else {
			refType = find(dto.getTypeId());
			refType.setTypeId(dto.getTypeId());
		}
		refType.setTranId(dto.getTranId());
		refType.setModuleId(dto.getModuleId());
		refType.setRedirectPath(dto.getRedirectPath());
		refType.setDescription(dto.getDescription());
		refType.setAutoClaim(dto.getAutoClaim());
		refType.setStatus(dto.getStatus());
		refType.setUpdateId(dto.getUserId());
		refType.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refType);
	}
}
