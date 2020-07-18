package com.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmTxnCodeConstants;
import com.idm.constants.Oauth2Constants;
import com.idm.core.AbstractRestController;
import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserLdap;
import com.idm.model.IdmFcm;
import com.idm.model.IdmFcmDevice;
import com.idm.model.IdmProfile;
import com.idm.model.IdmUserType;
import com.idm.model.IdmUserTypePortal;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.LoginDto;
import com.idm.sdk.model.Token;
import com.idm.service.IdmFcmDeviceService;
import com.idm.service.IdmFcmService;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmUserTypePortalService;
import com.idm.util.ClientOauth2;
import com.util.AuthCredentials;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.model.MessageResponse;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class AuthRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

	@Autowired
	ClientDetailsService clientDetailsService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	ConsumerTokenServices tokenServices;

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmUserTypePortalService idmUserTypePortalSvc;
	
	@Autowired
	private IdmFcmService idmFcmSvc;
	
	@Autowired
	private IdmFcmDeviceService idmFcmDeviceSvc;


	@PostMapping(value = IdmUrlConstants.LOGIN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public LoginDto login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
			HttpServletResponse response) {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials authCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		String clientId = authCredentials.getClient();

		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

		if (client == null) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
		}

		if (clientId == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM152, new String[] { clientId });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		
		loginDto.setClientId(clientId);
		loginDto.setClientSecret(client.getClientSecret());

		// Find user in IdmProfile
		IdmProfile idmProfile = idmProfileService.findByUserId(loginDto.getUserId());

		// If user not found in IdmProfile, throw error
		if (BaseUtil.isObjNull(idmProfile)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
					new String[] { loginDto.getUserId() });
			LOGGER.error("IdmException (IdmProfile): {}", idmEx.getMessage());
			throw idmEx;
		}

		LOGGER.info("PORTAL TYPE: {} - {}", loginDto.getUserType(), loginDto.getPortalType());

		// Check if System Type is available
		String systemType = getSystemHeader(request);
		if(!BaseUtil.isObjNull(systemType) && (!BaseUtil.isObjNull(idmProfile)
				&& !BaseUtil.isEqualsCaseIgnore(systemType, idmProfile.getSystemType()))) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
					new String[] { idmProfile.getUserId() });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		
		// Initialize to gain access to Lazy object
		Hibernate.initialize(idmProfile.getUserType());
		
		IdmUserType userType = idmProfile.getUserType();

		// Check User Type
		if (!BaseUtil.isObjNull(idmProfile) && (!BaseUtil.isObjNull(userType)
				&& !BaseUtil.isEqualsCaseIgnore("SYSA", userType.getUserTypeCode()))) {
			// && !BaseUtil.isEqualsCaseIgnore("ALL", loginDto.getUserType())
			LOGGER.info("LOGGED IN USER TYPE: {}", userType.getUserTypeCode());

			// Search Portals allowed by logged in user
			List<IdmUserTypePortal> userPortals = idmUserTypePortalSvc
					.searchByProperty(new IdmUserTypePortal(userType.getUserTypeCode(),
							BaseUtil.getStrUpperWithNull(loginDto.getPortalType())));
			LOGGER.info("LOGGED IN USER TYPE: {}", userPortals.size());
			if (BaseUtil.isListNullZero(userPortals)) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM157,
						new String[] { idmProfile.getUserId(), loginDto.getPortalType() });
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}

		}
		
		boolean bypassPword = userType.isBypassPword();
		
		boolean auth = bypassPword;
		
		if(!BaseUtil.isObjNull(loginDto.getPassword()) && !bypassPword) {
			// Search user in LDAP
			IdmUserLdap ldapEntryBean = null;
			try {
				ldapEntryBean = idmUserDao.searchEntry(loginDto.getUserId());
			} catch (Exception e) {
				throw new IdmException(IdmErrorCodeEnum.E500IDM903, new String[] { e.getMessage() });
			}
			
			// If user not found in LDAP, throw error
			if (ldapEntryBean == null) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM102,
						new String[] { loginDto.getUserId() });
				LOGGER.error("IdmException (LDAP): {}", idmEx.getMessage());
				throw idmEx;
			}
			
			// checking whether user status is inactive
			if (BaseConstants.USER_INACTIVE.equalsIgnoreCase(idmProfile.getStatus())) {
				throw new IdmException(IdmErrorCodeEnum.I409IDM119);
			} else if (BaseConstants.USER_PENDING_ACTIVATION.equalsIgnoreCase(idmProfile.getStatus())) {
				throw new IdmException(IdmErrorCodeEnum.I409IDM121);
			}
			
			ldapEntryBean = new IdmUserLdap();
			ldapEntryBean.setUserName(loginDto.getUserId());
			ldapEntryBean.setUserPassword(loginDto.getPassword());
			auth = idmUserDao.authenticate(ldapEntryBean);
		}

		if (!auth) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM103,
					new String[] { loginDto.getUserId() });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// Update Login Time
		try {
			IdmProfile idmProf = idmProfile;
			idmProf.setLastLogin(DateUtil.getSQLTimestamp());
			idmProfileService.update(idmProf);
		} catch (Exception e) {
			// DO NOTHING
			LOGGER.error("{}", e.getMessage());
		}
		
		// FCM Devices
		if(!BaseUtil.isObjNull(loginDto.getDevice())) {
			IdmFcm idmFcm = idmProfile.getFcm();
			if(BaseUtil.isObjNull(idmFcm)) {
				try {
					idmFcm = idmFcmSvc.findByUserId(loginDto.getUserId());
				} catch (Exception e) {
					LOGGER.error("{}", e.getMessage());
				}
			}
			
			// create idmFcm and fcmDevice
			if(BaseUtil.isObjNull(idmFcm)) {
				try {
					idmFcm = new IdmFcm();
					idmFcm.setUserId(loginDto.getUserId());
					idmFcm.setStatus(true);
					idmFcm.setCreateId(getCurrUserId(request));
					idmFcm.setCreateDt(DateUtil.getSQLTimestamp());
					idmFcm.setUpdateId(getCurrUserId(request));
					idmFcm.setUpdateDt(DateUtil.getSQLTimestamp());
					
					List<IdmFcmDevice> idmFcmDevices = new ArrayList<>();
					IdmFcmDevice idmFcmDevice = JsonUtil.transferToObject(loginDto.getDevice(), IdmFcmDevice.class);
					idmFcmDevice.setCreateId(getCurrUserId(request));
					idmFcmDevice.setCreateDt(DateUtil.getSQLTimestamp());
					idmFcmDevice.setUpdateId(getCurrUserId(request));
					idmFcmDevice.setUpdateDt(DateUtil.getSQLTimestamp());
					idmFcmDevice.setStatus(true);
					idmFcmDevice.setFcm(idmFcm);
					idmFcmDevices.add(idmFcmDevice);
					
					idmFcm.setFcmDevices(idmFcmDevices);
					idmFcmSvc.create(idmFcm);
				} catch (IOException e) {
					LOGGER.info("{}", e.getMessage());
				}
			} else {
				try {
					IdmFcmDevice newFcmDevice = JsonUtil.transferToObject(loginDto.getDevice(), IdmFcmDevice.class);
					newFcmDevice.setUpdateId(getCurrUserId(request));
					newFcmDevice.setUpdateDt(DateUtil.getSQLTimestamp());
					// Disable other devices
					if(loginDto.isForceDisableDevice()) {
						idmFcmDeviceSvc.updateStatusByUserId(loginDto.getUserId());
					}
					IdmFcmDevice idmFcmDevice = idmFcmDeviceSvc.findByFcmIdAndMachineId(idmFcm.getFcmId(), loginDto.getDevice().getMachineId());
					if(!BaseUtil.isObjNull(idmFcmDevice)) {
						newFcmDevice.setFcm(idmFcm);
						newFcmDevice.setDeviceId(idmFcmDevice.getDeviceId());
						newFcmDevice.setCreateId(idmFcmDevice.getCreateId());
						newFcmDevice.setCreateDt(idmFcmDevice.getCreateDt());
						newFcmDevice.setStatus(true);
						idmFcmDeviceSvc.update(newFcmDevice);
					} else {
						newFcmDevice.setFcm(idmFcm);
						newFcmDevice.setStatus(true);
						newFcmDevice.setCreateId(getCurrUserId(request));
						newFcmDevice.setCreateDt(DateUtil.getSQLTimestamp());
						idmFcmDeviceSvc.create(newFcmDevice);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// remove first the existing token
		Collection<OAuth2AccessToken> listTokens = tokenStore.findTokensByClientIdAndUserName(clientId,
				loginDto.getUserId());
		for (OAuth2AccessToken a : listTokens) {
			tokenServices.revokeToken(a.getValue());
		}

		// generated token and refresh token
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", loginDto.getClientId()));
		params.add(new BasicNameValuePair("client_secret", loginDto.getClientSecret()));
		params.add(new BasicNameValuePair("grant_type", Oauth2Constants.GRANT_TYPE_PWORD));
		params.add(new BasicNameValuePair("username", loginDto.getUserId()));
		
		if(!bypassPword) {
			params.add(new BasicNameValuePair("password", loginDto.getPassword()));
		}

		Token token = new ClientOauth2().generateAccessToken(params, request);
		LOGGER.info("GENERATED TOKEN: {}", JsonUtil.toJsonNode(token));

		loginDto.setStatus(idmProfile.getStatus());
		Hibernate.initialize(idmProfile.getUserRoleGroup());
		LOGGER.debug("getUserGroupCode------>{}", idmProfile.getUserRoleGroup().getUserGroupCode());
		loginDto.setUserRoleGroupCode(idmProfile.getUserRoleGroup().getUserGroupCode());
		loginDto.setGender(idmProfile.getGender());
		loginDto.setFirstName(idmProfile.getFirstName());
		loginDto.setLastName(idmProfile.getLastName());
		loginDto.setProfId(idmProfile.getProfId());
		if (!BaseUtil.isObjNull(userType)) {
//			Hibernate.initialize(idmProfile.getUserType());
			loginDto.setUserType(userType.getUserTypeCode());
		}
		loginDto.setCntry(idmProfile.getCntryCd());
		loginDto.setToken(token);

		response.setHeader(HttpHeaders.AUTHORIZATION,
				AuthCredentials.authorizationBasic(loginDto.getClientId(), token.getAccessToken()));
		response.setHeader("Content-Type", "application/json");
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGIN);
		request.setAttribute("currUserId", loginDto.getUserId());

		return loginDto;
	}


	@PostMapping(value = IdmUrlConstants.LOGOUT, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public MessageResponse logout(@RequestBody(required = false) LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_AUTH_LOGOUT);
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		AuthCredentials basicAuthCredentials = AuthCredentials.createCredentialsFromHeader(authorizationHeader,
				Oauth2Constants.TOKEN_TYPE_BASIC);

		LOGGER.info("logout: {}", authorizationHeader);
		
		// Disable device
		if(!BaseUtil.isObjNull(loginDto) && !BaseUtil.isObjNull(loginDto.getDevice())) {
			try {
				IdmFcm idmFcm = idmFcmSvc.findByUserId(loginDto.getUserId());
				if(!BaseUtil.isObjNull(idmFcm)) {
					IdmFcmDevice idmFcmDevice = idmFcmDeviceSvc.findByFcmIdAndMachineId(idmFcm.getFcmId(), loginDto.getDevice().getMachineId());
					if(!BaseUtil.isObjNull(idmFcmDevice)) {
						IdmFcmDevice newFcmDevice = JsonUtil.transferToObject(loginDto.getDevice(), IdmFcmDevice.class);
						newFcmDevice.setDeviceId(idmFcmDevice.getDeviceId());
						newFcmDevice.setFcm(idmFcmDevice.getFcm());
						newFcmDevice.setStatus(false);
						newFcmDevice.setUpdateId(getCurrUserId(request));
						newFcmDevice.setUpdateDt(DateUtil.getSQLTimestamp());
						idmFcmDeviceSvc.update(newFcmDevice);
					}
				}
			} catch (Exception e) {
				LOGGER.error("{}", e.getMessage());
			}
		}

		String token = basicAuthCredentials.getToken();

		if (tokenServices.revokeToken(token)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
			ctxLogOut.logout(request, response, auth);
			return new MessageResponse(DateUtil.getSQLTimestamp(), 200, IdmErrorCodeEnum.I200C000IDM.name(),
					IdmErrorCodeEnum.I200C000IDM.getMessage(), request.getRequestURI());
		} else {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I500IDM105, new String[] { token });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
	}


	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}


	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

}
