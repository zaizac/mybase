package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmRole.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLE_REPOSITORY)
public interface IdmRoleRepository extends GenericRepository<IdmRole> {

	public IdmRole findByRoleCode(String roleCode);

	@Query("select u from IdmRole u inner join fetch u.portalType p")
	public List<IdmRole> findAllIncludePortalType();
}