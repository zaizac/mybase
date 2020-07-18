package com.bff.idm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.bff.cmn.constants.BffUrlConstants;
import com.bff.core.AbstractController;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserGroupBranch;
import com.idm.sdk.model.UserType;
import com.util.BaseUtil;
import com.util.MediaType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_USER_GROUP)
public class UserGroupController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupController.class);
	
	ObjectMapper objMap = new ObjectMapper();
	
	@PostMapping(value = "/addUserGroup", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserGroup addUserGroup(@RequestBody UserGroup userGrp, HttpServletRequest request) {
		LOGGER.debug("UserGroup: {}", objMap.valueToTree(userGrp));
		return getIdmService().createUserGroup(userGrp);
	}
	
	@PostMapping(value = "/updateUserGroup", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserGroup updateUserGroup(@RequestBody UserGroup userGrp, HttpServletRequest request) {
		LOGGER.debug("UserGroup: {}", objMap.valueToTree(userGrp));
		return getIdmService().updateUserGroup(userGrp);
	}
	
	@GetMapping(value = "/userTypeList", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserType> findAllUserType(){
		return getIdmService().findAllUserTypes();
	}
	
	@GetMapping(value = "/userGroupList", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroup> findAllUserGroup(){
		return getIdmService().findAllUserGroups();
	}
	
	@PostMapping(value = "/deleteUserGroup", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteUserGroup(@RequestBody UserGroup userGrp, HttpServletRequest request) {
		LOGGER.debug("UserGroup: {}", objMap.valueToTree(userGrp));
		return getIdmService().deleteUserGroup(userGrp);
	}
	
	@GetMapping(value = "/byGroupCode", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroup findByGroupCode(@RequestParam(value = "groupCode", required = true) String groupCode) {
		LOGGER.debug("groupCode: {}", objMap.valueToTree(groupCode));
		return getIdmService().findUsrGroupByGrpCode(groupCode);
	}

	/****** USER GROUP BRANCH *************/
	@GetMapping(value = BffUrlConstants.USER_GROUP_BRANCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<UserGroupBranch> findAllUserGroupBranch() {
		return getIdmService().findAllUserGroupBranch();
	}
	
	@PostMapping(value = BffUrlConstants.USER_GROUP_BRANCH_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody UserGroupBranch createUserGroupBranch(@RequestBody UserGroupBranch ugb, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ugb)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		LOGGER.debug("Create User Group Branch : {}", new GsonBuilder().create().toJson(ugb));
		return getIdmService().createUserGroupBranch(ugb);
	}

	@PostMapping(value = BffUrlConstants.USER_GROUP_BRANCH_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody UserGroupBranch updateUserGroupBranch(@RequestBody UserGroupBranch ugb, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ugb)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		LOGGER.debug("Update UserGroupBranch : {}", new GsonBuilder().create().toJson(ugb));
		return getIdmService().updateUserGroupBranch(ugb);
	}

	@PostMapping(value = BffUrlConstants.USER_GROUP_BRANCH_DELETE, consumes = { MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deleteUserGroupBranch (@RequestBody UserGroupBranch ugb, HttpServletRequest request) {
		return getIdmService().deleteUserGroupBranch(ugb);
	}
	
	@PostMapping(value = BffUrlConstants.USER_GROUP_BRANCH_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<UserGroupBranch> searchUserGroupBranch(@RequestBody UserGroupBranch ugb, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ugb)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		LOGGER.debug("Search UserGroupBranch : Query Param : {}", new GsonBuilder().create().toJson(ugb));
		return getIdmService().searchUserGroupBranch(ugb);
	}
	
	@PostMapping(value = "/searchUserGroup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<UserGroup> searchUserGroup(@RequestBody UserGroup ugLst, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ugLst)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		LOGGER.debug("Search UserGroup : {}", new GsonBuilder().create().toJson(ugLst));
		return getIdmService().searchUserGroup(ugLst);
	}
}
