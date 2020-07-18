/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.GenericDao;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_ROLE_DAO)
@RepositoryDefinition(domainClass = WfwAsgnRole.class, idClass = Integer.class)
public interface WfwAsgnRoleDao extends GenericDao<WfwAsgnRole> {

	@Query("select u from WfwAsgnRole u where u.wfwAsgnRolePk = :wfwAsgnRolePk")
	public WfwAsgnRole findWfUserById(@Param("wfwAsgnRolePk") WfwAsgnRolePk wfwAsgnRolePk);


	@Query("select u from WfwAsgnRole u where u.wfwAsgnRolePk.levelId =:levelId)")
	public List<WfwAsgnRole> findWfAsgnRoleByLevelId(@Param("levelId") String levelId);


	@Query("select u from WfwAsgnRole u where u.wfwAsgnRolePk.roleId =:roleId)")
	public List<WfwAsgnRole> findWfAsgnRoleByRoleId(@Param("roleId") String roleId);

}
