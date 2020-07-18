/**
 * Copyright 2019. 
 */
package com.util;


import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @author mary.jane
 * @since Mar 6, 2019
 */
public class ErrorUtil {

	private ErrorUtil() {
		throw new IllegalStateException(ErrorUtil.class.getCanonicalName());
	}


	public static String getStackTrace(final Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer));
		String[] lines = writer.toString().split("\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(lines.length, 10); i++) {
			sb.append(lines[i]).append("\n");
		}
		return sb.toString();
	}

}
