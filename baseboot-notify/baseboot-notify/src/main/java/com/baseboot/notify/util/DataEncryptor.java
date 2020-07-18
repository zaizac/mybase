/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class DataEncryptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataEncryptor.class);

	/**
	 * Key variables which define the encryption or decryption methodology
	 */
	private static final String NC56 = "459245lk34jkbjfwer89fkj98345895hjkh9ebnkas0324bjhbq93849";

	private static final String ENCSCHEME = "DES";

	private static final String AES_ENC = "AES";

	private static final String UNICODE_FORMAT = "UTF8";

	private static final String KEYSTRING = "4D92199549E0F2EF";

	/**
	 * Key refernces for the Encryptor Or Decryptor
	 */
	private KeySpec keySpec;

	private SecretKeyFactory keyFactory;

	private Cipher cipher;

	public static final String BEAN_NAME = "dataEncryptor";


	/**
	 * Constructor which initialises the state of the class
	 * 
	 * @throws Exception
	 */
	public DataEncryptor() throws Exception {
		try {
			keySpec = new DESKeySpec(NC56.getBytes("UTF8"));
			keyFactory = SecretKeyFactory.getInstance(ENCSCHEME);
			cipher = Cipher.getInstance(ENCSCHEME);
		} catch (Exception e) {
			LOGGER.error("Failed to create DataEncryptor", e);
			throw new Exception("Failed to create DataEncryptor", e);
		}
	}


	/**
	 * Used to convert the bytes to String
	 * 
	 * @param bytes
	 * @return String value for the given Bytes
	 */
	private String bytes2String(byte bytes[]) {
		String bytesToString = null;
		if (bytes != null) {
			bytesToString = new String(bytes);
		}
		return bytesToString;
	}


	/**
	 * 
	 * <p>
	 * Get the hash of the password
	 * </p>
	 *
	 * @param password
	 * @return
	 * @throws CDCManagerException
	 */
	public String getHash(String password) throws Exception {
		String hashPassword = null;

		try {
			// Get the hash code of the password
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytePassword = md.digest(password.getBytes());
			hashPassword = new Base64().encodeToString(bytePassword);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception("Failed : while getting the hash of the password");
		}
		return hashPassword;
	}


	/**
	 * <p>
	 * Encrypt / Decrypt the given byte array using DES Algorithm based on the
	 * indicator
	 * </p>
	 *
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private byte[] encryptOrDecryptDES(byte[] byteText, int cryptId)
			throws InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		javax.crypto.SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(cryptId, key);
		byte ciphertext[] = cipher.doFinal(byteText);
		return ciphertext;
	}


	/**
	 * Used to encrypt the string to the format which is been defined by cipher
	 * 
	 * @param unencryptedString
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String unencryptedString) throws Exception {
		try {
			if (unencryptedString == null || unencryptedString.trim().length() == 0) {
				throw new IllegalArgumentException("unencrypted string was null or empty");
			} else {
				byte cleartext[] = unencryptedString.getBytes(UNICODE_FORMAT);
				byte ciphertext[] = encryptOrDecryptDES(cleartext, 1);
				return new Base64().encodeToString(ciphertext);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to encrypt the password in DataEncryptor", e);
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * Used to decrypt the string which is been provided.
	 * 
	 * @param encryptedString
	 * @return : Decrypted String
	 * @throws Exception
	 */
	public String decrypt(String encryptedString) throws Exception {
		try {
			if (encryptedString == null || encryptedString.trim().length() <= 0) {
				throw new IllegalArgumentException("encrypted string was null or empty");
			} else {
				byte cleartext[] = Base64.decodeBase64(encryptedString);
				byte ciphertext[] = encryptOrDecryptDES(cleartext, 2);
				return bytes2String(ciphertext);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to decrypt the password", e);
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * <p>
	 * Get the hash of the image content
	 * </p>
	 *
	 * @param imageContent
	 * @return Hash of the image content
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public byte[] getHash(byte[] imageContent) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// Get the hash code of the decrypted image
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashImage2 = md.digest(imageContent);
		LOGGER.debug("hash of the image {}", new String(hashImage2, "UTF-8"));
		return hashImage2;
	}


	/**
	 * Used to encrypt the string to the format which is been defined by cipher
	 * 
	 * @param unencrypted
	 *             String
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] unencryptedByteArray) throws Exception {
		try {
			if (unencryptedByteArray == null) {
				throw new IllegalArgumentException("unencrypted string is null or empty");
			} else {
				byte[] encrypted = encryptOrDecryptDES(unencryptedByteArray, 1);
				return encrypted;
			}
		} catch (Exception e) {
			LOGGER.error("Failed to encrypt the password in DataEncryptor", e);
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * Used to decrypt the byte array which is been provided.
	 * 
	 * @param encryptedString
	 * @return : Decrypted byte array
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] encryptedByteArray) throws Exception {
		try {
			if (encryptedByteArray == null) {
				throw new IllegalArgumentException("Input byte array is null or empty");
			} else {
				byte ciphertext[] = encryptOrDecryptDES(encryptedByteArray, 2);
				return ciphertext;

			}
		} catch (Exception e) {
			LOGGER.error("Failed to decrypt the password", e);
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * Encrypt the data
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptAES(byte[] data) throws Exception {
		byte[] encVal = null;
		try {
			if (data == null) {
				throw new IllegalArgumentException("Input byte array is null or empty");
			} else {
				Key key = generateKey();
				Cipher c = Cipher.getInstance(AES_ENC);
				c.init(Cipher.ENCRYPT_MODE, key);
				encVal = c.doFinal(data);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to encrypt the password in DataEncryptor", e);
			throw new Exception(e.getMessage(), e);
		}
		return encVal;
	}


	/**
	 * Decrypt the data
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptAES(byte[] encryptedData) throws Exception {
		byte[] decValue = null;
		try {
			if (encryptedData == null) {
				throw new IllegalArgumentException("Input byte array is null or empty");
			} else {
				Key key = generateKey();
				Cipher c = Cipher.getInstance(AES_ENC);
				c.init(Cipher.DECRYPT_MODE, key);
				decValue = c.doFinal(encryptedData);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to encrypt the password in DataEncryptor", e);
			throw new Exception(e.getMessage(), e);
		}
		return decValue;
	}


	/**
	 * Generate the key for AES Encryption
	 * 
	 * @return
	 * @throws Exception
	 */
	private Key generateKey() throws Exception {
		Key key = new SecretKeySpec(KEYSTRING.getBytes(), AES_ENC);
		return key;
	}

}
