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
import com.baseboot.notify.model.NotEmailTemplate;
import com.baseboot.notify.util.QualifierConstants;

/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotEmailTemplate.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_DAO)
public interface NotEmailTemplateRepository extends GenericRepository<NotEmailTemplate> {

	@Query("select u from NotEmailTemplate u where u.id = :id")
	public NotEmailTemplate findNotEmailTemplateById(@Param("id") Integer id);


	@Query("select u from NotEmailTemplate u where u.templateType = :templateType")
	public NotEmailTemplate findByTemplateType(@Param("templateType") String templateType);

}
