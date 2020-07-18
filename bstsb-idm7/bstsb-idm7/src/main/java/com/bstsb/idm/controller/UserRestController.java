/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.IdmMailTemplateConstants;
import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.dao.IdmSocialRepository;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.ldap.IdmUserLdap;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.model.IdmSocial;
import com.bstsb.idm.model.IdmUserType;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.ChangePassword;
import com.bstsb.idm.sdk.model.CustomNotification;
import com.bstsb.idm.sdk.model.ForgotPassword;
import com.bstsb.idm.sdk.model.SocialUserDto;
import com.bstsb.idm.sdk.model.UserProfile;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.IdmSocialService;
import com.bstsb.idm.service.IdmUserTypeService;
import com.bstsb.notify.sdk.constants.NotConfigConstants;
import com.bstsb.notify.sdk.model.Attachment;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.notify.sdk.model.ReportAttachment;
import com.bstsb.notify.sdk.util.MailUtil;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.DateUtil;
import com.bstsb.util.JsonUtil;
import com.bstsb.util.MediaType;
import com.bstsb.util.constants.BaseConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
/**
 * @author muhammad.hafidz
 *
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
	private IdmSocialRepository idmSocialRep;

	@Autowired
	private IdmSocialService idmSocialSvc;
	
	String skipValues = "[-+.^:,()*@/]";


	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile addUser(@Valid @RequestBody UserProfile tp,
			@RequestParam(value = "fixedId", required = false) boolean fixedId,
			@RequestParam(value = "fixedPword", required = false) boolean fixedPword,
			@RequestParam(value = "pendingActivation", required = false) boolean pendingActivation,
			@RequestParam(value = "sendMail", required = false, defaultValue = "true") boolean sendMail,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("TidmUserProfile: {}", tp);
		// throws IdmException if mandatory field not met.
		validateUserProfile(tp);

		// compare with JLS - variables name switched to allow keep using 'tp' declared above.
		// this however, subject to change and do change the calling method as well to this REST.
		IdmProfile userProfile = new IdmProfile();
		try {
			userProfile = dozerMapper.map(tp, IdmProfile.class);
		} catch (MappingException e) {
			LOGGER.error("Error: {}", e);
		}

		// check if id exist in social login information available in the
		// passing parameter otherwise assume normal login
		SocialUserDto socialUser = tp.getSocialUser();
		if (!BaseUtil.isObjNull(socialUser) && !StringUtils.isEmpty(socialUser.getProviderUserId())) {
			userProfile = addUserSocial(tp, userProfile, sendMail);
		} else {
			userProfile = addUserDefault(userProfile, tp, fixedId, fixedPword, pendingActivation, sendMail, request);
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_NEW);
		return userProfile;


	}


	/*
	 * Default login ADD IDM_PROFILE
	 */
	public IdmProfile addUserDefault(IdmProfile tp, UserProfile up, boolean fixedId, boolean fixedPword,
			boolean pendingActivation, boolean sendMail, HttpServletRequest request) {

		// addUserDefault might be slightly different than JLS 
		// - as core functionality adding default user mantains from this IDM implementation
		// do check any missing implementation
		String loginname = null;
		boolean isEmailAsId = idmUserTypeSvc.checkEmailAsUserId(tp.getUserType());

		if (isEmailAsId) {
			loginname = tp.getEmail();
		} else {
			loginname = super.genUserName(tp.getFullName().replaceAll(skipValues, ""));
		}

		// Overwrite loginname assigned previously, if fixedId is true
		if (fixedId) {
			loginname = tp.getUserId();
		}

		// Check User Type if Valid
		if (!BaseUtil.isEqualsCaseIgnoreAny("ALL", tp.getUserType())) {
			IdmUserType userType = idmUserTypeSvc.findByUserTypeCode(tp.getUserType());

			if (BaseUtil.isObjNull(userType)) {
				throw new IdmException(IdmErrorCodeEnum.E400IDM123, new String[] { tp.getUserType() });
			}
		}
		// throws IdmException if mandatory field not met.
		validateIdmProfile(tp, loginname);

		String pword = fixedPword ? tp.getPassword() : super.uidGenerator.getGeneratedPwd();

		IdmProfile idmProfile = dozerMapper.map(tp, IdmProfile.class);
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

		LOGGER.info("{}", JsonUtil.toJsonNode(idmProfile));

		IdmUserLdap newUser = new IdmUserLdap();
		newUser.setUserName(loginname);
		newUser.setUserPassword(pword);
		newUser.setEmail(tp.getEmail());
		boolean isUserCreated = idmProfileService.createUser(newUser, idmProfile);

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_NEW);

		if (!isUserCreated) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM153);
		}

		Map<String, Object> mailParams = new HashMap<>();
		String templateCode = null;
		List<Attachment> attachments = new ArrayList<>();
		if (!BaseUtil.isObjNull(up.getCustomNotification())) {
			CustomNotification cn = up.getCustomNotification();
			templateCode = cn.getTemplateCode();
			mailParams = cn.getTemplateParams();
			Object mail = mailParams.get("mailAttachment");
			if (mail instanceof String) {
				try {
					ReportAttachment ra = JsonUtil.objectMapper().readValue(mail.toString(),
							ReportAttachment.class);
					attachments.add(ra);
				} catch (IOException e) {
					LOGGER.error("Exception: {}", e.getMessage());
				}
			}
			mailParams.remove("mailAttachment");
		}

		if (sendMail) {
			try {
				sendMail(idmProfile, pword, templateCode, mailParams, attachments);
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e);
			}
		}


		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_USER_NEW);
		return tp;
	}


	/*
	 * Login with Social Add IDM_SOCIAL Add IDM_PROFILE Add IDM_USER_CONNECTION
	 */
	public IdmProfile addUserSocial(UserProfile userProfile, IdmProfile tp, boolean sendMail) {

		// check if user exist in IDM_SOCIAL. Create if none.
		IdmSocial idmSocial = idmSocialRep.findByEmail(tp.getEmail());
		if (BaseUtil.isObjNull(idmSocial)) {
			idmSocial = new IdmSocial();
			try {
				BeanUtils.copyProperties(userProfile.getSocialUser(), idmSocial);
			} catch (IllegalAccessException | InvocationTargetException e1) {
				LOGGER.error("Error: {}", e1);			
			}
			idmSocial.setCreateDt(DateUtil.convertDate2SqlTimeStamp(Calendar.getInstance().getTime()));
			idmSocial.setCreateId(userProfile.getSocialUser().getUserId());
			idmSocial.setUpdateDt(idmSocial.getCreateDt());
			idmSocial.setUpdateId(idmSocial.getCreateId());

			// INSERT into IDM_SOCIAL
			try {
				idmSocialSvc.create(idmSocial);
			} catch (Exception e) {
				LOGGER.error("Exception : create IDM_SOCIAL.", e);
			}
		}

		tp.setUserId(tp.getEmail()); // default IDM_PROFILE.USERID = tp.EMAIL
		tp.setDocMgtId(tp.getDocMgtId());
		if (BaseUtil.isObjNull(tp.getCreateId())) {
			tp.setCreateId(tp.getEmail());
		}
		if (BaseUtil.isObjNull(tp.getCreateDt())) {
			tp.setCreateDt(DateUtil.convertDate2SqlTimeStamp(Calendar.getInstance().getTime()));
		}
		if (BaseUtil.isObjNull(tp.getUpdateId())) {
			tp.setUpdateId(tp.getEmail());
		}
		if (BaseUtil.isObjNull(tp.getUpdateDt())) {
			tp.setUpdateDt(DateUtil.convertDate2SqlTimeStamp(Calendar.getInstance().getTime()));
		}
		tp.setStatus(BaseConstants.USER_ACTIVE); // default to active
		String pword = userProfile.getSocialUser().getProvider() + userProfile.getSocialUser().getProviderUserId();
		tp.setPassword(pword);
		try {
			// INSERT into IDM_PROFILE
			idmProfileService.createUser(null, tp);
		} catch (Exception e) {
			LOGGER.error("Exception : create IDM_PROFILE.", e);
		}
		
		Map<String, Object> mailParams = new HashMap<>();
		String templateCode = null;
		List<Attachment> attachments = new ArrayList<>();
		if (!BaseUtil.isObjNull(userProfile.getCustomNotification())) {
			CustomNotification cn = userProfile.getCustomNotification();
			templateCode = cn.getTemplateCode();
			mailParams = cn.getTemplateParams();
			Object mail = mailParams.get("mailAttachment");
			if (mail instanceof String) {
				try {
					ReportAttachment ra = JsonUtil.objectMapper().readValue(mail.toString(),
							ReportAttachment.class);
					attachments.add(ra);
				} catch (IOException e) {
					LOGGER.error("Exception: {}", e.getMessage());
				}
			}
			mailParams.remove("mailAttachment");
		}

		if (sendMail) {
			try {
				sendMailSocial(tp, templateCode, mailParams, attachments);
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e);
			}
		}		
		
		return tp;
	}
	
	
	/**
	 * @param tp
	 *             UserProfile
	 * @throws IdmException
	 */
	private void validateUserProfile(UserProfile tp) throws IdmException {
		if (tp == null) {
			// profile null
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}
		if (tp.getFullName() == null) {
			// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM137, new String[] { "Full Name", PROFILE });
		}
		if (tp.getEmail() == null) {
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
	private void validateIdmProfile(IdmProfile tp, String loginname) throws IdmException {
		IdmProfile profile = idmProfileService.findUserProfileByUserIdAndUserType(loginname, tp.getUserType());
		if (!BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I409IDM118, new String[] { loginname });
		}

		if (BaseUtil.isObjNull(loginname)) {
			// no user name
			throw new IdmException(IdmErrorCodeEnum.I400IDM120, new String[] { tp.getFullName(), tp.getUserId() });
		}
	}


	public boolean sendMail(IdmProfile tp, String pword, String templateCode, Map<String, Object> mailParams,
			List<Attachment> attachments) {
		mailParams.put(GEN_DT, new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		mailParams.put("name", tp.getFullName());
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

		LOGGER.info("Mail Params: {}", mailParams);

		Notification notification = new Notification();
		notification.setNotifyTo(tp.getEmail());
		notification.setMetaData(MailUtil.convertMapToJson(mailParams));
		if (!BaseUtil.isListNullZero(attachments)) {
			notification.setAttachments(attachments);
		}
		getNotifyService().addNotification(notification,
				!BaseUtil.isObjNull(templateCode) ? templateCode : IdmMailTemplateConstants.IDM_ACTIVATE_SUCCESS);
		return true;
	}

	public boolean sendMailSocial(IdmProfile tp, String templateCode, Map<String, Object> mailParams,
			List<Attachment> attachments) {
		mailParams.put(GEN_DT, new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
		mailParams.put("name", tp.getFullName());


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

		LOGGER.info("Mail Params: {}", mailParams);

		Notification notification = new Notification();
		notification.setNotifyTo(tp.getEmail());
		notification.setMetaData(MailUtil.convertMapToJson(mailParams));
		if (!BaseUtil.isListNullZero(attachments)) {
			notification.setAttachments(attachments);
		}
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
	 * @throws Exception
	 */
	@PostMapping(value = "/forgotPassword", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword, HttpServletRequest request,
			HttpServletResponse response) {
		IdmProfile profile = idmProfileService.findProfileByUserId(forgotPassword.getUserName());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { forgotPassword.getUserName() });
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
		map.put("name", profile.getFullName());
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
	@PostMapping(value = "/activateUser", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile activateUser(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) {
		IdmProfile profile = idmProfileService.findProfileByUserId(userProfile.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { userProfile.getUserId() });
		}
		// get mail id from Ldap
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(userProfile.getUserId());
		if (BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
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
		map.put("name", profile.getFullName());
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
	@PostMapping(value = "/changePassword", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile changePassword(@Valid @RequestBody ChangePassword changePassword, HttpServletRequest request,
			@RequestParam(value = "checkBranch", required = false) boolean checkBranch,
			HttpServletResponse response) {
		IdmProfile profile = idmProfileService.findProfileByUserId(changePassword.getUserId());
		if (BaseUtil.isObjNull(profile)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128, new String[] { changePassword.getUserId() });
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
			profile.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));
			idmProfileService.update(profile);
		}

		// For first time users need to update profile status to active
		if (BaseUtil.isEqualsCaseIgnore(changePassword.getRecoveryMethod(), "firstTime")) {
			profile.setStatus(BaseConstants.USER_ACTIVE);
			profile.setUpdateId(BaseUtil.getStr(request.getAttribute(CURR_USR_ID)));
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
				prfLstTemp = profileIncludePassword(includePassword);
			}

		} else {
			prfLstTemp = profileIncludePassword(includePassword);
		}

		return prfLstTemp;
	}


	@GetMapping(value = "/search", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
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

		return prfLstTemp;
	}


	// Active profiles include password.
	public List<IdmProfile> profileIncludePassword(String includePassword) {
		List<IdmProfile> prfLstTemp = idmProfileService.findAllProfilesActive();
		if (includePassword != null && includePassword.equalsIgnoreCase("true") && !BaseUtil.isListNull(prfLstTemp)) {
			for (IdmProfile p : prfLstTemp) {
				IdmUserLdap ldapBean = idmUserDao.searchEntry(p.getUserId());
				if (!BaseUtil.isObjNull(ldapBean)) {
					p.setPassword(ldapBean.getUserPassword());
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
	 * @throws Exception
	 */
	@PostMapping(value = "/activateNewRegisteredUser", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile activateNewRegisteredUser(@Valid @RequestBody UserProfile userProfile,
			HttpServletRequest request, HttpServletResponse response) {
		IdmProfile profile = idmProfileService.findProfileByUserId(userProfile.getUserId());
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

		return profile;
	}

}