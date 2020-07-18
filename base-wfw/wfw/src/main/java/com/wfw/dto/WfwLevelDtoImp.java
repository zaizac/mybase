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
import com.wfw.dao.WfwLevelDao;
import com.wfw.model.WfwLevel;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_LEVEL_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_LEVEL_DTO)
public class WfwLevelDtoImp extends AbstractService<WfwLevel> implements WfwLevelDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwLevelDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_LEVEL_DAO)
	private WfwLevelDao wfwLevelDao;


	@Override
	public List<WfwLevel> findWfLevelByDescription(String description) {
		return wfwLevelDao.findWfLevelByDescrition(description);
	}


	@Override
	public List<WfwLevel> findAllWfLevel() {
		return wfwLevelDao.findAll();
	}


	@Override
	public WfwLevel findLevelByLevelId(String levelId) {
		return wfwLevelDao.findWfLevelById(levelId);
	}


	@Override
	public List<WfwLevel> findWfLevelByTypeId(String typeId) {
		return wfwLevelDao.findWfLevelByTypeId(typeId);
	}


	@Override
	public GenericDao<WfwLevel> primaryDao() {
		return wfwLevelDao;
	}


	@Override
	public boolean addWfLevel(WfwLevel wfwLevel) {
		try {
			create(wfwLevel);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfLevel: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfLevel(WfwLevel wfwLevel) {
		try {
			update(wfwLevel);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfLevel: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfLevel(WfwLevel wfwLevel) {
		try {
			this.delete(wfwLevel);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfLevel: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfLevel(Integer id) {
		try {
			this.delete(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfLevel: {}", e.getMessage());
			return false;
		}
	}

}
