package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
import com.idm.model.IdmMenu;
import com.idm.model.IdmPortalType;
import com.idm.model.IdmRole;
import com.idm.model.IdmRoleMenu;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.RoleMenu;
import com.idm.service.IdmRoleMenuService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


@RestController
@RequestMapping(IdmUrlConstants.ROLE_MENU)
public class RoleMenuRestController extends AbstractRestController {

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMenuRestController.class);


	/**
	 * Fetch ALL User Types
	 *
	 * @return List<IdmUserType>
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/all", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<RoleMenu> getAllListRoleMenu() {
		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findAll();

		if (BaseUtil.isListNull(roleMenuList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		try {
			return JsonUtil.transferToList(roleMenuList, RoleMenu.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
	}

	
	/**
	 * Fetch ALL User Types By Portal Type
	 *
	 * @return List<IdmUserType>
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.PORTALTYPE, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<RoleMenu> getAllListRoleMenuByPortalType(@RequestParam("portalTypeCode") String portalTypeCode) {
		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findByPortalTypeCode(portalTypeCode);

		if (Boolean.TRUE.equals(BaseUtil.isListNull(roleMenuList))) {
			return Collections.emptyList();
		}

		try {
			return JsonUtil.transferToList(roleMenuList, RoleMenu.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/roleCode/{roleCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<RoleMenu> getRoleMenuListByRoleCode(@PathVariable String roleCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (BaseUtil.isObjNull(roleCode)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findByIdmRoleRoleCode(roleCode);

		if (!BaseUtil.isListNull(roleMenuList)) {
			return JsonUtil.transferToList(roleMenuList, RoleMenu.class);
		}

		return new ArrayList<>();
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/menuCode/{menuCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<RoleMenu> getRoleMenuListByMenuCode(@PathVariable String menuCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (BaseUtil.isObjNull(menuCode)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findRoleMenuByMenuCode(menuCode);

		if (!BaseUtil.isListNull(roleMenuList)) {
			return JsonUtil.transferToList(roleMenuList, RoleMenu.class);
		}

		return new ArrayList<>();
	}


	@PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public RoleMenu addRoleMenu(@Valid @RequestBody RoleMenu rm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (BaseUtil.isObjNull(rm)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		String userId = BaseUtil.getStr(request.getAttribute("currUserId"));
		IdmRoleMenu idmMenu = JsonUtil.transferToObject(rm, IdmRoleMenu.class);
		idmMenu.setCreateId(userId);
		idmMenu.setCreateDt(new Timestamp(new Date().getTime()));
		idmMenu.setUpdateId(userId);
		idmMenu.setUpdateDt(new Timestamp(new Date().getTime()));
		idmMenu = idmRoleMenuService.create(idmMenu);

		return JsonUtil.transferToObject(idmMenu, RoleMenu.class);
	}


	@PostMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public RoleMenu updateRoleMenu(@Valid @RequestBody RoleMenu rm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (BaseUtil.isObjNull(rm)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
		String userId = BaseUtil.getStr(request.getAttribute("currUserId"));
		IdmRoleMenu idmMenu = idmRoleMenuService.find(rm.getRoleMenuId());
		if (!BaseUtil.isObjNull(idmMenu)) {
			idmMenu.setIdmMenu(JsonUtil.transferToObject(rm.getMenu(), IdmMenu.class));
			idmMenu.setIdmRole(JsonUtil.transferToObject(rm.getRole(), IdmRole.class));
			if (rm.getPortalType() != null) {
				idmMenu.setIdmPortalType(JsonUtil.transferToObject(rm.getPortalType(), IdmPortalType.class));
			}
			idmMenu.setUpdateId(userId);
			idmMenu.setUpdateDt(new Timestamp(new Date().getTime()));

			idmMenu = idmRoleMenuService.update(idmMenu);
		}
		return JsonUtil.transferToObject(idmMenu, RoleMenu.class);

	}


	@PostMapping(value = "/delete", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public boolean deleteRoleMenu(@Valid @RequestBody RoleMenu rm, HttpServletRequest request,
			HttpServletResponse response) {

		if (BaseUtil.isObjNull(rm)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		return idmRoleMenuService.delete(rm.getRoleMenuId());
	}


	@PostMapping(value = IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<RoleMenu> searchUserProfilePaginated(@Valid @RequestBody RoleMenu roleMenu,
			HttpServletRequest request) {
		DataTableRequest<RoleMenu> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<RoleMenu> all = idmRoleMenuService.searchByPagination(roleMenu, null);
		List<RoleMenu> filtered = idmRoleMenuService.searchByPagination(roleMenu, dataTableInRQ);
		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}


	/**
	 * Retrieve Role Menu by id
	 *
	 * @param RoleMenuDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/findRoleMenuId/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public RoleMenu findByRoleMenuId(@PathVariable Integer id) throws IOException {
		IdmRoleMenu idmRoleMenuId = idmRoleMenuService.findByRoleMenuId(id);
		if (idmRoleMenuId == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToObject(idmRoleMenuId, RoleMenu.class);
	}
}
