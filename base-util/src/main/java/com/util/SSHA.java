/**
 * Copyright 2019. 
 */
package com.util;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class SSHA.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public final class SSHA {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SSHA.class);


	/**
	 * Instantiates a new ssha.
	 */
	private SSHA() {
	}


	/**
	 * Utf 8.
	 *
	 * @return the charset
	 */
	private static Charset utf8() {
		return Charset.forName(StandardCharsets.UTF_8.toString());
	}


	/**
	 * Gets the sha1.
	 *
	 * @return the sha1
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	private static MessageDigest getSHA1() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-1");
	}


	/**
	 * <p>
	 * This method takes a plaintext string and generates an SSHA hash out of
	 * it. If &lt;salt&gt; is not null, it will be used, otherwise a random
	 * salt will be generated.
	 * </p>
	 * 
	 * <p>
	 * The string returned will be a Base64 encoding of the concatenation of
	 * the SHA-1 hash of (salt concatenated to plaintext) and the salt.
	 * </p>
	 *
	 * @param plaintext the plaintext
	 * @param salt the salt
	 * @return the SSHA hash
	 */
	public static String getSSHAHash(String plaintext, String salt) {
		byte[] saltBytes;
		MessageDigest hasher;
		try {
			hasher = getSHA1();

			if (salt == null) {
				saltBytes = genRandomSalt();
			} else {
				saltBytes = salt.getBytes(utf8());
			}

			hasher.reset();
			hasher.update(plaintext.getBytes(utf8()));
			hasher.update(saltBytes);
			byte[] digestBytes = hasher.digest();
			byte[] outBytes = new byte[saltBytes.length + 20];

			assert (digestBytes.length == 20);

			System.arraycopy(digestBytes, 0, outBytes, 0, digestBytes.length);
			System.arraycopy(saltBytes, 0, outBytes, digestBytes.length, saltBytes.length);

			return org.apache.commons.codec.binary.Base64.encodeBase64String(outBytes);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("{}", e.getMessage());
			return null;
		}
	}


	/**
	 * <p>
	 * This method takes a plaintext string and generates an SSHA hash out of
	 * it. If &lt;salt&gt; is not null, it will be used, otherwise a random
	 * salt will be generated.
	 * </p>
	 * 
	 * <p>
	 * The string returned will be {SSHA} followed by the Base64 encoding of
	 * the concatenation of the SHA-1 hash of (salt concatenated to plaintext)
	 * and the salt.
	 * </p>
	 *
	 * @param plaintext the plaintext
	 * @param salt the salt
	 * @return the LDAPSSHA hash
	 */
	public static String getLDAPSSHAHash(String plaintext, String salt) {
		return "{SSHA}" + getSSHAHash(plaintext, salt);
	}


	/**
	 * <p>
	 * This method takes a Base64 encoded SHA/SSHA hashText (either with or
	 * without the preceding {SHA}|{SSHA}) and attempts to verify that the
	 * given plaintext matches it. Returns true on successful match.
	 * </p>
	 *
	 * @param hashText the hash text
	 * @param plaintext the plaintext
	 * @return true, if successful
	 */
	public static boolean matchSHAHash(String hashText, String plaintext) {
		byte[] hashBytes;
		byte[] plainBytes;
		byte[] saltBytes = null;
		MessageDigest hasher;
		try {
			hasher = getSHA1();
		} catch (NoSuchAlgorithmException e) {
			return false;
		}

		if (hashText.indexOf("{SSHA}") != -1) {
			hashText = hashText.substring(6);
		} else if (hashText.indexOf("{SHA}") != -1) {
			hashText = hashText.substring(5);
		}

		hashBytes = org.apache.commons.codec.binary.Base64.decodeBase64(hashText);

		if (hashBytes.length > 20) {
			saltBytes = new byte[hashBytes.length - 20];
			for (int i = 20; i < hashBytes.length; i++) {
				saltBytes[i - 20] = hashBytes[i];
			}
		}

		if (saltBytes != null) {
			byte[] inBytes = plaintext.getBytes(utf8());
			plainBytes = new byte[inBytes.length + saltBytes.length];

			for (int i = 0; i < inBytes.length; i++) {
				plainBytes[i] = inBytes[i];
			}

			for (int i = 0; i < saltBytes.length; i++) {
				plainBytes[i + inBytes.length] = saltBytes[i];
			}
		} else {
			plainBytes = plaintext.getBytes(utf8());
		}

		// okay, now we should have in plainBytes the input to the SHA
		// algorithm that would have been used to generate the hashText if
		// we indeed have a match. Let's just check.

		hasher.reset();
		hasher.update(plainBytes);
		byte[] matchBytes = hasher.digest();

		assert (matchBytes.length == 20);

		for (int i = 0; i < matchBytes.length; i++) {
			if (matchBytes[i] != hashBytes[i]) {
				return false;
			}
		}
		return true;
	}


	/** The randgen. */
	private static Random randgen = new Random();


	/**
	 * <p>
	 * This method generates a short, random sequence of salt bytes for use in
	 * generating a Netscape SSHA hash.
	 * </p>
	 *
	 * @return the byte[]
	 */
	public static byte[] genRandomSalt() {
		/*
		 * note that we don't need to worry about using a fancy-schmancy Java
		 * 1.2 SecureRandom here.. the hash is directly included in the SSHA
		 * hashtext, so it's not like we can hide it.. the only purpose of the
		 * salt is to discourage extensive pre-generation of hash
		 * dictionaries.. as long the odds of any two hashtexts sharing a salt
		 * are adequately low, we don't really care about whether the salt we
		 * use at any given time was completely unpredictable.
		 */
		byte[] saltBytes = new byte[4];

		randgen.nextBytes(saltBytes);

		return saltBytes;
	}

}