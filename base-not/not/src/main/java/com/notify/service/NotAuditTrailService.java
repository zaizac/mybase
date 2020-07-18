/**
 * Copyright 2019. 
 */
package com.notify.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notify.constants.QualifierConstants;
import com.notify.core.AbstractService;
import com.notify.core.GenericRepository;
import com.notify.dao.NotAuditTrailRepository;
import com.notify.model.NotAuditTrail;



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
	 * @see com.notify.core.AbstractService#primaryDao()
	 */
	@Override
	public GenericRepository<NotAuditTrail> primaryDao() {
		return notAuditTrailDao;
	}

}