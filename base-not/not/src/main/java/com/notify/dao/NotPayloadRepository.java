/**
 * Copyright 2019. 
 */
package com.notify.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notify.constants.QualifierConstants;
import com.notify.core.GenericRepository;
import com.notify.model.NotPayload;



/**
 * The Interface NotPayloadRepository.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotPayload.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_DAO)
public interface NotPayloadRepository extends GenericRepository<NotPayload> {

	/**
	 * Find notification by id.
	 *
	 * @param id the id
	 * @return the not payload
	 */
	@Query("select u from NotPayload u where u.id = :id")
	public NotPayload findNotificationById(@Param("id") Integer id);


	/**
	 * Find notification by status.
	 *
	 * @param status the status
	 * @return the list
	 */
	@Query("select u from NotPayload u where u.status = :status")
	public List<NotPayload> findNotificationByStatus(@Param("status") String status);
	
	/**
	 * Find jls wrkr email notification.
	 *
	 * @param notifyTo the notify to
	 * @return the list
	 */
	@Query("select u from NotPayload u where u.notifyTo = :notifyTo")
	public List<NotPayload> findJlsWrkrEmailNotification(@Param("notifyTo") String notifyTo);
	
	/**
	 * Find jls wrkr email fcm noti.
	 *
	 * @param notifyTo the notify to
	 * @return the list
	 */
	@Query("select u from NotPayload u where u.notifyTo = :notifyTo and u.notifyType = 'fcm' ")
	public List<NotPayload> findJlsWrkrEmailFcmNoti(@Param("notifyTo") String notifyTo);

}
