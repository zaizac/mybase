/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core.exception;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;

import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.portal.constants.PageConstants;
import com.portal.web.util.WebUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class ErrorHandlerFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerFilter.class);

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	 
	private int customSessionExpiredErrorCode = 901;
	    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// DO NOTHING
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception ex) {
			LOGGER.error("AN EXCEPTION IS THROWN.....");
        	Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
 
            if (ase == null) {
                ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }
            
            String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
 
            if (ase != null) {
                if (ase instanceof AuthenticationException) {
                    throw ase;
                } else if (ase instanceof AccessDeniedException) {
                    if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                        if ("XMLHttpRequest".equals(ajaxHeader)) {
                            HttpServletResponse resp = (HttpServletResponse) response;
                            resp.sendError(this.customSessionExpiredErrorCode);
                        } else {
                            throw ase;
                        }
                    } else {
                        throw ase;
                    }
                }
            }
            
            LOGGER.info("XMLHttpRequest: {} - {}", ajaxHeader, "XMLHttpRequest".equals(ajaxHeader));
            
			if (!"XMLHttpRequest".equals(ajaxHeader)
					&& (ex.getMessage().contains(IdmErrorCodeEnum.I404IDM113.name())
					|| ex.getMessage().contains(IdmErrorCodeEnum.I404IDM115.name()))) {
				request.getRequestDispatcher(PageConstants.PAGE_LOGOUT).forward(request, response);
			} else {
				// call ErrorHandler and dispatch to error page
				request.setAttribute("errorMsg", WebUtil.getStackTrace(ex));
				request.setAttribute("errorcode", "EXC");
				request.getRequestDispatcher(PageConstants.PAGE_ERROR).forward(request, response);
			}
		}
	}

	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer
    {
        /**
         * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
         */
        protected void initExtractorMap() {
            super.initExtractorMap();
 
            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                public Throwable extractCause(Throwable throwable) {
                	throwable.printStackTrace();
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }
 
    }
 
    public void setCustomSessionExpiredErrorCode(int customSessionExpiredErrorCode) {
        this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
    }

	@Override
	public void destroy() {
		// DO NOTHING
	}

}