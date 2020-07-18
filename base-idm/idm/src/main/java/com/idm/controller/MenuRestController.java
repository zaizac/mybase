package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmMenu;
import com.idm.model.IdmProfile;
import com.idm.model.IdmRoleMenu;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserMenu;
import com.idm.service.IdmMenuService;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmRoleMenuService;
import com.util.BaseUtil;
import com.util.JsonUtil;
//import com.util.MediaType;
import org.springframework.http.MediaType;
import com.util.constants.BaseConstants;


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
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.MENU_LIST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> getAllMenus() throws IOException {
		List<IdmMenu> idmMenuLst = idmMenuService.findAllMenus();
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		return JsonUtil.transferToList(idmMenuLst, UserMenu.class);
	}


	/**
	 * Find Menu by parentCode
	 *
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "{parentCode}", consumes = { MediaType.APPLICATION_JSON_VALUE}, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> getMenuByParent(@PathVariable String parentCode) throws IOException {
		List<IdmMenu> idmMenuLst = idmMenuService.findByMenuParentCode(parentCode);
		if (BaseUtil.isListNull(idmMenuLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		return JsonUtil.transferToList(idmMenuLst, UserMenu.class);
	}


	/**
	 * TODO: CHECK IF THIS SERVICE IS BEING USE
	 * 
	 * Fetch menu list by user role
	 *
	 * @param roles
	 * @return List<IdmMenu>
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.USER_MENUS_ROLES, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> getMenuByRole(@RequestParam String roleCode) throws IOException {
		List<IdmRoleMenu> roleMenuList = idmRoleMenuService.findByIdmRoleRoleCode(roleCode);
		if (BaseUtil.isListNullZero(roleMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		List<IdmMenu> menuList = new ArrayList<>();

		for (IdmRoleMenu idmMenu : roleMenuList) {
			menuList.add(idmMenu.getIdmMenu());
		}
		return JsonUtil.transferToList(menuList, UserMenu.class);
	}


	/**
	 * Find Menus by User Id
	 *
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.USER_USER_ID, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> findMenuByUserId(@RequestParam(value = "id", required = true) String userId)
			throws IOException {
		String roleRoleGroupCode = "";
		IdmProfile uProfile = idmProfileService.findByUserId(userId);
		if (BaseUtil.isObjNull(uProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM130);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		} else {
			Hibernate.initialize(uProfile.getUserRoleGroup());
			roleRoleGroupCode = BaseUtil.getStr(uProfile.getUserRoleGroup().getUserGroupCode());
		}

		LOGGER.debug("roleGroupCode: {}", roleRoleGroupCode);
		return idmRoleMenuService.findRoleMenuByGroupRole(roleRoleGroupCode);
	}


	/**
	 * Fetch menu list by menu level
	 *
	 * @param menuLevel
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.MENU_LEVEL_NUM, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> findMenuByMenuLevel(@PathVariable Integer menuLevel) throws IOException {
		List<IdmMenu> idmMenuList = idmMenuService.findMenuByMenuLevel(menuLevel);
		if (BaseUtil.isListNull(idmMenuList)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToList(idmMenuList, UserMenu.class);
	}


	/**
	 * Create Menu
	 *
	 * @param userMenu
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.ADD_MENU, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public UserMenu addMenu(@Valid @RequestBody UserMenu userMenu, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(userMenu)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = JsonUtil.transferToObject(userMenu, IdmMenu.class);
		idmMenu.setCreateId(userMenu.getCreateId());
		idmMenu.setUpdateId(userMenu.getCreateId());
		// retrieve max sequence
		//userMenu.getMenuSequence() <= 0 then query 
		Integer seqNO = idmMenuService.findByMaxMenuSequence(userMenu.getMenuParentCode());
		if(seqNO == null) {
			idmMenu.setMenuSequence(1);
		}else {
			idmMenu.setMenuSequence(seqNO);
		}
	
		IdmMenu menuLevel = idmMenuService.create(idmMenu);
		return JsonUtil.transferToObject(menuLevel, UserMenu.class);
	}


	/**
	 * Update Menu
	 *
	 * @param userMenu
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.UPDATE_MENU, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public UserMenu updateMenu(@Valid @RequestBody UserMenu userMenu, HttpServletRequest request) throws IOException {
		if (BaseUtil.isObjNull(userMenu)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		IdmMenu idmMenu = JsonUtil.transferToObject(userMenu, IdmMenu.class);
		idmMenu.setUpdateId(userMenu.getCreateId());
		idmMenu.setUpdateDt(new Timestamp(new Date().getTime()));
		IdmMenu menuLevel = idmMenuService.update(idmMenu);
		return JsonUtil.transferToObject(menuLevel, UserMenu.class);
	}


	/**
	 * Fetch menu by code
	 *
	 * @param menuCode
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.FIND_MENU_BY_MENU_CODE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public UserMenu findMenuByMenuCode(@PathVariable String menuCode) throws IOException {
		IdmMenu idmMenu = idmMenuService.findMenuByMenuCode(menuCode);
		if (idmMenu == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		return JsonUtil.transferToObject(idmMenu, UserMenu.class);
	}


	/**
	 * Delete User Group
	 *
	 * @param userGroupDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.DELETE_MENU, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Boolean deleteMenu(@RequestBody UserMenu umDto, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(umDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmMenu idmMenu = JsonUtil.transferToObject(umDto, IdmMenu.class);
		return idmMenuService.delete(idmMenu);
	}
	
	/**
	 * search menu by menucode, desc, level and parent code
	 * 
	 * @param idmMenuDto
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = IdmUrlConstants.SEARCH_MENU, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserMenu> searchMenu(@RequestBody UserMenu userMenu) throws IOException {
		
		if (BaseUtil.isObjNull(userMenu)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E400IDM913);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		List<IdmMenu> idmMenu = idmMenuService.searchIdmMenu(userMenu);
		return JsonUtil.transferToList(idmMenu, UserMenu.class);
	}
}