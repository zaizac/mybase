package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmUserGroupRoleGroup;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserGroupRoleGroup.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_DAO)
public interface IdmUserGroupRoleGroupRepository extends GenericRepository<IdmUserGroupRoleGroup> {

	@Query("select u from IdmUserGroupRoleGroup u where u.userGroupCode = :userGroupCode ")
	public List<IdmUserGroupRoleGroup> findUserGroupByGroupCode(@Param("userGroupCode") String userGroupCode);


	@Query("select u from IdmUserGroupRoleGroup u where u.userRoleGroup.userGroupCode = :userRoleGroupCode ")
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCode(
			@Param("userRoleGroupCode") String userRoleGroupCode);
	
	
	@Query("select u from IdmUserGroupRoleGroup u where u.userRoleGroup.userGroupCode = :userRoleGroupCode and u.systemType = :systemType ")
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCodeAndSystemType(
			@Param("userRoleGroupCode") String userRoleGroupCode, @Param("systemType") String systemType);


	@Query("select u from IdmUserGroupRoleGroup u order by u.userGroupCode")
	public List<IdmUserGroupRoleGroup> findAllUserGroup();


	@Query("select u from IdmUserGroupRoleGroup u where u.parentRoleGroup = :parentRoleGroup")
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(
			@Param("parentRoleGroup") String parentRoleGroup);
	
	@Query("select u from IdmUserGroupRoleGroup u where u.parentRoleGroup = :parentRoleGroup and u.systemType = :systemType")
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroupAndSystemType(
			@Param("parentRoleGroup") String parentRoleGroup,@Param("systemType") String systemType);
	
	@Query("select u from IdmUserGroupRoleGroup u where u.parentRoleGroup in (:parentRoleGroup)")
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroups(
			@Param("parentRoleGroup") List<String> parentRoleGroup);

}