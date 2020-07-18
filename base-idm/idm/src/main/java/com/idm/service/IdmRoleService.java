package com.idm.service;


import java.io.IOException;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmRoleQf;
import com.idm.dao.IdmRoleRepository;
import com.idm.model.IdmRole;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.model.UserRole;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_ROLE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLE_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmRoleService extends AbstractService<IdmRole> {

	@Autowired
	private IdmRoleRepository idmRoleRepository;

	@Autowired
	private IdmRoleQf idmRoleQf;


	@Override
	public GenericRepository<IdmRole> primaryDao() {
		return idmRoleRepository;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmRoleQf.generateCriteria(cb, from, criteria);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmRole> search(UserRole userRole) throws IOException {
		IdmRole idmRole = JsonUtil.transferToObject(userRole, IdmRole.class);
		return idmRoleRepository.findAll(idmRoleQf.searchByPropertyIncludePortalType(idmRole));
	}

	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE.concat(#roleCode)", condition = "#roleCode != null and #result != null")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public IdmRole findByRoleCode(String roleCode) {
		return idmRoleRepository.findByRoleCode(roleCode);
	}


	@Override
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public List<IdmRole> findAll() {
		return idmRoleRepository.findAll();
	}

	public List<IdmRole> findAllContainsPortalType() {
		return idmRoleRepository.findAllIncludePortalType();
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_ALL"), }, put = {
					@CachePut(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE.concat(#s.roleCode)") })
	public IdmRole create(IdmRole s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE.concat(#s.roleCode)") })
	public IdmRole update(IdmRole s) {
		return super.update(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}

	public boolean delete(IdmRole idmRole) {
		try {
			this.primaryDao().delete(idmRole);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}