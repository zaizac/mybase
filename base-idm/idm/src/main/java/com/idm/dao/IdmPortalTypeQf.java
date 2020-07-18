package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.idm.model.IdmPortalType;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.PortalType;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PORTAL_TYPE_QF)
public class IdmPortalTypeQf extends QueryFactory<IdmPortalType> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmPortalType> searchByProperty(IdmPortalType t) {
		return (Root<IdmPortalType> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmPortalType> searchAllByProperty(IdmPortalType t) {
		CriteriaQuery<IdmPortalType> cq = cb.createQuery(IdmPortalType.class);
		Root<IdmPortalType> from = cq.from(IdmPortalType.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmPortalType dto = JsonUtil.transferToObject(criteria, IdmPortalType.class);

			if (!BaseUtil.isObjNull(dto.getPortalTypeCode())) {
				predicates.add(cb.equal(from.get("portalTypeCode"), dto.getPortalTypeCode()));
			}

			if (!BaseUtil.isObjNull(dto.getPortalTypeDesc())) {
				predicates.add(cb.like(from.get("portalTypeDesc"), "%" + dto.getPortalTypeDesc() + "%"));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
