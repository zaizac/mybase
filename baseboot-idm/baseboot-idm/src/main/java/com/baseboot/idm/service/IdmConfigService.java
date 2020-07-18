/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractService;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.dao.IdmConfigRepository;
import com.baseboot.idm.model.IdmConfig;
import com.baseboot.idm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Service(QualifierConstants.CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_SVC)
@Transactional
public class IdmConfigService extends AbstractService<IdmConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmConfigService.class);

	@Lazy
	@Autowired
	private IdmConfigRepository idmConfigDao;


	@Override
	public GenericRepository<IdmConfig> primaryDao() {
		return idmConfigDao;
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmConfig findByConfigCode(String configCode) {
		return idmConfigDao.findByConfigCode(configCode);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public String findValueByConfigCode(String configCode) {
		IdmConfig config = idmConfigDao.findByConfigCode(configCode);
		if (!BaseUtil.isObjNull(config)) {
			return config.getConfigVal();
		}
		return null;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<IdmConfig> updateAll(List<IdmConfig> configs) {
		for (IdmConfig config : configs) {
			idmConfigDao.saveAndFlush(config);
		}
		return configs;
	}

}
