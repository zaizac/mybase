/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.bstsb.idm.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmConfigRepository;
import com.bstsb.idm.model.IdmConfig;
import com.bstsb.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Service(QualifierConstants.CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_SVC)
@Transactional
public class IdmConfigService extends AbstractService<IdmConfig> {

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
	public List<IdmConfig> updateAll(List<IdmConfig> configs, String userId) {
		for (IdmConfig config : configs) {
			IdmConfig conf = idmConfigDao.findByConfigCode(config.getConfigCode());
			if (!BaseUtil.isObjNull(conf)) {
				config.setId(conf.getId());
			} else {
				config.setCreateId(userId);
			}
			config.setUpdateId(userId);
			idmConfigDao.saveAndFlush(config);
		}
		return configs;
	}

}
