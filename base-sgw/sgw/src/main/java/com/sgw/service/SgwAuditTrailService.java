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
import com.sgw.dao.SgwAuditTrailRepository;
import com.sgw.model.SgwAuditTrail;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Transactional
@Service(QualifierConstants.AUDIT_TRAIL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_TRAIL_SVC)
public class SgwAuditTrailService extends AbstractService<SgwAuditTrail> {

	@Autowired
	SgwAuditTrailRepository auditTrailDao;


	@Override
	public GenericRepository<SgwAuditTrail> primaryDao() {
		return auditTrailDao;
	}

}