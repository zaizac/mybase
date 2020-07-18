/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.ldap.IdmUserDao;
import com.baseboot.idm.ldap.IdmUserLdap;
import com.baseboot.idm.model.IdmMenu;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.model.IdmRole;
import com.baseboot.idm.model.IdmUserGroupRole;
import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.pagination.DataTableRequest;
import com.baseboot.idm.sdk.pagination.DataTableResults;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.service.IdmRoleMenuService;
import com.baseboot.idm.service.IdmRoleService;
import com.baseboot.idm.service.IdmUserGroupRoleService;
import com.baseboot.idm.util.UidGenerator;
import com.baseboot.notify.sdk.constants.MailTemplate;
import com.baseboot.notify.sdk.constants.MailTypeEnum;
import com.baseboot.notify.sdk.constants.NotConfigConstants;
import com.baseboot.notify.sdk.model.Notification;
import com.baseboot.notify.sdk.util.MailUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.PROFILE)
public class ProfileRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileRestController.class);

	@Autowired
	protected IdmProfileService idmProfileService;

	@Autowired
	private UidGenerator uidGenerator;

	@Autowired
	@Qualifier(QualifierConstants.IDMROLE_SERVICE)
	private IdmRoleService idmRoleService;

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
	private IdmUserGroupRoleService idmUserGroupRolesService;

	@Lazy
	@Autowired
	private IdmUserDao idmUserDao;


	@RequestMapping(value = "/user", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile getProfile(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("id : {}", id);
			LOGGER.debug("role : {}", embededRole);
		}

		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, id);

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmRole> roleLst = getRoles(tidmUserProfile.getUserRoleGroupCode());
			List<IdmMenu> menuLst = new ArrayList<>();

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}

			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		return dozerMapper.map(tidmUserProfile, IdmProfile.class);
	}


	@RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmProfile> getProfile() {
		LOGGER.debug("Rest Get getProfile");
		List<IdmProfile> prfLst = idmProfileService.findAllProfiles();

		// Set Email
		for (IdmProfile o : prfLst) {
			getEmail(o, o.getUserId());
		}

		return prfLst;
	}


	/**
	 * Fetch All User by roles
	 *
	 * @param userRoles
	 * @return
	 */
	@RequestMapping(value = "/roles", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> getAllUserByUserRoles(
			@Valid @RequestParam(value = "userRoles", required = true) String userRoles) {
		LOGGER.debug("Calling get all user by user roles");
		List<IdmProfile> prfLst = idmProfileService.findAllUsersByRoleCodes(userRoles);
		if (BaseUtil.isListNullZero(prfLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}
		// Set Email
		for (IdmProfile o : prfLst) {
			getEmail(o, o.getUserId());
		}
		return prfLst;
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> searchUserProfile(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", required = true) Boolean isEmail, HttpServletRequest request) {
		LOGGER.debug("Rest Get getProfile: {}", userProfile.getUserType());
		List<IdmProfile> prfLst = idmProfileService.searchUserProfile(userProfile);
		if (BaseUtil.isListNullZero(prfLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}
		if (isEmail) {
			// Set Email
			for (IdmProfile o : prfLst) {
				getEmail(o, o.getUserId());

			}
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_PROF_FIND);
		return prfLst;
	}


	@RequestMapping(value = "/usernamelist", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> userProfileListByUsernameList(@RequestParam("usernames") String usernameList) {
		LOGGER.debug("Rest Get getProfile List by username List: {}", usernameList);
		if (!StringUtils.hasText(usernameList)) {
			return new ArrayList<>();
		}
		List<IdmProfile> prfLst = idmProfileService.userProfileListByUsernameList(usernameList);
		if (BaseUtil.isListNullZero(prfLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}
		return prfLst;
	}


	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile updateProfile(@RequestParam(value = "id", required = true) String id,
			@Valid @RequestBody IdmProfile tp, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("updateProfile id: {}", id);
		boolean isUpdateLdap = false;
		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		if (tidmUserProfile == null) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}

		if (!BaseUtil.isObjNull(tp.getFullName())) {
			tidmUserProfile.setFullName(tp.getFullName());
		}

		if (tp.getDob() != null) {
			tidmUserProfile.setDob(tp.getDob());
		}

		if (!BaseUtil.isObjNull(tp.getGender())) {
			tidmUserProfile.setGender(tp.getGender());
		}

		if (!BaseUtil.isObjNull(tp.getStatus())) {
			tidmUserProfile.setStatus(tp.getStatus());
		}

		if (!BaseUtil.isObjNull(tp.getMobPhoneNo())) {
			tidmUserProfile.setMobPhoneNo(tp.getMobPhoneNo());
		}

		if (!BaseUtil.isObjNull(tp.getProfId())) {
			tidmUserProfile.setProfId(tp.getProfId());
		}

		if (!BaseUtil.isObjNull(tp.getBranchId())) {
			tidmUserProfile.setBranchId(tp.getBranchId());
		}

		if (!BaseUtil.isObjNull(tp.getUserRoleGroupCode())) {
			tidmUserProfile.setUserRoleGroupCode(tp.getUserRoleGroupCode());
		}

		if (!BaseUtil.isObjNull(tp.getCntry())) {
			tidmUserProfile.setCntry(tp.getCntry());
		}

		if (!BaseUtil.isObjNull(tp.getDocMgtId())) {
			tidmUserProfile.setDocMgtId(tp.getDocMgtId());
		}

		tidmUserProfile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		tidmUserProfile.setUpdateDt(new Timestamp(new Date().getTime()));

		IdmUserLdap updateEmail = new IdmUserLdap();

		if (!BaseUtil.isObjNull(tp.getEmail())) {
			updateEmail.setUserName(tp.getUserId());
			updateEmail.setEmail(tp.getEmail());
			isUpdateLdap = true;
		}

		if (BaseUtil.isEquals(tidmUserProfile.getStatus(), BaseConstants.STATUS_INACTIVE)) {
			// set email id
			String emailAdd = null;
			IdmProfile addIdm = getEmail(tidmUserProfile, id);
			if (!BaseUtil.isObjNull(addIdm)) {
				emailAdd = addIdm.getEmail();
			}

			Map<String, Object> map = new HashMap<>();
			map.put("genDate", new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
			map.put("name", tp.getFullName());
			map.put("loginName", tp.getUserId());
			map.put("email", emailAdd);

			Map<String, String> notConfig = getNotifyService().findAllConfig();

			String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
			String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
			String loginUrl = notConfig.get(NotConfigConstants.PORTAL_URL);
			map.put("contactPhone", contactPhone);
			map.put("contactEmail", contactEmail);
			map.put("fwcmsUrl", loginUrl);
			Notification notification = new Notification();
			notification.setNotifyTo(emailAdd);
			notification.setSubject(MailTemplate.IDM_USER_DEACTIVATE.getSubject());
			notification.setMetaData(MailUtil.convertMapToJson(map));
			getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.IDM_USER_DEACTIVATE);
		}

		try {
			idmProfileService.updateUser(updateEmail, tidmUserProfile, isUpdateLdap);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_PROF_UPD);
		return tp;
	}


	/**
	 * update employer email id in ldap and contact number in idm_profile if
	 * they are different from existing one. if not exist in idm profile or in
	 * ldap then simply skip
	 *
	 * @param id
	 * @param emailId
	 * @param contactNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value = IdmUrlConstrants.UPDATE_IDM_CONTACT_NUM_EMAIL_ID, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile updateIDMProfileContactNumEmailId(@RequestParam String id, @RequestParam String emailId,
			@RequestParam String contactNum, HttpServletRequest request) {
		boolean isLdapUpdated = false;
		boolean isprofileUpdated = false;

		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		IdmUserLdap ldap = idmUserDao.searchEntry(id);

		if (ldap != null && !BaseUtil.isEqualsCaseIgnore(ldap.getEmail(), emailId)) {
			ldap.setEmail(emailId);
			isLdapUpdated = idmUserDao.updateMail(ldap);
			if (!isLdapUpdated) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM159);
			}
		}

		if (!BaseUtil.isObjNull(tidmUserProfile.getMobPhoneNo())
				&& !tidmUserProfile.getMobPhoneNo().equals(contactNum)) {
			tidmUserProfile.setMobPhoneNo(contactNum);
			isprofileUpdated = idmProfileService.updateUser(ldap, tidmUserProfile, false);
			getEmail(tidmUserProfile, id);
			if (!isprofileUpdated) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
				LOGGER.error(idmEx.getMessage());
				throw idmEx;
			}
		}

		return tidmUserProfile;
	}


	@RequestMapping(value = IdmUrlConstrants.SEARCH_PAGINATION, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public DataTableResults<IdmProfile> searchUserProfilePaginated(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", required = true) Boolean isEmail, HttpServletRequest request) {
		DataTableRequest<IdmProfile> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<IdmProfile> result = idmProfileService.searchUserProfile(userProfile, dataTableInRQ);
		DataTableResults<IdmProfile> dataTableInResp = new DataTableResults<>();
		if (!BaseUtil.isObjNull(result)) {
			dataTableInResp.setRecordsFiltered(result.getRecordsFiltered());
			dataTableInResp.setRecordsTotal(result.getRecordsTotal());
			if (!BaseUtil.isListNullZero(result.getData())) {
				List<IdmProfile> bpLst = new ArrayList<>();
				for (IdmProfile bbp : result.getData()) {
					IdmProfile trustee = dozerMapper.map(bbp, IdmProfile.class);
					if (isEmail) {
						getEmail(bbp, bbp.getUserId());
					}
					bpLst.add(trustee);
				}
				dataTableInResp.setData(bpLst);
			}
		}
		return dataTableInResp;
	}


	@RequestMapping(value = "/deactivate", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> deactivateUser(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) {
		if (BaseUtil.isObjNull(userProfile)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		// Only Active Users to be Deactivated
		List<IdmProfile> profLst = idmProfileService.searchProfile(userProfile);
		List<IdmProfile> newProfLst = new ArrayList<>();
		if (!BaseUtil.isObjNull(profLst)) {
			Iterator<IdmProfile> iterator = profLst.iterator();
			while (iterator.hasNext()) {
				IdmProfile prof = iterator.next();
				if (!BaseUtil.isEqualsCaseIgnore(BaseConstants.STATUS_INACTIVE, prof.getStatus())) {
					prof.setStatus(BaseConstants.STATUS_INACTIVE);
					updateProfile(prof.getUserId(), prof, request, response);
					newProfLst.add(prof);
				}
			}
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_DEACTIVATE_USER);
		return newProfLst;
	}


	/**
	 * Activate User by Role
	 *
	 * @param userProfile
	 * @param request
	 * @param response
	 * @return
	 * @throws IdmException
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> activateUserByRole(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) {
		if (BaseUtil.isObjNull(userProfile)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		List<IdmProfile> profileLst = idmProfileService.searchProfile(userProfile);
		List<IdmProfile> upList = new ArrayList<>();

		if (!BaseUtil.isListNullZero(profileLst)) {
			for (IdmProfile profile : profileLst) {
				upList.add(dozerMapper.map(profile, IdmProfile.class));
				// get mail id from Ldap
				IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(profile.getUserId());
				if (BaseUtil.isObjNull(ldapEntryBean) || BaseUtil.isObjNull(ldapEntryBean.getEmail())) {
					throw new IdmException(IdmErrorCodeEnum.I400IDM122, new String[] { "Email", "Password" });
				}

				String pword = uidGenerator.getGeneratedPwd();

				IdmUserLdap updatePwd = new IdmUserLdap();
				updatePwd.setUserName(profile.getUserId());
				updatePwd.setUserPassword(pword);

				profile.setStatus(BaseConstants.USER_FIRST_TIME);
				profile.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
				boolean isSuccess = idmProfileService.updateUserForForgotPwd(updatePwd, profile);
				if (!isSuccess) {
					throw new IdmException(IdmErrorCodeEnum.I404IDM159);
				}

				Map<String, Object> map = new HashMap<>();
				map.put("genDate",
						new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
				map.put("name", profile.getFullName());
				map.put("loginName", profile.getUserId());
				map.put("password", pword);

				Map<String, String> notConfig = getNotifyService().findAllConfig();

				String contactPhone = notConfig.get(NotConfigConstants.CUST_CARE_CONTACT);
				String contactEmail = notConfig.get(NotConfigConstants.CUST_CARE_EMAIL);
				String loginUrl = notConfig.get(NotConfigConstants.PORTAL_URL);
				map.put("contactPhone", contactPhone);
				map.put("contactEmail", contactEmail);
				map.put("fwcmsUrl", loginUrl);
				Notification notification = new Notification();
				notification.setNotifyTo(ldapEntryBean.getEmail());
				notification.setSubject(MailTemplate.IDM_USER_ACTIVATE.getSubject());
				notification.setMetaData(MailUtil.convertMapToJson(map));
				getNotifyService().addNotification(notification, MailTypeEnum.MAIL, MailTemplate.IDM_FORGOT_PWORD);
			}
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ACTIVATE_USER_ROLE);
		return upList;
	}


	@RequestMapping(value = "/profId", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile getProfile(@RequestParam(value = "profId", required = true) Integer profId,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu) {

		LOGGER.debug("profId : {}", profId);

		IdmProfile tidmUserProfile = idmProfileService.findProfileByProfId(profId);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, tidmUserProfile.getUserId());

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmRole> roleLst = getRoles(tidmUserProfile.getUserRoleGroupCode());
			List<IdmMenu> menuLst = new ArrayList<>();

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}
			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		return dozerMapper.map(tidmUserProfile, IdmProfile.class);
	}


	@RequestMapping(value = "/profIdUserType", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile getProfile(@RequestParam(value = "profId", required = true) Integer profId,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu,
			@RequestParam(value = "userType", required = false) String userType) {

		LOGGER.debug("profId : {}", profId);
		IdmProfile tidmUserProfile = null;
		if (!BaseUtil.isObjNull(userType)) {
			tidmUserProfile = idmProfileService.findUserProfileByProfIdAndUserType(profId, userType);
		} else {
			tidmUserProfile = idmProfileService.findProfileByProfId(profId);
		}

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, tidmUserProfile.getUserId());

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmRole> roleLst = getRoles(tidmUserProfile.getUserRoleGroupCode());
			List<IdmMenu> menuLst = new ArrayList<>();

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}

			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		return dozerMapper.map(tidmUserProfile, IdmProfile.class);
	}


	private IdmProfile getEmail(IdmProfile tidmUserProfile, String id) {
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(id);
		if (ldapEntryBean != null) {
			tidmUserProfile.setEmail(ldapEntryBean.getEmail());
		}
		return tidmUserProfile;
	}


	private List<IdmRole> getRoles(String userRoleGroupCode) {
		List<IdmRole> roleLst = new ArrayList<>();
		List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
				.findUserGroupByUserRoleGroupCode(userRoleGroupCode);

		// Retrieve ALL Roles of user
		for (IdmUserGroupRole t : userGroupRolesList) {
			LOGGER.debug("Role Code: {}", t.getRoleCode());
			roleLst.add(idmRoleService.findUserRoleByRoleCode(t.getRoleCode()));
		}
		return roleLst;
	}

}
