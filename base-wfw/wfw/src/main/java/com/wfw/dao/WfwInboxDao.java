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
import com.wfw.model.WfwInbox;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Deprecated
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_DAO)
@RepositoryDefinition(domainClass = WfwInbox.class, idClass = Integer.class)
public interface WfwInboxDao extends GenericDao<WfwInbox> {

	@Query("select u from WfwInbox u where u.taskId = :taskId")
	public WfwInbox findWfInboxByTaskId(@Param("taskId") String taskId);


	@Query("select u from WfwInbox u where u.refNo =:refNo)")
	public List<WfwInbox> findWfInboxByRefNo(@Param("refNo") String refNo);


	@Query("select u from WfwInbox u where u.taskId = :taskId and u.officerId=:officerId")
	public WfwInbox findWfInboxByTaskIdAndOfficerId(@Param("taskId") String taskId, @Param("officerId") String officerId);
	
	@Query("select u from WfwInbox u where u.refNo = :refNo and u.officerId=:officerId")
	public WfwInbox findWfInboxByRefNoAndOfficerId(@Param("refNo") String refNo, @Param("officerId") String officerId);

}
