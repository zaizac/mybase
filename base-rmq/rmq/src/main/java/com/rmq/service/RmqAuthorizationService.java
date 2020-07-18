/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmq.constants.QualifierConstants;
import com.rmq.dao.GenericRepository;
import com.rmq.dao.RmqAuthorizationRepository;
import com.rmq.model.RmqAuthorization;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Transactional
@Service(QualifierConstants.AUTHORIZATION_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUTHORIZATION_SVC)
public class RmqAuthorizationService extends AbstractService<RmqAuthorization> {

	@Autowired
	@Qualifier(QualifierConstants.AUTHORIZATION_DAO)
	RmqAuthorizationRepository authDao;


	@Override
	public GenericRepository<RmqAuthorization> primaryDao() {
		return authDao;
	}


	public RmqAuthorization findByClientIdAndTxnNo(String clientId, String trxnNo) {
		return authDao.findByClientIdAndTxnNo(clientId, trxnNo);
	}

}
