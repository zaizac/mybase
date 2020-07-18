/**
 * Copyright 2019. 
 */
package com.dm.exception;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.dm.util.WebUtil;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.model.ErrorResponse;


/**
 * Global Exception Handler.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@ControllerAdvice
public class DmExceptionController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DmExceptionController.class);


	/**
	 * Handle error 400.
	 *
	 * @param request
	 *             the request
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleError400(HttpServletRequest request, Exception e) {
		return processError(e, DmErrorCodeEnum.E400DOMGEN.name());
	}


	/**
	 * Handle error 404.
	 *
	 * @param request
	 *             the request
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = { HttpClientErrorException.class, UnknownRequestParamException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleError404(HttpServletRequest request, Exception e) {
		return processError(e, DmErrorCodeEnum.E404DOMGEN.name());
	}


	/**
	 * Handle error 405.
	 *
	 * @param request
	 *             the request
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody ErrorResponse handleError405(HttpServletRequest request, Exception e) {
		return processError(e, DmErrorCodeEnum.E405DOMGEN.name());
	}


	/**
	 * Handle error 408.
	 *
	 * @param request
	 *             the request
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(TimeoutException.class)
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
	public @ResponseBody ErrorResponse handleError408(HttpServletRequest request, Exception e) {
		return processError(e, DmErrorCodeEnum.E408DOMGEN.name());
	}


	/**
	 * Handle error 415.
	 *
	 * @param request
	 *             the request
	 * @param response
	 *             the response
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody ErrorResponse handleError415(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, DmErrorCodeEnum.E415DOMGEN.name());
	}


	/**
	 * Handle error 500.
	 *
	 * @param request
	 *             the request
	 * @param response
	 *             the response
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = { RuntimeException.class, IOException.class, AccessDeniedException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleError500(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, DmErrorCodeEnum.E500DOMGEN.name());
	}


	/**
	 * Handle error 503.
	 *
	 * @param request
	 *             the request
	 * @param response
	 *             the response
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = {})
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public @ResponseBody ErrorResponse handleError503(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, DmErrorCodeEnum.E503DOMGEN.name());
	}


	/**
	 * Handle internal error.
	 *
	 * @param request
	 *             the request
	 * @param response
	 *             the response
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@ExceptionHandler(value = { DmException.class, IdmException.class })
	public @ResponseBody ErrorResponse handleInternalError(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		String internalCode = null;
		if (e instanceof DmException) {
			internalCode = ((DmException) e).getInternalErrorCode();
			DmErrorCodeEnum rce = DmErrorCodeEnum.findByName(internalCode);
			response.setStatus(rce.getCode());
		} else if (e instanceof IdmException) {
			internalCode = ((IdmException) e).getInternalErrorCode();
			IdmErrorCodeEnum rce = IdmErrorCodeEnum.findByName(internalCode);
			if (rce != null) {
				response.setStatus(rce.getCode());
			}
		}

		return processError(e, internalCode);
	}


	/**
	 * Handle filter error.
	 *
	 * @param request
	 *             the request
	 * @param response
	 *             the response
	 * @param e
	 *             the e
	 * @return the error response
	 */
	@RequestMapping(value = "/error")
	public @ResponseBody ErrorResponse handleFilterError(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, String.valueOf(response.getStatus()));
	}


	/**
	 * Process error.
	 *
	 * @param ex
	 *             the ex
	 * @param errorCode
	 *             the error code
	 * @return the error response
	 */
	private ErrorResponse processError(Exception ex, String errorCode) {
		LOGGER.error("Response Error: {} - {}", errorCode, ex.getMessage());
		return new ErrorResponse(errorCode, ex.getMessage(), WebUtil.getStackTrace(ex));
	}

}