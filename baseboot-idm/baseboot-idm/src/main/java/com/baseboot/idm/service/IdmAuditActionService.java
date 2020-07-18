/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractService;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.dao.IdmAuditActionRepository;
import com.baseboot.idm.model.IdmAuditAction;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.AUDIT_ACTION_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_ACTION_SVC)
public class IdmAuditActionService extends AbstractService<IdmAuditAction> {

	@Lazy
	@Autowired
	private IdmAuditActionRepository idmAuditActionDao;


	@Override
	public GenericRepository<IdmAuditAction> primaryDao() {
		return idmAuditActionDao;
	}

}
