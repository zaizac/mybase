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
import com.bff.core.AbstractController;
import com.bff.util.constants.PageConstants;
import com.google.gson.GsonBuilder;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.model.UserRole;
import com.util.BaseUtil;
import com.util.MediaType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_USR_ROLE)
public class UserRoleController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleController.class);
	
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserRole> findAllRoles() {
		return getIdmService().findAllRoles();
	}
	
	@GetMapping(value = IdmUrlConstants.PORTALTYPE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserRole> findAllRolesIncludePortalType() {
		return getIdmService().findAllRolesIncludePortalType();
	}

	
	@PostMapping(value = IdmUrlConstants.CREATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<UserRole> createRole(@RequestBody UserRole userRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userRole)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Role : {}", new GsonBuilder().create().toJson(userRole));
		return getIdmService().createRole(userRole);
	}


	@PostMapping(value = IdmUrlConstants.UPDATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<UserRole> updateRole(@RequestBody UserRole userRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userRole)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Role : {}", new GsonBuilder().create().toJson(userRole));
		return getIdmService().updateRole(userRole);
	}
	
	
	@PostMapping(value = IdmUrlConstants.SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody List<UserRole> searchRole(@RequestBody UserRole role, HttpServletRequest request) {
		if (BaseUtil.isObjNull(role)) {
			throw new BeException(BeErrorCodeEnum.E400C003);
		}
		LOGGER.debug("Search Role : Query Param : {}", new GsonBuilder().create().toJson(role));
		return getIdmService().searchRole(role);
	}

	
	@PostMapping(value = IdmUrlConstants.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteRole(@RequestBody UserRole userRole, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userRole)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Delete Role : {}", new GsonBuilder().create().toJson(userRole));
		return getIdmService().deleteRole(userRole);
	}


	@GetMapping(value = "/findByUserRoleCd", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserRole searchByUserTypeCode(@RequestParam(value = "roleCode") String roleCode,
			HttpServletRequest request) {
		LOGGER.debug("Reference .... {}", roleCode);
		if (BaseUtil.isStringNull(roleCode)) {
			throw new BeException("Invalid portal type - Document DocDesc is empty.");
		}
		return getIdmService().getRoleByRoleCode(roleCode);
	}

}
