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
import com.wfw.model.WfwTaskHistory;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_TASK_HISTORY_DAO)
@RepositoryDefinition(domainClass = WfwTaskHistory.class, idClass = Integer.class)
public interface WfwTaskHistoryDao extends GenericDao<WfwTaskHistory> {

	@Query("select u from WfwTaskHistory u where u.taskHistoryId = :taskHistoryId")
	public WfwTaskHistory findByTaskHistoryId(@Param("taskHistoryId") String taskHistoryId);


	@Query("select u from WfwTaskHistory u where u.taskHistoryId = :taskHistoryId and u.taskMasterId = :taskMasterId")
	public WfwTaskHistory findByTaskHistoryIdAndTaskMasterId(@Param("taskHistoryId") String taskHistoryId,
			@Param("taskMasterId") String taskMasterId);


	@Query("select u from WfwTaskHistory u where u.taskMasterId = :taskMasterId order by u.createDt")
	public List<WfwTaskHistory> findByTaskMasterId(@Param("taskMasterId") String taskMasterId);


	@Query("select distinct u.taskMasterId from WfwTaskHistory u where u.submitBy = :submitBy order by u.taskMasterId, u.createDt")
	public List<String> findTaskMasterIdBySubmitBy(@Param("submitBy") String submitBy);

}
