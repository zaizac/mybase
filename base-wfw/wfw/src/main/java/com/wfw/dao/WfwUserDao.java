/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.GenericDao;
import com.wfw.model.WfwUser;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_USER_DAO)
@RepositoryDefinition(domainClass = WfwUser.class, idClass = Integer.class)
public interface WfwUserDao extends GenericDao<WfwUser> {

	@Query("select u from WfwUser u where u.wfUserId = :wfUserId")
	public WfwUser findWfUserById(@Param("wfUserId") Integer wfUserId);


	@Query("select u from WfwUser u where u.userName =:username)")
	public WfwUser findWfUsreByUsername(@Param("username") String username);


	@Query("select u from WfwUser u where u.userName =:username and u.password=:password)")
	public WfwUser findWfUsreByUsernamePassword(@Param("username") String username,
			@Param("password") String password);

}
