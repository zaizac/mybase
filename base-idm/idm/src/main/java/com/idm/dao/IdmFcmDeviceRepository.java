package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmFcmDevice;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Transactional
@Repository
@RepositoryDefinition(domainClass = IdmFcmDevice.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_DEVICE_REPOSITORY)
public interface IdmFcmDeviceRepository extends GenericRepository<IdmFcmDevice> {

	public IdmFcmDevice findByMachineIdAndFcmFcmId(String machineId, Integer fcmId);
	
	public IdmFcmDevice findByMachineId(String machineId);

	public List<IdmFcmDevice> findByFcmFcmId(Integer fcmId);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE IdmFcmDevice u SET u.status = false WHERE u.status = true AND u.fcm.fcmId = (SELECT x.fcmId FROM IdmFcm x WHERE x.userId = :userId)")
	int updateStatusByFcmUserId(@Param("userId") String userId);
}
