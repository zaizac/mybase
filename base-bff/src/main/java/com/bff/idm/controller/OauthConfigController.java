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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.exception.BeException;
import com.bff.core.AbstractController;
import com.bff.util.constants.PageConstants;
import com.google.gson.GsonBuilder;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.model.OauthClientDetails;
import com.util.BaseUtil;
import com.util.MediaType;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_OAUTH)
public class OauthConfigController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OauthConfigController.class);
	
	
	@GetMapping(value=IdmUrlConstants.OAUTH_CLIENT_DETAILS, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<OauthClientDetails> findAllOauthConfigDetails() {
		return getIdmService().findAllOauthClientDetails();
	}
	
	
	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_CREATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<OauthClientDetails> createOauthClientDetails(@RequestBody List<OauthClientDetails> ocds, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ocds)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Oauth Client Details : {}", new GsonBuilder().create().toJson(ocds));
		return getIdmService().createOauthClientDetails(ocds);
	}


	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_UPDATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<OauthClientDetails> updateRole(@RequestBody List<OauthClientDetails> ocds, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ocds)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Oauth Client Details : {}", new GsonBuilder().create().toJson(ocds));
		return getIdmService().updateOauthClientDetails(ocds);
	}
	
	
	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteRole(@RequestBody OauthClientDetails ocd, HttpServletRequest request) {
		if (BaseUtil.isObjNull(ocd)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Delete Oauth Client Details : {}", new GsonBuilder().create().toJson(ocd));
		return getIdmService().deleteOauthClientDetails(ocd);
	}


}
