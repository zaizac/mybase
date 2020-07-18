/**
 * 
 */
package com.idm.dao;

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
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmAuditAction;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;

/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.AUDIT_ACTION_QF)
public class IdmAuditActionQf  extends QueryFactory<IdmAuditAction> {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Specification<IdmAuditAction> searchByProperty(final IdmAuditAction t) {
		return (Root<IdmAuditAction> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}

	@Override
	public List<IdmAuditAction> searchAllByProperty(IdmAuditAction t) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<IdmAuditAction> cq = cb.createQuery(IdmAuditAction.class);
		Root<IdmAuditAction> from = cq.from(IdmAuditAction.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmAuditAction dto = JsonUtil.transferToObject(criteria, IdmAuditAction.class);
			if (!BaseUtil.isObjNull(dto.getAuditId())) {
				predicates.add(cb.equal(from.get("auditId"), dto.getAuditId()));
			}
			
			if (!BaseUtil.isObjNull(dto.getPortal())) {
				predicates.add(cb.equal(from.get("portal"), dto.getPortal()));
			}

			if (!BaseUtil.isObjNull(dto.getPage())) {
				predicates.add(cb.equal(from.get("page"), dto.getPage()));
			}

			if (!BaseUtil.isObjNull(dto.getUserId())) {
				predicates.add(cb.equal(from.get("userId"), dto.getUserId()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
