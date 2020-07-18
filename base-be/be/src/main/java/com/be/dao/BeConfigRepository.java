/**
 * Copyright 2017. Bestinet Sdn Bhd
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
import com.be.model.BeConfig;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = BeConfig.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_CONFIG_DAO)
public interface BeConfigRepository extends GenericRepository<BeConfig> {

	@Query("select u from BeConfig u where u.configCode= :configCode ")
	BeConfig findByConfigCode(@Param("configCode") String configCode);

}