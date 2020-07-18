package com.wfw.web.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.util.DateUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.ConfigForm;
import com.wfw.model.WfwAsgnRole;
import com.wfw.model.WfwAsgnRolePk;
import com.wfw.model.WfwAsgnTran;
import com.wfw.model.WfwLevel;
import com.wfw.model.WfwStatus;
import com.wfw.model.WfwType;
import com.wfw.sdk.constants.CmnConstants;
import com.wfw.util.PopupBox;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_WF_CONFIG_EDIT)
public class WfConfigEditController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfConfigEditController.class);

	private static final String ERROR_TEXT = "Error!";

	private static final String TWF_WF_STATUS_TEXT = "twfWfStatus";

	private static final String TWF_WF_TYPE_TEXT = "twfWfType";

	private static final String TWF_WF_LEVEL_TEXT = "twfWfLevel";


	@GetMapping
	public ModelAndView view(@RequestParam("id") String id, @RequestParam("type") String type,
			@RequestParam("action") String action, ConfigForm configForm) {
		log.info("id = {}", id);
		log.info("type = {}", type);
		log.info("action = {}", action);
		ModelAndView mav = new ModelAndView();
		if (BaseUtil.isEqualsCaseIgnore(type, "T")) {
			configForm = viewInitConfigFormTypeT(id, action, configForm);
		} else if (BaseUtil.isEqualsCaseIgnore(type, "L")) {
			configForm = viewInitConfigFormTypeL(id, action, configForm);
		} else if (BaseUtil.isEqualsCaseIgnore(type, "S")) {
			configForm = viewInitConfigFormTypeS(id, action, configForm);

			List<WfwLevel> nextLevelList = getCommonService().findWfLevelByTypeId(configForm.getTypeId());
			mav.addObject("nextLevel", nextLevelList);
		}

		configForm.setAction(action);

		mav.addObject(PageConstants.DATA_OBJ, configForm);
		mav.setViewName(PageConstants.PAGE_CONST_WF_CONFIG_EDIT);
		return mav;
	}


	private ConfigForm viewInitConfigFormTypeT(final String id, final String action, ConfigForm configForm) {
		if (BaseUtil.isEquals(action, "edit")) {
			WfwType wfType = getCommonService().findWfTypeById(id);
			if (!BaseUtil.isObjNull(wfType)) {
				configForm = new ConfigForm();
				configForm.setTypeId(wfType.getTypeId());
				configForm.setDescription(wfType.getDescription());
				configForm.setAssignType(wfType.getAssignType());
				configForm.setIsActive(wfType.getIsActive());
				configForm.setMaintenanceType("Edit Workflow Type");
				configForm.setDbTableName(TWF_WF_TYPE_TEXT);

				WfwAsgnTran assgnTrans = getCommonService().findWfAsgnTranByTypeId(id);
				if (!BaseUtil.isObjNull(assgnTrans)) {
					configForm.setTransId(assgnTrans.getTranId());
					configForm.setModuleId(assgnTrans.getModuleId());
					configForm.setViewPath(assgnTrans.getViewPath());
				}
			}
		} else if (BaseUtil.isEquals(action, "add")) {
			configForm = new ConfigForm();
			configForm.setAssignType("M");
			configForm.setIsActive("Y");
			configForm.setMaintenanceType("New Workflow Type");
			configForm.setDbTableName(TWF_WF_TYPE_TEXT);
		}
		return configForm;
	}


	private ConfigForm viewInitConfigFormTypeL(final String id, final String action, ConfigForm configForm) {
		if (BaseUtil.isEquals(action, "edit")) {
			WfwLevel wfLevel = getCommonService().findLevelByLevelId(id);
			if (!BaseUtil.isObjNull(wfLevel)) {
				configForm.setLevelId(wfLevel.getLevelId());
				configForm.setTypeId(wfLevel.getTypeId());
				configForm.setDescription(wfLevel.getDescription());
				configForm.setFlowNo(wfLevel.getFlowNo());
				configForm.setMaintenanceType("Edit Workflow Level");
				configForm.setDbTableName(TWF_WF_LEVEL_TEXT);
				List<WfwAsgnRole> roles = getCommonService().findWfAsgnRoleByLevelId(id);
				StringBuilder roleStr = new StringBuilder(CmnConstants.EMPTY_STRING);
				int count = 0;
				for (WfwAsgnRole twfWfAsgnRole : roles) {
					if (count == 0) {
						roleStr.append(BaseUtil.getStr(twfWfAsgnRole.getWfwAsgnRolePk().getRoleId()));
					} else {
						roleStr.append(roleStr).append(",")
								.append(BaseUtil.getStr(twfWfAsgnRole.getWfwAsgnRolePk().getRoleId()));
					}
					count++;
				}
				configForm.setRoles(roleStr.toString());
			}
		} else if (BaseUtil.isEquals(action, "add")) {
			configForm = new ConfigForm();
			configForm.setTypeId(id);
			configForm.setMaintenanceType("New Workflow Level");
			configForm.setDbTableName(TWF_WF_LEVEL_TEXT);
		}
		return configForm;
	}


	private ConfigForm viewInitConfigFormTypeS(final String id, final String action, ConfigForm configForm) {
		if (BaseUtil.isEquals(action, "edit")) {
			WfwStatus wfStatus = getCommonService().findStatusByStatusId(id);
			if (!BaseUtil.isObjNull(wfStatus)) {
				configForm.setLevelId(wfStatus.getLevelId());
				configForm.setTypeId(wfStatus.getTypeId());
				configForm.setStatusId(wfStatus.getStatusId());
				configForm.setDescription(wfStatus.getDescription());
				configForm.setApplStsId(wfStatus.getApplStsId());
				configForm.setFlowNo(wfStatus.getFlowNo());
				configForm.setNextProcId(wfStatus.getNextProcId());
				configForm.setIsActive(wfStatus.getIsActive());
				configForm.setIsDisplay(wfStatus.getIsDisplay());
				configForm.setIsEmailRequired(wfStatus.getIsEmailRequired());
				configForm.setIsInitialSts(wfStatus.getIsInitialSts());
				configForm.setMaintenanceType("Edit Workflow Status");
				configForm.setDbTableName(TWF_WF_STATUS_TEXT);

				WfwType wfType = getCommonService().findWfTypeById(wfStatus.getTypeId());
				if (!BaseUtil.isObjNull(wfType)) {
					configForm.setTypeDesc(wfType.getDescription());
				}

				WfwLevel wfLevel = getCommonService().findLevelByLevelId(wfStatus.getLevelId());
				if (!BaseUtil.isObjNull(wfLevel)) {
					configForm.setLevelDesc(wfLevel.getDescription());
				}
			}
		} else if (BaseUtil.isEquals(action, "add")) {
			WfwLevel wfLevel = getCommonService().findLevelByLevelId(id);
			configForm = new ConfigForm();
			if (!BaseUtil.isObjNull(wfLevel)) {
				configForm.setLevelId(wfLevel.getLevelId());
				configForm.setTypeId(wfLevel.getTypeId());
				configForm.setIsActive("Y");
				configForm.setIsDisplay("N");
				configForm.setIsEmailRequired("N");
				configForm.setIsInitialSts("N");
				configForm.setMaintenanceType("New Workflow Status");
				configForm.setDbTableName(TWF_WF_STATUS_TEXT);

				WfwType wfType = getCommonService().findWfTypeById(wfLevel.getTypeId());
				if (!BaseUtil.isObjNull(wfType)) {
					configForm.setTypeDesc(wfType.getDescription());
				}

				if (!BaseUtil.isObjNull(wfLevel)) {
					configForm.setLevelDesc(wfLevel.getDescription());
				}

			}
		}
		return configForm;
	}


	@PostMapping(params = "create")
	public ModelAndView create(@ModelAttribute("configForm") ConfigForm configForm, BindingResult result) {
		boolean isCreated = false;
		ModelAndView mav = new ModelAndView();
		final String IS_CREATE = "isCreated = {}";
		if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_TYPE_TEXT)) {
			WfwType wfType = new WfwType();
			wfType.setTypeId(BaseUtil.getStrUpper(configForm.getTypeId()));
			wfType.setDescription(configForm.getDescription());
			wfType.setAssignType(configForm.getAssignType());
			wfType.setIsActive(configForm.getIsActive());
			wfType.setCreateId(SessionData.getCurrentUserId());
			wfType.setCreateDt(DateUtil.getSQLTimestamp());
			wfType.setUpdateId(SessionData.getCurrentUserId());
			wfType.setUpdateDt(DateUtil.getSQLTimestamp());

			WfwAsgnTran assgnTrans = new WfwAsgnTran();
			assgnTrans.setTranId(configForm.getTransId());
			assgnTrans.setModuleId(configForm.getModuleId());
			assgnTrans.setTypeId(configForm.getTypeId());
			assgnTrans.setViewPath(configForm.getViewPath());
			assgnTrans.setCreateId(SessionData.getCurrentUserId());
			assgnTrans.setCreateDt(DateUtil.getSQLTimestamp());
			assgnTrans.setUpdateId(SessionData.getCurrentUserId());
			assgnTrans.setUpdateDt(DateUtil.getSQLTimestamp());

			isCreated = getCommonService().addWfType(wfType);
			if (isCreated) {
				isCreated = getCommonService().addWfAsgnTran(assgnTrans);
			}
			log.info(IS_CREATE, isCreated);
			if (isCreated) {
				mav.addAllObjects(PopupBox.success("Type", "Workflow Type", " Type has successfully saved."));
			} else {
				mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
						"Type has not saved, please try again or contact with admin."));
			}
		} else if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_LEVEL_TEXT)) {
			WfwLevel wfLevel = new WfwLevel();
			wfLevel.setLevelId(BaseUtil.getStrUpper(configForm.getLevelId()));
			wfLevel.setTypeId(BaseUtil.getStrUpper(configForm.getTypeId()));
			wfLevel.setDescription(configForm.getDescription());
			wfLevel.setFlowNo(configForm.getFlowNo());
			wfLevel.setCreateId(SessionData.getCurrentUserId());
			wfLevel.setCreateDt(DateUtil.getSQLTimestamp());
			wfLevel.setUpdateId(SessionData.getCurrentUserId());
			wfLevel.setUpdateDt(DateUtil.getSQLTimestamp());
			isCreated = getCommonService().addWfLevel(wfLevel);
			if (isCreated) {
				String roles = BaseUtil.getStrUpper(configForm.getRoles());
				roles = roles.replaceAll(" ", CmnConstants.EMPTY_STRING);
				String[] roleArr = roles.split(",");
				List<WfwAsgnRole> roleList = new ArrayList<>();
				for (String role : roleArr) {
					WfwAsgnRole asgnRole = new WfwAsgnRole();
					asgnRole.setWfwAsgnRolePk(new WfwAsgnRolePk(wfLevel.getLevelId(), role));
					asgnRole.setCreateId(SessionData.getCurrentUserId());
					asgnRole.setCreateDt(DateUtil.getSQLTimestamp());
					asgnRole.setUpdateId(SessionData.getCurrentUserId());
					asgnRole.setUpdateDt(DateUtil.getSQLTimestamp());
					roleList.add(asgnRole);
				}
				boolean deleted = getCommonService().deleteWfAsgnRoleByLevelId(configForm.getLevelId());
				if (deleted) {
					isCreated = getCommonService().addWfAsgnRole(roleList);
				}
				if (isCreated) {
					mav.addAllObjects(
							PopupBox.success("Level", "Workflow Level", " Level has successfully saved."));
				} else {
					mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
							"Level has not saved, please try again or contact with admin."));
				}
			}
			log.info(IS_CREATE, isCreated);
		} else if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_STATUS_TEXT)) {
			WfwStatus wfStatus = new WfwStatus();
			wfStatus.setStatusId(BaseUtil.getStrUpper(configForm.getStatusId()));
			wfStatus.setDescription(configForm.getDescription());
			wfStatus.setApplStsId(configForm.getApplStsId());
			wfStatus.setFlowNo(configForm.getFlowNo());
			wfStatus.setTypeId(configForm.getTypeId());
			wfStatus.setLevelId(configForm.getLevelId());
			wfStatus.setNextProcId(configForm.getNextProcId());
			wfStatus.setIsDisplay(configForm.getIsDisplay());
			wfStatus.setIsInitialSts(configForm.getIsInitialSts());
			wfStatus.setIsEmailRequired(configForm.getIsEmailRequired());
			wfStatus.setIsActive(configForm.getIsActive());
			wfStatus.setCreateId(SessionData.getCurrentUserId());
			wfStatus.setCreateDt(DateUtil.getSQLTimestamp());
			wfStatus.setUpdateId(SessionData.getCurrentUserId());
			wfStatus.setUpdateDt(DateUtil.getSQLTimestamp());

			isCreated = getCommonService().addWfStatus(wfStatus);
			log.info(IS_CREATE, isCreated);
			if (isCreated) {
				mav.addAllObjects(PopupBox.success("Status", "Workflow Status", " Status has successfully saved."));
			} else {
				mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
						"Status has not saved, please try again or contact with admin."));
			}
		}

		mav.addObject(PageConstants.DATA_LIST_OBJ, getCommonService().generateTypeList());
		mav.setViewName(PageConstants.PAGE_CONST_WF_CONFIG);
		return mav;
	}


	@PostMapping(params = "update")
	public ModelAndView update(@ModelAttribute("configForm") ConfigForm configForm, BindingResult result) {
		boolean isUpdated = false;
		ModelAndView mav = new ModelAndView();
		if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_TYPE_TEXT)) {
			WfwType wfType = getCommonService().findWfTypeById(configForm.getTypeId());
			if (!BaseUtil.isObjNull(wfType)) {
				wfType.setDescription(configForm.getDescription());
				wfType.setAssignType(configForm.getAssignType());
				wfType.setIsActive(configForm.getIsActive());
				wfType.setUpdateId(SessionData.getCurrentUserId());
				wfType.setUpdateDt(DateUtil.getSQLTimestamp());

				WfwAsgnTran assgnTrans = getCommonService().findWfAsgnTranByTypeId(wfType.getTypeId());
				boolean isDeleted = getCommonService().deleteWfAsgnTran(assgnTrans);
				if (isDeleted) {
					assgnTrans = null; // insert new
				}
				if (assgnTrans != null && !BaseUtil.isObjNull(assgnTrans)) {
					assgnTrans.setModuleId(configForm.getModuleId());
					assgnTrans.setTypeId(configForm.getTypeId());
					assgnTrans.setViewPath(configForm.getViewPath());
					assgnTrans.setUpdateId(SessionData.getCurrentUserId());
					assgnTrans.setUpdateDt(DateUtil.getSQLTimestamp());
				} else {
					assgnTrans = new WfwAsgnTran();
					assgnTrans.setTranId(configForm.getTransId());
					assgnTrans.setModuleId(configForm.getModuleId());
					assgnTrans.setTypeId(configForm.getTypeId());
					assgnTrans.setViewPath(configForm.getViewPath());
					assgnTrans.setCreateId(SessionData.getCurrentUserId());
					assgnTrans.setCreateDt(DateUtil.getSQLTimestamp());
					assgnTrans.setUpdateId(SessionData.getCurrentUserId());
					assgnTrans.setUpdateDt(DateUtil.getSQLTimestamp());
				}

				isUpdated = getCommonService().editWfType(wfType);
				if (isUpdated) {
					isUpdated = getCommonService().editWfAsgnTran(assgnTrans);
				}
				log.info("isUpdated = {}", isUpdated);
				if (isUpdated) {
					mav.addAllObjects(
							PopupBox.success("Type", "Workflow Type", " Type has successfully updated."));
				} else {
					mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
							"Type has not updated, please try again or contact with admin."));
				}
			}
		} else if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_LEVEL_TEXT)) {
			WfwLevel wfLevel = getCommonService().findLevelByLevelId(configForm.getLevelId());
			if (!BaseUtil.isObjNull(wfLevel)) {
				wfLevel.setDescription(configForm.getDescription());
				wfLevel.setFlowNo(configForm.getFlowNo());
				wfLevel.setUpdateId(SessionData.getCurrentUserId());
				wfLevel.setUpdateDt(DateUtil.getSQLTimestamp());
				isUpdated = getCommonService().editWfLevel(wfLevel);
				if (isUpdated) {
					String roles = BaseUtil.getStrUpper(configForm.getRoles());
					roles = roles.replaceAll(" ", CmnConstants.EMPTY_STRING);
					String[] roleArr = roles.split(",");
					List<WfwAsgnRole> roleList = new ArrayList<>();
					for (String role : roleArr) {
						WfwAsgnRole asgnRole = new WfwAsgnRole();
						asgnRole.setWfwAsgnRolePk(new WfwAsgnRolePk(wfLevel.getLevelId(), role));
						asgnRole.setCreateId(SessionData.getCurrentUserId());
						asgnRole.setCreateDt(DateUtil.getSQLTimestamp());
						asgnRole.setUpdateId(SessionData.getCurrentUserId());
						asgnRole.setUpdateDt(DateUtil.getSQLTimestamp());
						roleList.add(asgnRole);
					}
					boolean deleted = getCommonService().deleteWfAsgnRoleByLevelId(configForm.getLevelId());
					if (deleted) {
						isUpdated = getCommonService().addWfAsgnRole(roleList);
					}

				}
				log.info("isCreated = {}", isUpdated);
				if (isUpdated) {
					mav.addAllObjects(
							PopupBox.success("Level", "Workflow Level", " Level has successfully updated."));
				} else {
					mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
							"Level has not updated, please try again or contact with admin."));
				}
			}
		} else if (BaseUtil.isEquals(configForm.getDbTableName(), TWF_WF_STATUS_TEXT)) {
			WfwStatus wfStatus = getCommonService().findStatusByStatusId(configForm.getStatusId());
			if (!BaseUtil.isObjNull(wfStatus)) {
				wfStatus.setDescription(configForm.getDescription());
				wfStatus.setApplStsId(configForm.getApplStsId());
				wfStatus.setFlowNo(configForm.getFlowNo());
				wfStatus.setNextProcId(configForm.getNextProcId());
				wfStatus.setIsDisplay(configForm.getIsDisplay());
				wfStatus.setIsInitialSts(configForm.getIsInitialSts());
				wfStatus.setIsEmailRequired(configForm.getIsEmailRequired());
				wfStatus.setIsActive(configForm.getIsActive());
				wfStatus.setUpdateId(SessionData.getCurrentUserId());
				wfStatus.setUpdateDt(DateUtil.getSQLTimestamp());
				isUpdated = getCommonService().editWfStatus(wfStatus);
				log.info("isUpdated = {}", isUpdated);
				if (isUpdated) {
					mav.addAllObjects(
							PopupBox.success("Status", "Workflow Status", " Status has successfully updated."));
				} else {
					mav.addAllObjects(PopupBox.error(ERROR_TEXT, ERROR_TEXT,
							"Status has not updated, please try again or contact with admin."));
				}
			}
		}

		mav.addObject(PageConstants.DATA_LIST_OBJ, getCommonService().generateTypeList());
		mav.setViewName(PageConstants.PAGE_CONST_WF_CONFIG);
		return mav;
	}


	@PostMapping(params = "cancel")
	public String cancel(@ModelAttribute("configForm") ConfigForm configForm, BindingResult result) {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_WF_CONFIG;
	}

}