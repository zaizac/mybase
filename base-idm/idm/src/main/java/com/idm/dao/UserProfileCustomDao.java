package com.idm.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idm.constants.QualifierConstants;
import com.idm.model.IdmProfile;
import com.idm.model.IdmRole;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;
import com.idm.model.IdmUserGroupRoleGroup;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.model.LangDesc;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.util.pagination.PaginationCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_CUSTOM_REPOSITORY)
public class UserProfileCustomDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileCustomDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUERY_FIRST_NAME = " and r.firstName like :firstName ";

	private static final String QUERY_USER_TYPE = " and r.userType.userTypeCode = :userTypeCode ";

	private static final String QUERY_USER_ROLE_GROUP_CODE = " and r.userRoleGroup.userGroupCode = :userGroupCode ";

	private static final String LOG_QUERY = "Query: {}";

	private static final String CONST_FULLNAME = "firstName";

	private static final String CONST_STATUS = "status";

	private static final String CONST_USER_TYPE = "userTypeCode";

	private static final String CONST_USER_ROLE_GROUP_CODE = "userGroupCode";

	private static final String CONST_USER_ID = "userId";

	private static final String CONST_USER_GROUP_CODE = "userGroupCode";

	private static final String CONST_USER_ROLE_GROUP_CODES = "userRoleGroupCodes";

	private static final String CONST_SYNC_FLAG = "syncFlag";

	private static final String CONST_PARENT_ROLE_GROUP = "parentRoleGroup";

	private static final String CONST_PROF_ID = "profId";

	private static final String CONST_BRANCH_ID = "branchId";


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

		if (StringUtils.hasText(userProfile.getFirstName())) {
			sb.append(QUERY_FIRST_NAME);
		}

		
		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			sb.append(QUERY_USER_TYPE); 
		}
		

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(QUERY_USER_ROLE_GROUP_CODE);
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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(LOG_QUERY, sb);
		}

		TypedQuery<IdmProfile> query = entityManager.createQuery(sb.toString(), IdmProfile.class);

		if (StringUtils.hasText(userProfile.getStatus())) {
			query.setParameter(CONST_STATUS, userProfile.getStatus());
		}

		if (StringUtils.hasText(userProfile.getFirstName())) {
			query.setParameter(CONST_FULLNAME, "%" + userProfile.getFirstName() + "%");
		}
		
		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			query.setParameter(CONST_USER_TYPE, userProfile.getUserType().getUserTypeCode()); 
		}		

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODE, userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			query.setParameter(CONST_PROF_ID, userProfile.getProfId());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter(CONST_BRANCH_ID, userProfile.getBranchId());
		}

		if (!BaseUtil.isObjNull(userProfile.getStatus())) {
			query.setParameter(CONST_STATUS, userProfile.getStatus());
		}

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<IdmProfile> search(UserProfile userProfile) {

		List<IdmProfile> userList = new ArrayList<>();

		StringBuilder sb = searchConfigureQueryString(userProfile);
		LOGGER.debug(LOG_QUERY, sb);

		Query query = searchAssignQueryParameter(userProfile, sb);

		List<Object[]> result = query.getResultList();
		if (!BaseUtil.isListNull(result)) {
			for (Object[] curObj : result) {
				if (curObj.length > 1) {
					IdmProfile prof = (IdmProfile) curObj[0];
					IdmUserGroupRoleGroup group = (IdmUserGroupRoleGroup) curObj[1];
					updateUserGroupAndUserRoleGroupToUserList(prof, group, userList);
				}
			}
		}
		return userList;
	}


	private StringBuilder searchConfigureQueryString(UserProfile userProfile) {
		StringBuilder sb = new StringBuilder("select r, ugrp  ");
		sb.append(" from IdmProfile r, " + IdmUserGroupRoleGroup.class.getSimpleName() + " ugrp ");
		sb.append(" where r.userRoleGroup.userGroupCode=ugrp.userGroupCode ");
		if (StringUtils.hasText(userProfile.getStatus())) {
			if (StringUtils.pathEquals(userProfile.getStatus(), "I")) {
				sb.append(" and r.status != 'A' ");
			} else {
				sb.append(" and r.status ='A' ");
			}
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			sb.append(" and r.userId = :userId ");
		}

		if (StringUtils.hasText(userProfile.getFirstName())) {
			sb.append(QUERY_FIRST_NAME);
		}

		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			sb.append(QUERY_USER_TYPE); 
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			sb.append(" and ugrp.userGroupCode = :userGroupCode");
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			sb.append(" and ugrp.parentRoleGroup = :parentRoleGroup");
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(QUERY_USER_ROLE_GROUP_CODE);
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			sb.append(" and r.userGroupCode in ( :userRoleGroupCodes )");
		}
		
		if (!BaseUtil.isObjNull(userProfile.getCntryCd())) {
			sb.append(" and r.cntryCd = :cntryCd");
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			sb.append(" and r.syncFlag in ( :syncFlag )");
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			sb.append(" and r.profId in ( :profId )");
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			sb.append(" and r.branchId in ( :branchId )");
		}

		return sb;
	}


	private Query searchAssignQueryParameter(UserProfile userProfile, StringBuilder sb) {
		Query query = entityManager.createQuery(sb.toString());

		if (StringUtils.hasText(userProfile.getUserId())) {
			query.setParameter(CONST_USER_ID, userProfile.getUserId());
		}

		if (StringUtils.hasText(userProfile.getFirstName())) {
			query.setParameter(CONST_FULLNAME, "%" + userProfile.getFirstName() + "%");
		}

		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			query.setParameter(CONST_USER_TYPE, userProfile.getUserType().getUserTypeCode()); 
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			query.setParameter(CONST_USER_GROUP_CODE, userProfile.getUserGroupCode());
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			query.setParameter(CONST_PARENT_ROLE_GROUP, userProfile.getParentRoleGroup());
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODE, userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODES, userProfile.getUserRoleGroupCodeList());
		}
		
		if (!BaseUtil.isObjNull(userProfile.getCntryCd())) {
			query.setParameter("cntryCd", userProfile.getCntryCd());
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			query.setParameter(CONST_SYNC_FLAG, userProfile.getSyncFlag());
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			query.setParameter(CONST_PROF_ID, userProfile.getProfId());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter(CONST_BRANCH_ID, userProfile.getBranchId());
		}

		return query;
	}


	private void updateUserGroupAndUserRoleGroupToUserList(IdmProfile prof, IdmUserGroupRoleGroup group,
			List<IdmProfile> userList) {
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

		LOGGER.debug(LOG_QUERY, sb);
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
		sb.append(" where r.userRoleGroup.userGroupCode = ugrpr.userGroup.userGroupCode ");

		if (!BaseUtil.isListNull(roleList)) {
			sb.append("and ugrpr.role.roleCode in :roleList ");
		}

		LOGGER.debug(LOG_QUERY, sb);

		Query query = entityManager.createQuery(sb.toString());

		if (!BaseUtil.isListNull(roleList)) {
			query.setParameter("roleList", roleList);
		}

		List<Object[]> result = query.getResultList();
		if (!BaseUtil.isListNull(result)) {
			for (Object[] curObj : result) {
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
		return findRoleMemberByIdmRoleroleCode(roleCode, userId, "");
	}


	public List<IdmProfile> findRoleMemberByIdmRoleroleCode(String roleCode, String userId,
			String... userRoleGroupCodes) {
		List<IdmProfile> profilesList = new ArrayList<>();

		StringBuilder sb = new StringBuilder("select ip.userId, ip.branchId, ug.roleCode, ir.roleDesc,ip.status ");
		sb.append(" from IdmUserGroupRole ug, IdmProfile ip, IdmRole ir ");
		sb.append(" where ip.status in ('A','I') and ug.userGroup.userGroupCode = ip.userGroupCode and ir.roleCode = ug.roleCode and ip.branchCode != '' and ip.branchCode IS NOT NULL ");
		sb.append(" and ug.roleCode=:roleCode and ip.createUserFlag = 0");

		if (!BaseUtil.isObjNull(userRoleGroupCodes) && userRoleGroupCodes.length > 0) {
			sb.append(" and ip.userGroupCode in (:userRoleGroupCodes)");
		}

		if (userId != null) {
			sb.append(" and ip.userId = :userId");
		}

		LOGGER.debug(LOG_QUERY, sb);

		Query query = entityManager.createQuery(sb.toString()).setParameter("roleCode", roleCode);

		if (userId != null) {
			query.setParameter(CONST_USER_ID, userId);
		}

		if (!BaseUtil.isObjNull(userRoleGroupCodes) && userRoleGroupCodes.length > 0) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODES, Arrays.asList(userRoleGroupCodes));
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		for (Object[] queryResp : list) {
			IdmProfile profile = new IdmProfile();
			List<IdmRole> roleList = new ArrayList<>();

			IdmRole role = new IdmRole();
			role.setRoleDesc(!StringUtils.isEmpty(queryResp[3]) ? (LangDesc) queryResp[3] : null);
			role.setRoleCode(!StringUtils.isEmpty(queryResp[2]) ? (String) queryResp[2] : null);
			roleList.add(role);

			profile.setUserId(!StringUtils.isEmpty(queryResp[0]) ? (String) queryResp[0] : null);
			profile.setBranchId(Integer.valueOf((String) queryResp[1]));
			profile.setStatus(!StringUtils.isEmpty(queryResp[4]) ? (String) queryResp[4] : null);
			profile.setRoles(roleList);
			profilesList.add(profile);
		}

		return profilesList;

	}


	@SuppressWarnings("unchecked")
	public DataTableResults<IdmProfile> searchByPagination(UserProfile userProfile,
			DataTableRequest<IdmProfile> dataTableInRQ) {
		StringBuilder sb = searchQueryString("select r, iug  from ", userProfile);

		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("r"));
			}
		}

		// Filtered Query by pagination
		Query query = entityManager.createQuery(sb.toString());
		// Original Query
		Query query2 = entityManager.createQuery(sb.toString());

		searchQueryParameter(query, userProfile);
		searchQueryParameter(query2, userProfile);

		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			LOGGER.info("getFilterByClause: {}", pagination.getFilterByClause());
			LOGGER.info("getOrderByClause: {}", pagination.getOrderByClause());
			if (!BaseUtil.isObjNull(pagination)) {
				query.setFirstResult(pagination.getPageNumber());
				query.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<IdmProfile> dataTableResult = new DataTableResults<>();
		List<Object[]> svcResp = query.getResultList();
		List<Object[]> svcResp2 = query2.getResultList();
		LOGGER.info("Filtered Size: {}", svcResp.size());
		LOGGER.info("Original Size: {}", svcResp2.size());
		List<IdmProfile> userList = new ArrayList<>();
		processPaginatedSearchResult(userList, svcResp);

		// Get max total records in table
		long totalRecords = dataTableInRQ.isInitSearch() ? svcResp2.size() : totalRecords(userProfile);
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(userList);
		LOGGER.info("isFilterByEmpty: {}", dataTableInRQ.getPaginationRequest().isFilterByEmpty());
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(svcResp2.size()));
		}
		return dataTableResult;
	}


	private void processPaginatedSearchResult(List<IdmProfile> userList, List<Object[]> svcResp) {
		if (!BaseUtil.isListNull(svcResp)) {
			for (Object[] curObj : svcResp) {
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

	}


	private StringBuilder searchQueryString(String selectCriteria, UserProfile userProfile) {
		StringBuilder sb = new StringBuilder(selectCriteria);
		sb.append(IdmProfile.class.getSimpleName() + " r, ");
		sb.append(IdmUserGroupRoleGroup.class.getSimpleName() + " ugrp, ");
		sb.append(IdmUserGroup.class.getSimpleName() + " iug ");
		sb.append(" where r.userRoleGroup.userGroupCode = ugrp.userGroupCode ");
		sb.append(" and ugrp.userRoleGroup.userGroupCode = iug.userGroupCode ");

		if (StringUtils.hasText(userProfile.getStatus())) {
			sb.append(" and r.status = :status ");
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			sb.append(" and r.userId = :userId ");
		}

		if (StringUtils.hasText(userProfile.getFirstName())) {
			sb.append(QUERY_FIRST_NAME);
		}

		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			sb.append(QUERY_USER_TYPE); 
		}

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			sb.append(" and ugrp.userGroupCode = :userGroupCode");
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			sb.append(" and ugrp.parentRoleGroup = :parentRoleGroup");
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			sb.append(QUERY_USER_ROLE_GROUP_CODE);
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			sb.append(" and r.userGroupCode in ( :userRoleGroupCodes )");
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			sb.append(" and r.syncFlag in ( :syncFlag )");
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			sb.append(" and r.profId in ( :profId )");
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			sb.append(" and r.branchId in ( :branchId )");
		}

		return sb;
	}


	private void searchQueryParameter(Query query, UserProfile userProfile) {
		if (StringUtils.hasText(userProfile.getStatus())) {
			query.setParameter(CONST_STATUS, userProfile.getStatus());
		}

		if (StringUtils.hasText(userProfile.getUserId())) {
			query.setParameter(CONST_USER_ID, userProfile.getUserId());
		}

		if (StringUtils.hasText(userProfile.getFirstName())) {
			query.setParameter(CONST_FULLNAME, "%" + userProfile.getFirstName() + "%");
		}

		if (!BaseUtil.isObjNull(userProfile.getUserType()) 
				&& StringUtils.hasText(userProfile.getUserType().getUserTypeCode())) {
			query.setParameter(CONST_USER_TYPE, userProfile.getUserType().getUserTypeCode()); 
		}	

		if (StringUtils.hasText(userProfile.getUserGroupCode())) {
			query.setParameter(CONST_USER_GROUP_CODE, userProfile.getUserGroupCode());
		}

		if (StringUtils.hasText(userProfile.getParentRoleGroup())) {
			query.setParameter(CONST_PARENT_ROLE_GROUP, userProfile.getParentRoleGroup());
		}

		if (StringUtils.hasText(userProfile.getUserRoleGroupCode())) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODE, userProfile.getUserRoleGroupCode());
		}

		if (!BaseUtil.isListNull(userProfile.getUserRoleGroupCodeList())) {
			query.setParameter(CONST_USER_ROLE_GROUP_CODES, userProfile.getUserRoleGroupCodeList());
		}

		if (!BaseUtil.isObjNull(userProfile.getSyncFlag())) {
			query.setParameter(CONST_SYNC_FLAG, userProfile.getSyncFlag());
		}

		if (!BaseUtil.isObjNull(userProfile.getProfId())) {
			query.setParameter(CONST_PROF_ID, userProfile.getProfId());
		}

		if (!BaseUtil.isObjNull(userProfile.getBranchId())) {
			query.setParameter(CONST_BRANCH_ID, userProfile.getBranchId());
		}
	}


	private long totalRecords(UserProfile userProfile) {
		StringBuilder sb = searchQueryString("select count(r) from ", userProfile);

		// Filtered Query by pagination
		Query query = entityManager.createQuery(sb.toString());

		searchQueryParameter(query, userProfile);

		return (long) query.getSingleResult();
	}

}