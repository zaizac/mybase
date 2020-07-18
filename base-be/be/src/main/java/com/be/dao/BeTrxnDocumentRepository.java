/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.be.dao;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.constants.QualifierConstants;
import com.be.core.GenericRepository;
import com.be.model.BeTrxnDocument;
import com.be.model.BeTrxnDocumentPK;


/**
 * @author nurul.naimma
 *
 * @since 18 Feb 2019
 */

@Repository
@RepositoryDefinition(domainClass = BeTrxnDocument.class, idClass = String.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_TRXN_DOC_DAO)
public interface BeTrxnDocumentRepository extends GenericRepository<BeTrxnDocument> {

	@Query("select u from BeTrxnDocument u where u.id.docRefNo =:docRefNo and u.id.docId =:docId and u.id.docMgtId =:docMgtId")
	BeTrxnDocument findTxnDocByRefNoDocId(@Param("docRefNo") String docRefNo, @Param("docId") Integer docId,
			@Param("docMgtId") String docMgtId);


	@Query("select u from BeTrxnDocument u where u.appRefNo = :appRefNo ")
	List<BeTrxnDocument> findByQuotaRefNo(@Param("appRefNo") String appRefNo);


	@Query("select u from BeTrxnDocument u where u.id.docRefNo = :docRefNo ")
	List<BeTrxnDocument> findByDocRefNo(@Param("docRefNo") String docRefNo);


	@Query("select u from BeTrxnDocument u where u.id.docRefNo = :refNo and u.id.docId = :docId")
	BeTrxnDocument findTrxnDocumentsByRefNoAndDocId(@Param("refNo") String refNo, @Param("docId") Integer docId);


	@Modifying(clearAutomatically = true)
	@Query("update BeTrxnDocument u set u.id.docMgtId = :docMgtId, u.docContentType =:docContentType, u.updateId =:updateId, u.updateDt =:updateDt "
			+ "where u.id.docRefNo = :refNo and u.id.docId = :docId")
	int updateTrxnDocumentsByRefNoAndDocId(@Param("refNo") String refNo, @Param("docId") Integer docId,
			@Param("docMgtId") String docMgtId, @Param("docContentType") String docContentType,
			@Param("updateId") String updateId, @Param("updateDt") Timestamp updateDt);


	@Query("select u from BeTrxnDocument u where u.appRefNo = :appRefNo and u.id.docId = :docId")
	BeTrxnDocument findTrxnDocumentsByQuotaRefNoAndDocId(@Param("appRefNo") String appRefNo,
			@Param("docId") Integer docId);


	@Query("select u from BeTrxnDocument u where u.id = :id ")
	BeTrxnDocument findByPK(@Param("id") BeTrxnDocumentPK id);

}
