/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config.init;


import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext, new MultipartFilter());
	}

}
