/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config.gzip;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 16, 2018
 */
public class GzipResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(GzipResponseWrapper.class);

	private GzipResponseStream gzipStream;

	private ServletOutputStream outputStream;

	private PrintWriter printWriter;


	public GzipResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		response.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip");
	}


	public void finish() throws IOException {
		if (printWriter != null) {
			printWriter.close();
		}
		if (outputStream != null) {
			outputStream.close();
		}
		if (gzipStream != null) {
			gzipStream.close();
		}
	}


	@Override
	public void flushBuffer() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		}
		if (outputStream != null) {
			outputStream.flush();
		}
		super.flushBuffer();
	}


	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (printWriter != null) {
			throw new IllegalStateException("printWriter already defined");
		}
		if (outputStream == null) {
			initGzip();
			outputStream = gzipStream;
		}
		return outputStream;
	}


	@Override
	public PrintWriter getWriter() throws IOException {
		if (outputStream != null) {
			throw new IllegalStateException("printWriter already defined");
		}
		if (printWriter == null) {
			initGzip();
			printWriter = new PrintWriter(new OutputStreamWriter(gzipStream, getResponse().getCharacterEncoding()));
		}
		LOGGER.debug("GZIPFilter is Working fine!!!!!");
		return printWriter;
	}


	/*
	 * Creates a new output stream for writing compressed data in the GZIP file
	 * format.
	 */
	private void initGzip() throws IOException {
		gzipStream = new GzipResponseStream(getResponse().getOutputStream());
	}

}
