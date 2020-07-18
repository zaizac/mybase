/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.model.IdmRole;
import com.baseboot.idm.model.IdmUserGroup;
import com.baseboot.idm.model.IdmUserGroupRole;
import com.baseboot.idm.model.IdmUserGroupRoleGroup;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.pagination.DataTableRequest;
import com.baseboot.idm.sdk.pagination.DataTableResults;
import com.baseboot.idm.sdk.pagination.PaginationCriteria;
import com.baseboot.idm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMPROFILE_CUSTOM_REPOSITORY)
public class UserProfileCustomDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileCustomDao.class);

	@Lazy
	@Autowired
	private IdmProfileRepository idmProfileDao;

	@PersistenceContext
	private EntityManager entityManager;


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@SuppressWarnings("unchecked")
	public List<IdmProfile> searchUserProfile(UserProfile userProfile) {
		StringBuilder sb = new StringBuilder("select r ");
		sb.append(" from IdmProfile r ");
		sb.append(" where 1=1 ");

		if (StringUtils.hasText(userProfile.getStatus())) {
			sb.append(" and r.status =:status ");
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			sb.append(" and r.fullName like :fullName ");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			sb.append(" and r.userType = :userType");
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(" and r.userRoleGroupCode = :userRoleGroupCode");
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			sb.append(" and r.profId = :profId ");
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			sb.append(" and r.branchId = :branchId ");
		}

		if (!BaseUtil.isObjNull(userProfile.getStatus())) {
			sb.append(" and r.status = :status ");
		}

		LOGGER.debug("Query: {}", sb.toString());

		Query query = entityManager.createQuery(sb.toString());

		if (StringUtils.hasText(userProfile.getStatus())) {
			query.setParameter("status", userProfile.getStatus());
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			query.setParameter("fullName", "%" + userProfile.getFullName() + "%");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			query.setParameter("userType", userProfile.getUserType());
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter("userRoleGroupCode", userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			query.setParameter("profId", userProfile.getProfId());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter("branchId", userProfile.getBranchId());
		}

		if (!BaseUtil.isObjNull(userProfile.getStatus())) {
			query.setParameter("status", userProfile.getStatus());
		}

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<IdmProfile> search(UserProfile userProfile) {

		List<IdmProfile> userList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select r, ugrp  ");
		sb.append(" from IdmProfile r, " + IdmUserGroupRoleGroup.class.getSimpleName() + " ugrp ");
		sb.append(" where r.userRoleGroupCode=ugrp.userRoleGroupCode ");
		if (StringUtils.hasText(userProfile.getStatus())) {
			if (StringUtils.pathEquals(userProfile.getStatus(), "I")) {
				sb.append(" and r.status !='A' ");
			} else {
				sb.append(" and r.status ='A' ");
			}
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			sb.append(" and r.userId = :userId ");
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			sb.append(" and r.fullName like :fullName ");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			sb.append(" and r.userType = :userType");
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			sb.append(" and ugrp.userGroupCode = :userGroupCode");
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			sb.append(" and ugrp.parentRoleGroup = :parentRoleGroup");
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(" and r.userRoleGroupCode = :userRoleGroupCode");
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			sb.append(" and r.userRoleGroupCode in ( :userRoleGroupCodes )");
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			sb.append(" and r.syncFlag in ( :syncFlag )");
		}

		LOGGER.debug("Query: {}", sb.toString());

		Query query = entityManager.createQuery(sb.toString());

		if (StringUtils.hasText(userProfile.getUserId())) {
			query.setParameter("userId", userProfile.getUserId());
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			query.setParameter("fullName", "%" + userProfile.getFullName() + "%");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			query.setParameter("userType", userProfile.getUserType());
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			query.setParameter("userGroupCode", userProfile.getUserGroupCode());
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			query.setParameter("parentRoleGroup", userProfile.getParentRoleGroup());
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter("userRoleGroupCode", userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			query.setParameter("userRoleGroupCodes", userProfile.getUserRoleGroupCodeList());
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			query.setParameter("syncFlag", userProfile.getSyncFlag());
		}

		List<Object[]> result = query.getResultList();
		if (!BaseUtil.isListNull(result)) {
			for (Object curObj[] : result) {
				if (curObj.length > 1) {
					IdmProfile prof = (IdmProfile) curObj[0];
					IdmUserGroupRoleGroup group = (IdmUserGroupRoleGroup) curObj[1];
					if (!BaseUtil.isObjNull(prof)) {
						if (!BaseUtil.isObjNull(group)) {
							prof.setUserGroupCode(group.getUserGroupCode());
							if (!BaseUtil.isObjNull(group) && !BaseUtil.isObjNull(group.getUserRoleGroup())) {
								prof.setUserRoleGroupDesc(group.getUserRoleGroup().getUserGroupDesc());
							}
						}
						userList.add(prof);
					}
				}
			}
		}
		return userList;
	}


	@SuppressWarnings("unchecked")
	public List<IdmProfile> getUserProfileListByUserNameList(String usernames) {
		List<String> usernameList = new ArrayList<>();

		if (StringUtils.hasText(usernames)) {
			usernameList = Arrays.asList(usernames.split("\\s*,\\s*"));
			if (!BaseUtil.isListNull(usernameList)) {
				usernameList.removeAll(Collections.singleton(null));
				usernameList.removeAll(Collections.singleton(""));
				LOGGER.debug("usernameList: {}", usernameList);
			}
		}

		StringBuilder sb = new StringBuilder("select r from IdmProfile r where r.userId in :usernames");
		LOGGER.debug("Query: {}", sb.toString());
		Query query = entityManager.createQuery(sb.toString());

		if (!BaseUtil.isListNull(usernameList)) {
			query.setParameter("usernames", usernameList);
		}

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<IdmProfile> findAllUsersByRoleCodes(String userRoles) {

		List<String> roleList = new ArrayList<>();

		if (!BaseUtil.isObjNull(roleList)) {
			roleList = Arrays.asList(userRoles.split("\\s*,\\s*"));
			if (!BaseUtil.isListNull(roleList)) {
				roleList.removeAll(Collections.singleton(null));
				roleList.removeAll(Collections.singleton(""));
				LOGGER.debug("roleList: {}", roleList);
			}
		}

		List<IdmProfile> userList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select r, ugrpr  ");
		sb.append(" from IdmProfile r, " + IdmUserGroupRole.class.getName() + " ugrpr ");
		sb.append(" where r.userRoleGroupCode=ugrpr.userRoleGroupCode ");

		if (!BaseUtil.isListNull(roleList)) {
			sb.append("and ugrpr.roleCode in :roleList ");
		}

		LOGGER.debug("Query: {}", sb.toString());

		Query query = entityManager.createQuery(sb.toString());

		if (!BaseUtil.isListNull(roleList)) {
			query.setParameter("roleList", roleList);
		}

		List<Object[]> result = query.getResultList();
		if (!BaseUtil.isListNull(result)) {
			for (Object curObj[] : result) {
				if (curObj.length > 1) {
					IdmProfile prof = (IdmProfile) curObj[0];
					if (!BaseUtil.isObjNull(prof)) {
						userList.add(prof);
					}
				}
			}
		}
		return userList;
	}


	public List<IdmProfile> findRoleMemberByIdmRoleroleCode(String roleCode, String userId) {
		return findRoleMemberByIdmRoleroleCode(roleCode, userId, new String[] {});
	}


	public List<IdmProfile> findRoleMemberByIdmRoleroleCode(String roleCode, String userId,
			String... userRoleGroupCodes) {
		List<IdmProfile> profilesList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select ip.userId, ip.branchId, ug.roleCode, ir.roleDesc,ip.status ");
		sb.append(" from IdmUserGroupRole ug, IdmProfile ip, IdmRole ir ");
		sb.append(" where ip.status in ('A','I') and ug.userRoleGroupCode = ip.userRoleGroupCode and ir.roleCode = ug.roleCode and ip.branchCode != '' and ip.branchCode IS NOT NULL ");
		sb.append(" and ug.roleCode=:roleCode and ip.createUserFlag = 0");

		if (!BaseUtil.isObjNull(userRoleGroupCodes) && userRoleGroupCodes.length > 0) {
			sb.append(" and ip.userRoleGroupCode in (:userRoleGroupCodes)");
		}

		if (userId != null) {
			sb.append(" and ip.userId = :userId");
		}

		LOGGER.debug("Query: {}", sb.toString());

		Query query = entityManager.createQuery(sb.toString()).setParameter("roleCode", roleCode);

		if (userId != null) {
			query.setParameter("userId", userId);
		}

		if (!BaseUtil.isObjNull(userRoleGroupCodes) && userRoleGroupCodes.length > 0) {
			query.setParameter("userRoleGroupCodes", Arrays.asList(userRoleGroupCodes));
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		for (Object[] queryResp : list) {
			IdmProfile profile = new IdmProfile();
			List<IdmRole> roleList = new ArrayList<>();

			IdmRole role = new IdmRole();
			role.setRoleDesc(!StringUtils.isEmpty(queryResp[3]) ? (String) queryResp[3] : null);
			role.setRoleCode(!StringUtils.isEmpty(queryResp[2]) ? (String) queryResp[2] : null);
			roleList.add(role);

			profile.setUserId(!StringUtils.isEmpty(queryResp[0]) ? (String) queryResp[0] : null);
			profile.setBranchId(Integer.valueOf((String) queryResp[1]));
			profile.setStatus(!StringUtils.isEmpty(queryResp[4]) ? (String) queryResp[4] : null);
			profile.setRolesList(roleList);
			profilesList.add(profile);
		}

		return profilesList;

	}


	@SuppressWarnings("unchecked")
	public DataTableResults<IdmProfile> searchByPagination(UserProfile userProfile,
			DataTableRequest<IdmProfile> dataTableInRQ) {
		StringBuilder sb = new StringBuilder("select r, iug  from ");
		sb.append(IdmProfile.class.getSimpleName() + " r, ");
		sb.append(IdmUserGroupRoleGroup.class.getSimpleName() + " ugrp, ");
		sb.append(IdmUserGroup.class.getSimpleName() + " iug ");
		sb.append(" where r.userRoleGroupCode = ugrp.userRoleGroupCode ");
		sb.append(" and ugrp.userRoleGroupCode = iug.userGroupCode ");

		if (StringUtils.hasText(userProfile.getStatus())) {
			sb.append(" and r.status = :status ");
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			sb.append(" and r.userId = :userId ");
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			sb.append(" and r.fullName like :fullName ");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			sb.append(" and r.userType = :userType");
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			sb.append(" and ugrp.userGroupCode = :userGroupCode");
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			sb.append(" and ugrp.parentRoleGroup = :parentRoleGroup");
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(" and r.userRoleGroupCode = :userRoleGroupCode");
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			sb.append(" and r.userRoleGroupCode in ( :userRoleGroupCodes )");
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			sb.append(" and r.syncFlag in ( :syncFlag )");
		}

		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("r"));
			}
		}

		LOGGER.debug("Query: {}", sb.toString());

		// Filtered Query by pagination
		Query query = entityManager.createQuery(sb.toString());
		// Original Query
		Query query2 = entityManager.createQuery(sb.toString());

		if (StringUtils.hasText(userProfile.getStatus())) {
			query.setParameter("status", userProfile.getStatus());
			query2.setParameter("status", userProfile.getStatus());
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			query.setParameter("userId", userProfile.getUserId());
			query2.setParameter("userId", userProfile.getUserId());
		}

		if (StringUtils.hasText(userProfile.getFullName())) {
			query.setParameter("fullName", "%" + userProfile.getFullName() + "%");
			query2.setParameter("fullName", "%" + userProfile.getFullName() + "%");
		}

		if (StringUtils.hasText(userProfile.getUserType())) {
			query.setParameter("userType", userProfile.getUserType());
			query2.setParameter("userType", userProfile.getUserType());
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			query.setParameter("userGroupCode", userProfile.getUserGroupCode());
			query2.setParameter("userGroupCode", userProfile.getUserGroupCode());
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			query.setParameter("parentRoleGroup", userProfile.getParentRoleGroup());
			query2.setParameter("parentRoleGroup", userProfile.getParentRoleGroup());
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter("userRoleGroupCode", userProfile.getUserRoleGroupCode());
			query2.setParameter("userRoleGroupCode", userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			query.setParameter("userRoleGroupCodes", userProfile.getUserRoleGroupCodeList());
			query2.setParameter("userRoleGroupCodes", userProfile.getUserRoleGroupCodeList());
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			query.setParameter("syncFlag", userProfile.getSyncFlag());
			query.setParameter("syncFlag", userProfile.getSyncFlag());
		}

		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				query.setFirstResult(pagination.getPageNumber());
				query.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<IdmProfile> dataTableResult = new DataTableResults<>();
		List<Object[]> svcResp = query.getResultList();
		List<Object[]> svcResp2 = query2.getResultList();
		LOGGER.debug("Filtered Size: {}", svcResp.size());
		LOGGER.debug("Original Size: {}", svcResp2.size());
		List<IdmProfile> userList = new ArrayList<>();
		if (!BaseUtil.isListNull(svcResp)) {
			for (Object curObj[] : svcResp) {
				if (curObj.length > 1) {
					IdmProfile prof = (IdmProfile) curObj[0];
					IdmUserGroup group = (IdmUserGroup) curObj[1];
					if (!BaseUtil.isObjNull(prof)) {
						if (!BaseUtil.isObjNull(group)) {
							prof.setUserGroupCode(group.getUserGroupCode());
							prof.setUserRoleGroupDesc(group.getUserGroupDesc());
						}
						userList.add(prof);
					}
				}
			}
		}

		// Get max total records in table
		int totalRecords = dataTableInRQ.isInitSearch() ? svcResp2.size()
				: idmProfileDao.totalRecords(userProfile.getUserType());
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(userList);
		LOGGER.debug("isFilterByEmpty: {}", dataTableInRQ.getPaginationRequest().isFilterByEmpty());
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(svcResp2.size()));
		}
		return dataTableResult;
	}

}