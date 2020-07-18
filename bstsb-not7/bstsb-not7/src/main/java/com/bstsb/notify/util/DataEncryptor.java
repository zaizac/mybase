/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.util;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
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
 * The Class DataEncryptor.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class DataEncryptor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEncryptor.class);

	/** Key variables which define the encryption or decryption methodology. */
	private static final String NC56 = "459245lk34jkbjfwer89fkj98345895hjkh9ebnkas0324bjhbq93849";

	/** The Constant ENCSCHEME. */
	private static final String ENCSCHEME = "DES";

	/** The Constant AES_ENC. */
	private static final String AES_ENC = "AES";

	/** The Constant KEYSTRING. */
	private static final String KEYSTRING = "4D92199549E0F2EF";

	/** The Constant ERR_EMPTY_BYTE_ARRAY. */
	private static final String ERR_EMPTY_BYTE_ARRAY = "Input byte array is null or empty";

	/** Key refernces for the Encryptor Or Decryptor. */
	private KeySpec keySpec;

	/** The key factory. */
	private SecretKeyFactory keyFactory;

	/** The cipher. */
	private Cipher cipher;

	/** The Constant BEAN_NAME. */
	public static final String BEAN_NAME = "dataEncryptor";


	/**
	 * Constructor which initialises the state of the class.
	 *
	 * @throws GeneralSecurityException the general security exception
	 */
	public DataEncryptor() throws GeneralSecurityException {
		try {
			keySpec = new DESKeySpec(NC56.getBytes(StandardCharsets.UTF_8));
			keyFactory = SecretKeyFactory.getInstance(ENCSCHEME);
			cipher = Cipher.getInstance(ENCSCHEME);
		} catch (Exception e) {
			throw new GeneralSecurityException("Failed to create DataEncryptor", e);
		}
	}


	/**
	 * Used to convert the bytes to String.
	 *
	 * @param bytes the bytes
	 * @return String value for the given Bytes
	 */
	private String bytes2String(byte[] bytes) {
		String bytesToString = null;
		if (bytes != null) {
			bytesToString = new String(bytes);
		}
		return bytesToString;
	}


	/**
	 * <p>
	 * Get the hash of the password
	 * </p>.
	 *
	 * @param password the password
	 * @return the hash
	 * @throws GeneralSecurityException the general security exception
	 */
	public String getHash(String password) throws GeneralSecurityException {
		String hashPassword = null;

		try {
			// Get the hash code of the password
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytePassword = md.digest(password.getBytes());
			hashPassword = new Base64().encodeToString(bytePassword);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
			throw new GeneralSecurityException("Failed : while getting the hash of the password");
		}
		return hashPassword;
	}


	/**
	 * <p>
	 * Encrypt / Decrypt the given byte array using DES Algorithm based on the
	 * indicator
	 * </p>.
	 *
	 * @param byteText the byte text
	 * @param cryptId the crypt id
	 * @return the byte[]
	 * @throws InvalidKeySpecException the invalid key spec exception
	 * @throws InvalidKeyException the invalid key exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 * @throws BadPaddingException the bad padding exception
	 */
	private byte[] encryptOrDecryptDES(byte[] byteText, int cryptId)
			throws InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		javax.crypto.SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(cryptId, key);
		return cipher.doFinal(byteText);
	}


	/**
	 * Used to encrypt the string to the format which is been defined by cipher.
	 *
	 * @param unencryptedString the unencrypted string
	 * @return the string
	 * @throws GeneralSecurityException the general security exception
	 */
	public String encrypt(String unencryptedString) throws GeneralSecurityException {
		try {
			if (unencryptedString == null || unencryptedString.trim().length() == 0) {
				throw new IllegalArgumentException("unencrypted string was null or empty");
			} else {
				byte[] cleartext = unencryptedString.getBytes(StandardCharsets.UTF_8);
				byte[] ciphertext = encryptOrDecryptDES(cleartext, 1);
				return new Base64().encodeToString(ciphertext);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}


	/**
	 * Used to decrypt the string which is been provided.
	 *
	 * @param encryptedString the encrypted string
	 * @return : Decrypted String
	 * @throws GeneralSecurityException the general security exception
	 */
	public String decrypt(String encryptedString) throws GeneralSecurityException {
		try {
			if (encryptedString == null || encryptedString.trim().length() <= 0) {
				throw new IllegalArgumentException("encrypted string was null or empty");
			} else {
				byte[] cleartext = Base64.decodeBase64(encryptedString);
				byte[] ciphertext = encryptOrDecryptDES(cleartext, 2);
				return bytes2String(ciphertext);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}


	/**
	 * <p>
	 * Get the hash of the image content
	 * </p>.
	 *
	 * @param imageContent the image content
	 * @return Hash of the image content
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public byte[] getHash(byte[] imageContent) throws NoSuchAlgorithmException {
		// Get the hash code of the decrypted image
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashImage2 = md.digest(imageContent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("hash of the image {}", new String(hashImage2, StandardCharsets.UTF_8));
		}
		return hashImage2;
	}


	/**
	 * Used to encrypt the string to the format which is been defined by cipher.
	 *
	 * @param unencryptedByteArray the unencrypted byte array
	 * @return the byte[]
	 * @throws GeneralSecurityException the general security exception
	 */
	public byte[] encrypt(byte[] unencryptedByteArray) throws GeneralSecurityException {
		try {
			if (unencryptedByteArray == null) {
				throw new IllegalArgumentException("unencrypted string is null or empty");
			} else {
				return encryptOrDecryptDES(unencryptedByteArray, 1);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}


	/**
	 * Used to decrypt the byte array which is been provided.
	 *
	 * @param encryptedByteArray the encrypted byte array
	 * @return : Decrypted byte array
	 * @throws GeneralSecurityException the general security exception
	 */
	public byte[] decrypt(byte[] encryptedByteArray) throws GeneralSecurityException {
		try {
			if (encryptedByteArray == null) {
				throw new IllegalArgumentException(ERR_EMPTY_BYTE_ARRAY);
			} else {
				return encryptOrDecryptDES(encryptedByteArray, 2);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}


	/**
	 * Encrypt the data.
	 *
	 * @param data the data
	 * @return the byte[]
	 * @throws GeneralSecurityException the general security exception
	 */
	public byte[] encryptAES(byte[] data) throws GeneralSecurityException {
		byte[] encVal = null;
		try {
			if (data == null) {
				throw new IllegalArgumentException(ERR_EMPTY_BYTE_ARRAY);
			} else {
				Key key = generateKey();
				Cipher c = Cipher.getInstance(AES_ENC);
				c.init(Cipher.ENCRYPT_MODE, key);
				encVal = c.doFinal(data);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
		return encVal;
	}


	/**
	 * Decrypt the data.
	 *
	 * @param encryptedData the encrypted data
	 * @return the byte[]
	 * @throws GeneralSecurityException the general security exception
	 */
	public byte[] decryptAES(byte[] encryptedData) throws GeneralSecurityException {
		byte[] decValue = null;
		try {
			if (encryptedData == null) {
				throw new IllegalArgumentException(ERR_EMPTY_BYTE_ARRAY);
			} else {
				Key key = generateKey();
				Cipher c = Cipher.getInstance(AES_ENC);
				c.init(Cipher.DECRYPT_MODE, key);
				decValue = c.doFinal(encryptedData);
			}
		} catch (Exception e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
		return decValue;
	}


	/**
	 * Generate the key for AES Encryption.
	 *
	 * @return the key
	 */
	private Key generateKey() {
		return new SecretKeySpec(KEYSTRING.getBytes(), AES_ENC);
	}

}
