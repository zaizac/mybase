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

import com.util.DateUtil;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwRefLevelRoleDao;
import com.wfw.model.WfwRefLevelRole;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.WFW_REF_LEVEL_ROLE_SVC)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_LEVEL_ROLE_SVC)
public class WfwRefLevelRoleService extends AbstractService<WfwRefLevelRole> {

	@Autowired
	private WfwRefLevelRoleDao wfwRefLevelRoleDao;


	@Override
	public GenericDao<WfwRefLevelRole> primaryDao() {
		return wfwRefLevelRoleDao;
	}


	public List<WfwRefLevelRole> findAll() {
		return wfwRefLevelRoleDao.findAll();
	}


	public WfwRefLevelRole findByRoleId(String roleCd) {
		return wfwRefLevelRoleDao.findByRoleId(roleCd);
	}


	public List<WfwRefLevelRole> findByLevelId(Integer levelId) {
		return wfwRefLevelRoleDao.findByLevelId(levelId);
	}


	public List<Integer> findLevelIdByRoles(List<String> roles) {
		return wfwRefLevelRoleDao.findLevelListByRoles(roles);
	}


	public WfwRefLevelRole addUpdate(WfwRefLevelRole dto) {

		WfwRefLevelRole refLevelRole = wfwRefLevelRoleDao.findById(dto.getLevelRolePk());

		if (CmnUtil.isObjNull(refLevelRole)) {
			refLevelRole = new WfwRefLevelRole();
			refLevelRole.setLevelRolePk(dto.getLevelRolePk());
			refLevelRole.setCreateId(dto.getCreateId());
			refLevelRole.setCreateDt(DateUtil.getSQLTimestamp());
		}

		refLevelRole.setUpdateId(dto.getCreateId());
		refLevelRole.setUpdateDt(DateUtil.getSQLTimestamp());
		return update(refLevelRole);
	}


	public String setStrRoles(List<WfwRefLevelRole> lvlRoleList) {

		if (!CmnUtil.isListNull(lvlRoleList)) {
			List<String> roles = new ArrayList<>();
			for (WfwRefLevelRole lvlRole : lvlRoleList) {
				roles.add(lvlRole.getLevelRolePk().getRoleCd());
			}

			return String.join(", ", roles);
		}
		return null;
	}


	public List<String> setRoleList(List<WfwRefLevelRole> lvlRoleList) {

		if (!CmnUtil.isListNull(lvlRoleList)) {
			List<String> roles = new ArrayList<>();
			for (WfwRefLevelRole lvlRole : lvlRoleList) {
				roles.add(lvlRole.getLevelRolePk().getRoleCd());
			}

			return roles;
		}
		return null;
	}

}
