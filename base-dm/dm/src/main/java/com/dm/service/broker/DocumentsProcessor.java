/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.service.broker;


import java.util.List;
import java.util.concurrent.Future;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.AsyncResult;

import com.dm.config.cache.RedisCacheWrapper;
import com.dm.model.Documents;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.dm.service.DocumentsService;
import com.util.BaseUtil;


/**
 * The Class DocumentsProcessor.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DocumentsProcessor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsProcessor.class);

	/** The cache manager. */
	@Autowired
	CacheManager cacheManager;

	/** The documents service. */
	@Autowired
	private DocumentsService documentsService;

	/** The Constant LOG_CACHE_KEY. */
	private static final String LOG_CACHE_KEY = "Cache Key: {}";


	/**
	 * Process upload.
	 *
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents processUpload(Documents documents) {
		return processUpload(null, documents);
	}


	/**
	 * Process upload.
	 *
	 * @param projId
	 *             the proj id
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents processUpload(String projId, Documents documents) {
		try {
			documents = upload(projId, documents).get();
		} catch (DmException de) {
			de.printStackTrace();
			if (BaseUtil.isEqualsCaseIgnore(DmErrorCodeEnum.E500DOM004.name(), de.getInternalErrorCode())) {
				throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { documents.getId() });
			} else {
				throw new DmException(DmErrorCodeEnum.E500DOM001, new String[] { projId });
			}
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return documents;
	}


	/**
	 * Process update.
	 *
	 * @param projId
	 *             the proj id
	 * @param documents
	 *             the documents
	 * @return the documents
	 */
	public Documents processUpdate(String projId, Documents documents) {
		try {
			documents = documentsService.findAndModify(projId, documents);
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return documents;
	}


	/**
	 * Upload.
	 *
	 * @param projId
	 *             the proj id
	 * @param documents
	 *             the documents
	 * @return the future
	 */
	private Future<Documents> upload(String projId, Documents documents) {
		try {
			if (!BaseUtil.isObjNull(documents.getId())) {
				if (BaseUtil.isStringNull(documents.getId())) {
					throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { documents.getId() });
				}
				String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(documents.getId());
				LOGGER.info(LOG_CACHE_KEY, cacheKey);
				RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
				LOGGER.info(LOG_CACHE_KEY, cache.getName());
				cache.evictByPrefix(cacheKey);
			}
			documents = documentsService.createDocAndContainer(projId, documents);
			return new AsyncResult<>(documents);
		} catch (DmException de) {
			if (BaseUtil.isEqualsCaseIgnore(DmErrorCodeEnum.E500DOM004.name(), de.getInternalErrorCode())) {
				throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { documents.getId() });
			} else {
				throw new DmException(DmErrorCodeEnum.E500DOM001, new String[] { projId });
			}
		} catch (Exception e) {
			LOGGER.error("Upload Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM001, new String[] { projId });
		}
	}


	/**
	 * Process download.
	 *
	 * @param id
	 *             the id
	 * @return the documents
	 */
	public Documents processDownload(String id) {
		return processDownload(null, id);
	}


	/**
	 * Process download.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return the documents
	 */
	public Documents processDownload(String projId, String id) {
		Documents documents = null;
		try {
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(id);
			LOGGER.info(LOG_CACHE_KEY, cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			LOGGER.info(LOG_CACHE_KEY, cache.getName());
			Cache.ValueWrapper cv = cache.get(cacheKey);
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				LOGGER.info("Retrieve from Cache >> Doc Id: {}", id);

				if (cv.get() instanceof com.dm.sdk.model.Documents) {
					documents = new DozerBeanMapper().map(cv.get(), Documents.class);
				}

				if (cv.get() instanceof Documents) {
					documents = (Documents) cv.get();
				}

				if (documents != null) {
					LOGGER.info(
							"processDownload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}",
							projId, documents.getRefno(), documents.getDocid(), documents.getId(),
							documents.getFilesId());
				}
				return documents;
			}

			documents = download(projId, id).get();
			if (!BaseUtil.isObjNull(documents)) {
				LOGGER.info(
						"processDownload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}",
						projId, documents.getRefno(), documents.getDocid(), documents.getId(),
						documents.getFilesId());
				cache.put(cacheKey, documents);
			}
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
		}
		return documents;
	}


	/**
	 * Download.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return the future
	 */
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
			byte[] content = documentsService.findDocumentById(bucket, doc.getFilesId());
			doc.setContent(content);
			LOGGER.info("Download >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}", bucket,
					doc.getRefno(), doc.getDocid(), doc.getId(), doc.getFilesId());
			return new AsyncResult<>(doc);
		} catch (Exception e) {
			LOGGER.error("Download Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
		}
	}


	/**
	 * Process delete.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return true, if successful
	 */
	public boolean processDelete(String projId, String id) {
		String bucket = "DM_DEFAULT";
		if (projId != null) {
			bucket = projId;
		}
		LOGGER.info("Delete >> Bucket: {} - DocMgtId: {}", bucket, id);
		return documentsService.delete(bucket, id);
	}


	/**
	 * Process search.
	 *
	 * @param id
	 *             the id
	 * @return the documents
	 */
	public Documents processSearch(String id) {
		return documentsService.findByDocMgtId(null, id);
	}


	/**
	 * Process search.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return the documents
	 */
	public Documents processSearch(String projId, String id) {
		return documentsService.findByDocMgtId(projId, id);
	}


	/**
	 * Process search.
	 *
	 * @param projId
	 *             the proj id
	 * @param refno
	 *             the refno
	 * @param docid
	 *             the docid
	 * @param txnno
	 *             the txnno
	 * @param id
	 *             the id
	 * @return the list
	 */
	public List<Documents> processSearch(String projId, String refno, String docid, String txnno, String id) {
		return documentsService.findByRefNoAndDocId(projId, refno, docid, txnno, id);
	}

}