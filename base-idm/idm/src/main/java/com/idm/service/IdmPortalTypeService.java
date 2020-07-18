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

import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmPortalTypeQf;
import com.idm.dao.IdmPortalTypeRepository;
import com.idm.model.IdmPortalType;
import com.idm.sdk.constants.IdmCacheConstants;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_PORTAL_TYPE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PORTAL_TYPE_SVC)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmPortalTypeService extends AbstractService<IdmPortalType> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmPortalTypeService.class);

	@Autowired
	IdmPortalTypeRepository idmPortalTypeDao;

	@Autowired
	IdmPortalTypeQf idmPortalTypeQf;


	@Override
	public GenericRepository<IdmPortalType> primaryDao() {
		return idmPortalTypeDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmPortalTypeQf.generateCriteria(cb, from, criteria);
	}


	public IdmPortalType findByPortalTypeCode(String portalTypeCode) {
		return idmPortalTypeDao.findByPortalTypeCode(portalTypeCode);
	}


	public boolean delete(IdmPortalType idmPortalType) {
		try {
			this.primaryDao().delete(idmPortalType);
			return true;
		} catch (Exception e) {
			LOGGER.error("IdmPortalTypeService : DELETE {} : {}", idmPortalType.getPortalTypeCode(), e);
			return false;
		}
	}

}
