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
import com.dialect.constants.ElementEnum;
import com.dialect.constants.StyleConstants;


/**
 * Convert the <bst:btn-*> node to a normal <button> node
 *
 * @author Mary Jane Buenaventura
 * @since Aug 26, 2015
 */
public class ButtonElementProcessor extends AbstractBstElement {

	/**
	 * Button Element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public ButtonElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String elementNm = getElementName(element);

		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_BUTTON,
				false);
		newElement.setRecomputeProcessorsImmediately(true);

		String classVal = ElementEnum.findStyleByName(elementNm);

		if (element.getAttributeValue(AttributeConstants.ATTR_CLASS) != null) {
			classVal = classVal + StyleConstants.SPACE + element.getAttributeValue(AttributeConstants.ATTR_CLASS);
		}
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classVal);

		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}

}