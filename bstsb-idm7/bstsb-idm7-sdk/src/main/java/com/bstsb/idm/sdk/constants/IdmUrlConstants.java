/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.constants;


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

	public static final String ROLES = "/roles";

	public static final String REFERENCE = "/references";

	public static final String CONFIG = "/config";

	public static final String USER_GROUPS = "/usergroups";

	public static final String USER_TYPE = "/usertype";

	public static final String USERS = "/users";

	public static final String USERSGROUPS = "/usergroups";

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

	public static final String ADD_MENU_URL = MENUS + ADD_MENU;

	public static final String UPDATE_MENU = "/menulevel/updateMenu";

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

	public static final String USER_GROUPS_BRANCH_SEARCH = "/rolebranch/search";

	public static final String ADD_SOCIAL_USER = USERS + "/social";

	public static final String SOCIAL_LOGIN = "/social/login";

	public static final String FIND_ALL_USER_CONN = "/social/connections/all";

	public static final String FIND_ALL_USER_CONN_USERID = "/social/connections/userAll";

	public static final String FIND_ALL_USER_CONN_USERID_PROVID = "/social/connections/userConn";

	public static final String UPDATE_USER_CONN = "/social/connections/update";

	public static final String CREATE_USER_CONN = "/social/connections/createConnection";

	public static final String DELETE_USER_CONN = "/social/connections/remove";

	public static final String DELETE_USER_CONN_ALL_KEYS = "/social/connections/removeByAllKeys";

	public static final String FIND_PRIMARY_USER_CONN = "/social/connections/primary";

	public static final String FIND_USERID_USER_CONN = "/social/connections/findUserId";

	public static final String FIND_USERID_USERS_CONN = "/social/connections/findUserIds";

	// social for IDM_USER_CONNECTION
	public static final String FIND_USER_CONN = "/social/connections/user";

	public static final String FIND_CONNS_TO_USERS = "/social/connections/connectionsToUsers";

	public static final String FIND_SOCIAL_USER = "/social/social/finduser";

	public static final String FIND_SOCIAL_USER_BY_USERID = "/social/social/finduserByUserId"; // social

	public static final String FIND_SOCIAL_USER_BY_PROVID = "/social/social/findSocialUserByProviderId";

	public static final String FIND_CONN_MAX_RANK = "/social/connections/maxRank";

}