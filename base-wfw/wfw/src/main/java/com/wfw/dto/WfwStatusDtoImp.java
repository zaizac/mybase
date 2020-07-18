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

import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwStatusDao;
import com.wfw.model.WfwStatus;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_STATUS_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_STATUS_DTO)
public class WfwStatusDtoImp extends AbstractService<WfwStatus> implements WfwStatusDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwStatusDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_STATUS_DAO)
	private WfwStatusDao wfwStatusDao;


	@Override
	public List<WfwStatus> findWfStatusByDescription(String description) {
		return wfwStatusDao.findWfStatusByDescrition(description);
	}


	@Override
	public List<WfwStatus> findAllWfStatus() {
		return wfwStatusDao.findAll();
	}


	@Override
	public WfwStatus findStatusByStatusId(String statusId) {
		return wfwStatusDao.findWfStatusById(statusId);
	}


	@Override
	public List<WfwStatus> findWfStatusByLevelId(String levelId) {
		return wfwStatusDao.findWfStatusByLevelId(levelId);
	}


	@Override
	public List<WfwStatus> findAllWfStatusByLevelId(String levelId) {
		return wfwStatusDao.findAllWfStatusByLevelId(levelId);
	}


	@Override
	public List<WfwStatus> findWfStatusByLevelIdDisplayYes(String levelId) {
		return wfwStatusDao.findWfStatusByLevelIdDisplayYes(levelId);
	}


	@Override
	public WfwStatus findFirstStatusByLevelId(String levelId) {
		List<WfwStatus> statusList = wfwStatusDao.findWfStatusByLevelId(levelId);
		if (!CmnUtil.isListNull(statusList)) {
			return statusList.get(0);
		} else {
			return null;
		}
	}


	@Override
	public WfwStatus findWfStatusByLevelIdAppStatus(String levelId, String appStatus) {
		List<WfwStatus> statusList = wfwStatusDao.findWfStatusByLevelIdAppStatus(levelId, appStatus);
		if (!CmnUtil.isListNull(statusList)) {
			return statusList.get(0);
		} else {
			return null;
		}
	}


	@Override
	public GenericDao<WfwStatus> primaryDao() {
		return wfwStatusDao;
	}


	@Override
	public boolean addWfStatus(WfwStatus wfwStatus) {
		try {
			create(wfwStatus);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfStatus: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfStatus(WfwStatus wfwStatus) {
		try {
			update(wfwStatus);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfStatus: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfStatus(WfwStatus wfwStatus) {
		try {
			this.delete(wfwStatus);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfStatus: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfStatusById(Integer id) {
		try {
			this.delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfStatusById: {}", e.getMessage());
			return false;
		}
	}

}
