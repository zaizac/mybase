/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.notify.core.AbstractService;
import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.dao.NotConfigRepository;
import com.baseboot.notify.model.NotConfig;
import com.baseboot.notify.util.QualifierConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_SVC)
@Transactional
public class NotConfigService extends AbstractService<NotConfig, Integer> {

	@Autowired
	private NotConfigRepository notConfigDao;


	@Override
	public GenericRepository<NotConfig> primaryDao() {
		return notConfigDao;
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public NotConfig findByConfigCode(String configCode) {
		return notConfigDao.findByConfigCode(configCode);
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<NotConfig> updateAll(List<NotConfig> configs) {
		for (NotConfig config : configs) {
			notConfigDao.saveAndFlush(config);
		}
		return configs;
	}

}