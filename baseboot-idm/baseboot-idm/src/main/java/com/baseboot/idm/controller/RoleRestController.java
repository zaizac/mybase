/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


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
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.model.IdmMenu;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.model.IdmRole;
import com.baseboot.idm.model.IdmRoleMenu;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.AssignRole;
import com.baseboot.idm.sdk.model.UserMenu;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.model.UserRole;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmMenuService;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.service.IdmRoleMenuService;
import com.baseboot.idm.service.IdmRoleService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.ROLES)
public class RoleRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleRestController.class);

	@Autowired
	@Qualifier(QualifierConstants.IDMROLE_SERVICE)
	private IdmRoleService idmRoleService;

	@Autowired
	private IdmProfileService idmProfileService;

	@Lazy
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
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmRole addRole(@Valid @RequestBody IdmRole tr, HttpServletRequest request, HttpServletResponse response) {
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

		IdmRole tidmRoleC = null;
		tr.setCreateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		tr.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		try {
			tidmRoleC = idmRoleService.create(tr);

		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_NEW);
		return tidmRoleC;

	}


	// Get role by role id
	@RequestMapping(value = "{roleCode}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmRole getRole(@PathVariable String roleCode) {

		IdmRole tidmRole = idmRoleService.findUserRoleByRoleCode(roleCode);
		if (tidmRole == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		return tidmRole;
	}


	// assign role to list of users
	@RequestMapping(value = "{roleCode}/users", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
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

			/*
			 * try { // idmRoleMemberService.bulkInsert(trmLst, null); }
			 * catch (Exception e) { LOGGER.error("Exception:", e); throw new
			 * IdmException(IdmErrorCodeEnum.I404IDM153); }
			 */

		} catch (Exception e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_USERS);
		return rolestoUsers;
	}


	// assign role to list of users
	@RequestMapping(value = "{roleCode}/menus", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
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
		for (@Valid
		String menuId : menuLst) {
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
			idmRoleMenu.setCreateId(BaseUtil.getStr(request.getAttribute("currUserId")));
			idmRoleMenu.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
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
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmRole> getAllRoles() {
		List<IdmRole> tidmRoleLst = idmRoleService.findAllRoles();
		if (BaseUtil.isListNull(tidmRoleLst)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		return tidmRoleLst;
	}


	@RequestMapping(value = "/menus", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
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
	@RequestMapping(value = "{roleCode}", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmRole updateRole(@PathVariable String roleCode, @Valid @RequestBody UserRole role,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_UPD);
		if (role == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmRole idmRole = dozerMapper.map(role, IdmRole.class);
		LOGGER.info("idmRole.getRoleCode(): {}", idmRole.getRoleCode());
		role.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
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