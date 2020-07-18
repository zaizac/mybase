/**
 * Copyright 2019. 
 */
package com.idm.sdk.constants;

/**
 * @author Mary Jane Buenaventura
 * @since May 7, 2018
 */
public class IdmUrlConstants {

	private IdmUrlConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String ENCRYPT = "/encrypt";

	public static final String DECRYPT = "/decrypt";

	public static final String LOGIN = "/auth/login";

	public static final String LOGOUT = "/auth/logout";

	public static final String TOKENS = "/tokens";

	public static final String CLIENTS = TOKENS + "/clients";

	public static final String AUDIT_ACTION = "/audit";

	public static final String PROFILE = "/profiles";

	public static final String PROFILE_DEACTIVATE = PROFILE + "/deactivate";

	public static final String PROFILE_ACTIVATE = PROFILE + "/activate";

	public static final String SEARCH_PAGINATION = "/search/paginated";
	
	public static final String FINDALL = "/findall";
	
	public static final String SEARCH = "/search";
	
	public static final String CREATE = "/create";
	
	public static final String UPDATE = "/update";
	
	public static final String DELETE = "/delete";
	
	public static final String OAUTH = "/oauth";
	
	public static final String OAUTH_CLIENT_DETAILS = "/oauthClientDetails";
	
	public static final String OAUTH_CLIENT_DETAILS_SEARCH = OAUTH_CLIENT_DETAILS + SEARCH;
	
	public static final String OAUTH_CLIENT_DETAILS_CREATE = OAUTH_CLIENT_DETAILS + CREATE;
	
	public static final String OAUTH_CLIENT_DETAILS_UPDATE = OAUTH_CLIENT_DETAILS + UPDATE;
	
	public static final String OAUTH_CLIENT_DETAILS_DELETE = OAUTH_CLIENT_DETAILS + DELETE;

	public static final String ROLES = "/roles";
	
	public static final String ROLES_SEARCH = ROLES + SEARCH;
	
	public static final String ROLES_CREATE = ROLES + CREATE;
	
	public static final String ROLES_UPDATE = ROLES + UPDATE;
	
	public static final String ROLES_DELETE = ROLES + DELETE;
	
	public static final String REFERENCE = "/references";

	public static final String CONFIG = "/config";
	
	public static final String PORTALTYPE = "/portalType";
	
	public static final String PORTALTYPE_SEARCH = PORTALTYPE + SEARCH;
	
	public static final String PORTALTYPE_CREATE = PORTALTYPE + CREATE;
	
	public static final String PORTALTYPE_UPDATE = PORTALTYPE + UPDATE;
	
	public static final String PORTALTYPE_DELETE = PORTALTYPE + DELETE;
	
	public static final String PORTALTYPE_SEARCH_BY_PORTALTYPECODE = PORTALTYPE_SEARCH + "/portalTypeCode";

	public static final String USER_GROUPS = "/userGroup";
	
	public static final String USER_GROUPS_SEARCH = "/searchUserGroup";
	
	public static final String USER_GROUPS_DELETE = "/userGroupDelete";

	public static final String USER_TYPE = "/usertype";

	public static final String USERS = "/users";

	public static final String USERS_FORGOT_PWORD = USERS + "/forgotPassword";

	public static final String USERS_CHANGE_PWORD = USERS + "/changePassword";

	public static final String USERS_CHANGE_PWORD_DIRECT = USERS + "/changePassword/directUpdate";

	public static final String USERS_ACTIVATE = USERS + "/activateUser";

	public static final String ACTIVATE_NEW_REG_USER = USERS + "/activateNewRegisteredUser";

	public static final String STATIC = "/static";

	public static final String MENUS = "/menus";

	public static final String MENUS_USERS = "/users?id={userId}";

	public static final String MENU_LEVEL_NUM = "/menulevel/{menuLevel}";

	public static final String ADD_MENU = "/menulevel/addMenu";
	
	public static final String DELETE_MENU = "/menulevel/deleteMenu";
	
	public static final String SEARCH_MENU = "/menulevel/searchMenu";
	
	public static final String SEARCH_MENU_URL = MENUS + SEARCH_MENU;

	public static final String ADD_MENU_URL = MENUS + ADD_MENU;

	public static final String UPDATE_MENU = "/menulevel/updateMenu";
	
	public static final String MENU_LIST = "/menulevel/menuList";

	public static final String UPDATE_MENU_URL = MENUS + UPDATE_MENU;

	public static final String USER_MENUS_ROLES = "/usermenus/{roles}";

	public static final String USER_USER_ID = "/users";

	public static final String FIND_MENU_BY_MENU_CODE = "/menu/{menuCode}";

	public static final String FIND_MENU_BY_MENU_CODE_URL = MENUS + FIND_MENU_BY_MENU_CODE;

	public static final String UPDATE_IDM_CONTACT_NUM_EMAIL_ID = "/updateIDMProfileContactNumEmailId";

	public static final String UPDATE_IDM_CONTACT_NUM_EMAIL_ID_URL = PROFILE + UPDATE_IDM_CONTACT_NUM_EMAIL_ID;

	public static final String DELEGATE_USER = "/delegateUser";

	public static final String USER_GROUPS_ROLE_UPDATE = "/usergroups/updateRoles";

	public static final String USER_GROUPS_ROLE_FIND = "/usergroups/findRoles";

	public static final String USERS_UPDATE_TOKEN = USERS + "/updateToken";

	public static final String PROFILE_BY_USER_TYPE_ROLE_GRP = "/profByUserTypeRoleGrp";

	public static final String PROFILE_ID_BY_USER_TYPE = "/profIdUserType";

	public static final String MOBILE = "/mobile";

	public static final String MOBILE_ACCESS_TOKEN = "/accessToken";

	public static final String MOBILE_LOGIN = "/login";

	public static final String MOBILE_LOGOUT = "/logout";
	
	public static final String USER_GROUP_BRANCH = "/branch";

	public static final String USER_GROUP_BRANCH_SEARCH = USER_GROUP_BRANCH + SEARCH;
	
	public static final String USER_GROUP_BRANCH_CREATE = USER_GROUP_BRANCH + CREATE;
	
	public static final String USER_GROUP_BRANCH_UPDATE = USER_GROUP_BRANCH + UPDATE;
	
	public static final String USER_GROUP_BRANCH_DELETE = USER_GROUP_BRANCH + DELETE;
	
	public static final String ROLE_MENU = "/roleMenu";
	
	public static final String USER_CONFIG = "/allUserConfig";
	
	public static final String USER_CONFIG_UPDATE = "/updateUserConfig";
	
	public static final String FIND_USR_CONFIG_BY_CONFIG_CODE = "/userConfig/{configCode}";

	public static final String USER_GROUP_ROLE = "/userGroupRole";
	
	public static final String USER_ACTIVATION_GENERATE = "/activation/generate";
	
	public static final String USER_ACTIVATION_VERIFY = "/activation/verify";
	
	public static final String FIND_USER_GROUP_BY_CODE = "/byGroupCode/{groupCode}";
	
	public static final String FCM = "/fcm";
	
	public static final String FCM_PROFILE_FINDALL = FCM + FINDALL;
	
	public static final String FCM_PROFILE_CREATE = FCM + CREATE;
	
	public static final String FCM_PROFILE_UPDATE = FCM + UPDATE;
	
	public static final String FCM_PROFILE_DELETE = FCM + DELETE;
	
	public static final String FCM_PROFILE_FINDBY_FCMID = FCM + "/findByFcmId";
	
	public static final String FCM_PROFILE_FINDBY_USERID = FCM + "/findByUserId";
	
	public static final String FCM_SEARCH = FCM + SEARCH;

	public static final String FCM_DEVICE = "/fcmDevice";
	
	public static final String FCM_DEVICE_FINDALL = FCM_DEVICE + FINDALL;
	
	public static final String FCM_DEVICE_CREATE = FCM_DEVICE + CREATE;
	
	public static final String FCM_DEVICE_UPDATE = FCM_DEVICE + UPDATE;
	
	public static final String FCM_DEVICE_DELETE = FCM_DEVICE + DELETE;
	
	public static final String FCM_DEVICE_FINDBY_FCMID = FCM_DEVICE + "/findByFcmId";
	
	public static final String FCM_DEVICE_FINDBY_MACHINEID = FCM_DEVICE + "/findByMachineId";
	
	public static final String FCM_DEVICE_SEARCH = FCM_DEVICE + SEARCH;
	
	public static final String RPT_USER_REGISTERED_BY_DAY= "/report/countUserRegByDay";

}