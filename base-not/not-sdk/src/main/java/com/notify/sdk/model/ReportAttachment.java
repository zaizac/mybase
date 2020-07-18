/**
 * Copyright 2019. 
 */
package com.notify.sdk.model;


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * The Class ReportAttachment.
 *
 * @author mary.jane
 * @since Jan 15, 2019
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
public class ReportAttachment implements Attachment {

	/** The url. */
	private String url;

	/** The http method. */
	private String httpMethod;

	/** The request params. */
	private Map<String, Object> requestParams;

	/** The request body. */
	private Object requestBody;


	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * Sets the url.
	 *
	 * @param url
	 *             the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * Gets the http method.
	 *
	 * @return the http method
	 */
	public String getHttpMethod() {
		return httpMethod;
	}


	/**
	 * Sets the http method.
	 *
	 * @param httpMethod
	 *             the new http method
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}


	/**
	 * Gets the request params.
	 *
	 * @return the request params
	 */
	public Map<String, Object> getRequestParams() {
		return requestParams;
	}


	/**
	 * Sets the request params.
	 *
	 * @param requestParams
	 *             the request params
	 */
	public void setRequestParams(Map<String, Object> requestParams) {
		this.requestParams = requestParams;
	}


	/**
	 * Gets the request body.
	 *
	 * @return the request body
	 */
	public Object getRequestBody() {
		return requestBody;
	}


	/**
	 * Sets the request body.
	 *
	 * @param requestBody
	 *             the new request body
	 */
	public void setRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}

}