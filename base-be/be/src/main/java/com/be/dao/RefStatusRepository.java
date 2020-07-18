/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.be.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefStatus;


/**
 * @author michelle.angela
 * @since 19 Feb 2019
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefStatus.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATUS_DAO)
public interface RefStatusRepository extends GenericRepository<RefStatus> {
	
	@Query("select u from RefStatus u ")
	List<RefStatus> getAllStatus();

	@Query("select u from RefStatus u where u.statusCd = :statusCd ")
	public RefStatus findByStatusCode(@Param("statusCd") String statusCd);
}
