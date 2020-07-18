/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.service;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.dm.constants.BeanConstants;
import com.dm.core.AbstractService;
import com.dm.core.IMongoRepository;
import com.dm.dao.DocumentsRepository;
import com.dm.model.Documents;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.mongodb.gridfs.GridFSDBFile;
import com.util.BaseUtil;


/**
 * The Class DocumentsService.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.DOCUMENT_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.DOCUMENT_SVC)
public class DocumentsService extends AbstractService<Documents> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsService.class);

	/** The Constant DM_DEFAULT_TEXT. */
	private static final String DM_DEFAULT_TEXT = "DM_DEFAULT";

	/** The cache manager. */
	@Autowired
	CacheManager cacheManager;

	/** The mongo operations. */
	@Autowired
	MongoOperations mongoOperations;

	/** The doc dao. */
	@Autowired
	@Qualifier(BeanConstants.DOCUMENT_REPO)
	private DocumentsRepository docDao;

	/** The store svc. */
	@Autowired
	@Qualifier("storageService")
	private StorageService storeSvc;


	/*
	 * (non-Javadoc)
	 *
	 * @see com.dm.core.AbstractService#primaryDao()
	 */
	@Override
	public IMongoRepository<Documents> primaryDao() {
		return docDao;
	}


	/**
	 * Find by doc mgt id.
	 *
	 * @param projId
	 *             the proj id
	 * @param docId
	 *             the doc id
	 * @return the documents
	 */
	public Documents findByDocMgtId(String projId, String docId) {
		String bucket = DM_DEFAULT_TEXT;
		if (projId != null) {
			bucket = projId;
		}

		Documents documents = mongoOperations.findById(docId, Documents.class, bucket);
		if (BaseUtil.isObjNull(documents)) {
			LOGGER.info("Search >> Bucket: {} - DocMgtId: {}", bucket, docId);
			throw new DmException(DmErrorCodeEnum.E404DOM001);
		}
		LOGGER.info("Search >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {}  - Files Id: {}", bucket,
				documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFilesId());
		return documents;
	}


	/**
	 * Find by ref no and doc id.
	 *
	 * @param projId
	 *             the proj id
	 * @param refNo
	 *             the ref no
	 * @param docid
	 *             the docid
	 * @param txnno
	 *             the txnno
	 * @param id
	 *             the id
	 * @return the list
	 */
	public List<Documents> findByRefNoAndDocId(String projId, String refNo, String docid, String txnno, String id) {
		String bucket = DM_DEFAULT_TEXT;
		if (projId != null) {
			bucket = projId;
		}
		LOGGER.info("Search >> Bucket: {} - DocRefNo: {} - DocId: {} - TxnNo: {}", bucket, refNo, docid, txnno);
		Query query = new Query();
		if (!BaseUtil.isObjNull(refNo)) {
			query.addCriteria(Criteria.where("refno").is(refNo));
		}
		if (!BaseUtil.isObjNull(docid)) {
			query.addCriteria(Criteria.where("docid").is(docid));
		}
		if (!BaseUtil.isObjNull(txnno)) {
			query.addCriteria(Criteria.where("txnno").is(txnno));
		}
		if (!BaseUtil.isObjNull(id)) {
			query.addCriteria(Criteria.where("id").is(id));
		}
		List<Documents> documents = mongoOperations.find(query, Documents.class, bucket);
		if (BaseUtil.isListZero(documents)) {
			throw new DmException(DmErrorCodeEnum.E404DOM001);
		}
		return documents;
	}


	/**
	 * Creates the doc and container.
	 *
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents createDocAndContainer(Documents documents) {
		return createDocAndContainer(null, documents);
	}


	/**
	 * Creates the doc and container.
	 *
	 * @param projId
	 *             the proj id
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents createDocAndContainer(String projId, Documents documents) {
		String bucket = DM_DEFAULT_TEXT;
		if (projId != null) {
			bucket = projId;
		} else if (!BaseUtil.isObjNull(documents.getDmBucket())) {
			bucket = documents.getDmBucket();
		}

		String fileId = null;
		if (documents.getContent() != null) {
			fileId = storeSvc.save(bucket, new ByteArrayInputStream(documents.getContent()),
					documents.getContentType(), documents.getFilename());
			GridFSDBFile fileObj = storeSvc.get(bucket, fileId);
			documents.setLength(fileObj.getLength());
			documents.setUploadDate(fileObj.getUploadDate());
		}

		documents.setFilesId(fileId);
		int version = 1;
		List<Documents> history = new ArrayList<>();
		if (documents.getId() != null && !documents.getId().isEmpty()) {
			Documents docOld = findByDocMgtId(bucket, documents.getId());
			if (!BaseUtil.isObjNull(docOld)) {
				LOGGER.info("OLD DOCUMENT: {}", docOld.getVersion());
				version = version + docOld.getVersion();

				if (!BaseUtil.isListNullZero(docOld.getHistory())) {
					history = docOld.getHistory();
					docOld.setHistory(null);
				}
				history.add(docOld);
				documents.setHistory(history);
			}

			LOGGER.info("REUPLOAD Document: {}", documents.getId());
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(documents.getId());
			LOGGER.info("CACHE EVICT Document: {}", cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			cache.evict(cacheKey);
		}
		documents.setVersion(version);
		mongoOperations.save(documents, bucket);
		LOGGER.info("Upload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {}  - Files Id: {}", bucket,
				documents.getRefno(), documents.getDocid(), documents.getId(), fileId);
		return documents;
	}


	/**
	 * Find and modify.
	 *
	 * @param projId
	 *             the proj id
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents findAndModify(String projId, Documents documents) {
		String bucket = DM_DEFAULT_TEXT;
		if (projId != null) {
			bucket = projId;
		} else if (!BaseUtil.isObjNull(documents.getDmBucket())) {
			bucket = documents.getDmBucket();
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(documents.getId()));

		Update update = new Update();
		update.set("refno", documents.getRefno());
		update.set("txnno", documents.getTxnno());
		update.set("docid", documents.getDocid());

		documents = mongoOperations.findAndModify(query, update, Documents.class, bucket);
		if (documents.getId() != null && !documents.getId().isEmpty()) {
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(documents.getId());
			LOGGER.debug("CACHE EVICT Document: {}", cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			cache.evict(cacheKey);
		}
		LOGGER.info("Update Metadata >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {}  - Files Id: {}", bucket,
				documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFilesId());
		return documents;
	}


	/**
	 * Find document by id.
	 *
	 * @param bucket
	 *             the bucket
	 * @param id
	 *             the id
	 * @return the byte[]
	 */
	public byte[] findDocumentById(String bucket, String id) {
		GridFSDBFile dbFile = storeSvc.get(bucket, id);
		byte[] targetArray = new byte[1024];
		if (dbFile != null) {
			InputStream is = dbFile.getInputStream();
			try {
				targetArray = IOUtils.toByteArray(is);
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return targetArray;
	}


	/**
	 * Delete.
	 *
	 * @param bucket
	 *             the bucket
	 * @param id
	 *             the id
	 * @return true, if successful
	 */
	public boolean delete(String bucket, String id) {
		mongoOperations.remove(id, bucket);
		return true;
	}

}