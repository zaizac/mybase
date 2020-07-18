/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.client;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstsb.idm.sdk.builder.MobileBuilder;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.AssignRole;
import com.bstsb.idm.sdk.model.AuditAction;
import com.bstsb.idm.sdk.model.ChangePassword;
import com.bstsb.idm.sdk.model.ForgotPassword;
import com.bstsb.idm.sdk.model.IdmConfigDto;
import com.bstsb.idm.sdk.model.LoginDto;
import com.bstsb.idm.sdk.model.MenuDto;
import com.bstsb.idm.sdk.model.SocialUserDto;
import com.bstsb.idm.sdk.model.Token;
import com.bstsb.idm.sdk.model.UserGroup;
import com.bstsb.idm.sdk.model.UserGroupRole;
import com.bstsb.idm.sdk.model.UserGroupRoleBranch;
import com.bstsb.idm.sdk.model.UserMenu;
import com.bstsb.idm.sdk.model.UserProfile;
import com.bstsb.idm.sdk.model.UserRole;
import com.bstsb.idm.sdk.model.UserType;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.UriUtil;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.http.HttpAuthClient;
import com.bstsb.util.model.MessageResponse;
import com.bstsb.util.model.ServiceCheck;
import com.bstsb.util.pagination.DataTableResults;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmServiceClient.class);

	private static IdmRestTemplate restTemplate;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;

	private int readTimeout;


	public IdmServiceClient(String url) {
		this.url = url;
	}


	public IdmServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
	}


	public IdmServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
	}


	static {
		restTemplate = new IdmRestTemplate("IDM");
	}


	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.SERVICE_CHECK + "/test"), String.class);
	}


	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	public boolean evict() {
		return evict(null);
	}


	public boolean evict(String prefixKey) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(IdmUrlConstants.REFERENCE).append("/evict");
		if (!BaseUtil.isObjNull(prefixKey)) {
			sbUrl.append("?prefixKey=" + prefixKey);
		}
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	public SocialService social() {
		return new SocialService(getRestTemplate(), url);
	}


	/**
	 * Get User Profile
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> getUserProfile() {
		List<UserProfile> svcResp = null;
		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.PROFILE),
				UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			svcResp = Arrays.asList(resp);
		}
		return svcResp;
	}


	/**
	 * Search User Profile
	 *
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public List<UserProfile> searchUserProfile(UserProfile userProfile) {
		return searchUserProfile(userProfile, true);
	}


	public List<UserProfile> searchUserProfile(UserProfile userProfile, boolean isEmail) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/search");
		sb.append("?isEmail=" + isEmail);
		List<UserProfile> result = null;
		UserProfile[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), userProfile,
				UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Search User Profile by pagination
	 *
	 * @param userProfile
	 * @param isEmail
	 * @param pagination
	 * @return
	 * @throws IdmException
	 */
	@SuppressWarnings("unchecked")
	public DataTableResults<UserProfile> searchUserProfile(UserProfile userProfile, boolean isEmail,
			Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append(IdmUrlConstants.SEARCH_PAGINATION);
		pagination.put("isEmail", isEmail);
		return getRestTemplate().postForObject(getServiceURI(sb.toString(), pagination), userProfile,
				DataTableResults.class);
	}


	/**
	 * Search User Profiles by list of username
	 *
	 * @param usernameList
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> userProfileListByUsernameList(String usernameList) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/usernamelist");
		sb.append("?usernames={usernames}");

		Map<String, Object> params = new HashMap<>();
		params.put("usernames", usernameList);

		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class,
				params);
		List<UserProfile> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	public Boolean staticPage() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.STATIC), Boolean.class);
	}


	/**
	 * User Login Authentication
	 *
	 * @param loginDto
	 * @return
	 * @throws IdmException
	 */
	public LoginDto login(LoginDto loginDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.LOGIN), loginDto, LoginDto.class);
	}


	/**
	 * User Social Login Authentication
	 *
	 * @param loginDto
	 * @return
	 * @throws IdmException
	 */
	public LoginDto loginSocial(LoginDto loginDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.SOCIAL_LOGIN), loginDto,
				LoginDto.class);
	}


	public boolean logout(LoginDto loginDto) {
		boolean isLogout = false;
		MessageResponse response = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.LOGOUT), loginDto,
				MessageResponse.class);
		if (response != null) {
			isLogout = true;
		}
		return isLogout;
	}


	/**
	 * Force Logout
	 *
	 * @return
	 * @throws IdmException
	 */
	public boolean logout() {
		return logout(null);
	}


	/**
	 * Generate Access Token
	 *
	 * @return
	 * @throws IdmException
	 */
	public Token generatedAccessToken() {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.TOKENS), null, Token.class);
	}


	/**
	 * Validate Access Token
	 *
	 * @param embededRole
	 * @return
	 * @throws IdmException
	 */
	public Token validateAccessToken(boolean embededRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.TOKENS);
		sb.append("/validate");
		sb.append("?embededRole=" + embededRole);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), null, Token.class);
	}


	/**
	 * Delete Access Token
	 *
	 * @return
	 * @throws IdmException
	 */
	public Boolean deleteToken() {
		return getRestTemplate().deleteForObject(getServiceURI(IdmUrlConstants.TOKENS));
	}


	/**
	 * Find All Token by client id
	 *
	 * @param embededUser
	 * @param embededRole
	 * @return
	 * @throws IdmException
	 */
	public List<Token> findAllTokenByClient(boolean embededUser, boolean embededRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.CLIENTS);
		sb.append("?embededUser={embededUser}&embededRole={embededRole}");

		Map<String, Object> params = new HashMap<>();
		params.put("embededUser", embededUser);
		params.put(BaseConstants.EMBED_ROLE, embededRole);

		Token[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), Token[].class, params);
		List<Token> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find Access Token
	 *
	 * @param embededUser
	 * @param embededRole
	 * @return
	 * @throws IdmException
	 */
	public Token findByAccessToken(boolean embededUser, boolean embededRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.TOKENS);
		sb.append("?embededUser={embededUser}&embededRole={embededRole}");

		Map<String, Object> params = new HashMap<>();
		params.put("embededUser", embededUser);
		params.put(BaseConstants.EMBED_ROLE, embededRole);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), Token.class, params);
	}


	/**
	 * Find UserProfile by userId
	 *
	 * @param userId
	 * @param embededRole
	 * @param embededMenu
	 * @return
	 * @throws IdmException
	 */
	public UserProfile getUserProfileById(String userId, boolean embededRole, boolean embededMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/user");
		sb.append("?id={id}&embededRole={embededRole}&embededMenu={embededMenu}");

		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		params.put(BaseConstants.EMBED_ROLE, embededRole);
		params.put(BaseConstants.EMBED_MENU, embededMenu);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile.class, params);
	}


	/**
	 * Find UserProfile by profId
	 *
	 * @param profId
	 * @param embededRole
	 * @param embededMenu
	 * @return
	 * @throws IdmException
	 */
	public UserProfile getUserProfileByProfId(Integer profId, boolean embededRole, boolean embededMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/profId");
		sb.append("?profId={profId}&embededRole={embededRole}&embededMenu={embededMenu}");

		Map<String, Object> params = new HashMap<>();
		params.put("profId", profId);
		params.put(BaseConstants.EMBED_ROLE, embededRole);
		params.put(BaseConstants.EMBED_MENU, embededMenu);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile.class, params);
	}


	/**
	 * Find UserProfile by profId and UserType
	 *
	 * @param profId
	 * @param embededRole
	 * @param embededMenu
	 * @return
	 * @throws IdmException
	 */
	public UserProfile getUserProfileByProfIdUserType(Integer profId, String userType, boolean embededRole,
			boolean embededMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append(IdmUrlConstants.PROFILE_ID_BY_USER_TYPE);
		sb.append("?profId={profId}&userType={userType}&embededRole={embededRole}&embededMenu={embededMenu}");

		Map<String, Object> params = new HashMap<>();
		params.put("profId", profId);
		params.put(BaseConstants.EMBED_ROLE, embededRole);
		params.put(BaseConstants.EMBED_MENU, embededMenu);
		params.put("userType", userType);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile.class, params);
	}


	public List<UserProfile> getProfileByUserTypeRoleGrp(String userType, String userRoleGroupCode, String status) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append(IdmUrlConstants.PROFILE_BY_USER_TYPE_ROLE_GRP);
		sb.append("?userType={userType}&userRoleGroupCode={userRoleGroupCode}&status={status}");

		Map<String, Object> params = new HashMap<>();
		params.put("userType", userType);
		params.put("userRoleGroupCode", userRoleGroupCode);
		params.put("status", status);
		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class,
				params);
		List<UserProfile> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find User Profiles by roles
	 *
	 * @param userRoles
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> getUserProfileByRoles(String userRoles) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/roles?userRoles=");
		sb.append(userRoles);

		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class);
		List<UserProfile> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Create User
	 *
	 * @param up
	 * @return
	 * @throws IdmException
	 */
	public UserProfile createUser(UserProfile up) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USERS), up, UserProfile.class);
	}


	/**
	 * Create User by Fixed ID
	 *
	 * @param up
	 * @param fixedId
	 * @return
	 * @throws IdmException
	 */
	public UserProfile createUser(UserProfile up, boolean fixedId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append("?fixedId={fixedId}");

		Map<String, Object> params = new HashMap<>();
		params.put("fixedId", fixedId);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
	}


	/**
	 * Create User Profile with fixed ID and password
	 *
	 * @param up
	 * @param fixedId
	 * @param fixedPword
	 * @return
	 */
	public UserProfile createUser(UserProfile up, boolean fixedId, boolean fixedPword) {
		return createUser(up, fixedId, fixedPword, false);
	}


	/**
	 * Create User Profile with custom configuration
	 *
	 * call activateRegisteredUser(userId) method to activate the profile
	 *
	 * @param up
	 * @param fixedId
	 * @param fixedPword
	 * @param pendingActivation
	 * @return
	 */
	public UserProfile createUser(UserProfile up, boolean fixedId, boolean fixedPword, boolean pendingActivation) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append("?fixedId={fixedId}");

		Map<String, Object> params = new HashMap<>();
		params.put("fixedId", fixedId);

		if (fixedPword) {
			sb.append("&fixedPword={fixedPword}");
			params.put("fixedPword", fixedPword);
		}

		if (pendingActivation) {
			sb.append("&pendingActivation={pendingActivation}");
			params.put("pendingActivation", pendingActivation);
		}

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
	}


	public SocialUserDto addSocialUser(SocialUserDto userSocial) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ADD_SOCIAL_USER), userSocial,
				SocialUserDto.class);
	}


	/**
	 * Update User Profile
	 *
	 * @param up
	 * @return
	 * @throws IdmException
	 */
	public boolean updateProfile(UserProfile up) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/update");
		sb.append("?userId={userId}");
		Map<String, Object> params = new HashMap<>();
		params.put("userId", up.getUserId());
		getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
		return true;
	}


	/**
	 * Create User Role
	 *
	 * @param role
	 * @return
	 * @throws IdmException
	 */
	public UserRole createRole(UserRole role) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ROLES), role, UserRole.class);
	}


	/**
	 * Update User Role
	 *
	 * @param role
	 * @return
	 * @throws IdmException
	 */
	public boolean updateRoleByRoleCode(UserRole role) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLES);
		sb.append("/");
		sb.append(role.getRoleCode());
		getRestTemplate().postForObject(getServiceURI(sb.toString()), role, UserRole.class);
		return true;
	}


	/**
	 * Assign Role to list of users
	 *
	 * @param roleCode
	 * @param users
	 * @return
	 * @throws IdmException
	 */
	public AssignRole assignRoleToUsers(String roleCode, List<String> users) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLES);
		sb.append("/");
		sb.append(roleCode);
		sb.append("/");
		sb.append("users");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), users, AssignRole.class);
	}


	/**
	 * Assign list of roles to user
	 *
	 * @param userId
	 * @param roles
	 * @return
	 * @throws IdmException
	 */
	public String[] assignUserToRoles(String userId, List<String> roles) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLES);
		sb.append("/users/");
		sb.append("/");
		sb.append(userId);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), roles, String[].class);
	}


	/**
	 * Find User Group by role group
	 *
	 * @param roleGroupCode
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findUserGroupByRoleGroupCode(String roleGroupCode) {
		return findUserGroupByRoleGroupCode(roleGroupCode, false, false);
	}


	public List<UserGroup> findUserGroupByRoleGroupCode(String roleGroupCode, boolean hasMaxNoUserCreated,
			boolean noSystemCreate) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/");
		sb.append(roleGroupCode);
		sb.append("?hasMaxNoUserCreated={hasMaxNoUserCreated}");
		sb.append("&noSystemCreate={noSystemCreate}");

		Map<String, Object> params = new HashMap<>();
		params.put("hasMaxNoUserCreated", hasMaxNoUserCreated);
		params.put("noSystemCreate", noSystemCreate);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class, params);
		List<UserGroup> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find User Group by Parent Role Group
	 *
	 * @param roleGroupCode
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findUserGroupByParentRoleGroup(String roleGroupCode) {
		return findUserGroupByParentRoleGroup(roleGroupCode, false, false);
	}


	public List<UserGroup> findUserGroupByParentRoleGroup(String roleGroupCode, boolean hasMaxNoUserCreated,
			boolean noSystemCreate) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/parent/");
		sb.append(roleGroupCode);
		sb.append("?hasMaxNoUserCreated={hasMaxNoUserCreated}");
		sb.append("&noSystemCreate={noSystemCreate}");

		Map<String, Object> params = new HashMap<>();
		params.put("hasMaxNoUserCreated", hasMaxNoUserCreated);
		params.put("noSystemCreate", noSystemCreate);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class, params);
		List<UserGroup> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find ALL User Group Name
	 *
	 * @return
	 * @throws IdmException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findAllUserGroupName() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/groupname");
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), Map.class);
	}


	/**
	 * Find All User Group
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findAllUserGroup() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/all");

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		List<UserGroup> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find All User Groups
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findAllUserGroups() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/allGroups");

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		List<UserGroup> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find All User Types
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserType> findAllUserTypes() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append("/all");

		UserType[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserType[].class);
		List<UserType> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find User Group by Group code
	 *
	 * @param groupCode
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findUserGroupByGroupCode(String groupCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("?groupCode={groupCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("groupCode", groupCode);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class, params);
		List<UserGroup> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Assign role to list of users
	 *
	 * @param roleCode
	 * @param menusLst
	 * @return
	 * @throws IdmException
	 */
	public AssignRole assignRoleToMenus(String roleCode, List<String> menusLst) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLES);
		sb.append("/");
		sb.append(roleCode);
		sb.append("/");
		sb.append("menus");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), menusLst, AssignRole.class);
	}


	/**
	 * Get User Role by Role Code
	 *
	 * @param roleCode
	 * @return
	 * @throws IdmException
	 */
	public UserRole getRoleByRoleCode(String roleCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLES);
		sb.append("/");
		sb.append(roleCode);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserRole.class);
	}


	/**
	 * Find All User Roles
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserRole> findAllRoles() {
		UserRole[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.ROLES), UserRole[].class);
		List<UserRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find All Menus
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<MenuDto> findAllMenus() {
		MenuDto[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.MENUS), MenuDto[].class);
		List<MenuDto> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find All Menus of User
	 *
	 * @param userId
	 * @return
	 * @throws IdmException
	 */
	public List<UserMenu> findAllMenusByUserId(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		UserMenu[] resp = getRestTemplate().getForObject(
				getServiceURI(IdmUrlConstants.MENUS + IdmUrlConstants.MENUS_USERS), UserMenu[].class, params);
		List<UserMenu> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/*
	 * Find all menus by parent code
	 */
	/**
	 * Find All Menus by parent code
	 *
	 * @param parentCode
	 * @return
	 * @throws IdmException
	 */
	public List<MenuDto> findAllMenuByPerentCode(String parentCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("parentCode", parentCode);

		MenuDto[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.MENUS), MenuDto[].class,
				params);
		List<MenuDto> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Forgot Password
	 *
	 * @param forgotPassword
	 * @return
	 * @throws IdmException
	 */
	public UserProfile forgotPassword(ForgotPassword forgotPassword) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USERS_FORGOT_PWORD), forgotPassword,
				UserProfile.class);
	}


	/**
	 * Activate User
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public UserProfile activateUser(UserProfile userProfile) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USERS_ACTIVATE), userProfile,
				UserProfile.class);
	}


	/**
	 * <p>
	 * When this method is called, it update the status of the register user as
	 * Active
	 * </p>
	 *
	 * @param userId
	 * @return
	 * @throws IdmException
	 */
	public UserProfile activateRegisteredUser(String userId) {
		if (BaseUtil.isObjNull(userId)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setUserId(userId);
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ACTIVATE_NEW_REG_USER), userProfile,
				UserProfile.class);
	}


	/**
	 * Change Password
	 *
	 * @param changePassword
	 * @return
	 * @throws IdmException
	 */
	public UserProfile changePassword(ChangePassword changePassword) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USERS_CHANGE_PWORD), changePassword,
				UserProfile.class);
	}


	/**
	 * Change Password verify by branch
	 *
	 * @param changePassword
	 * @param checkBranch
	 * @return
	 * @throws IdmException
	 */
	public UserProfile changePassword(ChangePassword changePassword, boolean checkBranch) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS_CHANGE_PWORD);
		sb.append("?checkBranch=" + checkBranch);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), changePassword, UserProfile.class);
	}


	/**
	 * Replace Password in LDAP directly. (Password must by encrypted)
	 *
	 * @param changePassword
	 * @return
	 * @throws IdmException
	 */
	public ChangePassword replaceLdapPassword(ChangePassword changePassword) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USERS_CHANGE_PWORD_DIRECT),
				changePassword, ChangePassword.class);
	}


	/**
	 * Search Users
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> searchUsers(UserProfile userProfile) {
		return searchUsers(userProfile, true);
	}


	/**
	 * Search Users include password in response
	 *
	 * @param userProfile
	 * @param includePassword
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> searchUsers(UserProfile userProfile, boolean includePassword) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append("/search");
		sb.append("?includePassword=" + includePassword);
		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString(), userProfile),
				UserProfile[].class);
		List<UserProfile> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Update Sync Flag
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public UserProfile updateSyncFlag(UserProfile userProfile) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append("/updateSyncFlag");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), userProfile, UserProfile.class);
	}


	private IdmRestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM911);
		}
		if (authToken != null) {
			httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
		} else {
			httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.debug("Service Rest URL: {}", uri);
		return uri;
	}


	@SuppressWarnings("rawtypes")
	private String getServiceURI(String serviceName, Object param) {
		StringBuilder uri = new StringBuilder();
		uri.append(getServiceURI(serviceName));

		for (Field f : param.getClass().getDeclaredFields()) {
			try {
				f.setAccessible(true);
				Object obj = f.get(param);
				if (!BaseUtil.isObjNull(obj)) {
					if (String.class == f.getType()) {
						uri.append("&" + f.getName() + "=" + (String) obj);
					} else if (Integer.class == f.getType() || Long.class == f.getType()
							|| Date.class == f.getType()) {
						uri.append("&" + f.getName() + "=" + obj);
					} else if (List.class == f.getType()) {
						List lst = (List) obj;
						StringBuilder str = new StringBuilder();
						for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
							Object object = iterator.next();
							str.append("," + object);
						}
						uri.append("&" + f.getName() + "=" + str.substring(1, str.length()));
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}
		LOGGER.debug("Service Rest URL: {}", uri);
		return uri.toString();
	}


	protected String getServiceURI(String serviceName, Map<String, Object> obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		JsonNode jnode = mapper.valueToTree(obj);
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		sb.append(getServiceURI(serviceName));

		if (!BaseUtil.isObjNull(obj)) {
			try {
				Map<String, Object> maps = mapper.readValue(jnode.toString(),
						new TypeReference<Map<String, Object>>() {
						});
				for (Map.Entry<String, Object> entry : maps.entrySet()) {
					String mKey = entry.getKey();
					Object mValue = entry.getValue();
					if (!BaseUtil.isObjNull(mValue) && !BaseUtil.isEquals(mKey, "serialVersionUID")) {
						if (isFirst) {
							sb.append("?");
							isFirst = false;
						}
						if (mValue instanceof String) {
							mValue = UriUtil.getVariableValueAsString(mValue);
						}
						sb.append(mKey + "=" + mValue + "&");
					}
				}
			} catch (JsonParseException e) {
				LOGGER.error("JsonParseException: {}", e.getMessage());
			} catch (JsonMappingException e) {
				LOGGER.error("JsonMappingException: {}", e.getMessage());
			} catch (IOException e) {
				LOGGER.error("IOException: {}", e.getMessage());
			}
		}
		return !isFirst ? (sb.toString()).substring(0, sb.length() - 1) : sb.toString();
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public static void setRestTemplate(IdmRestTemplate restTemplate) {
		IdmServiceClient.restTemplate = restTemplate;
	}


	/**
	 * Find Menu by level
	 *
	 * @param menuLevel
	 * @return
	 * @throws IdmException
	 */
	public List<MenuDto> findMenuByMenuLevel(Integer menuLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MENUS);
		sb.append(IdmUrlConstants.MENU_LEVEL_NUM);

		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.MENU_LEVEL, menuLevel);

		MenuDto[] menuDtoArr = getRestTemplate().getForObject(getServiceURI(sb.toString()), MenuDto[].class, params);
		List<MenuDto> result = null;
		if (!BaseUtil.isObjNull(menuDtoArr)) {
			result = Arrays.asList(menuDtoArr);
		}
		return result;
	}


	/**
	 * Create Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public MenuDto createMenu(MenuDto menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ADD_MENU_URL), menuDto, MenuDto.class);
	}


	/**
	 * Update Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public MenuDto updateMenu(MenuDto menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.UPDATE_MENU_URL), menuDto,
				MenuDto.class);
	}


	/**
	 * Search Menu by code
	 *
	 * @param menuCode
	 * @return
	 * @throws IdmException
	 */
	public MenuDto findMenuByMenuCode(String menuCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_MENU_BY_MENU_CODE_URL);

		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.MENU_CODE, menuCode);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), MenuDto.class, params);
	}


	/**
	 * Create User Group
	 *
	 * @param ug
	 * @return
	 * @throws IdmException
	 */
	public UserGroup createUserGroup(UserGroup ug) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/createUserGroup");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ug, UserGroup.class);
	}


	/**
	 * Find User Group by code
	 *
	 * @param groupCode
	 * @return
	 * @throws IdmException
	 */
	public UserGroup findUserGroupByCode(UserGroup groupCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/byGroupCode");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), groupCode, UserGroup.class);
	}


	/**
	 * Update User Group
	 *
	 * @param ug
	 * @return
	 * @throws IdmException
	 */
	public UserGroup updateUserGroup(UserGroup ug) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append("/updateUserGroup");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ug, UserGroup.class);
	}


	/**
	 * Create User Type
	 *
	 * @param userType
	 * @return
	 * @throws IdmException
	 */
	public UserType createUserType(UserType userType) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.USER_TYPE), userType, UserType.class);
	}


	/**
	 * Get User Type by code
	 *
	 * @param userTypeCode
	 * @return
	 * @throws IdmException
	 */
	public UserType getUserTypeCode(String userTypeCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append("/");
		sb.append(userTypeCode);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserType.class);
	}


	/**
	 * Update User Type
	 *
	 * @param userType
	 * @return
	 * @throws IdmException
	 */
	public boolean updateUserTypeCode(UserType userType) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append("/");
		sb.append(userType.getUserTypeCode());
		getRestTemplate().postForObject(getServiceURI(sb.toString()), userType, UserType.class);
		return true;
	}


	/**
	 * Update User Group Role
	 *
	 * @param userGroupRole
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroupRole> updateUserGroupRoleGroup(List<UserGroupRole> userGroupRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS_ROLE_UPDATE);
		UserGroupRole[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroupRole,
				UserGroupRole[].class);
		List<UserGroupRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Find User Group Role by group code
	 *
	 * @param userGroupRoleCode
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroupRole> findUserGroupRoleByGroupCode(String userGroupRoleCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS_ROLE_FIND);
		sb.append("?userGroupRoleCode={userGroupRoleCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("userGroupRoleCode", userGroupRoleCode);

		List<UserGroupRole> result = null;
		UserGroupRole[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroupRole[].class,
				params);
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Create User Audit Trail Action
	 *
	 * @param audit
	 * @return
	 * @throws IdmException
	 */
	public AuditAction createAuditAction(AuditAction audit) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.AUDIT_ACTION), audit, AuditAction.class);
	}


	/**
	 * Activate Multiple Users
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> activateUserProfile(UserProfile userProfile) {
		UserProfile[] resp = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.PROFILE_ACTIVATE),
				userProfile, UserProfile[].class);
		List<UserProfile> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Deactivate Multiple Users
	 *
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public List<UserProfile> deactivateUserProfile(UserProfile userProfile) {
		List<UserProfile> result = null;
		UserProfile[] profile = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.PROFILE_DEACTIVATE),
				userProfile, UserProfile[].class);
		if (!BaseUtil.isObjNull(profile)) {
			result = Arrays.asList(profile);
		}
		return result;
	}


	public MobileBuilder mobile() {
		return new MobileBuilder(getRestTemplate(), url);
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> findAllConfig() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG),
				HashMap.class);
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> createIdmConfig(List<IdmConfigDto> idmConfigDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG),
				idmConfigDto, HashMap.class);
	}


	/**
	 * Search User Group Branch
	 *
	 * @param userGroupBranch
	 * @return
	 */
	public List<UserGroupRoleBranch> searchUserGroupRoleBranch(UserGroupRoleBranch userGroupBranch) {
		if (BaseUtil.isObjNull(userGroupBranch)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUPS_BRANCH_SEARCH);

		List<UserGroupRoleBranch> result = null;
		UserGroupRoleBranch[] profile = getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroupBranch,
				UserGroupRoleBranch[].class);
		if (!BaseUtil.isObjNull(profile)) {
			result = Arrays.asList(profile);
		}
		return result;
	}

}
