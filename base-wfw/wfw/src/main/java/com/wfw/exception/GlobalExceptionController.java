/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.exception;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.tiles.definition.NoSuchDefinitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.wfw.constant.ConfigConstants;
import com.wfw.constant.PageConstants;
import com.wfw.sdk.exception.WfwException;
import com.wfw.util.WebUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@ControllerAdvice(basePackages = { ConfigConstants.BASE_PACKAGE_CONTROLLER })
public class GlobalExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);


	@ExceptionHandler(value = { NoHandlerFoundException.class })
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		logger.error("handleError404: {}", e);
		try {
			sendMail("404", Arrays.toString(e.getStackTrace()));
		} catch (Exception ex) {
			logger.error("Error:sendMail:404 {}", ex);
		}
		return processView(e, "404");
	}


	@ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	public ModelAndView handleError400(HttpServletRequest request, Exception e) {
		logger.error("handleError400: ", e);

		try {
			sendMail("400", Arrays.toString(e.getStackTrace()));
		} catch (Exception ex) {
			logger.error("Error:sendMail:400 {}", ex);
		}
		return processView(e, "400");
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView handleError405(HttpServletRequest request, Exception e) {
		logger.error("handleError405: {}", e);
		try {
			sendMail("405", Arrays.toString(e.getStackTrace()));
		} catch (Exception ex) {
			logger.error("Error:sendMail:405 {}", ex);
		}
		return processView(e, "405");
	}


	@ExceptionHandler(value = { RuntimeException.class, IOException.class, NoSuchDefinitionException.class,
			Exception.class, TemplateProcessingException.class, AccessDeniedException.class,
			TemplateProcessingException.class })
	public ModelAndView handleError500(HttpServletRequest request, Exception e) {
		logger.error("handleError500: {}", e.getMessage());
		if (WebUtil.checkTokenError(e)) {
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		} else if (WebUtil.checkSystemDown(e)) {
			logger.info("FORCE LOGOUT: System Down");
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		} else if (e instanceof WfwException) {
			WfwException ex = (WfwException) e;
			logger.error("FORCE LOGOUT: WfwException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
		}
		try {
			sendMail("500", Arrays.toString(e.getStackTrace()));
		} catch (Exception ex) {
			logger.error("Error:sendMail:500 {}", ex);
		}
		return processView(e, "500");
	}


	private ModelAndView processView(Exception exception, String errorCode) {
		ModelAndView mav = new ModelAndView("pg_error");
		mav.addObject("exception", exception);
		mav.addObject("errorcode", errorCode);
		return mav;
	}


	private void sendMail(String errorCode, String errorMessage) {
		String message = "Error Code: " + errorCode + "\nError Message: " + errorMessage;
		logger.info("Exception Error!!! [{}]", message);
	}

}