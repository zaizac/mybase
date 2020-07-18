/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.exception.BeException;
import com.be.sdk.model.Document;
import com.bff.cmn.form.CustomMultipartFile;
import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.bff.util.constants.ProjectEnum;
import com.bff.util.helper.WebHelper;
import com.dm.sdk.model.Documents;
import com.google.gson.GsonBuilder;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PageConstants.DOCUMENTS)
public class DocumentController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

	private static final String LOG_DOWNLOAD = "Download: {}";


	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Document> findAllDocuments() {
		return staticData.findAllDocuments();
	}


	@GetMapping(value = "/download/{docId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Documents download(@PathVariable String docId, HttpServletRequest request) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(LOG_DOWNLOAD, docId);
				docResp = getDocInCache(ProjectEnum.URP.getName(), docId, request);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
				throw e;
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				throw e;
			}
		}
		return docResp;
	}


	@GetMapping(value = "/download/content/{projId}/{docId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			"image/jpeg", "image/gif", "image/png", "application/pdf" })
	public @ResponseBody ResponseEntity<byte[]> downloadContent(@PathVariable String projId,
			@PathVariable String docId, HttpServletRequest request) {
		byte[] fb = null;
		Documents docResp = null;

		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(LOG_DOWNLOAD, docId);
				docResp = getDocInCache(projId, docId, request);

				HttpHeaders headers = new HttpHeaders();
				String filename = null;

				if (docResp != null) {

					if (BaseUtil.isObjNull(docResp.getFilename())) {
						docResp.setFilename(docId);
					}

					LOGGER.debug("FILENAME: {}", docResp.getFilename());
					LOGGER.debug("CONTENT TYPE: {}", docResp.getContentType());
					LOGGER.debug("CONTENT: {}", docResp.getContent());

					fb = docResp.getContent();
					headers.setContentType(MediaType.parseMediaType(docResp.getContentType()));
					filename = genFileName(docId, docResp.getContentType());

				} else {
					String url = new WebHelper().baseUrl() + "/webjars/urp-ui-webjar/images/no_image.jpg";
					LOGGER.info("NO IMAGE URL: {}", url);

					fb = Base64.encodeBase64(IOUtils.toByteArray((new URL(url)).openStream()), true);
					headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE));
					filename = genFileName(docId, MediaType.IMAGE_JPEG_VALUE);
				}

				headers.setContentDispositionFormData(filename, filename);
				headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				return new ResponseEntity(fb, headers, HttpStatus.OK);

			} catch (IdmException e) {
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
				throw e;
			} catch (MalformedURLException e) {
				LOGGER.error("MalformedURLException: {}", e);
			} catch (IOException e) {
				LOGGER.error("IOException: {}", e);
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				throw e;
			}
		}
		return null;
	}


	@GetMapping(value = "/download/{projId}/{docId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Documents download(@PathVariable String projId, @PathVariable String docId,
			HttpServletRequest request) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(projId, docId);
				docResp = getDocInCache(projId, docId, request);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
				throw e;
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				throw e;
			}
		}
		return docResp;
	}


	@PostMapping(value = "/upload/{projId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Documents upload(@PathVariable String projId, @RequestBody Documents fileUpd,
			HttpServletRequest request) {
		LOGGER.info("Document Upload: {}", objectMapper.valueToTree(fileUpd));
		Documents doc = null;
		try {
			if (!BaseUtil.isObjNull(fileUpd) && !BaseUtil.isObjNull(fileUpd.getContent())) {
				fileUpd.setContent(Base64.decodeBase64(fileUpd.getContent()));
			}
			doc = getDmService(request, projId).upload(fileUpd);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			throw e;
		}
		return doc;
	}


	@PostMapping(value = "/upload/custommultipart/{projId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Documents upload(@PathVariable String projId, @RequestBody CustomMultipartFile file,
			HttpServletRequest request) {
		LOGGER.info("Document Upload: {}", objectMapper.valueToTree(file));
		Documents newDoc = null;
		Documents doc = null;
		try {
			doc = new Documents();
			doc.setContentType(file.getContentType());
			doc.setFilename(file.getFilename());
			doc.setDocid(file.getDocId());
			doc.setId(file.getDocMgtId());
			doc.setLength(file.getSize());
			doc.setRefno(file.getRefNo());
			doc.setContent(file.getContent());
			newDoc = getDmService(request, projId).upload(doc);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			throw e;
		}
		return newDoc;
	}


	// Static Document services
	@PostMapping(value = "/findBySearchCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Document> findBySearchCriteria(@RequestBody Document docs) {
		if (BaseUtil.isObjNull(docs)) {
			throw new BeException("Invalid document trxn no");
		}
		return getBeService().reference().findDocumentsByCriteria(docs);
	}


	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Document createReason(@RequestBody Document document, HttpServletRequest request) {
		if (BaseUtil.isObjNull(document)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Create Reason : " + new GsonBuilder().create().toJson(document));
		return staticData.addDocument(document);
	}


	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Document updateStatus(@RequestBody Document document, HttpServletRequest request) {
		if (BaseUtil.isObjNull(document)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Update Reason : " + new GsonBuilder().create().toJson(document));
		return staticData.updateDocument(document);
	}


	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Document> searchStatus(@RequestBody Document document, HttpServletRequest request) {
		if (BaseUtil.isObjNull(document)) {
			throw new BeException("Invalid config - Response Body is empty.");
		}
		LOGGER.debug("Search Reason : " + new GsonBuilder().create().toJson(document));
		return getBeService().reference().findDocumentsByCriteria(document);
	}


	@GetMapping(value = "/findByDocBucket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Document> searchByDocumentBucket(@RequestParam(value = "dmBucket") String dmBucket,
			HttpServletRequest request) {
		if (BaseUtil.isStringNull(dmBucket)) {
			throw new BeException("Invalid portal type - Document Bucket is empty.");
		}
		return staticData.findByDocumentBucket(dmBucket);
	}


	@GetMapping(value = "/findByDocTrxnNo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Document> searchByDocumentTrxnNo(@RequestParam(value = "docTrxnNo") String docTrxnNo,
			HttpServletRequest request) {
		LOGGER.info("Reference .... {}", docTrxnNo);
		if (BaseUtil.isStringNull(docTrxnNo)) {
			throw new BeException("Invalid portal type - Document TrxnNo is empty.");
		}
		return staticData.findDocumentsByTrxnNo(docTrxnNo);
	}


	@GetMapping(value = "/findByDocDesc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Document> searchByDocumentDesc(@RequestParam(value = "docDesc") String docDesc,
			HttpServletRequest request) {
		LOGGER.info("Reference .... {}", docDesc);
		if (BaseUtil.isStringNull(docDesc)) {
			throw new BeException("Invalid portal type - Document DocDesc is empty.");
		}
		return staticData.findByDocumentDesc(docDesc);
	}


	@GetMapping(value = "/findByDocType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Document> searchByDocumentType(@RequestParam(value = "type") String type,
			HttpServletRequest request) {
		LOGGER.info("Reference .... {}", type);
		if (BaseUtil.isStringNull(type)) {
			throw new BeException("Invalid portal type - Document type is empty.");
		}
		return staticData.findByDocumentType(type);
	}


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