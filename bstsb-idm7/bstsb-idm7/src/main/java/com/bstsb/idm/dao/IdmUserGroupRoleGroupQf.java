/**
 * Copyright 2019. Universl Recruitment Platform
 */
package com.bstsb.idm.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.model.IdmUserGroupRoleGroup;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Apr 15, 2019
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_QF)
public class IdmUserGroupRoleGroupQf {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserGroupRoleGroupQf.class);

	@Autowired
	IdmProfileService idmProfileSvc;

	@PersistenceContext
	private EntityManager entityManager;


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<IdmUserGroupRoleGroup> findUserGroupByParentRoleGroup(String userRoleGroupCode, boolean noSystemCreate,
			String currUserId) {
		StringBuilder sb = new StringBuilder("select r");
		sb.append(" from " + IdmUserGroupRoleGroup.class.getSimpleName() + " as r ");
		sb.append(" where 1 = 1");

		if (noSystemCreate) {
			sb.append(" and r.systemCreate = false");
		}

		if (!BaseUtil.isObjNull(userRoleGroupCode)) {
			sb.append(" and r.parentRoleGroup = :parentRoleGroup");
		}

		TypedQuery<IdmUserGroupRoleGroup> query = entityManager.createQuery(sb.toString(),
				IdmUserGroupRoleGroup.class);

		if (!BaseUtil.isObjNull(userRoleGroupCode)) {
			query.setParameter("parentRoleGroup", userRoleGroupCode);
		}

		List<IdmUserGroupRoleGroup> queryResult = query.getResultList();

		if (!BaseUtil.isListNull(queryResult)) {
			IdmProfile prof = idmProfileSvc.findProfileByUserId(currUserId);

			for (IdmUserGroupRoleGroup group : queryResult) {
				int maxNoUserCreated = idmProfileSvc.findMaxCountByUserGroup(group.getUserRoleGroupCode());
				LOGGER.info("maxNoUserCreated: {}", maxNoUserCreated);

				if (!BaseUtil.isObjNull(prof)) {

					// Update max no of user created by filtering with
					// prof id
					if (group.isCreateByProfId()) {
						maxNoUserCreated = idmProfileSvc.findMaxCount(prof.getProfId());
					}

					LOGGER.info("PROFID - maxNoUserCreated: {}", maxNoUserCreated);

					// Update max no of user created by filtering with
					// prof id and branch id
					if (group.isCreateByBranchId()) {
						maxNoUserCreated = idmProfileSvc.findMaxCount(prof.getProfId(), prof.getBranchId());
					}

					LOGGER.info("BRANCHID - maxNoUserCreated: {}", maxNoUserCreated);
				}

				group.setMaxNoOfUserCreated(maxNoUserCreated);
			}
		}

		return queryResult;
	}

}
