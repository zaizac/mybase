/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.model.IdmRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmRole.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMROLE_REPOSITORY)
public interface IdmRoleRepository extends GenericRepository<IdmRole> {

	@Query("select u from IdmRole u where u.roleCode = :roleCode ")
	public IdmRole findUserRoleByRoleCode(@Param("roleCode") String roleCode);

}