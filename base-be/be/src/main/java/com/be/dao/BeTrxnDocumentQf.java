/**
 * Copyright 2019. Universal Recruitment Platform
 */
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
import com.be.model.BeTrxnDocument;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.sdk.model.TrxnDocuments;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author mary.jane
 *
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_TRXN_DOC_QF)
public class BeTrxnDocumentQf extends QueryFactory<BeTrxnDocument> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<BeTrxnDocument> searchByProperty(final BeTrxnDocument t) {
		return (Root<BeTrxnDocument> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<BeTrxnDocument> searchAllByProperty(BeTrxnDocument t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BeTrxnDocument> cq = cb.createQuery(BeTrxnDocument.class);
		Root<BeTrxnDocument> statusRoot = cq.from(BeTrxnDocument.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			TrxnDocuments dto = JsonUtil.transferToObject(criteria, TrxnDocuments.class);

			if (!BaseUtil.isObjNull(dto.getDocRefNo())) {
				predicates.add(cb.equal(from.get("docRefNo"), dto.getDocRefNo()));
			}

			if (!BaseUtil.isObjNull(dto.getDocId())) {
				predicates.add(cb.equal(from.get("docId"), dto.getDocId()));
			}

			if (!BaseUtil.isObjNull(dto.getDocMgtId())) {
				predicates.add(cb.equal(from.get("docMgtId"), dto.getDocMgtId()));
			}

			if (!BaseUtil.isObjNull(dto.getAppRefNo())) {
				predicates.add(cb.equal(from.get("appRefNo"), dto.getAppRefNo()));
			}

			if (!BaseUtil.isObjNull(dto.getAppType())) {
				predicates.add(cb.equal(from.get("appType"), dto.getAppType()));
			}

			if (!BaseUtil.isObjNull(dto.getDocContentType())) {
				predicates.add(cb.like(from.<String> get("docContentType"), "%" + dto.getDocContentType() + "%"));
			}
		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}

}
