/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.dialect.processor;


import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.util.StringUtils;

import com.dialect.AbstractBstElement;
import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementConstants;
import com.dialect.constants.ElementEnum;
import com.dialect.constants.ElementHelper;
import com.util.BaseUtil;


/**
 * Convert the <bst:input-*> node to a normal <input> node
 *
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class InputElementProcessor extends AbstractBstElement {

	/**
	 * Input Element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public InputElementProcessor(String elementName) {
		super(elementName);
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String elementNm = getElementName(element);

		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_INPUT,
				false);
		newElement.setRecomputeProcessorsImmediately(true);

		// Process Type Attribute
		newElement = typeAttrValue(elementNm, element, newElement);

		// Process Class Attribute
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, classAttrValue(elementNm, element));

		// Process Time Picker
		if (StringUtils.equals(elementNm, ElementEnum.INPUT_AMOUNT.getName())) {
			newElement = ElementHelper.processAmount(newElement);
		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_TIME.getName())) {
			newElement = ElementHelper.processTimePicker(newElement);
		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_DATE_RANGE.getName())) {
			newElement = ElementHelper.processDateRangePicker(newElement);
		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_DATE.getName())) {
			newElement = ElementHelper.processDatePicker(newElement);
		} else if (StringUtils.equals(elementNm, ElementEnum.RADIO.getName())) {
			newElement = ElementHelper.processRadioButton(newElement);
		} else if (StringUtils.equals(elementNm, ElementEnum.CHECKBOX.getName())) {
			newElement = ElementHelper.processCheckboxButton(newElement);
		}

		processLblAttr(element, newElement);

		element.getParent().insertAfter(element, newElement);
		element.getParent().removeChild(element);
		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 100000;
	}


	private Element typeAttrValue(String elementNm, Element element, Element newElement) {
		if (StringUtils.equals(elementNm, ElementEnum.TEXTAREA.getName())) {
			newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_TEXTAREA,
					false);
			newElement.setRecomputeProcessorsImmediately(true);
		} else if (StringUtils.equals(elementNm, ElementEnum.RADIO.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_RADIO);

		} else if (StringUtils.equals(elementNm, ElementEnum.CHECKBOX.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_CHECKBOX);

		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_HIDDEN.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);

		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_PASSWORD.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_PWORD);

		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_NUMBER.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_NUMBER);

		} else if (StringUtils.equals(elementNm, ElementEnum.INPUT_CONTACT.getName())) {
			newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_TEL);

		} else {
			if (element.getAttributeValue(AttributeConstants.ATTR_TYPE) != null) {
				newElement.setAttribute(AttributeConstants.ATTR_TYPE,
						element.getAttributeValue(AttributeConstants.ATTR_TYPE));
			} else {
				newElement.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_TEXT);
			}
		}
		return newElement;
	}


	private void processLblAttr(Element element, Element newElement) {
		if (element.getAttributeValue("lbl") != null) {
			String forVal = element.getAttributeValue(AttributeConstants.ATTR_NAME);
			if (BaseUtil.isObjNull(forVal)) {
				forVal = element.getAttributeValue("th:field");
				if (!BaseUtil.isObjNull(forVal)) {
					forVal = forVal.replace("*{", "").replace("}", "");
				}
			}

			Element bindElement = new Element(ElementConstants.HTML_LABEL);
			String lblClass = ElementEnum.LABEL.getStyle();
			if (element.getAttributeValue("data-constraints") != null) {
				String data = element.getAttributeValue("data-constraints");
				if (StringUtils.contains(data, "@Required")) {
					bindElement.setAttribute(AttributeConstants.ATTR_TH_CLASSAPPEND, "required");
				}
			}
			bindElement.setAttribute(AttributeConstants.ATTR_CLASS, lblClass);
			bindElement.setAttribute("for", forVal);
			bindElement.setAttribute(AttributeConstants.ATTR_TH_TEXT, element.getAttributeValue("lbl"));
			element.getParent().insertBefore(element, bindElement);
			newElement.removeAttribute("lbl");
		}
	}

}