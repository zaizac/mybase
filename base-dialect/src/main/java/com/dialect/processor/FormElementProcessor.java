/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.dialect.processor;


import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;

import com.dialect.AbstractBstElement;
import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementConstants;


/**
 * Convert the <bst:form> node to a normal <form> node
 *
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class FormElementProcessor extends AbstractBstElement {

	/**
	 * Form Element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public FormElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_FORM,
				false);

		String elementNm = getElementName(element);
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classAttrValue(elementNm, element));

		if (element.getAttributeValue(AttributeConstants.ATTR_METHOD) == null) {
			newElement.setAttribute(AttributeConstants.ATTR_METHOD, "POST");
		}

		final Element inCsrf = new Element(ElementConstants.HTML_INPUT);
		inCsrf.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		inCsrf.setAttribute(AttributeConstants.ATTR_NAME, "_csrf");
		inCsrf.setAttribute(AttributeConstants.ATTR_TH_VALUE, "${_csrf.token}");

		newElement.addChild(inCsrf);

		newElement.setRecomputeProcessorsImmediately(true);
		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}

}