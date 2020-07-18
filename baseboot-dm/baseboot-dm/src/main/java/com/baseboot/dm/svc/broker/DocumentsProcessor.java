/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.svc.broker;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.AsyncResult;

import com.baseboot.dm.model.Documents;
import com.baseboot.dm.sdk.client.constants.DmCacheConstants;
import com.baseboot.dm.sdk.client.constants.DmErrorCodeEnum;
import com.baseboot.dm.sdk.exception.DmException;
import com.baseboot.dm.svc.DocumentsService;
import com.baseboot.idm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DocumentsProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsProcessor.class);

	@Autowired
	CacheManager cacheManager;

	@Autowired
	private DocumentsService documentsService;


	public Documents processUpload(Documents documents) throws DmException {
		return processUpload(null, documents);
	}


	public Documents processUpload(String projId, Documents documents) throws DmException {
		try {
			documents = upload(projId, documents).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return documents;
	}
	
	public Documents processUpdate(String projId, Documents documents) throws DmException {
		try {
			documents = documentsService.findAndModify(projId, documents);
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return documents;
	}
	
	private Future<Documents> upload(String projId, Documents documents) throws DmException {
		try {
			documents = documentsService.createDocAndContainer(projId, documents);
			return new AsyncResult<Documents>(documents);
		} catch (Exception e) {
			LOGGER.error("Upload Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM001, new String[] { projId });
		}
	}


	public Documents processDownload(String id) throws DmException {
		return processDownload(null, id);
	}


	public Documents processDownload(String projId, String id) throws DmException {
		Documents documents = null;
		try {
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(id);
			LOGGER.info("Cache Key: {}", cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			LOGGER.info("Cache Key: {}", cache.getName());
			Cache.ValueWrapper cv = cache.get(cacheKey);
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				LOGGER.info("Retrieve from Cache >> Doc Id: {}", id);
				
				if(cv.get() instanceof com.baseboot.dm.sdk.model.Documents) {
					documents = new DozerBeanMapper().map((com.baseboot.dm.sdk.model.Documents) cv.get(), Documents.class);
				}
				
				if(cv.get() instanceof Documents) {
					documents = (Documents) cv.get();
				}
				
				if (!BaseUtil.isObjNull(documents)) {
					LOGGER.info("Download >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}", projId,
						documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFiles_id());
				}
				return documents;
			}
			
			documents = download(projId, id).get();
			if (!BaseUtil.isObjNull(documents)) {
				LOGGER.info("Download >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}", projId,
						documents.getRefno(), documents.getDocid(), documents.getId(), documents.getFiles_id());
				cache.put(cacheKey, documents);
			}
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
		}
		return documents;
	}


	private Future<Documents> download(String projId, String id) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		LOGGER.debug("CONTAINER: {}", bucket);
		try {
			Documents doc = documentsService.findByDocMgtId(bucket, id);
			if (BaseUtil.isObjNull(doc)) {
				LOGGER.info("Download: Bucket: {} - Doc Id: {}", bucket, id);
				throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
			}
			byte[] content = documentsService.findDocumentById(bucket, doc.getFiles_id());
			doc.setContent(content);
			LOGGER.info("Download >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}", bucket,
					doc.getRefno(), doc.getDocid(), doc.getId(), doc.getFiles_id());
			return new AsyncResult<Documents>(doc);
		} catch (Exception e) {
			LOGGER.error("Download Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
		}
	}


	public boolean processDelete(String projId, String id) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		LOGGER.info("Delete >> Bucket: {} - DocMgtId: {}", bucket, id);
		return documentsService.delete(bucket, id);
	}


	public Documents processSearch(String id) {
		return documentsService.findByDocMgtId(null, id);
	}


	public Documents processSearch(String projId, String id) {
		return documentsService.findByDocMgtId(projId, id);
	}


	public List<Documents> processSearch(String projId, String refno, String docid, String txnno) {
		return documentsService.findByRefNoAndDocId(projId, refno, docid, txnno);
	}

}