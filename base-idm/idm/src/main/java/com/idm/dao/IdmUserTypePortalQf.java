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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmPortalType;
import com.idm.model.IdmUserType;
import com.idm.model.IdmUserTypePortal;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.service.IdmPortalTypeService;
import com.idm.service.IdmUserTypeService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since 22 Nov 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_PORTAL_QF)
public class IdmUserTypePortalQf extends QueryFactory<IdmUserTypePortal> {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	IdmUserTypeService idmUserTypeSvc;

	@Autowired
	IdmPortalTypeService idmPortalTypeSvc;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmUserTypePortal> searchByProperty(IdmUserTypePortal t) {
		return (Root<IdmUserTypePortal> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserTypePortal> searchAllByProperty(IdmUserTypePortal t) {
		CriteriaQuery<IdmUserTypePortal> cq = cb.createQuery(IdmUserTypePortal.class);
		Root<IdmUserTypePortal> from = cq.from(IdmUserTypePortal.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmUserTypePortal dto = JsonUtil.transferToObject(criteria, IdmUserTypePortal.class);

			if (!BaseUtil.isObjNull(dto.getUserPortalId())) {
				predicates.add(cb.equal(from.get("userPortalId"), dto.getUserPortalId()));
			}

			if (!BaseUtil.isObjNull(dto.getUserType())) {
				Join<IdmUserTypePortal, IdmUserType> utJoin = (Join) from.fetch("userType", JoinType.LEFT);
				predicates.addAll(idmUserTypeSvc.generateCriteria(cb, utJoin, dto.getUserType()));
			}

			if (!BaseUtil.isObjNull(dto.getPortalType())) {
				Join<IdmUserTypePortal, IdmPortalType> ptJoin = (Join) from.fetch("portalType", JoinType.LEFT);
				predicates.addAll(idmPortalTypeSvc.generateCriteria(cb, ptJoin, dto.getPortalType()));
			}

			if (!BaseUtil.isObjNull(dto.getCreateId())) {
				predicates.add(cb.equal(from.get("createId"), dto.getCreateId()));
			}

			if (!BaseUtil.isObjNull(dto.getUpdateId())) {
				predicates.add(cb.equal(from.get("updateId"), dto.getUpdateId()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
