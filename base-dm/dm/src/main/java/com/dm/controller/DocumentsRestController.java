/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.controller;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dm.config.cache.RedisCacheWrapper;
import com.dm.constants.BeanConstants;
import com.dm.exception.DocumentsAlreadyExistsException;
import com.dm.model.Documents;
import com.dm.sdk.client.constants.DmCacheConstants;
import com.dm.sdk.client.constants.DocMgtConstants;
import com.dm.sdk.exception.DmException;
import com.dm.service.broker.DocumentsProcessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;


/**
 * The Class DocumentsRestController.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
@RequestMapping(DocMgtConstants.DOCUMENTS)
public class DocumentsRestController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsRestController.class);

	/** The document processor. */
	@Autowired
	@Qualifier(BeanConstants.DOCUMENT_PROCESSOR)
	DocumentsProcessor documentProcessor;

	/** The cache manager. */
	@Autowired
	protected CacheManager cacheManager;

	/** The redis template. */
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;


	/**
	 * Upload Documents.
	 *
	 * @param docs the docs
	 * @return the documents
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public Documents upload(@Valid @RequestBody com.dm.sdk.model.Documents docs) {
		Documents documents = convertToDto(docs);
		documents = documentProcessor.processUpload(documents);
		documents.setContent(null);
		return documents;
	}


	/**
	 * Upload Documents to specific bucket.
	 *
	 * @param projId             - Bucket Name
	 * @param docs the docs
	 * @return the documents
	 */
	@PostMapping(value = "/{projId}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Documents upload(@PathVariable(name = "projId") String projId,
			@Valid @RequestBody com.dm.sdk.model.Documents docs) {
		Documents documents = convertToDto(docs);
		documents = documentProcessor.processUpload(projId, documents);
		documents.setContent(null);
		return documents;
	}


	/**
	 * Download Document.
	 *
	 * @param id             - DocMgtId
	 * @return the documents
	 */
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public Documents download(@PathVariable(name = "id") String id, @RequestParam(value = "hasHistory", required = false) boolean hasHistory) {
		Documents result = documentProcessor.processDownload(id);
		if(!hasHistory && !BaseUtil.isObjNull(result)) {
			result.setHistory(null);
		}
		return result;
	}


	/**
	 * Download document from specific bucket.
	 *
	 * @param projId             - bucket name
	 * @param id             - DocMgtId
	 * @return the documents
	 * @throws DmException the dm exception
	 */
	@GetMapping(value = "/{projId}/{id}", produces = { MediaType.APPLICATION_JSON })
	public Documents download(@PathVariable(name = "projId") String projId, @PathVariable(name = "id") String id, @RequestParam(value = "hasHistory", required = false) boolean hasHistory) {
		Documents result =  documentProcessor.processDownload(projId, id);
		if(!hasHistory && !BaseUtil.isObjNull(result)) {
			result.setHistory(null);
		}
		return result;
	}


	/**
	 * Delete specific document.
	 *
	 * @param projId             - bucket name
	 * @param id             - DocMgtId
	 * @return true, if successful
	 */
	@DeleteMapping(value = "/{projId}/{id}", produces = { MediaType.APPLICATION_JSON })
	public boolean delete(@PathVariable(name = "projId") String projId, @PathVariable(name = "id") String id) {
		return documentProcessor.processDelete(projId, id);
	}


	/**
	 * Find specific document metadata.
	 *
	 * @param id             - DocMgtId
	 * @return the documents
	 */
	@GetMapping(value = "metadata/{id}", produces = { MediaType.APPLICATION_JSON })
	public Documents metadata(@PathVariable(name = "id") String id, @RequestParam(value = "hasHistory", required = false) boolean hasHistory) {
		Documents result = documentProcessor.processSearch(id);
		if(!hasHistory && !BaseUtil.isObjNull(result)) {
			result.setHistory(null);
		}
		return result;
	}


	/**
	 * Convert to dto.
	 *
	 * @param docs the docs
	 * @return the documents
	 */
	private Documents convertToDto(com.dm.sdk.model.Documents docs) {
		Documents documents = new Documents();
		if (docs != null) {
			BeanUtils.copyProperties(docs, documents);
			if (docs.getDocid() != null) {
				documents.setDocid(docs.getDocid().toString());
			}
			if (docs.getUploadDate() != null) {
				documents.setUploadDate(docs.getUploadDate());
			}
		}
		return documents;
	}


	/**
	 * Update.
	 *
	 * @param projId the proj id
	 * @param docs the docs
	 * @return the documents
	 */
	@PostMapping(value = "/metadata/update", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Documents update(@RequestParam(required = true) String projId,
			@Valid @RequestBody com.dm.sdk.model.Documents docs) {
		Documents documents = convertToDto(docs);
		documents = documentProcessor.processUpdate(projId, documents);
		documents.setContent(null);
		return documents;
	}


	/**
	 * Metadata.
	 *
	 * @param projId the proj id
	 * @param id the id
	 * @return the documents
	 */
	@GetMapping(value = "/{projId}/metadata/{id}", produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	public Documents metadata(@PathVariable(name = "projId") String projId, @PathVariable(name = "id") String id,
			@RequestParam(value = "hasHistory", required = false) boolean hasHistory) {
		Documents result = documentProcessor.processSearch(projId, id);
		if(!hasHistory && !BaseUtil.isObjNull(result)) {
			result.setHistory(null);
		}
		return result;
	}


	/**
	 * Search.
	 *
	 * @param projId the proj id
	 * @param params the params
	 * @return the list
	 */
	@GetMapping(value = "/{projId}/search", params = { "refno", "docid", "txnno", "id" }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Documents> search(@PathVariable(name = "projId") String projId, @RequestParam(value = "hasHistory", required = false) boolean hasHistory,
			@RequestParam Map<String, Object> params) {
		String jsonStr = "";
		Documents doc = null;
		try {
			jsonStr = JsonUtil.convertMapToJson(params);
			doc = JsonUtil.transferToObject(jsonStr, Documents.class);
		} catch (JsonGenerationException | JsonMappingException e) {
			LOGGER.error("{}", e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		if (doc == null) {
			return Collections.emptyList();
		}
		return documentProcessor.processSearch(projId, doc.getRefno(), doc.getDocid(), doc.getTxnno(), doc.getId());
	}


	/**
	 * Download content.
	 *
	 * @param docMgtId the doc mgt id
	 * @param projId the proj id
	 * @param request the request
	 * @param session the session
	 * @return the byte[]
	 */
	@GetMapping(value = DocMgtConstants.REST_SVC_DOWNLOAD_FILE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] downloadContent(@RequestParam(name = "docMgtId") String docMgtId,
			@RequestParam(name = "projId") String projId, HttpServletRequest request, HttpSession session) {
		byte[] fb = null;
		Documents docResp = null;

		if (!BaseUtil.isObjNull(docMgtId)) {
			try {
				LOGGER.debug("docMgmntId {} ", docMgtId);
				docResp = documentProcessor.processDownload(projId, docMgtId);
			} catch (IdmException e) {
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
			if (docResp != null) {
				if (BaseUtil.isObjNull(docResp.getFilename())) {
					docResp.setFilename(docMgtId);
				}
				LOGGER.debug("FILENAME: {}", docResp.getFilename());
				LOGGER.debug("CONTENT TYPE: {}", docResp.getContentType());
				LOGGER.debug("CONTENT: {}", docResp.getContent());
				try {
					fb = docResp.getContent();
				} catch (Exception e) {
					LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
					fb = docResp.getContent();
				}
				genFileName(docResp.getFilename(), docResp.getContentType());
				return fb;
			}
		}
		return ArrayUtils.EMPTY_BYTE_ARRAY;
	}


	/**
	 * Handle documents already exists exception.
	 *
	 * @param e the e
	 * @return the string
	 */
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public String handleDocumentsAlreadyExistsException(DocumentsAlreadyExistsException e) {
		return e.getMessage();
	}


	/**
	 * Refresh.
	 *
	 * @param prefixKey the prefix key
	 * @return true, if successful
	 */
	@GetMapping(value = "/cacheEvict", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		if (!BaseUtil.isObjNull(prefixKey)) {
			RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			cache.evictByPrefix(prefixKey);
		} else {
			Cache cache = cacheManager.getCache(DmCacheConstants.CACHE_BUCKET);
			try {
				cache.clear();
				return true;
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}
		return false;
	}


	/**
	 * Gen file name.
	 *
	 * @param fileNm the file nm
	 * @param contentType the content type
	 * @return the string
	 */
	private String genFileName(String fileNm, String contentType) {
		String filename = fileNm;
		if (BaseUtil.isEquals(contentType, "image/jpeg")) {
			filename += ".jpg";
		} else if (BaseUtil.isEquals(contentType, "image/gif")) {
			filename += ".gif";
		} else if (BaseUtil.isEquals(contentType, "image/png")) {
			filename += ".png";
		} else if (BaseUtil.isEquals(contentType, "application/pdf")) {
			filename += ".pdf";
		}
		return filename;
	}

}