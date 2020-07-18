package com.idm.sdk.client;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.builder.MobileBuilder;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AssignRole;
import com.idm.sdk.model.AuditAction;
import com.idm.sdk.model.ChangePassword;
import com.idm.sdk.model.Fcm;
import com.idm.sdk.model.FcmDevice;
import com.idm.sdk.model.ForgotPassword;
import com.idm.sdk.model.IdmConfigDto;
import com.idm.sdk.model.LoginDto;
import com.idm.sdk.model.OauthClientDetails;
import com.idm.sdk.model.PortalType;
import com.idm.sdk.model.RoleMenu;
import com.idm.sdk.model.Token;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserGroupBranch;
import com.idm.sdk.model.UserGroupRole;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.idm.sdk.model.UserRole;
import com.idm.sdk.model.UserType;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.UriUtil;
import com.util.constants.BaseConstants;
import com.util.http.HttpAuthClient;
import com.util.model.MessageResponse;
import com.util.model.ServiceCheck;
import com.util.pagination.DataTableResults;


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
	
	private String portalType;

	private int readTimeout;
	
	private String systemType;


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
		return createUser(up, fixedId, fixedPword, pendingActivation, true);
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
	public UserProfile createUser(UserProfile up, boolean fixedId, boolean fixedPword, boolean pendingActivation, boolean sendMail) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append("?fixedId={fixedId}");
		sb.append("&fixedPword={fixedPword}");
		sb.append("&pendingActivation={pendingActivation}");
		sb.append("&sendMail={sendMail}");

		Map<String, Object> params = new HashMap<>();
		params.put("fixedId", fixedId);
		params.put("fixedPword", fixedPword);
		params.put("pendingActivation", pendingActivation);
		params.put("sendMail", sendMail);

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
		sb.append(IdmUrlConstants.PROFILE);
		sb.append("/update");
		sb.append("?userId={userId}");
		Map<String, Object> params = new HashMap<>();
		params.put("userId", up.getUserId());
		getRestTemplate().postForObject(getServiceURI(sb.toString()), up, UserProfile.class, params);
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
	 * Find All User Types
	 *
	 * @return
	 * @throws IdmException
	 */

	@SuppressWarnings("unchecked")
	public DataTableResults<UserType> searchUserTypes(UserType userProfile, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append(IdmUrlConstants.SEARCH_PAGINATION);
		return getRestTemplate().postForObject(getServiceURI(sb.toString(), pagination), userProfile,
				DataTableResults.class);
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
	 * Create User Role
	 *
	 * @param role
	 * @return
	 * @throws IdmException
	 */
	public List<UserRole> createRole(UserRole role) {
		UserRole [] resp = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ROLES_CREATE), role, UserRole[].class);
		List<UserRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}


	/**
	 * Update User Role
	 *
	 * @param role
	 * @return
	 * @throws IdmException
	 */
	public List<UserRole> updateRole(UserRole role) {
		UserRole [] resp = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ROLES_UPDATE), role, UserRole[].class);
		List<UserRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	
	/**
	 * Delete User Role
	 *
	 * @param role
	 * @return
	 * @throws IdmException
	 */
	public Boolean deleteRole(UserRole role) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ROLES_DELETE), role, Boolean.class);
	}


	public List<UserRole> searchRole(UserRole role) {
		UserRole [] resp = getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ROLES_SEARCH), role, UserRole[].class);
		List<UserRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
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
	 * Find All User Roles
	 * eager loads PortalTypes
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserRole> findAllRolesIncludePortalType() {
		UserRole[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.ROLES + IdmUrlConstants.PORTALTYPE), UserRole[].class);
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
	public List<UserMenu> findAllMenus() {
		UserMenu[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.MENUS + IdmUrlConstants.MENU_LIST), UserMenu[].class);
		List<UserMenu> result = null;
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
	public List<UserMenu> findAllMenuByPerentCode(String parentCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("parentCode", parentCode);

		UserMenu[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.MENUS), UserMenu[].class,
				params);
		List<UserMenu> result = null;
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
		HttpAuthClient hac = null;
		if (authToken != null) {
			if(portalType != null) {
				hac = new HttpAuthClient(authToken, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(authToken, messageId, readTimeout);
			}
		} else {
			if(portalType != null) {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout, portalType);
			} else {
				hac = new HttpAuthClient(clientId, token, messageId, readTimeout);
			}
		}
		
		if (systemType != null) {
			LOGGER.info("System Type: {} ", systemType);
			hac.setSystemType(systemType);
		}
		httpClient = hac.getHttpClient();
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
		ObjectMapper mapper = JsonUtil.objectMapper();
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
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
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
	
	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setSystemType(String systemType) {
		this.systemType = systemType;
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
	public List<UserMenu> findMenuByMenuLevel(Integer menuLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MENUS);
		sb.append(IdmUrlConstants.MENU_LEVEL_NUM);

		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.MENU_LEVEL, menuLevel);

		UserMenu[] menuDtoArr = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserMenu[].class,
				params);
		List<UserMenu> result = null;
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
	public UserMenu createMenu(UserMenu menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.ADD_MENU_URL), menuDto, UserMenu.class);
	}


	/**
	 * Update Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public UserMenu updateMenu(UserMenu menuDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.UPDATE_MENU_URL), menuDto,
				UserMenu.class);
	}
	
	/**
	 * Search Menu
	 *
	 * @param menuDto
	 * @return
	 * @throws IdmException
	 */
	public List<UserMenu> searchMenu(UserMenu menuDto) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MENUS);
		sb.append(IdmUrlConstants.SEARCH_MENU);
		UserMenu[] menuDtoArr = getRestTemplate().postForObject(getServiceURI(sb.toString()), menuDto, UserMenu[].class);
		return Arrays.asList(menuDtoArr);
	}


	/**
	 * Search Menu by code
	 *
	 * @param menuCode
	 * @return
	 * @throws IdmException
	 */
	public UserMenu findMenuByMenuCode(String menuCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.FIND_MENU_BY_MENU_CODE_URL);

		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.MENU_CODE, menuCode);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserMenu.class, params);
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
	public boolean deleteUserType(UserType userType) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append("/delete");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), userType, boolean.class);
	}


	/**
	 * Update User Type
	 *
	 * @param userType
	 * @return
	 * @throws IdmException
	 */
	public UserType updateUserType(UserType userType) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_TYPE);
		sb.append("/");
		sb.append(userType.getUserTypeCode());
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), userType, UserType.class);
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


	public List<IdmConfigDto> findAllByPortalType(String portalType) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.REFERENCE).append(IdmUrlConstants.CONFIG).append(IdmUrlConstants.PORTALTYPE);
		sb.append("?portalType={portalType}");

		Map<String, Object> params = new HashMap<>();
		params.put("portalType", portalType);

		IdmConfigDto[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), IdmConfigDto[].class,
				params);
		List<IdmConfigDto> idmConfigDto = new ArrayList<>();
		if (!BaseUtil.isObjNull(resp)) {
			idmConfigDto = Arrays.asList(resp);
		}
		return idmConfigDto;
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> createIdmConfig(List<IdmConfigDto> idmConfigDto) {
		return getRestTemplate().postForObject(getServiceURI(IdmUrlConstants.REFERENCE + IdmUrlConstants.CONFIG),
				idmConfigDto, HashMap.class);
	}


	public List<IdmConfigDto> createIdmConfigByPortal(String portalType, List<IdmConfigDto> idmConfigDto) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.REFERENCE).append(IdmUrlConstants.CONFIG).append(IdmUrlConstants.PORTALTYPE);
		sb.append("?portalType={portalType}");

		Map<String, Object> params = new HashMap<>();
		params.put("portalType", portalType);

		List<IdmConfigDto> result = null;
		IdmConfigDto[] configDto = getRestTemplate().postForObject(getServiceURI(sb.toString()), idmConfigDto,
				IdmConfigDto[].class, params);
		if (!BaseUtil.isObjNull(configDto)) {
			result = Arrays.asList(configDto);
		}
		return result;
	}


	/**
	 * Find All User Group Branch
	 *
	 * 
	 * @return
	 */
	public List<UserGroupBranch> findAllUserGroupBranch() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUP_BRANCH);

		List<UserGroupBranch> result = Collections.emptyList();
		UserGroupBranch[] ugb = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroupBranch[].class);
		if (!BaseUtil.isObjNull(ugb)) {
			result = Arrays.asList(ugb);
		}
		return result;
	}
	
	
	/**
	 * Create User Group Branch
	 *
	 * @param userGroupBranch
	 * @return
	 * @throws IdmException
	 */
	public UserGroupBranch createUserGroupBranch(UserGroupBranch ugbLst) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUP_BRANCH_CREATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ugbLst, UserGroupBranch.class);
	}
	
	
	/**
	 * Update User Group Branch
	 *
	 * @param userGroupBranch
	 * @return
	 * @throws IdmException
	 */
	public UserGroupBranch updateUserGroupBranch(UserGroupBranch ugbLst) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUP_BRANCH_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ugbLst, UserGroupBranch.class);
	}

	
	/**
	 * Delete User Group Branch
	 *
	 * @param userGroupBranch
	 * @return
	 * @throws IdmException
	 */
	public Boolean deleteUserGroupBranch(UserGroupBranch userGroupBranch) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUP_BRANCH_DELETE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroupBranch, Boolean.class);
	}
	
	
	/**
	 * Search User Group Branch
	 *
	 * @param userGroupBranch
	 * @return
	 */
	public List<UserGroupBranch> searchUserGroupBranch(UserGroupBranch userGroupBranch) {
		if (BaseUtil.isObjNull(userGroupBranch)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUP_BRANCH_SEARCH);

		List<UserGroupBranch> result = null;
		UserGroupBranch[] profile = getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroupBranch,
				UserGroupBranch[].class);
		if (!BaseUtil.isObjNull(profile)) {
			result = Arrays.asList(profile);
		}
		return result;
	}
	
	/**
	 * Search User Group
	 *
	 * @param userGroup
	 * @return
	 */
	public List<UserGroup> searchUserGroup(UserGroup userGroup) {
		if (BaseUtil.isObjNull(userGroup)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUPS_SEARCH);

		List<UserGroup> result = null;
		UserGroup[] profile = getRestTemplate().postForObject(getServiceURI(sb.toString()), userGroup,
				UserGroup[].class);
		if (!BaseUtil.isObjNull(profile)) {
			result = Arrays.asList(profile);
		}
		return result;
	}


	public List<PortalType> retrieveAllIdmPortalType() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PORTALTYPE_SEARCH);

		List<PortalType> result = null;
		PortalType[] portalTypes = getRestTemplate().getForObject(getServiceURI(sb.toString()), PortalType[].class);
		if (!BaseUtil.isObjNull(portalTypes)) {
			result = Arrays.asList(portalTypes);
		}
		return result;
	}

	public List<PortalType> updateIdmPortalType(List<PortalType> portalTypes) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PORTALTYPE_UPDATE);

		List<PortalType> result = null;
		PortalType[] idmPortalTypes = getRestTemplate().postForObject(getServiceURI(sb.toString()), portalTypes, PortalType[].class);
		if (!BaseUtil.isObjNull(portalTypes)) {
			result = Arrays.asList(idmPortalTypes);
		}
		return result;
	}
	
	public Boolean deleteIdmPortalType(PortalType portalType) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.PORTALTYPE_DELETE);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), portalType, Boolean.class);
	}
	
	
	public Boolean deleteUserGroup(UserGroup ug) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.USER_GROUPS_DELETE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ug, Boolean.class);
	}
	
	public Boolean deleteMenu(UserMenu umDto) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MENUS);
		sb.append(IdmUrlConstants.DELETE_MENU);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), umDto, Boolean.class);
	}
	
	
	/**
	 * Find All Role Menu
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<RoleMenu> findAllRoleMenu() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append("/all");

		RoleMenu[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), RoleMenu[].class);
		List<RoleMenu> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}

	
	/**
	 * Find All Role Menu by Portal Type
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<RoleMenu> findAllRoleMenuByPortalType(String portalTypeCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append(IdmUrlConstants.PORTALTYPE);
		sb.append("?portalTypeCode=").append(portalTypeCode);
		
		RoleMenu[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), RoleMenu[].class);
		List<RoleMenu> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public DataTableResults<RoleMenu> searchRoleMenu(RoleMenu roleMenu, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append(IdmUrlConstants.SEARCH_PAGINATION);
		return getRestTemplate().postForObject(getServiceURI(sb.toString(), pagination), roleMenu,
				DataTableResults.class);
	}
	
	/**
	 * Create Role Menu
	 *
	 * @param roleMenu
	 * @return
	 * @throws IdmException
	 */
	public RoleMenu createRoleMenu(RoleMenu roleMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append(IdmUrlConstants.CREATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), roleMenu, RoleMenu.class);
	}
	
	/**
	 * Update Role Menu
	 *
	 * @return
	 * @throws IdmException
	 */
	public RoleMenu updateRoleMenu(RoleMenu roleMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append(IdmUrlConstants.UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), roleMenu, RoleMenu.class);
	}
	
	/**
	 * Update Role Menu
	 *
	 * @return
	 * @throws IdmException
	 */
	public boolean deleteRoleMenu(RoleMenu roleMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append(IdmUrlConstants.DELETE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), roleMenu, boolean.class);
	}
	
	/**
	 * Update User Config
	 *
	 * @param ug
	 * @return
	 * @throws IdmException
	 */
	public IdmConfigDto updateUserConfig(IdmConfigDto userCfg) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.REFERENCE);
		sb.append(IdmUrlConstants.USER_CONFIG_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), userCfg, IdmConfigDto.class);
	}
	
	/**
	 * Search Menu by code
	 *
	 * @param menuCode
	 * @return
	 * @throws IdmException
	 */
	public IdmConfigDto findUsrCfgByConfigCode(String configCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.REFERENCE);
		sb.append(IdmUrlConstants.FIND_USR_CONFIG_BY_CONFIG_CODE);
		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.CONFIG_CODE, configCode);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), IdmConfigDto.class, params);
	}
	
	/**
	 * Find All User Config
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<IdmConfigDto> findAllUserConfig() {
		IdmConfigDto[] resp = getRestTemplate().getForObject(getServiceURI(IdmUrlConstants.REFERENCE + IdmUrlConstants.USER_CONFIG), IdmConfigDto[].class);
		List<IdmConfigDto> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Find All User Group Role
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<UserGroupRole> findAllUserGroupRole() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append("/all");

		UserGroupRole[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroupRole[].class);
		List<UserGroupRole> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public DataTableResults<UserGroupRole> searchUserGroupRole(UserGroupRole groupRole, boolean isEmail, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append(IdmUrlConstants.SEARCH_PAGINATION);
		pagination.put("isEmail", isEmail);
		return getRestTemplate().postForObject(getServiceURI(sb.toString(), pagination), groupRole,
				DataTableResults.class);
	}
	
	/**
	 * Create Role Menu
	 *
	 * @param roleMenu
	 * @return
	 * @throws IdmException
	 */
	public UserGroupRole createUserGroupRole(UserGroupRole groupRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append("/createGroupRole");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), groupRole, UserGroupRole.class);
	}
	
	/**
	 * Update Role Menu
	 *
	 * @return
	 * @throws IdmException
	 */
	public UserGroupRole updateUserGroupRole(UserGroupRole groupRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append("/updateGroupRole");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), groupRole, UserGroupRole.class);
	}
	
	/**
	 * Delete Role Menu
	 *
	 * @return
	 * @throws IdmException
	 */
	public boolean deleteUserGroupRole(UserGroupRole groupRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append("/delete");
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), groupRole, boolean.class);
	}
	
	/**
	 * find user group by group code
	 *
	 * @param groupCode
	 * @return
	 * @throws IdmException
	 */
	public UserGroup findUsrGroupByGrpCode(String groupCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUPS);
		sb.append(IdmUrlConstants.FIND_USER_GROUP_BY_CODE);
		Map<String, Object> params = new HashMap<>();
		params.put(BaseConstants.GROUP_CODE, groupCode);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroup.class, params);
	}
	
	/**
	 * Search group role by id
	 *
	 * @param id
	 * @return
	 * @throws IdmException
	 */
	public UserGroupRole findGroupRoleById(Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USER_GROUP_ROLE);
		sb.append("/findGroupRoleId/{id}");
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), UserGroupRole.class, params);
	}
	
	public RoleMenu findRoleMenuById(Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.ROLE_MENU);
		sb.append("/findRoleMenuId/{id}");
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), RoleMenu.class, params);
	}
	
	
	/**
	 * Find All Idm Oauth Client Details
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<OauthClientDetails> findAllOauthClientDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.OAUTH);
		sb.append(IdmUrlConstants.OAUTH_CLIENT_DETAILS);

		OauthClientDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), OauthClientDetails[].class);
		List<OauthClientDetails> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public DataTableResults<OauthClientDetails> searchOauthClientDetails(OauthClientDetails ocd, boolean isEmail, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.OAUTH);
		sb.append(IdmUrlConstants.OAUTH_CLIENT_DETAILS);
		sb.append(IdmUrlConstants.SEARCH_PAGINATION);
		pagination.put("isEmail", isEmail);
		return getRestTemplate().postForObject(getServiceURI(sb.toString(), pagination), ocd,
				DataTableResults.class);
	}
	
	/**
	 * Create Idm Oauth Client Details
	 *
	 * @param oath client details
	 * @return
	 * @throws IdmException
	 */
	public List<OauthClientDetails> createOauthClientDetails(List<OauthClientDetails> ocds) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.OAUTH);
		sb.append(IdmUrlConstants.OAUTH_CLIENT_DETAILS_CREATE);
		
		OauthClientDetails [] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), ocds, OauthClientDetails[].class);
		List<OauthClientDetails> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Update Idm Oauth Client Details
	 *
	 * @return
	 * @throws IdmException
	 */
	public List<OauthClientDetails> updateOauthClientDetails(List<OauthClientDetails> ocds) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.OAUTH);
		sb.append(IdmUrlConstants.OAUTH_CLIENT_DETAILS_UPDATE);

		OauthClientDetails [] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), ocds, OauthClientDetails[].class);
		List<OauthClientDetails> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Delete Idm Oauth Client Details
	 *
	 * @return
	 * @throws IdmException
	 */
	public boolean deleteOauthClientDetails(OauthClientDetails ocd) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.OAUTH);
		sb.append(IdmUrlConstants.OAUTH_CLIENT_DETAILS_DELETE);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), ocd, boolean.class);
	}
	
	/**
	 * Find All FCM
	 *
	 * @return List<FcmProfile>
	 * @throws IdmException
	 */
	public List<Fcm> findAllFcmProfile() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_PROFILE_FINDALL);
		Fcm [] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), Fcm[].class);
		List<Fcm> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Find All FCM by fcm id
	 *
	 * @param fcm
	 * @return
	 * @throws IdmException
	 */
	public Fcm findFcmById(Integer fcmId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_PROFILE_FINDBY_FCMID);
		sb.append("?fcmId=").append(fcmId);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), Fcm.class);

	}
	

	/**
	 * Find All FCM by user id
	 *
	 * @param userId
	 * @return
	 * @throws IdmException
	 */
	public Fcm findFcmByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_PROFILE_FINDBY_USERID);
		sb.append("?userId=").append(userId);
		
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), Fcm.class);
	}
	
	
	/**
	 * Create FCM
	 *
	 * @param fcm
	 * @return FcmProfile
	 * @throws IdmException
	 */
	public Fcm createFcmProfile(Fcm fcm) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_PROFILE_CREATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), fcm, Fcm.class);
	}
	
	
	/**
	 * Update FCM
	 *
	 * @param fcm
	 * @return FcmProfile
	 * @throws IdmException
	 */
	public Fcm updateFcmProfile(Fcm fcm) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_PROFILE_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), fcm, Fcm.class);
	}
	
	
	/**
	 * Find All FCM DEVICE
	 *
	 * @return List<FcmDevice>
	 * @throws IdmException
	 */
	public List<FcmDevice> findAllFcmDevice() {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_FINDALL);
		FcmDevice [] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), FcmDevice[].class);
		List<FcmDevice> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	
	/**
	 * Find All FCM by fcm id
	 *
	 * @param fcm
	 * @return
	 * @throws IdmException
	 */
	public List<FcmDevice> findFcmDeviceByFcmId(Integer fcmId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_FINDBY_FCMID);
		sb.append("?fcmId=").append(fcmId);
		
		FcmDevice[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), FcmDevice[].class);
		List<FcmDevice> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	
	/**
	 * Find All FCM by machine id
	 *
	 * @param fcm
	 * @return
	 * @throws IdmException
	 */
	public FcmDevice findFcmDeviceByMachineId(String machineId) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_FINDBY_MACHINEID);
		sb.append("?machineId=").append(machineId);
		
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), FcmDevice.class);
	}
	
	
	/**
	 * Create FCM DEVICE
	 *
	 * @param fcm
	 * @return FcmProfile
	 * @throws IdmException
	 */
	public FcmDevice createFcmDevice(FcmDevice fcm) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_CREATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), fcm, FcmDevice.class);
	}
	
	
	/**
	 * Update FCM DEVICE
	 *
	 * @param fcm
	 * @return FcmProfile
	 * @throws IdmException
	 */
	public FcmDevice updateFcmDevice(FcmDevice fcm) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), fcm, FcmDevice.class);
	}
	
	/**
	 * Search FcmDevice
	 * 
	 * @param fcmDevice
	 * @return
	 */
	public List<FcmDevice> searchFcmDevice(FcmDevice fcmDevice) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_SEARCH);
		FcmDevice[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), fcmDevice, FcmDevice[].class);
		List<FcmDevice> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Search Fcm
	 * 
	 * @param fcm
	 * @return
	 */
	public List<Fcm> searchFcm(Fcm fcm) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.MOBILE);
		sb.append(IdmUrlConstants.FCM_DEVICE_SEARCH);
		Fcm[] resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), fcm, Fcm[].class);
		List<Fcm> result = null;
		if (!BaseUtil.isObjNull(resp)) {
			result = Arrays.asList(resp);
		}
		return result;
	}
	
	/**
	 * Generate activation code
	 * Refer IDM_CONFIG.ACTIVATE_CODE_ISNUMERIC to set numeric only or alphanumeric. Default is true.
	 * and IDM_CONFIG.ACTIVATE_CODE_LENGTH to set length. Default is 6.
	 * @param userId
	 * @return UserProfile
	 */
	public UserProfile generateActivationCode( String userId ) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append(IdmUrlConstants.USER_ACTIVATION_GENERATE);
		sb.append("?userId=").append(userId);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), null, UserProfile.class);
	}
	
	/**
	 * Verify activation code
	 * @param userId
	 * @param activationCode
	 * @return UserProfile
	 */
	public UserProfile verifyActivationCode(String userId, String activationCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append(IdmUrlConstants.USER_ACTIVATION_VERIFY);
		sb.append("?userId=").append(userId);
		sb.append("&activationCode=").append(activationCode);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), null, UserProfile.class);
	}
	
	public List<UserProfile> getCountRegisteredUserByDay() {

		List<UserProfile> statistics = null;
		StringBuilder sb = new StringBuilder();
		sb.append(IdmUrlConstants.USERS);
		sb.append(IdmUrlConstants.RPT_USER_REGISTERED_BY_DAY);
		UserProfile[] userProfileArray = getRestTemplate().getForObject(getServiceURI(sb.toString()), UserProfile[].class);

		if (userProfileArray != null && userProfileArray.length > 0) {
			statistics = Arrays.asList(userProfileArray);
		}

		return statistics;
	}
}