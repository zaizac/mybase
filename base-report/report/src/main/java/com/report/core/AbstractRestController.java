/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.core;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.report.config.StaticData;
import com.report.mail.MailAttachment;
import com.report.mail.MailerInfo;
import com.report.sdk.model.Report;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


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


	public String testMail(Report report, String email) {
		MailerInfo mailerInfo = new MailerInfo();
		mailerInfo.setToEmail(email);
		mailerInfo.setMailSubject("Report Service - Test Mail");
		List<MailAttachment> attachments = new LinkedList<>();
		if (!BaseUtil.isObjNull(report)) {
			attachments.add(new MailAttachment(report.getFileName(), report.getReportBytes()));
		}
		mailerInfo.setAttachments(attachments);
		Map<String, Object> map = new HashMap<>();
		LOGGER.debug(" Test Email Address: {} ", mailerInfo.getToEmail());
		mailService.sendMailUsingVelocityWithAttach(mailerInfo.getMailSubject(), mailerInfo.getToEmail(), null, map,
				"/velocity/mail.test.vm", mailerInfo.getAttachments());
		return "testMail";
	}


	protected String getSystemHeader(HttpServletRequest request) {
		return request.getHeader("X-System-Type");

	}

}