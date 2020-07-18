package com.be.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefMetadata;
import com.be.model.RefReason;


@Repository
@RepositoryDefinition(domainClass = RefMetadata.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_REASON_DAO)
public interface RefReasonRepository extends GenericRepository<RefReason> {

}
