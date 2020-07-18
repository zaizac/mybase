/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sgw.constants.QualifierConstants;
import com.sgw.model.SgwAuthorization;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Repository
@RepositoryDefinition(domainClass = SgwAuthorization.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUTHORIZATION_DAO)
public interface SgwAuthorizationRepository extends GenericRepository<SgwAuthorization> {

	@Query("select u from SgwAuthorization u where u.clientId= :clientId and u.trxnNo = :trxnNo ")
	public SgwAuthorization findByClientIdAndTxnNo(@Param("clientId") String clientId, @Param("trxnNo") String trxnNo);

}
