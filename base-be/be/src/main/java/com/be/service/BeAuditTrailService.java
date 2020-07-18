/**
 * Copyright 2019
 */
package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.BeAuditTrailRepository;
import com.be.model.BeAuditTrail;
import com.be.sdk.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.BE_AUDIT_TRAIL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_AUDIT_TRAIL_SVC)
public class BeAuditTrailService extends AbstractService<BeAuditTrail> {

	@Autowired
	private BeAuditTrailRepository beAuditTrailDao;


	@Override
	public GenericRepository<BeAuditTrail> primaryDao() {
		return beAuditTrailDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}

}