package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmOauthClientDetails;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_OAUTH_CLIENT_DETAILS_QF)
public class IdmOauthClientDetailsQf extends QueryFactory<IdmOauthClientDetails> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmOauthClientDetails> searchByProperty(IdmOauthClientDetails t) {
		return (Root<IdmOauthClientDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmOauthClientDetails> searchAllByProperty(IdmOauthClientDetails t) {
		CriteriaQuery<IdmOauthClientDetails> cq = cb.createQuery(IdmOauthClientDetails.class);
		Root<IdmOauthClientDetails> from = cq.from(IdmOauthClientDetails.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmOauthClientDetails dto = JsonUtil.transferToObject(criteria, IdmOauthClientDetails.class);

			if (!BaseUtil.isObjNull(dto.getClientId())) {
				predicates.add(cb.equal(from.get("clientId"), dto.getClientId()));
			}

			if (!BaseUtil.isObjNull(dto.getAccessTokenValidity())) {
				predicates.add(cb.equal(from.get("accessTokenValidity"), dto.getAccessTokenValidity()));
			}

			if (!BaseUtil.isObjNull(dto.getAdditionalInformation())) {
				predicates.add(cb.equal(from.get("additionalInformation"), dto.getAdditionalInformation()));
			}

			if (!BaseUtil.isObjNull(dto.getAuthorities())) {
				predicates.add(cb.equal(from.get("authorities"), dto.getAuthorities()));
			}

			if (!BaseUtil.isObjNull(dto.getAuthorizedGrantTypes())) {
				predicates.add(cb.equal(from.get("authorizedGrantTypes"), dto.getAuthorizedGrantTypes()));
			}

			if (!BaseUtil.isObjNull(dto.getAutoapprove())) {
				predicates.add(cb.equal(from.get("autoapprove"), dto.getAutoapprove()));
			}

			if (!BaseUtil.isObjNull(dto.getClientSecret())) {
				predicates.add(cb.equal(from.get("clientSecret"), dto.getClientSecret()));
			}

			if (!BaseUtil.isObjNull(dto.getRefreshTokenValidity())) {
				predicates.add(cb.equal(from.get("refreshTokenValidity"), dto.getRefreshTokenValidity()));
			}

			if (!BaseUtil.isObjNull(dto.getResourceIds())) {
				predicates.add(cb.equal(from.get("resourceIds"), dto.getResourceIds()));
			}

			if (!BaseUtil.isObjNull(dto.getScope())) {
				predicates.add(cb.equal(from.get("scope"), dto.getScope()));
			}

			if (!BaseUtil.isObjNull(dto.getWebServerRedirectUri())) {
				predicates.add(cb.equal(from.get("webServerRedirectUri"), dto.getWebServerRedirectUri()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

	
	public List<IdmOauthClientDetails> searchByPagination(IdmOauthClientDetails t, DataTableRequest<?> dataTableInRQ) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IdmOauthClientDetails> cq = cb.createQuery(IdmOauthClientDetails.class);
		Root<IdmOauthClientDetails> from = cq.from(IdmOauthClientDetails.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);

		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		// Generate order by clause
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				cq.orderBy(getOrderByClause(cb, from, pagination));
			}
		}

		TypedQuery<IdmOauthClientDetails> tQuery = em.createQuery(cq);

		// first result & max Results
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				tQuery.setFirstResult(pagination.getPageNumber());
				tQuery.setMaxResults(pagination.getPageSize());
			}
		}

		return tQuery.getResultList();
	}
}
