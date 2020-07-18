/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.client;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.constants.BaseConstants;
import com.baseboot.idm.sdk.constants.IdmConstants;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.AssignRole;
import com.baseboot.idm.sdk.model.AuditAction;
import com.baseboot.idm.sdk.model.ChangePassword;
import com.baseboot.idm.sdk.model.ForgotPassword;
import com.baseboot.idm.sdk.model.LoginDto;
import com.baseboot.idm.sdk.model.MenuDto;
import com.baseboot.idm.sdk.model.ServiceCheck;
import com.baseboot.idm.sdk.model.Token;
import com.baseboot.idm.sdk.model.UserGroup;
import com.baseboot.idm.sdk.model.UserGroupRole;
import com.baseboot.idm.sdk.model.UserMenu;
import com.baseboot.idm.sdk.model.UserProfile;
import com.baseboot.idm.sdk.model.UserRole;
import com.baseboot.idm.sdk.model.UserType;
import com.baseboot.idm.sdk.pagination.DataTableResults;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.UriUtil;
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
		initialize();
	}


	public IdmServiceClient(String url, int readTimeout) {
		this.url = url;
		this.readTimeout = readTimeout;
		initialize();
	}


	public IdmServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
		initialize();
	}


	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.SERVICE_CHECK + "/test"), String.class);
	}


	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.SERVICE_CHECK), ServiceCheck.class);
	}


	public boolean evict() {
		return evict(null);
	}


	public boolean evict(String prefixKey) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(IdmUrlConstrants.REFERENCE).append("/evict");
		if (!BaseUtil.isObjNull(prefixKey)) {
			sbUrl.append("?prefixKey=" + prefixKey);
		}
		return getRestTemplate().getForObject(getServiceURI(sbUrl.toString()), boolean.class);
	}


	/**
	 * Get User Profile
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> getUserProfile(UserProfile userProfile) {
		List<UserProfile> svcResp = null;
		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.PROFILE, userProfile),
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/search");
		sb.append("?isEmail=" + isEmail);
		UserProfile[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), userProfile,
				UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append(IdmUrlConstrants.SEARCH_PAGINATION);
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/usernamelist");
		sb.append("?usernames={usernames}");

		Map<String, Object> params = new HashMap<>();
		params.put("usernames", usernameList);

		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class,
				params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	public Boolean staticPage() {
		return getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.STATIC), Boolean.class);
	}


	/**
	 * User Login Authentication
	 *
	 * @param loginDto
	 * @return
	 * @throws IdmException
	 */
	public LoginDto login(LoginDto loginDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.LOGIN), loginDto, LoginDto.class);
	}


	/**
	 * Logout
	 *
	 * @param loginDto
	 * @return
	 * @throws IdmException
	 */
	public Boolean logout(LoginDto loginDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.LOGOUT), loginDto, Boolean.class);
	}


	/**
	 * Force Logout
	 *
	 * @return
	 * @throws IdmException
	 */
	public Boolean logout() {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.LOGOUT), null, Boolean.class);
	}


	/**
	 * Generate Access Token
	 *
	 * @return
	 * @throws IdmException
	 */
	public Token generatedAccessToken() {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.TOKENS), null, Token.class);
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
		sb.append(IdmUrlConstrants.TOKENS);
		sb.append("/validate");
		sb.append("?embededRole={embededRole}");

		Map<String, Object> params = new HashMap<>();
		params.put(IdmConstants.KEY_EMBEDDED_ROLE, embededRole);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), null, Token.class, params);
	}


	/**
	 * Delete Access Token
	 *
	 * @return
	 * @throws IdmException
	 */
	public Boolean deleteToken() {
		return getRestTemplate().deleteForObject(getServiceURI(IdmUrlConstrants.TOKENS));
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
		sb.append(IdmUrlConstrants.CLIENTS);
		sb.append("?embededUser={embededUser}&embededRole={embededRole}");

		Map<String, Object> params = new HashMap<>();
		params.put("embededUser", embededUser);
		params.put(IdmConstants.KEY_EMBEDDED_ROLE, embededRole);

		Token[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), Token[].class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.TOKENS);
		sb.append("?embededUser={embededUser}&embededRole={embededRole}");

		Map<String, Object> params = new HashMap<>();
		params.put("embededUser", embededUser);
		params.put(IdmConstants.KEY_EMBEDDED_ROLE, embededRole);
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/");
		sb.append(userId);
		sb.append("?embededRole={embededRole}&embededMenu={embededMenu}");

		Map<String, Object> params = new HashMap<>();
		params.put(IdmConstants.KEY_EMBEDDED_ROLE, embededRole);
		params.put("embededMenu", embededMenu);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile.class, params);
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/roles?userRoles=");
		sb.append(userRoles);

		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Create User
	 *
	 * @param up
	 * @return
	 * @throws IdmException
	 */
	public UserProfile createUser(UserProfile up) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USERS), up, UserProfile.class);
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
		sb.append(IdmUrlConstrants.USERS);
		sb.append("?fixedId={fixedId}");

		Map<String, Object> params = new HashMap<>();
		params.put("fixedId", fixedId);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
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
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/");
		sb.append(up.getUserId());
		getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class);
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
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.ROLES), role, UserRole.class);
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
		sb.append(IdmUrlConstrants.ROLES);
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
		sb.append(IdmUrlConstrants.ROLES);
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
		sb.append(IdmUrlConstrants.ROLES);
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
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.USER_GROUPS);
		sb.append("/");
		sb.append(roleGroupCode);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Find User Group by Parent Role Group
	 *
	 * @param roleGroupCode
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findUserGroupByParentRoleGroup(String roleGroupCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.USER_GROUPS);
		sb.append("/parent/");
		sb.append(roleGroupCode);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.USER_GROUPS);
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
		sb.append(IdmUrlConstrants.USER_GROUPS);
		sb.append("/all");

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Find All User Groups
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroup> findAllUserGroups() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.USER_GROUPS);
		sb.append("/allGroups");

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Find All User Types
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserType> findAllUserTypes() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.USER_TYPE);
		sb.append("/all");

		UserType[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserType[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.USER_GROUPS);
		sb.append("?groupCode={groupCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("groupCode", groupCode);

		UserGroup[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup[].class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.ROLES);
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
		sb.append(IdmUrlConstrants.ROLES);
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
		UserRole[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.ROLES), UserRole[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Find All Menus
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<MenuDto> findAllMenus() {
		MenuDto[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.MENUS), MenuDto[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
				getServiceURI(IdmUrlConstrants.MENUS + IdmUrlConstrants.MENUS_USERS), UserMenu[].class, params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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

		MenuDto[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstrants.MENUS), MenuDto[].class,
				params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Forgot Password
	 *
	 * @param forgotPassword
	 * @return
	 * @throws IdmException
	 */
	public UserProfile forgotPassword(ForgotPassword forgotPassword) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USERS_FORGOT_PASSWORD), forgotPassword,
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
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USERS_ACTIVATE), userProfile,
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
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USERS_CHANGE_PASSWORD), changePassword,
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
		sb.append(IdmUrlConstrants.USERS_CHANGE_PASSWORD);
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
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USERS_CHANGE_PASSWORD_DIRECT),
				changePassword, ChangePassword.class);
	}


	private static void initialize() {
		restTemplate = new IdmRestTemplate();
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
		sb.append(IdmUrlConstrants.USERS);
		sb.append("/search");
		sb.append("?includePassword=" + includePassword);
		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString(), userProfile),
				UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.USERS);
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
		uri.append(url);
		uri.append(serviceName);

		for (Field f : param.getClass().getDeclaredFields()) {
			try {
				f.setAccessible(true);
				Object obj = f.get(param);
				if (!BaseUtil.isObjNull(obj)) {
					if (String.class == f.getType()) {
						uri.append("&" + f.getName() + "=" + (String) obj);
					} else if (Integer.class == f.getType()) {
						uri.append("&" + f.getName() + "=" + obj);
					} else if (Long.class == f.getType()) {
						uri.append("&" + f.getName() + "=" + obj);
					} else if (Date.class == f.getType()) {
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
		sb.append(IdmUrlConstrants.MENUS);
		sb.append(IdmUrlConstrants.MENU_LEVEL_NUM);

		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.MENU_LEVEL, menuLevel);

		MenuDto[] menuDtoArr = getRestTemplate().getForObject(getServiceURI(sb.toString()), MenuDto[].class, params);
		if (menuDtoArr != null) {
			return Arrays.asList(menuDtoArr);
		}

		return new ArrayList<>();
	}


	/**
	 * Create Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public MenuDto createMenu(MenuDto menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.ADD_MENU_URL), menuDto, MenuDto.class);
	}


	/**
	 * Update Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public MenuDto updateMenu(MenuDto menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.UPDATE_MENU_URL), menuDto,
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
		sb.append(IdmUrlConstrants.FIND_MENU_BY_MENU_CODE_URL);

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
		sb.append(IdmUrlConstrants.USER_GROUPS);
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
		sb.append(IdmUrlConstrants.USER_GROUPS);
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
		sb.append(IdmUrlConstrants.USER_GROUPS);
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
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.USER_TYPE), userType, UserType.class);
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
		sb.append(IdmUrlConstrants.USER_TYPE);
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
		sb.append(IdmUrlConstrants.USER_TYPE);
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
		sb.append(IdmUrlConstrants.USER_GROUPS_ROLE_UPDATE);
		UserGroupRole[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroupRole,
				UserGroupRole[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
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
		sb.append(IdmUrlConstrants.USER_GROUPS_ROLE_FIND);
		sb.append("?userGroupRoleCode={userGroupRoleCode}");

		Map<String, Object> params = new HashMap<>();
		params.put("userGroupRoleCode", userGroupRoleCode);

		UserGroupRole[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroupRole[].class,
				params);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}


	/**
	 * Create User Audit Trail Action
	 *
	 * @param audit
	 * @return
	 * @throws IdmException
	 */
	public AuditAction createAuditAction(AuditAction audit) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.AUDIT_ACTION), audit,
				AuditAction.class);
	}


	/**
	 * Activate Multiple Users
	 *
	 * @param userProfile
	 * @return
	 * @throws IdmException
	 */
	public List<UserProfile> activateUserProfile(UserProfile userProfile) {
		UserProfile[] profile = getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.PROFILE_ACTIVATE),
				userProfile, UserProfile[].class);
		if (!BaseUtil.isObjNull(profile)) {
			return Arrays.asList(profile);
		}
		return new ArrayList<>();
	}


	/**
	 * Deactivate Multiple Users
	 *
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public List<UserProfile> deactivateUserProfile(UserProfile userProfile) {
		UserProfile[] profile = getRestTemplate().postForObject(getServiceURI(IdmUrlConstrants.PROFILE_DEACTIVATE),
				userProfile, UserProfile[].class);
		if (!BaseUtil.isObjNull(profile)) {
			return Arrays.asList(profile);
		}
		return new ArrayList<>();
	}


	// create FWCMS user.
	public UserProfile createFwcmsUser(UserProfile up, boolean isFixedId, boolean isFwcmsId, boolean sendMail) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.USERS);
		sb.append("?fixedId={fixedId}&fwcmsId={fwcmsId}&sendMail={sendMail}");

		Map<String, Object> params = new HashMap<>();
		params.put("fixedId", isFixedId);
		params.put("fwcmsId", isFwcmsId);
		params.put("sendMail", sendMail);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
	}


	public List<UserProfile> findProfileByCmpanyRegNo(String cmpnyRegNo) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstrants.PROFILE);
		sb.append("/findProfileByCmpanyRegNo");
		sb.append("?cmpnyRegNo={cmpnyRegNo}");

		Map<String, String> params = new HashMap<>();
		params.put("cmpnyRegNo", cmpnyRegNo);

		UserProfile[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString(), params),
				UserProfile[].class);
		if (!BaseUtil.isObjNull(resp)) {
			return Arrays.asList(resp);
		}
		return new ArrayList<>();
	}

}
