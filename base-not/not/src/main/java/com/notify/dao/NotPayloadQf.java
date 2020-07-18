package com.notify.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.notify.constants.QualifierConstants;
import com.notify.core.QueryFactory;
import com.notify.model.NotPayload;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;


/**
 * @author hafidz
 * @since March 4, 2020
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_QF)
public class NotPayloadQf extends QueryFactory<NotPayload> {

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;
	
	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<NotPayload> searchByProperty(NotPayload t) {
		return (Root<NotPayload> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				List<Order> orderList = new ArrayList<>();
				orderList.add(cb.desc(root.get("updateDt")));
				
				if(!BaseUtil.isObjNull(t.getLength())) {
					return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
				} 
				return query.where(predLst.toArray(new Predicate[predLst.size()])).orderBy(orderList).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<NotPayload> searchAllByProperty(NotPayload t) {
		CriteriaQuery<NotPayload> cq = cb.createQuery(NotPayload.class);
		Root<NotPayload> from = cq.from(NotPayload.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			NotPayload dto = JsonUtil.transferToObject(criteria, NotPayload.class);

			if (!BaseUtil.isObjNull(dto.getId())) {
				predicates.add(cb.equal(from.get("id"), dto.getId()));
			}

			if (!BaseUtil.isObjNull(dto.getNotifyType())) {
				predicates.add(cb.equal(from.get("notifyType"), dto.getNotifyType()));
			}

			if (!BaseUtil.isObjNull(dto.getNotifyTo())) {
				predicates.add(cb.equal(from.get("notifyTo"), dto.getNotifyTo()));
			}

			if (!BaseUtil.isObjNull(dto.getNotifyCc())) {
				predicates.add(cb.equal(from.get("notifyCc"), dto.getNotifyCc()));
			}

			if (!BaseUtil.isObjNull(dto.getNotifyBcc())) {
				predicates.add(cb.equal(from.get("notifyBcc"), dto.getNotifyBcc()));
			}
			
			if (!BaseUtil.isObjNull(dto.getSubject())) {
				predicates.add(cb.equal(from.get("subject"), dto.getSubject()));
			}

			if (!BaseUtil.isObjNull(dto.getTemplateCode())) {
				predicates.add(cb.equal(from.get("templateCode"), dto.getTemplateCode()));
			}

			if (!BaseUtil.isObjNull(dto.getCreateId())) {
				predicates.add(cb.equal(from.get("createId"), dto.getCreateId()));
			}

			if (!BaseUtil.isObjNull(dto.getUpdateId())) {
				predicates.add(cb.equal(from.get("updateId"), dto.getUpdateId()));
			}
			
			if (!BaseUtil.isObjNull(dto.getUpdateId())) {
				predicates.add(cb.equal(from.get("updateId"), dto.getUpdateId()));
			}
			
			if (!BaseUtil.isObjNull(dto.getUpdateDtFrom())) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dto.getUpdateDtFrom());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date endDate = cal.getTime();
				predicates.add(cb.greaterThanOrEqualTo(from.get("updateDt"), endDate));
			}

			if (!BaseUtil.isObjNull(dto.getUpdateDtTo())) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dto.getUpdateDtTo());
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				Date endDate = cal.getTime();
				predicates.add(cb.lessThanOrEqualTo(from.get("updateDt"), endDate));
			}
			
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
