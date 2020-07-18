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
import com.rmq.dao.RmqAuditTrailRepository;
import com.rmq.model.RmqAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Transactional
@Service(QualifierConstants.AUDIT_TRAIL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_SVC)
public class RmqAuditTrailService extends AbstractService<RmqAuditTrail> {

	@Autowired
	RmqAuditTrailRepository auditTrailDao;


	@Override
	public GenericRepository<RmqAuditTrail> primaryDao() {
		return auditTrailDao;
	}

}