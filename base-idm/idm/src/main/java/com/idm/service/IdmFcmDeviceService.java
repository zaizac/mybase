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
import com.idm.dao.IdmFcmDeviceQf;
import com.idm.dao.IdmFcmDeviceRepository;
import com.idm.model.IdmFcmDevice;
import com.util.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Lazy
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_FCM_DEVICE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_DEVICE_SERVICE)
public class IdmFcmDeviceService extends AbstractService<IdmFcmDevice> {

	@Autowired
	private IdmFcmDeviceRepository idmFcmDeviceDao;

	@Autowired
	private IdmFcmDeviceQf idmFcmDeviceQf;

	@Override
	public GenericRepository<IdmFcmDevice> primaryDao() {
		return idmFcmDeviceDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmFcmDeviceQf.generateCriteria(cb, from, criteria);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmFcmDevice> search(IdmFcmDevice idmFcmDevice) {
		return idmFcmDeviceDao.findAll(idmFcmDeviceQf.searchByProperty(idmFcmDevice));
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmFcmDevice> findByFcmId(Integer fcmId) {
		return idmFcmDeviceDao.findByFcmFcmId(fcmId);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmFcmDevice findByMachineId(String machineId) {
		return idmFcmDeviceDao.findByMachineId(machineId);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmFcmDevice findByFcmIdAndMachineId(Integer fcmId, String machineId) {
		return idmFcmDeviceDao.findByMachineIdAndFcmFcmId(machineId, fcmId);
	}
	
	@Transactional(value = QualifierConstants.TRANS_MANAGER, readOnly = false, rollbackFor = Exception.class)
	public int updateStatusByUserId(String userId) {
		return idmFcmDeviceDao.updateStatusByFcmUserId(userId);
	}

}
