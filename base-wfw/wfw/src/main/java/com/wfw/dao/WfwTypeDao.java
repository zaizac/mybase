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
import com.wfw.model.WfwType;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_TYPE_DAO)
@RepositoryDefinition(domainClass = WfwType.class, idClass = Integer.class)
public interface WfwTypeDao extends GenericDao<WfwType> {

	@Query("select u from WfwType u where u.typeId = :typeId")
	public WfwType findTypeById(@Param("typeId") String typeId);


	@Query("select u from WfwType u where upper(u.description) = upper(:description)")
	public List<WfwType> findTypeByDescription(@Param("description") String description);

}
