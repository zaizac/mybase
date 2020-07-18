/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.ProjectEnum;
import com.baseboot.web.core.AbstractController;
import com.baseboot.web.helper.WebHelper;
import com.baseboot.web.util.WebUtil;
import com.dm.sdk.model.Documents;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Controller
@RequestMapping(PageConstants.DOCUMENTS)
public class DocumentController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);


	@RequestMapping(value = "/download/{docId}", method = RequestMethod.GET)
	public @ResponseBody Documents download(@PathVariable String docId, HttpServletRequest request,
			HttpSession session) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug("Download: {}", docId);
				docResp = getDocInCache(ProjectEnum.JLS, docId);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error("IdmException: {}", e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}
		return docResp;
	}


	@RequestMapping(value = "/download/content/{docId}", method = RequestMethod.GET, produces = { "image/jpeg",
			"image/gif", "image/png", "application/pdf" })
	public @ResponseBody ResponseEntity<byte[]> downloadContent(@PathVariable String docId, HttpServletRequest request,
			HttpSession session) {
		byte[] fb = null;
		Documents docResp = null;
		
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug("Download: {}", docId);
				docResp = getDocInCache(ProjectEnum.JLS, docId);
			} catch (IdmException e) {				
				LOGGER.error("IdmException: {}", e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
			if (!BaseUtil.isObjNull(docResp)) {
				if (BaseUtil.isObjNull(docResp.getFilename())) {
					docResp.setFilename(docId);
				}
				LOGGER.debug("FILENAME: {}", docResp.getFilename());
				LOGGER.debug("CONTENT TYPE: {}", docResp.getContentType());
				LOGGER.debug("CONTENT: {}", docResp.getContent());
				try {
					fb = docResp.getContent();
				} catch (Exception e) {
					LOGGER.error("Exception: {}", e.getMessage());
					fb = docResp.getContent();
				}
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.parseMediaType(docResp.getContentType()));
				String filename = genFileName(docId, docResp.getContentType());
				headers.setContentDispositionFormData(filename, filename);
				headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fb, headers, HttpStatus.OK);
				return response;
			} else {
				try {
					String url = new WebHelper().baseUrl() + "/webjars/joblottery-webjar/images/no_image.jpg";
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
				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fb, headers, HttpStatus.OK);
				return response;
			}
		}
		return null;
	}


	@RequestMapping(value = "/{projId}/download/{docId}", method = RequestMethod.GET)
	public @ResponseBody Documents download(@PathVariable String projId, @PathVariable String docId,
			HttpServletRequest request, HttpSession session) {
		Documents docResp = null;
		if (!BaseUtil.isObjNull(docId)) {
			try {
				LOGGER.debug("Download: {}" + docId);
				docResp = getDocInCache(ProjectEnum.JLS, docId);
			} catch (IdmException e) {
				if (WebUtil.checkTokenError(e)) {
					throw e;
				}
				LOGGER.error("IdmException: {}", e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Exception: {}", e.getMessage());
			}
		}
		return docResp;
	}


	@RequestMapping(value = "/upload/{projId}", method = RequestMethod.POST)
	public @ResponseBody Documents upload(@PathVariable String projId, Documents fileUpd, HttpServletRequest request,
			HttpSession session) {
		LOGGER.info("Document Upload: " + objectMapper.valueToTree(fileUpd));
		Documents doc = null;
		try {
			if (!BaseUtil.isObjNull(fileUpd) && !BaseUtil.isObjNull(fileUpd.getContent())) {
				fileUpd.setContent(Base64.decodeBase64(fileUpd.getContent()));
			}
			doc = getDmService(ProjectEnum.findByName(projId)).upload(fileUpd);
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.getMessage());
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

}