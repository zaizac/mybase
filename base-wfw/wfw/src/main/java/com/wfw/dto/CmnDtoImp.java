/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.util.BaseUtil;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;
import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;
import com.wfw.model.WfwAsgnTran;
import com.wfw.model.WfwInbox;
import com.wfw.model.WfwInboxHist;
import com.wfw.model.WfwInboxMstr;
import com.wfw.model.WfwLevel;
import com.wfw.model.WfwStatus;
import com.wfw.model.WfwType;
import com.wfw.model.WfwUser;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.constants.WorkflowConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.sdk.model.InterviewEnquiry;
import com.wfw.sdk.model.TaskDetails;
import com.wfw.sdk.model.TaskRemark;
import com.wfw.sdk.model.TaskStatus;
import com.wfw.sdk.model.WfwPayload;
import com.wfw.sdk.util.CmnUtil;
import com.wfw.util.BeanUtil;
import com.wfw.util.SeqGen;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
public class CmnDtoImp implements CmnDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmnDtoImp.class);

	private static final String TASKID_SPLIT_PATTERN = "\\s*,\\s*";

	private Random rand = new Random();

	@Autowired
	@Qualifier(QualifierConstants.WF_ASGN_ROLE_DTO)
	private WfwAsgnRoleDto wfwAsgnRoleDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_ASGN_TRAN_DTO)
	private WfwAsgnTranDto wfwAsgnTranDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_DTO)
	private WfwInboxDto wfwInboxDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_HIST_DTO)
	private WfwInboxHistDto wfwInboxHistDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_MSTR_DTO)
	private WfwInboxMstrDto wfwInboxMstrDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_LEVEL_DTO)
	private WfwLevelDto wfwLevelDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_TYPE_DTO)
	private WfwTypeDto wfwTypeDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_STATUS_DTO)
	private WfwStatusDto wfwStatusDto;

	@Autowired
	@Qualifier(QualifierConstants.WF_USER_DTO)
	private WfwUserDto wfwUserDto;


	@Override
	public List<WfwAsgnRole> findWfAsgnRoleByRoleId(String roleId) {
		return wfwAsgnRoleDto.findWfAsgnRoleByRoleId(roleId);
	}


	@Override
	public List<WfwAsgnRole> findWfAsgnRoleByLevelId(String levelId) {
		return wfwAsgnRoleDto.findWfAsgnRoleByLevelId(levelId);
	}


	@Override
	public List<WfwAsgnRole> findAllWfAsgnRole() {
		return wfwAsgnRoleDto.findAllWfAsgnRole();
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		return wfwAsgnRoleDto.addWfAsgnRole(wfwAsgnRole);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfAsgnRole(List<WfwAsgnRole> wfwAsgnRoleList) {
		return wfwAsgnRoleDto.addWfAsgnRole(wfwAsgnRoleList);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		return wfwAsgnRoleDto.editWfAsgnRole(wfwAsgnRole);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfAsgnRole(WfwAsgnRole wfwAsgnRole) {
		return wfwAsgnRoleDto.deleteWfAsgnRole(wfwAsgnRole);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfAsgnRoleByLevelId(String levelId) {
		return wfwAsgnRoleDto.deleteWfAsgnRoleByLevelId(levelId);
	}


	@Override
	public WfwAsgnRole findWfAsgnRoleById(WfwAsgnRolePk wfwAsgnRolePk) {
		return wfwAsgnRoleDto.findWfAsgnRoleById(wfwAsgnRolePk);
	}


	// WfAsgnTran
	@Override
	public WfwAsgnTran findAsgnTranByTranId(String tranId) {
		return wfwAsgnTranDto.findAsgnTranByTranId(tranId);
	}


	@Override
	public List<WfwAsgnTran> findAllWfAsgnTran() {
		return wfwAsgnTranDto.findAllWfAsgnTran();
	}


	@Override
	public WfwAsgnTran findWfAsgnTranByTypeId(String typeId) {
		return wfwAsgnTranDto.findWfAsgnTranByTypeId(typeId);
	}


	@Override
	public List<WfwAsgnTran> findWfAsgnTranByModuleId(String moduleId) {
		return wfwAsgnTranDto.findWfAsgnTranByModuleId(moduleId);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		return wfwAsgnTranDto.addWfAsgnTran(wfwAsgnTran);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		return wfwAsgnTranDto.editWfAsgnTran(wfwAsgnTran);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		return wfwAsgnTranDto.deleteWfAsgnTran(wfwAsgnTran);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfAsgnTran(Integer tranId) {
		return wfwAsgnTranDto.deleteWfAsgnTran(tranId);
	}


	// WfInbox

	@Override
	public WfwInbox findWfInboxByTaskId(String taskId) {
		return wfwInboxDto.findWfInboxByTaskId(taskId);
	}


	@Override
	public WfwInbox findWfInboxByTaskIdAndOfficerId(String taskId, String officerId) {
		return wfwInboxDto.findWfInboxByTaskIdAndOfficerId(taskId, officerId);
	}


	@Override
	public WfwInbox findWfInboxByRefNoAndOfficerId(String refNo, String officerId) {
		return wfwInboxDto.findWfInboxByRefNoAndOfficerId(refNo, officerId);
	}


	@Override
	public List<WfwInbox> findAllWfInbox() {
		return wfwInboxDto.findAllWfInbox();
	}


	@Override
	public List<WfwInbox> findWfInboxByRefNo(String refNo) {
		return wfwInboxDto.findWfInboxByRefNo(refNo);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfInbox(WfwInbox wfwInbox) {
		wfwInbox.setInboxId(SeqGen.getSequenceNo());
		return wfwInboxDto.addWfInbox(wfwInbox);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfInbox(WfwInbox wfwInbox) {
		return wfwInboxDto.editWfInbox(wfwInbox);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInbox(WfwInbox wfwInbox) {
		return wfwInboxDto.deleteWfInbox(wfwInbox);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInbox(String taskId) {
		return wfwInboxDto.deleteWfInbox(taskId);
	}


	@Override
	public List<WfwInbox> searchWfInbox(WfwInbox wfwInbox) {
		return wfwInboxDto.searchWfInbox(wfwInbox);
	}


	@Override
	public List<WfwInboxMstr> findInboxByOfficerIdModId(String officerId, String moduleId) {
		return findInboxByOfficerIdModIdRefNo(officerId, moduleId, CmnConstants.EMPTY_STRING);
	}


	@Override
	public List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String moduleId, String refId) {
		return wfwInboxDto.findInboxByOfficerIdModIdRefNo(officerId, moduleId, refId);
	}


	@Override
	public String findOfficerIdWithMinimumWorkLoad(String levelId) {
		return findOfficerIdWithMinimumWorkLoad(levelId, CmnConstants.EMPTY_STRING);
	}


	@Override
	public String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId) {
		return wfwInboxDto.findOfficerIdWithMinimumWorkLoad(levelId, currOfficerId);
	}


	// WfInboxHist

	@Override
	public WfwInboxHist findWfInboxHistByHistoryId(String historyId) {
		try {
			return wfwInboxHistDto.findWfInboxHistByHistoryId(historyId);
		} catch (Exception e) {
			LOGGER.error("Error:findWfInboxHistByHistoryId ", e);
			return null;
		}
	}


	@Override
	public List<WfwInboxHist> findWfInboxHistByTaskId(String taskId) {
		try {
			return wfwInboxHistDto.findHistoryByTaskId(taskId);
		} catch (Exception e) {
			LOGGER.error("Error:findWfInboxHistByTaskId ", e);
			return Collections.emptyList();
		}
	}


	@Override
	public List<WfwInboxHist> findAllWfInboxHist() {
		try {
			return wfwInboxHistDto.findAllWfInboxHist();
		} catch (Exception e) {
			LOGGER.error("Error:findAllWfInboxHist ", e);
			return Collections.emptyList();
		}
	}


	@Override
	@Transactional
	public List<WfwInboxHist> findWfInboxHistByRefNo(String refNo) {
		try {
			return wfwInboxHistDto.findWfInboxHistByRefNo(refNo);
		} catch (Exception e) {
			LOGGER.error("Error:findWfInboxHistByRefNo ", e);
			return Collections.emptyList();
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfInboxHist(WfwInboxHist wfwInboxHist) {
		try {
			wfwInboxHistDto.addWfInboxHist(wfwInboxHist);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:addWfInboxHist ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfInboxHist(WfwInboxHist wfwInboxHist) {
		try {
			wfwInboxHistDto.editWfInboxHist(wfwInboxHist);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:editWfInboxHist ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInboxHist(WfwInboxHist wfwInboxHist) {
		try {
			wfwInboxHistDto.deleteWfInboxHist(wfwInboxHist);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:deleteWfInboxHist ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInboxHist(Integer taskId) {
		try {
			wfwInboxHistDto.deleteWfInboxHist(taskId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:deleteWfInboxHist ", e);
			return false;
		}
	}


	// WfInboxMstr

	@Override
	public WfwInboxMstr findWfInboxMstrByTaskId(String taskId) {
		try {
			return wfwInboxMstrDto.findWfInboxMstrByRefNo(taskId);
		} catch (Exception e) {
			LOGGER.error("Error:findWfInboxMstrByTaskId ", e);
			return null;
		}
	}


	@Override
	public List<WfwInboxMstr> findAllWfInboxMstr() {
		try {
			return wfwInboxMstrDto.findAllWfInboxMstr();
		} catch (Exception e) {
			LOGGER.error("Error:findAllWfInboxMstr ", e);
			return Collections.emptyList();
		}

	}


	@Override
	public WfwInboxMstr findWfInboxMstrByRefNo(String refNo) {
		try {
			return wfwInboxMstrDto.findWfInboxMstrByRefNo(refNo);
		} catch (Exception e) {
			LOGGER.error("Error:findWfInboxMstrByRefNo ", e);
			return null;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		try {
			wfwInboxMstrDto.addWfInboxMstr(wfwInboxMstr);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:addWfInboxMstr ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfInboxMstrAndHistory(WfwInboxMstr wfwInboxMstr) {
		try {
			wfwInboxMstr.setTaskId(SeqGen.getSequenceNo());
			wfwInboxMstrDto.addWfInboxMstr(wfwInboxMstr);

			WfwInboxHist wfwInboxHist = new WfwInboxHist();
			wfwInboxHist.setHistId(SeqGen.getSequenceNo());
			BeanUtil.copyProperties(wfwInboxMstr, wfwInboxHist);
			wfwInboxHistDto.addWfInboxHist(wfwInboxHist);
			LOGGER.info("wfwInboxHist = {}", wfwInboxHist.getRefNo());
			return true;
		} catch (JpaSystemException e) {
			LOGGER.error("addWfInboxMstrAndHistory: JpaSystemException. ", e);
			return false;
		} catch (Exception e) {
			LOGGER.error("addWfInboxMstrAndHistory: Exception. ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean crudWfTask(WfwInboxMstr wfwInboxMstr, WfwInbox wfwInbox, WfwInboxHist wfwInboxHist,
			String oparationCode, String actionDoneBy) {
		try {
			if (oparationCode.length() > 2) {
				String mstrOpertion = String.valueOf(oparationCode.charAt(0));
				String inboxOpertion = String.valueOf(oparationCode.charAt(1));
				String histOpertion = String.valueOf(oparationCode.charAt(2));

				if (CmnUtil.isEquals(mstrOpertion, CmnConstants.DIGIT_1)) {
					wfwInboxMstr.setCreateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxMstr.setCreateId(actionDoneBy);
					wfwInboxMstr.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxMstr.setUpdateId(actionDoneBy);
					wfwInboxMstrDto.addWfInboxMstr(wfwInboxMstr);
				} else if (CmnUtil.isEquals(mstrOpertion, CmnConstants.DIGIT_2)) {
					wfwInboxMstr.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxMstr.setUpdateId(actionDoneBy);
					wfwInboxMstrDto.editWfInboxMstr(wfwInboxMstr);
				} else if (CmnUtil.isEquals(mstrOpertion, CmnConstants.DIGIT_3)) {
					wfwInboxMstrDto.deleteWfInboxMstr(wfwInboxMstr);
				}

				if (CmnUtil.isEquals(inboxOpertion, CmnConstants.DIGIT_1)) {
					wfwInbox.setInboxId(SeqGen.getSequenceNo());
					wfwInbox.setTaskId(wfwInboxMstr.getTaskId());
					wfwInbox.setCreateDt(CmnUtil.getCurrentDateSQL());
					wfwInbox.setCreateId(actionDoneBy);
					wfwInbox.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInbox.setUpdateId(actionDoneBy);
					wfwInboxDto.addWfInbox(wfwInbox);
				} else if (CmnUtil.isEquals(inboxOpertion, CmnConstants.DIGIT_2)) {
					wfwInbox.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInbox.setUpdateId(actionDoneBy);
					wfwInboxDto.editWfInbox(wfwInbox);
				} else if (CmnUtil.isEquals(inboxOpertion, CmnConstants.DIGIT_3)) {
					wfwInboxDto.deleteWfInbox(wfwInbox);
				}

				if (CmnUtil.isEquals(histOpertion, CmnConstants.DIGIT_1)) {
					wfwInboxHist.setHistId(SeqGen.getSequenceNo());
					wfwInboxHist.setTaskId(wfwInboxMstr.getTaskId());
					wfwInboxHist.setCreateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxHist.setCreateId(actionDoneBy);
					wfwInboxHist.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxHist.setUpdateId(actionDoneBy);
					wfwInboxHistDto.addWfInboxHist(wfwInboxHist);
				} else if (CmnUtil.isEquals(histOpertion, CmnConstants.DIGIT_2)) {
					wfwInboxHist.setUpdateDt(CmnUtil.getCurrentDateSQL());
					wfwInboxHist.setUpdateId(actionDoneBy);
					wfwInboxHistDto.editWfInboxHist(wfwInboxHist);
				} else if (CmnUtil.isEquals(histOpertion, CmnConstants.DIGIT_3)) {
					wfwInboxHistDto.editWfInboxHist(wfwInboxHist);
				}
				return true;
			} else {
				throw new WfwException(WfwErrorCodeEnum.E400WFW001);
			}
		} catch (JpaSystemException e) {
			throw new JpaSystemException(e);
		} catch (Exception e) {
			throw e;
		}

	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean claimTask(List<String> taskIdList, String officerId, String officerName, String userType) {
		try {
			List<WfwInboxMstr> wfwMstr = new ArrayList<>();
			for (String taskId : taskIdList) {
				WfwInboxMstr wfMstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskId);
				wfMstr.setClaimedDate(CmnUtil.getCurrentDateSQL());
				if (!CmnUtil.isObjNull(wfMstr)) {
					wfMstr.setOfficerId(officerId);
					wfMstr.setOfficerName(officerName);
					wfMstr.setQueueInd(CmnConstants.QUEUE_IND_INBOXED);
					wfwMstr.add(wfMstr);
				}
			}

			for (WfwInboxMstr wfwInboxMstr : wfwMstr) {
				WfwInbox inbox = new WfwInbox();
				BeanUtil.copyProperties(wfwInboxMstr, inbox);

				String currAppStatus = wfwInboxMstr.getApplStsCode();

				WfwInboxHist history = new WfwInboxHist();
				BeanUtil.copyProperties(wfwInboxMstr, history);
				history.setApplRemark(CmnConstants.REMARK_CLAIM_TASK);
				history.setCreateDt(CmnUtil.getCurrentDateSQL());
				history.setCreateId(officerId);
				history.setApplStsCodePrev(currAppStatus);
				crudWfTask(wfwInboxMstr, inbox, history, "211", officerId);
			}

			return true;

		} catch (JpaSystemException e) {
			LOGGER.error("claimTask:JpaSystemException. ", e);
			return false;
		} catch (Exception e) {
			LOGGER.error("claimTask:Exception. ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		WfwInboxMstr wfwInboxMstrTarget = findWfInboxMstrByRefNo(wfwInboxMstr.getTaskId());
		if (!CmnUtil.isObjNull(wfwInboxMstrTarget)) {
			BeanUtil.copyProperties(wfwInboxMstr, wfwInboxMstrTarget);
			wfwInboxMstr = wfwInboxMstrTarget;
		}
		try {
			wfwInboxMstrDto.editWfInboxMstr(wfwInboxMstr);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:editWfInboxMstr ", e);
			return false;
		}

	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		try {
			wfwInboxMstrDto.deleteWfInboxMstr(wfwInboxMstr);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:deleteWfInboxMstr ", e);
			return false;
		}
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfInboxMstr(Integer taskId) {
		try {
			wfwInboxMstrDto.deleteWfInboxMstr(taskId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error:deleteWfInboxMstr ", e);
			return false;
		}
	}


	@Override
	public List<WfwInboxMstr> searchWfInboxMstr(WfwInboxMstr wfwInboxMstr) {
		try {
			return wfwInboxMstrDto.searchWfInboxMstr(wfwInboxMstr);
		} catch (Exception e) {
			LOGGER.error("Error:searchWfInboxMstr ", e);
			return Collections.emptyList();
		}
	}


	@Override
	public List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId) {
		return findWfInboxMstrQueueByModRole(roleId, modId, CmnConstants.EMPTY_STRING);
	}


	@Override
	public List<WfwInboxMstr> findWfInboxMstrQueueByModRole(String roleId, String modId, String refNo) {
		return wfwInboxMstrDto.findWfInboxMstrQueueByModRole(roleId, modId, refNo);
	}


	private boolean createTask(TaskDetails taskDetails, String actionDoneBy) {
		String operationCode = CmnConstants.EMPTY_STRING;

		WfwInboxMstr inboxMstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskDetails.getRefNo());
		if (!BaseUtil.isObjNull(inboxMstr)) {
			wfwInboxMstrDto.deleteWfwInboxMstrByRefNo(taskDetails.getRefNo());
		}

		// make workflow master
		WfwInboxMstr mstr = new WfwInboxMstr();
		if (CmnUtil.isObjNull(taskDetails.getTaskId())) {
			mstr.setTaskId(SeqGen.getSequenceNo());
		} else {
			mstr.setTaskId(taskDetails.getTaskId());
		}
		mstr.setRaProfileId(taskDetails.getRaProfileId());
		mstr.setRaCmpnyRegNo(taskDetails.getRaCmpnyRegNo());
		mstr.setRefNo(taskDetails.getRefNo());
		mstr.setApplUserId(actionDoneBy);
		mstr.setApplUserName(taskDetails.getApplName());
		mstr.setApplDate(taskDetails.getApplDate());
		mstr.setApplStsCode(taskDetails.getApplStsCode());
		mstr.setCompanyName(taskDetails.getCompanyName());
		mstr.setCompanyRegNo(taskDetails.getCmpnyRegNo());
		if (!BaseUtil.isObjNull(taskDetails.getRecruitmentAgent())
				&& !BaseUtil.isListNull(taskDetails.getRecruitmentAgent())) {
			String[] agentProfile = new String[taskDetails.getRecruitmentAgent().size()];
			agentProfile = taskDetails.getRecruitmentAgent().toArray(agentProfile);
			mstr.setRecruitmentAgent(agentProfile);
		}
		mstr.setCountry(taskDetails.getCountry());
		mstr.setIsDisplay("1");
		mstr.setAttemptCnt(taskDetails.getAttemptCount());
		mstr.setSector(taskDetails.getSector());
		mstr.setApplRemark(taskDetails.getRemarks());
		mstr.setBranchId(taskDetails.getBranchCode());
		if (taskDetails.getNoOfWorkers() > 0) {
			mstr.setNoOfWorkers(taskDetails.getNoOfWorkers());
		}

		if (!CmnUtil.isObjNull(taskDetails.getExpiryDate())) {
			mstr.setExpiryDate(taskDetails.getExpiryDate());
		}

		String currAppStatus = mstr.getApplStsCode();

		WfwAsgnTran trans = wfwAsgnTranDto.findAsgnTranByTranId(taskDetails.getTransId());
		if (!CmnUtil.isObjNull(trans)) {
			mstr.setModuleId(trans.getModuleId());
			mstr.setTranId(trans.getTranId());
			WfwType wfType = wfwTypeDto.findByWfTypeId(trans.getTypeId());

			if (!CmnUtil.isObjNull(wfType)) {
				List<WfwLevel> levelList = wfwLevelDto.findWfLevelByTypeId(wfType.getTypeId());
				if (!CmnUtil.isListNull(levelList)) {
					mstr.setLevelId(levelList.get(0).getLevelId());
					List<WfwStatus> statusList = wfwStatusDto.findWfStatusByLevelId(levelList.get(0).getLevelId());
					if (!CmnUtil.isListNull(statusList)) {
						mstr.setStatusId(statusList.get(0).getStatusId());
						mstr.setApplStsId(statusList.get(0).getApplStsId());
						mstr.setLastStatusId(CmnConstants.DIGIT_0);
						mstr.setLastStatusDesc(CmnConstants.EMPTY_STRING);
					}
				}

				if (CmnUtil.isEquals(CmnUtil.getStr(wfType.getAssignType()), CmnConstants.ASSGN_TYPE_MANUAL)) {
					mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
					mstr.setOfficerId(CmnConstants.EMPTY_STRING);
					mstr.setOfficerName(CmnConstants.EMPTY_STRING);
					operationCode = "101";
				} else {
					String officerId = findOfficerIdWithMinimumWorkLoad(mstr.getLevelId());
					LOGGER.info("officerId = {}", officerId);
					if (!CmnUtil.isObjNull(officerId)) {
						mstr.setQueueInd(CmnConstants.QUEUE_IND_INBOXED);
						mstr.setOfficerId(officerId);
						mstr.setOfficerName(officerId);
						operationCode = "111";
					} else {
						mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
						mstr.setOfficerId(CmnConstants.EMPTY_STRING);
						mstr.setOfficerName(CmnConstants.EMPTY_STRING);
						operationCode = "101";
					}
				}
			}
		}
		WfwInbox inbox = new WfwInbox();
		BeanUtil.copyProperties(mstr, inbox);
		WfwInboxHist history = new WfwInboxHist();
		BeanUtil.copyProperties(mstr, history);
		history.setApplStsCodePrev(currAppStatus);
		return crudWfTask(mstr, inbox, history, operationCode, actionDoneBy);
	}


	private boolean createTaskAmend(TaskDetails taskDetails, String actionDoneBy) {
		WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskDetails.getTaskId());

		// make workflow master
		if (!CmnUtil.isObjNull(mstr)) {
			WfwInboxMstr mstrOriginal = new WfwInboxMstr();
			BeanUtil.copyProperties(mstr, mstrOriginal);

			mstr.setTaskId(taskDetails.getRefNo());
			mstr.setRefNo(taskDetails.getRefNo());
			mstr.setApplRemark(taskDetails.getRemarks());
			mstr.setOfficerId(actionDoneBy);
			if (taskDetails.getNoOfWorkers() > 0) {
				mstr.setNoOfWorkers(taskDetails.getNoOfWorkers());
			}

			if (!CmnUtil.isObjNull(taskDetails.getActionByFullName())) {
				mstr.setOfficerName(taskDetails.getActionByFullName());
			}

			if (!CmnUtil.isObjNull(taskDetails.getApplStsCode())) {
				mstr.setApplStsCode(taskDetails.getApplStsCode());
			}

			WfwInbox inbox = new WfwInbox();
			BeanUtil.copyProperties(mstr, inbox);
			WfwInboxHist history = new WfwInboxHist();
			BeanUtil.copyProperties(mstr, history);

			String oppertionCode = "101";

			mstr.setOfficerId(CmnConstants.EMPTY_STRING);

			// set workflow level
			WfwStatus status = wfwStatusDto.findWfStatusByLevelIdAppStatus(CmnUtil.getStr(mstr.getLevelId()),
					WorkflowConstants.AMEND_STS_CODE);
			if (!CmnUtil.isObjNull(status)) {
				boolean isCreatedAmendTask = crudWfTask(mstr, inbox, history, oppertionCode, actionDoneBy);
				LOGGER.info("isCreatedAmendTask = {}", isCreatedAmendTask);
				if (isCreatedAmendTask) {
					mstr.setLevelId(status.getNextProcId());
					WfwStatus statusNext = wfwStatusDto.findFirstStatusByLevelId(status.getNextProcId());
					if (!CmnUtil.isObjNull(statusNext)) {
						mstr.setStatusId(statusNext.getStatusId());
						mstr.setLastStatusId(mstr.getStatusId());
						mstr.setLastStatusDesc(WorkflowConstants.AMEND_STS_CODE);
						WfwInboxHist historyAmnd = new WfwInboxHist();
						BeanUtil.copyProperties(mstr, historyAmnd);
						boolean isLevelSet = crudWfTask(mstr, null, historyAmnd, "201", actionDoneBy);
						LOGGER.info("isLevelSet for amend task={}", isLevelSet);
						// update orginal record
						if (isLevelSet) {
							mstrOriginal.setLevelId(CmnConstants.EMPTY_STRING);
							mstrOriginal.setApplStsCode(CmnConstants.APP_AMENDED);
							WfwInboxHist historyAmndOrginal = new WfwInboxHist();
							BeanUtil.copyProperties(mstrOriginal, historyAmndOrginal);
							boolean isupdatedOrginal = crudWfTask(mstrOriginal, null, historyAmndOrginal, "201",
									actionDoneBy);
							LOGGER.info("isUpdatedOrginal for amend task={}", isupdatedOrginal);
							return isupdatedOrginal;
						}
						return false;
					} else {
						LOGGER.info("WFW_STATUS not found with level : {}", status.getNextProcId());
						return false;
					}
				} else {
					LOGGER.info("Amend Task creation fail.");
					return false;
				}
			} else {
				LOGGER.info("WFW_STATUS not found with status code : {}  at level {}",
						WorkflowConstants.AMEND_STS_CODE, mstr.getLevelId());
				return false;
			}

		} else {
			LOGGER.info("WfwInboxMstr not found for task ID: {}", taskDetails.getTaskId());
			return false;
		}

	}


	@Override
	@Transactional
	public boolean updateWfInboxMstrByWfAdmin(WfwInboxMstr wfwInboxMstr, String actionDoneBy) {
		WfwStatus wfStatus = wfwStatusDto.findFirstStatusByLevelId(CmnUtil.getStr(wfwInboxMstr.getLevelId()));
		if (!CmnUtil.isObjNull(wfStatus)) {
			wfwInboxMstr.setStatusId(wfStatus.getStatusId());
			wfwInboxMstr.setLastStatusId(wfStatus.getStatusId());
			wfwInboxMstr.setLastStatusDesc(wfStatus.getApplStsId());
		}

		String oppertionCode;
		String currAppStatus = wfwInboxMstr.getApplStsCode();

		WfwInboxHist history = new WfwInboxHist();
		BeanUtil.copyProperties(wfwInboxMstr, history);
		history.setApplStsCodePrev(currAppStatus);

		WfwInbox inbox;
		if (CmnUtil.isEquals(wfwInboxMstr.getQueueInd(), CmnConstants.QUEUE_IND_QUEUED)) {
			inbox = findWfInboxByTaskId(CmnUtil.getStr(wfwInboxMstr.getTaskId()));
			wfwInboxMstr.setOfficerId(CmnConstants.EMPTY_STRING);
			wfwInboxMstr.setOfficerName(CmnConstants.EMPTY_STRING);

			if (CmnUtil.isObjNull(inbox)) {
				oppertionCode = "201";
			} else {
				oppertionCode = "231";
			}
		} else {
			inbox = findWfInboxByTaskId(CmnUtil.getStr(wfwInboxMstr.getTaskId()));
			if (CmnUtil.isObjNull(inbox)) {
				inbox = new WfwInbox();
				BeanUtil.copyProperties(wfwInboxMstr, inbox);
				oppertionCode = "211";
			} else {
				inbox.setOfficerId(wfwInboxMstr.getOfficerId());
				oppertionCode = "221";
			}
		}

		return crudWfTask(wfwInboxMstr, inbox, history, oppertionCode, actionDoneBy);
	}


	private boolean completeCurrentTask(TaskDetails taskDetails, String actionDoneBy, String actionDoneByName) {
		WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskDetails.getRefNo());
		WfwStatus wfStatus = null;
		String currAppStatus = CmnConstants.EMPTY_STRING;
		// if send application status as workflow status from end point
		if (!CmnUtil.isObjNull(mstr)) {
			String levelId = CmnUtil.getStr(mstr.getLevelId());
			LOGGER.info("Workflow Level Id : {}  application status Id : {}", levelId, taskDetails.getWfStatus());
			wfStatus = wfwStatusDto.findWfStatusByLevelIdAppStatus(levelId, taskDetails.getWfStatus());
		}

		// if send workflow status from end point
		if (CmnUtil.isObjNull(wfStatus)) {
			wfStatus = wfwStatusDto.findStatusByStatusId(mstr.getStatusId());
		}

		WfwInbox inbox = null;

		// if call from scheduler
		if (CmnUtil.isEqualsCaseIgnore(actionDoneBy, WorkflowConstants.SYSTEM_USER_NAME)) {
			List<WfwInbox> inboxList = wfwInboxDto.findWfInboxByRefNo(taskDetails.getRefNo());
			if (!CmnUtil.isListNull(inboxList)) {
				inbox = inboxList.get(0);
			}
		} else {
			if (BaseUtil.isEqualsCaseIgnore(wfStatus.getIsSkipApprvr(), "Y")) { // added
																	// Nigel
				// 20170118
				List<WfwInbox> inboxList = wfwInboxDto.findWfInboxByRefNo(taskDetails.getRefNo());
				if (!CmnUtil.isListNull(inboxList)) {
					inbox = inboxList.get(0);
				}
			} else {
				inbox = wfwInboxDto.findWfInboxByRefNoAndOfficerId(taskDetails.getRefNo(), actionDoneBy);
			}
		}

		LOGGER.info("is master null ={}", CmnUtil.isObjNull(mstr));
		LOGGER.info("is inbox null ={}", CmnUtil.isObjNull(inbox));
		LOGGER.info("is wfStatus null ={}", CmnUtil.isObjNull(wfStatus));
		LOGGER.info("is actionDoneBy null ={}", CmnUtil.isObjNull(actionDoneBy));

		String oppertionCode = CmnConstants.EMPTY_STRING;

		if (!CmnUtil.isObjNull(mstr) && !CmnUtil.isObjNull(inbox) && inbox != null && !CmnUtil.isObjNull(wfStatus)
				&& !CmnUtil.isObjNull(actionDoneBy)) {
			currAppStatus = mstr.getApplStsCode();
			mstr.setApplRemark(taskDetails.getRemarks());
			mstr.setIsDisplay("1");

			if (!CmnUtil.isObjNull(taskDetails.getApplStsCode())) {
				mstr.setApplStsCode(taskDetails.getApplStsCode());
			}

			if (taskDetails.getAttemptCount() > 0) {
				mstr.setAttemptCnt(taskDetails.getAttemptCount());
			}

			if (taskDetails.getNoOfWorkers() > 0) {
				mstr.setNoOfWorkers(taskDetails.getNoOfWorkers());
			}

			if (!CmnUtil.isObjNull(taskDetails.getApproveDate())) {
				mstr.setApproveDate(taskDetails.getApproveDate());
			}

			if (!CmnUtil.isObjNull(taskDetails.getInterviewDate())) {
				mstr.setInterviewDate(taskDetails.getInterviewDate());
			}

			if (!CmnUtil.isObjNull(wfStatus)) {

				mstr.setLastStatusId(wfStatus.getStatusId());
				mstr.setLastStatusDesc(wfStatus.getApplStsId());
				mstr.setLevelId(wfStatus.getNextProcId());
				WfwStatus wfStatusNext = wfwStatusDto.findFirstStatusByLevelId(wfStatus.getNextProcId());
				if (!CmnUtil.isObjNull(wfStatusNext)) {
					mstr.setStatusId(wfStatusNext.getStatusId());
					mstr.setApplStsId(wfStatusNext.getApplStsId());
				} else {
					mstr.setStatusId(wfStatus.getStatusId());
					mstr.setApplStsId(wfStatus.getApplStsId());
				}

				WfwType wfType = wfwTypeDto.findByWfTypeId(wfStatus.getTypeId());
				if (!CmnUtil.isObjNull(wfType)) {
					if (CmnUtil.isEquals(wfType.getAssignType(), CmnConstants.ASSGN_TYPE_MANUAL)) {
						// in queue ind =I then all except hicommision and
						// bmet
						if (CmnUtil.isEquals(BaseUtil.getStr(mstr.getQueueInd()),
								CmnConstants.QUEUE_IND_INBOXED)) {
							mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
						} else {
							// if other than I then hicommission and
							// bment no need to change the
							// queue status.
						}
						mstr.setOfficerId(CmnConstants.EMPTY_STRING);
						mstr.setOfficerName(CmnConstants.EMPTY_STRING);
						oppertionCode = "231"; // update task, delete
											// inbox, create history
					} else {
						// code for auto here
						if (CmnUtil.isEquals(CmnUtil.getStr(mstr.getLevelId()), CmnConstants.EMPTY_STRING)
								|| CmnUtil.isEquals(CmnUtil.getStr(mstr.getLevelId()), CmnConstants.DIGIT_0)) {
							mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
							mstr.setOfficerId(CmnConstants.EMPTY_STRING);
							mstr.setOfficerName(CmnConstants.EMPTY_STRING);
							oppertionCode = "231"; // update task, delete
												// inbox, create
												// history
						} else {
							String officerId = findOfficerIdWithMinimumWorkLoad(mstr.getLevelId());
							LOGGER.info("officerId = {}", officerId);
							if (!CmnUtil.isObjNull(officerId)) {
								mstr.setQueueInd(CmnConstants.QUEUE_IND_INBOXED);
								mstr.setOfficerId(officerId);
								mstr.setOfficerName(officerId);
								inbox.setOfficerId(officerId);
								oppertionCode = "221"; // update task,
													// update
													// inbox,
													// create
													// history
							} else {
								mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
								mstr.setOfficerId(CmnConstants.EMPTY_STRING);
								mstr.setOfficerName(CmnConstants.EMPTY_STRING);
								oppertionCode = "231"; // update task,
													// delete
													// inbox,
													// create
													// history
							}
						}
					}

					// if current level and next level same and query
					// status I then don't release
					// the task ->
					// KIV- no release : Queue status I
					// Hicom and bemet queue status : QH,QB or QHB then
					// release
					if (CmnUtil.isEquals(wfStatus.getNextProcId(), wfStatus.getLevelId()) && CmnUtil
							.isEquals(CmnUtil.getStr(mstr.getQueueInd()), CmnConstants.QUEUE_IND_INBOXED)) {
						mstr.setQueueInd(CmnConstants.QUEUE_IND_INBOXED);
						mstr.setOfficerId(actionDoneBy);
						mstr.setOfficerName(actionDoneByName);
						inbox.setOfficerId(actionDoneBy);
						oppertionCode = "221";
					}
				}

			}
		}
		WfwInboxHist history = new WfwInboxHist();
		BeanUtil.copyProperties(mstr, history);
		history.setApplStsCodePrev(currAppStatus);
		history.setOfficerName(actionDoneByName);
		return crudWfTask(mstr, inbox, history, oppertionCode, actionDoneBy);
	}


	// Task RELEASE

	@Override
	@Transactional
	public boolean releaseTask(String taskId, String actionDoneBy, String userType) {
		WfwInboxMstr mstr = findWfInboxMstrByRefNo(taskId);
		WfwInbox inbox = findWfInboxByRefNoAndOfficerId(taskId, actionDoneBy);
		String oppCode = CmnConstants.EMPTY_STRING;
		if (!CmnUtil.isObjNull(mstr) && !CmnUtil.isObjNull(inbox)) {
			String currAppStatus = mstr.getApplStsCode();
			WfwLevel wfLevel = findLevelByLevelId(CmnUtil.getStr(mstr.getLevelId()));
			if (!CmnUtil.isObjNull(wfLevel)) {
				WfwType wfType = findWfTypeById(CmnUtil.getStr(wfLevel.getTypeId()));
				if (!CmnUtil.isObjNull(wfType)) {
					String asgnType = CmnUtil.getStr(wfType.getAssignType());
					if (CmnUtil.isEquals(asgnType, CmnConstants.ASSGN_TYPE_MANUAL)) {
						mstr.setOfficerId(CmnConstants.EMPTY_STRING);
						mstr.setOfficerName(CmnConstants.EMPTY_STRING);
						mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
						oppCode = "231"; // update master, delete inbox,
										// insert history
					} else {
						String officerId = findOfficerIdWithMinimumWorkLoad(CmnUtil.getStr(mstr.getLevelId()),
								CmnUtil.getStr(inbox.getOfficerId()));
						mstr.setOfficerId(officerId);
						mstr.setOfficerName(officerId);
						inbox.setOfficerId(officerId);
					}
				}
			}
			WfwInboxHist history = new WfwInboxHist();
			BeanUtil.copyProperties(mstr, history);
			history.setApplRemark(CmnConstants.REMARK_RELEASE_TASK);
			history.setApplStsCodePrev(currAppStatus);
			return crudWfTask(mstr, inbox, history, oppCode, actionDoneBy);
		}
		return false;

	}


	private boolean releaseTaskGroupAdmin(String taskId, String taskAuther, String actionDoneBy, String userType) {
		WfwInboxMstr mstr = findWfInboxMstrByRefNo(taskId);
		WfwInbox inbox = findWfInboxByTaskIdAndOfficerId(taskId, taskAuther);
		LOGGER.debug("taskId: {} | taskAuther {} | userType {}", taskId, taskAuther, userType);
		if (!CmnUtil.isObjNull(mstr) && !CmnUtil.isObjNull(inbox)) {
			String currAppStatus = mstr.getApplStsCode();

			mstr.setOfficerId(CmnConstants.EMPTY_STRING);
			mstr.setOfficerName(CmnConstants.EMPTY_STRING);
			mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
			String oppCode = "231"; // update master, delete inbox, insert
								// history

			WfwInboxHist history = new WfwInboxHist();
			BeanUtil.copyProperties(mstr, history);
			history.setApplRemark(CmnConstants.REMARK_RELEASE_TASK);
			history.setApplStsCodePrev(currAppStatus);
			return crudWfTask(mstr, inbox, history, oppCode, actionDoneBy);
		}
		return false;

	}


	// WfLevel
	@Override
	public WfwLevel findLevelByLevelId(String levelId) {
		return wfwLevelDto.findLevelByLevelId(levelId);
	}


	@Override
	public List<WfwLevel> findAllWfLevel() {
		return wfwLevelDto.findAllWfLevel();
	}


	@Override
	public List<WfwLevel> findWfLevelByDescription(String description) {
		return wfwLevelDto.findWfLevelByDescription(description);
	}


	@Override
	public List<WfwLevel> findWfLevelByTypeId(String typeId) {
		return wfwLevelDto.findWfLevelByTypeId(typeId);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfLevel(WfwLevel wfwLevel) {
		return wfwLevelDto.addWfLevel(wfwLevel);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfLevel(WfwLevel wfwLevel) {
		return wfwLevelDto.editWfLevel(wfwLevel);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfLevel(WfwLevel wfwLevel) {
		return wfwLevelDto.deleteWfLevel(wfwLevel);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfLevel(Integer statusId) {
		return wfwLevelDto.deleteWfLevel(statusId);
	}


	// WfType

	@Override
	public List<WfwType> findAllTWfTypeByDecription(String description) {
		return wfwTypeDto.findByWfwTypeByDescription(description);
	}


	@Override
	public List<WfwType> findAllWfType() {
		return wfwTypeDto.findAllWfType();
	}


	@Override
	public WfwType findWfTypeById(String typeId) {
		return wfwTypeDto.findByWfTypeId(typeId);
	}


	@Override
	public List<WfwType> generateTypeList() {
		List<WfwType> typeList = findAllWfType();

		for (WfwType wfwType : typeList) {
			List<WfwLevel> levelList = findWfLevelByTypeId(wfwType.getTypeId());
			WfwAsgnTran assignTran = findWfAsgnTranByTypeId(wfwType.getTypeId());
			wfwType.setWfwLevelList(levelList);
			if (!CmnUtil.isObjNull(assignTran)) {
				wfwType.setTranId(CmnUtil.getStr(assignTran.getTranId()));
				wfwType.setModuleId(CmnUtil.getStr(assignTran.getModuleId()));
				wfwType.setViewPath(CmnUtil.getStr(assignTran.getViewPath()));
			}
			for (WfwLevel wfwLevel : levelList) {
				List<WfwStatus> statusList = findWfStatusByLevelId(wfwLevel.getLevelId());
				for (WfwStatus wfwStatus : statusList) {
					WfwLevel nextLevel = findLevelByLevelId(CmnUtil.getStr(wfwStatus.getNextProcId()));
					if (!CmnUtil.isObjNull(nextLevel)) {
						wfwStatus.setNextProcId(nextLevel.getDescription());
					}
				}
				wfwLevel.setWfwStatusList(statusList);
				List<WfwAsgnRole> roles = findWfAsgnRoleByLevelId(wfwLevel.getLevelId());
				StringBuilder roleStr = new StringBuilder();
				int count = 0;
				for (WfwAsgnRole wfwAsgnRole : roles) {
					if (count == 0) {
						roleStr.append(CmnUtil.getStr(wfwAsgnRole.getWfwAsgnRolePk().getRoleId()));
					} else {
						roleStr.append(",").append(CmnUtil.getStr(wfwAsgnRole.getWfwAsgnRolePk().getRoleId()));
					}
					count++;
				}
				wfwLevel.setRoles(roleStr.toString());
			}
		}

		return typeList;
	}


	@Override
	public List<WfwType> generateTypeList(String typeId) {

		List<WfwType> typeList = new ArrayList<>();

		WfwType wfwType = findWfTypeById(typeId);

		if (!CmnUtil.isObjNull(wfwType)) {
			typeList.add(wfwType);
			List<WfwLevel> levelList = findWfLevelByTypeId(wfwType.getTypeId());
			WfwAsgnTran assignTran = findWfAsgnTranByTypeId(wfwType.getTypeId());
			wfwType.setWfwLevelList(levelList);
			if (!CmnUtil.isObjNull(assignTran)) {
				wfwType.setTranId(CmnUtil.getStr(assignTran.getTranId()));
				wfwType.setModuleId(CmnUtil.getStr(assignTran.getModuleId()));
				wfwType.setViewPath(CmnUtil.getStr(assignTran.getViewPath()));
			}
			for (WfwLevel wfwLevel : levelList) {
				List<WfwStatus> statusList = findAllWfStatusByLevelId(wfwLevel.getLevelId());
				for (WfwStatus wfwStatus : statusList) {
					WfwLevel nextLevel = findLevelByLevelId(CmnUtil.getStr(wfwStatus.getNextProcId()));
					if (!CmnUtil.isObjNull(nextLevel)) {
						wfwStatus.setNextProcId(nextLevel.getDescription());
					}
				}
				wfwLevel.setWfwStatusList(statusList);
				List<WfwAsgnRole> roles = findWfAsgnRoleByLevelId(wfwLevel.getLevelId());
				StringBuilder roleStr = new StringBuilder();
				int count = 0;
				for (WfwAsgnRole wfwAsgnRole : roles) {
					if (count == 0) {
						roleStr.append(CmnUtil.getStr(wfwAsgnRole.getWfwAsgnRolePk().getRoleId()));
					} else {
						roleStr.append(",").append(CmnUtil.getStr(wfwAsgnRole.getWfwAsgnRolePk().getRoleId()));
					}
					count++;
				}
				wfwLevel.setRoles(roleStr.toString());
			}
		}

		return typeList;
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfType(WfwType wfwType) {
		return wfwTypeDto.addWfType(wfwType);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfType(WfwType wfwType) {
		return wfwTypeDto.editWfType(wfwType);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfType(WfwType wfwType) {
		return wfwTypeDto.deleteWfType(wfwType);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfType(Integer typeId) {
		return wfwTypeDto.deleteWfType(typeId);
	}


	// WfStatus
	@Override
	public WfwStatus findStatusByStatusId(String statusId) {
		return wfwStatusDto.findStatusByStatusId(statusId);
	}


	@Override
	public List<WfwStatus> findAllWfStatus() {
		return wfwStatusDto.findAllWfStatus();
	}


	@Override
	public List<WfwStatus> findWfStatusByDescription(String description) {
		return wfwStatusDto.findWfStatusByDescription(description);
	}


	@Override
	public List<WfwStatus> findWfStatusByLevelId(String levelId) {
		return wfwStatusDto.findWfStatusByLevelId(levelId);
	}


	@Override
	public List<WfwStatus> findAllWfStatusByLevelId(String levelId) {
		return wfwStatusDto.findAllWfStatusByLevelId(levelId);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfStatus(WfwStatus wfwStatus) {
		return wfwStatusDto.addWfStatus(wfwStatus);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfStatus(WfwStatus wfwStatus) {
		return wfwStatusDto.editWfStatus(wfwStatus);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfStatus(WfwStatus wfwStatus) {
		return wfwStatusDto.deleteWfStatus(wfwStatus);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfStatusByStatusId(Integer statusId) {
		return wfwStatusDto.deleteWfStatusById(statusId);
	}


	// WfUser

	@Override
	public WfwUser findWfUserByUsernamePassword(String username, String password) {
		return wfwUserDto.findWfUserByUsernamePassword(username, password);
	}


	@Override
	public WfwUser findWfUserByUsername(String username) {
		return wfwUserDto.findWfUserByUsername(username);
	}


	@Override
	public List<WfwUser> findAllWfUser() {
		return wfwUserDto.findAllWfUser();
	}


	@Override
	public WfwUser findUserByUserId(Integer userId) {
		return wfwUserDto.findUserByUserId(userId);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean addWfUser(WfwUser wfwUser) {
		return wfwUserDto.addWfUser(wfwUser);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean editWfUser(WfwUser wfwUser) {
		return wfwUserDto.editWfUser(wfwUser);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfUser(WfwUser wfwUser) {
		return wfwUserDto.deleteWfUser(wfwUser);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean deleteWfUser(Integer userId) {
		return wfwUserDto.deleteWfUser(userId);
	}


	// method related to service

	@Override
	@Transactional
	public int claimRandomTask(WfwPayload wfwPayload) {

		wfwPayload.setIntvwDate(new Date());
		wfwPayload.setApplStatusCode("claimRandom");

		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchTaskListInInbox(wfwPayload);
		List<WfwInboxMstr> pendingWfwInboxMstrList = new ArrayList<>();

		LOGGER.info("Officer ID: {} No of total application for today : {}", wfwPayload.getTaskAuthorId(),
				wfwInboxMstrList.size());

		if (!CmnUtil.isListNull(wfwInboxMstrList)) {
			for (WfwInboxMstr mstr : wfwInboxMstrList) {
				WfwStatus status = wfwStatusDto.findStatusByStatusId(mstr.getLastStatusId());
				if (!CmnUtil.isObjNull(status) && !CmnUtil.isEqualsCaseIgnore(status.getApplStsId(), "KIV")) {
					pendingWfwInboxMstrList.add(mstr);
				} else {
					LOGGER.info("task {} is KIV", mstr.getTaskId());
				}
			}
		}

		LOGGER.info("Officer ID: {} No of pending application for today : {}", wfwPayload.getTaskAuthorId(),
				pendingWfwInboxMstrList.size());

		// No of pending application for today is zero, need to claim 1/2/3
		// random application
		if (pendingWfwInboxMstrList.isEmpty()) {
			List<WfwInboxMstr> queueList = wfwInboxMstrDto.searchTaskListInQueue(wfwPayload);
			wfwPayload.setApplStatusCode(null);
			LOGGER.info(" No of pending application for today's interview : {} interview date : {}",
					queueList.size(), wfwPayload.getIntvwDate());

			if (!CmnUtil.isListNull(queueList)) {
				int noOfAppTobeClaimed = 1;

				if (queueList.size() < 3) {
					noOfAppTobeClaimed = rand.nextInt(queueList.size()) + 1;
				} else {
					noOfAppTobeClaimed = rand.nextInt(3) + 1;
				}

				LOGGER.info("noOfAppTobeClaim ={}", noOfAppTobeClaimed);

				List<String> taskIds = new ArrayList<>();
				for (int i = 0; i < noOfAppTobeClaimed; i++) {
					WfwInboxMstr wfwInboxMstr = queueList.get(i);
					if (!CmnUtil.isObjNull(wfwInboxMstr)) {
						taskIds.add(wfwInboxMstr.getTaskId());
					}
				}

				boolean isClaimed = claimTask(taskIds, wfwPayload.getTaskAuthorId(),
						CmnUtil.getStr(wfwPayload.getTaskAuthorName()), CmnConstants.EMPTY_STRING);
				if (isClaimed) {
					LOGGER.info("[{}] application(s) has claimed by system for officer [{}]", taskIds.size(),
							wfwPayload.getTaskAuthorId());
				}
				return noOfAppTobeClaimed;
			}
		} else {
			return 0;
		}

		return 0;
	}


	@Override
	public List<TaskDetails> taskList(WfwPayload wfwPayload) {

		List<TaskDetails> taskDetailList = new ArrayList<>();
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchTaskListInQueue(wfwPayload);

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}
		return taskDetailList;
	}


	@Override
	public List<TaskDetails> applicationList(WfwPayload wfwPayload) {

		List<TaskDetails> taskDetailList = new ArrayList<>();
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchTaskListInQueueAll(wfwPayload);

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}

		return taskDetailList;
	}


	private TaskDetails transformMstrToTaskDetails(WfwInboxMstr wfwInboxMstr, String isQuotaSec) {
		TaskDetails taskDetails = new TaskDetails();
		if (!CmnUtil.isObjNull(wfwInboxMstr)) {
			LOGGER.debug("wfwInboxMstr.getTaskId = {} | isQuotaSec = {}", wfwInboxMstr.getTaskId(), isQuotaSec);
			taskDetails.setTaskId(wfwInboxMstr.getTaskId());
			taskDetails.setRefNo(wfwInboxMstr.getRefNo());
			taskDetails.setApplUserId(wfwInboxMstr.getApplUserId());
			taskDetails.setApplName(wfwInboxMstr.getApplUserName());
			taskDetails.setApplStatus(wfwInboxMstr.getApplStsId());
			taskDetails.setApplStsCode(wfwInboxMstr.getApplStsCode());
			taskDetails.setApplDate(wfwInboxMstr.getApplDate());
			taskDetails.setCompanyName(wfwInboxMstr.getCompanyName());
			taskDetails.setCmpnyRegNo(wfwInboxMstr.getCompanyRegNo());
			if (!BaseUtil.isObjNull(wfwInboxMstr.getRecruitmentAgent())) {
				taskDetails.setRecruitmentAgent(Arrays.asList(wfwInboxMstr.getRecruitmentAgent()));
			}
			taskDetails.setCountry(wfwInboxMstr.getCountry());
			taskDetails.setNoOfWorkers(wfwInboxMstr.getNoOfWorkers());
			taskDetails.setApproveDate(wfwInboxMstr.getApproveDate());
			taskDetails.setExpiryDate(wfwInboxMstr.getExpiryDate());
			taskDetails.setInterviewDate(wfwInboxMstr.getInterviewDate());
			taskDetails.setAttemptCount(wfwInboxMstr.getAttemptCnt());
			taskDetails.setModuleId(wfwInboxMstr.getModuleId());
			taskDetails.setSector(wfwInboxMstr.getSector());
			taskDetails.setRemarks(wfwInboxMstr.getApplRemark());
			taskDetails.setTaskClaimBy(wfwInboxMstr.getOfficerId());
			taskDetails.setActionByFullName(wfwInboxMstr.getOfficerName());
			taskDetails.setQueueInd(wfwInboxMstr.getQueueInd());
			taskDetails.setTransId(wfwInboxMstr.getTranId());
			taskDetails.setUpdateId(wfwInboxMstr.getUpdateId());
			taskDetails.setRaCmpnyRegNo(wfwInboxMstr.getRaCmpnyRegNo());// Yell
			taskDetails.setClaimedDate(wfwInboxMstr.getClaimedDate());
		}

		return taskDetails;

	}


	@Override
	public List<TaskDetails> claimTaskList(WfwPayload wfwPayload) {

		List<TaskDetails> taskDetailList = new ArrayList<>();
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchTaskListInInbox(wfwPayload);

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}

		return taskDetailList;
	}


	@Override
	public List<TaskDetails> claimTaskListGroupAdmin(WfwPayload wfwPayload) {

		List<TaskDetails> taskDetailList = new ArrayList<>();
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchTaskListInInboxGroupAdmin(wfwPayload);

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}

		return taskDetailList;
	}


	@Override
	public List<TaskRemark> taskRemarkList(WfwPayload wfwPayload, String range) {

		List<WfwInboxHist> wfwHistList = wfwInboxMstrDto.searchTaskHistoryList(wfwPayload);

		List<TaskRemark> taskRemarkList = new ArrayList<>();

		if (CmnUtil.isEqualsCaseIgnore(range, WorkflowConstants.REMARK_RANGE_ALL)) {
			for (WfwInboxHist wfwInboxHist : wfwHistList) {
				taskRemarkList.add(transfromHitoryToTaskRemark(wfwInboxHist));
			}
		} else if (CmnUtil.isEqualsCaseIgnore(range, WorkflowConstants.REMARK_RANGE_LATEST)) {
			WfwInboxHist latestInboxHist = null;
			for (WfwInboxHist wfwInboxHist : wfwHistList) {
				if (!wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_CLAIM_TASK)
						&& !wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_RELEASE_TASK)
						&& !wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_UPDATE_TASK)) {
					latestInboxHist = wfwInboxHist;
				}
			}

			if (!CmnUtil.isObjNull(latestInboxHist)) {
				taskRemarkList.add(transfromHitoryToTaskRemark(latestInboxHist));
			}
		} else if (CmnUtil.isEqualsCaseIgnore(range, WorkflowConstants.REMARK_RANGE_OFFICER)) {
			for (WfwInboxHist wfwInboxHist : wfwHistList) {
				if (!wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_CLAIM_TASK)
						&& !wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_RELEASE_TASK)
						&& !wfwInboxHist.getApplRemark().equalsIgnoreCase(CmnConstants.REMARK_UPDATE_TASK)) {
					taskRemarkList.add(transfromHitoryToTaskRemark(wfwInboxHist));
				}
			}
		}

		return taskRemarkList;
	}


	private TaskRemark transfromHitoryToTaskRemark(WfwInboxHist history) {
		TaskRemark taskRemark = new TaskRemark();
		if (history != null) {
			taskRemark.setTaskId(history.getTaskId());
			taskRemark.setRefNo(history.getRefNo());
			taskRemark.setApplStsCode(history.getApplStsCodePrev());
			taskRemark.setApplStsCodeNext(history.getApplStsCode());
			taskRemark.setUserAction(CmnUtil.getStr(history.getLastStatusDesc()));
			taskRemark.setRemarkBy(history.getUpdateId());
			taskRemark.setRemarkByFullName(history.getOfficerName());
			taskRemark.setRemarks(
					CmnUtil.getStr(history.getApplRemark()).replaceAll("~~~", CmnConstants.EMPTY_STRING));
			taskRemark.setRemarkTime(history.getCreateDt());
		}
		return taskRemark;
	}


	@Override
	public List<TaskDetails> appListbyUserActionStatus(String userAction, Date actionDateFrom, Date actionDateTo,
			String modules, String appStatuses) {
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.searchByUserActionUpdateDateStatus(userAction,
				actionDateFrom, actionDateTo, modules, appStatuses);
		List<TaskDetails> taskDetailList = new ArrayList<>();
		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, null));
		}
		return taskDetailList;
	}


	@Override
	public List<TaskDetails> draftApplicationList(String draftStatus, Date durationDates) {
		List<WfwInboxMstr> wfwInboxMstrList = wfwInboxMstrDto.draftApplicationsList(draftStatus, durationDates);
		List<TaskDetails> taskDetailList = new ArrayList<>();
		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrList) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, null));
		}
		return taskDetailList;
	}


	@Override
	public TaskDetails taskDetails(String taskId) {

		WfwInboxMstr wfwInboxMstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskId);

		TaskDetails taskDetails = null;
		if (!CmnUtil.isObjNull(wfwInboxMstr)) {
			taskDetails = transformMstrToTaskDetails(wfwInboxMstr, null);

			List<TaskStatus> statusList = new ArrayList<>();
			List<WfwStatus> wfStatusList = wfwStatusDto.findWfStatusByLevelIdDisplayYes(wfwInboxMstr.getLevelId());
			for (WfwStatus wfwStatus : wfStatusList) {
				TaskStatus status = new TaskStatus();
				status.setStatusId(wfwStatus.getStatusId());
				status.setDescription(wfwStatus.getDescription());
				status.setApplStatus(wfwStatus.getApplStsId());
				statusList.add(status);
			}

			if (!CmnUtil.isListNull(statusList)) {
				taskDetails.setStatuses(statusList);
			}

			// remarks
			WfwPayload wfwPayload = new WfwPayload();
			wfwPayload.setRefNo(taskId);
			List<TaskRemark> remarkList = taskRemarkList(wfwPayload, WorkflowConstants.REMARK_RANGE_OFFICER);
			if (!CmnUtil.isListNull(remarkList)) {
				taskDetails.setRemarkList(remarkList);
			}
		}

		return taskDetails;
	}


	// Claimed Date

	@Override
	public TaskDetails taskDetail(String taskId) {
		return null;
	}


	@Override
	public TaskDetails taskDetail1(String taskId) {
		return null;
	}


	@Override
	public List<TaskStatus> statusList(String taskId) {
		WfwInboxMstr inboxMstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(taskId);

		List<TaskStatus> statusList = new ArrayList<>();
		if (!CmnUtil.isObjNull(inboxMstr)) {
			List<WfwStatus> wfStatusList = wfwStatusDto.findWfStatusByLevelIdDisplayYes(inboxMstr.getLevelId());
			for (WfwStatus wfwStatus : wfStatusList) {
				TaskStatus status = new TaskStatus();
				status.setStatusId(wfwStatus.getStatusId());
				status.setDescription(wfwStatus.getDescription());
				status.setApplStatus(wfwStatus.getApplStsId());
				statusList.add(status);
			}
		}
		return statusList;
	}


	@Override
	@Transactional
	public boolean claimTasks(WfwPayload wfwPayload) {
		String officerId = CmnUtil.getStr(wfwPayload.getTaskAuthorId());
		if (CmnUtil.isObjNull(officerId)) {
			return false;
		} else {
			List<String> taskIds = new ArrayList<>();

			if (!CmnUtil.isObjNull(wfwPayload.getTaskIds())) {
				taskIds = Arrays.asList(wfwPayload.getTaskIds().split(TASKID_SPLIT_PATTERN));
				if (!CmnUtil.isListNull(taskIds)) {
					taskIds.removeAll(Collections.singleton(null));
					taskIds.removeAll(Collections.singleton(""));
					LOGGER.info("taskIds = {}", taskIds);
				}
			}

			if (!CmnUtil.isListNull(taskIds)) {
				if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
					return claimTask(taskIds, officerId, CmnUtil.getStr(wfwPayload.getTaskAuthorName()),
							CmnUtil.getStr(wfwPayload.getUserType()));
				} else {
					return claimTask(taskIds, officerId, CmnUtil.getStr(wfwPayload.getTaskAuthorName()),
							CmnConstants.EMPTY_STRING);
				}
			} else {
				return false;
			}
		}
	}


	@Override
	public boolean realeaseTasks(WfwPayload wfwPayload) {
		String officerId = CmnUtil.getStr(wfwPayload.getTaskAuthorId());
		if (CmnUtil.isObjNull(officerId)) {
			return false;
		} else {
			List<String> taskIds = new ArrayList<>();

			if (!CmnUtil.isObjNull(wfwPayload.getTaskIds())) {
				taskIds = Arrays.asList(wfwPayload.getTaskIds().split(TASKID_SPLIT_PATTERN));
				if (!CmnUtil.isListNull(taskIds)) {
					taskIds.removeAll(Collections.singleton(null));
					taskIds.removeAll(Collections.singleton(""));
					LOGGER.info("taskIds ={}", taskIds);
				}
			}

			if (!CmnUtil.isListNull(taskIds)) {
				if (!CmnUtil.isListNull(taskIds)) {
					if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
						return releaseTaskByIds(taskIds, officerId, CmnUtil.getStr(wfwPayload.getUserType()));
					} else {
						return releaseTaskByIds(taskIds, officerId, CmnConstants.EMPTY_STRING);
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}


	@Override
	public boolean releaseTaskGroupAdmin(WfwPayload wfwPayload) {
		String officerId = CmnUtil.getStr(wfwPayload.getTaskAuthorId());
		if (CmnUtil.isObjNull(officerId)) {
			return false;
		} else {
			List<String> taskIds = new ArrayList<>();

			if (!CmnUtil.isObjNull(wfwPayload.getTaskIds())) {
				taskIds = Arrays.asList(wfwPayload.getTaskIds().split(TASKID_SPLIT_PATTERN));
				if (!CmnUtil.isListNull(taskIds)) {
					taskIds.removeAll(Collections.singleton(null));
					taskIds.removeAll(Collections.singleton(""));
					LOGGER.info("taskIds ={}", taskIds);
				}
			}

			if (!CmnUtil.isListNull(taskIds)) {
				if (!CmnUtil.isListNull(taskIds)) {
					if (!CmnUtil.isObjNull(wfwPayload.getUserType())) {
						return releaseTaskByIdsGroupAdmin(taskIds, officerId, wfwPayload.getGroupAdminId(),
								CmnUtil.getStr(wfwPayload.getUserType()));
					} else {
						return releaseTaskByIdsGroupAdmin(taskIds, officerId, wfwPayload.getGroupAdminId(),
								CmnConstants.EMPTY_STRING);
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}


	private boolean releaseTaskByIds(List<String> taskIdList, String officerId, String userType) {
		try {
			for (String taskId : taskIdList) {
				releaseTask(taskId, officerId, userType);
			}
			return true;

		} catch (JpaSystemException e) {
			LOGGER.error("JpaSystemException. ", e);
			return false;
		} catch (Exception e) {
			LOGGER.error("Exception. ", e);
			return false;
		}

	}


	private boolean releaseTaskByIdsGroupAdmin(List<String> taskIdList, String taskAuthor, String actionDoneBy,
			String userType) {
		try {
			for (String taskId : taskIdList) {
				releaseTaskGroupAdmin(taskId, taskAuthor, actionDoneBy, userType);
			}
			return true;

		} catch (JpaSystemException e) {
			LOGGER.error("JpaSystemException. ", e);
			return false;
		} catch (Exception e) {
			LOGGER.error("Exception. ", e);
			return false;
		}

	}


	@Override
	public boolean startTask(TaskDetails payload) {
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {
			return createTask(payload, payload.getActionBy());
		}
		return false;
	}


	@Override
	public boolean startTask(TaskDetails payload, List<TaskDetails> newTaskList) {
		boolean res = false;
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {

			boolean isCompleted = completeCurrentTask(payload, payload.getActionBy(), payload.getActionByFullName());

			if (isCompleted) {
				for (TaskDetails taskDetails : newTaskList) {
					res = createTask(taskDetails, taskDetails.getActionBy());
				}

			}

		}
		return res;
	}


	@Override
	public boolean amendTask(TaskDetails payload) {
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {
			return createTaskAmend(payload, payload.getActionBy());
		}
		return false;
	}


	@Override
	public boolean completeTask(TaskDetails payload) {
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {
			return completeCurrentTask(payload, payload.getActionBy(), payload.getActionByFullName());
		}
		return false;
	}


	@Override
	public boolean completeAndStartTask(TaskDetails payload) {
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {
			boolean isComplete = completeCurrentTask(payload, payload.getActionBy(), payload.getActionByFullName());
			if (isComplete) {
				return createTask(payload, payload.getActionBy());
			}
		}
		return false;
	}


	@Override
	@Transactional
	public boolean updateTaskForEpay(TaskDetails payload) {
		boolean updateTaskPay = false;
		WfwStatus wfStatus = null;
		if (!CmnUtil.isObjNull(payload)) {
			String currAppStatus = CmnConstants.EMPTY_STRING;
			WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(payload.getTaskId());
			String operationCode = "201";
			String levelId = CmnUtil.getStr(mstr.getLevelId());

			LOGGER.info("Workflow Level Id :{} application status Id : {}", levelId, payload.getWfStatus());
			wfStatus = wfwStatusDto.findWfStatusByLevelIdAppStatus(levelId, payload.getWfStatus());

			if (!BaseUtil.isObjNull(mstr)) {
				if (!CmnUtil.isObjNull(wfStatus)) {
					mstr.setApplStsCode(payload.getApplStsCode());
					mstr.setLastStatusId(wfStatus.getStatusId());
					mstr.setLastStatusDesc(wfStatus.getApplStsId());
					mstr.setLevelId(wfStatus.getNextProcId());
					mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
					WfwStatus wfStatusNext = wfwStatusDto.findFirstStatusByLevelId(wfStatus.getNextProcId());
					if (!CmnUtil.isObjNull(wfStatusNext)) {
						mstr.setStatusId(wfStatusNext.getStatusId());
						mstr.setApplStsId(wfStatusNext.getApplStsId());
					} else {
						mstr.setStatusId(wfStatus.getStatusId());
						mstr.setApplStsId(wfStatus.getApplStsId());
					}
				}
				WfwInbox inbox = new WfwInbox();
				BeanUtil.copyProperties(mstr, inbox);
				WfwInboxHist history = new WfwInboxHist();
				BeanUtil.copyProperties(mstr, history);
				history.setApplStsCodePrev(currAppStatus);
				updateTaskPay = crudWfTask(mstr, inbox, history, operationCode, payload.getActionBy());
			}
		}
		return updateTaskPay;
	}


	@Override
	@Transactional
	public boolean updateTask(TaskDetails payload) {
		return updateTask(payload, false);
	}


	// updated Nigel 20160826
	@Override
	@Transactional
	public boolean updateTask(TaskDetails payload, boolean isCheckInbox) {
		if (!CmnUtil.isObjNull(payload)) {
			WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(payload.getTaskId());
			String operationCode = "201";
			WfwInbox wfwInbox = null;

			if (!CmnUtil.isObjNull(mstr)) {
				String currAppStatus = mstr.getApplStsCode();

				if (!CmnUtil.isObjNull(payload.getApplDate())) {
					mstr.setApplDate(payload.getApplDate());
				}

				if (!CmnUtil.isObjNull(payload.getApplName())) {
					mstr.setApplUserName(payload.getApplName());
				}

				if (!CmnUtil.isObjNull(payload.getApplStatus())) {
					mstr.setApplStsId(payload.getApplStatus());
				}

				if (!CmnUtil.isObjNull(payload.getApplStsCode())) {
					mstr.setApplStsCode(payload.getApplStsCode());
				}

				if (!CmnUtil.isObjNull(payload.getApplUserId())) {
					mstr.setApplUserId(payload.getApplUserId());
				}

				if (!CmnUtil.isObjNull(payload.getApproveDate())) {
					mstr.setApproveDate(payload.getApproveDate());
				}

				if (payload.getAttemptCount() > 0) {
					mstr.setAttemptCnt(payload.getAttemptCount());
				}

				if (!CmnUtil.isObjNull(payload.getBranchCode())) {
					mstr.setBranchId(payload.getBranchCode());
				}

				if (!CmnUtil.isObjNull(payload.getCompanyName())) {
					mstr.setCompanyName(payload.getCompanyName());
				}

				if (!CmnUtil.isObjNull(payload.getCmpnyRegNo())) {
					mstr.setCompanyRegNo(payload.getCmpnyRegNo());
				}

				if (!CmnUtil.isObjNull(payload.getRecruitmentAgent())) {
					String[] str = new String[payload.getRecruitmentAgent().size()];
					str = payload.getRecruitmentAgent().toArray(str);
					mstr.setRecruitmentAgent(str);
				}

				if (!CmnUtil.isObjNull(payload.getCountry())) {
					mstr.setCountry(payload.getCountry());
				}

				if (!CmnUtil.isObjNull(payload.getExpiryDate())) {
					mstr.setExpiryDate(payload.getExpiryDate());
				}

				if (!CmnUtil.isObjNull(payload.getInterviewDate())) {
					mstr.setInterviewDate(payload.getInterviewDate());
				}

				if (!CmnUtil.isObjNull(payload.getIsDisplay())) {
					mstr.setIsDisplay(payload.getIsDisplay());
				}

				if (!CmnUtil.isObjNull(payload.getModuleId())) {
					mstr.setModuleId(payload.getModuleId());
				}

				if (payload.getNoOfWorkers() > 0) {
					mstr.setNoOfWorkers(payload.getNoOfWorkers());
				}

				if (!CmnUtil.isObjNull(payload.getRemarks())) {
					mstr.setApplRemark(payload.getRemarks());
				}

				if (!CmnUtil.isObjNull(payload.getSector())) {
					mstr.setSector(payload.getSector());
				}

				if (!CmnUtil.isObjNull(payload.getSector())) {
					mstr.setSector(payload.getSector());
				}

				if (!CmnUtil.isObjNull(payload.getLevelId()) && payload.getLevelId().equalsIgnoreCase("empty")) {
					mstr.setLevelId(null);
				}

				WfwInboxHist history = new WfwInboxHist();
				BeanUtil.copyProperties(mstr, history);
				history.setApplRemark(CmnConstants.REMARK_UPDATE_TASK);
				history.setApplStsCodePrev(currAppStatus);

				if (isCheckInbox) {
					wfwInbox = findWfInboxByTaskId(payload.getTaskId());

					if (BaseUtil.isObjNull(wfwInbox)) {

						mstr.setOfficerId(payload.getActionBy());
						mstr.setQueueInd(CmnConstants.QUEUE_IND_INBOXED);

						wfwInbox = new WfwInbox();
						BeanUtil.copyProperties(mstr, wfwInbox);
						operationCode = "211";
					}
				}

				return crudWfTask(mstr, wfwInbox, history, operationCode, payload.getActionBy());
			}

		}
		return false;
	}


	@Override
	@Transactional
	public boolean rejectTask(TaskDetails payload) {

		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getActionBy())) {
			WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(payload.getTaskId());
			String currAppStatus = CmnConstants.EMPTY_STRING;
			if (!CmnUtil.isObjNull(mstr)) {
				currAppStatus = mstr.getApplStsCode();
				mstr.setLastStatusId(mstr.getStatusId());
				mstr.setStatusId(CmnConstants.EMPTY_STRING);
				mstr.setLevelId(CmnConstants.EMPTY_STRING);
				mstr.setApplRemark(payload.getRemarks());
			}
			WfwInboxHist history = new WfwInboxHist();
			BeanUtil.copyProperties(mstr, history);
			history.setApplStsCodePrev(currAppStatus);
			return crudWfTask(mstr, null, history, "201", payload.getActionBy());
		}
		return false;
	}


	@Override
	public boolean updateTaskKDNVerifier(TaskDetails payload) {

		boolean isUpdated = false;
		if (!CmnUtil.isObjNull(payload) && !CmnUtil.isObjNull(payload.getTaskId())) {
			WfwInboxMstr mstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(payload.getTaskId());
			if (!CmnUtil.isObjNull(mstr)) {
				mstr.setQueueInd(CmnConstants.QUEUE_IND_QUEUED);
				mstr.setOfficerId(CmnConstants.EMPTY_STRING);
				mstr.setOfficerName(CmnConstants.EMPTY_STRING);
				mstr.setUpdateDt(CmnUtil.getCurrentDateSQL());
				mstr.setUpdateId(payload.getActionBy());
			}
			wfwInboxMstrDto.editWfInboxMstr(mstr);
			isUpdated = true;
		}
		return isUpdated;
	}


	@Override
	public List<InterviewEnquiry> findInterviewForEnquiry(Date interviewDateFrom, Date interviewDateTo,
			String sectorAgency) {
		return wfwInboxMstrDto.findInterviewForEnquiry(interviewDateFrom, interviewDateTo, sectorAgency);
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = WfwException.class)
	public boolean updateWfTaskId(WfwPayload wfwPayload) {
		final String SEC_CNCL_TEXT = "SEC_CNCL";

		try {
			String postFix = "ABT";
			String wfwStatus = "QEABT";
			if (!BaseUtil.isObjNull(wfwPayload.getApplStatusCode())
					&& BaseUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), "QEREJ")) {
				postFix = "REJ";
				wfwStatus = "QEREJ";
			} else if (!BaseUtil.isObjNull(wfwPayload.getApplStatusCode())
					&& BaseUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), SEC_CNCL_TEXT)) {
				postFix = "";
				wfwStatus = SEC_CNCL_TEXT;
			}

			WfwInboxMstr inboxMstr = wfwInboxMstrDto.findWfInboxMstrByRefNo(wfwPayload.getTaskIds());
			List<WfwInbox> inboxList = wfwInboxDto.findWfInboxByRefNo(wfwPayload.getTaskIds());
			List<WfwInboxHist> histList = wfwInboxHistDto.findWfInboxHistByTaskId(wfwPayload.getTaskIds());

			if (!BaseUtil.isObjNull(inboxMstr)) {
				WfwInboxMstr newInboxMstr = inboxMstr;
				wfwInboxMstrDto.deleteWfInboxMstr(inboxMstr);

				newInboxMstr.setTaskId(inboxMstr.getTaskId() + postFix);
				newInboxMstr.setRefNo(newInboxMstr.getTaskId());
				newInboxMstr.setApplStsCode(wfwStatus);
				newInboxMstr.setLevelId(null);
				newInboxMstr.setOfficerId(null);
				newInboxMstr.setOfficerName(null);
				newInboxMstr.setQueueInd("Q");
				wfwInboxMstrDto.addWfInboxMstr(newInboxMstr);
			}

			if (!BaseUtil.isListNull(inboxList)) {
				for (WfwInbox inbox : inboxList) {
					wfwInboxDto.deleteWfInbox(inbox);
				}
			}

			if (!BaseUtil.isListNull(histList)) {
				WfwInboxHist newHist = histList.get(0);

				for (WfwInboxHist hist : histList) {
					hist.setTaskId(wfwPayload.getTaskIds() + postFix);
					hist.setRefNo(wfwPayload.getTaskIds() + postFix);
					wfwInboxHistDto.editWfInboxHist(hist);
				}

				newHist.setHistId(SeqGen.getSequenceNo());
				newHist.setTaskId(wfwPayload.getTaskIds() + postFix);
				newHist.setRefNo(wfwPayload.getTaskIds() + postFix);
				newHist.setApplStsId(wfwStatus);
				newHist.setApplStsCode(wfwStatus);
				if (!BaseUtil.isObjNull(wfwPayload.getApplStatusCode())
						&& BaseUtil.isEqualsCaseIgnore(wfwPayload.getApplStatusCode(), SEC_CNCL_TEXT)) {
					newHist.setApplRemark("~~~CANCEL BY GOVERNMENT~~~");
				} else {
					newHist.setApplRemark("~~~ABORT/REJECT BY OPERATOR~~~");
				}
				newHist.setOfficerId(null);
				newHist.setOfficerName(null);
				newHist.setQueueInd("Q");
				wfwInboxHistDto.addWfInboxHist(newHist);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Error. ", e);
		}
		return false;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<TaskDetails> taskListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ) {

		DataTableResults<TaskDetails> result = new DataTableResults<>();
		DataTableResults<WfwInboxMstr> wfwInboxMstrTable = wfwInboxMstrDto.searchTaskListInQueueTable(wfwPayload,
				dataTableInRQ);
		List<WfwInboxMstr> wfwInboxMstrLst = wfwInboxMstrTable.getData();
		List<TaskDetails> taskDetailList = new ArrayList<>();

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrLst) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}
		result.setData(taskDetailList);
		result.setDraw(wfwInboxMstrTable.getDraw());
		result.setRecordsFiltered(wfwInboxMstrTable.getRecordsFiltered());
		result.setRecordsTotal(wfwInboxMstrTable.getRecordsTotal());
		result.setError(wfwInboxMstrTable.getError());
		return result;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<TaskDetails> applicationListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ,
			String updateId) {

		DataTableResults<TaskDetails> result = new DataTableResults<>();
		DataTableResults<WfwInboxMstr> wfwInboxMstrTable = wfwInboxMstrDto.searchTaskListInQueueAllTable(wfwPayload,
				dataTableInRQ, updateId);

		List<WfwInboxMstr> wfwInboxMstrLst = wfwInboxMstrTable.getData();
		List<TaskDetails> taskDetailList = new ArrayList<>();

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrLst) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}

		result.setData(taskDetailList);
		result.setDraw(wfwInboxMstrTable.getDraw());
		result.setRecordsFiltered(wfwInboxMstrTable.getRecordsFiltered());
		result.setRecordsTotal(wfwInboxMstrTable.getRecordsTotal());
		result.setError(wfwInboxMstrTable.getError());
		return result;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<TaskDetails> claimTaskListTable(WfwPayload wfwPayload, DataTableRequest dataTableInRQ) {

		DataTableResults<TaskDetails> result = new DataTableResults<>();
		DataTableResults<WfwInboxMstr> wfwInboxMstrTable = wfwInboxMstrDto.searchTaskListInInboxTable(wfwPayload,
				dataTableInRQ);
		List<WfwInboxMstr> wfwInboxMstrLst = wfwInboxMstrTable.getData();
		List<TaskDetails> taskDetailList = new ArrayList<>();

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrLst) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}
		result.setData(taskDetailList);
		result.setDraw(wfwInboxMstrTable.getDraw());
		result.setRecordsFiltered(wfwInboxMstrTable.getRecordsFiltered());
		result.setRecordsTotal(wfwInboxMstrTable.getRecordsTotal());
		result.setError(wfwInboxMstrTable.getError());
		return result;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public DataTableResults<TaskDetails> claimTaskListGroupAdminTable(WfwPayload wfwPayload,
			DataTableRequest dataTableInRQ) {

		DataTableResults<TaskDetails> result = new DataTableResults<>();
		DataTableResults<WfwInboxMstr> wfwInboxMstrTable = wfwInboxMstrDto
				.searchTaskListInInboxGroupAdminTable(wfwPayload, dataTableInRQ);
		List<WfwInboxMstr> wfwInboxMstrLst = wfwInboxMstrTable.getData();
		List<TaskDetails> taskDetailList = new ArrayList<>();

		for (WfwInboxMstr wfwInboxMstr : wfwInboxMstrLst) {
			taskDetailList.add(transformMstrToTaskDetails(wfwInboxMstr, wfwPayload.getIsQuotaSec()));
		}

		result.setData(taskDetailList);
		result.setDraw(wfwInboxMstrTable.getDraw());
		result.setRecordsFiltered(wfwInboxMstrTable.getRecordsFiltered());
		result.setRecordsTotal(wfwInboxMstrTable.getRecordsTotal());
		result.setError(wfwInboxMstrTable.getError());
		return result;
	}

}