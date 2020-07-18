package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idm.core.AbstractRestController;
import com.idm.model.IdmRole;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserGroupRole;
import com.idm.service.IdmUserGroupRoleService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


@RestController
@RequestMapping(IdmUrlConstants.USER_GROUP_ROLE)
public class UserGroupRoleController extends AbstractRestController {

	@Autowired
	private IdmUserGroupRoleService idmUserGroupRoleService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupRoleController.class);


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/all", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserGroupRole> getAllListRoleMenu() {
		List<IdmUserGroupRole> roleMenuList = idmUserGroupRoleService.findAllUserGroup();

		if (BaseUtil.isListNull(roleMenuList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		try {
			return JsonUtil.transferToList(roleMenuList, UserGroupRole.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
	}


	@PostMapping(value = "/createGroupRole", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroupRole addUserGroupRole(@Valid @RequestBody UserGroupRole userGroupRoleDto,
			HttpServletRequest request) throws IOException {
		LOGGER.info("Create User Group Role. {}", userGroupRoleDto.getUserGroupCode());
		IdmUserGroupRole idmUserGroupRole = JsonUtil.transferToObject(userGroupRoleDto, IdmUserGroupRole.class);
		idmUserGroupRole.setCreateId(getCurrUserId(request));
		idmUserGroupRole.setUpdateId(getCurrUserId(request));
		IdmUserGroup idmUserGroup = JsonUtil.transferToObject(userGroupRoleDto.getUserGroup(), IdmUserGroup.class);
		idmUserGroupRole.setUserGroup(idmUserGroup);
		IdmRole idmRole = JsonUtil.transferToObject(userGroupRoleDto.getRole(), IdmRole.class);
		idmUserGroupRole.setRole(idmRole);
		IdmUserGroupRole idmUserGroupRole1 = idmUserGroupRoleService.create(idmUserGroupRole);
		return JsonUtil.transferToObject(idmUserGroupRole1, UserGroupRole.class);
	}


	@PostMapping(value = "/updateGroupRole", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroupRole updateUserGroupRole(@Valid @RequestBody UserGroupRole gr, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (BaseUtil.isObjNull(gr)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		String userId = BaseUtil.getStr(request.getAttribute("currUserId"));
		IdmUserGroupRole idmGroupRole = idmUserGroupRoleService.find(gr.getId());

		if (!BaseUtil.isObjNull(idmGroupRole)) {
			idmGroupRole.setRole(JsonUtil.transferToObject(gr.getRole(), IdmRole.class));
			idmGroupRole.setUserGroup(JsonUtil.transferToObject(gr.getUserGroup(), IdmUserGroup.class));
			idmGroupRole.setUpdateId(userId);
			idmGroupRole.setUpdateDt(new Timestamp(new Date().getTime()));

			idmGroupRole = idmUserGroupRoleService.update(idmGroupRole);
		}

		return JsonUtil.transferToObject(idmGroupRole, UserGroupRole.class);
	}


	@PostMapping(value = "/delete", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public boolean deleteUserGroupRole(@Valid @RequestBody UserGroupRole gr, HttpServletRequest request,
			HttpServletResponse response) {

		if (BaseUtil.isObjNull(gr)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
		return idmUserGroupRoleService.delete(gr.getId());
	}


	@PostMapping(value = IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<UserGroupRole> searchUserProfilePaginated(@Valid @RequestBody UserGroupRole groupRole,
			@Valid @RequestParam(value = "isEmail", defaultValue = "false") boolean isEmail,
			HttpServletRequest request) {
		DataTableRequest<UserGroupRole> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<UserGroupRole> all = idmUserGroupRoleService.searchByPagination(groupRole, null);
		List<UserGroupRole> filtered = idmUserGroupRoleService.searchByPagination(groupRole, dataTableInRQ);
		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}


	/**
	 * Retrieve User Group Role by id
	 *
	 * @param userGroupRoleDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/findGroupRoleId/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroupRole findGroupRoleById(@PathVariable Integer id) throws IOException {
		IdmUserGroupRole idmUserGroupRole = idmUserGroupRoleService.findByGroupRoleId(id);
		if (idmUserGroupRole == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToObject(idmUserGroupRole, UserGroupRole.class);
	}

}
