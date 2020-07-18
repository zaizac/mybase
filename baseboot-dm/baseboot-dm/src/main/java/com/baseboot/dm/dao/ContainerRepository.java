/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.baseboot.dm.core.IMongoRepository;
import com.baseboot.dm.model.Container;
import com.baseboot.dm.util.BeanConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Repository
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.CONTAINER_REPO)
@RepositoryDefinition(domainClass = Container.class, idClass = Integer.class)
public interface ContainerRepository extends IMongoRepository<Container> {

}