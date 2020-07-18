/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.wfw.config.iam.CustomUserDetailsService;
import com.wfw.constant.PageConstants;
import com.wfw.exception.AccessDeniedExceptionHandler;


/**
 * Spring Security Configuration
 *
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	UserDetailsService userDetailService;


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/scripts/**").antMatchers("/styles/**").antMatchers("/images/**")
				.antMatchers("/webjars/**").antMatchers("/templates/**");
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


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
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
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins());
		configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET"));
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight
		// request will fail with 403 Invalid CORS request
		configuration
				.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Message-Id"));
		return new UrlBasedCorsConfigurationSource();
	}


	public List<String> allowedOrigins() {
		return new ArrayList<>();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Headers
		http.csrf().disable();
		http.cors().configurationSource(corsConfigurationSource()).and().headers().cacheControl().and()
				.contentTypeOptions().and().frameOptions().sameOrigin().httpStrictTransportSecurity()
				.includeSubDomains(true).maxAgeInSeconds(31536000).and().xssProtection().block(true)
				.xssProtectionEnabled(true).and()
				.addHeaderWriter(new StaticHeadersWriter("X-Custom-Security-Header", "header-value"))
				.addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
						"sandbox allow-forms allow-same-origin allow-scripts allow-popups allow-modals; "
								+ "object-src 'self' data:;" + "media-src 'self' blob:;"
								+ "default-src 'self' 'unsafe-inline';"
								+ "script-src 'self' 'unsafe-inline' 'unsafe-eval' fonts.gstatic.com; "
								+ "style-src 'self' 'unsafe-inline' fonts.googleapis.com fonts.gstatic.com; "
								+ "img-src * 'self' data: blob:; " + "font-src 'self' fonts.gstatic.com"))
				.addHeaderWriter(new StaticHeadersWriter("Server", "Here to serve you"))
				.addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security",
						"max-age=31536000; includeSubDomains; preload"));

		http.exceptionHandling()
				// access-denied-page: this is the page users will be
				// redirected to when they try to access protected areas
				.accessDeniedHandler(new AccessDeniedExceptionHandler()).and().authorizeRequests()
				.antMatchers("/task/**").authenticated().antMatchers("/admin/**").authenticated()
				.antMatchers("/maintenance/**").authenticated().antMatchers("/home").authenticated()
				.antMatchers("/wfw/**").permitAll().antMatchers("/wf/**").permitAll().and().formLogin()
				.loginPage(PageConstants.PAGE_LOGIN).loginProcessingUrl(PageConstants.PAGE_LOGIN)
				.defaultSuccessUrl(PageConstants.PAGE_LOGIN_SUC).failureUrl(PageConstants.PAGE_LOGIN_ERR)
				.usernameParameter("username").passwordParameter("password").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher(PageConstants.PAGE_LOGOUT))
				.logoutSuccessUrl(PageConstants.PAGE_LOGIN).invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new MDCFilter(), SecurityContextPersistenceFilter.class);
	}


	@Bean
	public UserDetailsService userDetailService() {
		return new CustomUserDetailsService();
	}

}