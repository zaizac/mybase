package com.be.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.be.constants.QualifierConstants;
import com.be.core.QueryFactory;
import com.be.model.RefCountry;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_COUNTRY_QF)
public class RefCountryQf extends QueryFactory<RefCountry> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<RefCountry> searchByProperty(RefCountry t) {
		return (Root<RefCountry> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefCountry> searchAllByProperty(RefCountry t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefCountry> cq = cb.createQuery(RefCountry.class);
		Root<RefCountry> statusRoot = cq.from(RefCountry.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			RefCountry dto = JsonUtil.transferToObject(criteria, RefCountry.class);

			if (!BaseUtil.isObjNull(dto.getCntryCd())) {
				predicates.add(cb.equal(from.get("cntryCd"), dto.getCntryCd()));
			}

			if (!BaseUtil.isObjNull(dto.getCntryDesc())) {
				predicates.add(cb.like(from.<String> get("cntryDesc"), "%" + dto.getCntryDesc() + "%"));
			}

			if (!BaseUtil.isObjNull(dto.getCntryInd())) {
				predicates.add(cb.equal(from.get("cntryInd"), dto.getCntryInd()));
			}

			if (!BaseUtil.isObjNull(dto.getCurrency())) {
				predicates.add(cb.equal(from.get("currency"), dto.getCurrency()));
			}

			if (!BaseUtil.isObjNull(dto.getIntlCallCd())) {
				predicates.add(cb.equal(from.<String> get("intlCallCd"), dto.getIntlCallCd()));
			}

			if (!BaseUtil.isObjNull(dto.getNtnltyDesc())) {
				predicates.add(cb.like(from.<String> get("ntnltyDesc"), "%" + dto.getNtnltyDesc() + "%"));
			}

		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}

}