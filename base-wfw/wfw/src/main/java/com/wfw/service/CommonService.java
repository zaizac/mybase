/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.service;


import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.util.pagination.DataTableRequest;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwRefLevel;
import com.wfw.model.WfwRefLevelRole;
import com.wfw.model.WfwRefLevelRolePk;
import com.wfw.model.WfwRefStatus;
import com.wfw.model.WfwRefType;
import com.wfw.model.WfwRefTypeAction;
import com.wfw.model.WfwTaskMaster;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.RefLevel;
import com.wfw.sdk.model.RefStatus;
import com.wfw.sdk.model.RefTypeAction;
import com.wfw.sdk.model.TaskHistory;
import com.wfw.sdk.model.TaskMaster;
import com.wfw.sdk.model.WfwRefPayload;
import com.wfw.sdk.model.WfwReference;
import com.wfw.sdk.util.CmnUtil;


/**
 * @author michelle.angela
 *
 */
@Service(QualifierConstants.COMMON_SERVICE)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.COMMON_SERVICE)
public class CommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	private WfwTaskMasterService taskMasterSvc;

	@Autowired
	private WfwTaskHistoryService taskHistorySvc;

	@Autowired
	private WfwRefLevelService refLevelSvc;

	@Autowired
	private WfwRefStatusService refStatusSvc;

	@Autowired
	private WfwRefTypeService refTypeSvc;

	@Autowired
	private WfwRefLevelRoleService refLevelRoleSvc;

	@Autowired
	private WfwRefTypeActionService refTypeActionSvc;


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwTaskMaster startTask(WfwRefPayload dto) {

		WfwTaskMaster wfwTaskMaster = null;
		try {

			wfwTaskMaster = taskMasterSvc.findByApplicationRefNo(dto.getAppRefNo());

			// get Type by transaction
			if (CmnUtil.isObjNull(dto.getTypeId())) {
				WfwRefType type = refTypeSvc.getActiveRefTypeList(dto).get(0);
				dto.setTypeId(type.getTypeId());
			}

			// get Level Role
			WfwRefLevel level = refLevelSvc.getActiveWfwLevelList(dto).get(0);
			if (CmnUtil.isObjNull(level)) {
				throw new WfwException(WfwErrorCodeEnum.E400WFW002, new Object[] { dto.getTranId() });
			}

			// get status
			WfwRefStatus status = refStatusSvc.findActiveListByLevelId(level.getLevelId()).get(0);
			if (CmnUtil.isObjNull(status)) {
				throw new WfwException(WfwErrorCodeEnum.E400WFW002, new Object[] { level.getLevelId() });
			}

			dto.setTypeId(status.getTypeId());
			dto.setLevelId(status.getLevelId());
			dto.setStatusId(status.getStatusId());

			wfwTaskMaster = taskMasterSvc.createUpdate(dto, wfwTaskMaster);
			taskHistorySvc.createUpdate(dozerMapper.map(wfwTaskMaster, TaskHistory.class), CmnConstants.ACTION_START,
					CmnUtil.intToBoolean(status.getDisplay()), dto.getTaskAuthorId(), dto.getTaskAuthorName());

		} catch (Exception e) {
			LOGGER.error("Error:startTask ", e);
			throw new WfwException(WfwErrorCodeEnum.E500WFW004, new Object[] { e });
		}

		return wfwTaskMaster;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwTaskMaster updateTask(WfwRefPayload dto) {

		WfwTaskMaster wfwTaskMaster = taskMasterSvc.findBytTaskMasterId(dto.getTaskMasterId());

		if (CmnUtil.isObjNull(wfwTaskMaster)) {
			throw new WfwException(WfwErrorCodeEnum.I404WFW001, new Object[] { dto.getTaskMasterId() });
		}

		if (wfwTaskMaster.getRefType().getAutoClaim() == 0
				&& !CmnUtil.isEqualsCaseIgnore(dto.getTaskAuthorId(), wfwTaskMaster.getClaimBy())) {
			throw new WfwException(WfwErrorCodeEnum.E400WFW002, new Object[] { dto.getTaskMasterId() });
		}

		try {
			// get Status
			WfwRefStatus status = refStatusSvc.findByLevelIdStatusCd(wfwTaskMaster.getLevelId(), dto.getStatusCd());
			if (CmnUtil.isObjNull(status)) {
				throw new WfwException(WfwErrorCodeEnum.E400WFW002, new Object[] { dto.getTaskMasterId() });
			}

			wfwTaskMaster.setPrevStatusId(wfwTaskMaster.getStatusId());
			wfwTaskMaster.setPrevLevelId(wfwTaskMaster.getLevelId());
			wfwTaskMaster.setStatusId(status.getStatusId());
			wfwTaskMaster.setRemark(dto.getRemark());
			wfwTaskMaster = taskMasterSvc.setWfwTaskMaster(dto, wfwTaskMaster);
			taskHistorySvc.createUpdate(dozerMapper.map(wfwTaskMaster, TaskHistory.class), status.getStatusDesc(),
					CmnUtil.intToBoolean(status.getDisplay()), dto.getTaskAuthorId(), dto.getTaskAuthorName());

			// has next level
			if (status.getNextLevelId() > 0) {
				wfwTaskMaster = processNextLevel(dto, status, wfwTaskMaster.getAppRefNo());
			} else {
				wfwTaskMaster.setLevelId(null);
				wfwTaskMaster = taskMasterSvc.update(wfwTaskMaster);
			}

		} catch (Exception e) {
			LOGGER.error("Error:startTask ", e);
			throw new WfwException(WfwErrorCodeEnum.E500WFW004, new Object[] { e });
		}

		return wfwTaskMaster;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwTaskMaster processNextLevel(WfwRefPayload dto, WfwRefStatus status, String appRefNo) {

		if (!CmnUtil.isObjNull(status.getLevelId())) {
			dto.setAppRefNo(appRefNo);
			dto.setLevelId(status.getNextLevelId());
			dto.setPrevLevelId(status.getLevelId());
			dto.setPrevStatusId(status.getStatusId());
			dto.setTypeId(status.getTypeId());
			return startTask(dto);
		}

		return null;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean claimTask(WfwRefPayload dto) {

		for (String taskMasterId : dto.getTaskMasterIdList()) {
			try {
				WfwTaskMaster wfwTaskMaster = taskMasterSvc.findBytTaskMasterId(taskMasterId);

				if (CmnUtil.isObjNull(wfwTaskMaster)) {
					throw new WfwException(WfwErrorCodeEnum.I404WFW001, new Object[] { taskMasterId });
				}

				if (!CmnUtil.isObjNull(wfwTaskMaster.getClaimBy())) {
					return false;
				}

				dto.setAutoClaim(true);
				wfwTaskMaster = taskMasterSvc.createUpdate(dto, wfwTaskMaster);
				taskHistorySvc.createUpdate(dozerMapper.map(wfwTaskMaster, TaskHistory.class),
						CmnConstants.ACTION_CLAIM, true, dto.getTaskAuthorId(), dto.getTaskAuthorName());

			} catch (Exception e) {
				LOGGER.error("Error: CLAIMED", e);
				throw new WfwException(WfwErrorCodeEnum.E500WFW004, new Object[] { e });
			}
		}
		LOGGER.info("Success: CLAIMED");
		return true;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean releaseTask(WfwRefPayload dto) {

		for (String taskMasterId : dto.getTaskMasterIdList()) {
			try {
				WfwTaskMaster wfwTaskMaster = taskMasterSvc.findByTaskMasterIdAndClaimBy(taskMasterId,
						dto.getTaskAuthorId());

				if (CmnUtil.isObjNull(wfwTaskMaster)) {
					return false;
				}

				dto.setAutoClaim(false);
				wfwTaskMaster = taskMasterSvc.createUpdate(dto, wfwTaskMaster);
				taskHistorySvc.createUpdate(dozerMapper.map(wfwTaskMaster, TaskHistory.class),
						CmnConstants.ACTION_RELEASE, true, dto.getTaskAuthorId(), dto.getTaskAuthorName());

			} catch (Exception e) {
				LOGGER.error("Error: RELEASED", e);
				throw new WfwException(WfwErrorCodeEnum.E500WFW004, new Object[] { e });
			}
		}
		LOGGER.info("Success: RELEASED");
		return true;
	}


	public List<WfwReference> getRefListDetails(WfwReference dto, DataTableRequest<WfwReference> dataTableInRQ) {

		List<WfwRefType> wfwRefTypeList = refTypeSvc.getRefTypeList(dto, dataTableInRQ);
		if (CmnUtil.isListNull(wfwRefTypeList)) {
			return new ArrayList<>();
		}

		List<WfwReference> refList = new ArrayList<>();
		for (WfwRefType type : wfwRefTypeList) {

			WfwReference reference = dozerMapper.map(type, WfwReference.class);

			if (dto.isWithLevel()) {
				RefLevel level = new RefLevel();
				level.setTypeId(reference.getTypeId());
				reference.setRefLevelList(getRefLevelList(level, null, dto.isWithStatus()));
			}
			refList.add(reference);
		}

		return refList;
	}


	public List<RefLevel> getRefLevelList(RefLevel dto, DataTableRequest<RefLevel> dataTableInRQ, boolean withStatus) {

		List<RefLevel> levelList = new ArrayList<>();
		for (WfwRefLevel wfwLevel : refLevelSvc.getWfwLevelList(dto, dataTableInRQ)) {

			RefLevel level = dozerMapper.map(wfwLevel, RefLevel.class);
			level.setRoles(refLevelRoleSvc.setRoleList(wfwLevel.getRefLevelRoleList()));

			if (withStatus) {
				List<RefStatus> refStatus = new ArrayList<>();
				for (WfwRefStatus wfwStatus : refStatusSvc.findAllByLevelId(level.getLevelId())) {
					refStatus.add(dozerMapper.map(wfwStatus, RefStatus.class));
				}
				level.setRefStatus(refStatus);
			}

			levelList.add(level);
		}

		return levelList;
	}


	public WfwReference getRefDetails(WfwReference dto) {

		WfwRefType wfwRefType = refTypeSvc.getRefTypeList(dto, null).get(0);
		if (CmnUtil.isObjNull(wfwRefType)) {
			return null;
		}

		WfwReference reference = dozerMapper.map(wfwRefType, WfwReference.class);
		if (!CmnUtil.isObjNull(dto.getLevelId())) {

			RefLevel refLevel = new RefLevel();
			refLevel.setLevelId(dto.getLevelId());
			WfwRefLevel wfwLevel = refLevelSvc.getWfwLevelList(refLevel, null).get(0);
			if (!CmnUtil.isObjNull(wfwLevel)) {

				reference.setRefLevel(dozerMapper.map(wfwLevel, RefLevel.class));
				reference.getRefLevel().setRoles(refLevelRoleSvc.setRoleList(wfwLevel.getRefLevelRoleList()));
			}
		}

		if (!CmnUtil.isObjNull(dto.getStatusId())) {

			WfwRefStatus wfwStatus = refStatusSvc.find(dto.getStatusId());
			if (!CmnUtil.isObjNull(wfwStatus)) {
				reference.setRefStatus(dozerMapper.map(wfwStatus, RefStatus.class));
			}
		}

		return reference;
	}


	public WfwReference getType(WfwReference dto) {
		WfwRefType wfwRefType = refTypeSvc.getRefTypeList(dto, null).get(0);
		return dozerMapper.map(wfwRefType, WfwReference.class);
	}


	public List<WfwRefType> getRefTypeList() {
		return refTypeSvc.findAll();
	}


	public List<WfwRefLevelRole> getRefLevelRoleList() {
		return refLevelRoleSvc.findAll();
	}


	public RefLevel getRefLevel(Integer levelId) {
		return dozerMapper.map(refLevelSvc.find(levelId), RefLevel.class);
	}


	public List<WfwRefLevel> getRefLevelListByType(Integer typeId) {
		return refLevelSvc.findByTypeId(typeId);
	}


	public List<WfwRefStatus> getRefStatusListByLevel(Integer levelId) {
		return refStatusSvc.findAllByLevelId(levelId);
	}


	public List<RefStatus> getRefStatusList(RefStatus status, DataTableRequest<RefStatus> dataTableInRQ) {
		return refStatusSvc.findRefStatusList(status, dataTableInRQ);
	}


	public RefStatus getRefStatus(Integer statusId) {
		return dozerMapper.map(refStatusSvc.find(statusId), RefStatus.class);
	}


	public WfwRefType addUpdateType(WfwReference dto) {
		try {
			return refTypeSvc.createUpdate(dto);
		} catch (Exception e) {
			throw new WfwException(WfwErrorCodeEnum.E500WFW001, new Object[] { e.getMessage() });
		}
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public WfwRefLevel addUpdateLevel(RefLevel dto) {
		WfwRefLevel refLevel = null;
		try {
			refLevel = refLevelSvc.createUpdate(dto);

			if (CmnUtil.isObjNull(dto.getLevelId())) {
				refStatusSvc.createInitialStatus(refLevel.getTypeId(), refLevel.getLevelId(),
						refLevel.getCreateId());
			}

			// add/update/delete ROLES
			if (!CmnUtil.isObjNull(dto.getRoles())) {

				for (WfwRefLevelRole lvlRole : refLevelRoleSvc.findByLevelId(refLevel.getLevelId())) {
					if (!dto.getRoles().contains(lvlRole.getLevelRolePk().getRoleCd())) {
						LOGGER.info("deleted role {}", lvlRole.getLevelRolePk().getRoleCd());
						refLevelRoleSvc.delete(lvlRole);
						refLevel.getRefLevelRoleList().remove(lvlRole);
					}
				}

				for (String role : dto.getRoles()) {
					WfwRefLevelRole levelRole = new WfwRefLevelRole();
					levelRole.setLevelRolePk(new WfwRefLevelRolePk(refLevel.getLevelId(), role));
					levelRole.setCreateId(refLevel.getCreateId());
					refLevelRoleSvc.addUpdate(levelRole);
				}
			}

			// add or update ACTION
			if (!CmnUtil.isObjNull(dto.getRefTypeActionList())) {
				for (RefTypeAction action : dto.getRefTypeActionList()) {
					if (CmnUtil.isObjNull(action.getLevelId())) {
						action.setLevelId(refLevel.getLevelId());
					}
					if (CmnUtil.isObjNull(action.getTypeId())) {
						action.setTypeId(refLevel.getTypeId());
					}
					refTypeActionSvc.addUpdate(action, refLevel.getCreateId());
				}
			}

		} catch (Exception e) {
			throw new WfwException(WfwErrorCodeEnum.E500WFW001, new Object[] { e.getMessage() });
		}
		return refLevel;
	}


	public WfwRefStatus addUpdateStatus(RefStatus dto) {
		try {
			return refStatusSvc.createUpdate(dto);
		} catch (Exception e) {
			throw new WfwException(WfwErrorCodeEnum.E500WFW001, new Object[] { e.getMessage() });
		}
	}


	public List<TaskMaster> getTaskMasterList(WfwRefPayload payload) {
		return taskMasterSvc.getTaskMasterList(payload, null);
	}


	public TaskMaster getTaskMaster(String taskMasterId, boolean withHist) {
		try {

			TaskMaster taskMaster = dozerMapper.map(taskMasterSvc.findBytTaskMasterId(taskMasterId),
					TaskMaster.class);
			if (withHist) {
				taskMaster.setTaskHistoryList(taskHistorySvc.getHistoryByTaskMasterId(taskMasterId));
			}

			return taskMaster;
		} catch (Exception e) {
			throw new WfwException(WfwErrorCodeEnum.E500WFW001, new Object[] { e.getMessage() });
		}

	}


	public List<WfwReference> getRefTypeList(WfwReference dto) {
		List<WfwReference> wfwReferenceList = new ArrayList<>();
		List<WfwRefType> wfwRefTypeList = refTypeSvc.getRefTypeList(dto, null);
		for (WfwRefType refType : wfwRefTypeList) {
			wfwReferenceList.add(dozerMapper.map(refType, WfwReference.class));
		}
		return wfwReferenceList;
	}


	@SuppressWarnings("rawtypes")
	public List<RefTypeAction> getTypeActionList(RefTypeAction dto, DataTableRequest dataTableInRQ) {

		List<RefTypeAction> typeActionList = new ArrayList<>();

		for (WfwRefTypeAction typeAction : refTypeActionSvc.findTypeActionList(dto, dataTableInRQ)) {
			if (!CmnUtil.isObjNull(typeAction)) {
				typeActionList.add(dozerMapper.map(typeAction, RefTypeAction.class));
			}
		}
		return typeActionList;
	}


	public List<Integer> getLevelIdByRoles(List<String> roles) {
		return refLevelRoleSvc.findLevelIdByRoles(roles);
	}
}
