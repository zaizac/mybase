/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.core;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.service.IdmProfileService;
import com.bstsb.idm.util.UidGenerator;
import com.bstsb.notify.sdk.client.NotServiceClient;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public abstract class AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	protected UidGenerator uidGenerator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	protected Mapper dozerMapper;

	@Autowired
	private NotServiceClient notifyService;


	public NotServiceClient getNotifyService() {
		notifyService.setToken(messageSource.getMessage(BaseConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
		notifyService.setClientId(
				messageSource.getMessage(BaseConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		notifyService.setMessageId(UidGenerator.getMessageId());
		return notifyService;
	}


	protected String genUserName(String name) {
		name = name.replaceAll("[^\\w]", "");

		if (name.length() < 8) {
			name = name + RandomStringUtils.randomAlphabetic(8 - name.length());
		}

		String userName = "";
		if (name.length() >= 8) {
			userName = checkUserNameExistAndGenerate(name);
		} else {
			// error.. Name should be 8 chars.
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Generated Username: {}", userName.toLowerCase());
		}
		return userName.toLowerCase();
	}


	private String checkUserNameExistAndGenerate(String name) {
		int start = 4;
		int end = 4;
		String userName = name.substring(0, start) + name.substring(name.length() - end);
		LOGGER.debug("Username '{}' Already Exists..", userName);
		boolean isExists = isUserNameExists(userName);
		int counter = 1; // starts with 1
		while (isExists) {
			userName = name.substring(0, start - counter) + name.substring(name.length() - end - counter);
			isExists = isUserNameExists(userName);
			if (counter == 4 && isExists) {
				for (int i = 0; i < 8; i++) {
					String username = shuffle(userName);
					isExists = isUserNameExists(username);
					if (!isExists) {
						userName = username;
						break;
					}
				}
				break;
			}
			counter++;
		}
		return userName;
	}


	protected String shuffle(String username) {
		if (username.length() <= 1) {
			return username;
		}

		int split = username.length() / 2;

		String temp1 = shuffle(username.substring(0, split));
		String temp2 = shuffle(username.substring(split));

		if (Math.random() > 0.5) {
			return temp1 + temp2;
		} else {
			return temp2 + temp1;
		}
	}


	protected boolean isUserNameExists(String userName) {
		IdmProfile pf = idmProfileService.findProfileByUserId(userName);
		boolean userExists = false;

		if (BaseUtil.isObjNull(pf)) {
			return userExists;
		}

		String dbName = StringUtils.hasText(pf.getUserId()) ? pf.getUserId() : null;

		if (StringUtils.pathEquals(userName.toLowerCase(), dbName)) {
			userExists = true;
		}
		return userExists;
	}


	public String getHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashPassword = md.digest(password.getBytes());
		return Base64.encodeBase64String(hashPassword).trim();
	}


	protected String getCurrUserId(HttpServletRequest request) {
		return BaseUtil.getStr(request.getAttribute("currUserId"));
	}

}
