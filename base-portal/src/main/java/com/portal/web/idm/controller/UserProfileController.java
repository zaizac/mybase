/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.controller;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.be.sdk.constants.IdmRoleConstants;
import com.be.sdk.exception.BeException;
import com.dm.sdk.exception.DmException;
import com.dm.sdk.model.Documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.ForgotPassword;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserGroupBranch;
import com.idm.sdk.model.UserProfile;
import com.portal.config.audit.AuditActionControl;
import com.portal.config.audit.AuditActionPolicy;
import com.portal.constants.AppConstants;
import com.portal.constants.MessageConstants;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.constants.ProjectEnum;
import com.portal.core.AbstractController;
import com.portal.web.idm.form.IdmUploadProfilePictureForm;
import com.portal.web.util.WebUtil;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.PopupBox;
import com.util.constants.BaseConstants;
import com.util.model.CustomMultipartFile;
import com.util.model.FileUpload;
import com.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
@Controller
@RequestMapping(value = PageConstants.PAGE_IDM_USR_LST)
public class UserProfileController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

	private static final String JS_FILENAME = "user-profile";

	private static final String MODULE = "user_mgmt";

	private static final String FILE_UPLOAD = "fileUpload";

	private static final String PFX_IDMUPD = "idmupd";

	private static final String PFX_ISADMIN = "isAdmin";

	private static final String PFX_ISINACTIVE = "isInactive";

	private static final String PFX_NEWUSER = "newUser";

	private static final String PFX_UPDUSER = "updUser";

	private static final String PFX_USERID = "userId";

	private static final String PFX_USERPROFILE = "userProfile";

	private static final String PFX_USRCREATE = "usrCreate";

	private static final String PFX_USRUPDATE = "usrUpdate";

	private static final String PFX_ERROR = "Error";

	private static final String PRINT_DOM_RESP_ERR = "DOM Response Error: {} - {}";

	private static final String PRINT_IDM_RESP_ERR = "IDM Response Error: {} - {}";

	private static final String PRINT_PROF_PIC_DOCMGTID = "Profile Picture DocMgtId: {}";

	private static final String PRINT_UNBL_PROC_REQ = "Unable to process your request [";

	private static final String PAGE_USER_ID = "/user?id=";

	@Autowired
	@Qualifier("userProfileValidator")
	private Validator validator;


	@Override
	protected void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		binder.setBindEmptyMultipartFiles(false);
		super.bindingPreparation(binder);
	}


	@GetMapping()
	public ModelAndView view(UserProfile userProfile, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_LST, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.mgmt.usr"));
		List<UserGroup> userGrp = getUserRoleGroupList();
		if (!BaseUtil.isObjNull(userProfile) && !BaseUtil.isListNullZero(userGrp)) {
			int maxNoOfUser = getMaxNoOfUser();
			LOGGER.info("maxNoOfUser = {}", maxNoOfUser);
		} else {
			mav.addObject("viewCreateBtn", messageService.getMessage("lbl.msg.alrdy.max.lmt"));
		}
		return mav;
	}


	@GetMapping(value = "/paginated")
	public @ResponseBody String getApplicationInfoPaginated(@ModelAttribute("userProfile") UserProfile userProfile,
			BindingResult result, HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<UserProfile> tasks = null;
		try {
			UserProfile authUser = getCurrentUser();

			userProfile.setUserTypeCode(authUser.getUserTypeCode());
			if (!BaseUtil.isObjNull(authUser)) {
				String userRoleGroupCode = !BaseUtil.isObjNull(authUser.getUserRoleGroup())
						? BaseUtil.getStr(authUser.getUserRoleGroup().getUserGroupCode())
						: BaseUtil.getStr(authUser.getUserGroupCode());
				LOGGER.info("userRoleGroup: {}", userRoleGroupCode);

				// Add filter for the roles logged in user can create
				if (!BaseUtil.isObjNull(userRoleGroupCode)) {
					List<String> userGroup = new ArrayList<>();
					userGroup.add(userRoleGroupCode);
					userProfile.setUserRoleGroupCodeList(userGroup);
				} else {
					LOGGER.warn("User role group not found.");
				}

				// Add filter by country, if exists in logged in user
				if (!BaseUtil.isObjNull(authUser.getCntryCd())) {
					userProfile.setCntryCd(authUser.getCntryCd());
				}

				// Add filter by role branch, if exists in logged in user
				if (!BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCd())) {
					userProfile.setUserGroupRoleBranchCd(authUser.getUserGroupRoleBranchCd());
				}

				// Add filter by profId, if exists in logged in user
				if (!BaseUtil.isObjNull(authUser.getProfId())) {
					userProfile.setProfId(authUser.getProfId());
				}

				if(!BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCode())) {
					userProfile.setUserGroupRoleBranchCode(authUser.getUserGroupRoleBranchCode());
				}
				
				// Add filter by branchId, if exists in logged in user
				if ((!BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCd())
						|| !BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCode()))
						&& !BaseUtil.isObjNull(authUser.getBranchId())) {
					userProfile.setBranchId(authUser.getBranchId());
				}

			}

			tasks = getIdmService().searchUserProfile(userProfile, false, getPaginationRequest(request, true));
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return new Gson().toJson(tasks);
	}


	/**
	 * User Profile Detail Page
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return
	 */
	@GetMapping(value = "/user")
	public ModelAndView viewProfile(@RequestParam String id,
			@ModelAttribute("userProfile") IdmUploadProfilePictureForm userProfile, BindingResult result) {
		ModelAndView mav = getDefaultMavProfile(id);
		IdmUploadProfilePictureForm userProfileView = (IdmUploadProfilePictureForm) mav.getModelMap()
				.get(PFX_USERPROFILE);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.usr.prof"));
		mav.addObject(PFX_USERPROFILE, userProfileView);
		return mav;
	}


	@PostMapping(value = "/user", params = "reset")
	public ModelAndView reset(@RequestParam String id, @RequestParam String reset,
			@ModelAttribute("userProfile") IdmUploadProfilePictureForm userProfile, BindingResult result,
			HttpSession session) {
		return viewProfile(id, userProfile, result);
	}


	private ModelAndView getDefaultMavProfile(String userId) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CRED, MODULE, null, null, JS_FILENAME);
		IdmUploadProfilePictureForm userProfile = new IdmUploadProfilePictureForm();
		boolean isInactive = false;

		if (BaseUtil.isEqualsCaseIgnore("new", userId)) {
			mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_NEWUSER);
			userProfile.setUserTypeCode(AppConstants.TMS_USER_TYPE);
			userProfile.setPassword("password"); // Default Temporary
											// Password
		} else {
			String userName = BaseUtil.isEqualsCaseIgnore("id", userId) ? getCurrentUserId() : userId;

			try {
				UserProfile userProfileObj = getIdmService().getUserProfileById(userName, false, false);
				if (!BaseUtil.isEqualsCaseIgnore("id", userId) && !BaseUtil.isObjNull(userProfileObj)) {
					isInactive = !BaseUtil.isEqualsCaseIgnore(BaseConstants.STATUS_ACTIVE,
							userProfileObj.getStatus());
				}
				userProfile = dozerMapper.map(userProfileObj, IdmUploadProfilePictureForm.class);
				if (!BaseUtil.isObjNull(userProfileObj.getUserRoleGroup())) {
					userProfile.setUserRoleGroupCode(userProfileObj.getUserRoleGroup().getUserGroupCode());
				}
				if (!BaseUtil.isObjNull(userProfileObj.getUserGroupRoleBranchCode())
						|| !BaseUtil.isObjNull(userProfileObj.getUserGroupRoleBranchCd())) {
					if (!BaseUtil.isObjNull(userProfileObj.getUserGroupRoleBranchCode())) {
						userProfile.setUserGroupRoleBranchCd(userProfileObj.getUserGroupRoleBranchCode());
					} else {
						userProfile.setUserGroupRoleBranchCd(userProfileObj.getUserGroupRoleBranchCd());
					}
				}
				if (!BaseUtil.isObjNull(userProfileObj.getCntryCd())) {
					userProfile.setStateCd(userProfileObj.getCntryCd());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

			// Update for logged in User
			if (BaseUtil.isEqualsCaseIgnore("id", userId)) {
				mav.addObject(AppConstants.PORTAL_MODULE, PFX_IDMUPD);
			} else if (!isInactive) {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_UPDUSER);
			}

		}

		addCheck(userProfile, mav);

		mav.addObject(PFX_ISADMIN, hasAnyRole(IdmRoleConstants.ADMIN_ROLES));
		mav.addObject(PFX_ISINACTIVE, isInactive);
		mav.addObject(PFX_USERID, userId);
		mav.addObject(PFX_USERPROFILE, userProfile);
		return mav;
	}


	private void addCheck(IdmUploadProfilePictureForm userProfile, ModelAndView mav) {
		boolean isSelCountry = false;
		boolean isSelBranch = false;
		LOGGER.info("selected userGroup: {}", userProfile.getUserRoleGroupCode());
		if (!BaseUtil.isObjNull(userProfile.getUserRoleGroupCode())) {
			LOGGER.info("check selected userGroup: {}", userProfile.getUserRoleGroupCode());
			UserGroup ug = getUserRoleGroup(userProfile.getUserRoleGroupCode(), userProfile, null);
			if (!BaseUtil.isObjNull(ug)) {
				isSelCountry = ug.isSelCountry() != null ? ug.isSelCountry() : false;
				isSelBranch = ug.isSelBranch() != null ? ug.isSelBranch() : false;
			}
		}

		mav.addObject("isSelCountry", isSelCountry);
		mav.addObject("isSelBranch", isSelBranch);
	}


	/**
	 * User Profile Create
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.CREATE_USER)
	@PostMapping(value = "/user", params = "create")
	public ModelAndView create(@RequestParam String id,
			@Valid @ModelAttribute("userProfile") IdmUploadProfilePictureForm userProfile, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CRED, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.usr.cre"));
		if (!result.hasErrors()) {
			UserProfile pr = getCurrentUser();
			userProfile = dozerMapper.map(userProfile, IdmUploadProfilePictureForm.class);
			if (hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) {
				if (BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
					userProfile.setUserGroupCode(userProfile.getUserRoleGroupCode());
				}
				trySetUser(userProfile, mav);

			} else {
				userProfile.setUserTypeCode(pr.getUserType().getUserTypeCode());
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("userProfile: {}", new ObjectMapper().valueToTree(userProfile));
			}

			UserProfile createUserProfile = dozerMapper.map(userProfile, UserProfile.class);

			if (BaseUtil.isEqualsCaseIgnore("new", id)) {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_NEWUSER);

				UserProfile prof = null;
				try {
					prof = getIdmService().getUserProfileById(userProfile.getUserId(), false, false);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
				if (!BaseUtil.isObjNull(prof)) {
					// userid already exist
					mav.addAllObjects(PopupBox.error(PFX_USRCREATE, messageService.getMessage(
							MessageConstants.ERR_USER_ID_EXISTS, new String[] { userProfile.getUserId() })));
				} else {

					tryCreateUser(userProfile, createUserProfile, mav, result, id);
				}

			} else {
				LOGGER.info("tryUpdateUser: {}", userProfile.getStateCd());
				createUserProfile.setCntryCd(userProfile.getStateCd());
				tryUpdateUser(createUserProfile, mav, id);
			}
		}

		addCheck(userProfile, mav);

		mav.addObject(PFX_ISADMIN, hasAnyRole(IdmRoleConstants.ADMIN_ROLES));
		mav.addObject(PFX_USERPROFILE, userProfile);
		mav.addObject(PFX_ISINACTIVE, false);
		mav.addObject(PFX_USERID, id);
		return mav;
	}


	private void trySetUser(IdmUploadProfilePictureForm userProfile, ModelAndView mav) {
		try {
			UserGroup ug = findAdminRoleGroupCodeByUserGroup(userProfile.getUserGroupCode(),
					getCurrentUser().getUserRoleGroupCode());
			if (ug != null) {
				userProfile.setUserRoleGroupCode(ug.getUserRoleGroupCode());
				userProfile.setUserTypeCode(ug.getUserType().getUserTypeCode());
			}
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(WebUtil.checkServiceDown(e));
		} catch (Exception e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(WebUtil.checkServiceDown(e));
			mav.addAllObjects(PopupBox.error(PFX_USRCREATE,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
	}

	@GetMapping(value = "/checkUserIdExists")
	public @ResponseBody String checkUserIdExists(@RequestParam String userId) {
		LOGGER.info("checkUserIdExists: {}", userId);
		return null;
	}

	private void tryCreateUser(IdmUploadProfilePictureForm userProfile, UserProfile createUserProfile,
			ModelAndView mav, BindingResult result, String id) {
		try {
			LOGGER.debug("tryCreateUser: {}", JsonUtil.objectMapper().writeValueAsString(userProfile));

			// Profile Picture is only one
			if (!BaseUtil.isListNullZero(userProfile.getFileUploads()) && userProfile.getFileUploads().size() == 1) {
				FileUpload fu = userProfile.getFileUploads().get(0);
				CustomMultipartFile file = fu.getFile();
				setDocument(file, fu, createUserProfile, result);
			}

			UserProfile authUser = getCurrentUser();

			if (!BaseUtil.isObjNull(userProfile.getStateCd())) {
				createUserProfile.setCntryCd(userProfile.getStateCd());
			} else if (!BaseUtil.isObjNull(authUser) && !BaseUtil.isObjNull(authUser.getCntryCd())) {
				createUserProfile.setCntryCd(authUser.getCntryCd());
				if(!BaseUtil.isObjNull(authUser.getCntryCd())
						&& !BaseUtil.isObjNull(authUser.getBranchId())) {
					createUserProfile.setBranchId(authUser.getBranchId());
				}
			}

			if (!BaseUtil.isObjNull(createUserProfile.getCntryCd())
					&& BaseUtil.isObjNull(authUser.getBranchId())) {
				LOGGER.info("JANE_STATE------------------------{}", userProfile.getStateCd());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				userGroupBranch.setBranchCode(userProfile.getStateCd());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				ug.forEach(n -> {
					LOGGER.info("LOOP - {}, {}", n.getBranchCode(), userProfile.getStateCd());
					if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(), userProfile.getStateCd())) {
						branchId[0] = n.getBranchId();
					}
				});
				createUserProfile.setBranchId(branchId[0]);
			}

			if (!BaseUtil.isObjNull(userProfile.getUserGroupRoleBranchCd())) {
				createUserProfile.setUserGroupRoleBranchCd(userProfile.getUserGroupRoleBranchCd());
				createUserProfile.setUserGroupRoleBranchCode(userProfile.getUserGroupRoleBranchCd());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				LOGGER.info("JANE_------------------------{}", userProfile.getUserGroupRoleBranchCd());
				userGroupBranch.setBranchCode(userProfile.getUserGroupRoleBranchCd());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				ug.forEach(n -> {
					LOGGER.info("LOOP - {}, {}", n.getBranchCode(), userProfile.getUserGroupRoleBranchCd());
					if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(), userProfile.getUserGroupRoleBranchCd())) {
						branchId[0] = n.getBranchId();
					}
				});
				createUserProfile.setBranchId(branchId[0]);
			} else if (!BaseUtil.isObjNull(authUser) && !BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCode())) {
				createUserProfile.setUserGroupRoleBranchCode(authUser.getUserGroupRoleBranchCode());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				userGroupBranch.setBranchCode(authUser.getUserGroupRoleBranchCode());
				LOGGER.info("TEST: {}", authUser.getUserGroupRoleBranchCode());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				if (!BaseUtil.isListNullZero(ug)) {
					ug.forEach(n -> {
						LOGGER.info("LOOP - {}, {}", n.getBranchCode(), authUser.getUserGroupRoleBranchCode());
						if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(),
								authUser.getUserGroupRoleBranchCode())) {
							branchId[0] = n.getBranchId();
						}
					});
					createUserProfile.setBranchId(branchId[0]);
				}
			}

			if (BaseUtil.isObjNull(userProfile.getEmail())) {
				createUserProfile = getIdmService().createUser(createUserProfile, true, true, false, false);
			} else {
				createUserProfile = getIdmService().createUser(createUserProfile, true, true);
			}

			if (createUserProfile != null) {
				mav.addAllObjects(PopupBox.success(PFX_USRCREATE, null,
						messageService.getMessage(MessageConstants.SUCC_CREATE_USER),
						PageConstants.PAGE_IDM_USR_LST));
			} else {
				mav.addAllObjects(PopupBox.error(PFX_USRCREATE, null,
						messageService.getMessage(MessageConstants.ERROR_CRE_USER),
						PageConstants.PAGE_IDM_USR_LST + PAGE_USER_ID + id));
			}
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRCREATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		} catch (Exception e) {
			
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			mav.addAllObjects(PopupBox.error(PFX_USRCREATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
	}


	private void setDocument(CustomMultipartFile file, FileUpload fu, UserProfile createUserProfile,
			BindingResult result) {
		if (file != null) {
			Documents doc = new Documents();
			doc.setContentType(file.getContentType());
			doc.setFilename(file.getFilename());
			doc.setDocid(fu.getDocId());
			doc.setLength(file.getSize());
			doc.setRefno(createUserProfile.getUserId());
			doc.setContent(file.getContent());
			if (!BaseUtil.isObjNull(doc.getContent()) && (doc.getContent().length != 0)
					|| (BaseUtil.isObjNull(doc.getId()) && (doc.getContent().length != 0))) {
				trySetDocCreUsrProf(createUserProfile, doc, result);
			}
		}
	}


	private void tryUpdateUser(UserProfile createUserProfile, ModelAndView mav, String id) {
		try {
			if (BaseUtil.isEqualsCaseIgnore("id", id)) {
				mav.addObject(AppConstants.PORTAL_MODULE, PFX_IDMUPD);
				createUserProfile.setUserId(getCurrentUserId());
			} else {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_UPDUSER);
			}
			
			boolean isUpdated = getIdmService().updateProfile(createUserProfile);

			if (isUpdated) {
				if (BaseUtil.isEqualsCaseIgnore("id", id)) {
					mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
							messageService.getMessage(MessageConstants.SUCC_UPD_USER),
							PageConstants.PAGE_IDM_USR_UPD_LOGIN));
				} else {
					mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
							messageService.getMessage(MessageConstants.SUCC_UPD_USER),
							PageConstants.PAGE_IDM_USR_LST));
				}
			} else {
				mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.ERROR_UPD_USER),
						PageConstants.PAGE_IDM_USR_LST + PAGE_USER_ID + id));
			}
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		} catch (Exception e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
	}


	/**
	 * User Profile Update
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.UPDATE_USER)
	@PostMapping(value = "/user", params = "update")
	public ModelAndView update(@RequestParam String id,
			@Valid @ModelAttribute("userProfile") IdmUploadProfilePictureForm userProfile, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CRED, MODULE, null, null, JS_FILENAME);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.usr.upd"));
		if (!result.hasErrors()) {
			UserProfile authUser = getCurrentUser();
			userProfile = dozerMapper.map(userProfile, IdmUploadProfilePictureForm.class);
			
			if (hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) {
				if (BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
					userProfile.setUserGroupCode(userProfile.getUserRoleGroupCode());
				}
				trySetUser(userProfile, mav);
			} else {
				userProfile.setUserTypeCode(authUser.getUserType().getUserTypeCode());
			}
			UserProfile createUserProfile = dozerMapper.map(userProfile, UserProfile.class);
			createUserProfile.setCntryCd(userProfile.getStateCd());
			
			if (!BaseUtil.isObjNull(userProfile.getStateCd())) {
				createUserProfile.setCntryCd(userProfile.getStateCd());
			} else if (!BaseUtil.isObjNull(authUser) && !BaseUtil.isObjNull(authUser.getCntryCd())) {
				createUserProfile.setCntryCd(authUser.getCntryCd());
				if(!BaseUtil.isObjNull(authUser.getCntryCd())
						&& !BaseUtil.isObjNull(authUser.getBranchId())) {
					createUserProfile.setBranchId(authUser.getBranchId());
				}
			}

			if (!BaseUtil.isObjNull(createUserProfile.getCntryCd())
					&& BaseUtil.isObjNull(authUser.getBranchId())) {
				LOGGER.info("JANE_STATE------------------------{}", userProfile.getStateCd());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				userGroupBranch.setBranchCode(userProfile.getStateCd());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				ug.forEach(n -> {
					if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(), createUserProfile.getCntryCd())) {
						branchId[0] = n.getBranchId();
					}
				});
				createUserProfile.setBranchId(branchId[0]);
			}

			if (!BaseUtil.isObjNull(userProfile.getUserGroupRoleBranchCd())) {
				createUserProfile.setUserGroupRoleBranchCd(userProfile.getUserGroupRoleBranchCd());
				createUserProfile.setUserGroupRoleBranchCode(userProfile.getUserGroupRoleBranchCd());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				LOGGER.info("JANE_------------------------{}", userProfile.getUserGroupRoleBranchCd());
				userGroupBranch.setBranchCode(userProfile.getUserGroupRoleBranchCd());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				ug.forEach(n -> {
					if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(), createUserProfile.getUserGroupRoleBranchCd())) {
						branchId[0] = n.getBranchId();
					}
				});
				createUserProfile.setBranchId(branchId[0]);
			} else if (!BaseUtil.isObjNull(authUser) && !BaseUtil.isObjNull(authUser.getUserGroupRoleBranchCode())) {
				createUserProfile.setUserGroupRoleBranchCode(authUser.getUserGroupRoleBranchCode());
				UserGroupBranch userGroupBranch = new UserGroupBranch();
				userGroupBranch.setBranchCode(authUser.getUserGroupRoleBranchCode());
				LOGGER.info("TEST: {}", authUser.getUserGroupRoleBranchCode());
				List<UserGroupBranch> ug = getIdmService().searchUserGroupBranch(userGroupBranch);
				Integer[] branchId = new Integer[1];
				if (!BaseUtil.isListNullZero(ug)) {
					ug.forEach(n -> {
						LOGGER.info("LOOP - {}, {}", n.getBranchCode(), authUser.getUserGroupRoleBranchCode());
						if (BaseUtil.isEqualsCaseIgnore(n.getBranchCode(),
								authUser.getUserGroupRoleBranchCode())) {
							branchId[0] = n.getBranchId();
						}
					});
					createUserProfile.setBranchId(branchId[0]);
				}
			}
			
			if (BaseUtil.isEqualsCaseIgnore("new", id)) {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_NEWUSER);
				tryUpdUsrCreUser(createUserProfile, mav, id);
			} else {
				tryUpdUsrUpdateUser(userProfile, createUserProfile, mav, result, id);
			}
		}

		addCheck(userProfile, mav);

		mav.addObject(PFX_ISADMIN, hasAnyRole(IdmRoleConstants.ADMIN_ROLES));
		mav.addObject(PFX_USERPROFILE, userProfile);
		mav.addObject(PFX_ISINACTIVE, false);
		mav.addObject(PFX_USERID, id);
		return mav;
	}


	private void tryUpdUsrCreUser(UserProfile createUserProfile, ModelAndView mav, String id) {
		try {
			createUserProfile = getIdmService().createUser(createUserProfile);
			if (createUserProfile != null) {
				mav.addAllObjects(PopupBox.success(PFX_USRCREATE, null,
						messageService.getMessage(MessageConstants.SUCC_CRE_USER),
						PageConstants.PAGE_IDM_USR_LST));
			} else {
				mav.addAllObjects(PopupBox.error(PFX_USRCREATE, null,
						messageService.getMessage(MessageConstants.ERROR_CRE_USER),
						PageConstants.PAGE_IDM_USR_LST + PAGE_USER_ID + id));
			}
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRCREATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		} catch (Exception e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRCREATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
	}


	private void tryUpdUsrUpdateUser(IdmUploadProfilePictureForm userProfile, UserProfile createUserProfile,
			ModelAndView mav, BindingResult result, String id) {
		try {
			if (BaseUtil.isEqualsCaseIgnore("id", id)) {
				mav.addObject(AppConstants.PORTAL_MODULE, PFX_IDMUPD);
				createUserProfile.setUserId(getCurrentUserId());
			} else {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_UPDUSER);
			}
			// Profile Picture is only one
			if (!BaseUtil.isListNullZero(userProfile.getFileUploads()) && userProfile.getFileUploads().size() == 1) {

				FileUpload fu = userProfile.getFileUploads().get(0);
				CustomMultipartFile file = fu.getFile();
				setUpdUsrDocument(file, fu, createUserProfile, result);
			}

			boolean isUpdated = getIdmService().updateProfile(createUserProfile);

			if (isUpdated) {
				if (BaseUtil.isEqualsCaseIgnore("id", id)) {
					mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
							messageService.getMessage(MessageConstants.SUCC_UPD_USER),
							PageConstants.PAGE_IDM_USR_UPD_LOGIN));
				} else {
					if (hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) { // admin
						mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
								messageService.getMessage(MessageConstants.SUCC_UPD_USER),
								PageConstants.PAGE_IDM_USR_LST));
					} else { // normal user
						mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
								messageService.getMessage(MessageConstants.SUCC_UPD_USER),
								PageConstants.PAGE_IDM_USR_LST + "/user/" + id));
					}

				}
			} else {
				mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.ERROR_UPD_USER),
						PageConstants.PAGE_IDM_USR_LST + PAGE_USER_ID + id));
			}
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		} catch (Exception e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
		}
	}


	private void setUpdUsrDocument(CustomMultipartFile file, FileUpload fu, UserProfile createUserProfile,
			BindingResult result) {
		if (file != null) {
			Documents doc = new Documents();
			doc.setContentType(file.getContentType());
			doc.setFilename(file.getFilename());
			doc.setDocid(fu.getDocId());
			if (!BaseUtil.isObjNull(fu.getDocMgtId())) {
				doc.setId(fu.getDocMgtId());
			}
			doc.setLength(file.getSize());
			doc.setRefno(createUserProfile.getUserId());
			doc.setContent(file.getContent());
			if (!BaseUtil.isObjNull(doc.getContent()) && (doc.getContent().length != 0)
					|| (BaseUtil.isObjNull(doc.getId()) && (doc.getContent().length != 0))) {

				trySetDocCreUsrProf(createUserProfile, doc, result);
			}
		}
	}


	/**
	 * User Profile Update
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.UPDATE_USER)
	@PostMapping(value = "/user", params = "updateProfile")
	public ModelAndView updateProfile(@RequestParam String id, @RequestParam String updateProfile,
			@Valid @ModelAttribute("userProfile") IdmUploadProfilePictureForm userProfile, BindingResult result) {
		ModelAndView mav = getDefaultMav(PageTemplate.TEMP_IDM_USR_CRED, MODULE, null);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.usr.upd"));
		if (!result.hasErrors()) {
			UserProfile pr = getCurrentUser();
			userProfile = dozerMapper.map(userProfile, IdmUploadProfilePictureForm.class);
			if (hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) {
				if (BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
					userProfile.setUserGroupCode(userProfile.getUserRoleGroupCode());
				}
				trySetUser(userProfile, mav);
			} else {
				userProfile.setUserTypeCode(pr.getUserType().getUserTypeCode());
			}
			UserProfile createUserProfile = dozerMapper.map(userProfile, UserProfile.class);

			if (BaseUtil.isEqualsCaseIgnore("new", id)) {
				mav.addObject(AppConstants.PORTAL_TRANS_ID, PFX_NEWUSER);
				tryUpdUsrCreUser(createUserProfile, mav, id);

			} else {
				tryUpdUsrUpdateUser(userProfile, createUserProfile, mav, result, id);
			}
		}

		addCheck(userProfile, mav);

		mav.addObject(PFX_ISADMIN, hasAnyRole(IdmRoleConstants.ADMIN_ROLES));
		mav.addObject(PFX_USERPROFILE, userProfile);
		mav.addObject(PFX_ISINACTIVE, false);
		mav.addObject(PFX_USERID, id);
		return mav;
	}


	private void trySetDocCreUsrProf(UserProfile createUserProfile, Documents doc, BindingResult result) {
		try {
			Documents newDoc = getDmService(ProjectEnum.OUTBREAK).upload(doc);
			LOGGER.info(PRINT_PROF_PIC_DOCMGTID, newDoc.getId());
			createUserProfile.setDocMgtId(newDoc.getId());
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (DmException e) {
			String errorCode = e.getInternalErrorCode();
			LOGGER.error(PRINT_DOM_RESP_ERR, errorCode, e.getMessage());
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			result.rejectValue(FILE_UPLOAD, "", PRINT_UNBL_PROC_REQ + errorCode + "]");

		} catch (Exception e) {
			if (WebUtil.checkTokenError(e) 
					|| WebUtil.checkSystemDown(e)) {
				throw e;
			}
			String errorCode = null;
			result.rejectValue(FILE_UPLOAD, "", PRINT_UNBL_PROC_REQ + errorCode + "]");
		}

	}


	/**
	 * Deactivate User
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return
	 */
	@AuditActionControl(action = AuditActionPolicy.DEACTIVATE_USER)
	@PostMapping(value = "/user", params = "deactivate")
	public ModelAndView deactivate(@RequestParam String deactivate, @RequestParam String id,
			IdmUploadProfilePictureForm userProfile, BindingResult result) {
		LOGGER.info("[Post] User Deactivate: {}", id);
		ModelAndView mav = viewProfile(id, userProfile, result);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.usr.dea"));
		userProfile = (IdmUploadProfilePictureForm) mav.getModelMap().get(PFX_USERPROFILE);

		if (!BaseUtil.isEqualsCaseIgnore("id", id) && !BaseUtil.isEqualsCaseIgnore("new", id)
				&& !BaseUtil.isObjNull(userProfile)) {
			try {
				userProfile.setFullName(BaseUtil.getStrUpperWithNull(userProfile.getFullName()));
				userProfile.setStatus(AppConstants.INACTIVE);
				userProfile.setUserTypeCode(userProfile.getUserTypeCode());

				UserProfile userProfileObj = dozerMapper.map(userProfile, UserProfile.class);
				boolean isUpdated = getIdmService().updateProfile(userProfileObj);
				if (isUpdated) {
					mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
							messageService.getMessage(MessageConstants.SUCC_DEACTIVATE_USER),
							PageConstants.PAGE_IDM_USR_LST));
				} else {
					mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
							messageService.getMessage(MessageConstants.ERROR_FAIL_DEACTIVATE_USER),
							PageConstants.PAGE_IDM_USR_LST));
				}
				mav.addObject(PFX_ISINACTIVE, true);
			} catch (IdmException e) {
				LOGGER.error(PRINT_IDM_RESP_ERR, e.getInternalErrorCode(), e.getMessage());
				if (WebUtil.checkTokenError(e) 
						|| WebUtil.checkSystemDown(e)) {
					throw e;
				}
				mav.addAllObjects(PopupBox.error(e.getInternalErrorCode(), e.getMessage()));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
				mav.addAllObjects(PopupBox.error("userUpdate",
						messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
			}
		}

		return mav;
	}


	/**
	 * Activate User
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.ACTIVATE_USER)
	@PostMapping(value = "/user", params = "activate")
	public ModelAndView activate(@RequestParam String activate, @RequestParam String id,
			IdmUploadProfilePictureForm userProfile, BindingResult result) {
		LOGGER.info("[Post] User Activate: {}", id);
		ModelAndView mav = viewProfile(id, userProfile, result);
		mav.addObject(AppConstants.PAGE_TITLE, messageService.getMessage("lbl.cmn.usr.act"));
		userProfile = (IdmUploadProfilePictureForm) mav.getModelMap().get(PFX_USERPROFILE);

		if (!BaseUtil.isEqualsCaseIgnore("id", id) && !BaseUtil.isEqualsCaseIgnore("new", id)
				&& !BaseUtil.isObjNull(userProfile)) {
			try {
				userProfile.setUserTypeCode(userProfile.getUserTypeCode());
				UserProfile userProfileObj = dozerMapper.map(userProfile, UserProfile.class);
				getIdmService().activateUser(userProfileObj);
				mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.SUCC_ACTIVATE_USER),
						PageConstants.PAGE_IDM_USR_LST));
			} catch (IdmException e) {
				LOGGER.error(PRINT_IDM_RESP_ERR, e.getInternalErrorCode(), e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
				mav.addAllObjects(PopupBox.error(e.getInternalErrorCode(), e.getMessage()));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
				mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.ERROR_FAIL_ACTIVATE_USER),
						PageConstants.PAGE_IDM_USR_LST));
			}
		}

		return mav;
	}


	/**
	 * Resend User Credentials
	 *
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.RESEND_CRED)
	@PostMapping(value = "/user", params = "resent")
	public ModelAndView resentCredential(@RequestParam String resent, @RequestParam String id,
			IdmUploadProfilePictureForm userProfile, BindingResult result) {
		LOGGER.info("[Post] Resent User credential: {}", id);
		ModelAndView mav = viewProfile(id, userProfile, result);
		userProfile = (IdmUploadProfilePictureForm) mav.getModelMap().get(PFX_USERPROFILE);

		if (!BaseUtil.isEqualsCaseIgnore("id", id) && !BaseUtil.isEqualsCaseIgnore("new", id)
				&& !BaseUtil.isObjNull(userProfile)) {
			try {
				userProfile.setUserTypeCode(userProfile.getUserTypeCode());
				UserProfile userProfileObj = dozerMapper.map(userProfile, UserProfile.class);
				getIdmService().activateUser(userProfileObj);
				mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.SUCC_RESENT_USER_CRE),
						PageConstants.PAGE_IDM_USR_LST));
			} catch (IdmException e) {
				LOGGER.error(e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
			} catch (Exception e) {
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
				mav.addAllObjects(PopupBox.success(PFX_USRUPDATE, null,
						messageService.getMessage(MessageConstants.ERROR_RESENT_USER_CRE),
						PageConstants.PAGE_IDM_USR_LST));
			}
		}

		return mav;
	}


	/**
	 * Reset Password
	 *
	 * @param userId
	 * @param userProfile
	 * @param result
	 * @return @
	 */
	@AuditActionControl(action = AuditActionPolicy.RESET_PWORD)
	@PostMapping(value = "/user", params = "resetPwd")
	public ModelAndView forgotPword(@RequestParam String resetPwd, @RequestParam String id,
			IdmUploadProfilePictureForm userProfile, BindingResult result) {
		LOGGER.info("[POST] Reset Password : {}", id);
		ModelAndView mav = viewProfile(id, userProfile, result);

		try {
			ForgotPassword forgotPassword = new ForgotPassword();
			forgotPassword.setUserName(id);
			getIdmService().forgotPassword(forgotPassword);
			mav.addAllObjects(
					PopupBox.success(null, null, messageService.getMessage(MessageConstants.SUCC_FORGOT_PWORD),
							PageConstants.PAGE_IDM_USR_LST));
		} catch (IdmException e) {
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(PRINT_IDM_RESP_ERR, e.getInternalErrorCode(), e.getMessage());
			mav.addAllObjects(PopupBox.error(e.getInternalErrorCode(), e.getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mav.addAllObjects(PopupBox.error(PFX_USRUPDATE, PFX_ERROR,
					messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS)));
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}

		return mav;
	}


	private UserGroup findAdminRoleGroupCodeByUserGroup(String userGroup, String userLevel) {
		List<UserGroup> roleGroupCode = findAllUserRoleGroupByGroupCode(userGroup);
		for (UserGroup prUserGroup : roleGroupCode) {
			if (BaseUtil.isEquals(prUserGroup.getParentRoleGroup(), userLevel)) {
				return prUserGroup;
			}
		}
		return null;
	}


	private List<UserGroup> findAllUserRoleGroupByGroupCode(String groupCode) {
		LOGGER.debug("finding all with Id: {}", groupCode);

		String parentRoleGroup = getParentRoleGroup();
		LOGGER.debug("parentRoleGroup: {}", parentRoleGroup);

		List<UserGroup> userGroupList = new ArrayList<>();
		try {
			List<UserGroup> result = getIdmService().findUserGroupByRoleGroupCode(groupCode, true, true);
			for (UserGroup userGroup : result) {
				if (BaseUtil.isEquals(userGroup.getParentRoleGroup(), parentRoleGroup)) {
					userGroupList.add(userGroup);
				}
			}
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}

		return userGroupList;
	}


	private String getParentRoleGroup() {
		String parentRoleGroup = BaseConstants.EMPTY_STRING;
		UserProfile authUser = getCurrentUser();
		if (!BaseUtil.isObjNull(authUser)) {
			LOGGER.debug("authUser: {}", authUser);
			String userRoleGroup = BaseUtil.getStr(authUser.getUserRoleGroupCode());
			LOGGER.debug("userRoleGroup: {}", userRoleGroup);
			List<UserGroup> userGroups = getIdmService().findUserGroupByRoleGroupCode(userRoleGroup, true, true);
			LOGGER.debug("userGroups: {}", userGroups);
			for (UserGroup userGroup : userGroups) {
				if (BaseUtil.isEquals(userGroup.getUserRoleGroupCode(), userRoleGroup)) {
					parentRoleGroup = userGroup.getUserRoleGroupCode();
					break;
				}
			}
		}

		LOGGER.debug("parentRoleGroup: {}", parentRoleGroup);
		return parentRoleGroup;
	}


	@AuditActionControl(action = AuditActionPolicy.RESEND_CRED)
	@PostMapping(value = "/userGroup/child/{userGroup}")
	public @ResponseBody UserGroup getUserRoleGroup(@PathVariable String userGroup,
			IdmUploadProfilePictureForm userProfile, BindingResult result) {
		if (BaseUtil.isObjNull(userGroup)) {
			return new UserGroup();
		}

		List<UserGroup> ugLst = getUserRoleGroupList();
		return ugLst.stream().filter(ugr -> userGroup.equals(ugr.getUserGroupCode())).findFirst().orElse(new UserGroup());
	}


	@ModelAttribute("userRoleGroupList")
	public List<UserGroup> getUserRoleGroupList() {
		List<UserGroup> sortedLst = new ArrayList<>();
		if (hasAnyRole(IdmRoleConstants.ADMIN_ROLES)) {
			try {
				List<UserGroup> userGroupList = getIdmService()
						.findUserGroupByParentRoleGroup(getCurrentUser().getUserRoleGroup().getUserGroupCode());
				sortedLst = userGroupList.stream().sorted(Comparator.comparing(UserGroup::getUserGroupDesc))
						.collect(Collectors.toList());
			} catch (IdmException e) {
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
					throw e;
				}
			}
		}
		return sortedLst;
	}


	@ModelAttribute("districtList")
	public List<UserGroupBranch> getDistrict() {
		List<UserGroupBranch> sortedState = new ArrayList<>();

		try {
			UserProfile authUser = getCurrentUser();
			UserGroupBranch userGroupBranch = new UserGroupBranch();
			userGroupBranch.setUserGroupCode("DIST_ADMIN");
			userGroupBranch.setStateCd(authUser.getCntryCd());
			LOGGER.info("Retrieve List of District from current role : {}",
					authUser.getUserRoleGroup().getUserGroupCode(), authUser.getBranchId());
			List<UserGroupBranch> ugbList = getIdmService().searchUserGroupBranch(userGroupBranch);
			sortedState = ugbList.stream().filter(t -> {
				// This code filters the branch related to state
				return !t.getBranchId().equals(authUser.getBranchId());
			}).sorted(Comparator.comparing(UserGroupBranch::getBranchName))
					.collect(Collectors.toList());
		} catch (BeException e) {
			LOGGER.error(BaseConstants.LOG_BE_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}
		return sortedState;
	}

	private int getMaxNoOfUser() {
		int maxNumberOfUser = 0;
		UserProfile authUser = getCurrentUser();
		try {
			List<UserGroup> userGroupList = getIdmService()
					.findUserGroupByRoleGroupCode(authUser.getUserRoleGroupCode(), true, true);
			for (UserGroup userGroup : userGroupList) {
				if (BaseUtil.isEquals(userGroup.getUserRoleGroupCode(), authUser.getUserRoleGroupCode())) {
					maxNumberOfUser = userGroup.getMaxNoOfUser();
					break;
				}
			}
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e) || WebUtil.checkSystemDown(e)) {
				throw e;
			}
		}

		return maxNumberOfUser;
	}

}