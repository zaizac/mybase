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
import com.idm.dao.IdmOauthClientDetailsQf;
import com.idm.dao.IdmOauthClientDetailsRepository;
import com.idm.model.IdmOauthClientDetails;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.OauthClientDetails;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_OAUTH_CLIENT_DETAILS_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_OAUTH_CLIENT_DETAILS_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmOauthClientDetailsService extends AbstractService<IdmOauthClientDetails> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmOauthClientDetailsService.class);

	@Autowired
	IdmOauthClientDetailsRepository idmOauthClientDetailsDao;

	@Autowired
	IdmOauthClientDetailsQf idmOauthClientDetailsQf;


	@Override
	public GenericRepository<IdmOauthClientDetails> primaryDao() {
		return idmOauthClientDetailsDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmOauthClientDetailsQf.generateCriteria(cb, from, criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<OauthClientDetails> searchByPagination(OauthClientDetails t, DataTableRequest<?> dataTableInRQ) {
		try {
			List<IdmOauthClientDetails> result = idmOauthClientDetailsQf.searchByPagination(JsonUtil.transferToObject(t, IdmOauthClientDetails.class), dataTableInRQ);
			return JsonUtil.transferToList(result, OauthClientDetails.class);
		} catch (Exception e) {
			throw new IdmException(e.getMessage());
		}
	}


	public IdmOauthClientDetails findByClientId(String clientId) {
		return idmOauthClientDetailsDao.findByClientId(clientId);
	}


	public boolean delete(IdmOauthClientDetails idmOauthClientDetails) {
		try {
			this.primaryDao().delete(idmOauthClientDetails);
			return true;
		} catch (Exception e) {
			LOGGER.error("IdmOauthClientDetails : DELETE {} : {}", idmOauthClientDetails.getClientId(), e);
			return false;
		}
	}

}
