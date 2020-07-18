package com.idm.controller;


import java.io.IOException;
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

import org.hibernate.Hibernate;
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

import com.idm.constants.IdmMailTemplateConstants;
import com.idm.constants.IdmTxnCodeConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractRestController;
import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserLdap;
import com.idm.model.IdmMenu;
import com.idm.model.IdmProfile;
import com.idm.model.IdmRole;
import com.idm.model.IdmUserGroup;
import com.idm.model.IdmUserGroupRole;
import com.idm.model.IdmUserGroupRoleGroup;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.idm.service.IdmProfileService;
import com.idm.service.IdmRoleMenuService;
import com.idm.service.IdmRoleService;
import com.idm.service.IdmUserGroupRoleGroupService;
import com.idm.service.IdmUserGroupRoleService;
import com.notify.sdk.constants.NotConfigConstants;
import com.notify.sdk.model.Notification;
import com.notify.sdk.util.MailUtil;
import com.util.BaseUtil;
import com.util.BeanMapperUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


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
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_SVC)
	private IdmUserGroupRoleService idmUserGroupRolesService;

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private IdmUserGroupRoleGroupService idmUserGroupRoleGroupSvc;

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserProfile getProfile(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "embededRole", required = false) boolean embededRole,
			@RequestParam(value = "embededMenu", required = false) boolean embededMenu,
			HttpServletRequest request) throws IOException {

		LOGGER.debug("id : {}", id);
		LOGGER.debug("role : {}", embededRole);

		IdmProfile tidmUserProfile = idmProfileService.findByUserId(id);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, id);

		Hibernate.initialize(tidmUserProfile.getUserRoleGroup());
		Hibernate.initialize(tidmUserProfile.getUserType());

		UserProfile userProfile = JsonUtil.transferToObject(tidmUserProfile, UserProfile.class);
		if (embededRole) {
			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroup().getUserGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();

			boolean isSystem = false;
			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.info(LOG_ROLE_CODE, t.getRole().getRoleCode());
				if(BaseUtil.isEqualsCaseIgnore("SYSTEM", t.getRole().getRoleCode())) {
					isSystem = true;
				}
				roleLst.add(idmRoleService.findByRoleCode(t.getRole().getRoleCode()));
			}
			userProfile.setRoles(JsonUtil.transferToList(roleLst, UserRole.class));

			// Include menu only if user status is Active
			if (BaseConstants.USER_ACTIVE.equalsIgnoreCase(tidmUserProfile.getStatus())) {
				// Retrieve All Menu
				List<UserMenu> menuLst = new ArrayList<>();
				if (embededMenu) {
					String portalType = isSystem ? null : request.getHeader(BaseConstants.HEADER_PORTAL_TYPE);
					menuLst = idmRoleMenuService
							.findRoleMenuByPortalTypeAndGroupRole(portalType, tidmUserProfile.getUserRoleGroup().getUserGroupCode());
				}
				userProfile.setMenus(menuLst);
			}
		}
		return userProfile;
	}


	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmProfile> getProfile() {
		LOGGER.debug("Rest Get getProfile");
		List<IdmProfile> prfLst = idmProfileService.findAll();

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
	 * @throws IOException
	 */
	@GetMapping(value = "/roles", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserProfile> getAllUserByUserRoles(
			@Valid @RequestParam(value = "userRoles", required = true) String userRoles) throws IOException {
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
		return JsonUtil.transferToList(prfLst, UserProfile.class);
	}


	@PostMapping(value = "/search", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> searchUserProfile(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", required = true) Boolean isEmail, HttpServletRequest request)
			throws IOException {
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
		return JsonUtil.transferToList(prfLst, UserProfile.class);
	}


	@GetMapping(value = "/usernamelist", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> userProfileListByUsernameList(@RequestParam("usernames") String usernameList)
			throws IOException {
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
		return JsonUtil.transferToList(prfLst, UserProfile.class);
	}


	@PostMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile update(@RequestParam(value = "userId", required = true) String id,
			@Valid @RequestBody UserProfile tp, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (BaseUtil.isObjNull(tp)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		IdmProfile tidmUserProfile = JsonUtil.transferToObject(tp, IdmProfile.class);
		if (!BaseUtil.isObjNull(tp.getUserRoleGroupCode())) {
			IdmUserGroup userRoleGroup = new IdmUserGroup();
			userRoleGroup.setUserGroupCode(tp.getUserRoleGroupCode());
			tidmUserProfile.setUserRoleGroup(userRoleGroup);
		}

		return updateProfile(id, tidmUserProfile, request);
	}


	private UserProfile updateProfile(String id, IdmProfile tp, HttpServletRequest request) throws IOException {
		LOGGER.info("updateProfile id: {}", id);
		boolean isUpdateLdap = false;
		IdmProfile tidmUserProfile = idmProfileService.findByUserId(id);

		if (tidmUserProfile == null) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM130);
		}

		int userProfId = tidmUserProfile.getUserProfId();
		try {
			BeanMapperUtil.map(tp, tidmUserProfile, true);
			tidmUserProfile.setUserProfId(userProfId);
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

		try {
			boolean isUpdate = idmProfileService.updateUser(updateEmail, tidmUserProfile, isUpdateLdap);

			if (BaseUtil.isEquals(tidmUserProfile.getStatus(), BaseConstants.STATUS_INACTIVE) && isUpdate) {
				// set email id
				String emailAdd = null;
				IdmProfile addIdm = getEmail(tidmUserProfile, id);
				if (!BaseUtil.isObjNull(addIdm)) {
					emailAdd = addIdm.getEmail();
				}

				Map<String, Object> map = new HashMap<>();
				map.put("genDate",
						new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_MS).format(new Date()));
				map.put("name", tp.getFirstName());
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
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_PROF_UPD);
		return JsonUtil.transferToObject(tp, UserProfile.class);
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
	 * @throws IOException
	 */
	@GetMapping(value = IdmUrlConstants.UPDATE_IDM_CONTACT_NUM_EMAIL_ID, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserProfile updateIDMProfileContactNumEmailId(@RequestParam String id, @RequestParam String emailId,
			@RequestParam String contactNum, HttpServletRequest request) throws IOException {
		boolean isLdapUpdated = false;
		boolean isprofileUpdated = false;

		IdmProfile tidmUserProfile = idmProfileService.findByUserId(id);

		IdmUserLdap ldap = idmUserDao.searchEntry(id);

		if (!BaseUtil.isEqualsCaseIgnore(ldap.getEmail(), emailId)) {
			ldap.setEmail(emailId);
			isLdapUpdated = idmUserDao.updateMail(ldap);
			if (!isLdapUpdated) {
				throw new IdmException(IdmErrorCodeEnum.I404IDM159);
			}
		}

		if (!BaseUtil.isObjNull(tidmUserProfile.getContactNo())
				&& !tidmUserProfile.getContactNo().equals(contactNum)) {
			tidmUserProfile.setContactNo(contactNum);
			isprofileUpdated = idmProfileService.updateUser(ldap, tidmUserProfile, false);
			getEmail(tidmUserProfile, id);
			if (!isprofileUpdated) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}
		}

		return JsonUtil.transferToObject(tidmUserProfile, UserProfile.class);
	}


	/**
	 * Search All Users with Pagination
	 * 
	 * @param userProfile
	 * @param isEmail
	 * @param request
	 * @return
	 */
	@PostMapping(value = IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<UserProfile> searchUserProfilePaginated(@Valid @RequestBody UserProfile userProfile,
			@Valid @RequestParam(value = "isEmail", defaultValue = "false") boolean isEmail,
			HttpServletRequest request) {
		if(!BaseUtil.isListNullZero(userProfile.getUserRoleGroupCodeList())) {
			List<IdmUserGroupRoleGroup> ugrgLst = idmUserGroupRoleGroupSvc.findUserGroupByParentRoleGroups(userProfile.getUserRoleGroupCodeList());
			if(!BaseUtil.isListNullZero(ugrgLst)) {			
				List<String> strLst = new ArrayList<>();
				for(IdmUserGroupRoleGroup ugrg: ugrgLst) {
					strLst.add(ugrg.getUserGroupCode());
				}
				userProfile.setUserRoleGroupCodeList(strLst);
			}
		}
		String systemType = getSystemHeader(request);
		if(!BaseUtil.isObjNull(systemType)) {
			userProfile.setSystemType(systemType);
		}
		DataTableRequest<UserProfile> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<UserProfile> all = idmProfileService.searchByPagination(userProfile, null);
		List<UserProfile> filtered = idmProfileService.searchByPagination(userProfile, dataTableInRQ);
		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}


	@PostMapping(value = "/deactivate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> deactivateUser(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
		return JsonUtil.transferToList(newProfLst, UserProfile.class);
	}


	/**
	 * Activate User by Role
	 *
	 * @param userProfile
	 * @param request
	 * @param response
	 * @return @
	 * @throws IOException
	 */
	@PostMapping(value = "/activate", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<UserProfile> activateUserByRole(@Valid @RequestBody UserProfile userProfile,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (BaseUtil.isObjNull(userProfile)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		List<IdmProfile> profileLst = idmProfileService.searchProfile(userProfile);
		List<IdmProfile> upList = new ArrayList<>();

		if (!BaseUtil.isListNullZero(profileLst)) {
			for (IdmProfile profile : profileLst) {
				try {
					upList.add(JsonUtil.transferToObject(profile, IdmProfile.class));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				map.put("name", profile.getFirstName().concat(" ").concat(profile.getLastName()));
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
		return JsonUtil.transferToList(upList, UserProfile.class);
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/profId", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserProfile getProfile(@RequestParam(value = "profId", required = true) Integer profId,
			@RequestParam(value = "embededRole", required = false) String embededRole,
			@RequestParam(value = "embededMenu", required = false) String embededMenu) throws IOException {

		LOGGER.debug("profId : {}", profId);

		IdmProfile tidmUserProfile = idmProfileService.findProfileByProfId(profId);

		if (tidmUserProfile == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.E404IDM140);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		// set email id
		getEmail(tidmUserProfile, tidmUserProfile.getUserId());
		
		UserProfile result = JsonUtil.transferToObject(tidmUserProfile, UserProfile.class);

		if (embededRole != null && embededRole.equalsIgnoreCase("true")) {
			Hibernate.initialize(tidmUserProfile.getUserRoleGroup());
			
			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroup().getUserGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();
			List<UserMenu> menuLst = new ArrayList<>();

			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.debug(LOG_ROLE_CODE, t.getRole().getRoleCode());
				roleLst.add(idmRoleService.findByRoleCode(t.getRole().getRoleCode()));
			}

			tidmUserProfile.setRoles(roleLst);
			result.setRoles(JsonUtil.transferToList(JsonUtil.toJsonNode(roleLst), UserRole.class));
			if (embededMenu != null && embededMenu.equalsIgnoreCase("true")) {
				menuLst = idmRoleMenuService
						.findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroup().getUserGroupCode());
				result.setMenus(menuLst);
			}
		}

		return result;
	}


	@GetMapping(value = IdmUrlConstants.PROFILE_ID_BY_USER_TYPE, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserProfile getProfile(@RequestParam(value = "profId", required = true) Integer profId,
			@RequestParam(value = "embededRole", required = false) boolean embededRole,
			@RequestParam(value = "embededMenu", required = false) boolean embededMenu,
			@RequestParam(value = "userType", required = false) String userType) throws IOException {

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

		if (embededRole) {
			Hibernate.initialize(tidmUserProfile.getUserRoleGroup());

			List<IdmUserGroupRole> userGroupRolesList = idmUserGroupRolesService
					.findUserGroupByUserRoleGroupCode(tidmUserProfile.getUserRoleGroup().getUserGroupCode());

			List<IdmRole> roleLst = new ArrayList<>();
			List<IdmMenu> menuLst = new ArrayList<>();

			// Retrieve ALL Roles of user
			for (IdmUserGroupRole t : userGroupRolesList) {
				LOGGER.debug(LOG_ROLE_CODE, t.getRole().getRoleCode());
				roleLst.add(idmRoleService.findByRoleCode(t.getRole().getRoleCode()));
			}

			/*
			 * TODO: if (embededMenu) { menuLst = idmRoleMenuService
			 * .findRoleMenuByGroupRole(tidmUserProfile.getUserRoleGroup().
			 * getUserGroupCode()); }
			 */
			tidmUserProfile.setRoles(roleLst);
			tidmUserProfile.setMenus(menuLst);
		}

		return JsonUtil.transferToObject(tidmUserProfile, UserProfile.class);
	}


	@GetMapping(value = IdmUrlConstants.PROFILE_BY_USER_TYPE_ROLE_GRP, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserProfile> getProfileByUserTypeRoleGrp(
			@RequestParam(value = "userType", required = true) String userType,
			@RequestParam(value = "userRoleGroupCode", required = true) String userRoleGroupCode,
			@RequestParam(value = "status", required = true) String status) throws IOException {
		List<IdmProfile> result = idmProfileService.findByStatusAndUserTypeUserTypeCodeAndUserRoleGroupUserGroupCode(
				userType, userRoleGroupCode, status);
		return JsonUtil.transferToList(result, UserProfile.class);
	}

}
