package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmConfig;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmConfig.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_DAO)
public interface IdmConfigRepository extends GenericRepository<IdmConfig> {

	public IdmConfig findByConfigCode(String configCode);


	@Query("select u from IdmConfig u where u.configCode LIKE CONCAT ('%', :portalType, '%')")
	public List<IdmConfig> findByPortalType(@Param("portalType") String portalType);
}
