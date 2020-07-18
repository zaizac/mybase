/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.constants;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class IdmRoleConstants {

	private IdmRoleConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String SYSTEM_ADMIN = "SYSTEM";

	public static final String INT_ADMIN = "INT_ADMIN";

	public static final String JLS_ADMIN = "JLS_ADMIN";

	public static final String GOV_ADMIN = "GOV_ADMIN";

	public static final String EMP_ADMIN = "EMP_ADMIN";

	public static final String RA_ADMIN = "RA_ADMIN";

	public static final String WRKR_ADMIN = "WRKR_ADMIN";

	public static final String WRKR_USER = "WRKR_USER";

	public static final String DQ_USER = "DQ_USER";

	public static final String USER_ROLE_GROUP_CODE_EMP_OWNER = "EMP_OWNER";

	public static final String USER_ROLE_GROUP_CODE_EMP_PIC = "EMP_PIC";

	public static final String USER_ROLE_GROUP_CODE_RA_OWNER = "RA_OWNER";

	public static final String USER_ROLE_GROUP_CODE_RA_PIC = "RA_PIC";

	public static final String USER_ROLE_GROUP_CODE_JOB_SKR = "JOB_SKR";


	public static List<String> getAdminRoles() {
		List<String> adminRoles = new ArrayList<>();
		adminRoles.add(SYSTEM_ADMIN);
		adminRoles.add(INT_ADMIN);
		adminRoles.add(GOV_ADMIN);
		adminRoles.add(EMP_ADMIN);
		adminRoles.add(RA_ADMIN);
		return adminRoles;
	}

}