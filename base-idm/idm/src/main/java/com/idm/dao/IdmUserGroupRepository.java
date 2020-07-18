package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmUserGroup;
import org.springframework.data.repository.query.Param;


/**
 * @author Mary Jane Buenaventura
 * @since 15/11/2016
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserGroup.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_DAO)
public interface IdmUserGroupRepository extends GenericRepository<IdmUserGroup> {

  	@Query("select u from IdmUserGroup u inner join fetch u.userType ut order by u.userGroupCode")
	public List<IdmUserGroup> findAllUserGroups();
  
	@Query("select u from IdmUserGroup u inner join fetch u.userType ut where u.userGroupCode = :userGroupCode")
	public IdmUserGroup findByUserGroupCode(@Param("userGroupCode") String userGroupCode);

}