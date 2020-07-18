/**
 *
 */
package com.wfw.sdk.constants;


/**
 * @author Kamruzzaman
 * @since 30/10/2015
 */
public final class WorkflowConstants {

	private WorkflowConstants() {
		throw new IllegalStateException("WorkflowConstants Utility class");
	}


	public static final String SLASH = "/";

	public static final String DASH = "-";

	public static final String TASKS = "/wf/tasks";

	public static final String TEST = "/test";

	public static final String SERVICE_CHECK = "/api/v1/serviceCheck";

	public static final String TASK_DETAILS = "/{taskId}";

	public static final String TASK_BY_ID = "/getTaskById";

	public static final String CLAIMED_DATE = "/claimedDate/{taskId}";

	public static final String CLAIMED_DATE1 = "/claimedDate1/{taskId}";

	public static final String STATUS_LIST = "/statuses/{taskId}";

	public static final String CLAIM_TASK = "/claim";

	public static final String UPDATE_TASK = "/update";

	public static final String UPDATE_TASK_VERIFIER = "/updatekdnver";

	public static final String RELEASE_TASK = "/release";

	public static final String RELEASE_TASK_GROUP_ADMIN = "/release-admin";

	public static final String REJECT_TASK = "/reject";

	public static final String START_TASK = "/start";

	public static final String START_MULTIPLE_TASK = "/startmultiple";

	public static final String AMEND_TASK = "/amend";

	public static final String COMPLETE_TASK = "/complete";

	public static final String COMPLETE_START_TASK = "/completeAndStart";

	public static final String APPLICATION_LIST = "/applications";

	public static final String DRAFT_APPLICATION_LIST = "/draftapplications";

	public static final String TASK_REMARKS = "/remarks";

	public static final String TASK_REMARKS_TASK_ID = "/remarksTaskId";

	public static final String INTERVIEW_ENQUIRY = "/interviewEnquiry";

	public static final String HISTORY_BY_TASKID = "/historyByTaskId";

	public static final String UPDATE_TASK_ID = "/updateTaskId";

	public static final String UPDATE_INTERVIEW_DATE = "/updateIvDate";

	// Transation Codes
	public static final String TRANS_JS_UPD_PROFILE = "JSPROF";

	public static final String TRANS_EMP_UPD_PROFILE = "EMPPROF";

	public static final String TRANS_RA_UPD_PROFILE = "RAPROF";

	public static final String TRANS_JOB_OPNG_SUBMIT = "JOBOPN";

	// module
	public static final String MODULE_JLS = "JLS";

	// Transation Codes
	public static final String ACION_UPDATE_TASK = "UT";

	public static final String ACION_REJECT_TASK = "RT";

	public static final String ACION_START_TASK = "ST";

	public static final String ACION_COMPLETE_TASK = "CT";

	public static final String ACION_COMPLETE_START_TASK = "CST";

	public static final String REMARK_RANGE_OFFICER = "Officers";

	public static final String REMARK_RANGE_LATEST = "Latest";

	public static final String REMARK_RANGE_ALL = "All";

	public static final String SYSTEM_USER_NAME = "SYSTEM";

	public static final String AMEND_STS_CODE = "AMND";

	// STATUS CODE
	public static final String JS_PROF_SUBMIT = "SUB";

	public static final String EMP_PROF_SUBMIT = "SUB";

	public static final String RA_PROF_SUBMIT = "SUB";

	public static final String JOB_OPNG_SUBMIT = "SUB";

	/** New */
	public static final String TASK = "/wfw/task";

	public static final String MASTER = "/master";

	public static final String HISTORY = "/history";

	public static final String REFERENCE = "/reference";

	public static final String TYPE = "/type";

	public static final String TYPE_ACTION = "/typeAction";

	public static final String LEVEL = "/level";

	public static final String STATUS = "/status";

	public static final String PAGINATION = "/pagination";

	public static final String ADD_OR_UPDATE = "/addUpdate";
	
	public static final String REG_NO_APPLICANT = "/regNoApplicant";
	
	//public static final String REG_NO_APPLICANT = "/regNoApplicant";

	/** List Type */
	public static final String INBOX = "inbox";

	public static final String POOL = "pool";
}
