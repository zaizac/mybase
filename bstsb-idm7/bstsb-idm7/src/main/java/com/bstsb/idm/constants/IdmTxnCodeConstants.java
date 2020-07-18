/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.constants;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class IdmTxnCodeConstants {

	private IdmTxnCodeConstants() {
		// Block Initialization
	}


	public static final String HEADER_MESSAGE_ID = "X-Message-Id";

	public static final String HEADER_AUTHORIZATION = "Authorization";

	public static final String PERMISSION_CODE = "eqmTxnNo";

	public static final String TXN_REF_NO = "eqmTxnRefNo";

	public static final String IDM_AUTH_LOGIN = "IDM_AUTH_LOGIN";

	public static final String IDM_AUTH_LOGOUT = "IDM_AUTH_LOGOUT";

	public static final String IDM_TOKEN_NEW = "IDM_TOKEN_NEW";

	public static final String IDM_TOKEN_VALIDATE = "IDM_TOKEN_VALIDATE";

	public static final String IDM_TOKEN_DEL = "IDM_TOKEN_DEL";

	public static final String IDM_TOKEN_GET = "IDM_TOKEN_GET";

	public static final String IDM_TOKEN_FIND = "IDM_TOKEN_FIND";

	public static final String IDM_USER_NEW = "IDM_USER_NEW";

	public static final String IDM_USER_UPD = "IDM_USER_UPD";

	public static final String IDM_USER_DEL = "IDM_USER_DEL";

	public static final String IDM_USER_GET = "IDM_USER_GET";

	public static final String IDM_USER_FIND = "IDM_USER_FIND";

	public static final String IDM_USER_ROLE = "IDM_USER_ROLE";

	public static final String IDM_PROF_UPD = "IDM_PROF_UPD";

	public static final String IDM_PROF_GET = "IDM_PROF_GET";

	public static final String IDM_PROF_FIND = "IDM_PROF_FIND";

	public static final String IDM_ROLE_NEW = "IDM_ROLE_NEW";

	public static final String IDM_ROLE_UPD = "IDM_ROLE_UPD";

	public static final String IDM_ROLE_DEL = "IDM_ROLE_DEL";

	public static final String IDM_ROLE_GET = "IDM_ROLE_GET";

	public static final String IDM_ROLE_FIND = "IDM_ROLE_FIND";

	public static final String IDM_ROLE_PERMS = "IDM_ROLE_PERMS";

	public static final String IDM_ROLE_USERS = "IDM_ROLE_USERS";

	public static final String IDM_ROLE_MENU = "IDM_ROLE_MENU";

	public static final String IDM_FORGOT_PWORD = "IDM_FORGOT_PWORD";

	public static final String IDM_ACTIVATE_USER = "IDM_ACTIVATE_USER";

	public static final String IDM_CHANGE_PWORD = "IDM_CHANGE_PWORD";

	public static final String IDM_PAYLOAD_NEW = "IDM_PAYLOAD_NEW";

	public static final String IDM_ENCRYPT = "IDM_ENCRYPT";

	public static final String IDM_DECRYPT = "IDM_DECRYPT";

	public static final String IDM_ACTIVATE_USER_ROLE = "IDM_ACTIVATE_USER_ROLE";

	public static final String IDM_DEACTIVATE_USER = "IDM_DEACTIVATE_USER";

	// IGNORED LIST of TXN_NO for Audit Trail
	public static final List<String> IDM_AUDIT_TRAIL_IGNORED_LIST = Collections
			.unmodifiableList(Arrays.asList(IDM_TOKEN_VALIDATE, IDM_ENCRYPT, IDM_DECRYPT));

}
