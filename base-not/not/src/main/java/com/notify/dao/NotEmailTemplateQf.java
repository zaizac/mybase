package com.notify.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.notify.constants.QualifierConstants;
import com.notify.core.QueryFactory;
import com.notify.model.NotEmailTemplate;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author hafidz
 * @since March 4, 2020
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_QF)
public class NotEmailTemplateQf extends QueryFactory<NotEmailTemplate> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;
	
	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<NotEmailTemplate> searchByProperty(NotEmailTemplate t) {
		return (Root<NotEmailTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<NotEmailTemplate> searchAllByProperty(NotEmailTemplate t) {
		CriteriaQuery<NotEmailTemplate> cq = cb.createQuery(NotEmailTemplate.class);
		Root<NotEmailTemplate> from = cq.from(NotEmailTemplate.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			NotEmailTemplate dto = JsonUtil.transferToObject(criteria, NotEmailTemplate.class);

			if (!BaseUtil.isObjNull(dto.getId())) {
				predicates.add(cb.equal(from.get("id"), dto.getId()));
			}

			if (!BaseUtil.isObjNull(dto.getTemplateType())) {
				predicates.add(cb.equal(from.get("templateType"), dto.getTemplateType()));
			}

			if (!BaseUtil.isObjNull(dto.getTemplateCode())) {
				predicates.add(cb.equal(from.get("templateCode"), dto.getTemplateCode()));
			}

			if (!BaseUtil.isObjNull(dto.getTemplateDesc())) {
				predicates.add(cb.equal(from.get("templateDesc"), dto.getTemplateDesc()));
			}

			if (!BaseUtil.isObjNull(dto.getEmailContent())) {
				predicates.add(cb.equal(from.get("emailContent"), dto.getEmailContent()));
			}
			
			if (!BaseUtil.isObjNull(dto.getEmailSubject())) {
				predicates.add(cb.equal(from.get("emailSubject"), dto.getEmailSubject()));
			}

			if (!BaseUtil.isObjNull(dto.getEmailVariable())) {
				predicates.add(cb.equal(from.get("emailVariable"), dto.getEmailVariable()));
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
