/**
 *
 */
package com.baseboot.web.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.baseboot.web.core.AbstractController;


/**
 * @author Jhayne
 *
 */
public class PaymentSwitch extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentSwitch.class);

	@Autowired
	private MessageSource messageSource;


	public MessageSource getMessageSource() {
		return messageSource;
	}

}