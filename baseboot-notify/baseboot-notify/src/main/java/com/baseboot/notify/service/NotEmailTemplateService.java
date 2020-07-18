/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.notify.core.AbstractService;
import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.dao.NotEmailTemplateRepository;
import com.baseboot.notify.model.NotEmailTemplate;
import com.baseboot.notify.util.QualifierConstants;

/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.NOT_EMAIL_TEMPLATE_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_SVC)
@Transactional
public class NotEmailTemplateService extends AbstractService<NotEmailTemplate, Integer> {

	@Autowired
	private NotEmailTemplateRepository notEMailTemplateDao;


	@Override
	public GenericRepository<NotEmailTemplate> primaryDao() {
		return notEMailTemplateDao;
	}


	public NotEmailTemplate findNotEmailTemplateById(Integer id) {
		return notEMailTemplateDao.findNotEmailTemplateById(id);
	}


	public NotEmailTemplate findByTemplateType(String templateType) {
		return notEMailTemplateDao.findByTemplateType(templateType);
	}

}
