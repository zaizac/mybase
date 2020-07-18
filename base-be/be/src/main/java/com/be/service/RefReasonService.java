package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.RefReasonQf;
import com.be.dao.RefReasonRepository;
import com.be.model.RefReason;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


@Lazy
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Service(QualifierConstants.REF_REASON_SVC)
@Qualifier(QualifierConstants.REF_REASON_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefReasonService extends AbstractService<RefReason> {

	@Autowired
	private RefReasonRepository reasonDao;

	@Autowired
	private RefReasonQf refReasonQf;


	@Override
	public GenericRepository<RefReason> primaryDao() {
		return reasonDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refReasonQf.generateCriteria(cb, from, criteria);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefReason> getAllReason() {
		return reasonDao.findAll();
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefReason> searchAllByProperty(RefReason reason) {
		return reasonDao.findAll(refReasonQf.searchByProperty(reason));
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefReason saveAndUpdate(RefReason reason) {
		return reasonDao.saveAndFlush(reason);
	}

}