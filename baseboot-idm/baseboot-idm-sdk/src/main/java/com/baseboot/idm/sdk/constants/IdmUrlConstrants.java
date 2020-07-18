/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 7, 2018
 */
public class IdmUrlConstrants {

	private IdmUrlConstrants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String SERVICE_CHECK = "/serviceCheck";

	public static final String ENCRYPT = "/encrypt";

	public static final String DECRYPT = "/decrypt";

	public static final String LOGIN = "/login";

	public static final String LOGOUT = "/logout";

	public static final String TOKENS = "/tokens";

	public static final String CLIENTS = TOKENS + "/clients";

	public static final String AUDIT_ACTION = "/audit";

	public static final String PROFILE = "/profiles";

	public static final String PROFILE_DEACTIVATE = PROFILE + "/deactivate";

	public static final String PROFILE_ACTIVATE = PROFILE + "/activate";

	public static final String SEARCH_PAGINATION = "/search/paginated";

	public static final String ROLES = "/roles";

	public static final String REFERENCE = "/references";

	public static final String USER_GROUPS = "/usergroups";

	public static final String USER_TYPE = "/usertype";

	public static final String USERS = "/users";

	public static final String USERSGROUPS = "/usergroups";

	public static final String USERS_FORGOT_PASSWORD = USERS + "/forgotPassword";

	public static final String USERS_CHANGE_PASSWORD = USERS + "/changePassword";

	public static final String USERS_CHANGE_PASSWORD_DIRECT = USERS + "/changePassword/directUpdate";

	public static final String USERS_ACTIVATE = USERS + "/activateUser";

	public static final String STATIC = "/static";

	public static final String MENUS = "/menus";

	public static final String MENUS_USERS = "/users/{userId}";

	public static final String MENU_LEVEL_NUM = "/menulevel/{menuLevel}";

	public static final String ADD_MENU = "/menulevel/addMenu";

	public static final String ADD_MENU_URL = MENUS + ADD_MENU;

	public static final String UPDATE_MENU = "/menulevel/updateMenu";

	public static final String UPDATE_MENU_URL = MENUS + UPDATE_MENU;

	public static final String USER_MENUS_ROLES = "/usermenus/{roles}";

	public static final String USER_USER_ID = "/users/{userId}";

	public static final String FIND_MENU_BY_MENU_CODE = "/menu/{menuCode}";

	public static final String FIND_MENU_BY_MENU_CODE_URL = MENUS + FIND_MENU_BY_MENU_CODE;

	public static final String UPDATE_IDM_CONTACT_NUM_EMAIL_ID = "/updateIDMProfileContactNumEmailId";

	public static final String UPDATE_IDM_CONTACT_NUM_EMAIL_ID_URL = PROFILE + UPDATE_IDM_CONTACT_NUM_EMAIL_ID;

	public static final String DELEGATE_USER = "/delegateUser";

	public static final String USER_GROUPS_ROLE_UPDATE = "/usergroups/updateRoles";

	public static final String USER_GROUPS_ROLE_FIND = "/usergroups/findRoles";

	public static final String USERS_UPDATE_TOKEN = USERS + "/updateToken";
}