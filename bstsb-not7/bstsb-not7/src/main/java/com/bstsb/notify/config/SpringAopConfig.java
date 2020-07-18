/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.notify.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * The Class SpringAopConfig.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAopConfig {

}