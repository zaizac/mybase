package com.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmTxnCodeConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmOauthClientDetails;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.OauthClientDetails;
import com.idm.service.IdmOauthClientDetailsService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


/**
 * @author Hafidz Malik
 * @since Dec 6, 2019
 */
@RestController
@RequestMapping(IdmUrlConstants.OAUTH)
public class OauthRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OauthRestController.class);

	@Autowired
	private IdmOauthClientDetailsService idmOauthClientDetailsService;

	/**
	 * Find All Oauth Client Details
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<OauthClientDetails> getAllOauthClientDetails() {
		List<IdmOauthClientDetails> ocdLst = idmOauthClientDetailsService.findAll();
		if (BaseUtil.isListNull(ocdLst).booleanValue()) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

		try {
			return JsonUtil.transferToList(ocdLst, OauthClientDetails.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM124);
		}
	}


	/**
	 * Create Oauth Client Details
	 *
	 * @param ocds
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List <OauthClientDetails> addOauthClientDetails(@Valid @RequestBody List<OauthClientDetails> ocds, HttpServletRequest request,
			HttpServletResponse response) {
		if (ocds == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		
		List<OauthClientDetails> returnOauthClientDetails = new ArrayList<>();
		for (OauthClientDetails ocd: ocds) {
			IdmOauthClientDetails ocdObj = idmOauthClientDetailsService.findByClientId(ocd.getClientId().trim());
			if (ocdObj != null) {
				throw new IdmException(IdmErrorCodeEnum.I409IDM136, new String[] { ocd.getClientId() });
			}
	
			try {
				IdmOauthClientDetails idmOCD = JsonUtil.transferToObject(ocd, IdmOauthClientDetails.class);
				idmOCD = idmOauthClientDetailsService.create(idmOCD);
				request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_OAUTH_CLIENT_DETAILS_NEW);
				returnOauthClientDetails.add(JsonUtil.transferToObject(idmOCD, OauthClientDetails.class));
			} catch (Exception e) {
				LOGGER.error("Exception:", e);
				throw new IdmException(IdmErrorCodeEnum.I404IDM153);
			}
		}
		return returnOauthClientDetails;
	}


	/**
	 * Update Oauth Client Details
	 *
	 * @param ocds
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<OauthClientDetails> updateOauthClientDetails(@Valid @RequestBody List<OauthClientDetails> ocds, HttpServletRequest request,
			HttpServletResponse response) {
		if (ocds == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		
		List<OauthClientDetails> returnOauthClientDetails = new ArrayList<>();
		for (OauthClientDetails ocd: ocds) {
			try {
				IdmOauthClientDetails idmOCD = JsonUtil.transferToObject(ocd, IdmOauthClientDetails.class);
				request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_OAUTH_CLIENT_DETAILS_UPD);
				returnOauthClientDetails.add(JsonUtil.transferToObject(idmOauthClientDetailsService.update(idmOCD), OauthClientDetails.class));
			} catch (Exception e) {
				LOGGER.error("Exception:", e);
				throw new IdmException(IdmErrorCodeEnum.I404IDM159);
			}
		}
		return returnOauthClientDetails;
	}


	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS_DELETE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody Boolean deleteOauthClientDetails(@RequestBody OauthClientDetails ocd, HttpServletRequest request)
			throws IOException {
		if (BaseUtil.isObjNull(ocd)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_OAUTH_CLIENT_DETAILS_DEL);
		IdmOauthClientDetails idmOCD = JsonUtil.transferToObject(ocd, IdmOauthClientDetails.class);
		return idmOauthClientDetailsService.delete(idmOCD);
	}
	
	@PostMapping(value = IdmUrlConstants.OAUTH_CLIENT_DETAILS + IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<OauthClientDetails> searchUserProfilePaginated(@Valid @RequestBody OauthClientDetails ocd,
			@Valid @RequestParam(value = "isEmail", defaultValue = "false") boolean isEmail,
			HttpServletRequest request) {
		DataTableRequest<OauthClientDetails> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<OauthClientDetails> all = idmOauthClientDetailsService.searchByPagination(ocd, null);
		List<OauthClientDetails> filtered = idmOauthClientDetailsService.searchByPagination(ocd, dataTableInRQ);

		LOGGER.info("ALL DATA: {}", all);
		LOGGER.info("ALL filtered DATA : {}", filtered);

		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}

}