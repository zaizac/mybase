package com.idm.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idm.core.AbstractRestController;
import com.idm.model.IdmAuditAction;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.AuditAction;
import com.idm.service.IdmAuditActionService;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;
import com.util.MediaType;


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


	@PostMapping(value = "/new", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public AuditAction createAudit(@Valid @RequestBody AuditAction auditAction, HttpServletRequest request) {
		try {
			IdmAuditAction idmAuditAction = JsonUtil.transferToObject(auditAction, IdmAuditAction.class);
			if(!BaseUtil.isObjNull(idmAuditAction) && BaseUtil.isObjNull(idmAuditAction.getAuditDt())) {
				idmAuditAction.setAuditDt(DateUtil.getSQLTimestamp());
			}
			idmAuditAction = auditActionSvc.create(idmAuditAction);
			return JsonUtil.transferToObject(idmAuditAction, AuditAction.class);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
			return auditAction;
		}
	}
	
	@PostMapping(value = "/search", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<AuditAction> search(@Valid @RequestBody AuditAction auditAction, HttpServletRequest request) {
		try {
			IdmAuditAction idmAuditAction = JsonUtil.transferToObject(auditAction, IdmAuditAction.class);
			List<IdmAuditAction> idmAuditActionLst = auditActionSvc.search(idmAuditAction);
			AuditAction[] result = JsonUtil.transferToObject(idmAuditActionLst, AuditAction[].class);
			return Arrays.asList(result);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
	}
	
	@GetMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<AuditAction> findAll(HttpServletRequest request) {
		List<IdmAuditAction> idmAuditActionLst = auditActionSvc.findAll();
		if(BaseUtil.isObjNull(idmAuditActionLst)) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		
		try {
			AuditAction[] result = JsonUtil.transferToObject(idmAuditActionLst, AuditAction[].class);
			return Arrays.asList(result);
		} catch (IOException e) {
			LOGGER.info("{}", e.getMessage());
		}
		return null;
	}

}
