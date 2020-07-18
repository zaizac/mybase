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
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRoleGroup;
import com.idm.model.IdmUserGroupRoleGroup_;
import com.idm.model.IdmUserGroup_;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroupRoleGroup;
import com.idm.service.IdmProfileService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since Apr 15, 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_QF)
public class IdmUserGroupRoleGroupQf extends QueryFactory<IdmUserGroupRoleGroup> {

	@Autowired
	IdmProfileService idmProfileSvc;

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmUserGroupRoleGroup> searchByProperty(IdmUserGroupRoleGroup t) {
		return (Root<IdmUserGroupRoleGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserGroupRoleGroup> searchAllByProperty(IdmUserGroupRoleGroup t) {
		CriteriaQuery<IdmUserGroupRoleGroup> cq = cb.createQuery(IdmUserGroupRoleGroup.class);
		Root<IdmUserGroupRoleGroup> from = cq.from(IdmUserGroupRoleGroup.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			UserGroupRoleGroup dto = JsonUtil.transferToObject(criteria, UserGroupRoleGroup.class);
			if (!BaseUtil.isObjNull(dto.getGroupId())) {
				predicates.add(cb.equal(from.get("groupId"), dto.getGroupId()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroupCode())) {
				predicates.add(cb.equal(from.get("userGroupCode"), dto.getUserGroupCode()));
			}

			if (!BaseUtil.isObjNull(dto.getParentRoleGroup())) {
				predicates.add(cb.equal(from.get("parentRoleGroup"), dto.getParentRoleGroup()));
			}

			if (!BaseUtil.isObjNull(dto.getUserTypeCode())) {
				predicates.add(cb.equal(from.get("userTypeCode"), dto.getUserTypeCode()));
			}

			if (!BaseUtil.isObjNull(dto.getMaxNoOfUser())) {
				predicates.add(cb.equal(from.get("maxNoOfUser"), dto.getMaxNoOfUser()));
			}

			if (!BaseUtil.isObjNull(dto.isCreateByProfId())) {
				predicates.add(cb.equal(from.get("createByProfId"), dto.isCreateByProfId()));
			}

			if (!BaseUtil.isObjNull(dto.isCreateByBranchId())) {
				predicates.add(cb.equal(from.get("createByBranchId"), dto.isCreateByBranchId()));
			}

			if (!BaseUtil.isObjNull(dto.isSystemCreate())) {
				predicates.add(cb.equal(from.get("systemCreate"), dto.isSystemCreate()));
			}

			if (!BaseUtil.isObjNull(dto.isSelCountry())) {
				predicates.add(cb.equal(from.get("selCountry"), dto.isSelCountry()));
			}

			if (!BaseUtil.isObjNull(dto.isSelBranch())) {
				predicates.add(cb.equal(from.get("selBranch"), dto.isSelBranch()));
			}

			if (!BaseUtil.isObjNull(dto.getCntryCd())) {
				predicates.add(cb.equal(from.get("cntryCd"), dto.getCntryCd()));
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


	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroupCriteria(String userRoleGroupCode, String systemType,
			boolean noSystemCreate, String currUserId) {
		List<IdmUserGroupRoleGroup> result = new ArrayList<>();
		CriteriaQuery<IdmUserGroupRoleGroup> cq = cb.createQuery(IdmUserGroupRoleGroup.class);
		if (cq != null) {
			Root<IdmUserGroupRoleGroup> userGroupRoleGroup = cq.from(IdmUserGroupRoleGroup.class);
			Join<IdmUserGroupRoleGroup, IdmUserGroup> userGroup = userGroupRoleGroup
					.join(IdmUserGroupRoleGroup_.userRoleGroup);
			cq.multiselect(userGroupRoleGroup, userGroup);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(userGroupRoleGroup.get(IdmUserGroupRoleGroup_.userGroupCode),
					userGroup.get(IdmUserGroup_.userGroupCode)));

			if(!BaseUtil.isObjNull(systemType)) {
				predicates.add(cb.equal(userGroupRoleGroup.get(IdmUserGroupRoleGroup_.systemType), systemType));
			}
			
			if (noSystemCreate) {
				predicates.add(cb.equal(userGroupRoleGroup.get(IdmUserGroupRoleGroup_.systemCreate), false));
			}

			if (!BaseUtil.isObjNull(userRoleGroupCode)) {
				predicates.add(cb.equal(userGroupRoleGroup.get(IdmUserGroupRoleGroup_.parentRoleGroup),
						userRoleGroupCode));
			}

			cq.where(predicates.toArray(new Predicate[predicates.size()]));
			cq.groupBy(userGroupRoleGroup.get(IdmUserGroupRoleGroup_.userRoleGroup));
			TypedQuery<IdmUserGroupRoleGroup> q = em.createQuery(cq);
			result = q.getResultList();
		}
		return result;
	}

}