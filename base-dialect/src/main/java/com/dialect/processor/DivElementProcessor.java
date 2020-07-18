/**
 * Copyright 2016. Bestinet Sdn Bhd
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
import com.dialect.constants.ElementEnum;
import com.dialect.constants.ElementHelper;


/**
 * @author Mary Jane Buenaventura
 * @since Aug 26, 2015
 */
public class DivElementProcessor extends AbstractBstElement {

	/**
	 * Div element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public DivElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {

		final ApplicationContext appCtx = ((SpringWebContext) arguments.getContext()).getApplicationContext();
		MessageService messageService = appCtx.getBean(MessageService.class);

		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV,
				false);
		newElement.setRecomputeProcessorsImmediately(true);

		String elementNm = getElementName(element);
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classAttrValue(elementNm, element));

		if (elementNm.contains(ElementEnum.ALERT.getName())) {
			newElement = ElementHelper.processAlert(element, newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.OTP.getName())) {
			newElement = ElementHelper.processOTP(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.OTP_EMAIL.getName())) {
			newElement = ElementHelper.processOTPEmail(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.CAPTCHA.getName())) {
			newElement = ElementHelper.processCaptcha(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.FILE.getName())) {
			newElement = ElementHelper.processFile(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.FILE_THUMBNAIL.getName())) {
			newElement = ElementHelper.processFileThumbnail(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.FILE_THUMB_VIEW.getName())) {
			newElement = ElementHelper.processFileThumbnailView(newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.FILE_VIEW.getName())) {
			newElement = ElementHelper.processFileView(newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.FILE_BUTTON.getName())) {
			newElement = ElementHelper.processFileButton(newElement, messageService);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.PORTLET.getName())) {
			newElement = ElementHelper.processPortlet(newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.TAB_H_PANE.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_V_PANE.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_PILL_PANE.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_ROLE, "tabpanel");
		} else if (elementNm.equalsIgnoreCase(ElementEnum.WIDGET_HEADER.getName())) {
			newElement = ElementHelper.processWidgetHeader(newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.WIDGET.getName())) {
			newElement = ElementHelper.processWidget(newElement);
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