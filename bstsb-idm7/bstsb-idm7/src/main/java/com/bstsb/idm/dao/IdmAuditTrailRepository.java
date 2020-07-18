/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmAuditTrail.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_DAO)
public interface IdmAuditTrailRepository extends GenericRepository<IdmAuditTrail> {

}