package com.idm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idm.constants.IdmTxnCodeConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractRestController;
import com.idm.model.IdmUserType;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.constants.IdmUrlConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserType;
import com.idm.service.IdmUserTypeService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;
import com.util.constants.BaseConstants;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstants.USER_TYPE)
public class UserTypeRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeRestController.class);

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_TYPE_SERVICE)
	private IdmUserTypeService idmUserTypeService;


	/**
	 * Fetch ALL User Types
	 *
	 * @return List<IdmUserType>
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/all", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<UserType> getAllListUserType() {
		List<IdmUserType> userList = idmUserTypeService.findAll();

		if (BaseUtil.isListNull(userList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		try {
			return JsonUtil.transferToList(userList, UserType.class);
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}
	}


	/**
	 * Fetch User Types by Code
	 *
	 * @return List<IdmUserType>
	 * @throws IOException
	 */
	@GetMapping(value = "/{userTypeCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserType getUserTypeByCode(@PathVariable String userTypeCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (BaseUtil.isObjNull(userTypeCode)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		IdmUserType userType = idmUserTypeService.findByUserTypeCode(userTypeCode);

		if (!BaseUtil.isObjNull(userType)) {
			return JsonUtil.transferToObject(userType, UserType.class);
		}
		return null;
	}


	/**
	 * Create User Type
	 *
	 * @param tr
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public UserType addUserType(@Valid @RequestBody UserType tr, HttpServletRequest request,
			HttpServletResponse response) {
		if (tr == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		LOGGER.debug("tr.getUserTypeCode() : {}", tr.getUserTypeCode());
		LOGGER.debug("tr.getUserTypeDesc() : {}", tr.getUserTypeDesc());
		if (!StringUtils.hasText(tr.getUserTypeCode())) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.hasText(tr.getUserTypeCode())) {
				sb.append("userTypeCode,");
			}

			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM137,
					new String[] { sb.toString(), "UserType" });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

		IdmUserType roleObj = idmUserTypeService.findByUserTypeCode(tr.getUserTypeCode().trim());
		if (roleObj != null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I409IDM136,
					new String[] { tr.getUserTypeCode() });
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
		String userId = BaseUtil.getStr(request.getAttribute("currUserId"));

		try {
			IdmUserType idmUserType = JsonUtil.transferToObject(tr, IdmUserType.class);
			idmUserType.setCreateId(userId);
			idmUserType.setUpdateId(userId);
			idmUserType = idmUserTypeService.create(idmUserType);
			request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_NEW);
			return JsonUtil.transferToObject(idmUserType, UserType.class);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM153);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}

	}


	/**
	 * Update User Type
	 *
	 * @param userTypeCode
	 * @param userType
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "{userTypeCode}", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public UserType updateUserType(@PathVariable String userTypeCode, @Valid @RequestBody UserType userType,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			IdmUserType userType1 = JsonUtil.transferToObject(userType, IdmUserType.class);
			if (userType1 == null) {
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM148);
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}

			userType1.setUserTypeCode(userType.getUserTypeCode());
			userType1.setUserTypeDesc(userType.getUserTypeDesc());
			userType1.setEmailAsUserId(userType.getEmailAsUserId());
			userType1.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
			userType1.setUpdateDt(new Timestamp(new Date().getTime()));

			idmUserTypeService.update(userType1);
			request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_UPD);
			return JsonUtil.transferToObject(userType1, UserType.class);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
			LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
			throw idmEx;
		}
	}


	@PostMapping(value = "/delete", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public boolean deleteUserType(@Valid @RequestBody UserType userType, HttpServletRequest request,
			HttpServletResponse response) {
		if (!BaseUtil.isObjNull(userType)) {
			try {
				IdmUserType idmUserType = JsonUtil.transferToObject(userType, IdmUserType.class);
				return idmUserTypeService.delete(idmUserType);

			} catch (Exception e) {
				LOGGER.error("Exception: ", e);
				IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
				LOGGER.error(BaseConstants.LOG_IDM_EXCEPTION, idmEx.getMessage());
				throw idmEx;
			}
		} else {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}

	}


	@PostMapping(value = IdmUrlConstants.SEARCH_PAGINATION, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public DataTableResults<UserType> searchUserProfilePaginated(@Valid @RequestBody UserType userType,
			HttpServletRequest request) {
		DataTableRequest<UserType> dataTableInRQ = new DataTableRequest<>(request.getParameterMap());
		List<UserType> all = idmUserTypeService.searchByPagination(userType, null);
		List<UserType> filtered = idmUserTypeService.searchByPagination(userType, dataTableInRQ);

		LOGGER.info("ALL DATA: {}", all);
		LOGGER.info("ALL filtered DATA : {}", filtered);

		return new DataTableResults<>(dataTableInRQ, all.size(), filtered);
	}

}
