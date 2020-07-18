/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.exception;


import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

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

import com.baseboot.web.constants.PageConstants;
import com.baseboot.web.constants.PageTemplate;
import com.baseboot.web.util.WebUtil;
import com.idm.sdk.exception.IdmException;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
@ControllerAdvice
public class GlobalExceptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionController.class);


	@ExceptionHandler(value = { NoHandlerFoundException.class, UnknownRequestParamException.class,
			ResourceNotFoundException.class })
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		LOGGER.info("handleError404: {}", e.getMessage());
		if (WebUtil.checkTokenError(e)) {
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		} else if (WebUtil.checkSystemDown(e)) {
			LOGGER.info("FORCE LOGOUT: System Down");
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		}
		return processView(e, "404");
	}


	@ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	public ModelAndView handleError400(HttpServletRequest request, Exception e) {
		LOGGER.info("handleError400: {}", e.getMessage());
		return processView(e, "400");
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView handleError405(HttpServletRequest request, Exception e) {
		LOGGER.info("handleError405: {}", e.getMessage());
		return processView(e, "405");
	}


	@ExceptionHandler(value = { RuntimeException.class, IOException.class, Exception.class,
			TemplateProcessingException.class, AccessDeniedException.class, TemplateProcessingException.class })
	public ModelAndView handleError500(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		LOGGER.info("handleError500: {}", e.getMessage());
		if (WebUtil.checkTokenError(e)) {
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		} else if (WebUtil.checkSystemDown(e)) {
			LOGGER.info("FORCE LOGOUT: System Down");
			ModelAndView mav = new ModelAndView(PageConstants.getRedirectUrl(PageConstants.PAGE_LOGOUT));
			request.setAttribute("FORCE_LOGOUT", true);
			return mav;
		} else if (e instanceof IdmException) {
			IdmException ex = (IdmException) e;
			LOGGER.info("IdmException: {} - {} ", ex.getInternalErrorCode(), ex.getMessage());
		}
		return processView(e, "500");
	}


	@ExceptionHandler(UrlExpireException.class)
	public ModelAndView handleErrorCustom(HttpServletRequest request, Exception e) {
		LOGGER.info("handleErrorCustom: {}", e.getMessage());
		return processView(e, "C01");
	}


	private ModelAndView processView(Exception exception, String errorCode) {
		ModelAndView mav = new ModelAndView(PageTemplate.TEMP_ERROR);
		mav.addObject("exception", exception);
		mav.addObject("errorcode", errorCode);
		return mav;
	}

}