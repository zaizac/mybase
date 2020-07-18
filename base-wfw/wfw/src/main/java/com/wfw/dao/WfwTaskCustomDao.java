/**
 * Copyright 2019.
 */
package com.wfw.dao;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.util.JsonUtil;
import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwTaskHistory;
import com.wfw.model.WfwTaskMaster;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.service.WfwTaskHistoryService;


/**
 * @author michelle.angela
 *
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Transactional
@Qualifier(QualifierConstants.WFW_TASK_CUSTOM_DAO)
public class WfwTaskCustomDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WfwTaskCustomDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUERY_SLCT_WFW_TSK_MSTR = "select u from WfwTaskMaster u where 1 = 1";

	private static final String QUERY_SLCT_WFW_TSK_HSTRY = "select u from WfwTaskHistory u where 1 = 1";


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WfwTaskMaster> searchQueryWfwTaskMaster(WfwRefPayload payload, DataTableRequest dataTableInRQ) {

		StringBuilder sb = new StringBuilder(QUERY_SLCT_WFW_TSK_MSTR);
		setStringBuilder(payload, sb);

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!CmnUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("u"));
			}
		}

		Query query = entityManager.createQuery(sb.toString());
		setQueryParameter(payload, query);

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!CmnUtil.isObjNull(pagination)) {
				query.setFirstResult(pagination.getPageNumber());
				query.setMaxResults(pagination.getPageSize());
			}
		}

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<WfwTaskHistory> searchQueryWfwTaskHistory(WfwRefPayload payload,
			DataTableRequest<TaskHistory> dataTableInRQ) {

		StringBuilder sb = new StringBuilder(QUERY_SLCT_WFW_TSK_HSTRY);
		setStringBuilder(payload, sb);

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!CmnUtil.isObjNull(pagination)) {
				sb.append(pagination.getOrderByClause("u"));
			}
		} else {
			sb.append(" order by u.createDt asc");
		}

		Query query = entityManager.createQuery(sb.toString());
		setQueryParameter(payload, query);

		if (!CmnUtil.isObjNull(dataTableInRQ)) {
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			if (!CmnUtil.isObjNull(pagination)) {
				query.setFirstResult(pagination.getPageNumber());
				query.setMaxResults(pagination.getPageSize());
			}
		}

		return query.getResultList();
	}


	private StringBuilder setStringBuilder(WfwRefPayload payload, StringBuilder sb) {
		if (!CmnUtil.isObjNull(payload.getGlobalParam())) {

			setGlobalStringBuilder(payload, sb);

		} else {

			if (!CmnUtil.isObjNull(payload.getTaskMasterId())) {
				sb.append(" and u.taskMasterId =:taskMasterId");
			} else {
				if (!CmnUtil.isListNull(payload.getTaskMasterIdList())) {
					sb.append(" and u.taskMasterId in :taskMasterIdList");
				}
			}

			if (!CmnUtil.isObjNull(payload.getTypeId())) {
				sb.append(" and u.typeId =:typeId");
			}

			if (!CmnUtil.isObjNull(payload.getStatusList())) {
				sb.append(" and u.statusId in :statusList");
			}

			if (!CmnUtil.isObjNull(payload.getAppRefNo())) {
				sb.append(" and u.appRefNo =:appRefNo");
			}

			if (!CmnUtil.isObjNull(payload.getApplicantId())) {
				sb.append(" and u.applicantId =:applicantId");
			}

			if (!CmnUtil.isObjNull(payload.getAppStatus())) {
				sb.append(" and u.appStatus =:appStatus");
			}

			if (!CmnUtil.isObjNull(payload.getApplicant())) {
				sb.append(" and u.applicant =:applicant");
			}

			if (!CmnUtil.isObjNull(payload.getSubmitBy())) {
				sb.append(" and u.submitBy =:submitBy");
			}

			if (!CmnUtil.isObjNull(payload.getSubmitByName())) {
				sb.append(" and u.submitByName =:submitByName");
			}

			if (!CmnUtil.isObjNull(payload.getClaimByName())) {
				sb.append(" and u.claimByName =:claimByName");
			}

			if (!CmnUtil.isObjNull(payload.getAppDateFrom()) && !CmnUtil.isObjNull(payload.getAppDateTo())) {
				sb.append(" and u.appDate between :appDateFrom and :appDateTo ");
			}

			if (!CmnUtil.isObjNull(payload.getStatusCd())) {
				sb.append(" and u.refStatus.statusCd =:statusCd");
			}

			if (!CmnUtil.isObjNull(payload.getTaskHistoryId())) {
				sb.append(" and u.taskHistoryId =:taskHistoryId");
			}

			if (!CmnUtil.isObjNull(payload.getAction())) {
				sb.append(" and u.action =:action");
			}
			
		}

		if (!CmnUtil.isListNull(payload.getLevelList())) {
			sb.append(" and u.levelId in :levelList");
		}

		if (!CmnUtil.isObjNull(payload.getClaimBy())) {
			sb.append(" and u.claimBy =:claimBy");
		} else {
			if (payload.isPool()) {
				sb.append(" and u.claimBy is null");
			}
		}

		if (payload.isHistory() && !CmnUtil.isObjNull(payload.isDisplay())) {
			sb.append(" and u.display =:display");
		}
		
		if (!CmnUtil.isObjNull(payload.getAdditionalInfo())) {
			Map<String, Object> maps;
			try {
				maps = JsonUtil.convertJsonToMap(payload.getAdditionalInfo());
				for (String key : maps.keySet()) {					
					sb.append(" and u.additionalInfo <> '' and json_extract(u.additionalInfo, '$."+ key + "') = :addInfo" + key);
				}
			} catch (IOException e) {
				throw new WfwException(e.getMessage());
			}
			 
		}

		LOGGER.info(sb.toString());
		return sb;

	}


	private Query setQueryParameter(WfwRefPayload payload, Query query) {

		if (!CmnUtil.isObjNull(payload.getGlobalParam())) {
			query.setParameter("globalParam", "%" + payload.getGlobalParam() + "%");

		} else {

			if (!CmnUtil.isObjNull(payload.getTaskMasterId())) {
				query.setParameter("taskMasterId", payload.getTaskMasterId());
			} else {
				if (!CmnUtil.isListNull(payload.getTaskMasterIdList())) {
					query.setParameter("taskMasterIdList", payload.getTaskMasterIdList());
				}
			}

			if (!CmnUtil.isObjNull(payload.getTypeId())) {
				query.setParameter("typeId", payload.getTypeId());
			}

			if (!CmnUtil.isObjNull(payload.getStatusList())) {
				query.setParameter("statusList", payload.getStatusList());
			}

			if (!CmnUtil.isObjNull(payload.getAppRefNo())) {
				query.setParameter("appRefNo", payload.getAppRefNo());
			}

			if (!CmnUtil.isObjNull(payload.getApplicantId())) {
				query.setParameter("applicantId", payload.getApplicantId());
			}

			if (!CmnUtil.isObjNull(payload.getAppStatus())) {
				query.setParameter("appStatus", payload.getAppStatus());
			}

			if (!CmnUtil.isObjNull(payload.getApplicant())) {
				query.setParameter("applicant", payload.getApplicant());
			}

			if (!CmnUtil.isObjNull(payload.getSubmitBy())) {
				query.setParameter("submitBy", payload.getSubmitBy());
			}

			if (!CmnUtil.isObjNull(payload.getSubmitByName())) {
				query.setParameter("submitByName", payload.getSubmitByName());
			}

			if (!CmnUtil.isObjNull(payload.getClaimByName())) {
				query.setParameter("claimByName", payload.getClaimByName());
			}

			if (!CmnUtil.isObjNull(payload.getAppDateFrom()) && !CmnUtil.isObjNull(payload.getAppDateTo())) {
				query.setParameter("appDateFrom", payload.getAppDateFrom());
				query.setParameter("appDateTo", payload.getAppDateTo());
			}

			if (!CmnUtil.isObjNull(payload.getStatusCd())) {
				query.setParameter("statusCd", payload.getStatusCd());
			}

			if (!CmnUtil.isObjNull(payload.getTaskHistoryId())) {
				query.setParameter("taskHistoryId", payload.getTaskHistoryId());
			}

			if (!CmnUtil.isObjNull(payload.getAction())) {
				query.setParameter("action", payload.getAction());
			}
		}

		if (!CmnUtil.isObjNull(payload.getLevelList())) {
			query.setParameter("levelList", payload.getLevelList());
		}

		if (!CmnUtil.isObjNull(payload.getClaimBy())) {
			query.setParameter("claimBy", payload.getClaimBy());
		}

		if (payload.isHistory() && !CmnUtil.isObjNull(payload.isDisplay())) {
			query.setParameter("display", payload.isDisplay());
		}
		
		if (!CmnUtil.isObjNull(payload.getAdditionalInfo())) {
			Map<String, Object> maps;
			try {
				maps = JsonUtil.convertJsonToMap(payload.getAdditionalInfo());
				for (Map.Entry<String, Object> entry : maps.entrySet()) {					
					query.setParameter("addInfo"+entry.getKey(), entry.getValue());
				}
			} catch (IOException e) {
				throw new WfwException(e.getMessage());
			}
			
		}

		return query;
	}


	private StringBuilder setGlobalStringBuilder(WfwRefPayload payload, StringBuilder sb) {
		sb.append(" and (");
		if (payload.isHistory()) {
			sb.append(" and u.taskHistoryId like :globalParam");
			sb.append(" or u.action like :globalParam");
		} else {
			sb.append(" u.taskMasterId like :globalParam");
		}
		sb.append(" or u.appRefNo like :globalParam");
		sb.append(" or u.applicantId like :globalParam");
		sb.append(" or u.appStatus like :globalParam");
		sb.append(" or u.applicant like :globalParam");
		sb.append(" or u.submitBy like :globalParam");
		sb.append(" or u.submitByName like :globalParam");
		sb.append(" or u.claimBy like :globalParam");
		sb.append(" or u.claimByName like :globalParam");
		sb.append(" or u.appDate like :globalParam");
		sb.append(" or u.refType.description like :globalParam");
		sb.append(" or u.refLevel.description like :globalParam");
		sb.append(" or u.refStatus.statusCd like :globalParam");
		sb.append(" or u.refStatus.statusDesc like :globalParam");
		sb.append(" )");

		return sb;
	}
	
	
}
