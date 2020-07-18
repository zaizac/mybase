package com.idm.service;


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

import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmFcmQf;
import com.idm.dao.IdmFcmRepository;
import com.idm.model.IdmFcm;
import com.idm.model.IdmFcmDevice;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Lazy
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_FCM_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_SERVICE)
public class IdmFcmService extends AbstractService<IdmFcm> {

	@Autowired
	private IdmFcmRepository idmFcmDao;

	@Autowired
	private IdmFcmQf idmFcmQf;

	@Override
	public GenericRepository<IdmFcm> primaryDao() {
		return idmFcmDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmFcmQf.generateCriteria(cb, from, criteria);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmFcm> search(IdmFcm idmFcm) {
		return idmFcmDao.findAll(idmFcmQf.searchByProperty(idmFcm));
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmFcm findByFcmId(Integer fcmId) {
		return idmFcmDao.findByFcmId(fcmId);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmFcm findByUserId(String userId) {
		return idmFcmDao.findByUserId(userId);
	}

}
