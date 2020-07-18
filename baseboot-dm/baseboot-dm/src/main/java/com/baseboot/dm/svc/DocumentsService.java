/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.svc;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.baseboot.dm.core.AbstractService;
import com.baseboot.dm.core.IMongoRepository;
import com.baseboot.dm.dao.DocumentsRepository;
import com.baseboot.dm.model.Documents;
import com.baseboot.dm.sdk.client.constants.DmCacheConstants;
import com.baseboot.dm.sdk.client.constants.DmErrorCodeEnum;
import com.baseboot.dm.sdk.exception.DmException;
import com.baseboot.dm.util.BeanConstants;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Service(BeanConstants.DOCUMENT_SVC)
@Scope(BeanConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(BeanConstants.DOCUMENT_SVC)
public class DocumentsService extends AbstractService<Documents, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsService.class);

	@Autowired
	CacheManager cacheManager;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	@Qualifier(BeanConstants.DOCUMENT_REPO)
	private DocumentsRepository docDao;

	@Autowired
	@Qualifier("storageService")
	private StorageService storeSvc;


	@Override
	public IMongoRepository<Documents> primaryDao() {
		return docDao;
	}


	public Documents findByDocMgtId(String projId, String docId) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		Documents documents = mongoOperations.findById(docId, Documents.class, bucket);
		if (BaseUtil.isObjNull(documents)) {
			LOGGER.info("Search >> Bucket: {} - DocMgtId: {}", bucket, docId);
			throw new DmException(DmErrorCodeEnum.E404DOM001);
		}
		LOGGER.info("Search >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {}  - Files Id: {}", bucket,
				documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFiles_id());
		return documents;
	}


	public List<Documents> findByRefNoAndDocId(String projId, String refNo, String docid, String txnno) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		LOGGER.info("Search >> Bucket: {} - DocRefNo: {} - DocId: {} - TxnNo: {}", bucket, refNo, docid, txnno);
		Query query = new Query();
		query.addCriteria(Criteria.where("refno").is(refNo));
		query.addCriteria(Criteria.where("docid").is(docid));
		query.addCriteria(Criteria.where("txnno").is(txnno));
		List<Documents> documents = mongoOperations.find(query, Documents.class, bucket);
		if (BaseUtil.isListZero(documents)) {
			throw new DmException(DmErrorCodeEnum.E404DOM001);
		}
		return documents;
	}


	public Documents createDocAndContainer(Documents documents) {
		return createDocAndContainer(null, documents);
	}


	public Documents createDocAndContainer(String projId, Documents documents) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		String fileId = null;
		if (documents.getContent() != null) {
			fileId = storeSvc.save(bucket, new ByteArrayInputStream(documents.getContent()),
					documents.getContentType(), documents.getFilename());
			GridFSDBFile fileObj = storeSvc.get(bucket, fileId);
			documents.setLength(fileObj.getLength());
			documents.setUploadDate(fileObj.getUploadDate());
		}

		documents.setFiles_id(fileId);
		if (documents.getId() != null && !documents.getId().isEmpty()) {
			LOGGER.info("REUPLOAD Document: {}", documents.getId());
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(documents.getId());
			LOGGER.info("CACHE EVICT Document: {}", cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			cache.evict(cacheKey);
		}
		mongoOperations.save(documents, bucket);
		LOGGER.info("Upload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {}  - Files Id: {}", bucket,
				documents.getRefno(), documents.getDocid(), documents.getId(), fileId);
		return documents;
	}

	public Documents findAndModify(String projId, Documents documents) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
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
				documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFiles_id());
		return documents;
	}
	

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


	public boolean delete(String bucket, String id) {
		//FIXME: WriteResult result = mongoOperations.remove(id, bucket);
		return true;
	}

}