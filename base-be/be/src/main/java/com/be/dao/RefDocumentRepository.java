package com.be.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.RefDocument;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = RefDocument.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_DOCUMENT_DAO)
public interface RefDocumentRepository extends GenericRepository<RefDocument> {

	@Query("select u from RefDocument u where u.docId = :docId")
	public RefDocument findTrxnDocumentsByDocId(@Param("docId") Integer docId);


	@Query("select u from RefDocument u where u.docTrxnNo = :docTrxnNo")
	public List<RefDocument> findAllByTrxnNo(@Param("docTrxnNo") String docTrxnNo);

}