/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseboot.idm.constants.IdmTxnCodeConstants;
import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractRestController;
import com.baseboot.idm.model.IdmUserType;
import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.constants.IdmUrlConstrants;
import com.baseboot.idm.sdk.exception.IdmException;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.util.MediaType;
import com.baseboot.idm.service.IdmUserTypeService;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@RestController
@RequestMapping(IdmUrlConstrants.USER_TYPE)
public class IdmUserTypeRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserTypeRestController.class);

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_TYPE_SERVICE)
	private IdmUserTypeService idmUserTypeService;


	/**
	 * Fetch ALL User Types
	 *
	 * @return List<IdmUserType>
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public List<IdmUserType> getAllListUserType() {
		List<IdmUserType> userGroupList = new ArrayList<>();

		List<IdmUserType> userList = idmUserTypeService.findAll();

		if (BaseUtil.isListNull(userList)) {
			throw new IdmException(IdmErrorCodeEnum.I404IDM160);
		}

		for (IdmUserType refUserType : userList) {
			userGroupList.add(dozerMapper.map(refUserType, IdmUserType.class));
		}

		return userGroupList;
	}


	/**
	 * Create User Type
	 *
	 * @param tr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmUserType addUserType(@Valid @RequestBody IdmUserType tr, HttpServletRequest request,
			HttpServletResponse response) {
		if (tr == null) {
			throw new IdmException(IdmErrorCodeEnum.E404IDM140);
		}
		LOGGER.debug("tr.getUserTypeCode() : {}", tr.getUserTypeCode());
		LOGGER.debug("tr.getUserTypeDesc() : {}", tr.getUserTypeDesc());
		if (!StringUtils.hasText(tr.getUserTypeCode()) || !StringUtils.hasText(tr.getUserTypeDesc())) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.hasText(tr.getUserTypeCode())) {
				sb.append("userTypeCode,");
			}
			if (!StringUtils.hasText(tr.getUserTypeDesc())) {
				sb.append("userTypeDesc");
			}

			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I400IDM137,
					new String[] { sb.toString(), "UserType" });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}

		IdmUserType roleObj = idmUserTypeService.findByUserTypeCode(tr.getUserTypeCode().trim());
		if (roleObj != null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I409IDM136,
					new String[] { tr.getUserTypeCode() });
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		String userId = BaseUtil.getStr(request.getAttribute("currUserId"));
		IdmUserType tidmRoleC = null;
		tr.setCreateId(userId);
		tr.setUpdateId(userId);

		try {
			tidmRoleC = idmUserTypeService.create(tr);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM153);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_NEW);
		return tidmRoleC;

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
	@RequestMapping(value = "{userTypeCode}", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public IdmUserType updateUserType(@PathVariable String userTypeCode, @Valid @RequestBody IdmUserType userType,
			HttpServletRequest request, HttpServletResponse response) {
		IdmUserType userType1 = dozerMapper.map(userType, IdmUserType.class);
		if (userType1 == null) {
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM148);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		userType1.setUpdateId(BaseUtil.getStr(request.getAttribute("currUserId")));
		userType1.setUpdateDt(new Timestamp(new Date().getTime()));

		try {
			idmUserTypeService.update(userType1);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
			IdmException idmEx = new IdmException(IdmErrorCodeEnum.I404IDM159);
			LOGGER.error("IdmException: {}", idmEx.getMessage());
			throw idmEx;
		}
		request.setAttribute(IdmTxnCodeConstants.PERMISSION_CODE, IdmTxnCodeConstants.IDM_ROLE_UPD);
		return dozerMapper.map(userType1, IdmUserType.class);
	}

}
