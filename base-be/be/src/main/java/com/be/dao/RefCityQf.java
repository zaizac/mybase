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
import com.be.model.RefCity;
import com.be.model.RefState;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.service.RefStateService;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_CITY_QF)
public class RefCityQf extends QueryFactory<RefCity> {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RefStateService refStateSvc;


	@Override
	public Specification<RefCity> searchByProperty(final RefCity t) {
		return (Root<RefCity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefCity> searchAllByProperty(RefCity t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefCity> cq = cb.createQuery(RefCity.class);
		Root<RefCity> statusRoot = cq.from(RefCity.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			RefCity dto = JsonUtil.transferToObject(criteria, RefCity.class);

			if (!BaseUtil.isObjNull(dto.getCityId())) {
				predicates.add(cb.equal(from.get("cityId"), dto.getCityId()));
			}

			if (!BaseUtil.isObjNull(dto.getCityCd())) {
				predicates.add(cb.equal(from.get("cityCd"), dto.getCityCd()));
			}

			if (!BaseUtil.isObjNull(dto.getCityDesc())) {
				predicates.add(cb.like(from.<String> get("cityDesc"), "%" + dto.getCityDesc() + "%"));
			}

			if (!BaseUtil.isObjNull(dto.getState())) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Join<RefCity, RefState> stateJoin = (Join) from.fetch("state", JoinType.LEFT);
				predicates.addAll(refStateSvc.generateCriteria(cb, stateJoin, dto.getState()));
			}

		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}
		return predicates;
	}

}