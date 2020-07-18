package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmRole;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroupRole;
import com.idm.service.IdmRoleService;
import com.idm.service.IdmUserGroupService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;


/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_QF)
public class IdmUserGroupRoleQf extends QueryFactory<IdmUserGroupRole> {

	@Autowired
	IdmUserGroupService idmUserGroupSvc;

	@Autowired
	IdmRoleService idmRoleSvc;

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmUserGroupRole> searchByProperty(IdmUserGroupRole t) {
		return (Root<IdmUserGroupRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserGroupRole> searchAllByProperty(IdmUserGroupRole t) {
		CriteriaQuery<IdmUserGroupRole> cq = cb.createQuery(IdmUserGroupRole.class);
		Root<IdmUserGroupRole> from = cq.from(IdmUserGroupRole.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserGroupRole dto = JsonUtil.transferToObject(criteria, UserGroupRole.class);
			if (!BaseUtil.isObjNull(dto.getId())) {
				predicates.add(cb.equal(from.get("id"), dto.getId()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroup())) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Join<IdmUserGroupRole, IdmUserGroup> userGrpJoin = (Join) from.fetch("userGroup", JoinType.LEFT);
				predicates.addAll(idmUserGroupSvc.generateCriteria(cb, userGrpJoin, dto.getUserGroup()));
			}

			if (!BaseUtil.isObjNull(dto.getRole())) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Join<IdmUserGroupRole, IdmRole> countryJoin = (Join) from.fetch("role", JoinType.LEFT);
				predicates.addAll(idmRoleSvc.generateCriteria(cb, countryJoin, new IdmRole()));
			}

			if (!BaseUtil.isObjNull(dto.getCreateId())) {
				predicates.add(cb.equal(from.get("createId"), dto.getCreateId()));
			}

			if (dto.getCreateDtFrom() != null && dto.getCreateDtTo() != null) {
				Expression<java.util.Date> date = from.get("createDt");
				Calendar cal = Calendar.getInstance();
				cal.setTime(dto.getCreateDtTo());
				cal.add(Calendar.HOUR, 23);
				cal.add(Calendar.SECOND, 3599);
				// TODO: dto.setCreateDtTo(cal.getTime());
				predicates.add(cb.between(date, dto.getCreateDtFrom(), dto.getCreateDtTo()));
			} else if (dto.getCreateDtFrom() != null) {
				Expression<java.util.Date> date = from.get("createDt");
				predicates.add(cb.greaterThanOrEqualTo(date, dto.getCreateDtFrom()));
			} else if (dto.getCreateDtTo() != null) {
				Expression<java.util.Date> date = from.get("createDt");
				predicates.add(cb.lessThanOrEqualTo(date, dto.getCreateDtTo()));
			}

			if (!BaseUtil.isObjNull(dto.getUpdateId())) {
				predicates.add(cb.equal(from.get("updateId"), dto.getUpdateId()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}


	public List<IdmUserGroupRole> searchByPagination(IdmUserGroupRole ur, DataTableRequest<?> dataTableInRQ) {
		CriteriaQuery<IdmUserGroupRole> cq = cb.createQuery(IdmUserGroupRole.class);
		Root<IdmUserGroupRole> from = cq.from(IdmUserGroupRole.class);
		List<Predicate> predicates = generateCriteriaPagination(cb, from, ur);

		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		// Generate order by clause
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				cq.orderBy(getOrderByClause(cb, from, pagination));
			}
		}

		TypedQuery<IdmUserGroupRole> tQuery = em.createQuery(cq);

		// first result & max Results
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				tQuery.setFirstResult(pagination.getPageNumber());
				tQuery.setMaxResults(pagination.getPageSize());
			}
		}

		return tQuery.getResultList();
	}
	
	public List<Predicate> generateCriteriaPagination(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserGroupRole dto = JsonUtil.transferToObject(criteria, UserGroupRole.class);
			
			if (!BaseUtil.isObjNull(dto.getId())) {
				predicates.add(cb.equal(from.get("id"), dto.getId()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroup()) && !BaseUtil.isObjNull(dto.getUserGroup().getUserGroupCode())) {
				predicates.add(cb.equal(from.get("idmRole.roleCode"), dto.getUserGroup().getUserGroupCode()));
			}

			if (!BaseUtil.isObjNull(dto.getRole()) && !BaseUtil.isObjNull(dto.getRole().getRoleCode())) {
				predicates.add(cb.like(from.get("idmMenu.menuCode"), dto.getRole().getRoleCode()));
			}

		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
