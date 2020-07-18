/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.dialect;


import java.util.LinkedHashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.util.StringUtils;

import com.dialect.constants.ElementConstants;
import com.dialect.constants.ElementEnum;
import com.dialect.processor.ButtonElementProcessor;
import com.dialect.processor.DivElementProcessor;
import com.dialect.processor.FormElementProcessor;
import com.dialect.processor.InputElementProcessor;
import com.dialect.processor.MenuElementProcessor;
import com.dialect.processor.SelectElementProcessor;
import com.dialect.processor.SpanElementProcessor;
import com.dialect.processor.TableElementProcessor;


/**
 * @author Mary Jane Buenaventura
 * @since Aug 25, 2015
 */
public class CustomBstDialect extends AbstractDialect {

	/**
	 * Default Dialect Prefix
	 */
	public static final String DEFAULT_PREFIX = "bst";

	protected static final Set<IProcessor> PROCESSORS = new LinkedHashSet<>();

	static {
		for (ElementEnum v : ElementEnum.values()) {
			if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_FORM, v.getProcessor())) {
				PROCESSORS.add(new FormElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_INPUT, v.getProcessor())) {
				PROCESSORS.add(new InputElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_DIV, v.getProcessor())) {
				PROCESSORS.add(new DivElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_BUTTON, v.getProcessor())) {
				PROCESSORS.add(new ButtonElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_TABLE, v.getProcessor())) {
				PROCESSORS.add(new TableElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_UL, v.getProcessor())
					|| StringUtils.equalsIgnoreCase(ElementConstants.HTML_LI, v.getProcessor())) {
				PROCESSORS.add(new MenuElementProcessor(v.getName()));

			} else if (StringUtils.equalsIgnoreCase(ElementConstants.HTML_SELECT, v.getProcessor())) {
				PROCESSORS.add(new SelectElementProcessor(v.getName()));

			} else {
				PROCESSORS.add(new SpanElementProcessor(v.getName()));
			}
		}

	}


	@Override
	public String getPrefix() {
		return DEFAULT_PREFIX;
	}


	@Override
	public Set<IProcessor> getProcessors() {
		return PROCESSORS;
	}

}