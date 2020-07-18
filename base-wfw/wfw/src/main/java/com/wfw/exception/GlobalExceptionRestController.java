/**
 *
 */
package com.wfw.exception;


import java.io.IOException;
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
import org.springframework.web.servlet.NoHandlerFoundException;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.model.ErrorResponse;
import com.wfw.constant.ConfigConstants;
import com.wfw.sdk.constants.WfwErrorCodeEnum;
import com.wfw.sdk.exception.WfwException;
import com.wfw.util.WebUtil;


/**
 * Global Exception for Rest Service
 *
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@ControllerAdvice(basePackages = { ConfigConstants.BASE_PACKAGE_REST_CONTROLLER })
public class GlobalExceptionRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionRestController.class);


	@ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleError400(HttpServletRequest request, Exception e) {
		return processError(e, "400");
	}


	@ExceptionHandler(value = { HttpClientErrorException.class, NoHandlerFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleError404(HttpServletRequest request, Exception e) {
		return processError(e, "404");
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody ErrorResponse handleError405(HttpServletRequest request, Exception e) {
		return processError(e, "405");
	}


	@ExceptionHandler(TimeoutException.class)
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
	public @ResponseBody ErrorResponse handleError408(HttpServletRequest request, Exception e) {
		return processError(e, "408");
	}


	@ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody ErrorResponse handleError415(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, "415");
	}


	@ExceptionHandler(value = { RuntimeException.class, IOException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleError500(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, "500");
	}


	@ExceptionHandler(value = {})
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public @ResponseBody ErrorResponse handleError503(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, "503");
	}


	@ExceptionHandler(value = { WfwException.class, IdmException.class })
	public @ResponseBody ErrorResponse handleInternalError(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		String internalCode = null;
		if (e instanceof WfwException) {
			WfwException ex = (WfwException) e;
			internalCode = ex.getInternalErrorCode();
			WfwErrorCodeEnum rce = WfwErrorCodeEnum.findByName(ex.getInternalErrorCode());
			LOGGER.error("handleInternalError: {}", ex.getInternalErrorCode());
			response.setStatus(rce.getCode());
		} else if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			internalCode = ex.getInternalErrorCode();
			IdmErrorCodeEnum rce = IdmErrorCodeEnum.findByName(ex.getInternalErrorCode());
			LOGGER.error("handleInternalError: {}", ex.getInternalErrorCode());
			response.setStatus(rce.getCode());
		}

		return processError(e, internalCode);
	}


	@RequestMapping(value = "/error")
	public @ResponseBody ErrorResponse handleFilterError(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		return processError(e, String.valueOf(response.getStatus()));
	}


	private ErrorResponse processError(Exception ex, String errorCode) {
		LOGGER.error("WFW Response Error: {} - {}", errorCode, ex.getMessage());
		return new ErrorResponse(errorCode, ex.getMessage(), WebUtil.getStackTrace(ex));
	}

}