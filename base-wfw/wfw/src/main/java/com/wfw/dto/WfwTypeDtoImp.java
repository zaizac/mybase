/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwTypeDao;
import com.wfw.model.WfwType;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_TYPE_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_TYPE_DTO)
public class WfwTypeDtoImp extends AbstractService<WfwType> implements WfwTypeDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwTypeDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_TYPE_DAO)
	private WfwTypeDao wfwTypeDao;


	@Override
	@Transactional(readOnly = true)
	public List<WfwType> findByWfwTypeByDescription(String description) {
		LOGGER.info("roleCode = {}", description);
		LOGGER.info("roleDao = {}", CmnUtil.isObjNull(description));
		return wfwTypeDao.findTypeByDescription(description);
	}


	@Override
	@Transactional(readOnly = true)
	public List<WfwType> findAllWfType() {
		return wfwTypeDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public WfwType findByWfTypeId(String typeId) {
		return wfwTypeDao.findTypeById(typeId);
	}


	@Override
	public GenericDao<WfwType> primaryDao() {
		return wfwTypeDao;
	}


	@Override
	public boolean addWfType(WfwType wfwType) {
		try {
			create(wfwType);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfType: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfType(WfwType wfwType) {
		try {
			update(wfwType);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfType: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfType(WfwType wfwType) {
		try {
			this.delete(wfwType);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfType: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfType(Integer typeId) {
		try {
			this.delete(typeId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfType: {}", e.getMessage());
			return false;
		}
	}
}
