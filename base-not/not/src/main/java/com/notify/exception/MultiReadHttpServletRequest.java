/**
 * Copyright 2019. 
 */
package com.notify.exception;


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



/**
 * To read HTTP request body and then the servlet can still read it later.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

	/** The body. */
	private byte[] body;


	/**
	 * Instantiates a new multi read http servlet request.
	 *
	 * @param httpServletRequest the http servlet request
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public MultiReadHttpServletRequest(HttpServletRequest httpServletRequest) throws IOException {
		super(httpServletRequest);
		// Read the request body and save it as a byte array
		InputStream is = super.getInputStream();
		body = IOUtils.toByteArray(is);
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStreamImpl(new ByteArrayInputStream(body));
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getReader()
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		String enc = getCharacterEncoding();
		if (enc == null) {
			enc = "UTF-8";
		}
		return new BufferedReader(new InputStreamReader(getInputStream(), enc));
	}


	/**
	 * The Class ServletInputStreamImpl.
	 */
	private class ServletInputStreamImpl extends ServletInputStream {

		/** The is. */
		private InputStream is;


		/**
		 * Instantiates a new servlet input stream impl.
		 *
		 * @param is the is
		 */
		public ServletInputStreamImpl(InputStream is) {
			this.is = is;
		}


		/* (non-Javadoc)
		 * @see java.io.InputStream#read()
		 */
		@Override
		public int read() throws IOException {
			return is.read();
		}


		/* (non-Javadoc)
		 * @see java.io.InputStream#markSupported()
		 */
		@Override
		public boolean markSupported() {
			return false;
		}


		/* (non-Javadoc)
		 * @see java.io.InputStream#mark(int)
		 */
		@Override
		public synchronized void mark(int i) {
			throw new UnsupportedOperationException("mark/reset not supported");
		}


		/* (non-Javadoc)
		 * @see java.io.InputStream#reset()
		 */
		@Override
		public synchronized void reset() throws IOException {
			throw new IOException("mark/reset not supported");
		}


		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#isFinished()
		 */
		@Override
		public boolean isFinished() {
			return false;
		}


		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#isReady()
		 */
		@Override
		public boolean isReady() {
			return false;
		}


		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#setReadListener(javax.servlet.ReadListener)
		 */
		@Override
		public void setReadListener(ReadListener readListener) {
			// Auto-generated method stub

		}

	}

}