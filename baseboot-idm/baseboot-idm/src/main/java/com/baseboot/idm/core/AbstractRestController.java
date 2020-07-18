/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.idm.core;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.model.IdmProfile;
import com.baseboot.idm.sdk.util.BaseUtil;
import com.baseboot.idm.service.IdmProfileService;
import com.baseboot.idm.util.UidGenerator;
import com.baseboot.notify.sdk.client.NotServiceClient;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public abstract class AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

	@Autowired
	private IdmProfileService idmProfileService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private NotServiceClient notifyService;

	@Autowired
	protected Mapper dozerMapper;


	protected String genUserName(String name) {
		String userName = "";
		name = name.replaceAll("[^\\w]", "");

		if (name.length() < 8) {
			name = name + RandomStringUtils.randomAlphabetic(8 - name.length());
		}

		if (name.length() >= 8) {

			int start = 4;
			int end = 4;
			userName = name.substring(0, start) + name.substring(name.length() - end);
			LOGGER.debug("Username '{}' Already Exists..", userName);
			boolean isExists = false;
			isExists = isUserNameExists(userName);
			if (isExists) {
				// case -2
				userName = name.substring(0, start - 1) + name.substring(name.length() - end - 1);
				isExists = isUserNameExists(userName);

				if (isExists) {
					// case-3
					userName = name.substring(0, start - 2) + name.substring(name.length() - end - 2);
					isExists = isUserNameExists(userName);
					if (isExists) {
						// case-4
						userName = name.substring(0, start - 3) + name.substring(name.length() - end - 3);
						isExists = isUserNameExists(userName);
						if (isExists) {
							// case-5
							userName = name.substring(0, start - 4) + name.substring(name.length() - end - 4);
							// randomly generate username
							isExists = isUserNameExists(userName);
							if (isExists) {
								for (int i = 0; i < 8; i++) {
									String username = shuffle(userName);
									isExists = isUserNameExists(username);
									if (!isExists) {
										userName = username;
										break;
									}
								}
							}
						}
					}
				}

			}

		} else {
			// error.. Name should be 8 chars.
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Generated Username: {}", userName.toLowerCase());
		}
		return userName.toLowerCase();
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

		if (BaseUtil.isObjNull(pf)) {
			return false;
		}

		String dbName = StringUtils.hasText(pf.getUserId()) ? pf.getUserId() : null;
		return StringUtils.pathEquals(userName.toLowerCase(), dbName);
	}


	public String getHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashPassword = md.digest(password.getBytes());
		return Base64.encodeBase64String(hashPassword).trim();
	}


	public NotServiceClient getNotifyService() {
		notifyService.setToken(messageSource.getMessage(ConfigConstants.SVC_IDM_SKEY, null, Locale.getDefault()));
		notifyService
				.setClientId(messageSource.getMessage(ConfigConstants.SVC_IDM_CLIENT, null, Locale.getDefault()));
		notifyService.setMessageId(UidGenerator.getMessageId());
		return notifyService;
	}

}