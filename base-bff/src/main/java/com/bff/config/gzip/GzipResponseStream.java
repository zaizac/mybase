/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config.gzip;


import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;


/**
 * @author Mary Jane Buenaventura
 * @since May 16, 2018
 */
public class GzipResponseStream extends ServletOutputStream {

	GZIPOutputStream gzipStream;

	final AtomicBoolean open = new AtomicBoolean(true);

	OutputStream output;


	public GzipResponseStream(OutputStream output) throws IOException {
		this.output = output;
		gzipStream = new GZIPOutputStream(output);
	}


	@Override
	public void close() throws IOException {
		if (open.compareAndSet(true, false)) {
			gzipStream.close();
		}
	}


	@Override
	public void flush() throws IOException {
		gzipStream.flush();
	}


	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}


	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (!open.get()) {
			throw new IOException("Stream closed!");
		}
		gzipStream.write(b, off, len);
	}


	@Override
	public void write(int b) throws IOException {
		if (!open.get()) {
			throw new IOException("Stream closed!");
		}
		gzipStream.write(b);
	}


	@Override
	public boolean isReady() {
		return true;
	}


	@Override
	public void setWriteListener(WriteListener writeListener) {
		// DO NOTHING
	}
}
