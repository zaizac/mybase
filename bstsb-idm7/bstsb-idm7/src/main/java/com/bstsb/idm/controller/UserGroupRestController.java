/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmUserGroup;
import com.bstsb.idm.model.IdmUserGroupRole;
import com.bstsb.idm.model.IdmUserGroupRoleBranch;
import com.bstsb.idm.model.IdmUserGroupRoleGroup;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.UserGroup;
import com.bstsb.idm.sdk.model.UserGroupRole;
import com.bstsb.idm.sdk.model.UserGroupRoleBranch;
import com.bstsb.idm.service.IdmUserGroupRoleBranchService;
import com.bstsb.idm.service.IdmUserGroupRoleGroupService;
import com.bstsb.idm.service.IdmUserGroupRoleService;
import com.bstsb.idm.service.IdmUserGroupService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.JsonUtil;
import com.bstsb.util.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.USER_GROUPS)
public class UserGroupRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupRestController.class);

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
	private IdmUserGroupRoleService idmUserGroupRolesService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_SVC)
	private IdmUserGroupService idmUserGroupService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_SVC)
	private IdmUserGroupRoleGroupService idmUserGroupRoleGroupService;

	@Autowired
	private IdmUserGroupRoleBranchService idmUserGroupRoleBranchSvc;


	@GetMapping(value = "/allGroups", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroup> getAllListUserGroups() {
		LOGGER.info("Retrieve ALL User Groups.");
		List<IdmUserGroup> userList = idmUserGroupService.findAllUserGroups();

		if (BaseUtil.isListNull(userList)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		List<UserGroup> userGroupList = new ArrayList<>();
		for (IdmUserGroup uGroup : userList) {
			userGroupList.add(dozerMapper.map(uGroup, UserGroup.class));
		}

		return userGroupList;
	}


	/**
	 * Create User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/createUserGroup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup addUserGroup(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Create User Group.");
		IdmUserGroup idmUserGroup = dozerMapper.map(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(userGroupDto.getCrtUsrId());
		idmUserGroup.setUpdateId(userGroupDto.getCrtUsrId());
		IdmUserGroup idmUserGroup1 = idmUserGroupService.create(idmUserGroup);
		return dozerMapper.map(idmUserGroup1, UserGroup.class);
	}


	/**
	 * Retrieve User Group by code
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/byGroupCode", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup getUserGroupByCode(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Retrieve User Groups by code.");
		IdmUserGroup idmUserGroup = idmUserGroupService.findUserGroupByRoleGroupCode(userGroupDto.getUserGroupCode());
		return dozerMapper.map(idmUserGroup, UserGroup.class);
	}


	/**
	 * Update User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/updateUserGroup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup updateMenu(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Update User Group.");
		IdmUserGroup idmUserGroup = dozerMapper.map(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(userGroupDto.getCrtUsrId());
		idmUserGroup.setUpdateId(userGroupDto.getCrtUsrId());
		IdmUserGroup idmUserGroup1 = idmUserGroupService.update(idmUserGroup);
		return dozerMapper.map(idmUserGroup1, UserGroup.class);
	}


	/**
	 * Retrieve User Group by role group code
	 *
	 * @param roleGroupCode
	 * @return
	 */
	@GetMapping(value = "/{roleGroupCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroup> getUserGroupByRoleGroupCode(@PathVariable String roleGroupCode,
			@RequestParam(required = false) boolean hasMaxNoUserCreated,
			@RequestParam(required = false) boolean noSystemCreate, HttpServletRequest request) {
		LOGGER.info("Retrieve User Groups by role group code.");
		List<UserGroup> userGroupList = new ArrayList<>();

		List<IdmUserGroupRoleGroup> groupList = new ArrayList<>();

		if (hasMaxNoUserCreated) {
			String currUserId = getCurrUserId(request);
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode, noSystemCreate,
					currUserId);
		} else {
			groupList = idmUserGroupRoleGroupService.findUserGroupByRoleGroupCode(roleGroupCode);
		}

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		for (IdmUserGroupRoleGroup uGroup : groupList) {
			userGroupList.add(dozerMapper.map(uGroup, UserGroup.class));
		}
		return userGroupList;
	}


	/**
	 * Retrieve User Group by parent role group
	 *
	 * @param roleGroupCode
	 * @return
	 */
	@GetMapping(value = "/parent/{roleGroupCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroup> findUserGroupByParentRoleGroup(@PathVariable(value = "roleGroupCode") String roleGroupCode,
			@RequestParam(required = false) boolean hasMaxNoUserCreated,
			@RequestParam(required = false) boolean noSystemCreate, HttpServletRequest request) {
		LOGGER.info("Retrieve User Groups by parent role group.");
		List<UserGroup> userGroupList = new ArrayList<>();

		List<IdmUserGroupRoleGroup> groupList = new ArrayList<>();

		if (hasMaxNoUserCreated) {
			String currUserId = getCurrUserId(request);
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode, noSystemCreate,
					currUserId);
		} else {
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode);
		}

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupList >> {} ", new ObjectMapper().valueToTree(groupList));
		}
		for (IdmUserGroupRoleGroup uGroup : groupList) {
			UserGroup ug = dozerMapper.map(uGroup, UserGroup.class);
			IdmUserGroup iug = uGroup.getUserRoleGroup();
			if (!BaseUtil.isObjNull(iug)) {
				ug.setUserGroupDesc(iug.getUserGroupDesc());
			}
			userGroupList.add(ug);
		}
		return userGroupList;
	}


	/**
	 * Update Multiple User Group Roles
	 *
	 * @param userGroupRole
	 * @return
	 */
	@PostMapping(value = "/updateRoles", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroupRole> updateUserGroupRoleGroup(@RequestBody List<UserGroupRole> userGroupRole) {
		// first find if not exists insert
		if (BaseUtil.isListNull(userGroupRole)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		for (UserGroupRole ug : userGroupRole) {
			IdmUserGroupRole role = idmUserGroupRolesService.findUserGroupByRoleAndGroup(ug.getRoleCode(),
					ug.getUserRoleGroupCode());
			// found the roles based on the role.
			// if there is no same group then need to insert.
			if (BaseUtil.isObjNull(role)) {
				IdmUserGroupRole r = new IdmUserGroupRole();
				r.setRoleCode(ug.getRoleCode());
				r.setUserRoleGroupCode(ug.getUserRoleGroupCode());
				idmUserGroupRolesService.create(r);
			}
		}

		// if user removed from assigned list we need to remove from db as
		// well
		// all userRoleGroupCodes are same.

		List<IdmUserGroupRole> roleListByGroup = idmUserGroupRolesService
				.findUserGroupByUserRoleGroupCode(userGroupRole.get(0).getUserRoleGroupCode());
		for (IdmUserGroupRole r : roleListByGroup) {
			boolean exists = false;
			for (UserGroupRole ur : userGroupRole) {
				if (ur.getRoleCode().equals(r.getRoleCode())) {
					exists = true;
				}
			}
			if (!exists) {
				idmUserGroupRolesService.delete(r.getId());
			}
		}

		return userGroupRole;
	}


	/**
	 * Find User Group Role
	 *
	 * @param userGroupRoleCode
	 * @return
	 */
	@GetMapping(value = "/findRoles", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroupRole> findUserGroupRoleByGroupCode(
			@RequestParam("userGroupRoleCode") String userGroupRoleCode) {
		List<UserGroupRole> userGroupList = new ArrayList<>();

		List<IdmUserGroupRole> groupList = idmUserGroupRolesService
				.findUserGroupByUserRoleGroupCodeInstant(userGroupRoleCode);

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
		for (IdmUserGroupRole uGroup : groupList) {
			userGroupList.add(dozerMapper.map(uGroup, UserGroupRole.class));
		}
		return userGroupList;
	}


	/**
	 * Find User Group Role
	 *
	 * @param userGroupRoleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.USER_GROUPS_BRANCH_SEARCH, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroupRoleBranch> findUserGroupBranchByUserGroupCode(
			@Valid @RequestBody UserGroupRoleBranch userGroupBranch) {

		List<IdmUserGroupRoleBranch> groupList = idmUserGroupRoleBranchSvc.search(userGroupBranch);

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		JsonNode jnode = JsonUtil.toJsonNode(groupList);
		try {
			return JsonUtil.transferToList(jnode, UserGroupRoleBranch.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}

}
