/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.model.IdmAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmAuditTrail.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_DAO)
public interface IdmAuditTrailRepository extends GenericRepository<IdmAuditTrail> {

}
