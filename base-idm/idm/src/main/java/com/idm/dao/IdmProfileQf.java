package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmProfile;
import com.idm.model.IdmRole;
import com.idm.model.IdmRoleMenu;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserProfile;
import com.idm.service.IdmUserGroupService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;


/**
 * @author mary.jane
 * @since 18 Nov 2019
 */
@Repository
@Transactional(QualifierConstants.TRANS_MANAGER)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_QF)
public class IdmProfileQf extends QueryFactory<IdmProfile> {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IdmUserGroupService idmUserGroupSvc;


	@Override
	public Specification<IdmProfile> searchByProperty(IdmProfile t) {
		return (Root<IdmProfile> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmProfile> searchAllByProperty(IdmProfile t) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IdmProfile> cq = cb.createQuery(IdmProfile.class);
		Root<IdmProfile> from = cq.from(IdmProfile.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	public List<IdmProfile> searchByPagination(UserProfile t, DataTableRequest<?> dataTableInRQ) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IdmProfile> cq = cb.createQuery(IdmProfile.class);
		Root<IdmProfile> from = cq.from(IdmProfile.class);
		from.fetch("userRoleGroup", JoinType.LEFT);
		List<Predicate> predicates = generateCriteria(cb, from, t);

		if (!BaseUtil.isObjNull(t.getUserRoleGroupCode())) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Join<IdmProfile, IdmUserGroup> ugJoin = (Join) from.fetch("userRoleGroup", JoinType.LEFT);
			UserGroup ug = new UserGroup();
			ug.setUserGroupCode(t.getUserRoleGroupCode());
			predicates.addAll(idmUserGroupSvc.generateCriteria(cb, ugJoin, ug));
		}
		
		// Filter with User Group Role Group
		if(!BaseUtil.isObjNull(t.getUserRoleGroupCodeList())) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Join<IdmProfile, IdmUserGroup> ugJoin = (Join) from.fetch("userRoleGroup", JoinType.LEFT);
			In<String> in = cb.in(ugJoin.get("userGroupCode"));
			t.getUserRoleGroupCodeList().forEach(p -> in.value(p));
			predicates.add(in);
		}
		

		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		// Generate order by clause
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				cq.orderBy(getOrderByClause(cb, from, pagination));
			}
		}

		TypedQuery<IdmProfile> tQuery = em.createQuery(cq);

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


	public List<IdmProfile> searchUserProfile(UserProfile userProfile, boolean embededRole, boolean embededMenu) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IdmProfile> cq = cb.createQuery(IdmProfile.class);
		Root<IdmProfile> from = cq.from(IdmProfile.class);
		from.fetch("userType", JoinType.LEFT);
		from.fetch("userRoleGroup", JoinType.LEFT);
		if (embededRole) {
			Join<IdmProfile, IdmUserGroup> ug = from.join("userRoleGroup", JoinType.LEFT);
			Join<IdmUserGroup, IdmUserGroupRole> ugJoin = ug.join("idmUserGroupRoles", JoinType.LEFT);
			Join<IdmUserGroupRole, IdmRole> roleJoin = ug.join("role", JoinType.LEFT);
			if (embededMenu) {
				Join<IdmRole, IdmRoleMenu> roleMenJoin = ug.join("idmRoleMenus", JoinType.LEFT);
			}
		}
		List<Predicate> predicates = generateCriteria(cb, from, userProfile);
		cq.distinct(true).select(from);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserProfile dto = JsonUtil.transferToObject(criteria, UserProfile.class);

			if (!BaseUtil.isObjNull(dto.getUserId())) {
				predicates.add(cb.equal(from.get("userId"), dto.getUserId()));
			}

			if (!BaseUtil.isObjNull(dto.getFirstName())) {
				predicates.add(cb.like(from.get("firstName"), '%' + dto.getFirstName() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getLastName())) {
				predicates.add(cb.like(from.get("lastName"), '%' + dto.getLastName() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getNationalId())) {
				predicates.add(cb.equal(from.get("nationalId"), dto.getNationalId()));
			}

			if (!BaseUtil.isObjNull(dto.getGender())) {
				predicates.add(cb.equal(from.get("gender"), dto.getGender()));
			}

			if (!BaseUtil.isObjNull(dto.getEmail())) {
				predicates.add(cb.equal(from.get("email"), dto.getEmail()));
			}

			if (!BaseUtil.isObjNull(dto.getContactNo())) {
				predicates.add(cb.equal(from.get("contactNo"), dto.getContactNo()));
			}

			if (!BaseUtil.isObjNull(dto.getCntryCd())) {
				predicates.add(cb.equal(from.get("cntryCd"), dto.getCntryCd()));
			}

			if (!BaseUtil.isObjNull(dto.getPosition())) {
				predicates.add(cb.like(from.get("position"), '%' + dto.getPosition() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getUserRoleGroup())) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Join<IdmProfile, IdmUserGroup> ugJoin = (Join) from.fetch("userRoleGroup", JoinType.LEFT);
				predicates.addAll(idmUserGroupSvc.generateCriteria(cb, ugJoin, criteria));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroupRoleBranchCode())) {
				predicates.add(cb.equal(from.get("userGroupRoleBranchCode"), dto.getUserGroupRoleBranchCode()));
			}

			if (!BaseUtil.isObjNull(dto.getRoleBranch())) {
				predicates.add(cb.equal(from.get("roleBranch"), dto.getRoleBranch()));
			}

			if (!BaseUtil.isObjNull(dto.getProfRegNo())) {
				predicates.add(cb.equal(from.get("profRegNo"), dto.getProfRegNo()));
			}

			if (!BaseUtil.isObjNull(dto.getProfId())) {
				predicates.add(cb.equal(from.get("profId"), dto.getProfId()));
			}

			if (!BaseUtil.isObjNull(dto.getBranchId())) {
				predicates.add(cb.equal(from.get("branchId"), dto.getBranchId()));
			}

			if (!BaseUtil.isObjNull(dto.getDocMgtId())) {
				predicates.add(cb.equal(from.get("docMgtId"), dto.getDocMgtId()));
			}

			if (!BaseUtil.isObjNull(dto.getLastLogin())) {
				predicates.add(cb.equal(from.get("lastLogin"), dto.getLastLogin()));
			}

			if (!BaseUtil.isObjNull(dto.getFcmAccessToken())) {
				predicates.add(cb.equal(from.get("fcmAccessToken"), dto.getFcmAccessToken()));
			}
			
			if (!BaseUtil.isObjNull(dto.getActivationCode())) {
				predicates.add(cb.equal(from.get("activationCode"), dto.getActivationCode()));
			}

			if (!BaseUtil.isObjNull(dto.getStatus())) {
				predicates.add(cb.equal(from.get("status"), dto.getStatus()));
			}

			if (!BaseUtil.isObjNull(dto.getSyncFlag())) {
				predicates.add(cb.equal(from.get("syncFlag"), dto.getSyncFlag()));
			}
			
			if (!BaseUtil.isObjNull(dto.getSystemType())) {
				predicates.add(cb.equal(from.get("systemType"), dto.getSystemType()));
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
	
	public List<Object[]> getCountRegisteredUserByDay(String date) {
		List<Object[]> result = null;
		StringBuilder sb = new StringBuilder("SELECT  ");
		sb.append("COUNT(*) , ");
		sb.append("Date(createDt)  ");
		sb.append("FROM IdmProfile where Date(createDt) >  ' ") ;
		sb.append(date + "'");
		sb.append(" group by date(createDt) order by Date(createDt) desc ");
		TypedQuery<Object[]> typedQuery = em.createQuery(sb.toString(), Object[].class);
		try {
			result = typedQuery.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return result;
	}
	
	

}
