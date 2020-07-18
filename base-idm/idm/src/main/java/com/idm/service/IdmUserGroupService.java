package com.idm.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmUserGroupDao;
import com.idm.dao.IdmUserGroupQf;
import com.idm.dao.IdmUserGroupRepository;
import com.idm.model.IdmMenu;
import com.idm.model.IdmUserGroup;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserMenu;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_USER_GROUP_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupService extends AbstractService<IdmUserGroup> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserGroupService.class);

	@Autowired
	IdmUserGroupRepository idmUserGroupRepository;
	
	@Autowired
	IdmUserGroupDao idmUserGroupDao;

	@Autowired
	IdmUserGroupQf idmUserGroupQf;


	@Override
	public GenericRepository<IdmUserGroup> primaryDao() {
		return idmUserGroupRepository;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmUserGroupQf.generateCriteria(cb, from, criteria);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroup> findAllUserGroups() {
		return idmUserGroupRepository.findAllUserGroups();
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_RG.concat(#s.userGroupCode)") }, put = {
							@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
									+ ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)") })
	public IdmUserGroup create(IdmUserGroup s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP.concat(#s.userGroupCode)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_RG.concat(#s.userGroupCode)") })
	public IdmUserGroup update(IdmUserGroup s) {
		return super.update(s);
	}


	public boolean delete(IdmUserGroup idmUserGroup) {
		try {
			this.primaryDao().delete(idmUserGroup);
			return true;
		} catch (Exception e) {
			LOGGER.error("IdmUserGroup : DELETE {} : {}", idmUserGroup.getUserGroupCode(), e);
			return false;
		}
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_RG.concat(#userGroupCode)", condition = "#userGroupCode != null", unless = "#result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmUserGroup findByUserGroupCode(String userGroupCode) {
		return idmUserGroupRepository.findByUserGroupCode(userGroupCode);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroup> searchUserGroup(UserGroup userGroup){
		return idmUserGroupDao.searchUserGroup(userGroup);
	}

}