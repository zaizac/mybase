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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.model.UserMenu;
import com.util.BaseUtil;
import com.util.MediaType;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_MENU)
public class MenuMaintainController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuMaintainController.class);

	ObjectMapper objMap = new ObjectMapper();


	@PostMapping(value = "/addMenu", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public UserMenu addMenu(@RequestBody UserMenu menu, HttpServletRequest request) {
		LOGGER.info("menuAdd: {}", objMap.valueToTree(menu));
		return getIdmService().createMenu(menu);
	}
	
	@PostMapping(value = "/updateMenu", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public UserMenu updateMenu(@RequestBody UserMenu menu, HttpServletRequest request) {
		LOGGER.info("updateMenu: {}", objMap.valueToTree(menu));
		return getIdmService().updateMenu(menu);
	}
	
	@PostMapping(value = "/searchMenu", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserMenu> searchMenuByCodeEtc(@RequestBody UserMenu menu, HttpServletRequest request) {
		
		List<UserMenu> idmMenuList = getIdmService().searchMenu(menu);
		return idmMenuList;
	}
	
	
	@GetMapping(value = "/menuCode", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserMenu findMenuByMenuCode(@RequestParam(value = "menuCode", required = true) String menuCode) {
		LOGGER.info("menuCode: {}", objMap.valueToTree(menuCode));
		return getIdmService().findMenuByMenuCode(menuCode);
	}

	@GetMapping(value = "/menuLevel", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserMenu> findByMenuLevel(@RequestParam(value = "menuLevel", required = true) Integer menuLevel) {
		LOGGER.info("menuLevels: {}", objMap.valueToTree(menuLevel));
		return getIdmService().findMenuByMenuLevel(menuLevel);
	}
	
	@GetMapping(value = "/menuLists", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserMenu> findAllMenu(){
		return getIdmService().findAllMenus();
	}
	
	@PostMapping(value = "/deleteMenu", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteMenu(@RequestBody UserMenu umDto, HttpServletRequest request) {
		LOGGER.info("menuDelete: {}", objMap.valueToTree(umDto));
		return getIdmService().deleteMenu(umDto);
	}

}
