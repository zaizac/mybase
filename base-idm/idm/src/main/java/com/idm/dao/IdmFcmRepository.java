package com.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmFcm;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmFcm.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_REPOSITORY)
public interface IdmFcmRepository extends GenericRepository<IdmFcm> {

	public IdmFcm findByFcmId(Integer fcmId);
	
	public IdmFcm findByUserId(String userId);

}
