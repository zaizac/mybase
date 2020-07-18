/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config.audit;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditActionControl {

	AuditActionPolicy action();

}
