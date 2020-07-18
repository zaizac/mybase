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
import com.be.model.RefDocument;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.Document;
import com.be.sdk.model.IQfCriteria;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author Ilhomjon Abdu
 * @since 29 Oct 2019
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_DOCUMENT_QF)
public class RefDocumentQf extends QueryFactory<RefDocument> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<RefDocument> searchByProperty(RefDocument t) {
		return (Root<RefDocument> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefDocument> searchAllByProperty(RefDocument t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefDocument> cq = cb.createQuery(RefDocument.class);
		Root<RefDocument> statusRoot = cq.from(RefDocument.class);
		List<Predicate> predicates = generateCriteria(cb, statusRoot, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();

	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			Document dto = JsonUtil.transferToObject(criteria, Document.class);

			if (!BaseUtil.isObjNull(dto.getDocId())) {
				predicates.add(cb.equal(from.get("docId"), dto.getDocId()));
			}

			if (StringUtils.hasText(dto.getDmBucket())) {
				predicates.add(cb.equal(from.<String> get("dmBucket"), dto.getDmBucket()));
			}

			if (StringUtils.hasText(dto.getDocTrxnNo())) {
				predicates.add(cb.like(from.<String> get("docTrxnNo"), '%' + dto.getDocTrxnNo() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getDocDesc())) {
				predicates.add(cb.like(from.<String> get("docDesc"), '%' + dto.getDocDesc() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getOrder())) {
				predicates.add(cb.equal(from.get("order"), dto.getOrder()));
			}

			if (StringUtils.hasText(dto.getType())) {
				predicates.add(cb.like(from.<String> get("type"), '%' + dto.getType() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getSize())) {
				predicates.add(cb.equal(from.get("size"), dto.getSize()));
			}

			if (dto.isCompulsary()) {
				predicates.add(cb.equal(from.get("compulsary"), dto.isCompulsary()));
			}

			if (dto.isDimensionCompulsary()) {
				predicates.add(cb.equal(from.get("dimensionCompulsary"), dto.isDimensionCompulsary()));
			}
		} catch (IOException e) {
			throw new BeException(BeErrorCodeEnum.E400C912);
		}

		return predicates;
	}

}
