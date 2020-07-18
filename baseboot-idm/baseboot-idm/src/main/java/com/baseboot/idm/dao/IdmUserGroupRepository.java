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
import com.baseboot.idm.model.IdmUserGroup;


/**
 * @author Mary Jane Buenaventura
 * @since 15/11/2016
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmUserGroup.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_DAO)
public interface IdmUserGroupRepository extends GenericRepository<IdmUserGroup> {

	@Query("select u from IdmUserGroup u order by u.userGroupCode")
	public List<IdmUserGroup> findAllUserGroups();


	@Query("select u from IdmUserGroup u where u.userGroupCode = :userGroupCode ")
	public IdmUserGroup findUserGroupByUserGroupCode(@Param("userGroupCode") String userGroupCode);

}