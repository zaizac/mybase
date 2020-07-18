/**
 * Copyright 2019. 
 */
package com.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class CryptoUtil.
 *
 * @author Mary Jane Buenaventura
 * @since Sep 29, 2016
 */
public class CryptoUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class);

	/** The Constant CIPHER_STRING. */
	// Ignore SonarLint issue, as AES/GCM/NoPadding is not supported in JDK7
	private static final String CIPHER_STRING = "AES/ECB/PKCS5Padding";

	/** The secret key. */
	private static SecretKeySpec secretKey;


	/**
	 * Instantiates a new crypto util.
	 */
	private CryptoUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Sets the key.
	 *
	 * @param myKey the new key
	 */
	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
			sha = MessageDigest.getInstance("MD5");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		}
	}


	/**
	 * Encrypt.
	 *
	 * @param strToEncrypt the str to encrypt
	 * @param secret the secret
	 * @return the string
	 */
	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(CIPHER_STRING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			LOGGER.error("Error while encrypting: {}", e.getMessage());
		}
		return null;
	}


	/**
	 * Decrypt.
	 *
	 * @param strToDecrypt the str to decrypt
	 * @param secret the secret
	 * @return the string
	 */
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