/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.dialect.processor;


import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;

import com.dialect.AbstractBstElement;
import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementEnum;


/**
 * @author Mary Jane Buenaventura
 * @since Aug 27, 2015
 */
public class SpanElementProcessor extends AbstractBstElement {

	/**
	 * Span Element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public SpanElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String elementNm = getElementName(element);

		Element newElement = element.cloneElementNodeWithNewName(element.getParent(),
				ElementEnum.findProcessorByName(elementNm), false);
		newElement.setRecomputeProcessorsImmediately(true);
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, ElementEnum.findStyleByName(elementNm));

		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}

}
