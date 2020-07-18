/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.model.IdmUserGroup;
import com.baseboot.idm.model.IdmUserGroupRole;
import com.baseboot.idm.model.IdmUserGroupRoleGroup;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.UserGroup;
import com.baseboot.idm.sdk.model.UserGroupRole;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmUserGroupRoleGroupService;
import com.baseboot.idm.service.IdmUserGroupRoleService;
import com.baseboot.idm.service.IdmUserGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.USER_GROUPS)
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


	@RequestMapping(value = "/allGroups", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroup> getAllListUserGroups() {
		LOGGER.info("Retrieve ALL User Groups.");
		List<IdmUserGroup> userList = idmUserGroupService.findAllUserGroups();

		if (BaseUtil.isListNull(userList)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		List<UserGroup> userGroupList = new ArrayList<UserGroup>();
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
	@RequestMapping(value = "/createUserGroup", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroup addUserGroup(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Create User Group.");
		IdmUserGroup idmUserGroup = dozerMapper.map(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(userGroupDto.getCrtUsrId());
		idmUserGroup.setUpdateId(userGroupDto.getCrtUsrId());
		IdmUserGroup idmUserGroup1 = idmUserGroupService.create(idmUserGroup);
		UserGroup UserGroup2 = dozerMapper.map(idmUserGroup1, UserGroup.class);
		return UserGroup2;
	}


	/**
	 * Retrieve User Group by code
	 * 
	 * @param userGroupDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/byGroupCode", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroup getUserGroupByCode(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Retrieve User Groups by code.");
		IdmUserGroup idmUserGroup = idmUserGroupService.findUserGroupByRoleGroupCode(userGroupDto.getUserGroupCode());
		UserGroup UserGroup1 = dozerMapper.map(idmUserGroup, UserGroup.class);
		return UserGroup1;
	}


	/**
	 * Update User Group
	 * 
	 * @param userGroupDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserGroup", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroup updateMenu(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request) {
		LOGGER.info("Update User Group.");
		IdmUserGroup idmUserGroup = dozerMapper.map(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(userGroupDto.getCrtUsrId());
		idmUserGroup.setUpdateId(userGroupDto.getCrtUsrId());
		IdmUserGroup idmUserGroup1 = idmUserGroupService.update(idmUserGroup);
		UserGroup UserGroup2 = dozerMapper.map(idmUserGroup1, UserGroup.class);
		return UserGroup2;
	}


	// /byGroupCode
	/**
	 * Retrieve User Group by role group code
	 * 
	 * @param roleGroupCode
	 * @return
	 */
	@RequestMapping(value = "/{roleGroupCode}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroup> getUserGroupByRoleGroupCode(@PathVariable String roleGroupCode) {
		LOGGER.info("Retrieve User Groups by role group code.");
		List<UserGroup> userGroupList = new ArrayList<UserGroup>();

		List<IdmUserGroupRoleGroup> groupList = idmUserGroupRoleGroupService
				.findUserGroupByRoleGroupCode(roleGroupCode);

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
	@RequestMapping(value = "/parent/{roleGroupCode}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroup> findUserGroupByParentRoleGroup(@PathVariable String roleGroupCode) {
		LOGGER.info("Retrieve User Groups by parent role group.");
		List<UserGroup> userGroupList = new ArrayList<UserGroup>();

		List<IdmUserGroupRoleGroup> groupList = idmUserGroupRoleGroupService
				.findUserGroupByParentRoleGroup(roleGroupCode);

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		System.err.println("groupList >> " + new ObjectMapper().valueToTree(groupList));
		for (IdmUserGroupRoleGroup uGroup : groupList) {
			IdmUserGroup ug = uGroup.getUserRoleGroup();
			userGroupList.add(dozerMapper.map(ug, UserGroup.class));
		}
		return userGroupList;
	}


	/**
	 * Update Multiple User Group Roles
	 * 
	 * @param userGroupRole
	 * @return
	 */
	@RequestMapping(value = "/updateRoles", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
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
	@RequestMapping(value = "/findRoles", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroupRole> findUserGroupRoleByGroupCode(
			@RequestParam("userGroupRoleCode") String userGroupRoleCode) {
		List<UserGroupRole> userGroupList = new ArrayList<UserGroupRole>();

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

}
