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
import com.notify.model.NotEmailTemplate;


/**
 * The Interface NotEmailTemplateRepository.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = NotEmailTemplate.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_EMAIL_TEMPLATE_DAO)
public interface NotEmailTemplateRepository extends GenericRepository<NotEmailTemplate> {

	/**
	 * Find not email template by id.
	 *
	 * @param id the id
	 * @return the not email template
	 */
	@Query("select u from NotEmailTemplate u where u.id = :id")
	public NotEmailTemplate findNotEmailTemplateById(@Param("id") Integer id);


	/**
	 * Find by template type.
	 *
	 * @param templateType the template type
	 * @return the not email template
	 */
	@Query("select u from NotEmailTemplate u where u.templateType = :templateType")
	public NotEmailTemplate findByTemplateType(@Param("templateType") String templateType);

	
	/**
	 * Find by template code.
	 *
	 * @param templateCode the template code
	 * @return the not email template
	 */
	@Query("select u from NotEmailTemplate u where u.templateCode = :templateCode")
	public NotEmailTemplate findByTemplateCode(@Param("templateCode") String templateCode);
}
