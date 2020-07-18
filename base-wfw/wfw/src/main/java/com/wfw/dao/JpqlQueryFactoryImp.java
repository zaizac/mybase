/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.util.pagination.PaginationCriteria;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.WfwPayload;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_JPQLQF_DAO)
@Transactional
public class JpqlQueryFactoryImp implements JpqlQueryFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpqlQueryFactoryImp.class);

	private static final String WHERE_TEXT = " where ";

	private static final String COUNTRY_TEXT = "country";

	private static final String ROLEID_TEXT = "roleId";

	private static final String MODID_TEXT = "modId";

	private static final String REFNO_TEXT = "refNo";

	private static final String RA_PROFILE_ID_TEXT = "raProfileId";

	private static final String TASKID_TEXT = "taskId";

	private static final String OFFICERID_TEXT = "officerId";

	private static final String INBOXID_TEXT = "inboxId";

	private static final String COMPANY_NAME_TEXT = "companyName";

	private static final String CMPNY_NAME_TEXT = "cmpnyName";

	private static final String COMPANY_REGNO_TEXT = "companyRegNo";

	private static final String RA_COMPANY_REGNO_TEXT = "raCmpnyRegNo";

	private static final String RECRUITMENT_AGENT_TEXT = "recruitmentAgent";

	private static final String APP_DATE_FROM_TEXT = "appDateFrom";

	private static final String APP_DATE_TO_TEXT = "appDateTo";

	private static final String APPROVE_DT_FRM_TEXT = "apprveDtFrm";

	private static final String APPROVE_DT_TO_TEXT = "apprveDtTo";

	private static final String APPL_STS_CODE_TEXT = "applStsCode";

	private static final String ISDISPLAY_TEXT = "isDisplay";

	private static final String QUEUE_IND_TEXT = "queueInd";

	private static final String INTERVIEW_DATE_TEXT = "interviewDate";

	private static final String PYMT_INVOICE_NO_TEXT = "pymtInvoiceNo";

	private static final String SECTOR_AGENCY_TEXT = "sectorAgency";

	private static final String TRANSID_TEXT = "tranId";

	private static final String PRINT_STATUS_TEXT = "printStatus";

	private static final String QUERY_LOG_TEXT = "query = {}";

	private static final String ROLELIST_LOG_TEXT = "roleList: {}";

	private static final String MODLIST_LOG_TEXT = "modList: {}";

	private static final String APPSTATUSLIST_LOG_TEXT = "appStatusList: {}";

	private static final String USER_TYPE_TEXT = "userType";

	private static final String ROLE_SPLIT_PATTERN = "\\s*,\\s*";

	private static final String CLAIM_RANDOM_TEXT = "claimRandom";

	private static final String QUERY_SELECT_WFWINBOXMSTR_WFWINBOX = "select mstr from WfwInboxMstr mstr, WfwInbox inb ";

	private static final String QUERY_COND_EQ_OFFICERID = "and inb.officerId=:officerId ";

	private static final String QUERY_COND_LIKE_CMPNYNAME = "and mstr.companyName like :cmpnyName ";

	private static final String QUERY_COND_EQ_CMPNYNAME = "and mstr.companyName=:companyName ";

	private static final String QUERY_COND_EQ_CMPNYREGNO = "and mstr.companyRegNo=:companyRegNo ";

	private static final String QUERY_COND_EQ_RACMPNYREGNO = "and mstr.raCmpnyRegNo=:raCmpnyRegNo ";

	private static final String QUERY_COND_IN_APPLSTSCODE = "and mstr.applStsCode in :applStsCode ";

	private static final String QUERY_COND_EQ_APPLSTSCODE = "and mstr.applStsCode=:applStsCode ";

	private static final String QUERY_COND_EQ_INTERVIEWDATE = "and mstr.interviewDate=:interviewDate ";

	private static final String QUERY_COND_EQ_MODID = "and mstr.moduleId in :modId ";

	private static final String QUERY_COND_EQ_PYMTINVOICENO = "and mstr.pymtInvoiceNo like :pymtInvoiceNo ";

	private static final String QUERY_COND_IN_QUEUEIND = "and mstr.queueInd in :queueInd ";

	private static final String QUERY_COND_QUEUEIND_EQ_Q = "and mstr.queueInd='Q' ";

	private static final String QUERY_COND_QUEUEIND_EQ_I = "and mstr.queueInd='I' ";

	private static final String QUERY_COND_BETWEEN_APPDATE = "and mstr.applDate between :appDateFrom and :appDateTo ";

	private static final String QUERY_COND_APPDATE_LESS_APPDATETO = "and mstr.applDate<=:appDateTo ";

	private static final String QUERY_COND_APPDATE_MORE_APPDATEFROM = "and mstr.applDate>=:appDateFrom ";

	private static final String QUERY_COND_BETWEEN_APPROVEDATE = "and mstr.approveDate between :apprveDtFrm and :apprveDtTo ";

	private static final String QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO = "and mstr.approveDate<=:apprveDtTo ";

	private static final String QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM = "and mstr.approveDate>=:apprveDtFrm ";

	private static final String QUERY_COND_BETWEEN_DQVERDATE = "and mstr.dqVerDate between :appDateFrom and :appDateTo ";

	private static final String QUERY_COND_DQVERDATE_LESS_APPDATETO = "and mstr.dqVerDate<=:appDateTo ";

	private static final String QUERY_COND_DQVERDATE_MORE_APPDATEFROM = "and mstr.dqVerDate>=:appDateFrom ";

	// private static final String

	@PersistenceContext
	private EntityManager em;


	public EntityManager getEntityManager() {
		return em;
	}


	public void setEntityManager(EntityManager em) {
		this.em = em;
	}


	@Autowired
	@Qualifier(QualifierConstants.WF_MODULE_ROLE_DAO)
	private WfwModuleRoleDao wfwAsgnRoleDao;


	@Override
	public List<WfwInboxMstr> buildQueryTaskListInQueueByModRole(String roleId, String modId, String refNo) {
		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr, WfwAsgnRole rl ");
		sb.append(WHERE_TEXT);
		sb.append("rl.wfwAsgnRolePk.levelId=mstr.levelId ");
		if (!CmnUtil.isObjNull(roleId)) {
			sb.append("and rl.wfwAsgnRolePk.roleId=:roleId ");
		}
		if (!CmnUtil.isObjNull(modId)) {
			sb.append("and mstr.moduleId=:modId ");
		}
		if (!CmnUtil.isObjNull(refNo)) {
			sb.append("and mstr.refNo=:refNo ");
		}
		sb.append(QUERY_COND_QUEUEIND_EQ_Q);

		Query query = em.createQuery(sb.toString());
		if (!CmnUtil.isObjNull(roleId)) {
			query.setParameter(ROLEID_TEXT, roleId);
		}
		if (!CmnUtil.isObjNull(modId)) {
			query.setParameter(MODID_TEXT, modId);
		}
		if (!CmnUtil.isObjNull(refNo)) {
			query.setParameter(REFNO_TEXT, refNo);
		}
		@SuppressWarnings("unchecked")
		List<WfwInboxMstr> result = query.getResultList();
		return result;
	}


	@Override
	public List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String modId, String refNo) {
		StringBuilder sb = new StringBuilder(QUERY_SELECT_WFWINBOXMSTR_WFWINBOX);
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId=mstr.taskId ");

		if (!CmnUtil.isObjNull(officerId)) {
			sb.append(QUERY_COND_EQ_OFFICERID);
		}
		if (!CmnUtil.isObjNull(modId)) {
			sb.append("and mstr.moduleId=:modId ");
		}
		if (!CmnUtil.isObjNull(refNo)) {
			sb.append("and mstr.refNo=:refNo ");
		}
		sb.append(QUERY_COND_QUEUEIND_EQ_I);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		if (!CmnUtil.isObjNull(officerId)) {
			query.setParameter(OFFICERID_TEXT, officerId);
		}
		if (!CmnUtil.isObjNull(modId)) {
			query.setParameter(MODID_TEXT, modId);
		}
		if (!CmnUtil.isObjNull(refNo)) {
			query.setParameter(REFNO_TEXT, refNo);
		}
		@SuppressWarnings("unchecked")
		List<WfwInboxMstr> result = query.getResultList();

		return result;
	}


	@Override
	public boolean deleteWfInboxByTaskId(String taskId) {
		StringBuilder sb = new StringBuilder("delete from WfwInbox inb ");
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId=:taskId");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		query.setParameter(TASKID_TEXT, taskId);
		int deleted = query.executeUpdate();

		return (deleted > 0);
	}


	@Override
	public boolean deleteWfInboxMstrByTaskId(String taskId) {
		StringBuilder sb = new StringBuilder("delete from WfwInboxMstr mstr ");
		sb.append(WHERE_TEXT);
		sb.append("mstr.taskId=:taskId");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		query.setParameter(TASKID_TEXT, taskId);
		int deleted = query.executeUpdate();

		return (deleted > 0);
	}


	@Override
	public boolean deleteWfInboxMstrByRefNo(String refNo) {
		StringBuilder sb = new StringBuilder("delete from WfwInboxMstr mstr ");
		sb.append(WHERE_TEXT);
		sb.append("mstr.refNo=:refNo");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		query.setParameter(REFNO_TEXT, refNo);
		int deleted = query.executeUpdate();

		return (deleted > 0);
	}


	@Override
	public boolean deleteWfInboxByInboxId(String inboxId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("inboxId = {}", inboxId);
		}

		StringBuilder sb = new StringBuilder("delete from WfwInbox inb ");
		sb.append(WHERE_TEXT);
		sb.append("inb.inboxId=:inboxId");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		query.setParameter(INBOXID_TEXT, inboxId);
		int deleted = query.executeUpdate();

		return (deleted > 0);
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, propagation = Propagation.REQUIRED)
	public boolean deleteWfAsgnRoleByLevelId(String levelId) {
		StringBuilder sb = new StringBuilder("delete from WfwAsgnRole role ");
		sb.append(WHERE_TEXT);
		sb.append("role.wfwAsgnRolePk.levelId=:levelId");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		query.setParameter("levelId", levelId);
		int deleted = query.executeUpdate();

		return (deleted > 0);
	}


	@Override
	public String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId) {

		StringBuilder sb = new StringBuilder(
				"select q.user_name from (select temp.NAME, temp.USER_NAME, count(inb.TASK_ID) as total from ");
		sb.append("(select usr.* from WFW_USER usr, WFW_ASGN_ROLE role where usr.USER_ROLE = role.ROLE_ID and role.LEVEL_ID=:levelId) temp left outer join ");
		sb.append("WFW_INBOX inb on inb.OFFICER_ID=temp.USER_NAME group by temp.USER_NAME) q ");
		if (!CmnUtil.isObjNull(currOfficerId)) {
			sb.append("where q.user_name!=:officerId ");
		}
		sb.append("order by q.total,q.NAME limit 1");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("levelId", levelId);
		if (!CmnUtil.isObjNull(currOfficerId)) {
			query.setParameter(OFFICERID_TEXT, currOfficerId);
		}
		@SuppressWarnings("unchecked")
		List<Object> res = query.getResultList();
		if (CmnUtil.isListNull(res)) {
			return CmnConstants.EMPTY_STRING;
		} else {
			return res.get(0).toString();
		}
	}


	@Override
	public List<Object[]> findHistoryByTaskId(String taskId) {
		StringBuilder sb = new StringBuilder(
				"select h.HIST_ID, h.TASK_ID, h.REF_NO, l.DESCRIPTION as LEVEL_DESC, s.DESCRIPTION as STAUTS_DESC, ");
		sb.append("h.APPL_STS_ID, h.CREATE_ID, h.OFFICER_NAME, h.CREATE_DT, REPLACE (h.APPL_REMARK,'~~~','') as REMARKS, h.APPL_STS_CODE ");
		sb.append("from WFW_INBOX_HIST h left join WFW_USER u on h.CREATE_ID=u.USER_NAME ");
		sb.append("left join WFW_LEVEL l on h.LEVEL_ID=l.LEVEL_ID ");
		sb.append("left join WFW_STATUS s on h.STATUS_ID = s.STATUS_ID ");
		sb.append("where h.TASK_ID=:taskId ");
		sb.append("order by h.CREATE_DT");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createNativeQuery(sb.toString());
		query.setParameter(TASKID_TEXT, taskId);
		@SuppressWarnings("unchecked")
		List<Object[]> res = query.getResultList();
		return res;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<WfwInboxMstr> buildSearchQueryTaskListInQueue(WfwPayload wfwPayload) {

		List<String> roleList = wfwPayloadGetRoles(wfwPayload);
		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> isDisplyFlagList = wfwPayloadGetIsDisplay(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);
		List<String> appStatusList = wfwPayloadGetApplStatusCode(wfwPayload);

		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr, WfwAsgnRole rl ");
		sb.append(WHERE_TEXT);
		sb.append("rl.wfwAsgnRolePk.levelId=mstr.levelId ");

		if (!CmnUtil.isListNull(roleList)) {
			sb.append("and rl.wfwAsgnRolePk.roleId in :roleId ");
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
					&& CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
				sb.append("and mstr.interviewDate<=:interviewDate ");
			} else {
				sb.append(QUERY_COND_EQ_INTERVIEWDATE);
			}
		}

		if (!CmnUtil.isListNull(isDisplyFlagList)) {
			sb.append("and mstr.isDisplay in :isDisplay ");
		} else {
			sb.append("and mstr.isDisplay='1' ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append(QUERY_COND_IN_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_Q);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append(QUERY_COND_EQ_PYMTINVOICENO);
		}
		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isListNull(roleList)) {
			query.setParameter(ROLEID_TEXT, roleList);
		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isListNull(isDisplyFlagList)) {
			query.setParameter(ISDISPLAY_TEXT, isDisplyFlagList);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		return (query.getResultList());
	}


	private List<String> wfwPayloadGetRoles(WfwPayload wfwPayload) {
		List<String> roleList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getRoles())) {
			roleList = Arrays.asList(wfwPayload.getRoles().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(roleList)) {
				roleList.removeAll(Collections.singleton(null));
				roleList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ROLELIST_LOG_TEXT, roleList);
				}
			}
		}
		return roleList;
	}


	private List<String> wfwPayloadGetModules(WfwPayload wfwPayload) {
		List<String> modList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getModules())) {
			modList = Arrays.asList(wfwPayload.getModules().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(modList)) {
				modList.removeAll(Collections.singleton(null));
				modList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(MODLIST_LOG_TEXT, modList);
				}
			}
		}
		return modList;
	}


	private List<String> wfwPayloadGetIsDisplay(WfwPayload wfwPayload) {
		List<String> isDisplyFlagList = new ArrayList<>();

		if (!CmnUtil.isObjNull(wfwPayload.getIsDisplay())) {
			isDisplyFlagList = Arrays.asList(wfwPayload.getIsDisplay().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(isDisplyFlagList)) {
				isDisplyFlagList.removeAll(Collections.singleton(null));
				isDisplyFlagList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("isDisplyFlagList: {}", isDisplyFlagList);
				}
			}
		}
		return isDisplyFlagList;
	}


	private List<String> wfwPayloadGetQueueInd(WfwPayload wfwPayload) {
		List<String> queueIndList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getQueueInd())) {
			queueIndList = Arrays.asList(wfwPayload.getQueueInd().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(queueIndList)) {
				queueIndList.removeAll(Collections.singleton(null));
				queueIndList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("queueIndList: {}", queueIndList);
				}
			}
		}
		return queueIndList;
	}


	private List<String> wfwPayloadGetApplStatusCode(WfwPayload wfwPayload) {
		List<String> appStatusList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
				&& !CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
			appStatusList = Arrays.asList(wfwPayload.getApplStatusCode().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(appStatusList)) {
				appStatusList.removeAll(Collections.singleton(null));
				appStatusList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(APPSTATUSLIST_LOG_TEXT, appStatusList);
				}
			}
		}
		return appStatusList;
	}


	private List<String> wfwPayloadGetSectorAgency(WfwPayload wfwPayload) {
		List<String> sectorAgencyList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getSectorAgency())) {
			sectorAgencyList = Arrays.asList(wfwPayload.getSectorAgency().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(sectorAgencyList)) {
				sectorAgencyList.removeAll(Collections.singleton(null));
				sectorAgencyList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("sectorAgencyList: {}", sectorAgencyList);
				}
			}
		}
		return sectorAgencyList;
	}


	private List<String> wfwPayloadGetTranId(WfwPayload wfwPayload) {
		List<String> transIdList = new ArrayList<>();
		if (!CmnUtil.isObjNull(wfwPayload.getTranId())) {
			transIdList = Arrays.asList(wfwPayload.getTranId().split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(transIdList)) {
				transIdList.removeAll(Collections.singleton(null));
				transIdList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("transIdList: {}", transIdList);
				}
			}
		}
		return transIdList;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<WfwInboxMstr> buildSearchQueryTaskListInQueueAll(WfwPayload wfwPayload) {

		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> appStatusList = wfwPayloadGetApplStatusCode(wfwPayload);
		List<String> sectorAgencyList = wfwPayloadGetSectorAgency(wfwPayload);
		List<String> transIdList = wfwPayloadGetTranId(wfwPayload);

		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr ");
		sb.append("where 1=1 ");

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_BETWEEN_APPDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isListNull(transIdList)) {
			sb.append("and mstr.tranId in :tranId ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append(QUERY_COND_IN_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(sectorAgencyList)) {
			sb.append("and mstr.sectorAgency in :sectorAgency ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getPrintStatus())) {
			sb.append("and mstr.printStatus=:printStatus ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append(QUERY_COND_EQ_PYMTINVOICENO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCountry())) {
			sb.append("and mstr.country=:country ");
		}

		sb.append(" order by mstr.approveDate desc ");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		if (!CmnUtil.isListNull(sectorAgencyList)) {
			query.setParameter(SECTOR_AGENCY_TEXT, sectorAgencyList);
		}

		if (!CmnUtil.isListNull(transIdList)) {
			query.setParameter(TRANSID_TEXT, transIdList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getPrintStatus())) {
			query.setParameter(PRINT_STATUS_TEXT, wfwPayload.getPrintStatus());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCountry())) {
			query.setParameter(COUNTRY_TEXT, wfwPayload.getCountry());
		}
		return (query.getResultList());
	}


	// return record from inbox
	@SuppressWarnings("unchecked")
	@Override
	public List<WfwInboxMstr> buildSearchQueryTaskListInInbox(WfwPayload wfwPayload) {

		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);

		StringBuilder sb = new StringBuilder(QUERY_SELECT_WFWINBOXMSTR_WFWINBOX);
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId = mstr.taskId ");

		if (!CmnUtil.isObjNull(wfwPayload.getTaskAuthorId())) {
			sb.append(QUERY_COND_EQ_OFFICERID);
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}
		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			sb.append(QUERY_COND_EQ_INTERVIEWDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
				&& !CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
			sb.append(QUERY_COND_EQ_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_I);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append(QUERY_COND_EQ_PYMTINVOICENO);
		}
		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isObjNull(wfwPayload.getTaskAuthorId())) {
			query.setParameter(OFFICERID_TEXT, wfwPayload.getTaskAuthorId());
		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}
		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
				&& !CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
			query.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		return (query.getResultList());
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<WfwInboxMstr> buildSearchQueryTaskListInboxGroupAdmin(WfwPayload wfwPayload) {
		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> roleList = wfwPayloadGetRoles(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);

		StringBuilder sb = new StringBuilder(
				"select new com.workflow.model.WfwInboxMstr(mstr.taskId, mstr.refNo, mstr.applDate, mstr.companyName, mstr.sector,mstr.recruitmentAgent, mstr.moduleId, mstr.applStsCode, inb.officerId) ");
		sb.append("from WfwInboxMstr mstr, WfwInbox inb, WfwAsgnRole rl ");
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId = mstr.taskId ");
		sb.append(" and rl.wfwAsgnRolePk.levelId=mstr.levelId ");

		if (!CmnUtil.isListNull(roleList)) {
			sb.append("and rl.wfwAsgnRolePk.roleId in :roleId ");
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			sb.append(QUERY_COND_EQ_INTERVIEWDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())) {
			sb.append(QUERY_COND_EQ_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_I);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isListNull(roleList)) {
			query.setParameter(ROLEID_TEXT, roleList);
		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())) {
			query.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		return (query.getResultList());
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<WfwInboxHist> buildSearchQueryTaskHistoryList(WfwPayload wfwPayload) {
		List<String> appStatusList = wfwPayloadGetApplStatusCode(wfwPayload);

		StringBuilder sb = new StringBuilder("select hist from WfwInboxHist hist ");
		sb.append("where 1 = 1 ");

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and hist.refNo=:refNo ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append("and hist.applStsCodePrev in :applStsCode ");
		}

		sb.append(" order by hist.createDt ");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		return (query.getResultList());
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<WfwInboxMstr> buildQueryByUserAction(String userAction, Date actionDateFrom, Date actionDateTo,
			String modules, String appStatuses) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("userAction: {}", userAction);
			LOGGER.debug("actionDateFrom: {}", actionDateFrom);
			LOGGER.debug("actionDateTo: {}", actionDateTo);
			LOGGER.debug("modules: {}", modules);
			LOGGER.debug("appStatuses: {}", appStatuses);
		}

		List<String> modList = new ArrayList<>();

		if (!CmnUtil.isObjNull(modules)) {
			modList = Arrays.asList(modules.split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(modList)) {
				modList.removeAll(Collections.singleton(null));
				modList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(MODLIST_LOG_TEXT, modList);
				}
			}
		}

		List<String> appStatusList = new ArrayList<>();

		if (!CmnUtil.isObjNull(appStatuses)) {
			appStatusList = Arrays.asList(appStatuses.split(ROLE_SPLIT_PATTERN));
			if (!CmnUtil.isListNull(appStatusList)) {
				appStatusList.removeAll(Collections.singleton(null));
				appStatusList.removeAll(Collections.singleton(""));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(APPSTATUSLIST_LOG_TEXT, appStatusList);
				}
			}
		}

		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr ");
		sb.append("where 1 = 1 ");

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(actionDateFrom) && !CmnUtil.isObjNull(actionDateTo)) {
			sb.append(" and date(mstr.updateDt) between :actionDateFrom and :actionDateTo ");
		}

		if (!CmnUtil.isObjNull(userAction)) {
			sb.append("and mstr.lastStatusDesc=:userAction ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append(QUERY_COND_IN_APPLSTSCODE);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(actionDateFrom) && !CmnUtil.isObjNull(actionDateTo)) {
			query.setParameter("actionDateFrom", actionDateFrom, TemporalType.DATE);
			query.setParameter("actionDateTo", actionDateTo, TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(userAction)) {
			query.setParameter("userAction", userAction);
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo,
			String sectorAgency) {
		StringBuilder sb = new StringBuilder(
				"select new com.urp.wfw.sdk.model.InterviewEnquiry(mstr.interviewDate, ");
		sb.append("count(mstr.interviewDate) as noOfEmployer) from WfwInboxMstr mstr ");
		sb.append(WHERE_TEXT);

		if (!CmnUtil.isObjNull(interviewDateFrom) && !CmnUtil.isObjNull(interviewDateTo)) {
			sb.append("mstr.interviewDate between :interviewDateFrom and :interviewDateTo ");
		}

		if (!CmnUtil.isObjNull(sectorAgency)) {
			sb.append("and mstr.sectorAgency=:sectorAgency ");
		}

		sb.append("group by mstr.interviewDate");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());

		if (!CmnUtil.isObjNull(interviewDateFrom)) {
			query.setParameter("interviewDateFrom", interviewDateFrom);
		}

		if (!CmnUtil.isObjNull(interviewDateTo)) {
			query.setParameter("interviewDateTo", interviewDateTo);
		}

		if (!CmnUtil.isObjNull(sectorAgency)) {
			query.setParameter(SECTOR_AGENCY_TEXT, sectorAgency);
		}

		return query.getResultList();
	}


	// Pagination
	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListTableInQueue(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {

		List<String> roleList = wfwPayloadGetRoles(wfwPayload);
		// List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> modList = wfwAsgnRoleDao.findModuleIdByRoles(roleList);
		LOGGER.info("USER ROLES: {}", JsonUtil.toJsonNode(modList));
		List<String> isDisplyFlagList = wfwPayloadGetIsDisplay(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);
		List<String> appStatusList = wfwPayloadGetApplStatusCode(wfwPayload);

		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr, WfwAsgnRole rl ");
		sb.append(WHERE_TEXT);
		sb.append("rl.wfwAsgnRolePk.levelId=mstr.levelId ");

		if (!CmnUtil.isListNull(roleList)) {
			sb.append("and rl.wfwAsgnRolePk.roleId in :roleId ");
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
					&& CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
				sb.append("and mstr.interviewDate<=:interviewDate ");
			} else {
				sb.append(QUERY_COND_EQ_INTERVIEWDATE);
			}
		}

		if (!CmnUtil.isListNull(isDisplyFlagList)) {
			sb.append("and mstr.isDisplay in :isDisplay ");
		} else {
			sb.append("and mstr.isDisplay='1' ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append(QUERY_COND_IN_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_Q);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append(QUERY_COND_EQ_PYMTINVOICENO);
		}
		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			sb.append("and mstr.tranId = :tranId ");
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("mstr"));
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb);
		}

		Query query = em.createQuery(sb.toString());
		Query query2 = em.createQuery(sb.toString()); // Filtered Query by
												// pagination

		if (!CmnUtil.isListNull(roleList)) {
			query.setParameter(ROLEID_TEXT, roleList);
			query2.setParameter(ROLEID_TEXT, roleList);
		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
			query2.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
			query2.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
			query2.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
			query2.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
			query2.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
			query2.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);

			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
			query2.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isListNull(isDisplyFlagList)) {
			query.setParameter(ISDISPLAY_TEXT, isDisplyFlagList);
			query2.setParameter(ISDISPLAY_TEXT, isDisplyFlagList);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
			query2.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
			query2.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
			query2.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);

			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
			query2.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
			query2.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			query.setParameter(TRANSID_TEXT, wfwPayload.getUserType());
			query2.setParameter(TRANSID_TEXT, wfwPayload.getUserType());
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				query2.setFirstResult(pagination.getPageNumber());
				query2.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<WfwInboxMstr> dataTableResult = new DataTableResults<>();

		List<?> resultLst = query.getResultList();
		@SuppressWarnings("unchecked")
		List<WfwInboxMstr> resultLst2 = query2.getResultList();

		int totalRecords = dataTableInRQ.isInitSearch() ? resultLst.size() : resultLst2.size();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(resultLst2);
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(resultLst.size()));
		}
		return dataTableResult;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListInQueueAllTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ, String updateId) {

		// List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> roleList = wfwPayloadGetRoles(wfwPayload);
		List<String> modList = wfwAsgnRoleDao.findModuleIdByRoles(roleList);
		LOGGER.info("USER ROLES: {}", JsonUtil.toJsonNode(modList));

		List<String> appStatusList = wfwPayloadGetApplStatusCode(wfwPayload);
		List<String> sectorAgencyList = wfwPayloadGetSectorAgency(wfwPayload);
		List<String> transIdList = wfwPayloadGetTranId(wfwPayload);

		StringBuilder sb = new StringBuilder("select mstr from WfwInboxMstr mstr, WfwAsgnRole rl ");
		// sb.append("where 1=1 ");
		sb.append("where rl.wfwAsgnRolePk.levelId=mstr.levelId ");

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isListNull(transIdList)) {
			sb.append("and mstr.tranId in :tranId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			sb.append("and mstr.tranId = :userType ");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			sb.append(QUERY_COND_IN_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(sectorAgencyList)) {
			sb.append("and mstr.sectorAgency in :sectorAgency ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getPrintStatus())) {
			sb.append("and mstr.printStatus=:printStatus ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append("and mstr.pymtInvoiceNo like :pymtInvoiceNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCountry())) {
			sb.append("and mstr.country=:country ");
		}

		if (updateId != null && !updateId.isEmpty()) {
			sb.append("and mstr.updateId=:updateId ");
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("mstr"));
			}
		}

		if (sb.toString().contains("ORDER BY")) {
			sb.append(", mstr.approveDate desc ");
		} else {
			sb.append(" order by mstr.approveDate desc ");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		Query query = em.createQuery(sb.toString());
		Query query2 = em.createQuery(sb.toString()); // Filtered Query by
												// pagination

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
			query2.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
			query2.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
			query2.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
			query2.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
			query2.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
			query2.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
			query2.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		if (!CmnUtil.isListNull(appStatusList)) {
			query.setParameter(APPL_STS_CODE_TEXT, appStatusList);
			query2.setParameter(APPL_STS_CODE_TEXT, appStatusList);
		}

		if (!CmnUtil.isListNull(sectorAgencyList)) {
			query.setParameter(SECTOR_AGENCY_TEXT, sectorAgencyList);
			query2.setParameter(SECTOR_AGENCY_TEXT, sectorAgencyList);
		}

		if (!CmnUtil.isListNull(transIdList)) {
			query.setParameter(TRANSID_TEXT, transIdList);
			query2.setParameter(TRANSID_TEXT, transIdList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			query.setParameter(USER_TYPE_TEXT, wfwPayload.getUserType());
			query2.setParameter(USER_TYPE_TEXT, wfwPayload.getUserType());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getPrintStatus())) {
			query.setParameter(PRINT_STATUS_TEXT, wfwPayload.getPrintStatus());
			query2.setParameter(PRINT_STATUS_TEXT, wfwPayload.getPrintStatus());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);

			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}
		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
			query2.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCountry())) {
			query.setParameter(COUNTRY_TEXT, wfwPayload.getCountry());
			query2.setParameter(COUNTRY_TEXT, wfwPayload.getCountry());
		}

		if (updateId != null && !updateId.isEmpty()) {
			query.setParameter("updateId", updateId);
			query2.setParameter("updateId", updateId);
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				query2.setFirstResult(pagination.getPageNumber());
				query2.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<WfwInboxMstr> dataTableResult = new DataTableResults<>();
		List<?> resultLst = query.getResultList();
		@SuppressWarnings("unchecked")
		List<WfwInboxMstr> resultLst2 = query2.getResultList();

		int totalRecords = dataTableInRQ.isInitSearch() ? resultLst.size() : resultLst2.size();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(resultLst2);
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(resultLst.size()));
		}
		return dataTableResult;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListTableInInbox(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {

		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);

		StringBuilder sb = new StringBuilder(QUERY_SELECT_WFWINBOXMSTR_WFWINBOX);
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId = mstr.taskId ");

		if (!CmnUtil.isObjNull(wfwPayload.getTaskAuthorId())) {
			sb.append(QUERY_COND_EQ_OFFICERID);
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			sb.append("and mstr.recruitmentAgent like :recruitmentAgent ");
		}
		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_RACMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			sb.append("and mstr.raProfileId=:raProfileId ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_MORE_APPDATEFROM);
			} else {
				sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
			}
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_DQVERDATE_LESS_APPDATETO);
			} else {
				sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
			}
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			if (!CmnUtil.isObjNull(wfwPayload.getIsQuotaSec()) && wfwPayload.getIsQuotaSec().equalsIgnoreCase("Y")) {
				sb.append(QUERY_COND_BETWEEN_DQVERDATE);
			} else {
				sb.append(QUERY_COND_BETWEEN_APPDATE);
			}
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			sb.append(QUERY_COND_EQ_INTERVIEWDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			sb.append("and mstr.tranId=:userType ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
				&& !CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
			sb.append(QUERY_COND_EQ_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_I);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_MORE_APPRVEDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_APPROVEDATE_LESS_APPRVEDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			sb.append(QUERY_COND_BETWEEN_APPROVEDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			sb.append(QUERY_COND_EQ_PYMTINVOICENO);
		}
		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_LIKE_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("mstr"));
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		TypedQuery<WfwInboxMstr> query = em.createQuery(sb.toString(), WfwInboxMstr.class);
		// Filtered Filtered by pagination
		TypedQuery<WfwInboxMstr> query2 = em.createQuery(sb.toString(), WfwInboxMstr.class);

		if (!CmnUtil.isObjNull(wfwPayload.getTaskAuthorId())) {
			query.setParameter(OFFICERID_TEXT, wfwPayload.getTaskAuthorId());
			query2.setParameter(OFFICERID_TEXT, wfwPayload.getTaskAuthorId());
		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
			query2.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
			query2.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRecruitmentAgent())) {
			query.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
			query2.setParameter(RECRUITMENT_AGENT_TEXT, wfwPayload.getRecruitmentAgent() + "%");
		}
		if (!CmnUtil.isObjNull(wfwPayload.getRaCmpnyRegNo())) {
			query.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
			query2.setParameter(RA_COMPANY_REGNO_TEXT, wfwPayload.getRaCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
			query2.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRaProfileId()) && wfwPayload.getRaProfileId() > 0) {
			query.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
			query2.setParameter(RA_PROFILE_ID_TEXT, wfwPayload.getRaProfileId());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
			query2.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
			query2.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())
				&& !CmnUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), CLAIM_RANDOM_TEXT)) {
			query.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
			query2.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
			query2.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getApprveDtFrm()) && !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getApprveDtFrm())
				&& !CmnUtil.isObjNull(wfwPayload.getApprveDtTo())) {
			query.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);

			query2.setParameter(APPROVE_DT_FRM_TEXT, wfwPayload.getApprveDtFrm(), TemporalType.DATE);
			query2.setParameter(APPROVE_DT_TO_TEXT, wfwPayload.getApprveDtTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getInvoiceNo())) {
			query.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
			query2.setParameter(PYMT_INVOICE_NO_TEXT, wfwPayload.getInvoiceNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
			query.setParameter(USER_TYPE_TEXT, wfwPayload.getUserType());
			query2.setParameter(USER_TYPE_TEXT, wfwPayload.getUserType());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
			query2.setParameter(CMPNY_NAME_TEXT, "%" + wfwPayload.getCmpnyName() + "%");
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				query2.setFirstResult(pagination.getPageNumber());
				query2.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<WfwInboxMstr> dataTableResult = new DataTableResults<>();

		List<WfwInboxMstr> resultLst = query.getResultList();

		List<WfwInboxMstr> resultLst2 = query2.getResultList();

		int totalRecords = dataTableInRQ.isInitSearch() ? resultLst.size() : resultLst2.size();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(resultLst2);
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(resultLst.size()));
		}
		return dataTableResult;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public DataTableResults<WfwInboxMstr> buildSearchQueryTaskListInboxGroupAdminTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {

		List<String> roleList = wfwPayloadGetRoles(wfwPayload);
		List<String> modList = wfwPayloadGetModules(wfwPayload);
		List<String> queueIndList = wfwPayloadGetQueueInd(wfwPayload);

		StringBuilder sb = new StringBuilder(
				"select new com.workflow.model.WfwInboxMstr(mstr.taskId, mstr.refNo, mstr.applDate, mstr.companyName, mstr.sector,mstr.recruitmentAgent, mstr.moduleId, mstr.applStsCode, inb.officerId) ");
		sb.append("from WfwInboxMstr mstr, WfwInbox inb, WfwAsgnRole rl ");
		sb.append(WHERE_TEXT);
		sb.append("inb.taskId = mstr.taskId ");
		sb.append(" and rl.wfwAsgnRolePk.levelId=mstr.levelId ");

		if (!CmnUtil.isListNull(roleList)) {
			sb.append("and rl.wfwAsgnRolePk.roleId in :roleId ");
		}

		if (!CmnUtil.isListNull(modList)) {
			sb.append(QUERY_COND_EQ_MODID);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			sb.append("and mstr.refNo=:refNo ");
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			sb.append(QUERY_COND_EQ_CMPNYNAME);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_APPDATE_MORE_APPDATEFROM);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_APPDATE_LESS_APPDATETO);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			sb.append(QUERY_COND_BETWEEN_APPDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			sb.append(QUERY_COND_EQ_INTERVIEWDATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			sb.append(QUERY_COND_EQ_CMPNYREGNO);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())) {
			sb.append(QUERY_COND_EQ_APPLSTSCODE);
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			sb.append(QUERY_COND_IN_QUEUEIND);
		} else {
			sb.append(QUERY_COND_QUEUEIND_EQ_I);
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("mstr"));
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(QUERY_LOG_TEXT, sb.toString());
		}

		TypedQuery<WfwInboxMstr> query = em.createQuery(sb.toString(), WfwInboxMstr.class);
		// Filtered Filtered by pagination
		TypedQuery<WfwInboxMstr> query2 = em.createQuery(sb.toString(), WfwInboxMstr.class);

		if (!CmnUtil.isListNull(roleList)) {
			query.setParameter(ROLEID_TEXT, roleList);
			query2.setParameter(ROLEID_TEXT, roleList);

		}

		if (!CmnUtil.isListNull(modList)) {
			query.setParameter(MODID_TEXT, modList);
			query2.setParameter(MODID_TEXT, modList);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getRefNo())) {
			query.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
			query2.setParameter(REFNO_TEXT, wfwPayload.getRefNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyName())) {
			query.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
			query2.setParameter(COMPANY_NAME_TEXT, wfwPayload.getCmpnyName());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
		} else if (CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		} else if (!CmnUtil.isObjNull(wfwPayload.getAppDateFrom()) && !CmnUtil.isObjNull(wfwPayload.getAppDateTo())) {
			query.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
			query2.setParameter(APP_DATE_FROM_TEXT, wfwPayload.getAppDateFrom(), TemporalType.DATE);
			query2.setParameter(APP_DATE_TO_TEXT, wfwPayload.getAppDateTo(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getIntvwDate())) {
			query.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
			query2.setParameter(INTERVIEW_DATE_TEXT, wfwPayload.getIntvwDate(), TemporalType.DATE);
		}

		if (!CmnUtil.isObjNull(wfwPayload.getCmpnyRegNo())) {
			query.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
			query2.setParameter(COMPANY_REGNO_TEXT, wfwPayload.getCmpnyRegNo());
		}

		if (!CmnUtil.isObjNull(wfwPayload.getApplStatusCode())) {
			query.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
			query2.setParameter(APPL_STS_CODE_TEXT, wfwPayload.getApplStatusCode());
		}

		if (!CmnUtil.isListNull(queueIndList)) {
			query.setParameter(QUEUE_IND_TEXT, queueIndList);
			query2.setParameter(QUEUE_IND_TEXT, queueIndList);
		}

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!BaseUtil.isObjNull(pagination)) {
				query2.setFirstResult(pagination.getPageNumber());
				query2.setMaxResults(pagination.getPageSize());
			}
		}

		DataTableResults<WfwInboxMstr> dataTableResult = new DataTableResults<>();
		List<WfwInboxMstr> resultLst = query.getResultList();

		List<WfwInboxMstr> resultLst2 = query2.getResultList();

		int totalRecords = dataTableInRQ.isInitSearch() ? resultLst.size() : resultLst2.size();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(resultLst2);
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		} else {
			dataTableResult.setRecordsFiltered(Integer.toString(resultLst.size()));
		}
		return dataTableResult;
	}

}
