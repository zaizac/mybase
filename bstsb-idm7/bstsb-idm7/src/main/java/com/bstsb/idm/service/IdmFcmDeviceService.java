/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmFcmDeviceRepository;
import com.bstsb.idm.model.IdmFcmDevice;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Transactional
@Service(QualifierConstants.IDM_FCM_DEVICE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_DEVICE_SERVICE)
public class IdmFcmDeviceService extends AbstractService<IdmFcmDevice> {

	@Autowired
	private IdmFcmDeviceRepository idmFcmDeviceDao;


	@Override
	public GenericRepository<IdmFcmDevice> primaryDao() {
		return idmFcmDeviceDao;
	}


	@Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmFcmDevice findByFcmIdAndMachineId(Integer fcmId, String machineId) {
		return idmFcmDeviceDao.findByFcmIdAndMachineId(fcmId, machineId);
	}

}
