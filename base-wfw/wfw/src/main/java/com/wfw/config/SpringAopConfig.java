/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 14, 2018
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAopConfig {

}