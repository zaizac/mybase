/**
 *
 */
package com.dialect.processor;


import org.springframework.context.ApplicationContext;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.spring4.context.SpringWebContext;

import com.dialect.AbstractBstElement;
import com.dialect.MessageService;
import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementConstants;


/**
 * @author Mary Jane Buenaventura
 * @since 22/02/2018
 */
public class SelectElementProcessor extends AbstractBstElement {

	/**
	 * Select Element Processor
	 *
	 * @param elementName
	 */
	public SelectElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String elementNm = getElementName(element);
		final ApplicationContext appCtx = ((SpringWebContext) arguments.getContext()).getApplicationContext();
		appCtx.getBean(MessageService.class);
		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_SELECT,
				false);
		newElement.setRecomputeProcessorsImmediately(true);
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classAttrValue(elementNm, element));

		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);

		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}

}
