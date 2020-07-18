/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmConfigConstants;
import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.ldap.IdmUserDao;
import com.baseboot.idm.ldap.IdmUserLdap;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.ChangePassword;
import com.baseboot.idm.sdk.model.ForgotPassword;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmConfigService;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.util.UidGenerator;
import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.notify.sdk.constants.MailTypeEnum;
import com.baseboot.notify.sdk.constants.NotConfigConstants;
import com.baseboot.notify.sdk.model.Notification;
import com.baseboot.notify.sdk.util.MailUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.USERS)
public class UserRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmConfigService idmConfigSvc;

	@Autowired
	private UidGenerator uidGenerator;

	@Lazy
	@Autowired
	private IdmUserDao idmUserDao;

	String skipValues = "[-+.^:,()*@/]";


	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile addUser(@Valid @RequestBody IdmProfile tp,
			@RequestParam(value = "fixedId", required = false) String fixedId,
			@RequestParam(value = "fwcmsId", required = false) String fwcmsId,
			@RequestParam(value = "sendMail", required = false) String sendMail, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("TidmUserProfile: {}", tp);

		if (tp == null) {// profile null
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}
		if (tp.getFullName() == null) {// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { "Full Name", "Profile" });
		}

		if (tp.getEmail() == null) {// no password
			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { "Email", "Profile" });
		}
		String loginname = null;
		Boolean isEmailAsId = Boolean
				.valueOf(idmConfigSvc.findValueByConfigCode(IdmConfigConstants.EMAIL_AS_USER_ID));

		if (isEmailAsId && !BaseUtil.isEqualsCaseIgnore("INT", tp.getUserType())) {
			loginname = tp.getEmail();
			IdmProfile profile = idmProfileService.findProfileByUserId(loginname);
			if (!BaseUtil.isObjNull(profile)) {
				throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { loginname });
			}
		} else {
			if (BaseUtil.isEquals(fixedId, "true")) {
				loginname = tp.getUserId();
				IdmProfile profile = idmProfileService.findProfileByUserId(loginname);
				if (!BaseUtil.isObjNull(profile)) {
					throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { loginname });
				}
			} else {
				loginname = super.genUserName(tp.getFullName().replaceAll(skipValues, ""));
			}
		}

		if (BaseUtil.isObjNull(loginname)) {// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM120, new String[] { tp.getFullName(), tp.getUserId() });
		}

		String pword = uidGenerator.getGeneratedPwd();

		tp.setUserId(loginname);
		tp.setDocMgtId(tp.getDocMgtId());
		tp.setCreateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		tp.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		// build ldap entry to create
		IdmUserLdap newUser = new IdmUserLdap();
		newUser.setUserName(tp.getUserId());
		newUser.setUserPassword(pword);
		newUser.setEmail(tp.getEmail());

		// for fwcmsid create in user profile(No need to create in ldap).
		// create emp in fwcms for new emp and send mail.
		if (BaseUtil.isEquals(fwcmsId, "true")) {
			idmProfileService.create(tp);
			if (BaseUtil.isEquals(sendMail, "true")) {
				pword = tp.getPassword();
			}

		} else {
			if (tp.isDefaultActivated()) {
				tp.setStatus(BaseConstants.USER_ACTIVE);
			} else {
				tp.setStatus(BaseConstants.USER_FIRST_TIME);
			}
			idmProfileService.createUser(newUser, tp);
		}
		try {
			sendMail(tp, pword);
		} catch (Exception e) {
			// TODO: IGNORE ERROR WHEN MAIL IS FAILED?
			LOGGER.error("Exception", e);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_NEW);
		return tp;
	}


	public boolean sendMail(IdmProfile tp, String pword) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("genDate", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		map.put("name", tp.getFullName());
		map.put("loginName", tp.getUserId());
		map.put("password", pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get("PORTAL_URL_" + tp.getUserType());

			LOGGER.debug("contactPhone: {} - contactEmail: {} - portalUrl: {}", contactPhone, contactEmail,
					fwcmsUrl);
			map.put("contactPhone", contactPhone);
			map.put("contactEmail", contactEmail);
			map.put("fwcmsUrl", fwcmsUrl);
		}

		Notification notification = new Notification();
		notification.setNotifyTo(tp.getEmail());
		notification.setSubject(MailTemplate.IDM_ACTIVATE_SUCCESS.getSubject());
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.IDM_ACTIVATE_SUCCESS);
		return true;
	}


	/**
	 * Forgot Password
	 *
	 * @param forgotPassword
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IdmProfile profile = idmProfileService.findProfileByUserId(forgotPassword.getUserName());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { forgotPassword.getUserName() });
		}

		profile.setStatus(BaseConstants.USER_FIRST_TIME);
		profile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		String pword = null;
		String emailAdd = null;
		// get mail id from Ldap
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(forgotPassword.getUserName());
		if (BaseUtil.isObjNull(ldapEntryBean) || BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM122, new String[] { "Email", "Password" });
		}
		emailAdd = ldapEntryBean.getEmail();
		pword = uidGenerator.getGeneratedPwd();
		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(forgotPassword.getUserName());
		updatePwd.setUserPassword(pword);

		boolean isSuccess = idmProfileService.updateUserForForgotPwd(updatePwd, profile);
		if (!isSuccess) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("genDate", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		map.put("name", profile.getFullName());
		map.put("loginName", profile.getUserId());
		map.put("password", pword);

		// Email Notification
		Map<String, String> notConfig = getNotifyService().findAllConfig();
		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get("PORTAL_URL_" + profile.getUserType());
			LOGGER.debug("contactPhone: {} - contactEmail: {} - portalUrl: {}", contactPhone, contactEmail,
					fwcmsUrl);
			map.put("contactPhone", contactPhone);
			map.put("contactEmail", contactEmail);
			map.put("fwcmsUrl", fwcmsUrl);
		}

		LOGGER.debug("Email Recipient: {}", emailAdd);
		Notification notification = new Notification();
		notification.setNotifyTo(emailAdd);
		notification.setSubject(MailTemplate.IDM_FORGOT_PWORD.getSubject());
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.IDM_FORGOT_PWORD);
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_FORGOT_PWORD);
		return profile;
	}


	/**
	 * Activate User
	 *
	 * @param userProfile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/activateUser", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile activateUser(@Valid @RequestBody IdmProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IdmProfile profile = idmProfileService.findProfileByUserId(userProfile.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { userProfile.getUserId() });
		}
		// get mail id from Ldap
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(userProfile.getUserId());
		if (BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM122, new String[] { "Email", "Password" });
		}

		String pword = uidGenerator.getGeneratedPwd();

		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(userProfile.getUserId());
		updatePwd.setUserPassword(pword);

		profile.setStatus(BaseConstants.USER_FIRST_TIME);
		profile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		boolean isSuccess = idmProfileService.updateUserForForgotPwd(updatePwd, profile);
		if (!isSuccess) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		// Email Notification
		Map<String, Object> map = new HashMap<>();
		map.put("genDate", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		map.put("name", profile.getFullName());
		map.put("loginName", profile.getUserId());
		map.put("password", pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get("PORTAL_URL_" + profile.getUserType());
			LOGGER.debug("contactPhone: {} - contactEmail: {} - portalUrl: {}", contactPhone, contactEmail,
					fwcmsUrl);
			map.put("contactPhone", contactPhone);
			map.put("contactEmail", contactEmail);
			map.put("fwcmsUrl", fwcmsUrl);
		}

		Notification notification = new Notification();
		notification.setNotifyTo(ldapEntryBean.getEmail());
		notification.setSubject(MailTemplate.IDM_FORGOT_PWORD.getSubject());
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.IDM_FORGOT_PWORD);
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ACTIVATE_USER);
		return profile;
	}


	/**
	 * Change Password
	 *
	 * @param changePassword
	 * @param request
	 * @param checkBranch
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile changePassword(@Valid @RequestBody ChangePassword changePassword, HttpServletRequest request,
			@RequestParam(value = "checkBranch", required = false) boolean checkBranch,
			HttpServletResponse response) {
		IdmProfile profile = idmProfileService.findProfileByUserId(changePassword.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { changePassword.getUserId() });
		}

		if (checkBranch) {
			if (!BaseUtil.isEqualsCaseIgnore(String.valueOf(profile.getBranchId()), changePassword.getBranchId())) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM161, new String[] { changePassword.getBranchId() });
			}
		}

		IdmUserLdap entry = new IdmUserLdap();
		entry.setUserName(changePassword.getUserId());
		entry.setUserPassword(changePassword.getCurrPword());
		// checking entered password
		boolean isValidPassword = idmUserDao.authenticate(entry);
		if (!isValidPassword) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM103, new String[] { changePassword.getUserId() });
		}

		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(changePassword.getUserId());
		updatePwd.setUserPassword(changePassword.getNewPword());

		LOGGER.debug("getCurrPword: {}", changePassword.getCurrPword());
		LOGGER.debug("getNewPword: {}", changePassword.getNewPword());

		// boolean msg = idmUserDao.changePassword(updatePwd, false);
		boolean msg = idmUserDao.changePassword(updatePwd, true);
		LOGGER.debug("message from ladap: {}", msg);
		/*
		 * if(msg){ if(msg.contains("password history")){ throw new
		 * IdmException(ResponseCodeEnum.I404IDM158); } }
		 */
		if (msg) {
			profile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
			idmProfileService.update(profile);
		}

		// For first time users need to update profile status to active
		if (BaseUtil.isEqualsCaseIgnore(changePassword.getRecoveryMethod(), "firstTime")) {
			profile.setStatus(BaseConstants.USER_ACTIVE);
			profile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
			idmProfileService.update(profile);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_CHANGE_PWORD);
		return profile;
	}


	/**
	 * Change Password - Direct Update
	 *
	 * @param changePassword
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/changePassword/directUpdate", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public ChangePassword directUpdate(@Valid @RequestBody ChangePassword changePassword, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("Replace encrypted pwd directly in Ldap");

		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(changePassword.getUserId());
		if (BaseUtil.isObjNull(ldapEntryBean)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { changePassword.getUserId() });
		}
		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(changePassword.getUserId());
		updatePwd.setUserPassword(changePassword.getNewPword());

		boolean msg = idmUserDao.changePassword(updatePwd, true);
		LOGGER.info("message from ladap: {}", msg);
		if (!msg) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM158);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_CHANGE_PWORD);
		return changePassword;
	}


	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmProfile> getProfile(
			@RequestParam(value = "includePassword", required = false) String includePassword,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "userRoleGroupCodes", required = false) String... userRoleGroupCodes) {
		LOGGER.info("Rest Get getProfile.");
		List<IdmProfile> prfLstTemp = new ArrayList<>();
		if (!BaseUtil.isObjNull(embededRole)
				|| (includePassword != null && includePassword.equalsIgnoreCase("true"))) {
			if (!BaseUtil.isObjNull(embededRole)) {
				List<IdmProfile> profilesList = idmProfileService.findAllUsersByIdmRoleroleCode(embededRole, userId,
						userRoleGroupCodes);
				for (IdmProfile profile : profilesList) {
					if (includePassword != null && includePassword.equalsIgnoreCase("true")) {
						IdmUserLdap ldapBean = idmUserDao.searchEntry(profile.getUserId());
						LOGGER.debug("ldapBean User: {}", profile.getUserId());
						LOGGER.debug("Result form ldap: {}", ldapBean);
						if (ldapBean != null) {
							profile.setPassword(ldapBean.getUserPassword());
							prfLstTemp.add(profile);
						}
					}

				}
			} else {
				prfLstTemp = profileIncludePassword(includePassword, userRoleGroupCodes);
			}

		} else {
			prfLstTemp = profileIncludePassword(includePassword, userRoleGroupCodes);
		}

		return prfLstTemp;
	}


	@RequestMapping(value = "/search", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> search(@RequestParam(value = "includePassword", required = false) boolean includePassword,
			UserProfile userProfile) {
		LOGGER.info("Rest Get getProfile - Include Password: {}", includePassword);
		List<IdmProfile> prfLstTemp = idmProfileService.searchUserProfile(userProfile);

		LOGGER.debug("Rest Get getProfile - Include Password: {}", BaseUtil.isObjNull(userProfile.getSyncFlag()));
		// Update Sync Flag
		if (!BaseUtil.isObjNull(userProfile.getSyncFlag()) && !BaseUtil.isListNull(prfLstTemp)) {
			for (IdmProfile idmProf : prfLstTemp) {
				idmProfileService.updateSyncFlag(idmProf.getUserId(),
						Integer.valueOf(BaseConstants.SYNC_INPROGRESS));
			}
		}

		if (includePassword) {
			if (!BaseUtil.isListNull(prfLstTemp)) {
				for (IdmProfile p : prfLstTemp) {
					IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
					if (!BaseUtil.isObjNull(ldapBean)) {
						p.setPassword(ldapBean.getUserPassword());
					}
				}
			}
		}
		// email
		for (IdmProfile p : prfLstTemp) {
			IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
			if (!BaseUtil.isObjNull(ldapBean)) {
				getEmail(p, p.getUserId());
			}
		}

		return prfLstTemp;
	}


	// Active profiles include password.
	public List<IdmProfile> profileIncludePassword(String includePassword, String... userRoleGroupCodes) {
		List<IdmProfile> prfLstTemp = idmProfileService.findAllProfilesActive();
		if (includePassword != null && includePassword.equalsIgnoreCase("true")) {
			if (!BaseUtil.isListNull(prfLstTemp)) {
				for (IdmProfile p : prfLstTemp) {
					IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
					if (!BaseUtil.isObjNull(ldapBean)) {
						p.setPassword(ldapBean.getUserPassword());
					}
				}
			}
		}
		return prfLstTemp;
	}


	public boolean updateUser(String message) {
		String requestJsonMsg = message;
		LOGGER.info("requestJsonMsg: {}", requestJsonMsg);

		IdmUserLdap obj = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(requestJsonMsg, IdmUserLdap.class);
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		}
		boolean ld = idmUserDao.authenticate(obj);
		return ld;
	}


	public String getUsers(String id) {
		LOGGER.debug("id : {}", id);
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(id);
		JsonNode jn = new ObjectMapper().valueToTree(ldapEntryBean);
		return jn.toString();
	}


	public IdmProfile getEmail(IdmProfile tidmUserProfile, String id) {
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(id);
		if (!BaseUtil.isObjNull(ldapEntryBean)) {
			tidmUserProfile.setEmail(ldapEntryBean.getEmail());
		}
		return tidmUserProfile;
	}

}