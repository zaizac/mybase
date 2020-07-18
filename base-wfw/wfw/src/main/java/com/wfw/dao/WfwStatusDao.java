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
import com.wfw.model.WfwStatus;

/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_STATUS_DAO)
@RepositoryDefinition(domainClass=WfwStatus.class, idClass=Integer.class)
public interface WfwStatusDao extends GenericDao<WfwStatus> {

	@Query("select u from WfwStatus u where u.statusId = :statusId")
	public WfwStatus findWfStatusById(@Param("statusId") String statusId);
	
	@Query("select u from WfwStatus u where u.description =:description)")
	public List<WfwStatus> findWfStatusByDescrition(@Param("description") String description);
	
	@Query("select u from WfwStatus u where u.levelId =:levelId and u.isActive='Y' order by u.flowNo)")
	public List<WfwStatus> findWfStatusByLevelId(@Param("levelId") String levelId);
	
	@Query("select u from WfwStatus u where u.levelId =:levelId order by u.flowNo)")
	public List<WfwStatus> findAllWfStatusByLevelId(@Param("levelId") String levelId);
	
	@Query("select u from WfwStatus u where u.levelId =:levelId and u.isActive='Y' and u.isDisplay='Y' order by u.flowNo)")
	public List<WfwStatus> findWfStatusByLevelIdDisplayYes(@Param("levelId") String levelId);
	
	@Query("select u from WfwStatus u where u.levelId =:levelId and u.applStsId=:appStatus order by u.flowNo)")
	public List<WfwStatus> findWfStatusByLevelIdAppStatus(@Param("levelId") String levelId, @Param("appStatus") String appStatus);
	
}
