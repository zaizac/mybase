/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.notify.constants.QualifierConstants;
import com.bstsb.notify.core.AbstractService;
import com.bstsb.notify.core.GenericRepository;
import com.bstsb.notify.dao.NotConfigRepository;
import com.bstsb.notify.model.NotConfig;



/**
 * The Class NotConfigService.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_SVC)
@Transactional
public class NotConfigService extends AbstractService<NotConfig> {

	/** The not config dao. */
	@Autowired
	private NotConfigRepository notConfigDao;


	/* (non-Javadoc)
	 * @see com.bstsb.notify.core.AbstractService#primaryDao()
	 */
	@Override
	public GenericRepository<NotConfig> primaryDao() {
		return notConfigDao;
	}


	/**
	 * Find by config code.
	 *
	 * @param configCode the config code
	 * @return the not config
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public NotConfig findByConfigCode(String configCode) {
		return notConfigDao.findByConfigCode(configCode);
	}


	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<NotConfig> findAll() {
		return notConfigDao.findAll();
	}


	/**
	 * Update all.
	 *
	 * @param configs the configs
	 * @return the list
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<NotConfig> updateAll(List<NotConfig> configs) {
		for (NotConfig config : configs) {
			notConfigDao.saveAndFlush(config);
		}
		return configs;
	}

}