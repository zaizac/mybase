/**
 * Copyright 2019
 */
package com.be.sdk.constants;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author nurul.naimma
 *
 * @since 19 Nov 2018
 */
public class IdmRoleConstants {

	private IdmRoleConstants() {
		throw new IllegalStateException("IdmRoleConstants class");
	}


	public static final String SYSTEM_ADMIN = "SYSTEM";

	public static final String DQ_ADMIN = "DQ_ADMIN";

	public static final String JIM_ADMIN = "JIM_ADMIN";

	public static final String DQ_USER = "DQ_USER";

	public static final String JIM_BPA_ADMIN = "JIM_BPA_ADMIN";

	public static final String JIM_BPA_USER = "JIM_BPA_USER";

	public static final String JIM_VPP_ADMIN = "JIM_VPP_ADMIN";

	public static final String JIM_VPP_USER = "JIM_VPP_USER";

	public static final String JIM_ESD_ADMIN = "JIM_ESD_ADMIN";

	public static final String JIM_ESD_USER = "JIM_ESD_USER";

	public static final String JIM_EMGS_ADMIN = "JIM_EMGS_ADMIN";

	public static final String JIM_EMGS_USER = "JIM_EMGS_USER";

	public static final String VA_USER = "VA_USER";

	public static final String OSC_USER = "OSC_USER";

	public static final String OSC_ADMIN = "OSC_ADMIN";

	public static final String RA_ADMIN = "RA_ADMIN";

	public static final String RA_USER = "RA_USER";

	public static final String USER_TYPE_BANK = "BANK";

	public static final String BANK_PORTAL_ADMIN = "BANK_PORTAL_ADMIN";

	public static final String BANK_HQ_ADMIN = "BANK_HQ_ADMIN";

	public static final String BANK_HQ_USER = "BANK_HQ_USER";

	public static final String BANK_BRANCH_ADMIN = "BANK_BRANCH_ADMIN";

	public static final String BANK_BRANCH_USER = "BANK_BRANCH_USER";

	public static final String BANK_SUPER_ADMIN = "BANK_SUPER_ADMIN";

	public static final String ROLE_HC_BGD = "EMB_HC_BGD";

	public static final String ROLE_HC_BGD_ADMIN = "EMB_HC_BGD_ADMIN";

	public static final String ROLE_HC_ADMIN_BGD = "HC_ADMIN_BGD";

	public static final String ROLE_HC_HC_BGD = "HC_HC_BGD";

	public static final List<String> ADMIN_ROLES;
	
	static {
		final List<String> list = new ArrayList<>();
		list.add(SYSTEM_ADMIN);
		list.add(DQ_ADMIN);
		list.add(JIM_ADMIN);
		list.add(OSC_ADMIN);
		list.add(RA_ADMIN);
		ADMIN_ROLES = Collections.unmodifiableList(list);
	}

}
