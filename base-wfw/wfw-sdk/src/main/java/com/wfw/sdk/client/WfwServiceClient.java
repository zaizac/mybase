package com.wfw.sdk.client;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.http.HttpAuthClient;
import com.util.model.ServiceCheck;
import com.util.pagination.DataTableResults;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.InboxHist;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.MultipleTaskDetails;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.model.TaskDetails;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.TaskRemark;
import com.wfw.sdk.model.TaskStatus;
import com.wfw.sdk.model.WfwPayload;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author Md. Kamruzzaman
 * @since Sep 10, 2015
 */
public class WfwServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwServiceClient.class);

	private static WfwRestTemplate restTemplate;

	private String url;

	private String clientId;

	private String token;

	private String authToken;

	private String messageId;
	
	private String portalType;

	private int readTimeout;

	private static final String PARAM_TASKID = "/{taskId}";

	private static final String TASKID_TEXT = "taskId";

	private static final String PARAM_REPLACE_DASH = "?replaceDash={replaceDash}";

	private static final String REPLACE_DASH_TEXT = "replaceDash";

	private static final String APP_DATE_FROM_TEXT = "appDateFrom";

	private static final String APP_DATE_TO_TEXT = "appDateTo";

	private static final String APPROVE_DATE_FROM_TEXT = "apprveDtFrm";

	private static final String APPROVE_DATE_TO_TEXT = "apprveDtTo";

	private static final String COMPANY_REG_NO_TEXT = "cmpnyRegNo";

	private static final String PARAM_IS_CLAIM = "?isClaimed={isClaimed}";

	private static final String PARAM_TASKIDS = "&taskIds={taskIds}";

	private static final String PARAM_REFNO = "&refNo={refNo}";

	private static final String PARAM_RECRUITMENT_AGENT = "&recruitmentAgent={recruitmentAgent}";

	private static final String PARAM_RA_CMPNY_REG_NO = "&raCmpnyRegNo={raCmpnyRegNo}";

	private static final String PARAM_RA_PROFILE_ID = "&raProfileId={raProfileId}";

	private static final String PARAM_MODULES = "&modules={modules}";

	private static final String PARAM_ROLES = "&roles={roles}";

	private static final String PARAM_TASK_AUTHOR_ID = "&taskAuthorId={taskAuthorId}";

	private static final String PARAM_APP_DATE_FROM = "&appDateFrom={appDateFrom}";

	private static final String PARAM_APP_DATE_TO = "&appDateTo={appDateTo}";

	private static final String PARAM_CMPNY_REG_NO = "&cmpnyRegNo={cmpnyRegNo}";

	private static final String PARAM_QUEUE_IND = "&queueInd={queueInd}";

	private static final String PARAM_IS_DISPLAY = "&isDisplay={isDisplay}";

	private static final String PARAM_CMPNY_NAME = "&cmpnyName={cmpnyName}";

	private static final String PARAM_APPL_STATUS_CODE = "&applStatusCode={applStatusCode}";

	private static final String PARAM_APPROVE_DATE_FROM = "&apprveDtFrm={apprveDtFrm}";

	private static final String PARAM_APPROVE_DATE_TO = "&apprveDtTo={apprveDtTo}";

	private static final String PARAM_PYMT_INVOICE_NO = "&pymtInvoiceNo={pymtInvoiceNo}";

	private static final String PARAM_IS_QUOTA_SEC = "&isQuotaSec={isQuotaSec}";

	private static final String PARAM_PORTAL_TYPE = "&portalType={portalType}";

	private static final String IS_CLAIM_TEXT = "isClaimed";

	private static final String TASK_IDS_TEXT = "taskIds";

	private static final String REF_NO_TEXT = "refNo";

	private static final String RECRUITMENT_AGENT_TEXT = "recruitmentAgent";

	private static final String RA_CMPNY_REG_NO_TEXT = "raCmpnyRegNo";

	private static final String MODULES_TEXT = "modules";

	private static final String ROLES_TEXT = "roles";

	private static final String TASK_AUTHOR_ID_TEXT = "taskAuthorId";

	private static final String QUEUE_IND_TEXT = "queueInd";

	private static final String IS_DISPLAY_TEXT = "isDisplay";

	private static final String CMPNY_NAME_TEXT = "cmpnyName";

	private static final String APPL_STATUS_CODE_TEXT = "applStatusCode";

	private static final String PYMT_INVOICE_NO_TEXT = "pymtInvoiceNo";

	private static final String IS_QUOTA_SEC_TEXT = "isQuotaSec";

	private static final String RA_PROFILE_ID_TEXT = "raProfileId";

	static {
		restTemplate = new WfwRestTemplate();
	}


	public WfwServiceClient(String url) {
		this(url, null, 0);
	}


	public WfwServiceClient(String url, int readTimeout) {
		this(url, null, readTimeout);
	}


	public WfwServiceClient(String url, String clientId, int readTimeout) {
		this.url = url;
		this.clientId = clientId;
		this.readTimeout = readTimeout;
	}


	private WfwRestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = null;
		if (messageId == null) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW011);
		}
		if (authToken != null) {
			if(portalType != null) {
				httpClient = new HttpAuthClient(authToken, messageId, readTimeout, portalType).getHttpClient();
			} else {
				httpClient = new HttpAuthClient(authToken, messageId, readTimeout).getHttpClient();
			}
		} else {
			if(portalType != null) {
				httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout, portalType).getHttpClient();
			} else {
				httpClient = new HttpAuthClient(clientId, token, messageId, readTimeout).getHttpClient();
			}
		}
		restTemplate.setHttpClient(httpClient);
		return restTemplate;
	}


	private String getServiceURI(String serviceName) {
		String uri = url + serviceName;
		LOGGER.info("Service Rest URL: {}", uri);
		return uri;
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


	public String checkConnection() {
		return getRestTemplate().getForObject(getServiceURI(WorkflowConstants.SERVICE_CHECK + "/test"), String.class);
	}


	public ServiceCheck serviceTest() {
		return getRestTemplate().getForObject(getServiceURI(WorkflowConstants.SERVICE_CHECK), ServiceCheck.class);
	}


	@Deprecated
	public List<TaskDetails> getApplicationList(WfwPayload payload) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.APPLICATION_LIST);
		sb.append("?1=1");

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonStr = mapper.valueToTree(payload);
		Iterator<String> it = jsonStr.fieldNames();

		while (it.hasNext()) {
			String fieldName = it.next();
			if (!BaseUtil.isObjNull(fieldName)) {
				LOGGER.info("PARAMS: {} - {}", fieldName, jsonStr.get(fieldName));
				if (BaseUtil.isEqualsCaseIgnore(APP_DATE_FROM_TEXT, fieldName)) {
					sb.append("&" + fieldName + "="
							+ CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
				} else if (BaseUtil.isEqualsCaseIgnore(APP_DATE_TO_TEXT, fieldName)) {
					sb.append("&" + fieldName + "="
							+ CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
				} else if (BaseUtil.isEqualsCaseIgnore(APPROVE_DATE_FROM_TEXT, fieldName)) {
					sb.append("&" + fieldName + "="
							+ CmnUtil.convertDate(payload.getApprveDtFrm(), BaseConstants.DT_DD_MM_YYYY_SLASH));
				} else if (BaseUtil.isEqualsCaseIgnore(APPROVE_DATE_TO_TEXT, fieldName)) {
					sb.append("&" + fieldName + "="
							+ CmnUtil.convertDate(payload.getApprveDtTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
				} else {
					sb.append("&" + fieldName + "=" + URLEncoder.encode(jsonStr.get(fieldName).asText(), "UTF-8"));
				}
			}
		}
		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<TaskDetails> findDraftApplicationsByDate(String draftStatus, String durationDate) {

		if (CmnUtil.isObjNull(durationDate)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFWWP003);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.DRAFT_APPLICATION_LIST);
		sb.append("?draftStatus=" + draftStatus);
		sb.append("&durationDate=" + durationDate);
		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<InterviewEnquiry> findInterviewForEnquiry(String sectorAgency, String intrvwDtFrm, String intrvwDtTo) {

		if (CmnUtil.isObjNull(intrvwDtFrm) || CmnUtil.isObjNull(intrvwDtTo)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFWWP003);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.INTERVIEW_ENQUIRY);
		sb.append("?intrvwDtFrm=" + intrvwDtFrm);
		sb.append("&intrvwDtTo=" + intrvwDtTo);
		if (!CmnUtil.isObjNull(sectorAgency)) {
			sb.append("&sectorAgency=" + sectorAgency);
		}

		InterviewEnquiry[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()),
				InterviewEnquiry[].class);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<TaskDetails> getTaskList(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_REFNO);
		sb.append(PARAM_RECRUITMENT_AGENT);
		sb.append(PARAM_RA_CMPNY_REG_NO);
		sb.append(PARAM_RA_PROFILE_ID);
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_APP_DATE_FROM);
		sb.append(PARAM_APP_DATE_TO);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_IS_DISPLAY);
		sb.append(PARAM_CMPNY_NAME);
		sb.append(PARAM_APPL_STATUS_CODE);
		sb.append(PARAM_APPROVE_DATE_FROM);
		sb.append(PARAM_APPROVE_DATE_TO);
		sb.append(PARAM_PYMT_INVOICE_NO);
		sb.append(PARAM_IS_QUOTA_SEC);
		sb.append(PARAM_PORTAL_TYPE);
		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "false");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		params.put(REF_NO_TEXT, payload.getRefNo());
		params.put(RECRUITMENT_AGENT_TEXT, payload.getRecruitmentAgent());
		params.put(RA_CMPNY_REG_NO_TEXT, payload.getRaCmpnyRegNo());
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(APP_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APP_DATE_TO_TEXT, CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(CMPNY_NAME_TEXT, payload.getCmpnyName());
		params.put(APPL_STATUS_CODE_TEXT, payload.getApplStatusCode());
		params.put(APPROVE_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getApprveDtFrm(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APPROVE_DATE_TO_TEXT,
				CmnUtil.convertDate(payload.getApprveDtTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(PYMT_INVOICE_NO_TEXT, payload.getInvoiceNo());
		params.put(IS_QUOTA_SEC_TEXT, payload.getIsQuotaSec());
		params.put("portalType", payload.getPortalType());
		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}


	// Yella
	@Deprecated
	public List<TaskDetails> getTaskList1(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_RA_PROFILE_ID);
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_CMPNY_NAME);
		sb.append(PARAM_IS_DISPLAY);

		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "false");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		int raProfileId = 0;
		if (payload.getRaProfileId() != null && payload.getRaProfileId() > 0) {
			raProfileId = payload.getRaProfileId();
		}
		params.put(RA_PROFILE_ID_TEXT, Integer.toString(raProfileId));
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(CMPNY_NAME_TEXT, payload.getCmpnyName());

		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}
	// Yellamanda


	@Deprecated
	public List<TaskDetails> getMyTaskList(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_REFNO);
		sb.append(PARAM_RECRUITMENT_AGENT);
		sb.append(PARAM_RA_CMPNY_REG_NO);
		sb.append(PARAM_RA_PROFILE_ID);
		// adding for search
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_APP_DATE_FROM);
		sb.append(PARAM_APP_DATE_TO);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_IS_DISPLAY);
		sb.append(PARAM_APPL_STATUS_CODE);
		sb.append(PARAM_APPROVE_DATE_FROM);
		sb.append(PARAM_APPROVE_DATE_TO);
		sb.append(PARAM_PYMT_INVOICE_NO);
		sb.append(PARAM_CMPNY_NAME);
		sb.append(PARAM_IS_QUOTA_SEC);
		sb.append(PARAM_PORTAL_TYPE);
		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "true");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		params.put(REF_NO_TEXT, payload.getRefNo());
		params.put(RECRUITMENT_AGENT_TEXT, payload.getRecruitmentAgent());
		params.put(RA_CMPNY_REG_NO_TEXT, payload.getRaCmpnyRegNo());
		int raProfileId = 0;
		if (payload.getRaProfileId() != null && payload.getRaProfileId() > 0) {
			raProfileId = payload.getRaProfileId();
		}
		params.put(RA_PROFILE_ID_TEXT, Integer.toString(raProfileId));
		// start yell
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(APP_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APP_DATE_TO_TEXT, CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(APPL_STATUS_CODE_TEXT, payload.getApplStatusCode());
		params.put(APPROVE_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getApprveDtFrm(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APPROVE_DATE_TO_TEXT,
				CmnUtil.convertDate(payload.getApprveDtTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(PYMT_INVOICE_NO_TEXT, payload.getInvoiceNo());
		params.put(CMPNY_NAME_TEXT, payload.getCmpnyName());
		params.put(IS_QUOTA_SEC_TEXT, payload.getIsQuotaSec());
		params.put("portalType", payload.getPortalType());

		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}


	// Adding for Pool Searching
	@Deprecated
	public List<TaskDetails> getMyPoolSearchList(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_REFNO);
		sb.append(PARAM_RECRUITMENT_AGENT);
		sb.append(PARAM_RA_CMPNY_REG_NO);
		sb.append(PARAM_RA_PROFILE_ID);
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_APP_DATE_FROM);
		sb.append(PARAM_APP_DATE_TO);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_IS_DISPLAY);
		sb.append(PARAM_CMPNY_NAME);
		sb.append(PARAM_APPL_STATUS_CODE);

		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "true");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		params.put(REF_NO_TEXT, payload.getRefNo());
		params.put(RECRUITMENT_AGENT_TEXT, payload.getRecruitmentAgent());
		params.put(RA_CMPNY_REG_NO_TEXT, payload.getRaCmpnyRegNo());
		int raProfileId = 0;
		if (payload.getRaProfileId() != null && payload.getRaProfileId() > 0) {
			raProfileId = payload.getRaProfileId();
		}
		params.put(RA_PROFILE_ID_TEXT, Integer.toString(raProfileId));
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(APP_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APP_DATE_TO_TEXT, CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(APPL_STATUS_CODE_TEXT, payload.getApplStatusCode());

		params.put(CMPNY_NAME_TEXT, payload.getCmpnyName());
		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<TaskDetails> getGroupAdminTaskList(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_REFNO);
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_APP_DATE_FROM);
		sb.append(PARAM_APP_DATE_TO);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_IS_DISPLAY);
		sb.append(PARAM_CMPNY_NAME);
		sb.append(PARAM_APPL_STATUS_CODE);

		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "groupAdmin");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		params.put(REF_NO_TEXT, payload.getRefNo());
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(APP_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APP_DATE_TO_TEXT, CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(APPL_STATUS_CODE_TEXT, payload.getApplStatusCode());
		params.put(CMPNY_NAME_TEXT, payload.getCmpnyName());

		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<TaskDetails> getMyTaskListRandom(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_IS_CLAIM);
		sb.append(PARAM_TASKIDS);
		sb.append(PARAM_REFNO);
		sb.append(PARAM_MODULES);
		sb.append(PARAM_ROLES);
		sb.append(PARAM_TASK_AUTHOR_ID);
		sb.append(PARAM_APP_DATE_FROM);
		sb.append(PARAM_APP_DATE_TO);
		sb.append(PARAM_CMPNY_REG_NO);
		sb.append(PARAM_QUEUE_IND);
		sb.append(PARAM_IS_DISPLAY);
		sb.append(PARAM_APPL_STATUS_CODE);
		sb.append(PARAM_IS_QUOTA_SEC);

		Map<String, Object> params = new HashMap<>();
		params.put(IS_CLAIM_TEXT, "claimRandom");
		params.put(TASK_IDS_TEXT, payload.getTaskIds());
		params.put(REF_NO_TEXT, payload.getRefNo());
		params.put(MODULES_TEXT, payload.getModules());
		params.put(ROLES_TEXT, payload.getRoles());
		params.put(TASK_AUTHOR_ID_TEXT, payload.getTaskAuthorId());
		params.put(APP_DATE_FROM_TEXT,
				CmnUtil.convertDate(payload.getAppDateFrom(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(APP_DATE_TO_TEXT, CmnUtil.convertDate(payload.getAppDateTo(), BaseConstants.DT_DD_MM_YYYY_SLASH));
		params.put(COMPANY_REG_NO_TEXT, payload.getCmpnyRegNo());
		params.put(QUEUE_IND_TEXT, payload.getQueueInd());
		params.put(IS_DISPLAY_TEXT, payload.getIsDisplay());
		params.put(APPL_STATUS_CODE_TEXT, payload.getApplStatusCode());
		params.put(IS_QUOTA_SEC_TEXT, payload.getIsQuotaSec());

		TaskDetails[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails[].class,
				params);
		return Arrays.asList(resp);
	}


	@Deprecated
	public List<TaskRemark> remarksService(String range, String refNo, String appStautsCodes) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.TASK_REMARKS);
		sb.append("?range={range}");
		sb.append(PARAM_REFNO);
		sb.append(PARAM_APPL_STATUS_CODE);

		Map<String, Object> params = new HashMap<>();
		params.put("range", range);
		params.put(REF_NO_TEXT, refNo);
		params.put(APPL_STATUS_CODE_TEXT, appStautsCodes);

		TaskRemark[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskRemark[].class, params);
		return Arrays.asList(resp);
	}


	@Deprecated
	public TaskRemark getLatestRemarksByTaskId(String refNo, String appStautsCodes) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.TASK_REMARKS_TASK_ID);
		sb.append("?refNo={refNo}");
		sb.append(PARAM_APPL_STATUS_CODE);

		Map<String, Object> params = new HashMap<>();
		params.put(REF_NO_TEXT, refNo);
		params.put(APPL_STATUS_CODE_TEXT, appStautsCodes);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskRemark.class, params);
	}


	// Return all remars excluding claim, release, update action (Status code
	// example : VER1,JTK,KDN)
	@Deprecated
	public List<TaskRemark> getAllOfficerRemarks(String refNo, String appStautsCodes) {
		return getAllOfficerRemarks(refNo, appStautsCodes, CmnConstants.YES_FULL);
	}


	@Deprecated
	public List<TaskRemark> getAllOfficerRemarks(String refNo, String appStautsCodes, String replaceDash) {

		if (CmnUtil.isEquals(replaceDash, CmnConstants.YES_FULL)) {
			refNo = refNo.replaceAll("-", "/");
		}

		List<TaskRemark> officersRemark = remarksService(WorkflowConstants.REMARK_RANGE_OFFICER, refNo,
				appStautsCodes);
		if (CmnUtil.isListNull(officersRemark)) {
			officersRemark = new ArrayList<>();
		}
		return officersRemark;
	}


	// Return all remarks including claim, realse, update action (Status code
	// example : VER1,JTK,KDN)
	@Deprecated
	public List<TaskRemark> getAllRemarks(String refNo, String appStautsCodes) {
		return getAllOfficerRemarks(refNo, appStautsCodes, CmnConstants.YES_FULL);
	}


	@Deprecated
	public List<TaskRemark> getAllRemarks(String refNo, String appStautsCodes, String replaceDash) {

		if (CmnUtil.isEquals(replaceDash, CmnConstants.YES_FULL)) {
			refNo = refNo.replaceAll("-", "/");
		}

		List<TaskRemark> remarkList = remarksService(WorkflowConstants.REMARK_RANGE_ALL, refNo, appStautsCodes);
		if (CmnUtil.isListNull(remarkList)) {
			remarkList = new ArrayList<>();
		}
		return remarkList;
	}


	// Return latest remark for a task with specific statuscode (ex: VER1, JTK,
	// KDN)
	@Deprecated
	public TaskRemark getLatestRemarks(String refNo, String appStautsCodes) {
		return getLatestRemarks(refNo, appStautsCodes, CmnConstants.YES_FULL);
	}


	@Deprecated
	public TaskRemark getLatestRemarks(String refNo, String appStautsCodes, String replaceDash) {

		if (CmnUtil.isEquals(replaceDash, CmnConstants.YES_FULL)) {
			refNo = refNo.replaceAll("-", "/");
		}

		TaskRemark taskRemark;
		List<TaskRemark> remarkList = remarksService(WorkflowConstants.REMARK_RANGE_LATEST, refNo, appStautsCodes);
		if (CmnUtil.isListNull(remarkList)) {
			taskRemark = null;
		} else {
			taskRemark = remarkList.get(0);
		}
		return taskRemark;
	}


	@Deprecated
	public TaskRemark getRemarkByAppStatus(List<TaskRemark> remarkList, String appStautsCodes) {
		TaskRemark taskRemark = null;

		for (TaskRemark remark : remarkList) {
			if (CmnUtil.isEqualsCaseIgnore(remark.getApplStsCode(), appStautsCodes)) {
				taskRemark = remark;
			}
		}

		return taskRemark;
	}


	@Deprecated
	public TaskDetails getTaskDetailsById(String taskId) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.TASK_BY_ID);
		sb.append("?taskId={taskId}");
		Map<String, Object> params = new HashMap<>();
		params.put(TASKID_TEXT, taskId);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails.class, params);
	}


	@Deprecated
	public TaskDetails getTaskDetails(String taskId) {
		return getTaskDetails(taskId, CmnConstants.YES_FULL);
	}


	@Deprecated
	public TaskDetails getTaskDetails(String taskId, String replaceDash) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(PARAM_TASKID);
		sb.append(PARAM_REPLACE_DASH);

		Map<String, Object> params = new HashMap<>();
		params.put(TASKID_TEXT, taskId);
		params.put(REPLACE_DASH_TEXT, replaceDash);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails.class, params);
	}


	// Claimed Date
	@Deprecated
	public TaskDetails getClaimedDate(String taskId, String replaceDash) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/claimedDate");
		sb.append(PARAM_TASKID);
		sb.append(PARAM_REPLACE_DASH);

		Map<String, Object> params = new HashMap<>();
		params.put(TASKID_TEXT, taskId);
		params.put(REPLACE_DASH_TEXT, replaceDash);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails.class, params);

	}


	@Deprecated
	public TaskDetails getClaimedDate1(String taskId, String replaceDash) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/claimedDate1");
		sb.append(PARAM_TASKID);
		sb.append(PARAM_REPLACE_DASH);

		Map<String, Object> params = new HashMap<>();
		params.put(TASKID_TEXT, taskId);
		params.put(REPLACE_DASH_TEXT, replaceDash);

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskDetails.class, params);
	}


	@Deprecated
	public List<TaskStatus> getTaskStatuses(String taskId) {
		return getTaskStatuses(taskId, CmnConstants.YES_FULL);
	}


	@Deprecated
	public List<TaskStatus> getTaskStatuses(String taskId, String replaceDash) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/statuses");
		sb.append(PARAM_TASKID);
		sb.append(PARAM_REPLACE_DASH);

		Map<String, Object> params = new HashMap<>();
		params.put(TASKID_TEXT, taskId);
		params.put(REPLACE_DASH_TEXT, replaceDash);

		TaskStatus[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskStatus[].class, params);
		return Arrays.asList(resp);
	}


	public boolean claimTasks(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/claim");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean startTask(TaskDetails payload) {
		return startTask(payload, false);
	}


	@Deprecated
	public boolean startTask(TaskDetails payload, boolean autoClaimed) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.START_TASK);
		boolean resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);

		if (autoClaimed && resp) {
			WfwPayload wfPayload = new WfwPayload();
			wfPayload.setTaskIds(payload.getRefNo());
			wfPayload.setTaskAuthorId(payload.getActionBy());
			resp = claimTasks(wfPayload);
		}

		return resp;
	}


	@Deprecated
	public boolean startTask(List<TaskDetails> taskList, boolean autoClaimed) {
		boolean resp = false;
		for (TaskDetails taskDetails : taskList) {
			resp = startTask(taskDetails, autoClaimed);
		}

		return resp;
	}


	@Deprecated
	public boolean amendTask(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/amend");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean completeTask(TaskDetails payload) {
		return completeTask(payload, false);
	}


	@Deprecated
	public boolean completeTask(TaskDetails payload, boolean autoClaimed) {
		boolean resp = false;

		if (autoClaimed) {
			WfwPayload wfPayload = new WfwPayload();
			wfPayload.setTaskIds(payload.getTaskId());
			wfPayload.setTaskAuthorId(payload.getActionBy());
			claimTasks(wfPayload);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.COMPLETE_TASK);
		resp = getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
		return resp;
	}


	@Deprecated
	public boolean updateTask(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.UPDATE_TASK);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean updateTaskForEPay(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/updateEpay");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean updateTaskKDNVerifier(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/updatekdnver");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean rejectTask(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/reject");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean completeAndStartTask(TaskDetails payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/completenstart");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean releaseTasks(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/release");

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public boolean releaseTasksGroupAdmin(WfwPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.RELEASE_TASK_GROUP_ADMIN);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	@Deprecated
	public String testWorkflowService() {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/test");

		return getRestTemplate().getForObject(getServiceURI(sb.toString()), String.class);
	}


	@Deprecated
	public String getAppStatusNextLevelByWfStatus(List<TaskStatus> statusList, String wfStatus) {
		String appStatus = CmnConstants.EMPTY_STRING;
		for (TaskStatus taskStatus : statusList) {
			if (CmnUtil.isEqualsCaseIgnore(taskStatus.getStatusId(), wfStatus)) {
				appStatus = CmnUtil.getStr(taskStatus.getApplStatus());
				break;
			}
		}
		return appStatus;
	}


	@Deprecated
	public boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatus,
			String remarks, String actionByUserId) {
		return performWfAction(actionType, taskIds, workFlowStatus, applStatus, remarks, actionByUserId, false);
	}


	// if need to auto claim
	@Deprecated
	public boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatus,
			String remarks, String actionByUserId, boolean isAutoClaim) {
		return performWfAction(actionType, taskIds, workFlowStatus, applStatus, remarks, actionByUserId,
				CmnConstants.EMPTY_STRING, "1", isAutoClaim);
	}


	@Deprecated
	public boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatus,
			String remarks, String actionByUserId, boolean isAutoClaim, List<TaskDetails> taskList) {

		StringBuilder logInfo = new StringBuilder();
		logInfo.append("actionType = ").append(actionType).append(System.lineSeparator());
		logInfo.append("taskIds = ").append(taskIds).append(System.lineSeparator());
		logInfo.append("workFlowStatus = ").append(workFlowStatus).append(System.lineSeparator());
		logInfo.append("applStatusCode = ").append(applStatus).append(System.lineSeparator());
		logInfo.append("actionByUserId = ").append(actionByUserId).append(System.lineSeparator());
		logInfo.append("isAutoClaim = ").append(isAutoClaim).append(System.lineSeparator());
		LOGGER.info("{}", logInfo);

		TaskDetails payload = new TaskDetails();
		payload.setTaskId(taskIds);
		payload.setWfStatus(workFlowStatus);
		payload.setApplStsCode(applStatus);
		payload.setActionBy(actionByUserId);
		payload.setIsDisplay(CmnConstants.DIGIT_1);
		payload.setActionByFullName(CmnConstants.EMPTY_STRING);
		payload.setRemarks(remarks);

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.START_MULTIPLE_TASK);

		MultipleTaskDetails metaData = new MultipleTaskDetails(payload, taskList);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), metaData, Boolean.class);
	}


	// update officer name
	@Deprecated
	public boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatus,
			String remarks, String actionByUserId, String actionByFullName, String isDisplay, boolean isAutoClaim) {
		return performWfAction(actionType, taskIds, workFlowStatus, applStatus, remarks, 0, actionByUserId,
				actionByFullName, isDisplay, isAutoClaim);
	}


	// update officer name
	@Deprecated
	public boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatus,
			String remarks, int attempCount, String actionByUserId, String actionByFullName, String isDisplay,
			boolean isAutoClaim) {
		return performWfAction(actionType, taskIds, workFlowStatus, applStatus, 0, null, remarks, null, attempCount,
				actionByUserId, actionByFullName, isDisplay, isAutoClaim);
	}


	// Complete, Release, claim task(s)
	@Deprecated
	private boolean performWfAction(String actionType, String taskIds, String workFlowStatus, String applStatusCode,
			int noOfWorkers, Date approveDate, String remarks, Date interviewDate, int attempCount,
			String actionByUserId, String actionByFullName, String isDisplay, boolean isAutoClaim) {

		StringBuilder logInfo = new StringBuilder();
		logInfo.append("actionType = ").append(actionType).append(System.lineSeparator());
		logInfo.append("taskIds = ").append(taskIds).append(System.lineSeparator());
		logInfo.append("workFlowStatus = ").append(workFlowStatus).append(System.lineSeparator());
		logInfo.append("applStatusCode = ").append(applStatusCode).append(System.lineSeparator());
		logInfo.append("noOfWorkers = ").append(noOfWorkers).append(System.lineSeparator());
		logInfo.append("approveDate = ").append(approveDate).append(System.lineSeparator());
		logInfo.append("interviewDate = ").append(interviewDate).append(System.lineSeparator());
		logInfo.append("attempCount = ").append(attempCount).append(System.lineSeparator());
		logInfo.append("actionByUserId = ").append(actionByUserId).append(System.lineSeparator());
		logInfo.append("actionByFullName = ").append(actionByFullName).append(System.lineSeparator());
		logInfo.append("isDisplay = ").append(isDisplay).append(System.lineSeparator());
		logInfo.append("isAutoClaim = ").append(isAutoClaim).append(System.lineSeparator());
		LOGGER.info("{}", logInfo);

		TaskDetails payload = new TaskDetails();
		payload.setTaskId(taskIds);
		payload.setWfStatus(workFlowStatus);
		payload.setApplStsCode(applStatusCode);
		payload.setActionBy(actionByUserId);
		payload.setIsDisplay(isDisplay);

		if (noOfWorkers > 0) {
			payload.setNoOfWorkers(noOfWorkers);
		}

		if (attempCount > 0) {
			payload.setAttemptCount(attempCount);
		}

		if (interviewDate != null) {
			payload.setInterviewDate(interviewDate);
		}

		if (!CmnUtil.isObjNull(actionByFullName)) {
			payload.setActionByFullName(actionByFullName);
		}

		payload.setRemarks(remarks);

		boolean isSuccess = false;

		if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_COMPLETE_TASK)) {
			if (isAutoClaim) {
				isSuccess = completeTask(payload, isAutoClaim);
			} else {
				isSuccess = completeTask(payload);
			}
			LOGGER.info("isCompleted = {}", isSuccess);
		} else if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_REJECT_TASK)) {
			isSuccess = rejectTask(payload);
			LOGGER.info("isRejected = {}", isSuccess);
		} else {
			LOGGER.info("Invalid Action Type");
		}

		return isSuccess;

	}


	// startTask, UpdateTask, claim task(s)
	@Deprecated
	public boolean performWfAction(String actionType, TaskDetails payload) {

		boolean isSuccess = false;

		if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_COMPLETE_TASK)) {
			isSuccess = completeTask(payload);
			LOGGER.info("isCompleted = {}", isSuccess);
		} else if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_COMPLETE_START_TASK)) {
			isSuccess = completeAndStartTask(payload);
			LOGGER.info("isCompletedStartTask = {}", isSuccess);
		} else if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_START_TASK)) {
			isSuccess = startTask(payload);
			LOGGER.info("isCreated = {}", isSuccess);
		} else if (actionType.equalsIgnoreCase(WorkflowConstants.ACION_UPDATE_TASK)) {
			isSuccess = updateTask(payload);
			LOGGER.info("isUpdated = {}", isSuccess);
		} else {
			LOGGER.info("Invalid Action Type");
		}

		return isSuccess;
	}


	@Deprecated
	public List<InboxHist> historyByTaskId(String taskId) {

		if (CmnUtil.isObjNull(taskId)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFWWP003);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.HISTORY_BY_TASKID);
		sb.append("?taskId=" + taskId);
		InboxHist[] resp = getRestTemplate().getForObject(getServiceURI(sb.toString()), InboxHist[].class);

		return Arrays.asList(resp);
	}


	@Deprecated
	public boolean updateTaskId(String taskId, String appStatus) {

		if (CmnUtil.isObjNull(taskId) || CmnUtil.isObjNull(appStatus)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFWWP003);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.UPDATE_TASK_ID);
		sb.append("?taskId=" + taskId);
		sb.append("&appStatus=" + appStatus);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), boolean.class);
	}


	// Add by Afif for pagination
	@SuppressWarnings("unchecked")
	public DataTableResults<TaskDetails> getTaskListTable(WfwPayload payload, Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/false");

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<TaskDetails> getMyTaskListTable(WfwPayload payload, Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/true");

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@Deprecated
	@SuppressWarnings("unchecked")
	public DataTableResults<TaskDetails> getMyTaskListRandomTable(WfwPayload payload, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/claimRandom");

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<TaskDetails> getApplicationListTable(WfwPayload payload, Map<String, Object> pagination,
			String updateId) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.APPLICATION_LIST);
		if (updateId != null) {
			sb.append("/");
			sb.append(updateId);
		}
		sb.append("?1=1");

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@Deprecated
	@SuppressWarnings("unchecked")
	public DataTableResults<TaskDetails> getGroupAdminTaskListTable(WfwPayload payload,
			Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append("/groupAdmin");

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	protected String getServiceURIDuplicate(String serviceName, Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
							mValue = getVariableValueAsString(mValue);
						}
						sb.append(mKey + "=" + mValue + "&");
					}
				}
			} catch (IOException e) {
				LOGGER.error("Exception: ", e);
			}
		}
		return !isFirst ? (sb.toString()).substring(0, sb.length() - 1) : sb.toString();
	}


	@Deprecated
	public static String getVariableValueAsString(Object variableValue) {
		try {
			return (variableValue != null ? URLEncoder.encode(variableValue.toString(), "UTF-8") : "");
		} catch (Exception e) {
			return variableValue.toString();
		}
	}


	@Deprecated
	public void updateIvDate(String refNo, String ivDate) {
		if (CmnUtil.isObjNull(refNo) || CmnUtil.isObjNull(ivDate)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFWWP003);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASKS);
		sb.append(WorkflowConstants.UPDATE_INTERVIEW_DATE);
		sb.append("?refNo=" + refNo);
		sb.append("&ivDate=" + ivDate);
		getRestTemplate().getForObject(getServiceURI(sb.toString()), boolean.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<TaskMaster> getTaskMasterList(WfwRefPayload payload, Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.MASTER);

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<TaskMaster> getTaskMasterWithHistoryList(WfwRefPayload payload,
			Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.MASTER);
		payload.setHistory(true);

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<TaskHistory> getTaskHistoryList(WfwRefPayload payload, Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.HISTORY);

		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), payload,
				DataTableResults.class);
	}


	public boolean claimTasks(WfwRefPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.CLAIM_TASK);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	public boolean releaseTasks(WfwRefPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.RELEASE_TASK);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	public boolean startTask(WfwRefPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.START_TASK);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	public boolean completeTask(WfwRefPayload payload) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.COMPLETE_TASK);

		return getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, Boolean.class);
	}


	public TaskMaster getTasksByAppRefNo(String appRefNo) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append("?appRefNo=" + appRefNo);
		return getRestTemplate().getForObject(getServiceURI(sb.toString()), TaskMaster.class);
	}
	
	public WfwReference getWfwReference(WfwReference wfwRef) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), wfwRef, WfwReference.class);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<WfwReference> getRefTypeDataTable(WfwReference wfwRef, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.TYPE + WorkflowConstants.PAGINATION);
		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), wfwRef,
				DataTableResults.class);
	}


	public List<WfwReference> getRefTypeList(WfwReference wfwRef) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.TYPE);
		WfwReference[] refTypeArray = getRestTemplate().postForObject(getServiceURI(sb.toString()), wfwRef,
				WfwReference[].class);

		return Arrays.asList(refTypeArray);
	}


	@SuppressWarnings("unchecked")
	public DataTableResults<RefLevel> getRefLevelDataTable(RefLevel level, Map<String, Object> pagination) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.LEVEL + WorkflowConstants.PAGINATION);
		return getRestTemplate().postForObject(getServiceURIDuplicate(sb.toString(), pagination), level,
				DataTableResults.class);
	}


	public List<RefLevel> getRefLevelList(RefLevel level) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.LEVEL);
		RefLevel[] refLevelArray = getRestTemplate().postForObject(getServiceURI(sb.toString()), level,
				RefLevel[].class);

		return Arrays.asList(refLevelArray);
	}


	public boolean addUpdateType(WfwReference wfwRef) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.TYPE + WorkflowConstants.ADD_OR_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), wfwRef, Boolean.class);
	}


	public boolean addUpdateLevel(RefLevel level) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.LEVEL + WorkflowConstants.ADD_OR_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), level, Boolean.class);
	}


	public boolean addUpdateStatus(RefStatus status) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.STATUS + WorkflowConstants.ADD_OR_UPDATE);
		return getRestTemplate().postForObject(getServiceURI(sb.toString()), status, Boolean.class);
	}


	public List<RefTypeAction> getRefActionList(RefTypeAction typeAction) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.TYPE_ACTION);
		RefTypeAction[] refTypeActionArray = getRestTemplate().postForObject(getServiceURI(sb.toString()), typeAction,
				RefTypeAction[].class);

		return Arrays.asList(refTypeActionArray);
	}


	public List<RefStatus> getRefStatusList(WfwReference wfwRef) {
		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.REFERENCE);
		sb.append(WorkflowConstants.STATUS);
		RefStatus[] refStatusArray = getRestTemplate().postForObject(getServiceURI(sb.toString()), wfwRef,
				RefStatus[].class);

		return Arrays.asList(refStatusArray);
	}
	
	//@SuppressWarnings("unchecked")
	public List<TaskMaster> getRefNoApplicant(WfwRefPayload payload,
			Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.REG_NO_APPLICANT);
		TaskMaster[] taskMasterArray = getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, TaskMaster[].class);
		return Arrays.asList(taskMasterArray);

	}
	
	public List<TaskMaster> getRefNoApplicantHistoryList(WfwRefPayload payload,
			Map<String, Object> pagination) {

		StringBuilder sb = new StringBuilder();
		sb.append(WorkflowConstants.TASK);
		sb.append(WorkflowConstants.REG_NO_APPLICANT);
		payload.setHistory(true);
		TaskMaster[] taskMasterArry = getRestTemplate().postForObject(getServiceURI(sb.toString()), payload, TaskMaster[].class);

		return Arrays.asList(taskMasterArry);
	}
}
