/**
 * Copyright 2019
 */
package com.be.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2018
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAopConfig {

}