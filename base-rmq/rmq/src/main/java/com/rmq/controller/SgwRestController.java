/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmq.constants.SgwTxnCodeConstants;
import com.rmq.sdk.client.constants.SgwErrorCodeEnum;
import com.rmq.sdk.client.constants.SgwUriConstants;
import com.rmq.sdk.model.MessageResponse;
import com.util.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@RestController
@RequestMapping(SgwUriConstants.SERVICE)
public class SgwRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SgwRestController.class);
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public MessageResponse createAudit(@RequestParam(defaultValue = SgwTxnCodeConstants.SGW_TEST_URL) String trxnNo,HttpServletRequest request) {
		LOGGER.info("TESTURL...................................");
		return new MessageResponse(SgwErrorCodeEnum.E200SGW000, request.getRequestURI(), "SGW Test");
	}
	
}