/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.rmq.exception;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.util.BaseUtil;
import com.be.sdk.constants.BeErrorCodeEnum;
import com.be.sdk.exception.BeException;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.rmq.sdk.client.constants.SgwErrorCodeEnum;
import com.rmq.sdk.exception.SgwException;
import com.rmq.sdk.model.MessageResponse;


/**
 * Global Exception Handler
 *
 * @author Mary Jane Buenaventura
 * @since Nov 26, 2016
 */
@EnableWebMvc
@RestControllerAdvice
public class RmqExceptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RmqExceptionController.class);


	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	public @ResponseBody MessageResponse handleError400(HttpServletRequest request, Exception e) {
		return processError(e, 400, HttpStatus.BAD_REQUEST.getReasonPhrase(), request.getRequestURI());
	}


	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { HttpClientErrorException.class, NoHandlerFoundException.class })
	public @ResponseBody MessageResponse handleError404(HttpServletRequest request, Exception e) {
		if (e instanceof NoHandlerFoundException) {
			return processError(e, 404, "Service Not Found", request.getRequestURI());
		}
		return processError(e, 404, HttpStatus.NOT_FOUND.getReasonPhrase(), request.getRequestURI());
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody MessageResponse handleError405(HttpServletRequest request, Exception e) {
		return processError(e, 405, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), request.getRequestURI());
	}


	@ExceptionHandler(TimeoutException.class)
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
	public @ResponseBody MessageResponse handleError408(HttpServletRequest request, Exception e) {
		return processError(e, 408, HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), request.getRequestURI());
	}


	@ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody MessageResponse handleError415(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, 415, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), request.getRequestURI());
	}


	@ExceptionHandler(value = { SgwException.class, IdmException.class, BeException.class })
	public @ResponseBody MessageResponse handleInternalError(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		String internalCode = null;
		if (e instanceof SgwException) {
			SgwException ex = (SgwException) e;
			internalCode = ex.getInternalErrorCode();
			SgwErrorCodeEnum rce = SgwErrorCodeEnum.findByName(ex.getInternalErrorCode());
			response.setStatus(rce.getCode());
		} else if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			internalCode = ex.getInternalErrorCode();
			IdmErrorCodeEnum rce = IdmErrorCodeEnum.findByName(ex.getInternalErrorCode());
			if (!BaseUtil.isObjNull(rce)) {
				response.setStatus(rce.getCode());
			}
		} else if (e instanceof BeException) {
			BeException ex = (BeException) e;
			internalCode = ex.getInternalErrorCode();
			BeErrorCodeEnum rce = BeErrorCodeEnum.findByName(ex.getInternalErrorCode());
			if (!BaseUtil.isObjNull(rce)) {
				response.setStatus(rce.getCode());
			}
		}

		return processError(e, response.getStatus(), internalCode, request.getRequestURI());
	}


	@ExceptionHandler(value = { RuntimeException.class, IOException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody MessageResponse handleError500(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, 500, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), request.getRequestURI());
	}


	private MessageResponse processError(Exception ex, int status, String code, String path) {
		ex.printStackTrace();
		LOGGER.info("RMQ Response Error: {} - {}", status, ex.getMessage());
		String msg = ex.getMessage();
		SgwErrorCodeEnum err = mapErrorCodes.get(code);
		if (!BaseUtil.isObjNull(err)) {
			status = err.getCode();
			code = err.name();
			msg = err.getMessage();

		}
		return new MessageResponse(new Timestamp(new Date().getTime()), status, code, msg, path);
	}


	private static final Map<String, SgwErrorCodeEnum> mapErrorCodes;
	static {
		final Map<String, SgwErrorCodeEnum> map = new HashMap<>();
		map.put(IdmErrorCodeEnum.E503IDM000.name(), SgwErrorCodeEnum.E503SGW001);

		mapErrorCodes = Collections.unmodifiableMap(map);
	}
}