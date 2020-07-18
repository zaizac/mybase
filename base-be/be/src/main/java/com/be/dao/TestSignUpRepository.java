/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.be.dao;


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
import com.be.model.TestSignUp;


/**
 * @author mary.jane
 *
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefStatus.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier("testSignUpDao")
public interface TestSignUpRepository extends GenericRepository<TestSignUp> {

	@Query("select u from TestSignUp u where u.refNo = :refNo ")
	TestSignUp findByRefNo(@Param("refNo") String refNo);

}
