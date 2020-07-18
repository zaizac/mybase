/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmMenu;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.model.IdmRole;
import com.bstsb.idm.model.IdmRoleMenu;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.AssignRole;
import com.bstsb.idm.sdk.model.UserMenu;
import com.bstsb.idm.sdk.model.UserProfile;
import com.bstsb.idm.sdk.model.UserRole;
import com.bstsb.idm.service.IdmMenuService;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.IdmRoleMenuService;
import com.bstsb.idm.service.IdmRoleService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.MediaType;


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
	@Qualifier(QualifierConstants.IDM_ROLE_SERVICE)
	private IdmRoleService idmRoleService;

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmMenuService idmMenuService;

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;


	/**
	 * Create Role
	 *
	 * @param tr
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmRole addRole(@Valid @RequestBody UserRole tr, HttpServletRequest request, HttpServletResponse response) {
		if (tr == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		LOGGER.debug("tr.getRoleCode(): {}", tr.getRoleCode());
		LOGGER.debug("tr.getRoleDesc(): {}", tr.getRoleDesc());
		if (!StringUtils.hasText(tr.getRoleCode()) || !StringUtils.hasText(tr.getRoleDesc())) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.hasText(tr.getRoleCode())) {
				sb.append("roleCode,");
			}
			if (!StringUtils.hasText(tr.getRoleDesc())) {
				sb.append("roleDesc");
			}

			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { sb.toString(), "Role" });
		}

		IdmRole roleObj = idmRoleService.findUserRoleByRoleCode(tr.getRoleCode().trim());
		if (roleObj != null) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM136, new String[] { tr.getRoleCode() });
		}

		IdmRole idmRole = dozerMapper.map(tr, IdmRole.class);
		idmRole.setCreateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
		idmRole.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));

		try {
			idmRole = idmRoleService.create(idmRole);
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_NEW);
		return idmRole;

	}


	// Get role by role id
	@GetMapping(value = "{roleCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmRole getRole(@PathVariable String roleCode) {

		IdmRole tidmRole = idmRoleService.findUserRoleByRoleCode(roleCode);
		if (tidmRole == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		return tidmRole;
	}


	// assign role to list of users
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
			tidmRole = idmRoleService.findUserRoleByRoleCode(roleCode);
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
				IdmProfile prfObj = idmProfileService.findProfileByUserId(userId);
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
		tidmRole = idmRoleService.findUserRoleByRoleCode(roleCode);
		if (tidmRole == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		Mapper mapper = new DozerBeanMapper();
		UserRole userRoleObj = mapper.map(tidmRole, UserRole.class);
		rolestoUsers.setUserRole(userRoleObj);

		List<IdmRoleMenu> roleMenuLst = new ArrayList<>();
		for (String menuId : menuLst) {
			// check menu exists or not.
			IdmMenu menuObj = idmMenuService.findMenuByMenuCode(menuId);
			if (BaseUtil.isObjNull(menuObj)) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM155);
			}

			UserMenu userMenuObj = mapper.map(menuObj, UserMenu.class);
			userMenuList.add(userMenuObj);

			// check duplicate assignment Menu to role.
			List<IdmRoleMenu> roleMenuTempLst = idmRoleMenuService.findRoleMenuByRoleCodeAndMenuCode(roleCode,
					menuId);

			// Check if already assigned same Menu with the same role
			if (!BaseUtil.isListNull(roleMenuTempLst)) {
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
			if (!BaseUtil.isListNull(roleMenuLst)) {
				idmRoleMenuService.bulkInsert(roleMenuLst);
			}
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_MENU);
		return rolestoUsers;

	}


	// find all roles
	@GetMapping(value = "", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmRole> getAllRoles() {
		List<IdmRole> tidmRoleLst = idmRoleService.findAllRoles();
		if (BaseUtil.isListNull(tidmRoleLst)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		return tidmRoleLst;
	}


	@GetMapping(value = "/menus", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmRoleMenu> getAllRoleMenu() {
		List<IdmRoleMenu> tidmRoleLst = idmRoleMenuService.primaryDao().findAll();
		if (BaseUtil.isListNull(tidmRoleLst)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		return tidmRoleLst;
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
	@PostMapping(value = "{roleCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmRole updateRole(@PathVariable String roleCode, @Valid @RequestBody UserRole role,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_UPD);
		if (role == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmRole idmRole = dozerMapper.map(role, IdmRole.class);
		LOGGER.info("idmRole.getRoleCode(): {}", idmRole.getRoleCode());
		role.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USER_ID)));
		role.setUpdateDt(new Timestamp(new Date().getTime()));

		try {
			idmRoleService.update(idmRole);
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}
		return idmRole;
	}

}