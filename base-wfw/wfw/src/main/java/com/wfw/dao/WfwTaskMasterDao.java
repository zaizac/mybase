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
import com.wfw.model.WfwTaskMaster;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_TASK_MASTER_DAO)
@RepositoryDefinition(domainClass = WfwTaskMaster.class, idClass = Integer.class)
public interface WfwTaskMasterDao extends GenericDao<WfwTaskMaster> {

	@Query("select u from WfwTaskMaster u where u.taskMasterId = :taskMasterId")
	public WfwTaskMaster findBytTaskMasterId(@Param("taskMasterId") String taskMasterId);


	@Query("select u from WfwTaskMaster u where u.appRefNo =:appRefNo)")
	public WfwTaskMaster findByApplicationRefNo(@Param("appRefNo") String appRefNo);


	@Query("select u from WfwTaskMaster u where u.taskMasterId =:taskMasterId and u.claimBy =:claimBy)")
	public WfwTaskMaster findByTaskMasterIdAndClaimBy(@Param("taskMasterId") String taskMasterId,
			@Param("claimBy") String claimBy);
}
