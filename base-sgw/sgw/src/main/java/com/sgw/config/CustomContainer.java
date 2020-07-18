/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.sgw.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class CustomContainer implements EmbeddedServletContainerCustomizer {

	@Value("${sgw.server.port}")
	private int serverPort;


	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(serverPort);
	}

}