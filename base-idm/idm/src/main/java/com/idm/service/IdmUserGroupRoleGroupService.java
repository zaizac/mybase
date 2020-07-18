package com.idm.service;


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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmUserGroupRoleGroupQf;
import com.idm.dao.IdmUserGroupRoleGroupRepository;
import com.idm.model.IdmUserGroupRoleGroup;
import com.idm.sdk.constants.IdmCacheConstants;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
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


	@Override
	public GenericRepository<IdmUserGroupRoleGroup> primaryDao() {
		return idmUserGroupDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findAllUserGroup() {
		return idmUserGroupDao.findAllUserGroup();
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCode(String userRoleGroupCode, String systemType) {
		return idmUserGroupDao.findUserGroupByRoleGroupCodeAndSystemType(userRoleGroupCode, systemType);
	}
	
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_RG.concat(#userRoleGroupCode)", condition = "#userRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByRoleGroupCode(String userRoleGroupCode) {
		return idmUserGroupDao.findUserGroupByRoleGroupCode(userRoleGroupCode);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String userRoleGroupCode, String systemType, boolean noSystemCreate,
			String currUserId) {
		return idmUserGroupRoleGroupQf.findUserGroupByParentRoleGroupCriteria(userRoleGroupCode, systemType, noSystemCreate,
				currUserId);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP.concat(#userGroupCode)", condition = "#userGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByGroupCode(String userGroupCode) {
		return idmUserGroupDao.findUserGroupByGroupCode(userGroupCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_RG.concat(#userRoleGroupCode)", condition = "#userRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String parentRoleGroup) {
		return idmUserGroupDao.findUserGroupByParentRoleGroup(parentRoleGroup);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String parentRoleGroup, String systemType) {
		return idmUserGroupDao.findUserGroupByParentRoleGroupAndSystemType(parentRoleGroup, systemType);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroups(List<String> parentRoleGroup) {
		return idmUserGroupDao.findUserGroupByParentRoleGroups(parentRoleGroup);
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