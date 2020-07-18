/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.rest.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.util.BaseUtil;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.model.WfwRefLevel;
import com.wfw.model.WfwRefStatus;
import com.wfw.model.WfwRefType;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.service.CommonService;


/**
 * @author michelle.angela
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(WorkflowConstants.REFERENCE)
public class ReferenceRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonService commonSvc;

	@Autowired
	protected Mapper dozerMapper;


	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public WfwReference getWfwReference(@RequestBody WfwReference dto) {

		logger.info("Calling workflow get reference config");
		return commonSvc.getRefDetails(dto);
	}


	@PostMapping(value = WorkflowConstants.TYPE + WorkflowConstants.PAGINATION, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<WfwReference> getRefTypeDataTable(@Valid @RequestBody WfwReference dto,
			HttpServletRequest request) {

		logger.info("Calling workflow get reference list");

		DataTableRequest<WfwReference> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());

		DataTableResults<WfwReference> dataTableResult = new DataTableResults<>();
		try {

			List<WfwReference> resultLst = commonSvc.getRefListDetails(dto, dataTableInRQ);
			List<WfwReference> resultLst2 = commonSvc.getRefListDetails(dto, null);
			logger.info("Filtered Size: {}", resultLst.size());
			logger.info("Original Size: {}", resultLst2.size());

			long totalRecords = dataTableInRQ.isInitSearch() ? resultLst2.size() : resultLst.size();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));
			dataTableResult.setData(resultLst);

			logger.info("isFilterByEmpty: {}", dataTableInRQ.getPaginationRequest().isFilterByEmpty());
			dataTableResult.setRecordsFiltered(Integer.toString(resultLst2.size()));
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
			}
		} catch (Exception e) {
			logger.error("Error:startTask ", e);
			throw new WfwException(e.getMessage());
		}
		return dataTableResult;
	}


	@PostMapping(value = WorkflowConstants.TYPE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<WfwReference> getRefTypeList(WfwReference dto) {

		logger.info("Calling workflow get type list");
		return commonSvc.getRefTypeList(dto);
	}


	@PostMapping(value = WorkflowConstants.LEVEL + WorkflowConstants.PAGINATION, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<RefLevel> getRefLevelDataTable(@Valid @RequestBody RefLevel dto,
			HttpServletRequest request) {

		logger.info("Calling workflow get reference list");

		DataTableRequest<RefLevel> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<RefLevel> dataTableResult = new DataTableResults<>();
		try {

			List<RefLevel> resultLst = commonSvc.getRefLevelList(dto, dataTableInRQ, true);
			List<RefLevel> resultLst2 = commonSvc.getRefLevelList(dto, null, true);
			logger.info("Filtered Size: {}", resultLst.size());
			logger.info("Original Size: {}", resultLst2.size());

			setDataTableResults(dataTableInRQ, dataTableResult, resultLst.size(), resultLst2.size());
			dataTableResult.setData(resultLst);

		} catch (Exception e) {
			logger.error("Error:startTask ", e);
			throw new WfwException(e.getMessage());
		}
		return dataTableResult;
	}


	@PostMapping(value = WorkflowConstants.TYPE + WorkflowConstants.ADD_OR_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean addUpdateType(@RequestBody WfwReference reference) {

		if (CmnUtil.isObjNull(reference)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		WfwRefType wfwRefType = commonSvc.addUpdateType(reference);
		return CmnUtil.isObjNull(wfwRefType);
	}


	@PostMapping(value = WorkflowConstants.LEVEL + WorkflowConstants.ADD_OR_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean addUpdateLevel(@RequestBody RefLevel level) {

		if (CmnUtil.isObjNull(level)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		WfwRefLevel wfwRefLevel = commonSvc.addUpdateLevel(level);
		return CmnUtil.isObjNull(wfwRefLevel);
	}


	@PostMapping(value = WorkflowConstants.STATUS + WorkflowConstants.ADD_OR_UPDATE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public boolean addUpdateStatus(@RequestBody RefStatus status) {

		if (CmnUtil.isObjNull(status)) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW013);
		}

		WfwRefStatus wfwRefStatus = commonSvc.addUpdateStatus(status);
		return CmnUtil.isObjNull(wfwRefStatus);
	}


	@PostMapping(value = WorkflowConstants.LEVEL, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<RefLevel> getRefLevelList(@RequestBody RefLevel dto) {

		logger.info("Calling workflow get level list");
		return commonSvc.getRefLevelList(dto, null, false);
	}


	@PostMapping(value = WorkflowConstants.TYPE_ACTION + WorkflowConstants.PAGINATION, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<RefTypeAction> getRefActionDataTable(@Valid @RequestBody RefTypeAction dto,
			HttpServletRequest request) {

		logger.info("Calling workflow get type action DataTableResults");

		DataTableRequest<RefTypeAction> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<RefTypeAction> dataTableResult = new DataTableResults<>();
		try {

			List<RefTypeAction> resultLst = commonSvc.getTypeActionList(dto, dataTableInRQ);
			List<RefTypeAction> resultLst2 = commonSvc.getTypeActionList(dto, null);
			logger.info("Filtered Size: {}", resultLst.size());
			logger.info("Original Size: {}", resultLst2.size());

			setDataTableResults(dataTableInRQ, dataTableResult, resultLst.size(), resultLst2.size());
			dataTableResult.setData(resultLst);

		} catch (Exception e) {
			logger.error("Error:startTask ", e);
			throw new WfwException(e.getMessage());
		}
		return dataTableResult;
	}


	@PostMapping(value = WorkflowConstants.TYPE_ACTION, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<RefTypeAction> getRefActionList(@Valid @RequestBody RefTypeAction dto, HttpServletRequest request) {

		logger.info("Calling workflow get type action list");
		return commonSvc.getTypeActionList(dto, null);
	}


	@PostMapping(value = WorkflowConstants.STATUS, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<RefStatus> getRefStatusList(@RequestBody WfwReference dto) {

		logger.info("Calling workflow get level list");

		RefStatus status = dozerMapper.map(dto, RefStatus.class);
		if (!CmnUtil.isObjNull(dto.getRoles())) {
			status.setLevelIdList(commonSvc.getLevelIdByRoles(CmnUtil.getList(dto.getRoles())));
		}

		return commonSvc.getRefStatusList(status, null);
	}


	@PostMapping(value = WorkflowConstants.STATUS + WorkflowConstants.PAGINATION, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DataTableResults<RefStatus> getRefStatusDataTable(@Valid @RequestBody RefStatus dto,
			HttpServletRequest request) {

		logger.info("Calling workflow get type action list");

		DataTableRequest<RefStatus> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		DataTableResults<RefStatus> dataTableResult = new DataTableResults<>();
		try {

			List<RefStatus> resultLst = commonSvc.getRefStatusList(dto, dataTableInRQ);
			List<RefStatus> resultLst2 = commonSvc.getRefStatusList(dto, null);
			logger.info("Filtered Size: {}", resultLst.size());
			logger.info("Original Size: {}", resultLst2.size());

			setDataTableResults(dataTableInRQ, dataTableResult, resultLst.size(), resultLst2.size());
			dataTableResult.setData(resultLst);

		} catch (Exception e) {
			logger.error("Error:startTask ", e);
			throw new WfwException(e.getMessage());
		}
		return dataTableResult;
	}


	@SuppressWarnings("rawtypes")
	private DataTableResults setDataTableResults(DataTableRequest dataTableInRQ, DataTableResults dataTableResult,
			int resultSize, int resultSize2) {

		long totalRecords = dataTableInRQ.isInitSearch() ? resultSize2 : resultSize;
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setRecordsTotal(BaseUtil.getStr(totalRecords));

		logger.info("isFilterByEmpty: {}", dataTableInRQ.getPaginationRequest().isFilterByEmpty());
		dataTableResult.setRecordsFiltered(Integer.toString(resultSize2));
		if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(BaseUtil.getStr(totalRecords));
		}

		return dataTableResult;
	}

}
