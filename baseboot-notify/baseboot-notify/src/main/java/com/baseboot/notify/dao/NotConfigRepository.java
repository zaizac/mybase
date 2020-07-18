/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.model.NotConfig;
import com.baseboot.notify.util.QualifierConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotConfig.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.CONFIG_DAO)
public interface NotConfigRepository extends GenericRepository<NotConfig> {

	@Query("select u from NotConfig u where u.configCode= :configCode ")
	public NotConfig findByConfigCode(@Param("configCode") String configCode);

}