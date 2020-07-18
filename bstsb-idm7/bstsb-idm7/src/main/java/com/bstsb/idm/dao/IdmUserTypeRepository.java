/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmUserType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserType.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_REPOSITORY)
public interface IdmUserTypeRepository extends GenericRepository<IdmUserType> {

	@Query("select u from IdmUserType u where u.userTypeCode = :userTypeCode ")
	public IdmUserType findByUserTypeCode(@Param("userTypeCode") String userTypeCode);

}