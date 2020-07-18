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
import com.be.dao.RefStateQf;
import com.be.dao.RefStateRepository;
import com.be.model.RefState;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Transactional
@Service(QualifierConstants.REF_STATE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATE_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefStateService extends AbstractService<RefState> {

	@Autowired
	private RefStateRepository stateRepository;

	@Autowired
	private RefStateQf refStateQf;


	@Override
	public GenericRepository<RefState> primaryDao() {
		return stateRepository;
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refStateQf.generateCriteria(cb, from, criteria);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_STATE_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefState> allStates() {
		return stateRepository.findAllStates();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_STATE.concat(#stateCd)", condition = "#stateCd != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefState findByStateCode(String stateCd) {
		return stateRepository.findByStateCode(stateCd);
	}


	public List<RefState> searchAllByProperty(RefState state) {
		return refStateQf.searchAllByProperty(state);
	}

}