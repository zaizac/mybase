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
import com.idm.sdk.model.RoleMenu;
import com.idm.sdk.model.UserGroupRole;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.pagination.DataTableResults;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_ROLE_MENU)
public class RoleMenuController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMenuController.class);
	
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<RoleMenu> findAllUserType() {
		return getIdmService().findAllRoleMenu();
	}
	
	@PostMapping(value = "/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<RoleMenu> getApplicationInfoPaginated(RoleMenu roleMenu, HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<RoleMenu> tasks = null;
		try {
			if (!BaseUtil.isObjNull(roleMenu)) {
				tasks = getIdmService().searchRoleMenu(roleMenu, getPaginationRequest(request, true));
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
	public @ResponseBody RoleMenu createRoleMenu(@RequestBody RoleMenu roleMenu, HttpServletRequest request) {
		if (BaseUtil.isObjNull(roleMenu) && BaseUtil.isObjNull(roleMenu.getMenu()) && BaseUtil.isObjNull(roleMenu.getRole())) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Reason : " + new GsonBuilder().create().toJson(roleMenu));
		return getIdmService().createRoleMenu(roleMenu);
	}


	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody RoleMenu updateRoleMenu(@RequestBody RoleMenu roleMenu, HttpServletRequest request) {
		if (BaseUtil.isObjNull(roleMenu)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(roleMenu));
		return getIdmService().updateRoleMenu(roleMenu);
	}
	
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody boolean deleteRoleMenu(@RequestBody RoleMenu roleMenu, HttpServletRequest request) {
		if (BaseUtil.isObjNull(roleMenu)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(roleMenu));
		return getIdmService().deleteRoleMenu(roleMenu);
	}
	
	@GetMapping(value = "/roleMenu", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public RoleMenu findByRoleMenuId(@RequestParam(value = "id", required = true) Integer id) {
		return getIdmService().findRoleMenuById(id);
	}

}
