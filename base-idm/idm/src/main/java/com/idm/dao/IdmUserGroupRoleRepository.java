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
import com.idm.model.IdmProfile;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmProfile.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_DAO)
public interface IdmUserGroupRoleRepository extends GenericRepository<IdmUserGroupRole> {

	public List<IdmUserGroupRole> findByRoleRoleCode(String roleCode);


	public List<IdmUserGroupRole> findByUserGroupUserGroupCode(String userRoleGroupCode);


	@Query("select u.role.roleCode from IdmUserGroupRole u where u.userGroup.userGroupCode =:userRoleGroupCode")
	public List<String> findRoleRoleCodeByUserGroupUserGroupCode(@Param("userRoleGroupCode") String userRoleGroupCode);


	@Query("select u from IdmUserGroupRole u inner join fetch u.userGroup ut inner join fetch u.role r order by u.id")
	public List<IdmUserGroupRole> findAllUserGroupRoles();


	@Query("select u from IdmUserGroupRole u where u.role.roleCode =:roleCode and u.userGroup.userGroupCode =:userRoleGroupCode")
	public IdmUserGroupRole findUserGroupByRoleAndGroupCode(@Param("roleCode") String roleCode,
			@Param("userRoleGroupCode") String userRoleGroupCode);
	
	@Query("select u from IdmUserGroupRole u inner join fetch u.userGroup ut inner join fetch u.role r where u.id = :id")
	public IdmUserGroupRole findByGroupRoleId(@Param("id") Integer id);
}