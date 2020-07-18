/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.constants;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


/**
 * @author Mary Jane Buenaventura
 * @since 19/08/2016
 */
public class ReportHeaders {

	public static HttpHeaders getHttpHeadersPDF(String fileName, int contentLength) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("application/pdf"));
		header.setContentDispositionFormData(fileName, fileName);
		header.setContentLength(contentLength);
		header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		header.setExpires(0);
		header.setPragma("");
		return header;
	}


	public static HttpHeaders getHttpHeadersExcel(String fileName, int contentLength) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "inline;filename=" + fileName + "." + "xls");
		header.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		header.setContentLength(contentLength);
		header.setCacheControl("no-cache");
		header.setExpires(0);
		header.setPragma("");
		return header;
	}
}
