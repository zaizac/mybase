/**
 * Copyright 2018. Bestinet Sdn. Bhd.
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
import com.baseboot.idm.model.IdmConfig;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmConfig.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_DAO)
public interface IdmConfigRepository extends GenericRepository<IdmConfig> {

	@Query("select u from IdmConfig u where u.configCode= :configCode ")
	public IdmConfig findByConfigCode(@Param("configCode") String configCode);

}
