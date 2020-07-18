/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.config.ConfigConstants;
import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmUserTypeRepository;
import com.bstsb.idm.model.IdmUserType;
import com.bstsb.idm.sdk.constants.IdmCacheConstants;
import com.bstsb.util.BaseUtil;


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

	@Autowired
	private IdmUserTypeRepository userTypeDao;


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserType> findAll() {
		return userTypeDao.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE.concat(#userTypeCode)", condition = "#userTypeCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmUserType findByUserTypeCode(String userTypeCode) {
		return userTypeDao.findByUserTypeCode(userTypeCode);
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE_ALL"), }, put = {
					@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
							+ ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	public IdmUserType create(IdmUserType s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USER_TYPE_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	public IdmUserType update(IdmUserType s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	/**
	 * This method returns boolean for config checking if the user will assign
	 * email as userId based on the userType
	 *
	 * @param userType
	 * @return boolean
	 */
	public boolean checkEmailAsUserId(String userType) {
		IdmUserType idmUserType = findByUserTypeCode(userType);
		boolean isEmailAsId = false;
		if (!BaseUtil.isObjNull(idmUserType)) {
			isEmailAsId = idmUserType.isEmailAsUserId();
		}
		return isEmailAsId;
	}


	@Override
	public GenericRepository<IdmUserType> primaryDao() {
		return userTypeDao;
	}

}