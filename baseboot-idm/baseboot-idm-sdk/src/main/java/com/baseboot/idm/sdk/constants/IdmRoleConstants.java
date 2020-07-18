/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.constants;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmRoleConstants {

	private IdmRoleConstants() {
		// Block Initialization
	}


	public static final String USER_TYPE_BANK = "BANK";

	public static final String SYSTEM_ADMIN = "SYSTEM";

	public static final String BANK_PORTAL_ADMIN = "BANK_PORTAL_ADMIN";

	public static final String BANK_HQ_ADMIN = "BANK_HQ_ADMIN";

	public static final String BANK_HQ_USER = "BANK_HQ_USER";

	public static final String BANK_BRANCH_ADMIN = "BANK_BRANCH_ADMIN";

	public static final String BANK_BRANCH_USER = "BANK_BRANCH_USER";

	public static final String BANK_SUPER_ADMIN = "BANK_SUPER_ADMIN";

	public static final String EGATE_ADMIN = "EGATE_ADMIN";

	public static final String EGATE_USER = "EGATE_USER";


	public static List<String> getAdminRoles() {
		List<String> adminRoles = new ArrayList<>();
		adminRoles.add(SYSTEM_ADMIN);
		adminRoles.add(BANK_PORTAL_ADMIN);
		adminRoles.add(BANK_HQ_ADMIN);
		adminRoles.add(BANK_BRANCH_ADMIN);
		return adminRoles;
	}

}