package com.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmPortalType;


/**
 * @author mary.jane
 * @since 22 Nov 2019
 */
@Repository
@RepositoryDefinition(domainClass = IdmPortalType.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PORTAL_TYPE_DAO)
public interface IdmPortalTypeRepository extends GenericRepository<IdmPortalType> {

	public IdmPortalType findByPortalTypeCode(String portalTypeCode);
	
}
