/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.util.pagination.DataTableRequest;
import com.util.pagination.PaginationCriteria;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwRefLevel;
import com.wfw.model.WfwRefStatus;
import com.wfw.model.WfwRefType;
import com.wfw.model.WfwRefTypeAction;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 * @since 20 Sep 2019
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Transactional
@Qualifier(QualifierConstants.WFW_REF_CUSTOM_DAO)
public class WfwRefCustomDao {

	@PersistenceContext
	private EntityManager entityManager;

	public static final String TYPE_ID = "typeId";

	public static final String LEVEL_ID = "levelId";


	@SuppressWarnings("rawtypes")
	public List<WfwRefLevel> searchWfwLevel(RefLevel lvl, DataTableRequest dataTableInRQ) {

		List<WfwRefLevel> qResult = new ArrayList<>();
		TypedQuery<WfwRefLevel> query = null;
		StringBuilder sb = new StringBuilder("select distinct lvl from WfwRefLevel lvl "
				+ "left join fetch lvl.refLevelRoleList rl where 1 = 1 ");

		try {
			if (!CmnUtil.isObjNull(lvl.getLevelId())) {
				sb.append(" and lvl.levelId =:levelId");
			}

			if (!CmnUtil.isObjNull(lvl.getTypeId())) {
				sb.append(" and lvl.typeId =:typeId");
			}

			if (!CmnUtil.isObjNull(lvl.getStatus())) {
				sb.append(" and lvl.status =:status");
			}

			if (!CmnUtil.isObjNull(lvl.getRoles())) {
				sb.append(" and rl.levelRolePk.roleCd in :roles");
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					sb.append(pagination.getOrderByClause("lvl.flowNo asc"));
				}
			}

			query = entityManager.createQuery(sb.toString(), WfwRefLevel.class);

			if (!CmnUtil.isObjNull(lvl.getLevelId())) {
				query.setParameter(LEVEL_ID, lvl.getLevelId());
			}

			if (!CmnUtil.isObjNull(lvl.getTypeId())) {
				query.setParameter(TYPE_ID, lvl.getTypeId());
			}

			if (!CmnUtil.isObjNull(lvl.getStatus())) {
				query.setParameter("status", lvl.getStatus());
			}

			if (!CmnUtil.isObjNull(lvl.getRoles())) {
				query.setParameter("roles", lvl.getRoles());
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					query.setFirstResult(pagination.getPageNumber());
					query.setMaxResults(pagination.getPageSize());
				}
			}

			qResult = query.getResultList();
		} catch (Exception e) {
			throw e;
		}
		return qResult;
	}


	@SuppressWarnings("rawtypes")
	public List<WfwRefType> searchWfwType(WfwReference ref, DataTableRequest dataTableInRQ) {

		List<WfwRefType> qResult = null;
		TypedQuery<WfwRefType> query = null;
		StringBuilder sb = new StringBuilder("select distinct t from WfwRefType t where 1 = 1 ");

		try {
			if (!CmnUtil.isObjNull(ref.getTranId())) {
				sb.append(" and t.tranId =:tranId");
			}

			if (!CmnUtil.isObjNull(ref.getModuleId())) {
				sb.append(" and t.moduleId =:moduleId");
			}

			if (!CmnUtil.isObjNull(ref.getTypeId())) {
				sb.append(" and t.typeId =:typeId");
			}

			if (!CmnUtil.isObjNull(ref.getStatus())) {
				sb.append(" and t.status =:status");
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					sb.append(pagination.getOrderByClause("t"));
				}
			}

			query = entityManager.createQuery(sb.toString(), WfwRefType.class);

			if (!CmnUtil.isObjNull(ref.getTranId())) {
				query.setParameter("tranId", ref.getTranId());
			}

			if (!CmnUtil.isObjNull(ref.getModuleId())) {
				query.setParameter("moduleId", ref.getModuleId());
			}

			if (!CmnUtil.isObjNull(ref.getTypeId())) {
				query.setParameter(TYPE_ID, ref.getTypeId());
			}

			if (!CmnUtil.isObjNull(ref.getStatus())) {
				query.setParameter("status", ref.getStatus());
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					query.setFirstResult(pagination.getPageNumber());
					query.setMaxResults(pagination.getPageSize());
				}
			}

			qResult = query.getResultList();

		} catch (Exception e) {
			throw new WfwException(e.getMessage());
		}
		return qResult;
	}


	@SuppressWarnings("rawtypes")
	public List<WfwRefTypeAction> searchWfwTypeAction(RefTypeAction actn, DataTableRequest dataTableInRQ) {

		List<WfwRefTypeAction> qResult = null;
		TypedQuery<WfwRefTypeAction> query = null;
		StringBuilder sb = new StringBuilder("select distinct a from WfwRefTypeAction a where 1 = 1 ");

		try {
			if (!CmnUtil.isObjNull(actn.getTypeActionId())) {
				sb.append(" a.typeActionId =:typeActionId");
			}

			if (!CmnUtil.isObjNull(actn.getTypeId())) {
				sb.append(" and a.typeId =:typeId");
			}

			if (!CmnUtil.isObjNull(actn.getLevelId())) {
				sb.append(" and a.levelId =:levelId");
			}

			if (!CmnUtil.isObjNull(actn.getStatus())) {
				sb.append(" and a.status =:status");
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					sb.append(pagination.getOrderByClause("a"));
				}
			}

			query = entityManager.createQuery(sb.toString(), WfwRefTypeAction.class);

			if (!CmnUtil.isObjNull(actn.getTypeActionId())) {
				query.setParameter("typeActionId", actn.getTypeActionId());
			}

			if (!CmnUtil.isObjNull(actn.getTypeId())) {
				query.setParameter(TYPE_ID, actn.getTypeId());
			}

			if (!CmnUtil.isObjNull(actn.getLevelId())) {
				query.setParameter(LEVEL_ID, actn.getLevelId());
			}

			if (!CmnUtil.isObjNull(actn.getStatus())) {
				query.setParameter("status", actn.getStatus());
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					query.setFirstResult(pagination.getPageNumber());
					query.setMaxResults(pagination.getPageSize());
				}
			}

			qResult = query.getResultList();

		} catch (Exception e) {
			throw new WfwException(e.getMessage());
		}
		return qResult;
	}


	@SuppressWarnings("rawtypes")
	public List<WfwRefStatus> searchWfwStatus(RefStatus status, DataTableRequest dataTableInRQ) {

		List<WfwRefStatus> qResult = new ArrayList<>();
		TypedQuery<WfwRefStatus> query = null;
		StringBuilder sb = new StringBuilder("select s from WfwRefStatus s where 1 = 1 ");

		try {
			if (!CmnUtil.isObjNull(status.getLevelId())) {
				sb.append(" and s.levelId =:levelId");
			} else if (!CmnUtil.isObjNull(status.getLevelIdList())) {
				sb.append(" and s.levelId in :levelIds");
			}

			if (!CmnUtil.isObjNull(status.getTypeId())) {
				sb.append(" and s.typeId =:typeId");
			}

			if (!CmnUtil.isObjNull(status.getStatus())) {
				sb.append(" and s.status =:status");
			}

			if (!CmnUtil.isObjNull(status.getDisplay())) {
				sb.append(" and s.display =:display");
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					sb.append(pagination.getOrderByClause(" s "));
				}
			} else if (!CmnUtil.isObjNull(status.getGroupBy())) {
				sb.append(" group by " + status.getGroupBy());
			}

			query = entityManager.createQuery(sb.toString(), WfwRefStatus.class);

			if (!CmnUtil.isObjNull(status.getLevelId())) {
				query.setParameter(LEVEL_ID, status.getLevelId());
			} else if (!CmnUtil.isObjNull(status.getLevelIdList())) {
				query.setParameter("levelIds", status.getLevelIdList());
			}

			if (!CmnUtil.isObjNull(status.getTypeId())) {
				query.setParameter(TYPE_ID, status.getTypeId());
			}

			if (!CmnUtil.isObjNull(status.getStatus())) {
				query.setParameter("status", status.getStatus());
			}

			if (!CmnUtil.isObjNull(status.getDisplay())) {
				query.setParameter("display", status.getDisplay());
			}

			if (!CmnUtil.isObjNull(dataTableInRQ)) {
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				if (!CmnUtil.isObjNull(pagination)) {
					query.setFirstResult(pagination.getPageNumber());
					query.setMaxResults(pagination.getPageSize());
				}
			}

			qResult = query.getResultList();
		} catch (Exception e) {
			throw new WfwException(e.getMessage());
		}
		return qResult;
	}
}
