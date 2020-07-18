/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since 02/10/2014
 */
public final class CmnConstants {

	private CmnConstants() {
		throw new IllegalStateException("CmnConstants Utility class");
	}


	public static final Boolean USE_REST_SERVICE = true;

	public static final String EMPTY_STRING = "";

	public static final String SPACE = " ";

	public static final String COMMA = ",";

	public static final Integer ZERO = 0;

	public static final Integer ONE = 1;

	public static final String DIGIT_0 = "0";

	public static final String DIGIT_1 = "1";

	public static final String DIGIT_2 = "2";

	public static final String DIGIT_3 = "3";

	public static final String YES_SHORT = "Y";

	public static final String NO_SHORT = "N";

	public static final String YES_FULL = "YES";

	public static final String NO_FULL = "NO";

	public static final String CUSTOM_FULL = "CUSTOM";

	public static final String NULL = "NULL";

	public static final String QUEUE_IND_INBOXED = "I";

	public static final String QUEUE_IND_QUEUED = "Q";

	public static final String QUEUE_IND_HC = "QH";

	public static final String QUEUE_IND_BMET = "QB";

	public static final String QUEUE_IND_HC_BMET = "QHB";

	public static final String ASSGN_TYPE_MANUAL = "M";

	public static final String ASSGN_TYPE_AUTO = "I";

	public static final String BEAN_SCOPE_PROTOTYPE = "prototype";

	public static final String REMARK_RELEASE_TASK = "~~~RELEASED TASK BY OFFICER~~~";

	public static final String REMARK_CLAIM_TASK = "~~~CLAIM TASK BY OFFICER~~~";

	public static final String REMARK_UPDATE_TASK = "~~~UPDATE TASK~~~";

	public static final String APP_AMENDED = "AMND";

	public static final String LEGEND_COLOR = "#90a4ae";

	/** TASK HISTORY ACTION */
	public static final String ACTION_CLAIM = "CLAIMED";

	public static final String ACTION_RELEASE = "RELEASED";

	public static final String ACTION_START = "STARTED";

	public static final String ACTION_COMPLETE = "COMPLETED";

	public static final String BTN_CREATE = "Create";

	public static final String BTN_UPDATE = "Update";

	public static final String BTN_CANCEL = "Cancel";

	public static final String BTN_BACK = "Back";

	public static final String REFERENCE_TYPE = "Type";

	public static final String REFERENCE_LEVEl = "Level";

	public static final String REFERENCE_STATUS = "Status";

	public static final String NEW = "NEW";
}
