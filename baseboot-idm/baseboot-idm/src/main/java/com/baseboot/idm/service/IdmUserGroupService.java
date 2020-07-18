/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractService;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.dao.IdmUserGroupRepository;
import com.baseboot.idm.model.IdmUserGroup;
import com.baseboot.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.IDM_USER_GROUP_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupService extends AbstractService<IdmUserGroup> {

	@Lazy
	@Autowired
	IdmUserGroupRepository idmUserGroupDao;


	@Override
	public GenericRepository<IdmUserGroup> primaryDao() {
		return idmUserGroupDao;
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USR_GRP_ALL", unless = "#result != null and #result.size()
	// == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroup> findAllUserGroups() {
		return idmUserGroupDao.findAllUserGroups();
	}


	@Override
	/*
	 * @Caching(evict = {
	 *
	 * @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_RG.concat(#s.userGroupCode)") }, put = {
	 *
	 * @CachePut(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)") })
	 */
	public IdmUserGroup create(IdmUserGroup s) {
		return super.create(s);
	}


	@Override
	/*
	 * @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ALL"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_RG.concat(#s.userGroupCode)") })
	 */
	public IdmUserGroup update(IdmUserGroup s) {
		return super.update(s);
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USR_GRP_RG.concat(#userGroupCode)", condition =
	// "#userGroupCode != null", unless = "#result != null and #result.size()
	// == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmUserGroup findUserGroupByRoleGroupCode(String userGroupCode) {
		return idmUserGroupDao.findUserGroupByUserGroupCode(userGroupCode);
	}

}