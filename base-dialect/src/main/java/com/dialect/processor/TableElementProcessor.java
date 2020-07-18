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
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 18, 2015
 */
public class TableElementProcessor extends AbstractBstElement {

	/**
	 * Table Element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public TableElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {

		final Element respTable = new Element(ElementConstants.HTML_DIV);
		respTable.setAttribute(AttributeConstants.ATTR_CLASS, "table-responsive");

		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_TABLE,
				false);
		newElement.setRecomputeProcessorsImmediately(true);

		String elementNm = getElementName(element);
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classAttrValue(elementNm, element));

		if (BaseUtil.isObjNull(element.getAttributeValue(AttributeConstants.ATTR_DATA_PAGE_LEN))) {
			newElement.setAttribute("data-page-length", "15");
		}

		if (BaseUtil.isObjNull(element.getAttributeValue(AttributeConstants.ATTR_DATA_PAGING))) {
			newElement.setAttribute("data-paging", "true");
		}

		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}
}
