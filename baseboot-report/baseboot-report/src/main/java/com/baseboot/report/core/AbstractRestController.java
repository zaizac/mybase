/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baseboot.be.sdk.constants.BaseConstants;
import com.baseboot.report.config.StaticData;
import com.baseboot.report.mail.MailAttachment;
import com.baseboot.report.mail.MailerInfo;
import com.baseboot.report.sdk.model.Report;
import com.baseboot.report.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public abstract class AbstractRestController extends GenericAbstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	protected StaticData staticData;

	@Autowired
	protected Mapper dozerMapper;


	protected String getRealPath() {
		return this.getClass().getResource(BaseConstants.SLASH).getPath();
	}


	protected String getCurrUserId(HttpServletRequest request) {
		String currUser = BaseUtil.getStr(request.getAttribute("currUserId"));
		return !BaseUtil.isObjNull(currUser) ? currUser : null;
	}


	protected Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		return currentTimestamp;
	}


	public String testMail(Report report, String email) throws Exception {
		MailerInfo mailerInfo = new MailerInfo();
		mailerInfo.setToEmail(email);
		mailerInfo.setMailSubject("Report Service - Test Mail");
		List<MailAttachment> attachments = new LinkedList<MailAttachment>();
		if (!BaseUtil.isObjNull(report))
			attachments.add(new MailAttachment(report.getFileName(), report.getReportBytes()));
		mailerInfo.setAttachments(attachments);
		Map<String, Object> map = new HashMap<String, Object>();
		LOGGER.debug(" Test Email Address: {} ", mailerInfo.getToEmail());
		this.mailService.sendMailUsingVelocityWithAttach(mailerInfo.getMailSubject(), mailerInfo.getToEmail(), null,
				map, "/velocity/mail.test.vm", mailerInfo.getAttachments());
		return "testMail";
	}

}