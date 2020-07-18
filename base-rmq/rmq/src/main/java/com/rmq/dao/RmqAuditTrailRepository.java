/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.rmq.constants.QualifierConstants;
import com.rmq.model.RmqAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Repository
@RepositoryDefinition(domainClass = RmqAuditTrail.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_DAO)
public interface RmqAuditTrailRepository extends GenericRepository<RmqAuditTrail> {

}
