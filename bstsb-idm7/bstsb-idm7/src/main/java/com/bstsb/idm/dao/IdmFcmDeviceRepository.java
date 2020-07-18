/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmFcmDevice;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmFcmDevice.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_DEVICE_REPOSITORY)
public interface IdmFcmDeviceRepository extends GenericRepository<IdmFcmDevice> {

	@Query("select u from IdmFcmDevice u where u.fcmId = :fcmId and u.machineId = :machineId ")
	public IdmFcmDevice findByFcmIdAndMachineId(@Param("fcmId") Integer fcmId, @Param("machineId") String machineId);

}
