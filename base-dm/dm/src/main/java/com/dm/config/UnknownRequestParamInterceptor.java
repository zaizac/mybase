/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.config;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dm.exception.UnknownRequestParamException;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class UnknownRequestParamInterceptor extends HandlerInterceptorAdapter {

	private String[] defaultDatatableParam = new String[] { "lang", "draw", "columns[", "order[", "start", "length",
			"search[", "_", "submit", "search", "reset", "create", "update" };


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			checkParams(request, getDeclaredRequestParams(handlerMethod));
		}
		return true;
	}


	private void checkParams(HttpServletRequest request, Set<String> allowedParams) {
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String param = en.nextElement();
			if (!allowedParams.contains(param) && !param.contains(".") && !checkDatatableParam(param)) {
				throw new UnknownRequestParamException(param, allowedParams);
			}
		}
	}


	private boolean checkDatatableParam(String param) {
		for (String str : Arrays.asList(defaultDatatableParam)) {
			if (BaseUtil.isEquals(str, param) || param.startsWith(str)) {
				return true;
			}
		}
		return false;
	}


	private Set<String> getDeclaredRequestParams(HandlerMethod handlerMethod) {
		Set<String> declaredRequestParams = new HashSet<>();
		declaredRequestParams.add("_csrf"); // DEFAULT PARAM

		MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
		ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

		for (MethodParameter methodParameter : methodParameters) {
			if (methodParameter.hasParameterAnnotation(RequestParam.class)) {
				RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
				if (StringUtils.hasText(requestParam.value())) {
					declaredRequestParams.add(requestParam.value());
				} else {
					methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
					declaredRequestParams.add(methodParameter.getParameterName());
				}
			} else {
				methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
				for (Field field : methodParameter.getParameterType().getDeclaredFields()) {
					declaredRequestParams.add(field.getName());
				}
			}

		}

		return declaredRequestParams;
	}

}