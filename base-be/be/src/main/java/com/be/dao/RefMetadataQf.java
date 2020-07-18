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
import com.be.model.RefMetadata;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.be.sdk.model.IQfCriteria;
import com.be.sdk.model.Metadata;
import com.util.BaseUtil;
import com.util.JsonUtil;


/**
 * @author Ilhomjon Abdu
 * @since 29 Oct 2019
 */
@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_METADATA_QF)
public class RefMetadataQf extends QueryFactory<RefMetadata> {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Specification<RefMetadata> searchByProperty(RefMetadata t) {
		return (Root<RefMetadata> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<RefMetadata> searchAllByProperty(RefMetadata t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RefMetadata> cq = cb.createQuery(RefMetadata.class);
		Root<RefMetadata> from = cq.from(RefMetadata.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();

	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			Metadata dto = JsonUtil.transferToObject(criteria, Metadata.class);
			if (!BaseUtil.isObjNull(dto.getMtdtId())) {
				predicates.add(cb.equal(from.get("mtdtId"), dto.getMtdtId()));
			}

			if (!BaseUtil.isObjNull(dto.getMtdtType())) {
				predicates.add(cb.like(from.<String> get("mtdtType"), '%' + dto.getMtdtType() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getMtdtCd())) {
				predicates.add(cb.like(from.<String> get("mtdtCd"), '%' + dto.getMtdtCd() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getMtdtDesc())) {
				predicates.add(cb.like(from.<String> get("mtdtDesc"), '%' + dto.getMtdtDesc() + '%'));
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
