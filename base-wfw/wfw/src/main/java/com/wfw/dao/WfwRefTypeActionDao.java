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
import com.wfw.model.WfwRefTypeAction;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_TYPE_ACTION_DAO)
@RepositoryDefinition(domainClass = WfwRefTypeAction.class, idClass = Integer.class)
public interface WfwRefTypeActionDao extends GenericDao<WfwRefTypeAction> {

	@Query("select u from WfwRefTypeAction u where u.levelId =:levelId)")
	List<WfwRefTypeAction> findByLevelId(@Param("levelId") Integer levelId);


	@Query("select u from WfwRefTypeAction u where u.typeId =:typeId)")
	List<WfwRefTypeAction> findByTypeId(@Param("typeId") Integer typeId);

}
