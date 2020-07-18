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
import com.wfw.dao.JpqlQueryFactory;
import com.wfw.dao.WfwAsgnRoleDao;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_ASGN_ROLE_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_ROLE_DTO)
public class WfwAsgnRoleDtoImp extends AbstractService<WfwAsgnRole> implements WfwAsgnRoleDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwAsgnRoleDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_ASGN_ROLE_DAO)
	private WfwAsgnRoleDao wfwAsgnRoleDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_JPQLQF_DAO)
	private JpqlQueryFactory jpqlQueryFactoryDao;


	@Override
	public WfwAsgnRole findWfAsgnRoleById(WfwAsgnRolePk wfwAsgnRolePk) {
		return wfwAsgnRoleDao.findWfUserById(wfwAsgnRolePk);
	}


	@Override
	@Transactional(readOnly = true)
	public List<WfwAsgnRole> findWfAsgnRoleByRoleId(String roleId) {
		return wfwAsgnRoleDao.findWfAsgnRoleByRoleId(roleId);
	}


	@Transactional(readOnly = true)
	@Override
	public List<WfwAsgnRole> findAllWfAsgnRole() {
		return wfwAsgnRoleDao.findAll();
	}


	@Transactional(readOnly = true)
	@Override
	public List<WfwAsgnRole> findWfAsgnRoleByLevelId(String levelId) {
		return wfwAsgnRoleDao.findWfAsgnRoleByLevelId(levelId);
	}


	@Override
	public GenericDao<WfwAsgnRole> primaryDao() {
		return wfwAsgnRoleDao;
	}


	@Override
	public boolean addWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		try {
			create(wfwAsgnRole);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfAsgnRole: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean addWfAsgnRole(List<WfwAsgnRole> wfwAsgnRoleList) {
		try {
			for (WfwAsgnRole wfwAsgnRole : wfwAsgnRoleList) {
				create(wfwAsgnRole);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfAsgnRole: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		try {
			update(wfwAsgnRole);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfAsgnRole: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		try {
			this.delete(wfwAsgnRole);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfAsgnRole: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfAsgnRoleByLevelId(String levelId) {
		try {
			jpqlQueryFactoryDao.deleteWfAsgnRoleByLevelId(levelId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfAsgnRoleByLevelId: {}", e.getMessage());
			return false;
		}
	}

}
