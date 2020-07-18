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
import com.wfw.model.WfwRefStatus;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_STATUS_DAO)
@RepositoryDefinition(domainClass = WfwRefStatus.class, idClass = Integer.class)
public interface WfwRefStatusDao extends GenericDao<WfwRefStatus> {

	@Query("select u from WfwRefStatus u where u.statusCd = :statusCd")
	WfwRefStatus findByStatusCd(@Param("statusCd") String statusCd);


	@Query("select u from WfwRefStatus u where u.statusDesc =:statusDesc)")
	List<WfwRefStatus> findByStatusDesc(@Param("statusDesc") String description);


	@Query("select u from WfwRefStatus u where u.levelId =:levelId and u.status=1 order by u.statusSequence)")
	List<WfwRefStatus> findActiveByLevelId(@Param("levelId") Integer levelId);


	@Query("select u from WfwRefStatus u where u.levelId =:levelId order by u.statusSequence)")
	List<WfwRefStatus> findAllByLevelId(@Param("levelId") Integer levelId);


	@Query("select u from WfwRefStatus u where u.levelId =:levelId and u.status=1 and u.display=1 order by u.statusSequence)")
	List<WfwRefStatus> findByLevelIdDisplayYes(@Param("levelId") Integer levelId);


	@Query("select u from WfwRefStatus u where u.levelId =:levelId and u.statusCd=:statusCd order by u.statusSequence)")
	WfwRefStatus findByLevelIdStatusCd(@Param("levelId") Integer levelId, @Param("statusCd") String statusCd);


	@Query("select max(u.statusSequence) from WfwRefStatus u where u.levelId =:levelId)")
	Integer findMaxStatusSequence(@Param("levelId") Integer levelId);
}
