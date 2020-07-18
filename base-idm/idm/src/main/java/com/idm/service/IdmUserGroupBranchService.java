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
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmUserGroupBranchQf;
import com.idm.dao.IdmUserGroupBranchRepository;
import com.idm.model.IdmUserGroupBranch;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.model.UserGroupBranch;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_USER_GROUP_BRANCH_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_BRANCH_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserGroupBranchService extends AbstractService<IdmUserGroupBranch> {

	@Autowired
	IdmUserGroupBranchRepository idmUserGroupBranchDao;

	@Autowired
	IdmUserGroupBranchQf idmUserGroupBranchQf;


	@Override
	public GenericRepository<IdmUserGroupBranch> primaryDao() {
		return idmUserGroupBranchDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmUserGroupBranchQf.generateCriteria(cb, from, criteria);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupBranch> search(IdmUserGroupBranch idmUserGroupBranch) throws IOException {
		return idmUserGroupBranchDao.findAll(idmUserGroupBranchQf.searchByProperty(idmUserGroupBranch));
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USR_GRP_BRNCH_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserGroupBranch> findAllUserGroups() {
		return idmUserGroupBranchDao.findAllIncludeUserType();
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_BRNCH_RG.concat(#s.branchCode)") }, put = {
							@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
									+ ".CACHE_KEY_USR_GRP_BRNCH.concat(#s.branchCode)") })
	public IdmUserGroupBranch create(IdmUserGroupBranch s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_ALL"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USR_GRP_BRNCH.concat(#s.branchCode)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USR_GRP_BRNCH_RG.concat(#s.branchCode)") })
	public IdmUserGroupBranch update(IdmUserGroupBranch s) {
		return super.update(s);
	}

	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}

	public boolean delete(IdmUserGroupBranch idmUserGroupBranch) {
		try {
			this.primaryDao().delete(idmUserGroupBranch);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}