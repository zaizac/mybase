/**
 * Copyright 2017. Bestinet Sdn Bhd
 */
package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.BeConfigRepository;
import com.be.model.BeConfig;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Service(QualifierConstants.BE_CONFIG_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_CONFIG_SVC)
@Transactional(QualifierConstants.TRANS_MANAGER)
public class BeConfigService extends AbstractService<BeConfig> {

	@Autowired
	private BeConfigRepository beConfigDao;


	@Override
	public GenericRepository<BeConfig> primaryDao() {
		return beConfigDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}


	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = true, rollbackFor = Exception.class)
	public BeConfig findByConfigCode(String configCode) {
		return beConfigDao.findByConfigCode(configCode);
	}


	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = false, rollbackFor = Exception.class)
	public List<BeConfig> updateAll(List<BeConfig> beConfigs) {
		for (BeConfig beConfig : beConfigs) {
			beConfigDao.saveAndFlush(beConfig);
		}
		return beConfigs;
	}

}