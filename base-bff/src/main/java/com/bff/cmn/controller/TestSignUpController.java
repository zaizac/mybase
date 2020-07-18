/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.sdk.model.SignUp;
import com.bff.core.AbstractController;
import com.bff.idm.controller.UserProfileController;
import com.util.MediaType;


/**
 * @author mary.jane
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/test")
public class TestSignUpController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);


	@PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public SignUp signUp(@RequestBody SignUp signUp) {
		LOGGER.info("signUp: {}", objectMapper.valueToTree(signUp));
		return getBeService().testSignUp(signUp);

	}


	@GetMapping(value = "/findSignUpByRefNo", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public SignUp signUp(@RequestParam String refNo) {
		return getBeService().testSignUpByRefNo(refNo);

	}


	@PostMapping(value = "/updateSignUp", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public SignUp updateSignUp(@RequestBody SignUp signUp) {
		LOGGER.info("signUp: {}", objectMapper.valueToTree(signUp));
		return getBeService().testUpdateSignUp(signUp);

	}
}
