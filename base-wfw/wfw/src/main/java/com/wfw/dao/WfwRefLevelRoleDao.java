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
import com.wfw.model.WfwRefLevelRole;
import com.wfw.model.WfwRefLevelRolePk;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_LEVEL_DAO)
@RepositoryDefinition(domainClass = WfwRefLevelRole.class, idClass = Integer.class)
public interface WfwRefLevelRoleDao extends GenericDao<WfwRefLevelRole> {

	@Query("select u from WfwRefLevelRole u where u.levelRolePk = :levelRolePk")
	WfwRefLevelRole findById(@Param("levelRolePk") WfwRefLevelRolePk levelRolePk);


	@Query("select u from WfwRefLevelRole u where u.levelRolePk.levelId =:levelId)")
	List<WfwRefLevelRole> findByLevelId(@Param("levelId") Integer levelId);


	@Query("select u from WfwRefLevelRole u where u.levelRolePk.roleCd =:roleCd)")
	WfwRefLevelRole findByRoleId(@Param("roleCd") String roleCd);


	@Query("select distinct u.levelRolePk.levelId from WfwRefLevelRole u where u.levelRolePk.roleCd in :roles")
	List<Integer> findLevelListByRoles(@Param("roles") List<String> roles);
}
