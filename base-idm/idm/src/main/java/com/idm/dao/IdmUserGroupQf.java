package com.idm.dao;


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

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmUserGroup;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroup;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_QF)
public class IdmUserGroupQf extends QueryFactory<IdmUserGroup> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmUserGroup> searchByProperty(IdmUserGroup t) {
		return (Root<IdmUserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserGroup> searchAllByProperty(IdmUserGroup t) {
		CriteriaQuery<IdmUserGroup> cq = cb.createQuery(IdmUserGroup.class);
		Root<IdmUserGroup> from = cq.from(IdmUserGroup.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserGroup dto = JsonUtil.transferToObject(criteria, UserGroup.class);
			if (!BaseUtil.isObjNull(dto.getId())) {
				predicates.add(cb.equal(from.get("id"), dto.getId()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroupCode())) {
				predicates.add(cb.equal(from.get("userGroupCode"), dto.getUserGroupCode()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroupDesc())) {
				predicates.add(cb.equal(from.get("userGroupDesc"), dto.getUserGroupDesc()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
