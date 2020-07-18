/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.model.IdmUserGroupRoleBranch;
import com.bstsb.idm.sdk.model.UserGroupRoleBranch;
import com.bstsb.util.BaseUtil;


/**
 * @author mary.jane
 * @since Feb 8, 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_BRANCH_QF)
public class IdmUserGroupRoleBranchQf {

	@PersistenceContext
	private EntityManager entityManager;


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<IdmUserGroupRoleBranch> search(UserGroupRoleBranch userProfile) {
		StringBuilder sb = new StringBuilder("select r ");
		sb.append(" from " + IdmUserGroupRoleBranch.class.getSimpleName() + " r ");
		sb.append(" where 1 = 1 ");

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			sb.append(" and r.branchId = :branchId ");
		}

		if (StringUtils.hasText(userProfile.getUserGroupRoleCode())) {
			sb.append(" and r.userGroupRoleCode = :userGroupRoleCode");
		}

		if (StringUtils.hasText(userProfile.getBranchCode())) {
			sb.append(" and r.branchCode = :branchCode");
		}

		if (StringUtils.hasText(userProfile.getBranchName())) {
			sb.append(" and r.branchName = :branchName");
		}

		if (!BaseUtil.isObjNull(userProfile.getStateCode())) {
			sb.append(" and r.stateCode = :stateCode");
		}

		if (!BaseUtil.isObjNull(userProfile.getCntryCode())) {
			sb.append(" and r.cntryCode = :cntryCode ");
		}

		if (userProfile.isStatus()) {
			sb.append(" and r.status = '1' ");
		} else {
			sb.append(" and r.status = '0' ");

		}

		TypedQuery<IdmUserGroupRoleBranch> query = entityManager.createQuery(sb.toString(),
				IdmUserGroupRoleBranch.class);

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter("branchId", userProfile.getBranchId());
		}

		if (!BaseUtil.isObjNull(userProfile.getUserGroupRoleCode())) {
			query.setParameter("userGroupRoleCode", userProfile.getUserGroupRoleCode());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchCode())) {
			query.setParameter("branchCode", userProfile.getBranchCode());
		}

		if (StringUtils.hasText(userProfile.getBranchName())) {
			query.setParameter("branchName", "%" + userProfile.getBranchName() + "%");
		}

		if (!BaseUtil.isObjNull(userProfile.getStateCode())) {
			query.setParameter("stateCode", userProfile.getStateCode());
		}

		if (!BaseUtil.isObjNull(userProfile.getCntryCode())) {
			query.setParameter("cntryCode", userProfile.getCntryCode());
		}

		return query.getResultList();
	}

}
