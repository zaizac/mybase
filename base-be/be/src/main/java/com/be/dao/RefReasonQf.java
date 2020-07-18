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
import org.springframework.util.StringUtils;

import com.be.constants.QualifierConstants;
import com.be.core.QueryFactory;
import com.be.model.RefReason;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.sdk.model.Reason;
import com.util.BaseUtil;
import com.util.JsonUtil;


@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_REASON_QF)
public class RefReasonQf extends QueryFactory<RefReason> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<RefReason> searchByProperty(RefReason t) {
		return (Root<RefReason> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefReason> searchAllByProperty(RefReason t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefReason> cq = cb.createQuery(RefReason.class);
		Root<RefReason> statusRoot = cq.from(RefReason.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			Reason dto = JsonUtil.transferToObject(criteria, Reason.class);

			if (!BaseUtil.isObjNull(dto.getReasonId())) {
				predicates.add(cb.equal(from.get("reasonId"), dto.getReasonId()));
			}

			if (StringUtils.hasText(dto.getReasonType())) {
				predicates.add(cb.equal(from.<String> get("reasonType"), dto.getReasonType()));
			}

			if (StringUtils.hasText(dto.getReasonCd())) {
				predicates.add(cb.equal(from.<String> get("reasonCd"), dto.getReasonCd()));
			}

			if (StringUtils.hasText(dto.getReasonDesc())) {
				predicates.add(cb.like(from.<String> get("reasonDesc"), '%' + dto.getReasonDesc() + '%'));
			}

			if (dto.isStatus()) {
				predicates.add(cb.equal(from.get("status"), dto.isStatus()));
			}
		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}
}
