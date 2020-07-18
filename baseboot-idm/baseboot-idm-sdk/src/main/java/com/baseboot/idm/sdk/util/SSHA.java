/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.util;


import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.constants.IdmErrorCodeEnum;
import com.baseboot.idm.sdk.exception.IdmException;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public final class SSHA {

	private static final Logger LOGGER = LoggerFactory.getLogger(SSHA.class);


	private SSHA() {
	}


	private static Charset utf8() {
		return Charset.forName("UTF-8");
	}


	private static MessageDigest getSHA1() {
		try {
			return MessageDigest.getInstance("SHA-1");
		} catch (java.security.NoSuchAlgorithmException ex) {
			throw new IdmException(IdmErrorCodeEnum.E503IDMGEN.name(), ex.getMessage());
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
	 * The string returned will be a Base64 encoding of the concatenation of
	 * the SHA-1 hash of (salt concatenated to plaintext) and the salt.
	 * </p>
	 */
	public static String getSSHAHash(String plaintext, String salt) {
		byte[] saltBytes;
		MessageDigest hasher = getSHA1();
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
	 */
	public static boolean matchSHAHash(String hashText, String plaintext) {
		byte[] hashBytes;
		byte[] plainBytes;
		byte[] saltBytes = null;
		MessageDigest hasher = getSHA1();

		if (hashText.indexOf("{SSHA}") != -1) {
			hashText = hashText.substring(6);
		} else if (hashText.indexOf("{SHA}") != -1) {
			hashText = hashText.substring(5);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Hash Text: {}", hashText);
		}

		hashBytes = org.apache.commons.codec.binary.Base64.decodeBase64(hashText);

		if (hashBytes.length > 20) {
			saltBytes = new byte[hashBytes.length - 20];
			for (int i = 20; i < hashBytes.length; i++) {
				saltBytes[i - 20] = hashBytes[i];
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Salt is {}", String.valueOf(saltBytes));
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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Match text is {}", String.valueOf(plainBytes));
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
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Char mismatch [{}]", i);
				}

				return false;
			}
		}
		return true;
	}


	/**
	 * <p>
	 * This method generates a short, random sequence of salt bytes for use in
	 * generating a Netscape SSHA hash.
	 * </p>
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
		java.util.Random randgen = new java.util.Random();
		byte[] saltBytes = new byte[4];

		randgen.nextBytes(saltBytes);

		return saltBytes;
	}

}