/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmAuditTrailRepository;
import com.bstsb.idm.model.IdmAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.AUDIT_TRAIL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_SVC)
public class IdmAuditTrailService extends AbstractService<IdmAuditTrail> {

	@Autowired
	private IdmAuditTrailRepository idmAuditTrailDao;


	@Override
	public GenericRepository<IdmAuditTrail> primaryDao() {
		return idmAuditTrailDao;
	}

}