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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmUserTypePortalQf;
import com.idm.dao.IdmUserTypePortalRepository;
import com.idm.model.IdmUserTypePortal;
import com.idm.sdk.constants.IdmCacheConstants;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since 22 Nov 2019
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_USER_TYPE_PORTAL_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_PORTAL_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmUserTypePortalService extends AbstractService<IdmUserTypePortal> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserTypePortalService.class);

	@Autowired
	private IdmUserTypePortalRepository idmUserTypePortalDao;

	@Autowired
	private IdmUserTypePortalQf idmUserTypePortalQf;


	@Override
	public GenericRepository<IdmUserTypePortal> primaryDao() {
		return idmUserTypePortalDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmUserTypePortalQf.generateCriteria(cb, from, criteria);
	}


	public List<IdmUserTypePortal> searchByProperty(IdmUserTypePortal t) {
		return idmUserTypePortalDao.findAll(idmUserTypePortalQf.searchByProperty(t));
	}


	public List<IdmUserTypePortal> findByUserTypeUserTypeCode(String userTypeCode) {
		return idmUserTypePortalDao.findByUserTypeUserTypeCode(userTypeCode);
	}


	public List<IdmUserTypePortal> findByPortalTypePortalTypeCode(String portalTypeCode) {
		return idmUserTypePortalDao.findByPortalTypePortalTypeCode(portalTypeCode);
	}

}
