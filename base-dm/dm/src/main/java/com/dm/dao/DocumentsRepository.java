/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.dm.constants.BeanConstants;
import com.dm.core.IMongoRepository;
import com.dm.model.Documents;


/**
 * The Interface DocumentsRepository.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Repository
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.DOCUMENT_REPO)
@RepositoryDefinition(domainClass = Documents.class, idClass = Integer.class)
public interface DocumentsRepository extends IMongoRepository<Documents> {


}