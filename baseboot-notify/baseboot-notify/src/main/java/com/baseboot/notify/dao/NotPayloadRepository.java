/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.model.NotPayload;
import com.baseboot.notify.util.QualifierConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotPayload.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOTIFICATION_DAO)
public interface NotPayloadRepository extends GenericRepository<NotPayload> {

	@Query("select u from NotPayload u where u.id = :id")
	public NotPayload findNotificationById(@Param("id") Integer id);


	@Query("select u from NotPayload u where u.status = :status")
	public List<NotPayload> findNotificationByStatus(@Param("status") String status);

}
