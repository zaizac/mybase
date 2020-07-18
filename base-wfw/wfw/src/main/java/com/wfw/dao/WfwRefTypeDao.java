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
import com.wfw.model.WfwRefType;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WFW_REF_TYPE_DAO)
@RepositoryDefinition(domainClass = WfwRefType.class, idClass = Integer.class)
public interface WfwRefTypeDao extends GenericDao<WfwRefType> {

	@Query("select u from WfwRefType u where u.typeId = :typeId")
	public WfwRefType findAll(@Param("typeId") Integer typeId);

	@Query("select u from WfwRefType u where u.tranId = :tranId")
	public WfwRefType findByTranId(@Param("tranId") String tranId);


	@Query("select u from WfwRefType u where u.moduleId = :moduleId")
	public WfwRefType findByModuleId(@Param("moduleId") String moduleId);


	@Query("select u from WfwRefType u where upper(u.description) = upper(:description)")
	public List<WfwRefType> findByDescription(@Param("description") String description);
}
