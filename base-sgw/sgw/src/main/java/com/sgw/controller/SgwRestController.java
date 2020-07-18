/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.sgw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dm.sdk.model.Documents;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.Fcm;
import com.idm.sdk.model.FcmDevice;
import com.notify.sdk.model.Notification;
import com.sgw.constants.SgwTxnCodeConstants;
import com.sgw.sdk.client.constants.SgwErrorCodeEnum;
import com.sgw.sdk.client.constants.SgwUriConstants;
import com.sgw.sdk.model.MessageResponse;
import com.sgw.sdk.model.MobileNotify;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.constants.BaseConstants;

/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@RestController
public class SgwRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SgwRestController.class);
	
	@PostMapping(value = SgwUriConstants.GET_MOBILE_NOTIFY, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Object postListNotification(
			@RequestParam(defaultValue = SgwTxnCodeConstants.SGW_MOBILE_NOTIFY) String trxnNo,
			@Valid @RequestBody MobileNotify mobileRequest, HttpServletRequest request) {
		MessageResponse mobileResponse = new MessageResponse();
		mobileResponse.setLocale(mobileRequest.getLocale());
		List<Notification> listNotification = new ArrayList<>();
		List<Map<String, Object>> listnotificationMap = new ArrayList<Map<String, Object>>();

		if (BaseUtil.isObjNull(mobileRequest)) {
			return invalidResponse(SgwUriConstants.GET_MOBILE_NOTIFY);
		}

		try {
			String FCM = "fcm";
			String notifyTo = mobileRequest.getNotifyTo();
			Fcm fcm = new Fcm();

			if (BaseUtil.isObjNull(notifyTo)) {
				if (!BaseUtil.isObjNull(mobileRequest.getDevice().getMachineId())) {
					FcmDevice fcmDevice = new FcmDevice();
					fcmDevice.setMachineId(mobileRequest.getDevice().getMachineId());
					List<FcmDevice> listfcmDevice = getIdmService(request).searchFcmDevice(fcmDevice);
					if (!BaseUtil.isObjNull(listfcmDevice)) {
						for (FcmDevice devicex : listfcmDevice) {
							if (devicex.getStatus() == Boolean.TRUE) {
								fcm = devicex.getFcm();
								notifyTo = fcm.getUserId();
							}
						}
					}
				}
			}

			if (!BaseUtil.isObjNull(notifyTo)) {
				Notification notification = new Notification();
				notification.setNotifyTo(mobileRequest.getNotifyTo());
				notification.setNotifyType(FCM);
				listNotification = getNotifyService(request).searchNotPayload(notification);

				if (!BaseUtil.isObjNull(listNotification)) {
					for (Notification notx : listNotification) {
						Map<String, Object> notificationMap = new HashMap<String, Object>();

						ObjectMapper mapper = new ObjectMapper();
						Map<String, Object> result = mapper.readValue(notx.getContent(),
								new TypeReference<Map<String, Object>>() {
								});
						Map<String, Object> not = mapper.convertValue(result.get("notification"),
								new TypeReference<Map<String, Object>>() {
								});

						notificationMap.put("subject", not.get("title"));
						notificationMap.put("details", not.get("body"));
						notificationMap.put("createDt",
								DateUtil.convertDate(notx.getCreateDt(), BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_S));
						notificationMap.put("eventId", not.get("eventId"));
						listnotificationMap.add(notificationMap);
					}

					mobileResponse.setResult(listnotificationMap);
					mobileResponse.setCode(SgwErrorCodeEnum.E200SGW000.name());
					mobileResponse.setPath(SgwUriConstants.GET_MOBILE_NOTIFY);
					mobileResponse.setTimestamp(getSQLTimestamp());

				} else {
					mobileResponse.setCode(SgwErrorCodeEnum.E404SGW001.name());
					mobileResponse.setPath(SgwUriConstants.GET_MOBILE_NOTIFY);
					mobileResponse.setTimestamp(getSQLTimestamp());
				}

			} else {
				return invalidResponse(SgwUriConstants.GET_MOBILE_NOTIFY);
			}

		} catch (Exception e) {
			mobileResponse.setCode(SgwErrorCodeEnum.E500SGW004.name());
			mobileResponse.setPath(SgwUriConstants.GET_MOBILE_NOTIFY);
			mobileResponse.setTimestamp(getSQLTimestamp());
		}

		return mobileResponse;

	}

	@GetMapping(value = "/download/content/{projId}/{docMgtId}", 
			produces = { "image/jpeg", "image/gif", "image/png", "application/pdf" })
	public @ResponseBody ResponseEntity<byte[]> downloadContent(
			@RequestParam(defaultValue = SgwTxnCodeConstants.SGW_DOCUMENT_DOWNLOAD) String trxnNo,
			@PathVariable String projId,
			@PathVariable String docMgtId, HttpServletRequest request) {
		byte[] fb = null;
		Documents docResp = null;

		if (!BaseUtil.isObjNull(docMgtId)) {
			try {
				LOGGER.debug("{}", docMgtId);
				docResp = getDmService(request, projId).download(docMgtId);
				HttpHeaders headers = new HttpHeaders();
				String filename = null;

				if (docResp != null) {

					if (BaseUtil.isObjNull(docResp.getFilename())) {
						docResp.setFilename(docMgtId);
					}

					LOGGER.debug("FILENAME: {}", docResp.getFilename());
					LOGGER.debug("CONTENT TYPE: {}", docResp.getContentType());
					LOGGER.debug("CONTENT: {}", docResp.getContent());

					fb = docResp.getContent();
					headers.setContentType(MediaType.parseMediaType(docResp.getContentType()));
					headers.setContentLength(docResp.getLength());
					filename = genFileName(docMgtId, docResp.getContentType());
				}

				headers.setContentDispositionFormData(filename, filename);
				headers.setCacheControl("max-age=3600"); //"must-revalidate, post-check=0, pre-check=0");
				return new ResponseEntity<byte[]>(fb, headers, HttpStatus.OK);

			} catch (IdmException e) {
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
				throw e;
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				throw e;
			}
		}
		return null;
	}
	
	
}