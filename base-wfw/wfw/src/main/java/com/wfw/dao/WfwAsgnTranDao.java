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
import com.wfw.model.WfwAsgnTran;


/**
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_TRAN_DAO)
@RepositoryDefinition(domainClass = WfwAsgnTran.class, idClass = Integer.class)
public interface WfwAsgnTranDao extends GenericDao<WfwAsgnTran> {

	@Query("select u from WfwAsgnTran u where u.tranId = :tranId")
	public WfwAsgnTran findWfWfAsgnTranByTranId(@Param("tranId") String tranId);


	@Query("select u from WfwAsgnTran u where u.moduleId =:moduleId)")
	public List<WfwAsgnTran> findWfWfAsgnTranByModuleId(@Param("moduleId") String moduleId);


	@Query("select u from WfwAsgnTran u where u.typeId =:typeId)")
	public WfwAsgnTran findWfWfAsgnTranByTypeId(@Param("typeId") String typeId);

}
