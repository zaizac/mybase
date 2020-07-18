/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmMenu;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.model.IdmRoleMenu;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.MenuDto;
import com.bstsb.idm.service.IdmMenuService;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.IdmRoleMenuService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.MediaType;
import com.bstsb.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.MENUS)
public class MenuRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuRestController.class);

	@Autowired
	private IdmMenuService idmMenuService;

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;

	@Autowired
	private IdmProfileService idmProfileService;


	/**
	 * Find All Menus
	 *
	 * @return List<IdmMenu>
	 */
	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmMenu> getAllMenus() {
		List<IdmMenu> idmMenuLst = idmMenuService.findAllMenus();
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
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
	@GetMapping(value = "{parentCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmMenu> getMenuByParent(@PathVariable String parentCode) {
		List<IdmMenu> idmMenuLst = idmMenuService.findMenuByParentCode(parentCode);
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
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
	@GetMapping(value = IdmUrlConstants.USER_MENUS_ROLES, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmMenu> getMenuByRole(@PathVariable String roles) {
		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findUserRoleByRoleCode(roles);
		if (BaseUtil.isListNullZero(roleMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
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
	@GetMapping(value = IdmUrlConstants.USER_USER_ID, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmMenu> findMenuByUserId(@RequestParam(value = "id", required = true) String userId) {
		String roleRoleGroupCode = "";
		IdmProfile uProfile = idmProfileService.findProfileByUserId(userId);
		if (BaseUtil.isObjNull(uProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM130);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		} else {
			roleRoleGroupCode = BaseUtil.getStr(uProfile.getUserRoleGroupCode());
		}

		LOGGER.debug("roleGroupCode: {}", roleRoleGroupCode);
		return idmRoleMenuService.findRoleMenuByGroupRole(roleRoleGroupCode);
	}


	/**
	 * Fetch menu list by menu level
	 *
	 * @param menuLevel
	 * @return
	 */
	@GetMapping(value = IdmUrlConstants.MENU_LEVEL_NUM, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmMenu> findMenuByMenuLevel(@PathVariable Integer menuLevel) {
		List<IdmMenu> idmMenuList = idmMenuService.findMenuByMenuLevel(menuLevel);
		if (BaseUtil.isListNull(idmMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
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
	@PostMapping(value = IdmUrlConstants.ADD_MENU, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public MenuDto addMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) {
		if (BaseUtil.isObjNull(menuDto)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = dozerMapper.map(menuDto, IdmMenu.class);
		idmMenu.setCreateId(menuDto.getCrtUsrId());
		idmMenu.setUpdateId(menuDto.getCrtUsrId());
		IdmMenu menuLevel = idmMenuService.create(idmMenu);
		return dozerMapper.map(menuLevel, MenuDto.class);
	}


	/**
	 * Update Menu
	 *
	 * @param menuDto
	 * @param request
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.UPDATE_MENU, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public MenuDto updateMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) {
		if (BaseUtil.isObjNull(menuDto)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = dozerMapper.map(menuDto, IdmMenu.class);
		idmMenu.setUpdateId(menuDto.getCrtUsrId());
		idmMenu.setUpdateDt(new Timestamp(new Date().getTime()));
		IdmMenu menuLevel = idmMenuService.update(idmMenu);
		return dozerMapper.map(menuLevel, MenuDto.class);
	}


	/**
	 * Fetch menu by code
	 *
	 * @param menuCode
	 * @return
	 */
	@GetMapping(value = IdmUrlConstants.FIND_MENU_BY_MENU_CODE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public MenuDto findMenuByMenuCode(@PathVariable String menuCode) {
		IdmMenu idmMenu = idmMenuService.findMenuByMenuCode(menuCode);
		if (idmMenu == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return dozerMapper.map(idmMenu, MenuDto.class);
	}

}