package com.bff.idm.controller;


import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.model.Document;
import com.bff.config.audit.AuditActionPolicy;
import com.bff.core.AbstractController;
import com.bff.idm.form.Logout;
import com.bff.idm.form.User;
import com.bff.util.WebUtil;
import com.bff.util.constants.MessageConstants;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.LoginDto;
import com.idm.sdk.model.Token;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;


/**
 * @author mary.jane
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


	@PostMapping(value = PageConstants.PAGE_LOGIN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public User authenticate(@RequestBody LoginDto auth, HttpServletRequest request) {
		LOGGER.info("Performing custom authentication");
		LOGGER.info("Username: {} - Password: {} - Portal Type: {}", auth.getUserId(), auth.getPassword(),
				auth.getPortalType());

		String userId = auth.getUserId();
		String pword = auth.getPassword();

		LoginDto login = new LoginDto();
		login.setUserId(userId);
		login.setPassword(pword);
		login.setPortalType(auth.getPortalType());
		if (!BaseUtil.isObjNull(auth.getUserType())) {
			login.setUserType(auth.getUserType());
		}

		LOGGER.info("Portal Type: {} - User Type: {}", login.getPortalType(), login.getUserType());

		// Check portal access time
		portalAccess();

		// Execute Login
		login = processLogin(login, request);

		if (login == null || login.getToken() == null) {
			throw new IdmException("404", messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		Token token = login.getToken();
		// here to view upload profile picture
		UserProfile userProfile = null;

		try {
			userProfile = getIdmService(login.getClientId(), token.getAccessToken()).getUserProfileById(userId, true,
					true);
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			String errorCode = e.getInternalErrorCode();
			throw new IdmException(errorCode, messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD)
					+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			throw new IdmException("404", messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		if (BaseUtil.isObjNull(userProfile) || BaseUtil.isObjNull(userProfile.getUserId())) {
			addUserAction(AuditActionPolicy.LOGIN_FAILED, auth.getUserId(), login, request);
			throw new IdmException("404", messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}

		addUserAction(AuditActionPolicy.LOGIN, auth.getUserId(), login, request);
		LOGGER.debug("USER PROFILE: {}", JsonUtil.toJsonNode(userProfile));
		User user = new User();
		try {
			user = JsonUtil.transferToObject(JsonUtil.toJsonNode(userProfile), User.class);
			user.setToken(token);
			user.setRoles(userProfile.getRoles());
			user.setMenus(userProfile.getMenus());
			user.setAuthOption(userProfile.getAuthOption());
			// Retrieve the DM Bucket Name for the Profile Picture
			List<Document> docLst = staticData.findDocumentsByTrxnNo("PROFPIC");
			if(!docLst.isEmpty()) {
				for(Document doc : docLst) {				
					user.setDmBucket(doc.getDmBucket());
					break;
				}
			}
		} catch (IOException e) {
			LOGGER.error("{}", e.getMessage());
		}
		return user;
	}
	
	
	
	@PostMapping(value = PageConstants.PAGE_LOGOUT, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public boolean logout(HttpServletRequest request, @RequestBody Logout auth) {
		boolean isLogout = false;

		try {
			staticData.addUserAction(AuditActionPolicy.LOGOUT, auth.getUserId(), null, request);
			LOGGER.info("User Auth Token on Logout: {} - {}", auth.getAuthToken(), getClientId(request));
			isLogout = getIdmService(getClientId(request), auth.getAuthToken()).logout();
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}

		return isLogout;
	}


	private void addUserAction(AuditActionPolicy auditPolicy, String userId, Object login,
			HttpServletRequest request) {
		String reqMsg = "";
		try {
			reqMsg = JsonUtil.objectMapper().writeValueAsString(login);
		} catch (JsonProcessingException e1) {
			// ignore
		}

		try {
			staticData.addUserAction(auditPolicy, userId, reqMsg, request);
		} catch (Exception e) {
			LOGGER.error("IDM-AuditAction Response Error: {}", e.getMessage());
		}

	}


	private LoginDto processLogin(LoginDto login, HttpServletRequest request) {
		try {
			login = getIdmService(request).login(login);
		} catch (IdmException e) {
			LOGGER.error("IdmException: {}", e.getMessage());

			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
			String errorCode = e.getInternalErrorCode();
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.E503IDM000.name(), errorCode)) {
				throw new IdmException(errorCode, "System unavailable. Please contact administrator. <br/>"
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			}
			if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM157.name(), errorCode)) {
				throw new IdmException(errorCode, messageService.getMessage("lbl.err.inv.usr")
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			} else {
				throw new IdmException(errorCode, messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD)
						+ (errorCode != null ? " [ " + errorCode + " ]" : ""));
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			throw new IdmException("400", messageService.getMessage(MessageConstants.ERROR_INVALID_PWORD));
		}
		return login;
	}


	/**
	 * Checking the time of the day the application is accessible, only if the
	 * flag is enabled.
	 */
	private void portalAccess() {
		boolean portalAccess = Boolean.parseBoolean(messageService.getMessage(BaseConfigConstants.PORTAL_ACCESS));

		if (portalAccess) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String timeStr = sdf.format(now);
			try {
				Time timeNow = new Time(sdf.parse(timeStr).getTime());
				Time timeFrom = new Time(
						sdf.parse(messageService.getMessage(BaseConfigConstants.PORTAL_ACCESS_FROM)).getTime());
				Time timeTo = new Time(
						sdf.parse(messageService.getMessage(BaseConfigConstants.PORTAL_ACCESS_TO)).getTime());
				LOGGER.debug(
						"\n\t[Time NOW: {} \n\tTime From: {} \n\tTime To: {} \n\tDate From: {} \n\tDate To: {} \n]",
						timeNow, timeFrom, timeTo, timeNow.before(timeFrom), timeNow.after(timeTo));
				if (timeNow.before(timeFrom) || timeNow.after(timeTo)) {
					if ((timeNow.after(timeFrom) && timeNow.before(timeTo))) {
						// DO NOTHING
					} else {
						throw new IdmException(messageService.getMessage("msg.sys.off.acc"));
					}
				}

			} catch (ParseException e) {
				// IGNORE
			}

		}
	}


}
