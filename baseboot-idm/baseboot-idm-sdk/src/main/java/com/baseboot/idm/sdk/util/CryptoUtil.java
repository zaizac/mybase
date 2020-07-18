/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 29, 2016
 */
public class CryptoUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class);

	private static final String CIPHER_STRING = "AES/ECB/PKCS5Padding";

	private static SecretKeySpec secretKey;


	private CryptoUtil() {
	}


	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("MD5");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
		}
	}


	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(CIPHER_STRING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			LOGGER.error("Error while encrypting: {}", e.getMessage());
		}
		return null;
	}


	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(CIPHER_STRING);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {
			LOGGER.error("Error while decrypting: {}", e.getMessage());
		}
		return null;
	}

}