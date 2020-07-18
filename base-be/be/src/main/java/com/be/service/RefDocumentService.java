package com.be.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.RefDocumentQf;
import com.be.dao.RefDocumentRepository;
import com.be.model.RefDocument;
import com.be.sdk.constants.BeCacheConstants;
import com.be.sdk.model.IQfCriteria;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@Transactional
@Service(QualifierConstants.REF_DOCUMENT_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.REF_DOCUMENT_SVC)
@CacheConfig(cacheNames = BeCacheConstants.CACHE_BUCKET)
public class RefDocumentService extends AbstractService<RefDocument> {

	@Autowired
	private RefDocumentRepository refDocumentDao;

	@Autowired
	private RefDocumentQf refDocumentQf;


	@Override
	public GenericRepository<RefDocument> primaryDao() {
		return refDocumentDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return refDocumentQf.generateCriteria(cb, from, criteria);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RefDocument> getAllDocuments() {
		return refDocumentDao.findAll();
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefDocument findTrxnDocumentsByDocId(Integer docId) {
		return refDocumentDao.findTrxnDocumentsByDocId(docId);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<RefDocument> findTrxnDocumentsByCriteria(RefDocument docs) {
		return refDocumentQf.searchAllByProperty(docs);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RefDocument saveAndUpdate(RefDocument refDoc) {
		return refDocumentDao.saveAndFlush(refDoc);
	}

}