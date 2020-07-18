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
import com.bstsb.idm.model.IdmFcm;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmFcm.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_REPOSITORY)
public interface IdmFcmRepository extends GenericRepository<IdmFcm> {

	@Query("select u from IdmFcm u where u.userId = :userId ")
	public IdmFcm findByUserId(@Param("userId") String userId);

}
