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
import com.baseboot.idm.model.IdmUserGroupRoleGroup;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmUserGroupRoleGroup.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_DAO)
public interface IdmUserGroupRoleGroupRepository extends GenericRepository<IdmUserGroupRoleGroup> {

	@Query("select u from IdmUserGroupRoleGroup u where u.userGroupCode = :userGroupCode ")
	public List<IdmUserGroupRoleGroup> findUserGroupByGroupCode(@Param("userGroupCode") String userGroupCode);


	@Query("select u from IdmUserGroupRoleGroup u where u.userRoleGroupCode = :userRoleGroupCode ")
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCode(
			@Param("userRoleGroupCode") String userRoleGroupCode);


	@Query("select u from IdmUserGroupRoleGroup u order by u.userGroupCode")
	public List<IdmUserGroupRoleGroup> findAllUserGroup();


	@Query("select u from IdmUserGroupRoleGroup u where u.parentRoleGroup = :parentRoleGroup")
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(
			@Param("parentRoleGroup") String parentRoleGroup);

}