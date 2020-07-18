/**
 * Copyright 2017. Bestinet Sdn Bhd
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
import com.be.model.RefState;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefState.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_STATE_DAO)
public interface RefStateRepository extends GenericRepository<RefState> {

	@Query("select u from RefState u ")
	public List<RefState> findAllStates();


	@Query("select u from RefState u where u.stateCd = :stateCd ")
	public RefState findByStateCode(@Param("stateCd") String stateCd);

}