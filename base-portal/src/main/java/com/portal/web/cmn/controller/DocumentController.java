/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.cmn.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.sdk.model.Documents;
import com.idm.sdk.exception.IdmException;
import com.portal.config.WebMvcConfig;
import com.portal.constants.PageConstants;
import com.portal.constants.ProjectEnum;
import com.portal.core.AbstractController;
import com.portal.web.util.WebUtil;
import com.portal.web.util.helper.WebHelper;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Controller
@RequestMapping(PageConstants.DOCUMENTS)
public class DocumentController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

	private static final String LOG_DOWNLOAD = "Download: {}";


	@GetMapping(value = "/download/{docId}")
	public @ResponseBody Documents download(@PathVariable String docId, HttpServletRequest request,
			HttpSession session) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(LOG_DOWNLOAD, docId);
				docResp = getDocInCache(ProjectEnum.OUTBREAK, docId);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
		}
		return docResp;
	}


	@GetMapping(value = "/download/content/{docId}", produces = { "image/jpeg", "image/gif", "image/png",
			"application/pdf" })
	public @ResponseBody ResponseEntity<byte[]> downloadContent(@PathVariable String docId, HttpServletRequest request,
			HttpSession session) {
		Documents docResp = null;

		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(LOG_DOWNLOAD, docId);
				docResp = getDocInCache(ProjectEnum.OUTBREAK, docId);
			} catch (IdmException e) {
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
			if (docResp != null) {
				return docRespNotNullResp(docResp, docId);
			} else {
				return docRespNullResp(docId);
			}
		}
		return null;
	}


	private ResponseEntity<byte[]> docRespNotNullResp(Documents docResp, String docId) {
		byte[] fb = null;
		if (BaseUtil.isObjNull(docResp.getFilename())) {
			docResp.setFilename(docId);
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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(docResp.getContentType()));
		String filename = genFileName(docId, docResp.getContentType());
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(fb, headers, HttpStatus.OK);
	}


	@PostMapping(value = "/upload/{projId}")
	public @ResponseBody Documents upload(@PathVariable String projId, Documents fileUpd, HttpServletRequest request,
			HttpSession session) {
		LOGGER.info("Document Upload: {}", objectMapper.valueToTree(fileUpd));
		Documents doc = null;
		try {
			if (!BaseUtil.isObjNull(fileUpd) && !BaseUtil.isObjNull(fileUpd.getContent())) {
				fileUpd.setContent(Base64.decodeBase64(fileUpd.getContent()));
			}
			doc = getDmService(ProjectEnum.findByName(projId)).upload(fileUpd);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return doc;
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


	private ResponseEntity<byte[]> docRespNullResp(String docId) {
		byte[] fb = null;
		try {
			String url = new WebHelper().baseUrl() + "/webjars/monster-ui/images/no_image.jpg";
			LOGGER.info("NO IMAGE URL: {}", url);
			fb = Base64.encodeBase64(IOUtils.toByteArray((new URL(url)).openStream()), true);
		} catch (MalformedURLException e) {
			LOGGER.error("MalformedURLException: {}", e);
		} catch (IOException e) {
			LOGGER.error("IOException: {}", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE));
		String filename = genFileName(docId, MediaType.IMAGE_JPEG_VALUE);
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(fb, headers, HttpStatus.OK);
	}


	@GetMapping(value = "/{projId}/download/{docId}")
	public @ResponseBody Documents download(@PathVariable String projId, @PathVariable String docId,
			HttpServletRequest request, HttpSession session) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug(LOG_DOWNLOAD, docId);
				docResp = getDocInCache(ProjectEnum.OUTBREAK, docId);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
		}
		return docResp;
	}
	
	@GetMapping(value = "/download/form/{formTemplate}", produces = { "image/jpeg", "image/gif", "image/png",
	"application/pdf" })
	public @ResponseBody ResponseEntity<byte[]> downloadTemplate(@PathVariable String formTemplate, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fileName = staticData.beConfig(formTemplate);
		String fullPath = WebMvcConfig.TEMPLATE_PATH.concat(fileName);
		LOGGER.info("Download form Template Path: {}", fullPath);

		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		byte[] fb = Files.readAllBytes(Paths.get(fullPath));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(mimeType));
		String filename = genFileName(fileName, mimeType);
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(fb, headers, HttpStatus.OK);
	}

}