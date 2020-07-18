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
import com.wfw.model.WfwLevel;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_LEVEL_DAO)
@RepositoryDefinition(domainClass = WfwLevel.class, idClass = Integer.class)
public interface WfwLevelDao extends GenericDao<WfwLevel> {

	@Query("select u from WfwLevel u where u.levelId = :levelId")
	public WfwLevel findWfLevelById(@Param("levelId") String levelId);


	@Query("select u from WfwLevel u where u.description =:description)")
	public List<WfwLevel> findWfLevelByDescrition(@Param("description") String description);


	@Query("select u from WfwLevel u where u.typeId =:typeId order by u.flowNo)")
	public List<WfwLevel> findWfLevelByTypeId(@Param("typeId") String typeId);

}
