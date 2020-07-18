/**
 * Copyright 2019
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
import com.wfw.model.WfwRefTypeRole;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_TYPE_ROLE_DAO)
@RepositoryDefinition(domainClass = WfwRefTypeRole.class, idClass = Integer.class)
public interface WfwRefTypeRoleDao extends GenericDao<WfwRefTypeRole> {

	@Query("select u from WfwRefTypeRole u where u.typeId = :typeId")
	public WfwRefTypeRole findTypeById(@Param("typeId") Integer typeId);

}
