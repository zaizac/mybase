/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_ROLE_DTO)
@EnableTransactionManagement
public interface WfwAsgnRoleDto {

	public WfwAsgnRole findWfAsgnRoleById(WfwAsgnRolePk wfwAsgnRolePk);


	public List<WfwAsgnRole> findWfAsgnRoleByRoleId(String roleId);


	public List<WfwAsgnRole> findWfAsgnRoleByLevelId(String levelId);


	public List<WfwAsgnRole> findAllWfAsgnRole();


	public boolean addWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	public boolean addWfAsgnRole(List<WfwAsgnRole> wfwAsgnRoleList);


	public boolean editWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	public boolean deleteWfAsgnRole(WfwAsgnRole wfwAsgnRole);


	public boolean deleteWfAsgnRoleByLevelId(String levelId);

}
