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
import com.wfw.model.WfwInboxHist;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Deprecated
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_HIST_DAO)
@RepositoryDefinition(domainClass = WfwInboxHist.class, idClass = Integer.class)
public interface WfwInboxHistDao extends GenericDao<WfwInboxHist> {

	@Query("select u from WfwInboxHist u where u.histId = :histId")
	public WfwInboxHist findWfInboxHistByHistId(@Param("histId") String histId);


	@Query("select u from WfwInboxHist u where u.taskId = :taskId order by u.createDt")
	public List<WfwInboxHist> findWfInboxHistByTaskId(@Param("taskId") String taskId);


	@Query("select u from WfwInboxHist u where u.refNo =:refNo)")
	public List<WfwInboxHist> findWfInboxHistByRefNo(@Param("refNo") String refNo);


	@Query("select u from WfwInboxHist u where u.taskId = :taskId and applStsCode = :applStsCode order by u.updateDt desc")
	public List<WfwInboxHist> findWfInboxHistByTaskidAndStausCode(@Param("taskId") String taskId,
			@Param("applStsCode") String applStsCode);

}
