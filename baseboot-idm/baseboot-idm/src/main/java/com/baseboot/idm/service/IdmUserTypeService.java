/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractService;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.dao.IdmUserTypeRepository;
import com.baseboot.idm.model.IdmUserType;
import com.baseboot.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.IDM_USER_TYPE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserTypeService extends AbstractService<IdmUserType> {

	@Lazy
	@Autowired
	private IdmUserTypeRepository userTypeDao;


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USER_TYPE_ALL", unless = "#result != null and
	// #result.size() == 0")
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserType> findAll() {
		return userTypeDao.findAll();
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USER_TYPE.concat(#userTypeCode)", condition =
	// "#userTypeCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmUserType findByUserTypeCode(String userTypeCode) {
		return userTypeDao.findByUserTypeCode(userTypeCode);
	}


	@Override
	/*
	 * @Caching(evict = { @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USER_TYPE_ALL"), }, put =
	 * {
	 *
	 * @CachePut(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	 */
	public IdmUserType create(IdmUserType s) {
		return super.create(s);
	}


	@Override
	/*
	 * @Caching(evict = {
	 *
	 * @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USER_TYPE_ALL"),
	 *
	 * @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	 */
	public IdmUserType update(IdmUserType s) {
		return super.update(s);
	}


	@Override
	// @Caching(evict = { @CacheEvict(beforeInvocation = true, key =
	// ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USER_TYPE_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	@Override
	public GenericRepository<IdmUserType> primaryDao() {
		return userTypeDao;
	}

}