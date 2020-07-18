package com.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idm.core.AbstractRestController;
import com.idm.model.IdmFcm;
import com.idm.model.IdmFcmDevice;
import com.idm.model.IdmProfile;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.Fcm;
import com.idm.sdk.model.FcmDevice;
import com.idm.sdk.model.MobileAccessToken;
import com.idm.service.IdmFcmDeviceService;
import com.idm.service.IdmFcmService;
import com.idm.service.IdmProfileService;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;
import com.util.model.Device;


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
	public IdmFcmDevice accessToken(@RequestBody MobileAccessToken accessToken, HttpServletRequest request)
			throws IOException {
		LOGGER.info("Mobile Access Token");
		if (BaseUtil.isObjNull(accessToken) || BaseUtil.isObjNull(accessToken.getUserId())
				|| BaseUtil.isObjNull(accessToken.getAccessToken())
				|| BaseUtil.isObjNull(accessToken.getDeviceInfo())) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		String userId = accessToken.getUserId();
		IdmProfile idmProf = idmProfileSvc.findByUserId(userId);

		if (BaseUtil.isObjNull(idmProf)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM128);
		}

		IdmFcm idmFcm = idmFcmSvc.findByUserId(userId);

		String currUserId = getCurrUserId(request);

		// If does not exists, create FCM
		if (BaseUtil.isObjNull(idmFcm)) {
			idmFcm = new IdmFcm();
			idmFcm.setUserId(userId);
			idmFcm.setStatus(true);
			idmFcm.setCreateId(currUserId);
			idmFcm.setUpdateId(currUserId);
			idmFcm = idmFcmSvc.create(idmFcm);
		}

		Device deviceInfo = accessToken.getDeviceInfo();

		IdmFcmDevice idmFcmDevice = idmFcmDeviceSvc.findByFcmIdAndMachineId(idmFcm.getFcmId(),
				deviceInfo.getMachineId());

		if (!BaseUtil.isObjNull(idmFcmDevice)) {
			idmFcmDevice.setFcmToken(accessToken.getAccessToken());
		} else {
			idmFcmDevice = JsonUtil.transferToObject(deviceInfo, IdmFcmDevice.class);
			idmFcmDevice.setFcmToken(accessToken.getAccessToken());
			idmFcmDevice.setFcm(new IdmFcm(idmFcm.getFcmId()));
			idmFcmDevice.setCreateId(currUserId);
		}

		idmFcmDevice.setUpdateId(currUserId);

		idmFcmDevice = idmFcmDeviceSvc.update(idmFcmDevice);
		return idmFcmDevice;
	}


	// These are IdmFcm methods -----------
	@GetMapping(value = IdmUrlConstants.FCM_PROFILE_FINDALL, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Fcm> getFcmAll() throws IOException {

		List<Fcm> resList = new ArrayList<>();
		List<IdmFcm> fcmList = idmFcmSvc.findAll();
		if (Boolean.FALSE.equals(BaseUtil.isListNull(fcmList))) {
			for (IdmFcm fc : fcmList) {
				Fcm fcmPro = JsonUtil.transferToObject(fc, Fcm.class);
				resList.add(fcmPro);
			}
		}
		return resList;
	}
	
	
	@GetMapping(value = IdmUrlConstants.FCM_PROFILE_FINDBY_FCMID, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Fcm getFcmByFcmId(@RequestParam(value = "fcmId", required = true) Integer fcmId) throws IOException {
		IdmFcm fcm = idmFcmSvc.findByFcmId(fcmId);
		if (fcm != null ) {
			return JsonUtil.transferToObject(fcm, Fcm.class);
		}
		return null;
	}

	
	@GetMapping(value = IdmUrlConstants.FCM_PROFILE_FINDBY_USERID, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Fcm getFcmByUserId(@RequestParam(value = "userId", required = true) String userId) throws IOException {

		IdmFcm fcm = idmFcmSvc.findByUserId(userId);
		if (fcm != null ) {
			return JsonUtil.transferToObject(fcm, Fcm.class);
		}
		return null;
	}


	@PostMapping(value = IdmUrlConstants.FCM_PROFILE_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Fcm createFcm(@Valid @RequestBody Fcm profile, HttpServletRequest request) throws IOException {
		IdmFcm fcm = null;
		if (!BaseUtil.isObjNull(profile)) {
			LOGGER.debug("Create Fcm Profile. {}", profile);
			fcm = JsonUtil.transferToObject(profile, IdmFcm.class);
			fcm.setCreateId(getCurrUserId(request));
			fcm.setCreateDt(DateUtil.getSQLTimestamp());
			fcm.setUpdateId(getCurrUserId(request));
			fcm.setUpdateDt(DateUtil.getSQLTimestamp());
			fcm = idmFcmSvc.create(fcm);
		}
		return JsonUtil.transferToObject(fcm, Fcm.class);
	}


	@PostMapping(value = IdmUrlConstants.FCM_PROFILE_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public Fcm updateFcm(@Valid @RequestBody Fcm profile, HttpServletRequest request) throws IOException {
		IdmFcm fcm = null;
		if (!BaseUtil.isObjNull(profile) && !BaseUtil.isObjNull(profile.getFcmId())) {
			LOGGER.debug("Update Fcm Profile. {}", profile);
			fcm = JsonUtil.transferToObject(profile, IdmFcm.class);
			fcm.setUpdateId(getCurrUserId(request));
			fcm.setUpdateDt(DateUtil.getSQLTimestamp());
			fcm = idmFcmSvc.update(fcm);
		}
		return JsonUtil.transferToObject(fcm, Fcm.class);
	}
	

	// These are IdmFcmDevice methods -----------
	@GetMapping(value = IdmUrlConstants.FCM_DEVICE_FINDALL, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<FcmDevice> getFcmDeviceAll() throws IOException {

		List<FcmDevice> resList = new ArrayList<>();
		List<IdmFcmDevice> fcmDeviceList = idmFcmDeviceSvc.findAll();
		if (Boolean.FALSE.equals(BaseUtil.isListNull(fcmDeviceList))) {
			for (IdmFcmDevice fc : fcmDeviceList) {
				FcmDevice fcmDevice = JsonUtil.transferToObject(fc, FcmDevice.class);
				resList.add(fcmDevice);
			}
		}
		return resList;
	}

	
	@GetMapping(value = IdmUrlConstants.FCM_DEVICE_FINDBY_FCMID, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<FcmDevice> getFcmDeviceByFcmId(@RequestParam(value = "fcmId", required = true) Integer fcmId) throws IOException {

		List<FcmDevice> resList = new ArrayList<>();
		List<IdmFcmDevice> fcmDeviceList = idmFcmDeviceSvc.findByFcmId(fcmId);
		if (Boolean.FALSE.equals(BaseUtil.isListNull(fcmDeviceList))) {
			for (IdmFcmDevice fc : fcmDeviceList) {
				FcmDevice fcmDevice = JsonUtil.transferToObject(fc, FcmDevice.class);
				resList.add(fcmDevice);
			}
		}
		return resList;
	}
	
	
	@GetMapping(value = IdmUrlConstants.FCM_DEVICE_FINDBY_MACHINEID, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public FcmDevice getFcmDeviceByMachineId(@RequestParam(value = "machineId", required = true) String machineId) throws IOException {

		IdmFcmDevice fcm = idmFcmDeviceSvc.findByMachineId(machineId);
		if (fcm != null ) {
			return JsonUtil.transferToObject(fcm, FcmDevice.class);
		}
		return null;
	}


	@PostMapping(value = IdmUrlConstants.FCM_DEVICE_CREATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public FcmDevice createFcmDevice(@Valid @RequestBody FcmDevice device, HttpServletRequest request) throws IOException {
		IdmFcmDevice fcmDevice = null;
		if (!BaseUtil.isObjNull(device)) {
			LOGGER.debug("Create Fcm Device. {}", device);
			fcmDevice = JsonUtil.transferToObject(device, IdmFcmDevice.class);
			fcmDevice.setCreateId(getCurrUserId(request));
			fcmDevice.setCreateDt(DateUtil.getSQLTimestamp());
			fcmDevice.setUpdateId(getCurrUserId(request));
			fcmDevice.setUpdateDt(DateUtil.getSQLTimestamp());
			fcmDevice = idmFcmDeviceSvc.create(fcmDevice);
		}
		return JsonUtil.transferToObject(fcmDevice, FcmDevice.class);
	}


	@PostMapping(value = IdmUrlConstants.FCM_DEVICE_UPDATE, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public FcmDevice updateFcmDevice(@Valid @RequestBody FcmDevice device, HttpServletRequest request) throws IOException {
		IdmFcmDevice fcmDevice = null;
		if (!BaseUtil.isObjNull(device) && !BaseUtil.isObjNull(device.getFcmId())) {
			LOGGER.debug("Update Fcm Device. {}", device);
			fcmDevice = JsonUtil.transferToObject(device, IdmFcmDevice.class);
			fcmDevice.setUpdateId(getCurrUserId(request));
			fcmDevice.setUpdateDt(DateUtil.getSQLTimestamp());
			fcmDevice = idmFcmDeviceSvc.update(fcmDevice);
		}
		return JsonUtil.transferToObject(fcmDevice, FcmDevice.class);
	}	
	
	@PostMapping(value = IdmUrlConstants.FCM_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<Fcm> search(@RequestBody IdmFcm idmFcm) throws IOException {
		List<Fcm> resList = new ArrayList<>();
		List<IdmFcm> fcmList = idmFcmSvc.search(idmFcm);
		if (Boolean.FALSE.equals(BaseUtil.isListNullZero(fcmList))) {
			resList = JsonUtil.transferToList(fcmList, Fcm.class);
		}
		return resList;
	}
	
	@PostMapping(value = IdmUrlConstants.FCM_DEVICE_SEARCH, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<FcmDevice> search(@RequestBody IdmFcmDevice idmFcmDevice) throws IOException {
		LOGGER.info("FcmDevice Status: {}", idmFcmDevice.getStatus());
		List<FcmDevice> resList = new ArrayList<>();
		List<IdmFcmDevice> fcmList = idmFcmDeviceSvc.search(idmFcmDevice);
		if (Boolean.FALSE.equals(BaseUtil.isListNullZero(fcmList))) {
			LOGGER.info("FcmDevice: {}", fcmList.size());
			resList = JsonUtil.transferToList(fcmList, FcmDevice.class);
		}
		return resList;
	}
}
