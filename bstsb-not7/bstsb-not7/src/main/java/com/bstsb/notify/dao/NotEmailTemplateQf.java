package com.bstsb.notify.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bstsb.notify.constants.QualifierConstants;
import com.bstsb.notify.model.NotEmailTemplate;
import com.bstsb.notify.sdk.model.EmailTemplate;
import com.bstsb.util.BaseUtil;


@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_QF)
public class NotEmailTemplateQf {

	@PersistenceContext
	private EntityManager entityManager;


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<NotEmailTemplate> searchEmailTemplate(EmailTemplate emailTemplate) {

		StringBuilder sb = new StringBuilder("select r ");
		sb.append(" from " + NotEmailTemplate.class.getSimpleName() + " r ");
		sb.append(" where 1 = 1 ");

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateCode())) {
			sb.append(" and r.templateCode = :templateCode ");
		}

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateType())) {
			sb.append(" and r.templateType = :templateType ");
		}

		if (!BaseUtil.isObjNull(emailTemplate.getEmailSubject())) {
			sb.append(" and r.emailSubject = :emailSubject ");
		}

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateDesc())) {
			sb.append(" and r.templateDesc = :templateDesc ");
		}

		TypedQuery<NotEmailTemplate> query = entityManager.createQuery(sb.toString(), NotEmailTemplate.class);

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateCode())) {
			query.setParameter("templateCode", emailTemplate.getTemplateCode());
		}

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateType())) {
			query.setParameter("templateType", emailTemplate.getTemplateType());
		}

		if (!BaseUtil.isObjNull(emailTemplate.getEmailSubject())) {
			query.setParameter("emailSubject", emailTemplate.getEmailSubject());
		}

		if (!BaseUtil.isObjNull(emailTemplate.getTemplateDesc())) {
			query.setParameter("templateDesc", emailTemplate.getTemplateDesc());
		}

		return query.getResultList();
	}

}
