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
import com.baseboot.idm.dao.IdmRoleRepository;
import com.baseboot.idm.model.IdmRole;
import com.baseboot.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.IDMROLE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMROLE_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmRoleService extends AbstractService<IdmRole> {

	@Lazy
	@Autowired
	private IdmRoleRepository idmRoleRepository;


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_ROLE.concat(#roleCode)", condition = "#roleCode != null
	// and #result != null")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmRole findUserRoleByRoleCode(String roleCode) {
		return idmRoleRepository.findUserRoleByRoleCode(roleCode);
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_ROLE_ALL", unless = "#result != null and #result.size() ==
	// 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmRole> findAllRoles() {
		return idmRoleRepository.findAll();
	}


	@Override
	/*
	 * @Caching(evict = { @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE_ALL"), }, put = {
	 *
	 * @CachePut(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_ROLE.concat(#s.roleCode)") })
	 */
	public IdmRole create(IdmRole s) {
		return super.create(s);
	}


	@Override
	// @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_ROLE_ALL"),
	// @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_ROLE.concat(#s.roleCode)") })
	public IdmRole update(IdmRole s) {
		return super.update(s);
	}


	@Override
	// @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_ROLE_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	@Override
	public GenericRepository<IdmRole> primaryDao() {
		return idmRoleRepository;
	}

}