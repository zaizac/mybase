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
import com.be.model.RefStatus;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.sdk.model.Status;
import com.util.BaseUtil;
import com.util.JsonUtil;


@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATUS_CUSTOM_DAO)
public class RefStatusQf extends QueryFactory<RefStatus> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<RefStatus> searchByProperty(final RefStatus t) {
		return (Root<RefStatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefStatus> searchAllByProperty(RefStatus t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefStatus> cq = cb.createQuery(RefStatus.class);
		Root<RefStatus> statusRoot = cq.from(RefStatus.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			Status dto = JsonUtil.transferToObject(criteria, Status.class);

			if (!BaseUtil.isObjNull(dto)) {
				if (!BaseUtil.isObjNull(dto.getStatusId())) {
					predicates.add(cb.equal(from.get("statusId"), dto.getStatusId()));
				}

				if (!BaseUtil.isObjNull(dto.getStatusType())) {
					predicates.add(cb.equal(from.get("statusType"), dto.getStatusType()));
				}

				if (!BaseUtil.isObjNull(dto.getStatusCd())) {
					predicates.add(cb.equal(from.get("statusCd"), dto.getStatusCd()));
				}

				if (!BaseUtil.isObjNull(dto.getStatusDesc())) {
					predicates.add(cb.like(from.get("statusDesc"), "%" + dto.getStatusDesc() + "%"));
				}

				if (!BaseUtil.isObjNull(dto.isActive())) {
					predicates.add(cb.equal(from.get("status"), dto.isActive()));
				}

			}
		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}

}
