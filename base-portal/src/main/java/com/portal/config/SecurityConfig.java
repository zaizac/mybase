/**
 * Copyright 2018. Bestinet Sdn Bhd
 */

package com.portal.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portal.config.iam.CustomAuthenticationManager;
import com.portal.constants.PageConstants;
import com.portal.core.exception.AccessDeniedExceptionHandler;


/**
 * Configuration of Spring Security
 *
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	@Description("This is to ensure that the static content (JavaScript, CSS, etc) is accessible from the login page without authentication")
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}


	@Bean
	public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter upaf = new UsernamePasswordAuthenticationFilter();
		upaf.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(PageConstants.PAGE_LOGIN, "POST"));
		upaf.setAuthenticationManager(authenticationManagerBean());
		upaf.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
		upaf.setAuthenticationSuccessHandler(authSuccessHandler());
		upaf.setUsernameParameter("username");
		upaf.setPasswordParameter("password");
		return upaf;
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new CustomAuthenticationManager();
	}


	@Bean
	public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
		SimpleUrlAuthenticationFailureHandler suafh = new SimpleUrlAuthenticationFailureHandler();
		suafh.setDefaultFailureUrl(PageConstants.PAGE_LOGIN);
		return suafh;
	}


	@Bean
	public SimpleUrlAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		SimpleUrlAuthenticationSuccessHandler suash = new SimpleUrlAuthenticationSuccessHandler();
		suash.setDefaultTargetUrl(PageConstants.PAGE_SRC);
		return suash;
	}


	@Bean
	public LoginUrlAuthenticationEntryPoint authenticationEntryPoint() {
		return new LoginUrlAuthenticationEntryPoint(PageConstants.PAGE_LOGIN);
	}


	@Bean
	public LoginSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}


	@Bean
	public LogoutHandler logoutHandler() {
		return new LogoutHandler();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// default implementation ignored
	}


	@Bean
	public BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
		return new BasicAuthenticationFilter(authenticationManagerBean(), authenticationEntryPoint());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Headers
		http.cors().configurationSource(corsConfigurationSource()).and().headers()
				// .defaultsDisabled()
				.cacheControl().and().contentTypeOptions().and().frameOptions().sameOrigin()
				.httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000).and()
				.xssProtection().block(true).xssProtectionEnabled(true).and()
				.addHeaderWriter(new StaticHeadersWriter("X-Custom-Security-Header", "header-value"))
				/*
				 * .addHeaderWriter(new
				 * StaticHeadersWriter("Content-Security-Policy",
				 * "sandbox allow-forms allow-same-origin allow-scripts allow-popups allow-modals; "
				 * + "object-src 'self' data:;" + "media-src 'self' blob:;"
				 * + "default-src 'self' 'unsafe-inline';" +
				 * "script-src 'self' 'unsafe-inline' 'unsafe-eval' fonts.gstatic.com; "
				 * +
				 * "style-src 'self' 'unsafe-inline' fonts.googleapis.com fonts.gstatic.com; "
				 * + "img-src * 'self' data: blob:; " +
				 * "font-src 'self' fonts.gstatic.com"))
				 */
				.addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
						"script-src 'self' 'unsafe-inline' 'unsafe-eval'"))
				.addHeaderWriter(new StaticHeadersWriter("Server", "Here to serve you"))
				.addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security",
						"max-age=31536000; includeSubDomains; preload"));

		http.exceptionHandling()
				// access-denied-page: this is the page users will be
				// redirected to when they try to access protected areas
				.accessDeniedHandler(new AccessDeniedExceptionHandler()).and()
				// The intercept-url configuration is where we specify what
				// roles are allowed access to what areas.
				.authorizeRequests()
				// PERMIT ALL
				.antMatchers("/**/ecovid19_icon.png").permitAll().antMatchers("/scripts/**").permitAll()
				.antMatchers("/styles/**").permitAll().antMatchers("/images/**").permitAll()
				.antMatchers("/webjars/**").permitAll().antMatchers("/templates/**").permitAll()
				.antMatchers("/default/**").permitAll()

				// UNAUTHENTICATED USER
				.antMatchers(PageConstants.PAGE_COMPONENTS).permitAll().antMatchers(PageConstants.PAGE_WELCOME)
				.anonymous().antMatchers(PageConstants.PAGE_IDM_FRGT_PWORD).permitAll()
				.antMatchers(PageConstants.PAGE_MAIN + "/**").permitAll()
				.antMatchers(PageConstants.PATIENT_MNGMNT + "/**").permitAll()
				.antMatchers(PageConstants.PAGE_CMN_OTP + "/**").permitAll()
				.antMatchers(PageConstants.PAGE_CMN_CAPTCHA + "/**").permitAll()
				.antMatchers(PageConstants.SERVICE_CHECK + "/**").permitAll().antMatchers(PageConstants.PAGE_SRC)
				.permitAll().antMatchers(PageConstants.PAGE_HOME).authenticated()
				.antMatchers(PageConstants.PAGE_CMN_STATIC + "/**").permitAll()
				.antMatchers(PageConstants.DOCUMENTS + "/**").permitAll()
				.antMatchers(PageConstants.INQUIRY + "/**").permitAll()

				.anyRequest().authenticated().and()
				// This is where we configure our login form.
				.formLogin()
				// login-page: the page that contains the login screen
				.loginPage(PageConstants.PAGE_LOGIN)
				// login-processing-url: this is the URL to which the login
				// form should be submitted
				.loginProcessingUrl(PageConstants.PAGE_LOGIN)
				// success-url: Handler to redirected the user to any
				// specific page
				.successHandler(authSuccessHandler())
				// authentication-failure-url: the URL to which the user
				// will be redirected if they fail login
				.failureUrl(PageConstants.PAGE_LOGIN_ERR)
				// username-parameter: the name of the request parameter
				// which contains the username
				.usernameParameter("username")
				// password-parameter: the name of the request parameter
				// which contains the password
				.passwordParameter("password").permitAll()

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				// session-management/concurrency-control@max-sessions
				.invalidSessionUrl(PageConstants.PAGE_LOGIN).maximumSessions(1)
				// session-management/concurrency-control@error-if-maximum-exceeded
				.maxSessionsPreventsLogin(true)
				// session-management/concurrency-control@expired-url
				.expiredUrl(PageConstants.PAGE_LOGIN).and().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher(PageConstants.PAGE_LOGOUT))
				.logoutSuccessHandler(logoutHandler()).deleteCookies("JSESSIONID").invalidateHttpSession(false)
				.and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new MDCFilter(), SecurityContextPersistenceFilter.class);
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins());
		configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET"));
		configuration.setAllowCredentials(true);

		List<String> headers = Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Message-Id");
		configuration.setAllowedHeaders(headers);
		return new UrlBasedCorsConfigurationSource();
	}


	public List<String> allowedOrigins() {
		return new ArrayList<>();
	}

}