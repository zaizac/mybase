/**
 *
 */
package com.bstsb.idm.controller;


import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmSocial;
import com.bstsb.idm.model.IdmUserConnection;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.SocialUserConnectionDto;
import com.bstsb.idm.sdk.model.SocialUserDto;
import com.bstsb.idm.service.IdmSocialService;
import com.bstsb.idm.service.IdmUserConnectionService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.MediaType;


/**
 * @author mary.jane
 *
 */
@RestController
public class SocialRestController extends AbstractRestController {

	@Autowired
	private IdmSocialService idmSocialSvc;

	@Autowired
	private IdmUserConnectionService idmUserConnSvc;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;


	/*********************************
	 * IDM_SOCIAL
	 *******************************************/

	@PostMapping(value = IdmUrlConstants.ADD_SOCIAL_USER, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmSocial addUserSocial(@Valid @RequestBody SocialUserDto dto) {
		IdmSocial tp = dozerMapper.map(dto, IdmSocial.class);
		tp = idmSocialSvc.create(tp);
		return tp;
	}


	@GetMapping(value = IdmUrlConstants.FIND_SOCIAL_USER, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmSocial findSocialUserByIds(@RequestParam String userId, @RequestParam String provider,
			@RequestParam String providerUserId) {
		return idmSocialSvc.findSocialUserByIds(userId, provider, providerUserId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_SOCIAL_USER_BY_PROVID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmSocial findSocialUserByProviderId(@RequestParam String provider, @RequestParam String providerUserId) {
		return idmSocialSvc.findSocialUserByProviderId(provider, providerUserId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_SOCIAL_USER_BY_USERID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmSocial findSocialUserByUserId(@RequestParam String userId) {
		return idmSocialSvc.findSocialUserByUserId(userId);
	}


	/*********************************
	 * IDM_USER_CONNECTION
	 *******************************************/

	@GetMapping(value = IdmUrlConstants.FIND_USERID_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<String> findUserIdFromUserConn(@RequestParam String providerId, @RequestParam String providerUserId) {
		return idmUserConnSvc.findUserByProviderIdAndProviderUserId(providerId, providerUserId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_USERID_USERS_CONN, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<String> findUserIdsFromUserConn(@RequestParam String providerId,
			@RequestParam Set<String> providerUserIds) {
		return idmUserConnSvc.findUsersByProviderIdAndProviderUserId(providerId, providerUserIds);
	}


	@GetMapping(value = IdmUrlConstants.FIND_ALL_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<String> findAllUserConnections(@RequestParam String providerId,
			@RequestParam Set<String> providerUserIds) {
		return idmUserConnSvc.findUserByProviderIdAndProviderUserId(providerId, providerUserIds);
	}


	@PostMapping(value = IdmUrlConstants.CREATE_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmUserConnection createUserConnection(@Valid @RequestBody SocialUserConnectionDto dto) {
		IdmUserConnection idc = dozerMapper.map(dto, IdmUserConnection.class);
		return idmUserConnSvc.createUserConnection(idc);
	}


	@PutMapping(value = IdmUrlConstants.UPDATE_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmUserConnection updateUserConnection(@Valid @RequestBody SocialUserConnectionDto dto) {
		IdmUserConnection idc = dozerMapper.map(dto, IdmUserConnection.class);
		if(BaseUtil.isObjNull(idc.getAccessToken())) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM114);
		}
		return idmUserConnSvc.updateUserConnection(idc);
	}


	@DeleteMapping(value = IdmUrlConstants.DELETE_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmUserConnection removeConnections(@RequestParam String userId, @RequestParam String providerId) {
		return idmUserConnSvc.removeConnections(userId, providerId);
	}


	@DeleteMapping(value = IdmUrlConstants.DELETE_USER_CONN_ALL_KEYS, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmUserConnection removeConnection(@RequestParam String userId, @RequestParam String providerId,
			@RequestParam String providerUserId) {
		return idmUserConnSvc.removeConnection(userId, providerId, providerUserId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_PRIMARY_USER_CONN, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmUserConnection findPrimaryUserConnection(@RequestParam String userId, @RequestParam String providerId) {
		List<IdmUserConnection> userConns = idmUserConnSvc.findByUserIdAndProviderId(userId, providerId);

		IdmUserConnection userConn = new IdmUserConnection();
		// return the highest rank
		for (IdmUserConnection userConnTemp : userConns) {
			if (userConn.getRank() == null || userConn.getRank() < userConnTemp.getRank()) {
				userConn = userConnTemp;
			}
		}
		return userConn;
	}


	@GetMapping(value = IdmUrlConstants.FIND_ALL_USER_CONN_USERID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmUserConnection> findUserConnectionsByUserId(@RequestParam String userId) {
		return idmUserConnSvc.findUserConnectionsByUserId(userId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_ALL_USER_CONN_USERID_PROVID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmUserConnection> findUserConnectionsByUserId(@RequestParam String userId,
			@RequestParam String providerId) {
		return idmUserConnSvc.findByUserIdAndProviderId(userId, providerId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_USER_CONN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmUserConnection> findUserConByUserIdProviderIdProviderUserId(@RequestParam String userId,
			@RequestParam String providerId, @RequestParam String providerUserId) {
		return idmUserConnSvc.findUserConByUserIdProviderIdProviderUserId(userId, providerId, providerUserId);
	}


	@GetMapping(value = IdmUrlConstants.FIND_CONNS_TO_USERS, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmUserConnection> findConnectionsToUsers(@RequestParam String userId, @RequestParam String providerId,
			@RequestParam Set<String> providerUserIds) {
		return idmUserConnSvc.findConnectionsToUsers(userId, providerId, providerUserIds);
	}

}
