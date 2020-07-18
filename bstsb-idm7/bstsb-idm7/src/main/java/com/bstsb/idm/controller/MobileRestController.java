/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmFcm;
import com.bstsb.idm.model.IdmFcmDevice;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.constants.IdmErrorCodeEnum;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.exception.IdmException;
import com.bstsb.idm.sdk.model.MobileAccessToken;
import com.bstsb.idm.service.IdmFcmDeviceService;
import com.bstsb.idm.service.IdmFcmService;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConstants;
import com.bstsb.util.model.Device;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.MOBILE)
public class MobileRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MobileRestController.class);

	@Autowired
	IdmFcmService idmFcmSvc;

	@Autowired
	IdmFcmDeviceService idmFcmDeviceSvc;

	@Autowired
	IdmProfileService idmProfileSvc;


	@PostMapping(value = IdmUrlConstants.MOBILE_ACCESS_TOKEN, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmFcmDevice accessToken(@RequestBody MobileAccessToken accessToken, HttpServletRequest request) {
		LOGGER.info("Mobile Access Token");
		if (BaseUtil.isObjNull(accessToken) || BaseUtil.isObjNull(accessToken.getUserId())
				|| BaseUtil.isObjNull(accessToken.getAccessToken())
				|| BaseUtil.isObjNull(accessToken.getDeviceInfo())) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		String userId = accessToken.getUserId();
		IdmProfile idmProf = idmProfileSvc.findProfileByUserId(userId);

		if (BaseUtil.isObjNull(idmProf)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128);
		}

		IdmFcm idmFcm = idmFcmSvc.findByUserId(userId);

		String currUserId = getCurrUserId(request);

		// If does not exists, create FCM
		if (BaseUtil.isObjNull(idmFcm)) {
			idmFcm = new IdmFcm();
			idmFcm.setUserId(userId);
			idmFcm.setStatus(BaseConstants.USER_ACTIVE);
			idmFcm.setCreateId(currUserId);
			idmFcm.setUpdateId(currUserId);
			idmFcm = idmFcmSvc.create(idmFcm);
		}

		Device deviceInfo = accessToken.getDeviceInfo();

		IdmFcmDevice idmFcmDevice = idmFcmDeviceSvc.findByFcmIdAndMachineId(idmFcm.getId(),
				deviceInfo.getMachineId());

		if (!BaseUtil.isObjNull(idmFcmDevice)) {
			idmFcmDevice.setAccessToken(accessToken.getAccessToken());
		} else {
			idmFcmDevice = dozerMapper.map(deviceInfo, IdmFcmDevice.class);
			idmFcmDevice.setAccessToken(accessToken.getAccessToken());
			idmFcmDevice.setFcmId(idmFcm.getId());
			idmFcmDevice.setCreateId(currUserId);
		}

		idmFcmDevice.setUpdateId(currUserId);

		idmFcmDevice = idmFcmDeviceSvc.update(idmFcmDevice);
		return idmFcmDevice;
	}

}
