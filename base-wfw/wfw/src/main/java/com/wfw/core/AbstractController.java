/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wfw.constant.QualifierConstants;
import com.wfw.dao.WfwInboxHistDao;
import com.wfw.dto.CmnDto;
import com.wfw.service.CommonService;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
public abstract class AbstractController {

	@Autowired
	private CmnDto cmnDto;

	@Autowired
	private CommonService commonService;

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_HIST_DAO)
	protected WfwInboxHistDao wfwInboxHistDao;


	public CmnDto getCommonService() {
		return cmnDto;
	}


	public CommonService getCmmnService() {
		return commonService;
	}

}