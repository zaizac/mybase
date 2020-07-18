package com.bff.idm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.exception.BeException;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.google.gson.GsonBuilder;
import com.idm.sdk.model.UserGroupRole;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.pagination.DataTableResults;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_USER_GROUP_ROLE)
public class UserGroupRoleController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupRoleController.class);
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserGroupRole> findAllUserType() {
		return getIdmService().findAllUserGroupRole();
	}
	
	@GetMapping(value = "/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<UserGroupRole> getApplicationInfoPaginated(UserGroupRole groupRole, BindingResult result,
			@RequestParam("portalAdmin") boolean isPortalAdmin, HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<UserGroupRole> tasks = null;
		try {
			if (!BaseUtil.isObjNull(groupRole)) {
				tasks = getIdmService().searchUserGroupRole(groupRole, false, getPaginationRequest(request, true));
			}
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		LOGGER.info("Result data: {}", tasks.getData());
		
		return tasks;
	}
	
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserGroupRole createUserGroupRole(@RequestBody UserGroupRole groupRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(groupRole) && BaseUtil.isObjNull(groupRole.getUserGroup()) && BaseUtil.isObjNull(groupRole.getRole())) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Reason : " + new GsonBuilder().create().toJson(groupRole));
		return getIdmService().createUserGroupRole(groupRole);
	}


	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserGroupRole updateUserGroupRole(@RequestBody UserGroupRole groupRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(groupRole)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(groupRole));
		return getIdmService().updateUserGroupRole(groupRole);
	}
	
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody boolean deleteUserGroupRole(@RequestBody UserGroupRole groupRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(groupRole)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(groupRole));
		return getIdmService().deleteUserGroupRole(groupRole);
	}
	
	@GetMapping(value = "/groupRole", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserGroupRole findByGroupId(@RequestParam(value = "id", required = true) Integer id) {
		return getIdmService().findGroupRoleById(id);
	}

}
