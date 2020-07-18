package com.be.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefMetadata;

@Repository
@RepositoryDefinition(domainClass = RefMetadata.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_METADATA_DAO)
public interface RefMetadataRepository extends GenericRepository<RefMetadata>{
	
	@Query("select u from RefMetadata u ")
	List<RefMetadata> getAllMetadata();

	@Query("select u from RefMetadata u where u.mtdtType = :mtdtType ")
	public List<RefMetadata> findByMtdtType(@Param("mtdtType") String mtdtType);
	
}
