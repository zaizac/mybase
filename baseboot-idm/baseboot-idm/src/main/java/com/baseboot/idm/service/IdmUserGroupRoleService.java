/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import java.util.List;

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
import com.baseboot.idm.dao.IdmUserGroupRoleRepository;
import com.baseboot.idm.model.IdmUserGroupRole;
import com.baseboot.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupRoleService extends AbstractService<IdmUserGroupRole> {

	@Lazy
	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_REPOSITORY)
	private IdmUserGroupRoleRepository idmUserGroupRolesDao;


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USR_GRP_ROLE_ALL", unless = "#result != null and
	// #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRole> findAllUserGroup() {
		return idmUserGroupRolesDao.findAllUserGroupRoles();
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USR_GRP_ROLE.concat(#roleCode)", condition = "#roleCode !=
	// null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRole> findUserGroupByRoleCode(String roleCode) {
		return idmUserGroupRolesDao.findUserGroupByRoleCode(roleCode);
	}


	// @Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
	// + ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#userRoleGroupCode)", condition =
	// "#userRoleGroupCode != null", unless = "#result != null and
	// #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRole> findUserGroupByUserRoleGroupCode(String userRoleGroupCode) {
		return idmUserGroupRolesDao.findUserGroupByUserRoleGroupCode(userRoleGroupCode);
	}


	@Override
	/*
	 * @Caching(evict = {
	 *
	 * @CacheEvict(beforeInvocation = true, key =
	 * ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_ALL"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#s.userRoleGroupCode)") }, put = {
	 *
	 * @CachePut(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ROLE.concat(#s.roleCode)") })
	 */
	public IdmUserGroupRole create(IdmUserGroupRole s) {
		return super.create(s);
	}


	@Override
	/*
	 * @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ROLE_ALL"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ROLE.concat(#s.roleCode)"),
	 *
	 * @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	 * ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#s.userRoleGroupCode)") })
	 */
	public IdmUserGroupRole update(IdmUserGroupRole s) {
		return super.update(s);
	}


	@Override
	// @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_USR_GRP_ROLE_ALL") })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	@Override
	public GenericRepository<IdmUserGroupRole> primaryDao() {
		return idmUserGroupRolesDao;
	}


	// @Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_USR_GRP_ROLE_ALL"),
	// @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE +
	// ".CACHE_KEY_USR_GRP_ROLE.concat(#groupCode)") })
	public IdmUserGroupRole findUserGroupByRoleAndGroup(String roleCode, String groupCode) {
		return idmUserGroupRolesDao.findUserGroupByRoleAndGroupCode(roleCode, groupCode);
	}


	public List<IdmUserGroupRole> findUserGroupByUserRoleGroupCodeInstant(String userRoleGroupCode) {
		return idmUserGroupRolesDao.findUserGroupByUserRoleGroupCode(userRoleGroupCode);
	}
}