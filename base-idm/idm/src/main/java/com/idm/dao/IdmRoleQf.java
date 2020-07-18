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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmRole;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.model.LangDesc;


/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLE_QF)
public class IdmRoleQf extends QueryFactory<IdmRole> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;
	
	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmRole> searchByProperty(IdmRole t) {
		return (Root<IdmRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	public Specification<IdmRole> searchByPropertyIncludePortalType(IdmRole t) {
		return (Root<IdmRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				root.fetch("portalType");
				return query.where(predLst.toArray(new Predicate[predLst.size()]))
						.getRestriction();
			}

			return query.getRestriction();
		};
	}
	
	
	@Override
	public List<IdmRole> searchAllByProperty(IdmRole t) {
		CriteriaQuery<IdmRole> cq = cb.createQuery(IdmRole.class);
		Root<IdmRole> from = cq.from(IdmRole.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmRole dto = JsonUtil.transferToObject(criteria, IdmRole.class);

			if (!BaseUtil.isObjNull(dto.getPortalType()) && !BaseUtil.isObjNull(dto.getPortalType().getPortalTypeCode())) {
				predicates.add(cb.equal(from.get("portalType"), dto.getPortalType()));
			}

			if (!BaseUtil.isObjNull(dto.getRoleCode())) {
				predicates.add(cb.equal(from.get("roleCode"), dto.getRoleCode()));
			}

			if (!BaseUtil.isObjNull(dto.getRoleDesc())) {
				/**
				 * Note: Usage as below does not works
				 * 1. predicates.add(cb.like(from.get("roleDesc"), "%" + dto.getRoleDesc() + "%"));
				 * thrown : java.lang.IllegalArgumentException: Parameter value [%com.util.model.LangDesc@448be340%] did not match expected type [com.util.model.LangDesc (n/a)]
				 * 2. predicates.add(cb.like(from.get("roleDesc.en"), "%" + dto.getRoleDesc().getEn() + "%"));
				 * thrown: Unable to locate Attribute  with the the given name [roleDesc.en] on this ManagedType [com.idm.model.IdmRole]
				 */
				// Use Path Expression below for attribute with type LangDesc
				// * LangDesc does not belongs to entity attribute
				Path<LangDesc> roleDesc = from.get("roleDesc");
				if (dto.getRoleDesc().getEn() != null) {
					predicates.add(cb.like( roleDesc.as(String.class), "%" + dto.getRoleDesc().getEn() + "%"));
				} else if (dto.getRoleDesc().getBd() != null ) {
					predicates.add(cb.like( roleDesc.as(String.class), "%" + dto.getRoleDesc().getBd() + "%"));
				} else if (dto.getRoleDesc().getMy() != null ) {
					predicates.add(cb.like( roleDesc.as(String.class), "%" + dto.getRoleDesc().getMy() + "%"));
				} else if (dto.getRoleDesc().getIn() != null ) {
					predicates.add(cb.like( roleDesc.as(String.class), "%" + dto.getRoleDesc().getIn() + "%"));
				}
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
