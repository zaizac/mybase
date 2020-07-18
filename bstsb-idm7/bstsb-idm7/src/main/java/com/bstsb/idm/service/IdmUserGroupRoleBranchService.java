/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.List;

import javax.transaction.Transactional;
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
import com.bstsb.idm.dao.IdmUserGroupRoleBranchQf;
import com.bstsb.idm.dao.IdmUserGroupRoleBranchRepository;
import com.bstsb.idm.model.IdmUserGroupRoleBranch;
import com.bstsb.idm.sdk.constants.IdmCacheConstants;
import com.bstsb.idm.sdk.model.UserGroupRoleBranch;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
@Transactional
@Service(QualifierConstants.IDM_USER_GROUP_ROLE_BRANCH_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_BRANCH_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupRoleBranchService extends AbstractService<IdmUserGroupRoleBranch> {

	@Autowired
	IdmUserGroupRoleBranchRepository idmUserGroupRoleBranchDao;

	@Autowired
	IdmUserGroupRoleBranchQf idmUserGroupRoleBranchQf;


	@Override
	public GenericRepository<IdmUserGroupRoleBranch> primaryDao() {
		return idmUserGroupRoleBranchDao;
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleBranch> search(UserGroupRoleBranch userGroupBranch) {
		return idmUserGroupRoleBranchQf.search(userGroupBranch);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_BRNCH_ALL", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleBranch> findAllUserGroups() {
		return idmUserGroupRoleBranchDao.findAll();
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_BRNCH_RG.concat(#s.userGroupCode)") }, put = {
							@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
									+ ".CACHE_KEY_USR_GRP_BRNCH.concat(#s.userGroupCode)") })
	public IdmUserGroupRoleBranch create(IdmUserGroupRoleBranch s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_BRNCH.concat(#s.userGroupCode)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_BRNCH_RG.concat(#s.userGroupCode)") })
	public IdmUserGroupRoleBranch update(IdmUserGroupRoleBranch s) {
		return super.update(s);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_BRNCH_RG.concat(#userGroupCode)", condition = "#userGroupCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmUserGroupRoleBranch> findByUserGroupRoleCode(String userGroupCode) {
		return idmUserGroupRoleBranchDao.findByUserGroupRoleCode(userGroupCode);
	}

}