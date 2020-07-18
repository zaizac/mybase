/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.model.LoginDto;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.CryptoUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
public class ReferenceRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceRestController.class);


	@RequestMapping(value = IdmUrlConstrants.ENCRYPT, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String encrypt(@Valid @RequestBody LoginDto loginDto, @RequestParam(required = false) boolean isHashKey,
			HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (BaseUtil.isObjNull(loginDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		if (isHashKey) {
			return getHash(loginDto.getPassword());
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ENCRYPT);
		return CryptoUtil.encrypt(loginDto.getPassword(), loginDto.getClientSecret());
	}


	@RequestMapping(value = IdmUrlConstrants.DECRYPT, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String decrypt(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		if (BaseUtil.isObjNull(loginDto)) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_DECRYPT);
		return CryptoUtil.decrypt(loginDto.getPassword(), loginDto.getClientSecret());
	}

}