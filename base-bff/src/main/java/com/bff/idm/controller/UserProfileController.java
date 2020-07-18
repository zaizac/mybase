/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.idm.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.constants.FileUploadConstants;
import com.be.sdk.model.Document;
import com.bff.cmn.form.CustomMultipartFile;
import com.bff.config.audit.AuditActionControl;
import com.bff.config.audit.AuditActionPolicy;
import com.bff.core.AbstractController;
import com.bff.idm.form.IdmUploadProfilePictureForm;
import com.bff.util.WebUtil;
import com.bff.util.constants.AppConstants;
import com.bff.util.constants.MessageConstants;
import com.bff.util.constants.PageConstants;
import com.dm.sdk.exception.DmException;
import com.dm.sdk.model.Documents;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.ForgotPassword;
import com.idm.sdk.model.UserGroup;
import com.idm.sdk.model.UserGroupBranch;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_USR_LST)
public class UserProfileController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);


	@GetMapping(value = "/paginated", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public DataTableResults<UserProfile> getApplicationInfoPaginated(UserProfile userProfile, BindingResult result,
			@RequestParam("portalAdmin") boolean isPortalAdmin, HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....");
		DataTableResults<UserProfile> tasks = null;
		try {
			if (!BaseUtil.isObjNull(userProfile.getParentRoleGroup())) {
				String userRoleGroupCode = BaseUtil.getStr(userProfile.getParentRoleGroup());
				LOGGER.debug("userRoleGroup: {}", userRoleGroupCode);
				LOGGER.debug("isSuperAdmin: {}", isPortalAdmin);

				userProfile.setParentRoleGroup(userRoleGroupCode);

				if (isPortalAdmin) {
					userProfile.setUserType(null);
				} else {
					if (!BaseUtil.isObjNull(userRoleGroupCode)) {
						String userGroup = getUserGroupCode(userRoleGroupCode);
						userProfile.setUserGroupCode(userGroup);
					}
				}
			}

			tasks = getIdmService().searchUserProfile(userProfile, false, getPaginationRequest(request, true));
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}

		return tasks;
	}


	private String getUserGroupCode(String userRoleGroupCode) {
		List<UserGroup> userGroupList = new ArrayList<>();
		String userGroupCode = BaseConstants.EMPTY_STRING;
		try {
			userGroupList = getIdmService().findUserGroupByRoleGroupCode(userRoleGroupCode);
			for (UserGroup userGroup : userGroupList) {
				if (BaseUtil.isEquals(userGroup.getUserRoleGroupCode(), userRoleGroupCode)) {
					userGroupCode = userGroup.getUserGroupCode();
					break;
				}
			}
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}

		return userGroupCode;
	}


	/**
	 * Create User
	 *
	 * @param userProfile
	 * @return @
	 * @throws IOException
	 */
	@AuditActionControl(action = AuditActionPolicy.CREATE_USER)
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String createUser(@RequestBody IdmUploadProfilePictureForm userProfile, HttpServletRequest request)
			throws IOException {
		LOGGER.info("userProfile: {}", JsonUtil.objectMapper().valueToTree(userProfile));
		return addUser(userProfile, request);

	}


	/**
	 * Update User/ Update Profile
	 *
	 * @param userProfile
	 * @return @
	 * @throws IOException
	 */
	@AuditActionControl(action = AuditActionPolicy.UPDATE_USER)
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String updateUser(@RequestBody IdmUploadProfilePictureForm userProfile, HttpServletRequest request)
			throws IOException {
		LOGGER.info("userProfile: {}", objectMapper.valueToTree(userProfile));
		return addUser(userProfile, request);
	}


	/**
	 * Get All User Role Groups
	 *
	 * @param userType
	 * @param userRoleGroupCode
	 * @return
	 */
	@GetMapping(value = "/getUserRoleGroups", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserGroup> getUserRoleGroups(@RequestParam("userType") String userType,
			@RequestParam("userRoleGroupCode") String userRoleGroupCode,
			@RequestParam("hasMaxNoUserCreated") boolean hasMaxNoUserCreated,
			@RequestParam("noSystemCreate") boolean noSystemCreate, HttpServletRequest request) {

		List<UserGroup> userGroupList = new ArrayList<>();
		try {
			userGroupList = getIdmService(request).findUserGroupByParentRoleGroup(userRoleGroupCode,
					hasMaxNoUserCreated, noSystemCreate);
		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return userGroupList;
	}


	@GetMapping(value = "/userGroupBranch", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserGroupBranch> userGroupBranch(@RequestParam("userGroupCode") String branchCode,
			@RequestParam(value = "countryCode", required = false) String countryCode) {
		List<UserGroupBranch> userGroupList = new ArrayList<>();
		try {
			UserGroupBranch ugrb = new UserGroupBranch();
			ugrb.setUserGroupCode(branchCode);
			if (!BaseUtil.isObjNull(countryCode)) {
				ugrb.setCntryCd(countryCode);
			}
			List<UserGroupBranch> userGroupList1 = getIdmService().searchUserGroupBranch(ugrb);

			for (UserGroupBranch group : userGroupList1) {
				userGroupList.add(group);
			}

		} catch (IdmException e) {
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
			if (WebUtil.checkTokenError(e)) {
				throw e;
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return userGroupList;
	}


	private String addUser(IdmUploadProfilePictureForm userProfile, HttpServletRequest request) throws IOException {

		LOGGER.info("userProfile: {}", objectMapper.valueToTree(userProfile));

		if (BaseUtil.isEquals(userProfile.getIsAdmin(), "Y") && BaseUtil.isObjNull(userProfile.getUserGroupCode())) {
			userProfile.setUserRoleGroupCode(userProfile.getUserRoleGroupCode());
			userProfile.setUserType(userProfile.getUserType());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("userProfile: {}", objectMapper.valueToTree(userProfile));
		}

		UserProfile createUserProfile = JsonUtil.transferToObject(userProfile, UserProfile.class);

		if (userProfile.getUserId() == null) {
			try {
				// Profile Picture is only one
				uploadFile(userProfile.getFileUploads(), createUserProfile, request);

				createUserProfile = getIdmService().createUser(createUserProfile);
				if (createUserProfile != null) {
					return messageService.getMessage(MessageConstants.SUCC_CRE_USER);
				} else {
					return messageService.getMessage(MessageConstants.ERROR_CRE_USER);
				}
			} catch (IdmException e) {
				if (WebUtil.checkSystemDown(e)) {
					throw e;
				}

				return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);

			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
				return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
			}

		} else {
			try {
				if (userProfile.getUserId() != null) {
					createUserProfile.setUserId(userProfile.getUserId());
				}

				uploadFile(userProfile.getFileUploads(), createUserProfile, request);
				boolean isUpdated = getIdmService().updateProfile(createUserProfile);

				if (isUpdated) {
					return messageService.getMessage(MessageConstants.SUCC_UPD_USER);
				} else {
					return messageService.getMessage(MessageConstants.ERROR_UPD_USER);
				}
			} catch (IdmException e) {
				if (WebUtil.checkSystemDown(e)) {
					throw e;
				}
				return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
			} catch (Exception e) {
				return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
			}
		}

	}


	/**
	 * Get All Users
	 *
	 * @param userProfile
	 * @param isPortalAdmin
	 * @return
	 */
	@GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public List<UserProfile> getAllUsers(UserProfile userProfile,
			@RequestParam(value = "portalAdmin", required = false) boolean isPortalAdmin,
			HttpServletRequest request) {
		LOGGER.info("GET PAGINATED USER LIST....{}", JsonUtil.toJsonNode(userProfile));
		List<UserProfile> userProfileList = null;
		try {
			userProfile.setUserType(userProfile.getUserType());

			if (!BaseUtil.isObjNull(userProfile.getParentRoleGroup())) {
				String userRoleGroupCode = BaseUtil.getStr(userProfile.getParentRoleGroup());
				LOGGER.info("userRoleGroup: {}", userRoleGroupCode);
				LOGGER.info("isSuperAdmin: {}", isPortalAdmin);

				if (isPortalAdmin) {
					userProfile.setUserType(null);
				} else {
					if (!BaseUtil.isObjNull(userRoleGroupCode)) {
						String userGroup = getUserGroupCode(userRoleGroupCode);
						LOGGER.debug("userGroup code: {}", userGroup);
						userProfile.setUserGroupCode(userGroup);
					} else {
						LOGGER.debug("User role group not found.");
					}
				}
			}

			userProfileList = getIdmService().searchUsers(userProfile);
		} catch (Exception e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("GET PAGINATED USER LIST....{}", JsonUtil.toJsonNode(userProfileList));
		return userProfileList;
	}


	private void uploadFile(List<CustomMultipartFile> fileList, UserProfile createUserProfile,
			HttpServletRequest request) {
		if (BaseUtil.isListNullZero(fileList)) {
			return;
		}
		for (CustomMultipartFile file : fileList) {
			if (!BaseUtil.isObjNull(file) && !BaseUtil.isObjNull(file.getContent())
					&& (file.getContent().length != 0)
					|| (BaseUtil.isObjNull(file.getDocMgtId())
							&& (!BaseUtil.isObjNull(file.getContent()) && file.getContent().length != 0))) {
				Documents doc = new Documents();
				doc.setContentType(file.getContentType());
				doc.setFilename(file.getFilename());
				doc.setDocid(file.getDocId());
				doc.setId(file.getDocMgtId());
				doc.setLength(file.getSize());
				doc.setRefno(createUserProfile.getUserId());
				doc.setContent(file.getContent());

				try {
					Documents newDoc = getDmService(request, file.getDmBucket()).upload(doc);
					LOGGER.info("Profile Picture DocMgtId: {}", newDoc.getId());
					createUserProfile.setDocMgtId(newDoc.getId());
				} catch (IdmException e) {
					LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
					if (WebUtil.checkTokenError(e)) {
						throw e;
					}
				} catch (DmException e) {
					LOGGER.error("DmException: {}", e.getMessage());
					if (WebUtil.checkTokenError(e)) {
						throw e;
					}
					String errorCode = e.getInternalErrorCode();
					LOGGER.error("DOM Response Error: {} - {}", errorCode, e.getMessage());
					throw new DmException("Unable to process your request [" + errorCode + "]");
				} catch (Exception e) {
					throw new DmException("Unable to process your request " + e.getMessage());
				}
			}
		}

	}


	/**
	 * Find User
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/find", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public IdmUploadProfilePictureForm findUser(@RequestParam("userId") String userId, HttpServletRequest request) {
		LOGGER.info("Get User Details .... {}", userId);
		IdmUploadProfilePictureForm userProfile = null;

		UserProfile userProfileObj = null;

		try {
			userProfileObj = getIdmService().getUserProfileById(userId, false, false);
			userProfile = JsonUtil.transferToObject(userProfileObj, IdmUploadProfilePictureForm.class);
			userProfile.setCntry(userProfileObj.getCntryCd());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new IdmException(IdmErrorCodeEnum.E404IDM140.getMessage(),
					messageService.getMessage(MessageConstants.ERROR_NO_REC_FOUND));
		}

		// Retrieve Profile Picture
		List<CustomMultipartFile> fileUpload = new ArrayList<>();
		List<Document> refDocLst = staticData.findDocumentsByTrxnNo(FileUploadConstants.PROFILE_PIC);
		if (!BaseUtil.isListZero(refDocLst)) {
			for (Document refDoc : refDocLst) {
				CustomMultipartFile file = new CustomMultipartFile();
				Documents docs = null;
				if (userProfileObj.getDocMgtId() != null) {
					try {
						docs = getDmService(request, refDoc.getDmBucket()).getMetadata(userProfile.getDocMgtId());
						if (docs != null && refDoc.getDocId().equals(docs.getDocid())) {
							file = JsonUtil.transferToObject(docs, CustomMultipartFile.class);
						}
					} catch (IdmException e) {
						LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, e.getMessage());
						if (WebUtil.checkSystemDown(e)) {
							throw e;
						}
					} catch (DmException e) {
						LOGGER.error("DmException: {}", e.getMessage());
						if (WebUtil.checkSystemDown(e)) {
							throw e;
						}
					} catch (Exception e) {
						LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
					}
				}
				file.setDocId(refDoc.getDocId());
				file.setDocMgtId(userProfile.getDocMgtId());
				file.setCompulsary(refDoc.isCompulsary());
				fileUpload.add(file);
			}
			userProfile.setFileUploads(fileUpload);
		}

		return userProfile;

	}


	/**
	 * Resend User Credentials
	 *
	 * @param userId
	 * @return
	 */
	@AuditActionControl(action = AuditActionPolicy.RESEND_CRED)
	@GetMapping(value = "resentCredentials", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String resentCredentials(@RequestParam("userId") String userId) {
		LOGGER.info("[Post] Resent User credential: {}", userId);

		UserProfile userProfileObj = getIdmService().getUserProfileById(userId, false, false);
		if (userProfileObj == null) {
			LOGGER.error("User Object not found {}", userId);
			throw new IdmException("User Not Found");
		}

		UserProfile us = getIdmService().activateUser(userProfileObj);

		if (us != null) {
			return messageService.getMessage(MessageConstants.SUCC_RESENT_USER_CRE);
		} else {
			return messageService.getMessage(MessageConstants.ERROR_RESENT_USER_CRE);
		}

	}


	/**
	 * Reset Password
	 *
	 * @param userId
	 * @return
	 */
	@AuditActionControl(action = AuditActionPolicy.RESET_PWORD)
	@GetMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String resetPassword(@RequestParam("userId") String userId) {
		LOGGER.info("[Post] Reset User password: {}", userId);

		try {
			ForgotPassword forgotPassword = new ForgotPassword();
			forgotPassword.setUserName(userId);
			UserProfile pf = getIdmService().forgotPassword(forgotPassword);
			if (pf != null) {
				return messageService.getMessage(MessageConstants.SUCC_FORGOT_PWORD);
			} else {
				return messageService.getMessage(MessageConstants.ERR_FORGOT_PWORD);
			}

		} catch (IdmException e) {
			if (WebUtil.checkSystemDown(e)) {
				throw e;
			}
			LOGGER.error("IDM Response Error: {} - {}", e.getInternalErrorCode(), e.getMessage());
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		} catch (Exception e) {
			return messageService.getMessage(MessageConstants.ERROR_UNABLE_TO_PROCESS);
		}

	}


	/**
	 * Activate User
	 *
	 * @param userId
	 * @return
	 */
	@AuditActionControl(action = AuditActionPolicy.ACTIVATE_USER)
	@GetMapping(value = "/activate", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String activateUser(@RequestParam("userId") String userId) {
		LOGGER.info("[Post] Activate User : {}", userId);

		UserProfile userProfileObj = getIdmService().getUserProfileById(userId, false, false);
		if (userProfileObj == null) {
			LOGGER.error("User Object not found {}", userId);
			throw new IdmException("User Not Found");
		}

		UserProfile us = getIdmService().activateUser(userProfileObj);

		if (us != null) {
			return messageService.getMessage(MessageConstants.SUCC_ACTIVATE_USER);
		} else {
			return messageService.getMessage(MessageConstants.ERROR_FAIL_ACTIVATE_USER);
		}

	}


	/**
	 * Deactivate User
	 *
	 * @param userId
	 * @return
	 */
	@AuditActionControl(action = AuditActionPolicy.DEACTIVATE_USER)
	@GetMapping(value = "/deactivate", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String deActivateUser(@RequestParam("userId") String userId) {
		LOGGER.info("[Post] Deactivate User: {}", userId);

		if (userId == null) {
			LOGGER.error("User Object not found {}", userId);
			throw new IdmException(IdmErrorCodeEnum.E400IDM913);
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setUserId(userId);
		userProfile.setStatus(AppConstants.INACTIVE);

		boolean isUpdated = getIdmService().updateProfile(userProfile);
		if (isUpdated) {
			return messageService.getMessage(MessageConstants.SUCC_DEACTIVATE_USER);
		} else {
			return messageService.getMessage(MessageConstants.ERROR_FAIL_DEACTIVATE_USER);
		}
	}

}