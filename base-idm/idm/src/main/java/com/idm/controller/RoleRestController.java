package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmTxnCodeConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmMenu;
import com.idm.model.IdmProfile;
import com.idm.model.IdmRole;
import com.idm.model.IdmRoleMenu;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AssignRole;
import com.idm.sdk.model.RoleMenu;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.idm.service.IdmMenuService;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmRoleMenuService;
import com.idm.service.IdmRoleService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.ROLES)
public class RoleRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleRestController.class);

	private static final String CURR_USER_ID = "currUserId";

	@Autowired
	private IdmRoleService idmRoleService;

	@Autowired
	private IdmProfileService idmProfileSvc;

	@Autowired
	private IdmMenuService idmMenuSvc;

	@Autowired
	private IdmRoleMenuService idmRoleMenuSvc;


	/**
	 * Find All Roles lazy load Portal Type
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserRole> getAllRoles() throws IOException {
		List<IdmRole> tidmRoleLst = idmRoleService.findAll();
		if (BaseUtil.isListNull(tidmRoleLst).booleanValue()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		try {
			return JsonUtil.transferToList(tidmRoleLst, UserRole.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}


	/**
	 * Find All Roles eager load Portal Type
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.PORTALTYPE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserRole> getAllRolesIncludesPortalTypes() throws IOException {
		List<IdmRole> tidmRoleLst = idmRoleService.findAllContainsPortalType();
		if (BaseUtil.isListNull(tidmRoleLst).booleanValue()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		
		try {
			return JsonUtil.transferToList(tidmRoleLst, UserRole.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}


	/**
	 * Get role by code
	 * 
	 * @param roleCode
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "findByRoleCode", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserRole findByRoleCode(@RequestParam String roleCode) throws IOException {
		IdmRole tidmRole = idmRoleService.findByRoleCode(roleCode);
		if (tidmRole == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		return JsonUtil.transferToObject(tidmRole, UserRole.class);
	}


	/**
	 * Create Role
	 *
	 * @param tr
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserRole> addRole(@Valid @RequestBody UserRole role, HttpServletRequest request,
			HttpServletResponse response) {
		if (role == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		List<UserRole> returnUserRole = new ArrayList<>();

		if (!StringUtils.hasText(role.getRoleCode()) || BaseUtil.isObjNull(role.getRoleDesc())) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.hasText(role.getRoleCode())) {
				sb.append("roleCode,");
			}
			if (BaseUtil.isObjNull(role.getRoleDesc())) {
				sb.append("roleDesc,");
			}

			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { sb.toString(), "Role" });
		}

		IdmRole roleObj = idmRoleService.findByRoleCode(role.getRoleCode().trim());
		if (roleObj != null) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM136, new String[] { role.getRoleCode() });
		}

		try {
			IdmRole idmRole = JsonUtil.transferToObject(role, IdmRole.class);
			idmRole.setCreateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
			idmRole.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
			idmRole = idmRoleService.create(idmRole);
			request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_NEW);
			returnUserRole.add(JsonUtil.transferToObject(idmRole, UserRole.class));
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}

		return returnUserRole;
	}


	/**
	 * Update Role
	 *
	 * @param roleCode
	 * @param role
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserRole> updateRole(@Valid @RequestBody UserRole role, HttpServletRequest request,
			HttpServletResponse response) {
		if (role == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		List<UserRole> returnUserRole = new ArrayList<>();

		try {
			IdmRole idmRole = JsonUtil.transferToObject(role, IdmRole.class);
			LOGGER.debug("idmRole.getRoleCode(): {}", idmRole.getRoleCode());
			role.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
			role.setUpdateDt(new Timestamp(new Date().getTime()));
			request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_UPD);
			returnUserRole.add(JsonUtil.transferToObject(idmRoleService.update(idmRole), UserRole.class));
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		return returnUserRole;
	}


	@PostMapping(value = IdmUrlConstants.DELETE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deleteRole(@RequestBody UserRole userRole, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(userRole)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_DEL);
		IdmRole idmRole = JsonUtil.transferToObject(userRole, IdmRole.class);
		return idmRoleService.delete(idmRole);
	}


	/**
	 * Search Role
	 * 
	 * @param userRole
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.SEARCH, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserRole> searchRole(
			@Valid @RequestBody UserRole userRole) throws IOException {

		if (BaseUtil.isObjNull(userRole)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		List<IdmRole> roleList = idmRoleService.search(userRole);

		if (Boolean.TRUE.equals(BaseUtil.isListNull(roleList))) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		try {
			return JsonUtil.transferToList(roleList, UserRole.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}
	
	
	
	/**
	 * Assign role to list of users
	 * 
	 * @param roleCode
	 * @param users
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "{roleCode}/users", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public AssignRole assignRoleToUsers(@PathVariable String roleCode, @Valid @RequestBody String[] users,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		List<UserProfile> userProfileList = new ArrayList<>();
		AssignRole rolestoUsers = new AssignRole();
		String[] usersLst = users;
		IdmRole tidmRole = null;
		try {
			tidmRole = idmRoleService.findByRoleCode(roleCode);
			if (tidmRole == null) {
				throw new IdmException(IdmErrorCodeEnum.E404IDM140);
			}
			Mapper mapper = new DozerBeanMapper();
			UserRole userRoleObj = mapper.map(tidmRole, UserRole.class);
			rolestoUsers.setUserRole(userRoleObj);

			if (StringUtils.isEmpty(usersLst)) {
				throw new IdmException(IdmErrorCodeEnum.E404IDM140);
			}

			for (String userId : usersLst) {
				// check userid exists or not.
				IdmProfile prfObj = idmProfileSvc.findByUserId(userId);
				if (BaseUtil.isObjNull(prfObj)) {
					throw new IdmException(IdmErrorCodeEnum.I404IDM151, new String[] { userId });
				}

				UserProfile prf = mapper.map(prfObj, UserProfile.class);
				userProfileList.add(prf);
				sb.append(userId).append(",");
				IdmRole r = new IdmRole();
				r.setRoleCode(roleCode);
				IdmProfile tup = new IdmProfile();
				tup.setUserId(userId);
			}
			rolestoUsers.setProfiles(userProfileList);
		} catch (Exception e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_USERS);
		return rolestoUsers;
	}


	// assign role to list of users
	@PostMapping(value = "{roleCode}/menus", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public AssignRole assignRoleToMenus(@PathVariable String roleCode, @Valid @RequestBody String[] menuLst,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("assignRoleToMenus roleCode: {}", roleCode);

		IdmRole tidmRole = null;
		AssignRole rolestoUsers = new AssignRole();
		List<UserMenu> userMenuList = new ArrayList<>();
		tidmRole = idmRoleService.findByRoleCode(roleCode);
		if (tidmRole == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		Mapper mapper = new DozerBeanMapper();
		UserRole userRoleObj = mapper.map(tidmRole, UserRole.class);
		rolestoUsers.setUserRole(userRoleObj);

		List<IdmRoleMenu> roleMenuLst = new ArrayList<>();
		for (String menuId : menuLst) {
			// check menu exists or not.
			IdmMenu menuObj = idmMenuSvc.findMenuByMenuCode(menuId);
			if (BaseUtil.isObjNull(menuObj)) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM155);
			}

			UserMenu userMenuObj = mapper.map(menuObj, UserMenu.class);
			userMenuList.add(userMenuObj);

			// check duplicate assignment Menu to role.
			List<IdmRoleMenu> roleMenuTempLst = idmRoleMenuSvc.findRoleMenuByRoleCodeAndMenuCode(roleCode, menuId);

			// Check if already assigned same Menu with the same role
			if (!BaseUtil.isListNull(roleMenuTempLst).booleanValue()) {
				continue;
			}
			IdmRoleMenu idmRoleMenu = new IdmRoleMenu();
			LOGGER.info("Role code: {} - Menu Code: {}", roleCode, menuId);
			IdmMenu idmMenu = new IdmMenu();
			idmMenu.setMenuCode(menuId);
			idmRoleMenu.setIdmMenu(idmMenu);

			IdmRole idmRole = new IdmRole();
			idmRole.setRoleCode(roleCode);
			idmRoleMenu.setIdmRole(idmRole);
			idmRoleMenu.setCreateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
			idmRoleMenu.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
			roleMenuLst.add(idmRoleMenu);

		}

		rolestoUsers.setMenuList(userMenuList);

		try {
			if (!BaseUtil.isListNull(roleMenuLst).booleanValue()) {
				idmRoleMenuSvc.bulkInsert(roleMenuLst);
			}
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_MENU);
		return rolestoUsers;

	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/menus", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<RoleMenu> getAllRoleMenu() throws IOException {
		List<IdmRoleMenu> tidmRoleLst = idmRoleMenuSvc.findAll();
		if (BaseUtil.isListNull(tidmRoleLst).booleanValue()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		return JsonUtil.transferToList(tidmRoleLst, RoleMenu.class);
	}

}