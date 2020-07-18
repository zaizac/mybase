package com.idm.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.util.StringUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmUserGroupBranch;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroupBranch;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Feb 8, 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_BRANCH_QF)
public class IdmUserGroupBranchQf extends QueryFactory<IdmUserGroupBranch> {

	@PersistenceContext
	private EntityManager entityManager;


	public List<IdmUserGroupBranch> search(UserGroupBranch userProfile) {
		StringBuilder sb = new StringBuilder("select r ");
		sb.append(" from " + IdmUserGroupBranch.class.getSimpleName() + " r ");
		sb.append(" where 1 = 1 ");

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			sb.append(" and r.branchId = :branchId ");
		}
		
		if (!BaseUtil.isObjNull(userProfile.getUserType())) {
			sb.append(" and r.userType = :userType ");
		}

		if (!BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
			sb.append(" and r.userGroupCode = :userGroupCode ");
		}

		if (StringUtils.hasText(userProfile.getBranchCode())) {
			sb.append(" and r.branchCode = :branchCode");
		}

		if (StringUtils.hasText(userProfile.getBranchName())) {
			sb.append(" and r.branchName = :branchName");
		}

		if (StringUtils.hasText(userProfile.getBranchDept())) {
			sb.append(" and r.branchDept = :branchDept");
		}

		if (!BaseUtil.isObjNull(userProfile.getStateCd())) {
			sb.append(" and r.stateCd = :stateCd");
		}

		if (!BaseUtil.isObjNull(userProfile.getCntryCd())) {
			sb.append(" and r.cntryCd = :cntryCd ");
		}

		if (Boolean.TRUE.equals(userProfile.getStatus())) {
			sb.append(" and r.status = '1' ");
		} else {
			sb.append(" and r.status = '0' ");

		}

		TypedQuery<IdmUserGroupBranch> query = entityManager.createQuery(sb.toString(), IdmUserGroupBranch.class);

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter("branchId", userProfile.getBranchId());
		}

		if (!BaseUtil.isObjNull(userProfile.getUserType())) {
			query.setParameter("userType", userProfile.getUserType());
		}

		if (!BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
			query.setParameter("userGroupCode", userProfile.getUserGroupCode());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchCode())) {
			query.setParameter("branchCode", userProfile.getBranchCode());
		}

		if (StringUtils.hasText(userProfile.getBranchName())) {
			query.setParameter("branchName", "%" + userProfile.getBranchName() + "%");
		}

		if (!BaseUtil.isObjNull(userProfile.getStateCd())) {
			query.setParameter("stateCd", userProfile.getStateCd());
		}

		if (!BaseUtil.isObjNull(userProfile.getCntryCd())) {
			query.setParameter("cntryCd", userProfile.getCntryCd());
		}

		if (userProfile.getStatus() != null) {
			query.setParameter("status", userProfile.getStatus());
		}

		return query.getResultList();
	}


	@Override
	public Specification<IdmUserGroupBranch> searchByProperty(final IdmUserGroupBranch t) {
		return (Root<IdmUserGroupBranch> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmUserGroupBranch> searchAllByProperty(IdmUserGroupBranch t) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<IdmUserGroupBranch> cq = cb.createQuery(IdmUserGroupBranch.class);
		Root<IdmUserGroupBranch> from = cq.from(IdmUserGroupBranch.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return entityManager.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmUserGroupBranch dto = JsonUtil.transferToObject(criteria, IdmUserGroupBranch.class);
			if (!BaseUtil.isObjNull(dto.getBranchId())) {
				predicates.add(cb.equal(from.get("branchId"), dto.getBranchId()));
			}
			
			if (!BaseUtil.isObjNull(dto.getUserType()) && !BaseUtil.isObjNull(dto.getUserType().getUserTypeCode())) {
				predicates.add(cb.equal(from.get("userType"), dto.getUserType()));
			}

			if (!BaseUtil.isObjNull(dto.getUserGroupCode())) {
				predicates.add(cb.equal(from.get("userGroupCode"), dto.getUserGroupCode()));
			}

			if (!BaseUtil.isObjNull(dto.getBranchCode())) {
				predicates.add(cb.equal(from.get("branchCode"), dto.getBranchCode()));
			}

			if (!BaseUtil.isObjNull(dto.getBranchName())) {
				predicates.add(cb.like(from.get("branchName"), '%' + dto.getBranchName() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getBranchDept())) {
				predicates.add(cb.like(from.get("branchDept"), '%' + dto.getBranchDept() + '%'));
			}

			if (!BaseUtil.isObjNull(dto.getStateCd())) {
				predicates.add(cb.equal(from.get("stateCd"), dto.getStateCd()));
			}

			if (!BaseUtil.isObjNull(dto.getCntryCd())) {
				predicates.add(cb.equal(from.get("cntryCd"), dto.getCntryCd()));
			}
			
			if (dto.getStatus() != null) {
				predicates.add(cb.equal(from.get("status"), dto.getStatus()));
			}
			
			if (!BaseUtil.isObjNull(dto.getSystemType())) {
				predicates.add(cb.like(from.get("systemType"), dto.getSystemType()));
			}
			
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
