package com.idm.controller;


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

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.constants.IdmMailTemplateConstants;
import com.idm.constants.IdmTxnCodeConstants;
import com.idm.core.AbstractRestController;
import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserLdap;
import com.idm.model.IdmProfile;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserType;
import com.idm.model.IdmUserTypePortal;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.ChangePassword;
import com.idm.sdk.model.CustomNotification;
import com.idm.sdk.model.ForgotPassword;
import com.idm.sdk.model.UserProfile;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmUserGroupService;
import com.idm.service.IdmUserTypePortalService;
import com.idm.service.IdmUserTypeService;
import com.notify.sdk.constants.NotConfigConstants;
import com.notify.sdk.model.Notification;
import com.notify.sdk.util.MailUtil;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.USERS)
public class UserRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	private static final String CONTACT_EMAIL = "contactEmail";

	private static final String CONTACT_PHONE = "contactPhone";

	private static final String CURR_USR_ID = "currUserId";

	private static final String FWCMS_URL = "fwcmsUrl";

	private static final String GEN_DT = "genDate";

	private static final String LOGIN_NAME = "loginName";

	private static final String LOGIN_PSWD = "password";

	private static final String PORTAL_URL = "PORTAL_URL_";

	private static final String EMAIL = "Email";

	private static final String PROFILE = "Profile";

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmUserTypeService idmUserTypeSvc;

	@Autowired
	private IdmUserGroupService idmUserGroupSvc;
	
	@Autowired
	private IdmUserTypePortalService idmUserTypePortalSvc;

	String skipValues = "[-+.^:,()*@/]";


	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserProfile addUser(@Valid @RequestBody UserProfile tp,
			@RequestParam(value = "fixedId", required = false) boolean fixedId,
			@RequestParam(value = "fixedPword", required = false) boolean fixedPword,
			@RequestParam(value = "pendingActivation", required = false) boolean pendingActivation,
			@RequestParam(value = "sendMail", required = false, defaultValue = "true") boolean sendMail,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("TidmUserProfile: {}", tp);
		// throws IdmException if mandatory field not met.
		validateUserProfile(tp, sendMail);

		IdmUserGroup userGroupRole = idmUserGroupSvc.findByUserGroupCode(tp.getUserRoleGroupCode());

		Hibernate.initialize(userGroupRole.getUserType());
		
		IdmUserType ut = userGroupRole.getUserType();

		boolean isEmailAsId = ut.getEmailAsUserId();
		boolean isBypassPword = ut.isBypassPword();

		String loginname = isEmailAsId ? loginname = tp.getEmail() : super.genUserName(tp.getFirstName().replaceAll(skipValues, ""));

		// Overwrite loginname assigned previously, if fixedId is true
		if (fixedId) {
			loginname = tp.getUserId();
		}

		String userType = !BaseUtil.isObjNull(userGroupRole.getUserType())
				? userGroupRole.getUserType().getUserTypeCode()
				: null;

		// Check User Type if Valid
		if (!BaseUtil.isEqualsCaseIgnore("ALL", userType)) {
			IdmUserType idmUserType = idmUserTypeSvc.findByUserTypeCode(userType);

			if (BaseUtil.isObjNull(idmUserType)) {
				throw new IdmException(IdmErrorCodeEnum.E400IDM123, new String[] { userType });
			}
		}

		// throws IdmException if mandatory field not met.
		validateIdmProfile(tp, loginname, userType);


		IdmProfile idmProfile = new IdmProfile();
		try {
			idmProfile = JsonUtil.transferToObject(tp, IdmProfile.class);
		} catch (IOException e1) {
			LOGGER.error("{}", e1);
		}
		String systemType = getSystemHeader(request);
		if(!BaseUtil.isObjNull(systemType)) {
			idmProfile.setSystemType(systemType);
		}
		
		idmProfile.setUserType(userGroupRole.getUserType());
		idmProfile.setUserRoleGroup(userGroupRole);
		idmProfile.setUserId(loginname);
		idmProfile.setCreateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));
		idmProfile.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));

		if (tp.isDefaultActivated()) {
			idmProfile.setStatus(BaseConstants.USER_ACTIVE);
		} else if (pendingActivation) {
			idmProfile.setStatus(BaseConstants.USER_PENDING_ACTIVATION);
		} else {
			idmProfile.setStatus(BaseConstants.USER_FIRST_TIME);
		}

		boolean isUserCreated = false;
		IdmUserLdap newUser = new IdmUserLdap();
		String pword = null;
		if(!isBypassPword) {
			pword = fixedPword ? tp.getPassword() : super.uidGenerator.getGeneratedPwd();
			newUser.setUserName(loginname);
			newUser.setUserPassword(pword);
			newUser.setEmail(tp.getEmail());
			isUserCreated = idmProfileService.createUser(newUser, idmProfile);
		} else {
			// Bypass Password, not required to store in LDAP
			isUserCreated = idmProfileService.createUser(idmProfile);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_NEW);

		if (!isUserCreated) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}

		// Email Notification
		if (sendMail && !isBypassPword) {
			Map<String, Object> mailParams = new HashMap<>();
			String templateCode = null;
			if (!BaseUtil.isObjNull(tp.getCustomNotification())) {
				CustomNotification cn = tp.getCustomNotification();
				templateCode = cn.getTemplateCode();
				mailParams = cn.getTemplateParams();
			}

			try {
				sendMail(idmProfile, pword, templateCode, mailParams);
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e);
			}
		}

		return JsonUtil.transferToObject(idmProfile, UserProfile.class);
	}


	/**
	 * @param tp
	 *             UserProfile
	 * @throws IdmException
	 */
	private void validateUserProfile(UserProfile tp, boolean sendMail) throws IdmException {
		if (tp == null) {
			// profile null
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}
		if (tp.getFirstName() == null) {
			// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { "First Name", PROFILE });
		}
		if (tp.getEmail() == null && sendMail) {
			// no password
			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { EMAIL, PROFILE });
		}

	}


	/**
	 * @param tp
	 *             UserProfile
	 * @param loginname
	 *             String
	 * @throws IdmException
	 */
	private void validateIdmProfile(UserProfile tp, String loginname, String userType) throws IdmException {
		IdmProfile profile = idmProfileService.findUserProfileByUserIdAndUserType(loginname, userType);
		if (!BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { loginname });
		}

		if (BaseUtil.isObjNull(loginname)) {
			// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM120, new String[] { tp.getFirstName(), tp.getUserId() });
		}
	}


	public boolean sendMail(IdmProfile tp, String pword, String templateCode, Map<String, Object> mailParams) {
		mailParams.put(GEN_DT, new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		mailParams.put("name", tp.getFirstName());
		mailParams.put(LOGIN_NAME, tp.getUserId());
		mailParams.put(LOGIN_PSWD, pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get(PORTAL_URL + tp.getUserType());

			LOGGER.debug(CONTACT_PHONE + BaseConstants.EMPTY_BRACE + CONTACT_EMAIL + BaseConstants.EMPTY_BRACE
					+ PORTAL_URL + BaseConstants.EMPTY_BRACE, contactPhone, contactEmail, fwcmsUrl);
			mailParams.put(CONTACT_PHONE, contactPhone);
			mailParams.put(CONTACT_EMAIL, contactEmail);
			mailParams.put(FWCMS_URL, fwcmsUrl);
		}

		LOGGER.error("Mail Params: {}", mailParams);

		Notification notification = new Notification();
		notification.setNotifyTo(tp.getEmail());
		notification.setMetaData(MailUtil.convertMapToJson(mailParams));
		getNotifyService().addNotification(notification,
				!BaseUtil.isObjNull(templateCode) ? templateCode : IdmMailTemplateConstants.IDM_ACTIVATE_SUCCESS);
		return true;
	}


	/**
	 * Forgot Password
	 *
	 * @param forgotPassword
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/forgotPassword", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		IdmProfile profile = idmProfileService.findByUserId(forgotPassword.getUserName());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { forgotPassword.getUserName() });
		}
		
		String portalType = request.getHeader(BaseConstants.HEADER_PORTAL_TYPE);
		
		if(!BaseUtil.isObjNull(portalType)) {		
			// Search Portals allowed by logged in user
			List<IdmUserTypePortal> userPortals = idmUserTypePortalSvc
					.searchByProperty(new IdmUserTypePortal(profile.getUserType().getUserTypeCode(),
							portalType));
			LOGGER.info("LOGGED IN USER TYPE: {}", userPortals.size());
			if (BaseUtil.isListNullZero(userPortals)) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM157,
						new String[] { profile.getUserId(), portalType });
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}
		}

		profile.setStatus(BaseConstants.USER_FIRST_TIME);
		profile.setUpdateId(BaseUtil.getStr(forgotPassword.getUserName()));
		profile.setUpdateDt(DateUtil.getSQLTimestamp());
		String pword = null;
		String emailAdd = null;
		// get mail id from Ldap
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(forgotPassword.getUserName());
		if (BaseUtil.isObjNull(ldapEntryBean) || BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM122, new String[] { EMAIL, LOGIN_PSWD });
		}
		emailAdd = ldapEntryBean.getEmail();
		pword = super.uidGenerator.getGeneratedPwd();
		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(forgotPassword.getUserName());
		updatePwd.setUserPassword(pword);

		boolean isSuccess = idmProfileService.updateUserForForgotPwd(updatePwd, profile);
		if (!isSuccess) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		Map<String, Object> map = new HashMap<>();
		map.put(GEN_DT, new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		StringBuilder sb = new StringBuilder();
		sb.append(BaseUtil.getStrWithNull(profile.getFirstName())).append(" ")
				.append(BaseUtil.getStrWithNull(profile.getLastName()));
		map.put("name", sb.toString());
		map.put(LOGIN_NAME, profile.getUserId());
		map.put(LOGIN_PSWD, pword);

		// Email Notification
		Map<String, String> notConfig = getNotifyService().findAllConfig();
		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get(PORTAL_URL + profile.getUserType());
			LOGGER.debug(CONTACT_PHONE + BaseConstants.EMPTY_BRACE + CONTACT_EMAIL + BaseConstants.EMPTY_BRACE
					+ PORTAL_URL + BaseConstants.EMPTY_BRACE, contactPhone, contactEmail, fwcmsUrl);
			map.put(CONTACT_PHONE, contactPhone);
			map.put(CONTACT_EMAIL, contactEmail);
			map.put(FWCMS_URL, fwcmsUrl);
		}

		LOGGER.debug("Email Recipient: {}", emailAdd);
		Notification notification = new Notification();
		notification.setNotifyTo(emailAdd);
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, IdmMailTemplateConstants.IDM_FORGOT_PWORD);
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_FORGOT_PWORD);
		profile.setUserRoleGroup(null);
		return JsonUtil.transferToObject(profile, UserProfile.class);
	}


	/**
	 * Activate User
	 *
	 * @param userProfile
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/activateUser", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile activateUser(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		IdmProfile profile = idmProfileService.findByUserId(userProfile.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { userProfile.getUserId() });
		}
		// get mail id from Ldap
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(userProfile.getUserId());
		if (!BaseUtil.isObjNull(ldapEntryBean) && BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
			throw new IdmException(IdmErrorCodeEnum.I400IDM122, new String[] { EMAIL, LOGIN_PSWD });
		}

		String pword = super.uidGenerator.getGeneratedPwd();

		IdmUserLdap updatePwd = new IdmUserLdap();
		updatePwd.setUserName(userProfile.getUserId());
		updatePwd.setUserPassword(pword);

		profile.setStatus(BaseConstants.USER_FIRST_TIME);
		profile.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));
		boolean isSuccess = idmProfileService.updateUserForForgotPwd(updatePwd, profile);
		if (!isSuccess) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		// Email Notification
		Map<String, Object> map = new HashMap<>();
		map.put(GEN_DT, new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		map.put("name", profile.getFirstName());
		map.put(LOGIN_NAME, profile.getUserId());
		map.put(LOGIN_PSWD, pword);

		Map<String, String> notConfig = getNotifyService().findAllConfig();

		if (!BaseUtil.isObjNull(notConfig)) {
			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String fwcmsUrl = notConfig.get(PORTAL_URL + profile.getUserType());
			LOGGER.debug(CONTACT_PHONE + BaseConstants.EMPTY_BRACE + CONTACT_EMAIL + BaseConstants.EMPTY_BRACE
					+ PORTAL_URL + BaseConstants.EMPTY_BRACE, contactPhone, contactEmail, fwcmsUrl);
			map.put(CONTACT_PHONE, contactPhone);
			map.put(CONTACT_EMAIL, contactEmail);
			map.put(FWCMS_URL, fwcmsUrl);
		}

		Notification notification = new Notification();
		notification.setNotifyTo(ldapEntryBean.getEmail());
		notification.setMetaData(MailUtil.convertMapToJson(map));
		getNotifyService().addNotification(notification, IdmMailTemplateConstants.IDM_FORGOT_PWORD);
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ACTIVATE_USER);
		return JsonUtil.transferToObject(profile, UserProfile.class);
	}


	/**
	 * Change Password
	 *
	 * @param changePassword
	 * @param request
	 * @param checkBranch
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/changePassword", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile changePassword(@Valid @RequestBody ChangePassword changePassword, HttpServletRequest request,
			@RequestParam(value = "checkBranch", required = false) boolean checkBranch, HttpServletResponse response)
			throws IOException {
		
		IdmProfile profile = idmProfileService.findByUserId(changePassword.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM104, new String[] { changePassword.getUserId() });
		}
		
		if (checkBranch && !BaseUtil.isEqualsCaseIgnore(String.valueOf(profile.getBranchId()),
				changePassword.getBranchId())) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM161, new String[] { changePassword.getBranchId() });
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

		boolean msg = idmUserDao.changePassword(updatePwd, true);
		LOGGER.debug("message from ladap: {}", msg);

		if (msg) {
			// For first time users need to update profile status to active
			if (BaseUtil.isEqualsCaseIgnore(changePassword.getRecoveryMethod(), "firstTime")) {
				profile.setStatus(BaseConstants.USER_ACTIVE);
			}
			profile.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));
			idmProfileService.update(profile);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_CHANGE_PWORD);
		if (!BaseUtil.isObjNull(profile.getUserRoleGroup())) {
			profile.setUserRoleGroup(null);
		}
		return JsonUtil.transferToObject(profile, UserProfile.class);
	}


	/**
	 * Change Password - Direct Update
	 *
	 * @param changePassword
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/changePassword/directUpdate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
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


	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserProfile> getProfile(
			@RequestParam(value = "includePassword", required = false) String includePassword,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "userRoleGroupCodes", required = false) String... userRoleGroupCodes)
			throws IOException {
		LOGGER.info("Rest Get getProfile.");
		List<UserProfile> prfLstTemp = new ArrayList<>();
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
							try {
								prfLstTemp.add(JsonUtil.transferToObject(profile, UserProfile.class));
							} catch (IOException e) {
								LOGGER.error(e.getMessage());
							}
						}
					}

				}
			} else {
				prfLstTemp = profileIncludePassword(includePassword);
			}

		} else {
			prfLstTemp = profileIncludePassword(includePassword);
		}

		return prfLstTemp;
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/search", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> search(@RequestParam(value = "includePassword", required = false) boolean includePassword,
			UserProfile userProfile, HttpServletRequest request) throws IOException {
		LOGGER.info("Rest Get getProfile - Include Password: {}", includePassword);
		String systemType = getSystemHeader(request);
		if(!BaseUtil.isObjNull(systemType)) {
			userProfile.setSystemType(systemType);
		}
		
		List<IdmProfile> prfLstTemp = idmProfileService.searchUserProfile(userProfile);

		LOGGER.debug("Rest Get getProfile - Include Password: {}", BaseUtil.isObjNull(userProfile.getSyncFlag()));
		// Update Sync Flag
		if (!BaseUtil.isObjNull(userProfile.getSyncFlag()) && !BaseUtil.isListNull(prfLstTemp)) {
			for (IdmProfile idmProf : prfLstTemp) {
				idmProfileService.updateSyncFlag(idmProf.getUserId(),
						Integer.valueOf(BaseConstants.SYNC_INPROGRESS));
			}
		}

		if (includePassword && !BaseUtil.isListNull(prfLstTemp)) {
			for (IdmProfile p : prfLstTemp) {
				IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
				if (!BaseUtil.isObjNull(ldapBean)) {
					p.setPassword(ldapBean.getUserPassword());
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

		return JsonUtil.transferToList(prfLstTemp, UserProfile.class);
	}


	// Active profiles include password.
	@SuppressWarnings("unchecked")
	public List<UserProfile> profileIncludePassword(String includePassword) throws IOException {
		List<IdmProfile> prfLstTemp = idmProfileService.findAllProfilesActive();
		if (includePassword != null && includePassword.equalsIgnoreCase("true") && !BaseUtil.isListNull(prfLstTemp)) {
			for (IdmProfile p : prfLstTemp) {
				IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
				if (!BaseUtil.isObjNull(ldapBean)) {
					p.setPassword(ldapBean.getUserPassword());
				}
			}
		}

		return JsonUtil.transferToList(prfLstTemp, UserProfile.class);
	}


	public boolean updateUser(String message) {
		String requestJsonMsg = message;
		LOGGER.info("requestJsonMsg: {}", requestJsonMsg);

		IdmUserLdap obj = null;

		try {
			obj = objectMapper.readValue(requestJsonMsg, IdmUserLdap.class);
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		}
		return idmUserDao.authenticate(obj);
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


	/**
	 * Activate User
	 *
	 * @param userProfile
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/activateNewRegisteredUser", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile activateNewRegisteredUser(@Valid @RequestBody UserProfile userProfile,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		IdmProfile profile = idmProfileService.findByUserId(userProfile.getUserId());
		IdmUserLdap ldap = idmUserDao.searchEntry(userProfile.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { userProfile.getUserId() });
		}

		if (BaseUtil.isEquals(profile.getStatus(), BaseConstants.USER_ACTIVE)) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { userProfile.getUserId() });
		}

		profile.setStatus(BaseConstants.USER_ACTIVE);
		profile.setUpdateId(userProfile.getUserId());
		profile.setUpdateDt(DateUtil.getSQLTimestamp());
		boolean isSuccess = idmProfileService.updateUser(ldap, profile, false);
		if (!isSuccess) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM159);
		}

		return JsonUtil.transferToObject(profile, UserProfile.class);
	}

	/**
	 * Generate activation code
	 * Default - 6 digit numbers / 
	 * 
	 * @param userId
	 * @return UserProfile
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.USER_ACTIVATION_GENERATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile generateActivationCode(@RequestParam(value = "userId", required = true) String userId) throws IOException {
		IdmProfile profile = idmProfileService.findByUserId(userId);
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { userId });
		}
		return JsonUtil.transferToObject(idmProfileService.generateActivationCode(profile), UserProfile.class);
	}
	
	
	
	/**
	 * Verify activation code
	 * 
	 * @param userProfile
	 * @return UserProfile
	 * @throws IOException
	 */
	@PostMapping(value = IdmUrlConstants.USER_ACTIVATION_VERIFY, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile verifyActivationCode(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "activationCode", required = true) String activationCode) throws IOException {
		
		if (BaseUtil.isObjNull(userId)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM126, new String[] { "userId" });
		}
		
		if (BaseUtil.isObjNull(activationCode)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM126, new String[] { "activationCode" });
		}
		
		IdmProfile profile = idmProfileService.findByUserId(userId);
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM130, new String[] { userId });
		}

		
		if( idmProfileService.verifyActivationCode(userId, activationCode) ) {
			return JsonUtil.transferToObject(profile, UserProfile.class);
		} else {
			throw new IdmException(IdmErrorCodeEnum.E400IDM125, new String[] { activationCode });
		}
	}
	
	
	/**
	 * method for report Total Registered User By Day
	 */
	@GetMapping(value = IdmUrlConstants.RPT_USER_REGISTERED_BY_DAY, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> getCountRegisteredUserByDay(HttpServletRequest request) {
		return idmProfileService.getCountRegisteredUserByDay();
	}
}