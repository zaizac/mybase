/**
 * Copyright 2019. 
 */
package com.notify.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notify.constants.QualifierConstants;
import com.notify.core.GenericRepository;
import com.notify.model.NotConfig;



/**
 * The Interface NotConfigRepository.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotConfig.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_DAO)
public interface NotConfigRepository extends GenericRepository<NotConfig> {

	/**
	 * Find by config code.
	 *
	 * @param configCode the config code
	 * @return the not config
	 */
	@Query("select u from NotConfig u where u.configCode= :configCode ")
	public NotConfig findByConfigCode(@Param("configCode") String configCode);

}