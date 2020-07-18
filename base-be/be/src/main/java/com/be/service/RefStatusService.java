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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.ConfigConstants;
import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.RefStatusQf;
import com.be.dao.RefStatusRepository;
import com.be.model.RefStatus;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


/**
 * @author michelle.angela
 * @since 19 Feb 2019
 */
@Lazy
@Transactional
@Service(QualifierConstants.REF_STATUS_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATUS_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefStatusService extends AbstractService<RefStatus> {

	@Autowired
	private RefStatusRepository refStatusDao;

	@Autowired
	private RefStatusQf refStatusQf;


	@Override
	public GenericRepository<RefStatus> primaryDao() {
		return refStatusDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refStatusQf.generateCriteria(cb, from, criteria);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_STATUS_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefStatus> getAllStatus() {
		return refStatusDao.getAllStatus();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_STATUS.concat(#statusCode)", condition = "#statusCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefStatus findByStatusCode(String statusCode) {
		return refStatusDao.findByStatusCode(statusCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_STATUS.concat(#statusCode)", condition = "#statusCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefStatus> searchAllByProperty(RefStatus status) {
		return refStatusQf.searchAllByProperty(status);
	}

}
