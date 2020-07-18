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

import com.be.sdk.exception.BeException;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.google.gson.GsonBuilder;
import com.idm.sdk.model.UserType;
import com.util.BaseUtil;
import com.util.MediaType;
import com.util.pagination.DataTableResults;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_USR_TYPE)

public class UserTypeController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeController.class);


	@GetMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserType> findAllUserType() {
		return getIdmService().findAllUserTypes();
	}


	@PostMapping(value = "/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<UserType> getApplicationInfoPaginated(UserType userType, HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<UserType> tasks = null;
		try {
			if (!BaseUtil.isObjNull(userType)) {
				tasks = getIdmService().searchUserTypes(userType, getPaginationRequest(request, true));
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
	public @ResponseBody UserType createUserType(@RequestBody UserType userType, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userType)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Reason : " + new GsonBuilder().create().toJson(userType));
		return getIdmService().createUserType(userType);
	}


	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserType updateUserType(@RequestBody UserType userType, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userType)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(userType));
		return getIdmService().updateUserType(userType);
	}
	
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody boolean deleteUserType(@RequestBody UserType userType, HttpServletRequest request) {
		if (BaseUtil.isObjNull(userType)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(userType));
		return getIdmService().deleteUserType(userType);
	}


	@GetMapping(value = "/findByUserTypeCd", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody UserType searchByUserTypeCode(@RequestParam(value = "userTypeCode") String userTypeCode,
			HttpServletRequest request) {
		LOGGER.info("Reference .... {}", userTypeCode);
		if (BaseUtil.isStringNull(userTypeCode)) {
			throw new BeException("Invalid portal type - Document DocDesc is empty.");
		}
		return getIdmService().getUserTypeCode(userTypeCode);
	}

}
