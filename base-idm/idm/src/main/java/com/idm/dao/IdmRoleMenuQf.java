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
import com.idm.model.IdmMenu;
import com.idm.model.IdmPortalType;
import com.idm.model.IdmRole;
import com.idm.model.IdmRoleMenu;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.RoleMenu;
import com.idm.service.IdmMenuService;
import com.idm.service.IdmPortalTypeService;
import com.idm.service.IdmRoleService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;

@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLE_MENU_QF)
public class IdmRoleMenuQf extends QueryFactory<IdmRoleMenu> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;
	
	@Autowired
	IdmRoleService idmRoleSvc;

	@Autowired
	IdmMenuService idmMenuSvc;
	
	@Autowired
	IdmPortalTypeService idmPortalTypeSvc;

	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}
	
	@Override
	public Specification<IdmRoleMenu> searchByProperty(IdmRoleMenu t) {
		return (Root<IdmRoleMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmRoleMenu> searchAllByProperty(IdmRoleMenu t) {
		CriteriaQuery<IdmRoleMenu> cq = cb.createQuery(IdmRoleMenu.class);
		Root<IdmRoleMenu> from = cq.from(IdmRoleMenu.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			RoleMenu dto = JsonUtil.transferToObject(criteria, RoleMenu.class);

			if (!BaseUtil.isObjNull(dto.getRole()) && !BaseUtil.isObjNull(dto.getRole().getRoleCode())) {
				Join<IdmRoleMenu, IdmRole> ugJoin = (Join) from.fetch("idmRole", JoinType.LEFT);
				predicates.addAll(idmRoleSvc.generateCriteria(cb, ugJoin, criteria));
			}

			if (!BaseUtil.isObjNull(dto.getMenu()) && !BaseUtil.isObjNull(dto.getMenu().getMenuCode())) {
				Join<IdmRoleMenu, IdmMenu> ugJoin = (Join) from.fetch("idmMenu", JoinType.LEFT);
				predicates.addAll(idmMenuSvc.generateCriteria(cb, ugJoin, criteria));
			}

			if (!BaseUtil.isObjNull(dto.getPortalType()) && !BaseUtil.isObjNull(dto.getPortalType().getPortalTypeCode())) {
				Join<IdmRoleMenu, IdmPortalType> ugJoin = (Join) from.fetch("idmPortalType", JoinType.LEFT);
				predicates.addAll(idmPortalTypeSvc.generateCriteria(cb, ugJoin, criteria));
			}
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}
	
	public List<IdmRoleMenu> searchByPagination(RoleMenu t, DataTableRequest<?> dataTableInRQ) {
		CriteriaQuery<IdmRoleMenu> cq = cb.createQuery(IdmRoleMenu.class);
		Root<IdmRoleMenu> from = cq.from(IdmRoleMenu.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);

		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		// Generate order by clause
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				cq.orderBy(getOrderByClause(cb, from, pagination));
			}
		}

		TypedQuery<IdmRoleMenu> tQuery = em.createQuery(cq);

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
}
