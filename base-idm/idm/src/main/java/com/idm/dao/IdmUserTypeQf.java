package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
import com.idm.model.IdmUserType;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserType;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;


/**
 * @author mary.jane
 * @since 23 Nov 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_TYPE_QF)
public class IdmUserTypeQf extends QueryFactory<IdmUserType> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmUserType> searchByProperty(IdmUserType t) {
		return (Root<IdmUserType> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserType> searchAllByProperty(IdmUserType t) {
		CriteriaQuery<IdmUserType> cq = cb.createQuery(IdmUserType.class);
		Root<IdmUserType> from = cq.from(IdmUserType.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserType dto = JsonUtil.transferToObject(criteria, UserType.class);

			if (!BaseUtil.isObjNull(dto.getUserTypeCode())) {
				predicates.add(cb.equal(from.get("userTypeCode"), dto.getUserTypeCode()));
			}

			if (!BaseUtil.isObjNull(dto.getUserTypeDesc())) {
				predicates.add(cb.like(from.get("userTypeDesc"), "%" + dto.getUserTypeDesc() + "%"));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}


	public List<IdmUserType> searchByPagination(UserType t, DataTableRequest<?> dataTableInRQ) {
		
		try {
			CriteriaQuery<IdmUserType> cq = cb.createQuery(IdmUserType.class);
			Root<IdmUserType> from = cq.from(IdmUserType.class);
			IdmUserType dto = JsonUtil.transferToObject(t, IdmUserType.class);
			List<Predicate> predicates = generateCriteria(cb, from, dto);
	
			cq.select(from).distinct(true);
			cq.where(predicates.toArray(new Predicate[predicates.size()]));
	
			// Generate order by clause
			if (!BaseUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!BaseUtil.isObjNull(pagination)) {
					cq.orderBy(getOrderByClause(cb, from, pagination));
				}
			}
	
			TypedQuery<IdmUserType> tQuery = em.createQuery(cq);
	
			// first result & max Results
			if (!BaseUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!BaseUtil.isObjNull(pagination)) {
					tQuery.setFirstResult(pagination.getPageNumber());
					tQuery.setMaxResults(pagination.getPageSize());
				}
			}
	
			return tQuery.getResultList();
		} catch (IOException e){
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}
	}

}
