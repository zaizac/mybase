/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.model.IdmUserGroupRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmProfile.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_REPOSITORY)
public interface IdmUserGroupRoleRepository extends GenericRepository<IdmUserGroupRole> {

	@Query("select u from IdmUserGroupRole u where u.roleCode = :roleCode ")
	public List<IdmUserGroupRole> findUserGroupByRoleCode(@Param("roleCode") String roleCode);


	@Query("select u from IdmUserGroupRole u where u.userRoleGroupCode = :userRoleGroupCode ")
	public List<IdmUserGroupRole> findUserGroupByUserRoleGroupCode(
			@Param("userRoleGroupCode") String userRoleGroupCode);


	@Query("select u from IdmUserGroupRole u ")
	public List<IdmUserGroupRole> findAllUserGroupRoles();


	@Query("select u from IdmUserGroupRole u where u.roleCode =:roleCode and u.userRoleGroupCode =:userRoleGroupCode")
	public IdmUserGroupRole findUserGroupByRoleAndGroupCode(@Param("roleCode") String roleCode,
			@Param("userRoleGroupCode") String userRoleGroupCode);
}