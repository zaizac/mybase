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
import com.idm.dao.IdmUserTypeQf;
import com.idm.dao.IdmUserTypeRepository;
import com.idm.model.IdmUserType;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserType;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_USER_TYPE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserTypeService extends AbstractService<IdmUserType> {

	@Autowired
	private IdmUserTypeRepository userTypeDao;

	@Autowired
	private IdmUserTypeQf idmUserTypeQf;


	@Override
	public GenericRepository<IdmUserType> primaryDao() {
		return userTypeDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmUserTypeQf.generateCriteria(cb, from, criteria);
	}


	@Override
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmUserType> findAll() {
		return userTypeDao.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE.concat(#userTypeCode)", condition = "#userTypeCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmUserType findByUserTypeCode(String userTypeCode) {
		return userTypeDao.findByUserTypeCode(userTypeCode);
	}


	@Override
	@Caching(evict = { @CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_USER_TYPE_ALL"), }, put = {
					@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
							+ ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	public IdmUserType create(IdmUserType s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_USER_TYPE_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_USER_TYPE.concat(#s.userTypeCode)") })
	public IdmUserType update(IdmUserType s) {
		return super.update(s);
	}


	public boolean delete(IdmUserType idmPortalType) {
		try {
			this.primaryDao().delete(idmPortalType);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * This method returns boolean for config checking if the user will assign
	 * email as userId based on the userType
	 *
	 * @param userType
	 * @return boolean
	 */
	public boolean checkEmailAsUserId(String userType) {
		IdmUserType idmUserType = findByUserTypeCode(userType);
		boolean isEmailAsId = false;
		if (!BaseUtil.isObjNull(idmUserType)) {
			isEmailAsId = idmUserType.getEmailAsUserId();
		}
		return isEmailAsId;
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserType> searchByPagination(UserType t, DataTableRequest<?> dataTableInRQ) {
		try {
			List<IdmUserType> result = idmUserTypeQf.searchByPagination(t, dataTableInRQ);
			return JsonUtil.transferToList(result, UserType.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdmException(e.getMessage());
		}
	}

}