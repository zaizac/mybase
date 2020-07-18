/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.notify.constants.QualifierConstants;
import com.bstsb.notify.core.AbstractService;
import com.bstsb.notify.core.GenericRepository;
import com.bstsb.notify.dao.NotAuditTrailRepository;
import com.bstsb.notify.model.NotAuditTrail;



/**
 * The Class NotAuditTrailService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.NOT_AUDIT_TRAIL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_AUDIT_TRAIL_SVC)
public class NotAuditTrailService extends AbstractService<NotAuditTrail> {

	/** The not audit trail dao. */
	@Autowired
	private NotAuditTrailRepository notAuditTrailDao;


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractService#primaryDao()
	 */
	@Override
	public GenericRepository<NotAuditTrail> primaryDao() {
		return notAuditTrailDao;
	}

}