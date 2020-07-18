package com.be.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.be.constants.QualifierConstants;
import com.be.core.QueryFactory;
import com.be.model.RefCountry;
import com.be.model.RefState;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.service.RefCountryService;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATE_QF)
public class RefStateQf extends QueryFactory<RefState> {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RefCountryService refCountrySvc;


	@Override
	public Specification<RefState> searchByProperty(final RefState t) {
		return (Root<RefState> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefState> searchAllByProperty(RefState t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefState> cq = cb.createQuery(RefState.class);
		Root<RefState> statusRoot = cq.from(RefState.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			RefState dto = JsonUtil.transferToObject(criteria, RefState.class);

			if (!BaseUtil.isObjNull(dto.getStateId())) {
				predicates.add(cb.equal(from.get("stateId"), dto.getStateId()));
			}

			if (!BaseUtil.isObjNull(dto.getStateCd())) {
				predicates.add(cb.equal(from.get("stateCd"), dto.getStateCd()));
			}

			if (!BaseUtil.isObjNull(dto.getStateDesc())) {
				predicates.add(cb.like(from.<String> get("stateDesc"), "%" + dto.getStateDesc() + "%"));
			}

			if (!BaseUtil.isObjNull(dto.getCountry())) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Join<RefState, RefCountry> countryJoin = (Join) from.fetch("country", JoinType.LEFT);
				predicates.addAll(refCountrySvc.generateCriteria(cb, countryJoin, dto.getCountry()));
			}

		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}

}