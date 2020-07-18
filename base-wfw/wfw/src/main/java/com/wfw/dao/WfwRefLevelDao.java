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
import com.wfw.model.WfwRefLevel;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_LEVEL_DAO)
@RepositoryDefinition(domainClass = WfwRefLevel.class, idClass = Integer.class)
public interface WfwRefLevelDao extends GenericDao<WfwRefLevel> {

	@Query("select u from WfwRefLevel u where u.description =:description)")
	List<WfwRefLevel> findByDescrition(@Param("description") String description);


	@Query("select u from WfwRefLevel u where u.typeId =:typeId order by u.flowNo)")
	List<WfwRefLevel> findByTypeId(@Param("typeId") Integer typeId);


	@Query("select max(u.flowNo) from WfwRefLevel u where u.typeId =:typeId)")
	Integer findMaxFlowNo(@Param("typeId") Integer typeId);
}
