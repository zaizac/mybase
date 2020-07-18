/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.be.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.BeCtrlGen;
import com.be.model.BeCtrlGenPK;


/**
 * @author Mubarak A
 * @since Feb 14, 2019
 */
@Repository
@RepositoryDefinition(domainClass = BeCtrlGen.class, idClass = BeCtrlGen.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_CTRL_GEN_DAO)
public interface BeCtrlGenRepository extends GenericRepository<BeCtrlGen> {

	@Query("select u from BeCtrlGen u where u.beCtrlGenPK= :beCtrlGenPK ")
	BeCtrlGen findByPK(@Param("beCtrlGenPK") BeCtrlGenPK beCtrlGenPK);

}