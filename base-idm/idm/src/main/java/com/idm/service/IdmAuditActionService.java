package com.idm.service;


import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmAuditActionQf;
import com.idm.dao.IdmAuditActionRepository;
import com.idm.model.IdmAuditAction;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Service(QualifierConstants.AUDIT_ACTION_SVC)
@Qualifier(QualifierConstants.AUDIT_ACTION_SVC)
public class IdmAuditActionService extends AbstractService<IdmAuditAction> {

	@Autowired
	private IdmAuditActionRepository idmAuditActionDao;

	@Autowired
	private IdmAuditActionQf idmAuditActionQf;

	@Override
	public GenericRepository<IdmAuditAction> primaryDao() {
		return idmAuditActionDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmAuditActionQf.generateCriteria(cb, from, criteria);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmAuditAction> search(IdmAuditAction idmAuditAction) throws IOException {
		return idmAuditActionDao.findAll(idmAuditActionQf.searchByProperty(idmAuditAction));
	}

}
