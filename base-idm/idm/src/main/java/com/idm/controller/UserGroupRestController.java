package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmTxnCodeConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmMenu;
import com.idm.model.IdmProfile;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupBranch;
import com.idm.model.IdmUserGroupRole;
import com.idm.model.IdmUserGroupRoleGroup;
import com.idm.model.IdmUserType;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserGroupBranch;
import com.idm.sdk.model.UserGroupRole;
import com.idm.sdk.model.UserMenu;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmUserGroupBranchService;
import com.idm.service.IdmUserGroupRoleGroupService;
import com.idm.service.IdmUserGroupRoleService;
import com.idm.service.IdmUserGroupService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.USER_GROUPS)
public class UserGroupRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupRestController.class);

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_SVC)
	private IdmUserGroupRoleService idmUserGroupRolesService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_SVC)
	private IdmUserGroupService idmUserGroupService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_GROUP_SVC)
	private IdmUserGroupRoleGroupService idmUserGroupRoleGroupService;

	@Autowired
	private IdmUserGroupBranchService idmUserGroupBranchSvc;

	@Autowired
	private IdmProfileService idmProfileSvc;


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/allGroups", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroup> getAllListUserGroups() throws IOException {
		LOGGER.info("Retrieve ALL User Groups.");
		List<IdmUserGroup> userList = idmUserGroupService.findAllUserGroups();

		if (BaseUtil.isListNull(userList)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		return JsonUtil.transferToList(userList, UserGroup.class);
	}


	/**
	 * Create User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/createUserGroup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup addUserGroup(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request)
			throws IOException {
		LOGGER.info("Create User Group. {}", userGroupDto.getUserTypeCode());
		IdmUserGroup idmUserGroup = JsonUtil.transferToObject(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(getCurrUserId(request));
		idmUserGroup.setUpdateId(getCurrUserId(request));
		IdmUserType idmUserType = JsonUtil.transferToObject(userGroupDto.getUserType(), IdmUserType.class);
		idmUserGroup.setUserType(idmUserType);
		IdmUserGroup idmUserGroup1 = idmUserGroupService.create(idmUserGroup);
		return JsonUtil.transferToObject(idmUserGroup1, UserGroup.class);
	}


	/**
	 * Delete User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.USER_GROUPS_DELETE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deleteUserGroup(@RequestBody UserGroup userGroupDto, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(userGroupDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmUserGroup idmUserGroup = JsonUtil.transferToObject(userGroupDto, IdmUserGroup.class);
		return idmUserGroupService.delete(idmUserGroup);
	}


	/**
	 * Retrieve User Group by code
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.FIND_USER_GROUP_BY_CODE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroup findUsrGrpCodeByGrpCode(@PathVariable String groupCode) throws IOException {
		IdmUserGroup idmUserGroup = idmUserGroupService.findByUserGroupCode(groupCode);
		if (idmUserGroup == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToObject(idmUserGroup, UserGroup.class);
	}


	/**
	 * Update User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/updateUserGroup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup updateUserGroup(@Valid @RequestBody UserGroup userGroupDto, HttpServletRequest request)
			throws IOException {
		LOGGER.info("Update User Group.");
		IdmUserGroup idmUserGroup = JsonUtil.transferToObject(userGroupDto, IdmUserGroup.class);
		idmUserGroup.setCreateId(getCurrUserId(request));
		idmUserGroup.setUpdateId(getCurrUserId(request));
		IdmUserType idmUserType = new IdmUserType();
		idmUserType = JsonUtil.transferToObject(userGroupDto.getUserType(), IdmUserType.class);
		idmUserGroup.setUserType(idmUserType);
		IdmUserGroup idmUserGroup1 = idmUserGroupService.update(idmUserGroup);
		return JsonUtil.transferToObject(idmUserGroup1, UserGroup.class);
	}


	/**
	 * Retrieve User Group by role group code
	 *
	 * @param roleGroupCode
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/{roleGroupCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroup> getUserGroupByRoleGroupCode(@PathVariable String roleGroupCode,
			@RequestParam(required = false) boolean hasMaxNoUserCreated,
			@RequestParam(required = false) boolean noSystemCreate, HttpServletRequest request) throws IOException {
		LOGGER.info("Retrieve User Groups by role group code.");
		List<IdmUserGroupRoleGroup> groupList = new ArrayList<>();

		String systemType = getSystemHeader(request);
		
		if (hasMaxNoUserCreated) {
			String currUserId = getCurrUserId(request);
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode, systemType, noSystemCreate,
					currUserId);
		} else if(!BaseUtil.isObjNull(systemType)) {
			groupList = idmUserGroupRoleGroupService.findUserGroupByRoleGroupCode(roleGroupCode, systemType);
		} else {
			groupList = idmUserGroupRoleGroupService.findUserGroupByRoleGroupCode(roleGroupCode);
		}

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		return JsonUtil.transferToList(groupList, UserGroup.class);
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
		String userId = getCurrUserId(request);
		LOGGER.info("Retrieve User Groups by parent role group. - {}", userId);
		List<UserGroup> userGroupList = new ArrayList<>();

		String systemType = getSystemHeader(request);
		
		List<IdmUserGroupRoleGroup> groupList;

		if (hasMaxNoUserCreated) {
			String currUserId = getCurrUserId(request);
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode, systemType, noSystemCreate,
					currUserId);
		} else if(!BaseUtil.isObjNull(systemType)) {
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode, systemType);
		} else {
			groupList = idmUserGroupRoleGroupService.findUserGroupByParentRoleGroup(roleGroupCode);
		}

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupList >> {} ", objectMapper.valueToTree(groupList));
		}
		for (IdmUserGroupRoleGroup uGroup : groupList) {
			try {
				UserGroup ug = JsonUtil.transferToObject(uGroup, UserGroup.class);
				IdmUserGroup iug = uGroup.getUserRoleGroup();
				if (!BaseUtil.isObjNull(iug)) {
					ug.setUserGroupDesc(iug.getUserGroupDesc());
				}
				if (hasMaxNoUserCreated) {
					boolean remove = false;
					// Check the maxNoUserCreated if reached the
					// maxNoOfUser
					if (!BaseUtil.isObjNull(uGroup.getMaxNoOfUser())
							&& !BaseUtil.isObjNull(uGroup.getMaxNoOfUserCreated())
							&& uGroup.getMaxNoOfUser() <= uGroup.getMaxNoOfUserCreated()
							&& (Boolean.FALSE.equals(!uGroup.isCreateByProfId())
									|| Boolean.FALSE.equals(!uGroup.isCreateByBranchId()))) {
						remove = true;
					} else {
						// Check if the role is per ProfId
						if (uGroup.isCreateByProfId()) {
							IdmProfile profile = idmProfileSvc.findByUserId(userId);
							if (!BaseUtil.isObjNull(profile)) {
								int maxCnt = idmProfileSvc.findMaxCount(userId, profile.getProfId());
								LOGGER.info("MAX COUNT: {}", maxCnt);
								if (uGroup.getMaxNoOfUser() <= maxCnt) {
									remove = true;
								}
							}
						}
					}
					if (!remove) {
						userGroupList.add(ug);
					}
				} else {
					userGroupList.add(ug);
				}
			} catch (IOException e) {
			}

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

		/**
		 * TODO: REDO LOGIC for (UserGroupRole ug : userGroupRole) {
		 * IdmUserGroupRole role =
		 * idmUserGroupRolesService.findUserGroupByRoleAndGroup(ug.getRoleCode(),
		 * ug.getUserRoleGroupCode()); // found the roles based on the role.
		 * // if there is no same group then need to insert. if
		 * (BaseUtil.isObjNull(role)) { IdmUserGroupRole r = new
		 * IdmUserGroupRole(); IdmRole idmRole = new IdmRole();
		 * idmRole.setRoleCode(ug.getRoleCode()); r.setRole(idmRole);
		 * IdmUserGroup userGroup = new IdmUserGroup();
		 * userGroup.setUserGroupCode(ug.getUserRoleGroupCode()); // TODO:
		 * r.setUserRoleGroupCode(ug.getUserRoleGroupCode());
		 * idmUserGroupRolesService.create(r); } }
		 */

		// if user removed from assigned list we need to remove from db as
		// well
		// all userRoleGroupCodes are same.

		/**
		 * TODO: REDO LOGIC List<IdmUserGroupRole> roleListByGroup =
		 * idmUserGroupRolesService
		 * .findUserGroupByUserRoleGroupCode(userGroupRole.get(0).getUserRoleGroupCode());
		 * for (IdmUserGroupRole r : roleListByGroup) { boolean exists =
		 * false; for (UserGroupRole ur : userGroupRole) { if
		 * (ur.getRoleCode().equals(r.getRole().getRoleCode())) { exists =
		 * true; } } if (!exists) {
		 * idmUserGroupRolesService.delete(r.getId()); } }
		 * 
		 * return userGroupRole;
		 */
		return null;
	}


	/**
	 * Find User Group Role
	 *
	 * @param userGroupRoleCode
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/findRoles", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroupRole> findUserGroupRoleByGroupCode(
			@RequestParam("userGroupRoleCode") String userGroupRoleCode) throws IOException {
		List<IdmUserGroupRole> groupList = idmUserGroupRolesService
				.findUserGroupByUserRoleGroupCodeInstant(userGroupRoleCode);

		if (BaseUtil.isListNull(groupList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
		return JsonUtil.transferToList(groupList, UserGroupRole.class);
	}


	/********** USER GROUP BRANCH *******************/

	/**
	 * Find All User Group Branch lazy load
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.USER_GROUP_BRANCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserGroupBranch> getAllUserGroupBranch() throws IOException {
		List<UserGroupBranch> userGroupBranchLst = JsonUtil.transferToList(idmUserGroupBranchSvc.findAllUserGroups(),
				UserGroupBranch.class);

		if (BaseUtil.isListNull(userGroupBranchLst).booleanValue()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		try {
			return JsonUtil.transferToList(userGroupBranchLst, UserGroupBranch.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM124);
		}
	}


	/**
	 * Create User Group Branch
	 * 
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.USER_GROUP_BRANCH_CREATE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroupBranch createUserGroupBranch(@RequestBody UserGroupBranch userGroupBranches,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (BaseUtil.isObjNull(userGroupBranches)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmUserGroupBranch iugb = JsonUtil.transferToObject(userGroupBranches, IdmUserGroupBranch.class);
		iugb.setCreateId(BaseUtil.getStr(request.getAttribute(getCurrUserId(request))));
		iugb.setCreateDt(new Timestamp(new Date().getTime()));
		iugb.setUpdateId(BaseUtil.getStr(request.getAttribute(getCurrUserId(request))));
		iugb.setUpdateDt(new Timestamp(new Date().getTime()));
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_GROUP_BRANCH_NEW);
		iugb = idmUserGroupBranchSvc.create(iugb);
		UserGroupBranch result = JsonUtil.transferToObject(iugb, UserGroupBranch.class);
		return result;
	}


	/**
	 * Update User Group Branch
	 * 
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.USER_GROUP_BRANCH_UPDATE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserGroupBranch updateUserGroupBranch(@Valid @RequestBody UserGroupBranch userGroupBranches,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (BaseUtil.isObjNull(userGroupBranches)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		IdmUserGroupBranch iugb = JsonUtil.transferToObject(userGroupBranches, IdmUserGroupBranch.class);
		iugb.setUpdateId(BaseUtil.getStr(request.getAttribute(getCurrUserId(request))));
		iugb.setUpdateDt(new Timestamp(new Date().getTime()));
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_GROUP_BRANCH_UPD);
		UserGroupBranch result = JsonUtil.transferToObject(idmUserGroupBranchSvc.update(iugb), UserGroupBranch.class);
		return result;
	}


	/**
	 * Delete User Group Branch
	 * 
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.USER_GROUP_BRANCH_DELETE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deleteUserGroupBranch(@RequestBody UserGroupBranch ugb, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(ugb)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_GROUP_BRANCH_DEL);
		IdmUserGroupBranch iugb = JsonUtil.transferToObject(ugb, IdmUserGroupBranch.class);
		return idmUserGroupBranchSvc.delete(iugb);
	}


	/**
	 * Search User Group Branch
	 *
	 * @param userGroupRoleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.USER_GROUP_BRANCH_SEARCH, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroupBranch> searchUserGroupBranch(
			@Valid @RequestBody IdmUserGroupBranch userGroupBranch, HttpServletRequest request) throws IOException {

		if (BaseUtil.isObjNull(userGroupBranch)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		String systemType = getSystemHeader(request);
		if(!BaseUtil.isObjNull(systemType)) {
			userGroupBranch.setSystemType(systemType);
		}
		List<IdmUserGroupBranch> groupList = idmUserGroupBranchSvc.search(userGroupBranch);

		if (Boolean.TRUE.equals(BaseUtil.isListNull(groupList))) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		try {
			return JsonUtil.transferToList(groupList, UserGroupBranch.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}
	
	/**
	 * search menu by menucode, desc, level and parent code
	 * 
	 * @param idmMenuDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.USER_GROUPS_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
public List<UserGroup> searchUserGroup(@RequestBody UserGroup userGroup) throws IOException {
		
		if (BaseUtil.isObjNull(userGroup)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		List<IdmUserGroup> idmUserGroup = idmUserGroupService.searchUserGroup(userGroup);
		return JsonUtil.transferToList(idmUserGroup, UserGroup.class);
	}

}
