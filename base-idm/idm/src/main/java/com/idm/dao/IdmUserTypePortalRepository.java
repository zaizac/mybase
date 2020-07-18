package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmUserTypePortal;


/**
 * @author mary.jane
 * @since 22 Nov 2019
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserTypePortal.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_PORTAL_DAO)
public interface IdmUserTypePortalRepository extends GenericRepository<IdmUserTypePortal> {

	public List<IdmUserTypePortal> findByUserTypeUserTypeCode(String userTypeCode);


	public List<IdmUserTypePortal> findByPortalTypePortalTypeCode(String portalTypeCode);

}
