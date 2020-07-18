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
import com.idm.dao.IdmUserGroupRoleQf;
import com.idm.dao.IdmUserGroupRoleRepository;
import com.idm.model.IdmUserGroupRole;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroupRole;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_USER_GROUP_ROLE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupRoleService extends AbstractService<IdmUserGroupRole> {

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_DAO)
	private IdmUserGroupRoleRepository idmUserGroupRoleDao;
	
	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_QF)
	private IdmUserGroupRoleQf idmUserGroupRoleQf;


	@Override
	public GenericRepository<IdmUserGroupRole> primaryDao() {
		return idmUserGroupRoleDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ROLE_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRole> findAllUserGroup() {
		return idmUserGroupRoleDao.findAllUserGroupRoles();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ROLE.concat(#roleCode)", condition = "#roleCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRole> findUserGroupByRoleCode(String roleCode) {
		return idmUserGroupRoleDao.findByRoleRoleCode(roleCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#userRoleGroupCode)", condition = "#userRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupRole> findUserGroupByUserRoleGroupCode(String userRoleGroupCode) {
		return idmUserGroupRoleDao.findByUserGroupUserGroupCode(userRoleGroupCode);
	}


	@Override
	@Caching(evict = {@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#s.id)") }, 
	put = {	@CachePut(key = ConfigConstants.CACHE_JAVA_FILE	+ ".CACHE_KEY_USR_GRP_ROLE.concat(#s.id)") })
	public IdmUserGroupRole create(IdmUserGroupRole s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE.concat(#s.id)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_ROLE_RG.concat(#s.id)") })
	public IdmUserGroupRole update(IdmUserGroupRole s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_ALL") })
	public boolean delete(Integer id) {
		return super.delete(id);
	}


	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ROLE.concat(#groupCode)") })
	public IdmUserGroupRole findUserGroupByRoleAndGroup(String roleCode, String groupCode) {
		return idmUserGroupRoleDao.findUserGroupByRoleAndGroupCode(roleCode, groupCode);
	}


	public List<IdmUserGroupRole> findUserGroupByUserRoleGroupCodeInstant(String userRoleGroupCode) {
		return idmUserGroupRoleDao.findByUserGroupUserGroupCode(userRoleGroupCode);
	}


	public List<String> findAllRolesByUserRoleGroupCode(String userRoleGroupCode) {
		return idmUserGroupRoleDao.findRoleRoleCodeByUserGroupUserGroupCode(userRoleGroupCode);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserGroupRole> searchByPagination(UserGroupRole t, DataTableRequest<?> dataTableInRQ) {
		try {
			List<IdmUserGroupRole> result = idmUserGroupRoleQf.searchByPagination(JsonUtil.transferToObject(t, IdmUserGroupRole.class), dataTableInRQ);
			return JsonUtil.transferToList(result, UserGroupRole.class);
		} catch (Exception e) {
			throw new IdmException(e.getMessage());
		}
	}
	
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmUserGroupRole findByGroupRoleId(Integer id) {
		return idmUserGroupRoleDao.findByGroupRoleId(id);
	}
}