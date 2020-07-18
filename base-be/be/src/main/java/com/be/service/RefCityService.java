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
import com.be.dao.RefCityQf;
import com.be.dao.RefCityRepository;
import com.be.model.RefCity;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Service(QualifierConstants.REF_CITY_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_CITY_SVC)
@Transactional
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefCityService extends AbstractService<RefCity> {

	@Autowired
	private RefCityRepository refCityDao;

	@Autowired
	private RefCityQf refCityQf;


	@Override
	public GenericRepository<RefCity> primaryDao() {
		return refCityDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refCityQf.generateCriteria(cb, from, criteria);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefCity> searchByProperty(final RefCity refCity) {
		return refCityDao.findAll(refCityQf.searchByProperty(refCity));
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefCity> searchAllByProperty(RefCity city) {
		return refCityQf.searchAllByProperty(city);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, RefCity criteria) {
		return refCityQf.generateCriteria(cb, from, criteria);
	}


	@Override
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_CITY_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefCity> findAll() {
		return refCityDao.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_CITY.concat(#cityCd)", condition = "#cityCd != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefCity findByCityCode(String cityCd) {
		return refCityDao.findByCityCode(cityCd);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_CITY_STATE.concat(#cityCd)", condition = "#cityCd != null and #stateCd!=null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefCity findByCityStateCode(String cityCd, String stateCd) {
		return refCityDao.findByCityStateCode(cityCd, stateCd);
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_CITY_ALL"), }, put = {
					@CachePut(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_CITY.concat(#s.cityCd)") })
	public RefCity create(RefCity s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_CITY_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_CITY.concat(#s.cityCd)") })
	public RefCity update(RefCity s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_CITY_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}

}