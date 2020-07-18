package com.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmOauthClientDetails;


/**
 * @author hafidz malik
 * @since 6 Dec 2019
 */
@Repository
@RepositoryDefinition(domainClass = IdmOauthClientDetails.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_OAUTH_CLIENT_DETAILS_REPOSITORY)
public interface IdmOauthClientDetailsRepository extends GenericRepository<IdmOauthClientDetails> {

	public IdmOauthClientDetails findByClientId(String clientId);
	
}
