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
import com.wfw.dao.WfwUserDao;
import com.wfw.model.WfwUser;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_USER_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_USER_DTO)
public class WfwUserDtoImp extends AbstractService<WfwUser> implements WfwUserDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwUserDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_USER_DAO)
	private WfwUserDao wfwUserDao;


	@Override
	public WfwUser findWfUserByUsernamePassword(String username, String password) {
		return wfwUserDao.findWfUsreByUsernamePassword(username, password);
	}


	@Override
	public WfwUser findWfUserByUsername(String username) {
		return wfwUserDao.findWfUsreByUsername(username);
	}


	@Override
	public List<WfwUser> findAllWfUser() {
		return wfwUserDao.findAll();
	}


	@Override
	public WfwUser findUserByUserId(Integer id) {
		return wfwUserDao.findWfUserById(id);
	}


	@Override
	public GenericDao<WfwUser> primaryDao() {
		return wfwUserDao;
	}


	@Override
	public boolean addWfUser(WfwUser wfwUser) {
		try {
			create(wfwUser);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfUser: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfUser(WfwUser wfwUser) {
		try {
			update(wfwUser);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfUser: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfUser(WfwUser wfwUser) {
		try {
			this.delete(wfwUser);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfUser: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfUser(Integer userId) {
		try {
			this.delete(userId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfUser: {}", e.getMessage());
			return false;
		}
	}

}
