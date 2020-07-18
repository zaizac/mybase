/**
 * Copyright 2019
 */
package com.be.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.core.AbstractRestController;
import com.be.model.TestSignUp;
import com.be.sdk.constants.BeUrlConstants;
import com.be.sdk.model.SignUp;
import com.be.service.TestSignUpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.UidGenerator;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.ServiceCheck;
import com.wfw.sdk.model.WfwRefPayload;


/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@RestController
@RequestMapping(BeUrlConstants.SERVICE_CHECK)
public class ServiceCheckRestController extends AbstractRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	TestSignUpService testSignUpSvc;

	@Value("${" + BaseConfigConstants.SERVICE_TAG_PFX + "}")
	private String serviceName;


	@GetMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ServiceCheck serviceCheck(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		ServiceCheck svcTest = new ServiceCheck(serviceName, url.substring(0, url.indexOf(uri)),
				BaseConstants.SUCCESS);

		// DB CONNECTION
		String mysqlUrl = messageSource.getMessage(BaseConfigConstants.DB_CONF_URL, null, Locale.getDefault());
		try {
			if (!BaseUtil.isObjNull(dataSource.getConnection())) {
				svcTest.setMysql(new ServiceCheck("MySQL Connection", mysqlUrl, BaseConstants.SUCCESS));
			} else {
				svcTest.setMysql(new ServiceCheck("MySQL Connection", mysqlUrl, BaseConstants.FAILED));
			}
		} catch (Exception e) {
			svcTest.setMysql(new ServiceCheck("MySQL Connection (BE) ", mysqlUrl,
					BaseConstants.FAILED + " [" + e.getMessage() + "]"));
			svcTest.setServiceResponse(BaseConstants.FAILED);
		}

		return svcTest;
	}


	@GetMapping(value = "test", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String serviceCheck() {
		return serviceName;
	}


	@PostMapping(value = "test", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String postServiceCheck() {
		return serviceName;
	}


	@PostMapping(value = "signUp", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TestSignUp signUp(@RequestBody TestSignUp signUp, HttpServletRequest request) {
		LOGGER.info("TestSignUp: {}", new ObjectMapper().valueToTree(signUp));
		signUp.setRefNo(UidGenerator.generateUid("TSTREG"));
		signUp.setStatus("REG_APPLY");
		TestSignUp result = testSignUpSvc.create(signUp);
		WfwRefPayload payload = new WfwRefPayload();
		payload.setAppRefNo(signUp.getRefNo());
		payload.setAdditionalInfo(JsonUtil.toJsonNode(result).asText());
		payload.setApplicantId("reguser");
		payload.setApplicant(result.getFullName());
		payload.setAppStatus("REG_APPLY");
		payload.setTaskAuthorId("systemuser");
		payload.setTaskAuthorName("SYSTEM");
		payload.setTranId("TSTREG");
		getWfwService(request).startTask(payload);
		return result;
	}


	@PostMapping(value = "updateSignUp", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TestSignUp updateSignUp(@RequestBody SignUp signUp, HttpServletRequest request) {
		LOGGER.info("TestSignUp: {}", new ObjectMapper().valueToTree(signUp));
		TestSignUp testSignUp = testSignUpSvc.findByRefNo(signUp.getRefNo());
		testSignUp.setStatus("REG_VERIFY");
		TestSignUp result = testSignUpSvc.update(testSignUp);
		WfwRefPayload payload = new WfwRefPayload();
		payload.setTaskMasterId(signUp.getTaskMasterId());
		payload.setAppRefNo(signUp.getRefNo());
		payload.setAppStatus("REG_VERIFY");
		payload.setStatusCd(signUp.getStatusCd());
		payload.setTaskAuthorId(signUp.getTaskAuthorId());
		payload.setTaskAuthorName("SYSTEM");
		payload.setRemark(signUp.getRemark());
		getWfwService(request).completeTask(payload);
		return result;
	}


	@GetMapping(value = "signUpByRefNo/{refNo}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public TestSignUp signUpByRefNo(@PathVariable String refNo) {
		return testSignUpSvc.findByRefNo(refNo);
	}

}