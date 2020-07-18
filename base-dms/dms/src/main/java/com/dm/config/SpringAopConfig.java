/**
 * Copyright 2019. 
 */
package com.dm.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * The Class SpringAopConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAopConfig {

}