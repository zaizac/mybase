package com.idm.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmConfigRepository;
import com.idm.model.IdmConfig;
import com.util.BaseUtil;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_SVC)
public class IdmConfigService extends AbstractService<IdmConfig> {

	@Autowired
	private IdmConfigRepository idmConfigDao;


	@Override
	public GenericRepository<IdmConfig> primaryDao() {
		return idmConfigDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
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


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmConfig> findAllByPortalType(String portalType) {
		List<IdmConfig> config = new ArrayList<>(idmConfigDao.findByPortalType(portalType));
		if (!BaseUtil.isObjNull(config)) {
			return config;
		}
		return Collections.emptyList();
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<IdmConfig> updateAll(List<IdmConfig> configs, String userId) {
		for (IdmConfig config : configs) {
			IdmConfig conf = idmConfigDao.findByConfigCode(config.getConfigCode());
			if (!BaseUtil.isObjNull(conf)) {
				config.setConfigId(conf.getConfigId());
			} else {
				config.setCreateId(userId);
			}
			config.setUpdateId(userId);
			idmConfigDao.saveAndFlush(config);
		}
		return configs;
	}
	
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmConfig> findAllUserConfig() {
		return idmConfigDao.findAll();
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public IdmConfig updateUserConfig(IdmConfig s) {
		return super.update(s);
	}

}
