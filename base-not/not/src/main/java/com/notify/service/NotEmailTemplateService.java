/**
 * Copyright 2019. 
 */
package com.notify.service;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notify.constants.QualifierConstants;
import com.notify.core.AbstractService;
import com.notify.core.GenericRepository;
import com.notify.dao.NotEmailTemplateQf;
import com.notify.dao.NotEmailTemplateRepository;
import com.notify.model.NotEmailTemplate;
import com.notify.sdk.model.EmailTemplate;
import com.util.JsonUtil;



/**
 * The Class NotEmailTemplateService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.NOT_EMAIL_TEMPLATE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_SVC)
@Transactional
public class NotEmailTemplateService extends AbstractService<NotEmailTemplate> {

	/** The not E mail template dao. */
	@Autowired
	private NotEmailTemplateRepository notEMailTemplateDao;
	
	@Autowired
	private NotEmailTemplateQf notEmailTemplateQf;


	/* (non-Javadoc)
	 * @see com.notify.core.AbstractService#primaryDao()
	 */
	@Override
	public GenericRepository<NotEmailTemplate> primaryDao() {
		return notEMailTemplateDao;
	}


	/**
	 * Find not email template by id.
	 *
	 * @param id the id
	 * @return the not email template
	 */
	public NotEmailTemplate findNotEmailTemplateById(Integer id) {
		return notEMailTemplateDao.findNotEmailTemplateById(id);
	}


	/**
	 * Find by template type.
	 *
	 * @param templateType the template type
	 * @return the not email template
	 */
	public NotEmailTemplate findByTemplateType(String templateType) {
		return notEMailTemplateDao.findByTemplateType(templateType);
	}


	/**
	 * Find by template code.
	 *
	 * @param templateCode the template code
	 * @return the not email template
	 */
	public NotEmailTemplate findByTemplateCode(String templateCode) {
		return notEMailTemplateDao.findByTemplateCode(templateCode);
	}
	
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<NotEmailTemplate> search(EmailTemplate emailTemplate) throws IOException {
		NotEmailTemplate notEmailTemplates = JsonUtil.transferToObject(emailTemplate, NotEmailTemplate.class);
		return notEMailTemplateDao.findAll(notEmailTemplateQf.searchByProperty(notEmailTemplates));
	}
}
