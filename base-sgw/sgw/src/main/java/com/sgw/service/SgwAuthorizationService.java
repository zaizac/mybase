/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgw.constants.QualifierConstants;
import com.sgw.dao.GenericRepository;
import com.sgw.dao.SgwAuthorizationRepository;
import com.sgw.model.SgwAuthorization;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Transactional
@Service(QualifierConstants.AUTHORIZATION_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUTHORIZATION_SVC)
public class SgwAuthorizationService extends AbstractService<SgwAuthorization> {

	@Autowired
	@Qualifier(QualifierConstants.AUTHORIZATION_DAO)
	SgwAuthorizationRepository authDao;


	@Override
	public GenericRepository<SgwAuthorization> primaryDao() {
		return authDao;
	}


	public SgwAuthorization findByClientIdAndTxnNo(String clientId, String trxnNo) {
		return authDao.findByClientIdAndTxnNo(clientId, trxnNo);
	}

}
