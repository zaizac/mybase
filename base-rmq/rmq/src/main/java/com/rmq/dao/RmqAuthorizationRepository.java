/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmq.constants.QualifierConstants;
import com.rmq.model.RmqAuthorization;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Repository
@RepositoryDefinition(domainClass = RmqAuthorization.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUTHORIZATION_DAO)
public interface RmqAuthorizationRepository extends GenericRepository<RmqAuthorization> {

	@Query("select u from RmqAuthorization u where u.clientId= :clientId and u.trxnNo = :trxnNo ")
	public RmqAuthorization findByClientIdAndTxnNo(@Param("clientId") String clientId, @Param("trxnNo") String trxnNo);

}
