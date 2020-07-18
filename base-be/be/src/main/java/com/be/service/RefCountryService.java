package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.ConfigConstants;
import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.RefCountryQf;
import com.be.dao.RefCountryRepository;
import com.be.model.RefCountry;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Service(QualifierConstants.REF_COUNTRY_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_COUNTRY_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
@Transactional
public class RefCountryService extends AbstractService<RefCountry> {

	@Autowired
	private RefCountryRepository refCountryDao;

	@Autowired
	private RefCountryQf refCountryQf;


	@Override
	public GenericRepository<RefCountry> primaryDao() {
		return refCountryDao;
	}


	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refCountryQf.generateCriteria(cb, from, criteria);
	}


	@Override
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_COUNTRY_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefCountry> findAll() {
		return refCountryDao.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_COUNTRY.concat(#cntryCd)", condition = "#cntryCd != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefCountry findByCountryCode(String cntryCd) {
		return refCountryDao.findByCountryCode(cntryCd);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_COUNTRY_CRNCY.concat(#currency)", condition = "#currency != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefCountry findByCurrencyCode(String currency) {
		return refCountryDao.findByCurrencyCode(currency);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefCountry> findByCountryInd(Boolean cntryInd) {
		return refCountryDao.findByCountryInd(cntryInd);
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_COUNTRY_ALL"), }, put = {
					@CachePut(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_COUNTRY.concat(#s.cntryCd)") })
	public RefCountry create(RefCountry s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_COUNTRY_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_COUNTRY.concat(#s.cntryCd)") })
	public RefCountry update(RefCountry s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_COUNTRY_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	public List<RefCountry> searchAllByProperty(RefCountry country) {
		return refCountryQf.searchAllByProperty(country);
	}

}