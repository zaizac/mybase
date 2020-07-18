/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.model.IdmMenu;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.model.IdmRoleMenu;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.MenuDto;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmMenuService;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.service.IdmRoleMenuService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@RestController
@RequestMapping(IdmUrlConstrants.MENUS)
public class MenuRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuRestController.class);

	@Autowired
	protected IdmProfileService idmProfileService;

	@Lazy
	@Autowired
	private IdmMenuService idmMenuService;

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;


	/**
	 * Find All Menus
	 *
	 * @return List<IdmMenu>
	 */
	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmMenu> getAllMenus() {
		List<IdmMenu> idmMenuLst = idmMenuService.findAllMenus();
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		return idmMenuLst;
	}


	/**
	 * Find Menu by parentCode
	 *
	 * @param parentCode
	 * @return
	 */
	@RequestMapping(value = "{parentCode}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmMenu> getMenuByParent(@PathVariable String parentCode) {
		List<IdmMenu> idmMenuLst = idmMenuService.findMenuByParentCode(parentCode);
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		return idmMenuLst;
	}


	/**
	 * Fetch menu list by user role
	 *
	 * @param roles
	 * @return List<IdmMenu>
	 */
	@RequestMapping(value = IdmUrlConstrants.USER_MENUS_ROLES, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmMenu> getMenuByRole(@PathVariable String roles) {
		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findUserRoleByRoleCode(roles);
		if (BaseUtil.isListNullZero(roleMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		List<IdmMenu> menuList = new ArrayList<>();

		for (IdmRoleMenu idmMenu : roleMenuList) {
			menuList.add(idmMenu.getIdmMenu());
		}
		return menuList;
	}


	/**
	 * Find Menus by User Id
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.USER_USER_ID, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmMenu> findMenuByUserId(@RequestParam(value = "id", required = true) String userId) {
		List<IdmMenu> menuLst = new ArrayList<>();
		String roleRoleGroupCode = BaseConstants.EMPTY_STRING;
		IdmProfile uProfile = idmProfileService.findProfileByUserId(userId);
		if (BaseUtil.isObjNull(uProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM130);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		} else {
			roleRoleGroupCode = BaseUtil.getStr(uProfile.getUserRoleGroupCode());
		}

		LOGGER.debug("roleGroupCode: {}", roleRoleGroupCode);
		menuLst = idmRoleMenuService.findRoleMenuByGroupRole(roleRoleGroupCode);
		return menuLst;
	}


	/**
	 * Fetch menu list by menu level
	 *
	 * @param menuLevel
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.MENU_LEVEL_NUM, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmMenu> findMenuByMenuLevel(@PathVariable Integer menuLevel) {
		List<IdmMenu> idmMenuList = idmMenuService.findMenuByMenuLevel(menuLevel);
		if (BaseUtil.isListNull(idmMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		return idmMenuList;
	}


	/**
	 * Create Menu
	 *
	 * @param menuDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.ADD_MENU, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public MenuDto addMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) {
		if (BaseUtil.isObjNull(menuDto)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = dozerMapper.map(menuDto, IdmMenu.class);
		idmMenu.setCreateId(menuDto.getCrtUsrId());
		idmMenu.setUpdateId(menuDto.getCrtUsrId());
		IdmMenu menuLevel = idmMenuService.create(idmMenu);
		MenuDto menuDto2 = dozerMapper.map(menuLevel, MenuDto.class);
		return menuDto2;
	}


	/**
	 * Update Menu
	 *
	 * @param menuDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.UPDATE_MENU, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public MenuDto updateMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) {
		if (BaseUtil.isObjNull(menuDto)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = dozerMapper.map(menuDto, IdmMenu.class);
		idmMenu.setUpdateId(menuDto.getCrtUsrId());
		idmMenu.setUpdateDt(new Timestamp(new Date().getTime()));
		IdmMenu menuLevel = idmMenuService.update(idmMenu);
		MenuDto menuDto2 = dozerMapper.map(menuLevel, MenuDto.class);
		return menuDto2;
	}


	/**
	 * Fetch menu by code
	 *
	 * @param menuCode
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.FIND_MENU_BY_MENU_CODE, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public MenuDto findMenuByMenuCode(@PathVariable String menuCode) {
		IdmMenu idmMenu = idmMenuService.findMenuByMenuCode(menuCode);
		if (idmMenu == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		return dozerMapper.map(idmMenu, MenuDto.class);
	}

}