/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.List;

import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bstsb.idm.config.ConfigConstants;
import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmUserGroupRoleGroupQf;
import com.bstsb.idm.dao.IdmUserGroupRoleGroupRepository;
import com.bstsb.idm.model.IdmUserGroupRoleGroup;
import com.bstsb.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupRoleGroupService extends AbstractService<IdmUserGroupRoleGroup> {

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_DAO)
	private IdmUserGroupRoleGroupRepository idmUserGroupDao;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_QF)
	private IdmUserGroupRoleGroupQf idmUserGroupRoleGroupQf;


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ALL", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleGroup> findAllUserGroup() {
		return idmUserGroupDao.findAllUserGroup();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_RG.concat(#userRoleGroupCode)", condition = "#userRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCode(String userRoleGroupCode) {
		return idmUserGroupDao.findUserGroupByRoleGroupCode(userRoleGroupCode);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String userRoleGroupCode, boolean noSystemCreate,
			String currUserId) {
		return idmUserGroupRoleGroupQf.findUserGroupByParentRoleGroup(userRoleGroupCode, noSystemCreate, currUserId);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP.concat(#userGroupCode)", condition = "#userGroupCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByGroupCode(String userGroupCode) {
		return idmUserGroupDao.findUserGroupByGroupCode(userGroupCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_RG.concat(#userRoleGroupCode)", condition = "#userRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String parentRoleGroup) {
		return idmUserGroupDao.findUserGroupByParentRoleGroup(parentRoleGroup);
	}


	@Override
	public GenericRepository<IdmUserGroupRoleGroup> primaryDao() {
		return idmUserGroupDao;
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_RG.concat(#s.userRoleGroupCode)") }, put = {
							@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
									+ ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)") })
	public IdmUserGroupRoleGroup create(IdmUserGroupRoleGroup s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_RG.concat(#s.userRoleGroupCode)") })
	public IdmUserGroupRoleGroup update(IdmUserGroupRoleGroup s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}

}