/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.dm.config.cache.RedisCacheWrapper;
import com.baseboot.dm.exception.DocumentsAlreadyExistsException;
import com.baseboot.dm.model.Documents;
import com.baseboot.dm.sdk.client.constants.DmCacheConstants;
import com.baseboot.dm.sdk.client.constants.DocMgtConstants;
import com.baseboot.dm.sdk.exception.DmException;
import com.baseboot.dm.sdk.util.BaseUtil;
import com.baseboot.dm.svc.broker.DocumentsProcessor;
import com.baseboot.dm.util.BeanConstants;
import com.baseboot.dm.util.JsonUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@RestController
@RequestMapping(DocMgtConstants.DOCUMENTS)
public class DocumentsRestController implements DmCacheConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsRestController.class);

	@Autowired
	@Qualifier(BeanConstants.DOCUMENT_PROCESSOR)
	DocumentsProcessor documentProcessor;

	@Autowired
	protected CacheManager cacheManager;

	@Autowired
	protected RedisTemplate<String, String> redisTemplate;


	/**
	 * Upload Documents
	 * 
	 * @param documents
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Documents upload(@Valid @RequestBody Documents documents) {
		documents = documentProcessor.processUpload(documents);
		documents.setContent(null);
		return documents;
	}


	/**
	 * Upload Documents to specific bucket
	 * 
	 * @param projId
	 *             - Bucket Name
	 * @param documents
	 * @return
	 */
	@RequestMapping(value = "{projId}", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Documents upload(@PathVariable String projId, @Valid @RequestBody Documents documents) {
		documents = documentProcessor.processUpload(projId, documents);
		documents.setContent(null);
		return documents;
	}


	/**
	 * Download Document
	 * 
	 * @param id
	 *             - DocMgtId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	public Documents download(@PathVariable String id) throws DmException {
		return documentProcessor.processDownload(id);
	}


	/**
	 * Download document from specific bucket
	 * 
	 * @param projId - bucket name
	 * @param id - DocMgtId
	 * @return
	 * @throws DmException
	 */
	@RequestMapping(value = "/{projId}/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	public Documents download(@PathVariable String projId, @PathVariable String id) throws DmException {
		return documentProcessor.processDownload(projId, id);
	}


	/**
	 * Delete specific document
	 * 
	 * @param projId
	 *             - bucket name
	 * @param id
	 *             - DocMgtId
	 * @return
	 */
	@RequestMapping(value = "/{projId}/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	public boolean delete(@PathVariable String projId, @PathVariable String id) {
		return documentProcessor.processDelete(projId, id);
	}


	/**
	 * Find specific document metadata
	 * 
	 * @param id
	 *             - DocMgtId
	 * @return
	 */
	@RequestMapping(value = "metadata/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	public Documents metadata(@PathVariable String id) {
		return documentProcessor.processSearch(id);
	}
	
	@RequestMapping(value = "/metadata/update", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Documents update(@RequestParam(required=true) String projId, @Valid @RequestBody Documents documents) {
		documents = documentProcessor.processUpdate(projId, documents);
		documents.setContent(null);
		return documents;
	}


	@RequestMapping(value = "/{projId}/metadata/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Documents metadata(@PathVariable String projId, @PathVariable String id) {
		return documentProcessor.processSearch(projId, id);
	}


	@RequestMapping(value = "/{projId}/search", params = { "refno", "docid", "txnno",
			"id" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Documents> search(@PathVariable String projId, @RequestParam Map<String, Object> params) {
		String jsonStr = "";
		Documents doc = null;
		try {
			jsonStr = JsonUtil.convertMapToJson(params);
			doc = JsonUtil.transferToObject(jsonStr, Documents.class);
		} catch (JsonGenerationException e) {
			LOGGER.error(e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return documentProcessor.processSearch(projId, doc.getRefno(), doc.getDocid(), doc.getTxnno());
	}


	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public String handleDocumentsAlreadyExistsException(DocumentsAlreadyExistsException e) {
		return e.getMessage();
	}


	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public boolean refresh(@RequestParam(value = "prefixKey", required = false) String prefixKey) {
		if (!BaseUtil.isObjNull(prefixKey)) {
			RedisCacheWrapper cache = (RedisCacheWrapper) cacheManager.getCache(CACHE_BUCKET);
			cache.evictByPrefix(prefixKey);
		} else {
			Cache cache = cacheManager.getCache(CACHE_BUCKET);
			try {
				cache.clear();
				return true;
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}
		return false;
	}

}