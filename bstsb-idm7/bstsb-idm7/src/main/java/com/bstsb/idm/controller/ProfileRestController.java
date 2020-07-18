/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.constants.IdmMailTemplateConstants;
import com.bstsb.idm.constants.IdmTxnCodeConstants;
import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.ldap.IdmUserLdap;
import com.bstsb.idm.model.IdmMenu;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.model.IdmRole;
import com.bstsb.idm.model.IdmSocial;
import com.bstsb.idm.model.IdmUserGroupRole;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.SocialUserDto;
import com.bstsb.idm.sdk.model.UserProfile;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.service.IdmRoleMenuService;
import com.bstsb.idm.service.IdmRoleService;
import com.bstsb.idm.service.IdmSocialService;
import com.bstsb.idm.service.IdmUserGroupRoleService;
import com.bstsb.notify.sdk.constants.NotConfigConstants;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.notify.sdk.util.MailUtil;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.BeanMapperUtil;
import com.bstsb.util.MediaType;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.pagination.DataTableRequest;
import com.bstsb.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.PROFILE)
public class ProfileRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileRestController.class);

	private static final String LOG_ROLE_CODE = "Role Code: {}";

	@Autowired
	@Qualifier(QualifierConstants.IDM_ROLE_SERVICE)
	private IdmRoleService idmRoleService;

	@Autowired
	private IdmRoleMenuService idmRoleMenuService;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
	private IdmUserGroupRoleService idmUserGroupRolesService;

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmSocialService idmSocialSvc;


	@GetMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserProfile getProfile(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu) {

		UserProfile userProfile = null;

		LOGGER.debug("id : {}", id);
		LOGGER.debug("role : {}", embededRole);

		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, id);

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();
			List<IdmMenu> menuLst = new ArrayList<>();

			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.debug(LOG_ROLE_CODE, t.getRoleCode());
				roleLst.add(idmRoleService.findUserRoleByRoleCode(t.getRoleCode()));
			}

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}
			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		userProfile = dozerMapper.map(tidmUserProfile, UserProfile.class);

		if (!BaseUtil.isObjNull(userProfile)) {
			IdmSocial social = idmSocialSvc.findSocialUserByUserId(userProfile.getUserId());
			if (!BaseUtil.isObjNull(social)) {
				SocialUserDto socialUser = dozerMapper.map(social, SocialUserDto.class);
				userProfile.setSocialUser(socialUser);
			}
		}

		return userProfile;
	}


	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
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
	@GetMapping(value = "/roles", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> getAllUserByUserRoles(
			@Valid @RequestParam(value = "userRoles", required = true) String userRoles) {
		LOGGER.debug("Calling get all user by user roles");
		List<IdmProfile> prfLst = idmProfileService.findAllUsersByRoleCodes(userRoles);
		if (BaseUtil.isListNullZero(prfLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		// Set Email
		for (IdmProfile o : prfLst) {
			getEmail(o, o.getUserId());
		}
		return prfLst;
	}


	@PostMapping(value = "/search", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmProfile> searchUserProfile(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", required = true) Boolean isEmail, HttpServletRequest request) {
		LOGGER.debug("Rest Get getProfile: {}", userProfile.getUserType());
		List<IdmProfile> prfLst = idmProfileService.searchUserProfile(userProfile);
		if (BaseUtil.isListNullZero(prfLst)) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
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


	@GetMapping(value = "/usernamelist", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
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


	@PostMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile update(@RequestParam(value = "userId", required = true) String id,
			@Valid @RequestBody UserProfile tp, HttpServletRequest request, HttpServletResponse response) {
		if (BaseUtil.isObjNull(tp)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		IdmProfile tidmUserProfile = dozerMapper.map(tp, IdmProfile.class);
		return updateProfile(id, tidmUserProfile, request);
	}


	private IdmProfile updateProfile(String id, IdmProfile tp, HttpServletRequest request) {
		LOGGER.debug("updateProfile id: {}", id);
		boolean isUpdateLdap = false;
		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		if (tidmUserProfile == null) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}

		try {
			BeanMapperUtil.map(tp, tidmUserProfile, true);
		} catch (Exception e) {
			LOGGER.error("{}", e);
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
			notification.setMetaData(MailUtil.convertMapToJson(map));
			getNotifyService().addNotification(notification, IdmMailTemplateConstants.IDM_USER_DEACTIVATE);
		}

		try {
			idmProfileService.updateUser(updateEmail, tidmUserProfile, isUpdateLdap);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_PROF_UPD);
		return tp;
	}


	public IdmProfile getEmail(IdmProfile tidmUserProfile, String id) {
		IdmUserLdap ldapEntryBean = idmUserDao.searchEntry(id);
		if (!BaseUtil.isObjNull(ldapEntryBean)) {
			tidmUserProfile.setEmail(ldapEntryBean.getEmail());
		}
		return tidmUserProfile;
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
	@GetMapping(value = IdmUrlConstants.UPDATE_IDM_CONTACT_NUM_EMAIL_ID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmProfile updateIDMProfileContactNumEmailId(@RequestParam String id, @RequestParam String emailId,
			@RequestParam String contactNum, HttpServletRequest request) {
		boolean isLdapUpdated = false;
		boolean isprofileUpdated = false;

		IdmProfile tidmUserProfile = idmProfileService.findProfileByUserId(id);

		IdmUserLdap ldap = idmUserDao.searchEntry(id);

		if (!BaseUtil.isEqualsCaseIgnore(ldap.getEmail(), emailId)) {
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
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}
		}

		return tidmUserProfile;
	}


	@PostMapping(value = IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<IdmProfile> searchUserProfilePaginated(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", defaultValue = "false") boolean isEmail,
			HttpServletRequest request) {
		DataTableRequest<IdmProfile> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<IdmProfile> result = idmProfileService.searchUserProfile(userProfile, dataTableInRQ);
		DataTableResults<IdmProfile> dataTableInResp = new DataTableResults<>();
		if (!BaseUtil.isObjNull(result)) {
			dataTableInResp.setRecordsFiltered(result.getRecordsFiltered());
			dataTableInResp.setRecordsTotal(result.getRecordsTotal());
			if (!BaseUtil.isListNullZero(result.getData())) {
				List<IdmProfile> bpLst = new ArrayList<>();
				for (IdmProfile bbp : result.getData()) {
					IdmProfile idmProfile = dozerMapper.map(bbp, IdmProfile.class);
					if (isEmail) {
						getEmail(bbp, bbp.getUserId());
					}
					bpLst.add(idmProfile);
				}
				dataTableInResp.setData(bpLst);
			}
		}
		return dataTableInResp;
	}


	@PostMapping(value = "/deactivate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
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
					updateProfile(prof.getUserId(), prof, request);
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
	 * @return @
	 */
	@PostMapping(value = "/activate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
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

				String pword = super.uidGenerator.getGeneratedPwd();

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
				notification.setMetaData(MailUtil.convertMapToJson(map));
				getNotifyService().addNotification(notification, IdmMailTemplateConstants.IDM_USER_ACTIVATE);
			}
		}

		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ACTIVATE_USER_ROLE);
		return upList;
	}


	@GetMapping(value = "/profId", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmProfile getProfile(@RequestParam(value = "profId", required = true) Integer profId,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu) {

		LOGGER.debug("profId : {}", profId);

		IdmProfile tidmUserProfile = idmProfileService.findProfileByProfId(profId);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, tidmUserProfile.getUserId());

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();
			List<IdmMenu> menuLst = new ArrayList<>();

			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.debug(LOG_ROLE_CODE, t.getRoleCode());
				roleLst.add(idmRoleService.findUserRoleByRoleCode(t.getRoleCode()));
			}

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}
			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		return dozerMapper.map(tidmUserProfile, IdmProfile.class);
	}


	@GetMapping(value = IdmUrlConstants.PROFILE_ID_BY_USER_TYPE, consumes = {
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
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, tidmUserProfile.getUserId());

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();
			List<IdmMenu> menuLst = new ArrayList<>();

			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.debug(LOG_ROLE_CODE, t.getRoleCode());
				roleLst.add(idmRoleService.findUserRoleByRoleCode(t.getRoleCode()));
			}

			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroupCode());
			}
			tidmUserProfile.setRolesList(roleLst);
			tidmUserProfile.setMenusList(menuLst);
		}

		return dozerMapper.map(tidmUserProfile, IdmProfile.class);
	}


	@GetMapping(value = IdmUrlConstants.PROFILE_BY_USER_TYPE_ROLE_GRP, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> getProfileByUserTypeRoleGrp(
			@RequestParam(value = "userType", required = true) String userType,
			@RequestParam(value = "userRoleGroupCode", required = true) String userRoleGroupCode,
			@RequestParam(value = "status", required = true) String status) {
		return idmProfileService.findProfileByUserTypeRoleGrp(userType, userRoleGroupCode, status);
	}

}
