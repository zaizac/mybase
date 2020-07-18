package com.bstsb.notify.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bstsb.notify.constants.QualifierConstants;
import com.bstsb.notify.model.NotEmailTemplate;
import com.bstsb.notify.model.NotPayload;
import com.bstsb.notify.sdk.model.Notification;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConstants;


@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_QF)
public class NotPayloadQf {

	@PersistenceContext
	private EntityManager entityManager;


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<NotPayload> seachNotPayload(Notification notification) {
		StringBuilder sb = new StringBuilder("select r ");
		sb.append(" from " + NotPayload.class.getSimpleName() + " r ");
		sb.append(" where 1 = 1 ");

		if (!BaseUtil.isObjNull(notification.getNotifyType())) {
			sb.append(" and r.notifyType = :notifyType ");
		}

		if (!BaseUtil.isObjNull(notification.getNotifyTo())) {
			sb.append(" and r.notifyTo = :notifyTo ");
		}

		TypedQuery<NotPayload> query = entityManager.createQuery(sb.toString(), NotPayload.class);

		if (!BaseUtil.isObjNull(notification.getNotifyType())) {
			query.setParameter("notifyType", notification.getNotifyType());
		}

		if (!BaseUtil.isObjNull(notification.getNotifyTo())) {
			query.setParameter("notifyTo", notification.getNotifyTo());
		}

		return query.getResultList();
	}


	public List<Notification> findWrkrEmailNotification(String notifyTo) {

		StringBuilder sb = new StringBuilder("SELECT p , t ");
		sb.append(" FROM NotPayload p, NotEmailTemplate t  ");
		sb.append(" WHERE 1=1 ");

		if (!BaseUtil.isObjNull(notifyTo)) {
			sb.append(" AND p.notifyTo = :notifyTo ");
		}
		sb.append(" AND p.notifyType = :notifyType ");
		sb.append(" AND p.templateCode = t.templateCode ");

		String hql = sb.toString();
		Query q = entityManager.createQuery(hql);

		if (!BaseUtil.isObjNull(notifyTo)) {
			q.setParameter("notifyTo", notifyTo);
		}
		q.setParameter("notifyType", BaseConstants.MAIL);

		List<Notification> notificationLst = new ArrayList<>();

		@SuppressWarnings("unchecked")
		List<Object[]> result = q.getResultList();

		if (!BaseUtil.isListNull(result)) {
			for (Object[] temp : result) {
				Notification notification = new Notification();
				NotPayload prof = (NotPayload) temp[0];
				if (!BaseUtil.isObjNull(prof)) {

					notification.setSubject(prof.getSubject());
					notification.setNotifyTo(prof.getNotifyTo());
					notification.setNotifyCc(prof.getNotifyCc());
					notification.setNotifyType(prof.getNotifyType());
					notification.setCreateDt(prof.getCreateDt());
					notification.setMetaData(prof.getMetaData());
					notification.setTemplateCode(prof.getTemplateCode());
				}

				NotEmailTemplate template = (NotEmailTemplate) temp[1];
				if (!BaseUtil.isObjNull(template)) {
					notification.setSubject(template.getEmailSubject());
					notification.setEmailContent(template.getEmailContent());
				}

				notificationLst.add(notification);
			}

		}
		return notificationLst;
	}

}
