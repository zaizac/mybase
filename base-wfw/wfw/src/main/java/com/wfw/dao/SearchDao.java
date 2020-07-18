package com.wfw.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInbox;
import com.wfw.model.WfwInboxMstr;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author Md Kamruzzaman
 *
 */
@Component(QualifierConstants.WF_SEARCH_DAO)
@Qualifier(QualifierConstants.WF_SEARCH_DAO)
public class SearchDao {

	public Specification<WfwInbox> searchWfInBox(final WfwInbox paramInbox) {

		return new Specification<WfwInbox>() {

			@Override
			public Predicate toPredicate(Root<WfwInbox> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predList = new ArrayList<>();

				if (!CollectionUtils.isEmpty(predList)) {
					return query.where(predList.toArray(new Predicate[predList.size()])).getRestriction();
				}

				return query.getRestriction();

			}
		};
	}


	public Specification<WfwInboxMstr> searchWfInBoxMstr(final WfwInboxMstr paramInboxMstr) {

		return new Specification<WfwInboxMstr>() {

			@Override
			public Predicate toPredicate(Root<WfwInboxMstr> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predList = new ArrayList<>();

				if (!CmnUtil.isObjNull(paramInboxMstr.getBranchId())) {
					Predicate branchIdPred = cb.and(cb.equal(root.get("branchId"), paramInboxMstr.getBranchId()));
					predList.add(branchIdPred);
				}

				if (!CmnUtil.isObjNull(paramInboxMstr.getOfficerId())) {
					Predicate offIdPred = cb.and(cb.equal(root.get("officerId"), paramInboxMstr.getOfficerId()));
					predList.add(offIdPred);
				}

				if (!CmnUtil.isObjNull(paramInboxMstr.getRefNo())) {
					Predicate refNoPred = cb.and(cb.equal(root.get("refNo"), paramInboxMstr.getRefNo()));
					predList.add(refNoPred);
				}

				if (StringUtils.hasText(paramInboxMstr.getApplUserName())) {
					Expression<String> expr = root.get("applUserName");
					Predicate applUserNamePred = cb
							.and(cb.like(expr, "%" + paramInboxMstr.getApplUserName() + "%"));
					predList.add(applUserNamePred);
				}

				if (!CollectionUtils.isEmpty(predList)) {
					return query.where(predList.toArray(new Predicate[predList.size()])).getRestriction();
				}

				return query.getRestriction();

			}
		};
	}

}
