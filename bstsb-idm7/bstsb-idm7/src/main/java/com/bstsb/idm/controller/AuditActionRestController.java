/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstsb.idm.core.AbstractRestController;
import com.bstsb.idm.model.IdmAuditAction;
import com.bstsb.idm.sdk.constants.IdmUrlConstants;
import com.bstsb.idm.sdk.model.AuditAction;
import com.bstsb.idm.service.IdmAuditActionService;
import com.bstsb.util.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.AUDIT_ACTION)
public class AuditActionRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditActionRestController.class);

	@Autowired
	IdmAuditActionService auditActionSvc;


	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public AuditAction createAudit(@Valid @RequestBody AuditAction menuDto, HttpServletRequest request) {
		IdmAuditAction idmMenu = dozerMapper.map(menuDto, IdmAuditAction.class);
		IdmAuditAction menuLevel = null;
		try {
			menuLevel = auditActionSvc.create(idmMenu);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
		return dozerMapper.map(menuLevel, AuditAction.class);
	}

}
