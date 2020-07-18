/**
 * Copyright 2019. 
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
import com.dm.model.FileMetadata;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.dm.sdk.model.Documents;
import com.dm.service.FileStorageService;
import com.util.BaseUtil;


/**
 * The Class FileDocumentProcessor.
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

	/** The FileDocument service. */
	@Autowired
	private FileStorageService fileStorageService;

	/** The Constant LOG_CACHE_KEY. */
	private static final String LOG_CACHE_KEY = "Cache Key: {}";


	/**
	 * Process upload.
	 *
	 * @param FileMetadata
	 *             the FileDocument
	 * @return the FileDocument
	 */
	public FileMetadata processUpload(FileMetadata FileMetadata) {
		return processUpload(null, FileMetadata);
	}


	/**
	 * Process upload.
	 *
	 * @param projId
	 *             the proj id
	 * @param FileMetadata
	 *             the FileDocument
	 * @return the FileDocument
	 */
	public FileMetadata processUpload(String projId, FileMetadata FileMetadata) {
		try {
			FileMetadata = upload(projId, FileMetadata).get();
		} catch (DmException de) {
			de.printStackTrace();
			if (BaseUtil.isEqualsCaseIgnore(DmErrorCodeEnum.E500DOM004.name(), de.getInternalErrorCode())) {
				throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { FileMetadata.getId() });
			} else {
				throw new DmException(DmErrorCodeEnum.E500DOM001, new String[] { projId });
			}
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return FileMetadata;
	}


	/**
	 * Process update.
	 *
	 * @param projId
	 *             the proj id
	 * @param FileMetadata
	 *             the FileDocument
	 * @return the FileDocument
	 */
	public FileMetadata processUpdate(String projId, FileMetadata FileMetadata) {
		try {
			// TODO: FileDocument = fileStorageService.findAndModify(projId, FileDocument);
		} catch (Exception e) {
			throw new DmException(DmErrorCodeEnum.E500DOM003, new String[] { projId, e.getMessage() });
		}
		return FileMetadata;
	}


	/**
	 * Upload.
	 *
	 * @param projId
	 *             the proj id
	 * @param fileMetadata
	 *             the FileDocument
	 * @return the future
	 */
	private Future<FileMetadata> upload(String projId, FileMetadata fileMetadata) {
		try {
			if (!BaseUtil.isObjNull(fileMetadata.getId())) {
				if (BaseUtil.isStringNull(fileMetadata.getId())) {
					throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { fileMetadata.getId() });
				}
				String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(fileMetadata.getId());
				LOGGER.info(LOG_CACHE_KEY, cacheKey);
				RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
				LOGGER.info(LOG_CACHE_KEY, cache.getName());
				cache.evictByPrefix(cacheKey);
			}
			int version = 1;
			// Search last version
			if (fileMetadata.getId() != null && !fileMetadata.getId().isEmpty()) {
				FileMetadata docOld = fileStorageService.findByDocMgtId(fileMetadata.getId());
				if (!BaseUtil.isObjNull(docOld)) {
					LOGGER.info("OLD DOCUMENT: {}", docOld.getVersion());
					version = version + docOld.getVersion();
				}
			}
			fileMetadata.setProjId(projId);
			fileMetadata.setVersion(version);
			fileMetadata = fileStorageService.saveFile(fileMetadata);
			return new AsyncResult<>(fileMetadata);
		} catch (DmException de) {
			if (BaseUtil.isEqualsCaseIgnore(DmErrorCodeEnum.E500DOM004.name(), de.getInternalErrorCode())) {
				throw new DmException(DmErrorCodeEnum.E500DOM004, new String[] { fileMetadata.getId() });
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
	 * @return the FileDocument
	 */
	public FileMetadata processDownload(String id) {
		return processDownload(null, id);
	}


	/**
	 * Process download.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return the FileDocument
	 */
	public FileMetadata processDownload(String projId, String id) {
		FileMetadata FileMetadata = null;
		try {
			String cacheKey = DmCacheConstants.CACHE_KEY_DM_DOWNLOAD.concat(id);
			LOGGER.info(LOG_CACHE_KEY, cacheKey);
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			LOGGER.info(LOG_CACHE_KEY, cache.getName());
			Cache.ValueWrapper cv = cache.get(cacheKey);
			if (!BaseUtil.isObjNull(cv) && !BaseUtil.isObjNull(cv.get())) {
				LOGGER.info("Retrieve from Cache >> Doc Id: {}", id);

				if (cv.get() instanceof Documents) {
					FileMetadata = new DozerBeanMapper().map(cv.get(), FileMetadata.class);
				}

				if (cv.get() instanceof FileMetadata) {
					FileMetadata = (FileMetadata) cv.get();
				}

				if (FileMetadata != null) {
					LOGGER.info(
							"processDownload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}",
							projId, FileMetadata.getRefno(), FileMetadata.getDocid(), FileMetadata.getId(),
							FileMetadata.getFilesId());
				}
				return FileMetadata;
			}

			FileMetadata = download(projId, id).get();
			if (!BaseUtil.isObjNull(FileMetadata)) {
				LOGGER.info(
						"processDownload >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}",
						projId, FileMetadata.getRefno(), FileMetadata.getDocid(), FileMetadata.getId(),
						FileMetadata.getFilesId());
				cache.put(cacheKey, FileMetadata);
			}
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
			throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
		}
		return FileMetadata;
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
	private Future<FileMetadata> download(String projId, String id) {
		try {
			FileMetadata doc = fileStorageService.findByDocMgtId(id);
			if (BaseUtil.isObjNull(doc)) {
				LOGGER.info("Download: Bucket: {} - Doc Id: {}", projId, id);
				throw new DmException(DmErrorCodeEnum.E500DOM002, new String[] { projId, id });
			}
			byte[] content = fileStorageService.findDocumentById(doc.getFilesId());
			doc.setContent(content);
			LOGGER.info("Download >> Bucket: {} - DocRefNo: {} - DocId: {} - DocMgtId: {} - Files Id: {}", projId,
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
		return fileStorageService.delete(id);
	}


	/**
	 * Process search.
	 *
	 * @param id
	 *             the id
	 * @return the FileDocument
	 */
	public FileMetadata processSearch(String id) {
		return null; // TODO: fileStorageService.findByDocMgtId(null, id);
	}


	/**
	 * Process search.
	 *
	 * @param projId
	 *             the proj id
	 * @param id
	 *             the id
	 * @return the FileDocument
	 */
	public FileMetadata processSearch(String projId, String id) {
		return null; // TODO: fileStorageService.findByDocMgtId(projId, id);
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
	public List<FileMetadata> processSearch(String projId, String refno, String docid, String txnno, String id) {
		return null; //TODO: fileStorageService.findByRefNoAndDocId(projId, refno, docid, txnno, id);
	}

}