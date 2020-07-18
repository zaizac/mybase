/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.GenericDao;
import com.wfw.model.WfwInboxMstr;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Deprecated
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_MSTR_DAO)
@RepositoryDefinition(domainClass = WfwInboxMstr.class, idClass = Integer.class)
public interface WfwInboxMstrDao extends GenericDao<WfwInboxMstr> {

	@Query("select u from WfwInboxMstr u where u.taskId = :taskId")
	public WfwInboxMstr findWfInboxMstrByTaskId(@Param("taskId") String taskId);


	@Query("select u from WfwInboxMstr u where u.refNo =:refNo)")
	public WfwInboxMstr findWfInboxMstrByRefNo(@Param("refNo") String refNo);


	@Query("select u from WfwInboxMstr u where u.applStsCode =:applStsCode and date(u.applDate) <=:applDate ")
	public List<WfwInboxMstr> findDraftApplicationList(@Param("applStsCode") String applStsCode,
			@Param("applDate") Date applDate);
}
