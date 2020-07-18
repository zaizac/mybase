/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.exception;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

import com.wfw.sdk.exception.WfwException;


/**
 * To read HTTP request body and then the servlet can still read it later
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

	private byte[] body;


	public MultiReadHttpServletRequest(HttpServletRequest httpServletRequest) throws IOException {
		super(httpServletRequest);
		// Read the request body and save it as a byte array
		InputStream is = super.getInputStream();
		body = IOUtils.toByteArray(is);
	}


	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStreamImpl(new ByteArrayInputStream(body));
	}


	@Override
	public BufferedReader getReader() throws IOException {
		String enc = getCharacterEncoding();
		if (enc == null) {
			enc = "UTF-8";
		}
		return new BufferedReader(new InputStreamReader(getInputStream(), enc));
	}


	private class ServletInputStreamImpl extends ServletInputStream {

		private InputStream is;


		public ServletInputStreamImpl(InputStream is) {
			this.is = is;
		}


		@Override
		public int read() throws IOException {
			return is.read();
		}


		@Override
		public boolean markSupported() {
			return false;
		}


		@Override
		public synchronized void mark(int i) {
			throw new WfwException((new IOException("mark/reset not supported")).getMessage());
		}


		@Override
		public synchronized void reset() throws IOException {
			throw new IOException("mark/reset not supported");
		}


		@Override
		public boolean isFinished() {
			return false;
		}


		@Override
		public boolean isReady() {
			return false;
		}


		@Override
		public void setReadListener(ReadListener arg0) {
			// Comment generated for SonarLint Fix
		}
	}

}