/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.bstsb.notify.constants.QualifierConstants;
import com.bstsb.notify.core.GenericRepository;
import com.bstsb.notify.model.NotAuditTrail;


/**
 * The Interface NotAuditTrailRepository.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotAuditTrail.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_AUDIT_TRAIL_DAO)
public interface NotAuditTrailRepository extends GenericRepository<NotAuditTrail> {

}