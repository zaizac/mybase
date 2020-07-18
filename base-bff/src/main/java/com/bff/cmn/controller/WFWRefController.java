/**
com.idm * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bff.core.AbstractController;
import com.bff.util.WebUtil;
import com.bff.util.constants.PageConstants;
import com.util.MediaType;
import com.util.pagination.DataTableResults;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_WFW_REFERENCE)
public class WFWRefController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(InboxController.class);


	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public WfwReference getWfwReference(@RequestBody WfwReference dto) {

		logger.info("Calling workflow get reference config");
		return getWfwService().getWfwReference(dto);
	}


	@PostMapping(value = "/pagination", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<WfwReference> getRefTypeDataTable(@RequestBody WfwReference dto,
			HttpServletRequest request) {

		logger.info("Calling workflow get reference list");
		return getWfwService().getRefTypeDataTable(dto, getPaginationRequest(request, true));
	}


	@PostMapping(value = "type-list", produces = MediaType.APPLICATION_JSON)
	public List<WfwReference> getRefTypeList(WfwReference dto) {

		logger.info("Calling workflow get type list");
		return getWfwService().getRefTypeList(dto);
	}


	@PostMapping(value = PageConstants.PAGE_LEVEL
			+ "/pagination", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<RefLevel> getRefLevelDataTable(@RequestBody RefLevel dto, HttpServletRequest request) {

		DataTableResults<RefLevel> dataTableResults = new DataTableResults<>();
		logger.info("Calling workflow getRefLevelDataTable");
		try {
			dataTableResults = getWfwService().getRefLevelDataTable(dto, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			logger.error(e.getMessage());
		}
		return dataTableResults;
	}


	@PostMapping(value = PageConstants.PAGE_TYPE
			+ PageConstants.PAGE_ADDUPDATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public boolean addUpdateType(@RequestBody WfwReference reference) {
		return getWfwService().addUpdateType(reference);
	}


	@PostMapping(value = PageConstants.PAGE_LEVEL
			+ PageConstants.PAGE_ADDUPDATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public boolean addUpdateLevel(@RequestBody RefLevel level) {

		return getWfwService().addUpdateLevel(level);
	}


	@PostMapping(value = PageConstants.PAGE_STATUS
			+ PageConstants.PAGE_ADDUPDATE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public boolean addUpdateStatus(@RequestBody RefStatus status) {

		return getWfwService().addUpdateStatus(status);
	}


	@PostMapping(value = "level-list", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<RefLevel> getRefLevelList(@RequestBody RefLevel dto) {

		logger.info("Calling workflow get level list");
		return getWfwService().getRefLevelList(dto);
	}


	@PostMapping(value = "action-list", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<RefTypeAction> getRefActionList(@RequestBody RefTypeAction dto) {

		logger.info("Calling workflow get type action list");
		return getWfwService().getRefActionList(dto);
	}


	@PostMapping(value = "status-list", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<RefStatus> getRefStatusList(@RequestBody WfwReference dto) {

		logger.info("Calling workflow get level list");

		if (CmnUtil.isObjNull(dto.getRoles())) {
			CmnUtil.getList(dto.getRoles());
		}

		return getWfwService().getRefStatusList(dto);
	}

}
