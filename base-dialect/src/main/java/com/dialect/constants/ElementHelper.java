/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.dialect.constants;


import java.util.Date;
import java.util.List;

import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.util.StringUtils;

import com.dialect.MessageService;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class ElementHelper {

	private static final String AMPERSAND = "#";

	private static final String EMAIL = "email";

	private static final String MOBILE = "mobile";

	private static final String VERIFY = "verify";

	private static final String OTP = "otp";

	private static final String OTP_BOX = "otp-box";

	private static final String OTP_EMAIL = "otpEmail";

	private static final String OTP_MOBILE = "otpMobile";

	private static final String OTP_TIMER = "otpTimer";

	private static final String OTP_TYPE = "otpType";

	private static final String OTP_VAL = "otpVal";

	private static final String DISPLAY_NONE = "display:none";

	private static final String NO_IMAGE_SRC = "@{/webjars/monster-ui/images/no_image.jpg}";


	private ElementHelper() {
		throw new IllegalStateException(ElementHelper.class.getName());
	}


	/**
	 * Process Alert Component
	 *
	 * @param element
	 *             Orginal Element
	 * @param newElement
	 *             Custom Element
	 * @return Updated Custom Element
	 */
	public static Element processAlert(Element element, Element newElement) {
		if (element.getAttributeValue(AttributeConstants.ATTR_TYPE) != null
				&& "dismissible".equalsIgnoreCase(element.getAttributeValue(AttributeConstants.ATTR_TYPE))) {
			final Element eClose = new Element(ElementConstants.HTML_ANCHOR);
			eClose.setRecomputeProcessorsImmediately(true);
			eClose.setAttribute(AttributeConstants.ATTR_CLASS, "close");
			eClose.setAttribute(AttributeConstants.ATTR_DATA_DISMISS, ElementEnum.ALERT.getName());
			eClose.setAttribute(AttributeConstants.ATTR_HREF, AMPERSAND);
			eClose.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");
			eClose.setAttribute(AttributeConstants.ATTR_TH_TEXT, "#{×}");
			newElement.removeAttribute(AttributeConstants.ATTR_TYPE);
			newElement.insertChild(0, eClose);
			newElement.setRecomputeProcessorsImmediately(true);
		}
		return newElement;
	}


	/**
	 * Generates OTP Element
	 *
	 * @param element
	 *             Original Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processOTP(Element element, MessageService messageService) {
		String otpId = String.valueOf(new Date().getTime());

		final Element otpBox = new Element(ElementConstants.HTML_DIV);
		otpBox.setAttribute(AttributeConstants.ATTR_CLASS, OTP_BOX);
		otpBox.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP);

		Element otp = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_INPUT, false);
		otp.setAttribute(AttributeConstants.ATTR_REQUIRED, AttributeConstants.ATTR_REQUIRED);
		otp.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otp.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_EMAIL);

		if (!StringUtils.isEmptyOrWhitespace(otp.getAttributeValue(EMAIL))) {
			otp.setAttribute(AttributeConstants.ATTR_TH_VALUE, otp.getAttributeValue(EMAIL));
			otp.removeAttribute(EMAIL);
		}

		final Element inputMobile = new Element(ElementConstants.HTML_INPUT);
		inputMobile.setAttribute(AttributeConstants.ATTR_ID, OTP_MOBILE);
		inputMobile.setAttribute(AttributeConstants.ATTR_CLASS, ElementEnum.INPUT.getStyle());
		inputMobile.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		inputMobile.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_EMAIL);

		if (!StringUtils.isEmptyOrWhitespace(otp.getAttributeValue(MOBILE))) {
			inputMobile.setAttribute(AttributeConstants.ATTR_TH_VALUE, otp.getAttributeValue(MOBILE));
			otp.removeAttribute(MOBILE);
		}

		final Element div = new Element(ElementConstants.HTML_DIV);

		final Element span = new Element(ElementConstants.HTML_SPAN);
		span.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FORM_BTN_SECONDARY + " otp-new");
		span.setAttribute(AttributeConstants.ATTR_DATA_ID, otpId);
		span.setAttribute(AttributeConstants.ATTR_DATA_TRIGGER, VERIFY);

		String lbl = element.getAttributeValue(AttributeConstants.ATTR_DATA_BTN_LABEL);
		if (!BaseUtil.isObjNull(lbl)) {
			span.addChild(new Text(lbl));
		} else {
			span.addChild(new Text(messageService.getMessage(MessageDialectConstants.BTN_VERIFY)));
		}

		final Element errLbl = new Element(ElementConstants.HTML_P);
		errLbl.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_ERRORS);

		final Element otpVal = new Element(ElementConstants.HTML_INPUT);
		otpVal.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpVal.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_VAL);

		final Element otpTimer = new Element(ElementConstants.HTML_INPUT);
		otpTimer.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpTimer.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_TIMER);

		final Element otpType = new Element(ElementConstants.HTML_INPUT);
		otpType.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpType.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_TYPE);
		otpType.setAttribute(AttributeConstants.ATTR_TH_VALUE, OTP);

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);

		div.addChild(otpType);
		div.addChild(span);

		addOnDiv.addChild(otp);
		addOnDiv.addChild(inputMobile);
		addOnDiv.addChild(div);
		otpBox.addChild(addOnDiv);
		otpBox.addChild(errLbl);
		otpBox.addChild(otpVal);
		otpBox.addChild(otpTimer);
		otpBox.setRecomputeProcessorsImmediately(true);
		return otpBox;
	}


	/**
	 * Generates OTP with Email
	 *
	 * @param element
	 *             Orignal Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processOTPEmail(Element element, MessageService messageService) {
		String otpId = String.valueOf(new Date().getTime());

		final Element otpBox = new Element(ElementConstants.HTML_DIV);
		otpBox.setAttribute(AttributeConstants.ATTR_CLASS, OTP_BOX);
		otpBox.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP);

		Element otp = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_INPUT, false);
		otp.setAttribute(AttributeConstants.ATTR_REQUIRED, AttributeConstants.ATTR_REQUIRED);
		otp.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_EMAIL);

		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP);

		final Element addOnDivReset = new Element(ElementConstants.HTML_DIV);
		addOnDivReset.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);

		final Element spanReset = new Element(ElementConstants.HTML_SPAN);
		spanReset.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FORM_BTN_SECONDARY
				+ StyleConstants.SPACE + StyleConstants.STYLE_INPUT_GROUP_TEXT);
		spanReset.setAttribute(AttributeConstants.ATTR_DATA_TRIGGER, "reset");
		spanReset.setAttribute(AttributeConstants.ATTR_STYLE, DISPLAY_NONE);

		final Element refresh = new Element(ElementConstants.HTML_I);
		refresh.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_REFRESH);
		refresh.setAttribute(AttributeConstants.ATTR_TITLE,
				messageService.getMessage(MessageDialectConstants.OTP_RESET));
		refresh.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		spanReset.addChild(refresh);
		addOnDivReset.addChild(spanReset);

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);

		final Element span = new Element(ElementConstants.HTML_SPAN);
		span.setAttribute(AttributeConstants.ATTR_DATA_ID, otpId);
		span.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FORM_BTN_SECONDARY + " otp-new");
		span.setAttribute(AttributeConstants.ATTR_DATA_TRIGGER, VERIFY);
		String lbl = element.getAttributeValue(AttributeConstants.ATTR_DATA_BTN_LABEL);
		if (!BaseUtil.isObjNull(lbl)) {
			span.addChild(new Text(lbl));
		} else {
			span.addChild(new Text(messageService.getMessage(MessageDialectConstants.OTP_VERIFY)));
		}

		final Element spanValidate = new Element(ElementConstants.HTML_SPAN);
		spanValidate.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FORM_BTN_SUCCESS);
		spanValidate.setAttribute(AttributeConstants.ATTR_DATA_TRIGGER, "validate");
		spanValidate.setAttribute(AttributeConstants.ATTR_TITLE,
				messageService.getMessage(MessageDialectConstants.OTP_VERIFIED));
		spanValidate.setAttribute(AttributeConstants.ATTR_STYLE, DISPLAY_NONE);

		final Element icon = new Element(ElementConstants.HTML_I);
		icon.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_CHECK);
		icon.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		final Element errLbl = new Element(ElementConstants.HTML_P);
		errLbl.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_ERRORS);

		final Element otpVal = new Element(ElementConstants.HTML_INPUT);
		otpVal.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpVal.setAttribute(AttributeConstants.ATTR_ID, OTP_VAL);
		otpVal.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_VAL);

		final Element otpTimer = new Element(ElementConstants.HTML_INPUT);
		otpTimer.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpTimer.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_TIMER);

		final Element otpType = new Element(ElementConstants.HTML_INPUT);
		otpType.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		otpType.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, OTP_TYPE);
		otpType.setAttribute(AttributeConstants.ATTR_TH_VALUE, EMAIL);

		spanValidate.insertChild(0, icon);
		addOnDiv.addChild(otpType);
		addOnDiv.addChild(span);
		addOnDiv.addChild(spanValidate);
		inpGrp.addChild(otp);
		inpGrp.addChild(addOnDivReset);
		inpGrp.addChild(addOnDiv);
		otpBox.addChild(inpGrp);
		otpBox.addChild(errLbl);
		otpBox.addChild(otpVal);
		otpBox.addChild(otpTimer);
		otpBox.setRecomputeProcessorsImmediately(true);
		return otpBox;
	}


	/**
	 * Generate Captcha Element
	 *
	 * @param element
	 *             Original Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processCaptcha(Element element, MessageService messageService) {
		String captchaId = String.valueOf(new Date().getTime());

		Element captcha = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_INPUT,
				false);
		captcha.setAttribute(AttributeConstants.ATTR_DATA_ID, captchaId);
		captcha.setAttribute(AttributeConstants.ATTR_REQUIRED, AttributeConstants.ATTR_REQUIRED);
		captcha.setAttribute(AttributeConstants.ATTR_ONINVALID,
				"this.setCustomValidity('Please enter the code above')");
		captcha.setAttribute(AttributeConstants.ATTR_ONINPUT, "setCustomValidity('')");
		captcha.setAttribute(AttributeConstants.ATTR_PLACEHOLDER,
				messageService.getMessage(MessageDialectConstants.CAPTCHA));

		final Element captchaBox = new Element(ElementConstants.HTML_DIV);
		captchaBox.setAttribute(AttributeConstants.ATTR_CLASS, "captcha-box");
		captchaBox.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, "captcha");

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append("/captcha/");
		sbUrl.append(element.getAttributeValue(AttributeConstants.ATTR_DATA_PAGE));
		sbUrl.append("?captchaId=");
		sbUrl.append(captchaId);

		final Element img = new Element(ElementConstants.HTML_IMG);
		img.setAttribute(AttributeConstants.ATTR_TH_SRC, "@{" + sbUrl.toString() + "}");

		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP);

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_APPEND + " reload");

		final Element addOn = new Element(ElementConstants.HTML_SPAN);
		addOn.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_TEXT);
		addOn.setAttribute(AttributeConstants.ATTR_DATA_TRIGGER, "reload");

		final Element icon = new Element(ElementConstants.HTML_I);
		icon.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_REFRESH);
		icon.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		addOn.insertChild(0, icon);
		addOnDiv.insertChild(0, addOn);
		inpGrp.insertChild(0, captcha);
		inpGrp.insertChild(1, addOnDiv);

		captchaBox.insertChild(0, img);
		captchaBox.insertChild(1, inpGrp);
		captchaBox.setRecomputeProcessorsImmediately(true);
		return captchaBox;
	}


	/**
	 * Generate Portlet Element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processPortlet(Element element) {
		Element portlet = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		portlet.clearChildren();

		final Element spanBody = new Element(ElementConstants.HTML_DIV);
		spanBody.setAttribute(AttributeConstants.ATTR_CLASS, "span12");

		final Element grid = new Element(ElementConstants.HTML_DIV);
		grid.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_GRID + " simple");

		final Element portletBody = new Element(ElementConstants.HTML_DIV);
		portletBody.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_GRID_BODY);

		List<Element> nodeLst = element.getElementChildren();
		for (Element node : nodeLst) {
			portletBody.addChild(node);
		}

		if (element.getAttributeValue(AttributeConstants.ATTR_TITLE) != null
				|| element.getAttributeValue(AttributeConstants.ATTR_TH_TITLE) != null) {
			final Element portletHeader = new Element(ElementConstants.HTML_DIV);
			portletHeader.setAttribute(AttributeConstants.ATTR_CLASS,
					StyleConstants.STYLE_GRID_TITLE + " no-border");

			final Element portletTitle = new Element(ElementConstants.HTML_H4);
			portletTitle.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_SEMI_BOLD);

			if (element.getAttributeValue(AttributeConstants.ATTR_TITLE) != null) {
				portletTitle.addChild(new Text(element.getAttributeValue(AttributeConstants.ATTR_TITLE)));
			} else if (element.getAttributeValue(AttributeConstants.ATTR_TH_TITLE) != null) {
				portletTitle.setAttribute(AttributeConstants.ATTR_TH_TITLE,
						element.getAttributeValue(AttributeConstants.ATTR_TH_TITLE));
			}

			final Element divTools = new Element(ElementConstants.HTML_DIV);
			divTools.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_TOOLS);

			final Element aTools = new Element(ElementConstants.HTML_ANCHOR);
			aTools.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_COLLAPSE);
			aTools.setAttribute(AttributeConstants.ATTR_HREF, "javascript:;");
			divTools.insertChild(0, aTools);

			portletHeader.insertChild(0, portletTitle);
			portletHeader.insertChild(1, divTools);
			portletHeader.setRecomputeProcessorsImmediately(true);

			grid.removeAttribute(AttributeConstants.ATTR_TITLE);
			grid.insertChild(0, portletHeader);
			grid.insertChild(1, portletBody);
			grid.setRecomputeProcessorsImmediately(true);
		} else {
			grid.insertChild(0, portletBody);
		}
		spanBody.insertChild(0, grid);
		portlet.insertChild(0, spanBody);
		portlet.setRecomputeProcessorsImmediately(true);
		return portlet;
	}


	/**
	 * Generate Timepicker Element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processTimePicker(Element element) {
		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_INPUT_GROUP + " bootstrap-timepicker timepicker time");

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);

		final Element addOn = new Element(ElementConstants.HTML_SPAN);
		addOn.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_TEXT);

		final Element icon = new Element(ElementConstants.HTML_I);
		icon.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_CLOCK_O);
		icon.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		addOn.insertChild(0, icon);
		addOnDiv.insertChild(0, addOn);
		inpGrp.insertChild(0, addOnDiv);
		inpGrp.insertChild(1, element);

		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate Daterangepicker Element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processDateRangePicker(Element element) {
		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP + " daterange");

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);
		addOnDiv.setAttribute(AttributeConstants.ATTR_ID, element.getAttributeValue(AttributeConstants.ATTR_ID));

		final Element addOn = new Element(ElementConstants.HTML_SPAN);
		addOn.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_INPUT_GROUP_TEXT + StyleConstants.SPACE + StyleConstants.STYLE_CURSOR_POINTER);

		final Element icon = new Element(ElementConstants.HTML_I);
		icon.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_CALENDAR);
		icon.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		final Element fromInput = new Element(ElementConstants.HTML_INPUT);
		fromInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FORM_CONTROL);
		fromInput.setAttribute(AttributeConstants.ATTR_READONLY, AttributeConstants.ATTR_READONLY);
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_START_ID))) {
			fromInput.setAttribute(AttributeConstants.ATTR_ID,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_START_ID));
			element.removeAttribute(AttributeConstants.ATTR_DATA_START_ID);
		}
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_START_VALUE))) {
			fromInput.setAttribute(AttributeConstants.ATTR_VALUE,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_START_VALUE));
			element.removeAttribute(AttributeConstants.ATTR_DATA_START_VALUE);
		}
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_START_NAME))) {
			fromInput.setAttribute(AttributeConstants.ATTR_NAME,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_START_NAME));
			element.removeAttribute(AttributeConstants.ATTR_DATA_START_NAME);
		}

		final Element toAddOn = new Element(ElementConstants.HTML_SPAN);
		toAddOn.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_ADDON);
		toAddOn.addChild(new Text("-"));

		final Element toInput = new Element(ElementConstants.HTML_INPUT);
		toInput.setAttribute(AttributeConstants.ATTR_CLASS, "form-control");
		toInput.setAttribute(AttributeConstants.ATTR_READONLY, AttributeConstants.ATTR_READONLY);
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_END_ID))) {
			toInput.setAttribute(AttributeConstants.ATTR_ID,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_END_ID));
			element.removeAttribute(AttributeConstants.ATTR_DATA_END_ID);
		}
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_END_VALUE))) {
			toInput.setAttribute(AttributeConstants.ATTR_VALUE,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_END_VALUE));
			element.removeAttribute(AttributeConstants.ATTR_DATA_END_VALUE);
		}
		if (!StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_END_NAME))) {
			toInput.setAttribute(AttributeConstants.ATTR_NAME,
					element.getAttributeValue(AttributeConstants.ATTR_DATA_END_NAME));
			element.removeAttribute(AttributeConstants.ATTR_DATA_END_NAME);
		}

		element.removeAttribute(AttributeConstants.ATTR_ID);

		addOn.insertChild(0, icon);
		addOnDiv.insertChild(0, addOn);
		inpGrp.insertChild(0, addOnDiv);
		inpGrp.insertChild(1, fromInput);
		inpGrp.insertChild(2, toAddOn);
		inpGrp.insertChild(3, toInput);

		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate Datepicker Element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processDatePicker(Element element) {
		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP + " date");

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);
		addOnDiv.setAttribute(AttributeConstants.ATTR_ID, element.getAttributeValue(AttributeConstants.ATTR_ID));

		final Element addOn = new Element(ElementConstants.HTML_SPAN);
		addOn.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_INPUT_GROUP_TEXT + StyleConstants.SPACE + StyleConstants.STYLE_CURSOR_POINTER);

		final Element icon = new Element(ElementConstants.HTML_I);
		icon.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_CALENDAR);
		icon.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		element.removeAttribute(AttributeConstants.ATTR_ID);

		addOn.insertChild(0, icon);
		addOnDiv.insertChild(0, addOn);
		inpGrp.insertChild(0, addOnDiv);
		inpGrp.insertChild(1, element);

		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate Amount Element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processAmount(Element element) {
		if (StringUtils.isEmptyOrWhitespace(element.getAttributeValue(AttributeConstants.ATTR_DATA_CURRENCY))) {
			return element;
		}

		String elemClass = element.getAttributeValue(AttributeConstants.ATTR_CLASS);
		element.setAttribute(AttributeConstants.ATTR_CLASS, elemClass);

		final Element inpGrp = new Element(ElementConstants.HTML_DIV);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP + " amount");
		inpGrp.setAttribute(AttributeConstants.ATTR_ID, element.getAttributeValue(AttributeConstants.ATTR_ID));

		final Element addOnDiv = new Element(ElementConstants.HTML_DIV);
		addOnDiv.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_PREPEND);

		final Element addOn = new Element(ElementConstants.HTML_SPAN);
		addOn.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_TEXT);
		addOn.addChild(new Text(element.getAttributeValue(AttributeConstants.ATTR_DATA_CURRENCY)));

		addOnDiv.insertChild(0, addOn);
		inpGrp.insertChild(0, addOnDiv);
		inpGrp.insertChild(1, element);
		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate Radio Button element
	 *
	 * @param element
	 *             Orginal Element
	 * @return Custom Element
	 */
	public static Element processRadioButton(Element element) {
		final Element inpGrp = new Element(ElementConstants.HTML_LABEL);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, "");
		final Element inpSpan = new Element(ElementConstants.HTML_SPAN);
		inpSpan.setAttribute(AttributeConstants.ATTR_CLASS, "label-text");
		Text value = !BaseUtil.isListNull(element.getChildren()) ? (Text) element.getChildren().get(0) : new Text("");
		inpSpan.addChild(value);
		element.setChildren(null);

		element.addChild(inpSpan);
		inpGrp.insertChild(0, element);

		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate checkbox element
	 *
	 * @param element
	 *             - original component
	 * @return element
	 */
	public static Element processCheckboxButton(Element element) {
		String lblGrpClass = element.getAttributeValue(AttributeConstants.ATTR_DATA_CLASSAPPEND);

		final Element inpGrp = new Element(ElementConstants.HTML_LABEL);
		inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_CHECKBOX_INLINE);

		// Custom class design
		if (!BaseUtil.isObjNull(lblGrpClass)) {
			inpGrp.setAttribute(AttributeConstants.ATTR_CLASS, lblGrpClass);
		}

		final Element inpSpan = new Element(ElementConstants.HTML_SPAN);
		inpSpan.setAttribute(AttributeConstants.ATTR_CLASS, "label-text");
		Text value = !BaseUtil.isListNull(element.getChildren()) ? (Text) element.getChildren().get(0) : new Text("");
		inpSpan.addChild(value);
		element.setChildren(null);

		element.addChild(inpSpan);
		inpGrp.insertChild(0, element);
		inpGrp.setRecomputeProcessorsImmediately(true);
		return inpGrp;
	}


	/**
	 * Generate File button element
	 *
	 * @param element
	 *             Original Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processFileButton(Element element, MessageService messageService) {
		Element file = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		file.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, StyleConstants.STYLE_FILEUPLOAD);
		String attrClass = file.getAttributeValue(AttributeConstants.ATTR_CLASS);

		final Element spanfile = new Element(ElementConstants.HTML_SPAN);
		spanfile.setAttribute(AttributeConstants.ATTR_CLASS, "btn btn-white btn-common btn-file");

		final Element spanfileNew = new Element(ElementConstants.HTML_SPAN);
		spanfileNew.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_NEW);
		spanfileNew.addChild(new Text(messageService.getMessage(MessageDialectConstants.SLCT_FILE)));

		final Element spanfileExsts = new Element(ElementConstants.HTML_SPAN);
		spanfileExsts.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		spanfileExsts.addChild(new Text(messageService.getMessage(MessageDialectConstants.CHNG_FILE)));

		final Element fileInput = new Element(ElementConstants.HTML_INPUT);
		fileInput.setAttribute(AttributeConstants.ATTR_TYPE, "file");
		fileInput.setAttribute(AttributeConstants.ATTR_NAME, "...");
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD))) {
			fileInput.setAttribute(AttributeConstants.ATTR_TH_FIELD,
					file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD));
			file.removeAttribute(AttributeConstants.ATTR_TH_FIELD);
		}
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(StyleConstants.STYLE_ACCEPT))) {
			fileInput.setAttribute(StyleConstants.STYLE_ACCEPT, file.getAttributeValue(StyleConstants.STYLE_ACCEPT));
			file.removeAttribute(StyleConstants.STYLE_ACCEPT);
		}

		spanfile.addChild(spanfileNew);
		spanfile.addChild(spanfileExsts);
		spanfile.addChild(fileInput);

		final Element spanFileName = new Element(ElementConstants.HTML_SPAN);
		spanFileName.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-filename");
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER))) {
			spanFileName.addChild(new Text(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER)));
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		} else {
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_NEW);
		}
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_ONCLICK))) {
			spanFileName.setAttribute(AttributeConstants.ATTR_ONCLICK,
					file.getAttributeValue(AttributeConstants.ATTR_ONCLICK));
			file.removeAttribute(AttributeConstants.ATTR_ONCLICK);
		}

		final Element aClose = new Element(ElementConstants.HTML_ANCHOR);
		aClose.setAttribute(AttributeConstants.ATTR_CLASS, "close fileupload-exists");
		aClose.setAttribute(AttributeConstants.ATTR_HREF, AMPERSAND);
		aClose.setAttribute(AttributeConstants.ATTR_DATA_DISMISS, StyleConstants.STYLE_FILEUPLOAD);
		aClose.setAttribute(AttributeConstants.ATTR_STYLE, "float: none");
		aClose.addChild(new Text("×"));

		file.clearChildren();
		file.addChild(spanfile);
		file.addChild(spanFileName);
		file.addChild(aClose);
		file.setRecomputeProcessorsImmediately(true);
		return file;
	}


	/**
	 * Generate File View element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processFileView(Element element) {
		Element file = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		List<Node> nodes = file.getChildren();

		final Element fileInput = new Element(ElementConstants.HTML_I);
		fileInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FA_FILE + " file-view");
		fileInput.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		file.clearChildren();
		file.addChild(fileInput);

		for (Node node : nodes) {
			file.addChild(node);
		}

		String attrClassAdd = file.getAttributeValue(AttributeConstants.ATTR_TH_CLASSAPPEND);

		file.removeAttribute(AttributeConstants.ATTR_CLASS);
		file.setAttribute(AttributeConstants.ATTR_CLASS, "cursor-pointer file-view " + attrClassAdd);
		file.setRecomputeProcessorsImmediately(true);
		return file;
	}


	/**
	 * Generate File element
	 *
	 * @param element
	 *             Original Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processFile(Element element, MessageService messageService) {
		Element file = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		String attrClass = file.getAttributeValue(AttributeConstants.ATTR_CLASS);
		file.clearChildren();

		String fieldAttr = file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD);
		file.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, StyleConstants.STYLE_FILEUPLOAD);

		final Element fileInput = new Element(ElementConstants.HTML_DIV);
		fileInput.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_FORM_CONTROL + " fileupload-ellipsis");
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_ONCLICK))) {
			fileInput.setAttribute(AttributeConstants.ATTR_ONCLICK,
					file.getAttributeValue(AttributeConstants.ATTR_ONCLICK));
			file.removeAttribute(AttributeConstants.ATTR_ONCLICK);
		}

		final Element i = new Element(ElementConstants.HTML_I);
		i.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_FA_FILE + StyleConstants.SPACE + StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		i.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");

		final Element span = new Element(ElementConstants.HTML_SPAN);
		span.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_FILENAME);
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER))) {
			span.addChild(new Text(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER)));
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		} else {
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_NEW);
		}

		fileInput.addChild(i);
		fileInput.addChild(span);

		final Element addOnDivFile = new Element(ElementConstants.HTML_DIV);
		addOnDivFile.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_INPUT_GROUP_APPEND);

		final Element fileBtn = new Element(ElementConstants.HTML_SPAN);
		fileBtn.setAttribute(AttributeConstants.ATTR_CLASS,
				StyleConstants.STYLE_FORM_BTN_SECONDARY + " btn-common btn-file");

		final Element newInput = new Element(ElementConstants.HTML_SPAN);
		newInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_NEW);
		newInput.addChild(new Text(messageService.getMessage(MessageDialectConstants.SLCT_FILE)));

		final Element exstsInput = new Element(ElementConstants.HTML_SPAN);
		exstsInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		exstsInput.addChild(new Text(messageService.getMessage(MessageDialectConstants.CHNG_FILE)));

		final Element hiddenInput = new Element(ElementConstants.HTML_INPUT);
		hiddenInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		hiddenInput.setAttribute(AttributeConstants.ATTR_NAME, "...");

		String newField = fieldAttr != null ? fieldAttr.substring(2, fieldAttr.length() - 1) : "";

		final Element contentInput = new Element(ElementConstants.HTML_INPUT);
		contentInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		contentInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-content");
		contentInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".content}");

		final Element fnameInput = new Element(ElementConstants.HTML_INPUT);
		fnameInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		fnameInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload");
		fnameInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".filename}");

		final Element sizeInput = new Element(ElementConstants.HTML_INPUT);
		sizeInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		sizeInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-size");
		sizeInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".size}");

		final Element ctypeInput = new Element(ElementConstants.HTML_INPUT);
		ctypeInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		ctypeInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-type");
		ctypeInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".contentType}");

		final Element oldInput = new Element(ElementConstants.HTML_INPUT);
		oldInput.setAttribute(AttributeConstants.ATTR_TYPE, "file");
		oldInput.setAttribute(AttributeConstants.ATTR_NAME, file.getAttributeValue(AttributeConstants.ATTR_NAME));
		oldInput.setAttribute(AttributeConstants.ATTR_ID, file.getAttributeValue(AttributeConstants.ATTR_NAME));
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD))) {
			oldInput.setAttribute(AttributeConstants.ATTR_TH_FIELD,
					file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD));
			file.removeAttribute(AttributeConstants.ATTR_TH_FIELD);
		}
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(StyleConstants.STYLE_ACCEPT))) {
			oldInput.setAttribute(StyleConstants.STYLE_ACCEPT, file.getAttributeValue(StyleConstants.STYLE_ACCEPT));
			file.removeAttribute(StyleConstants.STYLE_ACCEPT);
		}

		fileBtn.addChild(newInput);
		fileBtn.addChild(oldInput);
		fileBtn.addChild(exstsInput);
		fileBtn.addChild(hiddenInput);
		fileBtn.addChild(fnameInput);
		fileBtn.addChild(sizeInput);
		fileBtn.addChild(ctypeInput);
		fileBtn.addChild(contentInput);
		addOnDivFile.addChild(fileBtn);

		final Element aBtn = new Element(ElementConstants.HTML_ANCHOR);
		aBtn.setAttribute(AttributeConstants.ATTR_HREF, AMPERSAND);
		aBtn.setAttribute(AttributeConstants.ATTR_CLASS, "btn btn-secondary btn-common fileupload-exists");
		aBtn.setAttribute(AttributeConstants.ATTR_DATA_DISMISS, StyleConstants.STYLE_FILEUPLOAD);
		aBtn.addChild(new Text(messageService.getMessage(MessageDialectConstants.RMV_FILE)));
		addOnDivFile.addChild(aBtn);

		file.addChild(fileInput);
		file.addChild(addOnDivFile);

		file.removeAttribute(AttributeConstants.ATTR_NAME);
		file.setRecomputeProcessorsImmediately(true);
		return file;
	}


	/**
	 * Generate File Thumbnail element
	 *
	 * @param element
	 *             Original Element
	 * @param messageService
	 *             Message Service for Locale
	 * @return Custom Element
	 */
	public static Element processFileThumbnail(Element element, MessageService messageService) {
		Element file = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		String attrClass = file.getAttributeValue(AttributeConstants.ATTR_CLASS);
		file.clearChildren();

		String fieldAttr = file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD);
		file.setAttribute(AttributeConstants.ATTR_DATA_PROVIDES, StyleConstants.STYLE_FILEUPLOAD);

		String imgAlign = file.getAttributeValue(AttributeConstants.ATTR_DATA_IMG_ALIGN);
		if (imgAlign != null) {
			attrClass = attrClass + " text-" + imgAlign;
		}

		String imgWidth = file.getAttributeValue(AttributeConstants.ATTR_DATA_IMG_WIDTH);

		// THUMBNAIL
		final Element div = new Element(ElementConstants.HTML_DIV);
		String prevClass = "fileupload-preview thumbnail ";
		div.setAttribute(AttributeConstants.ATTR_CLASS, imgWidth != null ? prevClass + imgWidth : prevClass);

		String onclickAttr = file.getAttributeValue(AttributeConstants.ATTR_ONCLICK);
		String thclickAttr = file.getAttributeValue(AttributeConstants.ATTR_TH_ONCLICK);

		if (!BaseUtil.isObjNull(onclickAttr) || !BaseUtil.isObjNull(thclickAttr)) {
			div.setAttribute(AttributeConstants.ATTR_CLASS, div.getAttributeValue(AttributeConstants.ATTR_CLASS)
					+ StyleConstants.SPACE + StyleConstants.STYLE_CURSOR_POINTER);
			if (!BaseUtil.isObjNull(thclickAttr)) {
				div.setAttribute(AttributeConstants.ATTR_TH_ONCLICK, thclickAttr);
				file.removeAttribute(AttributeConstants.ATTR_TH_ONCLICK);
			}
			if (!BaseUtil.isObjNull(onclickAttr)) {
				div.setAttribute(AttributeConstants.ATTR_ONCLICK, onclickAttr);
				file.removeAttribute(AttributeConstants.ATTR_ONCLICK);
			}
		}

		final Element img = new Element(ElementConstants.HTML_IMG);
		img.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-img-thumbnail-view img-thumbnail");
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER))) {
			String docMgtId = file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER);
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_EXISTS);
			img.setAttribute(AttributeConstants.ATTR_TH_SRC, "@{/documents/download/content/" + docMgtId + "}");
		} else {
			file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass + StyleConstants.STYLE_FILEUPLOAD_NEW);
			img.setAttribute(AttributeConstants.ATTR_TH_SRC, NO_IMAGE_SRC);
		}

		div.addChild(img);

		final Element divBtn = new Element(ElementConstants.HTML_DIV);

		final Element fileBtn = new Element(ElementConstants.HTML_SPAN);
		fileBtn.setAttribute(AttributeConstants.ATTR_CLASS, "btn btn-white btn-common btn-file");

		final Element newInput = new Element(ElementConstants.HTML_SPAN);
		newInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_NEW);
		newInput.addChild(new Text(messageService.getMessage(MessageDialectConstants.SLCT_FILE)));

		final Element exstsInput = new Element(ElementConstants.HTML_SPAN);
		exstsInput.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_FILEUPLOAD_EXISTS);
		exstsInput.addChild(new Text(messageService.getMessage(MessageDialectConstants.CHNG_FILE)));

		final Element hiddenInput = new Element(ElementConstants.HTML_INPUT);
		hiddenInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		hiddenInput.setAttribute(AttributeConstants.ATTR_NAME, "...");

		String newField = !BaseUtil.isObjNull(fieldAttr) ? fieldAttr.substring(2, fieldAttr.length() - 1) : "";

		final Element contentInput = new Element(ElementConstants.HTML_INPUT);
		contentInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		contentInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-content");
		contentInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".content}");

		final Element fnameInput = new Element(ElementConstants.HTML_INPUT);
		fnameInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		fnameInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload");
		fnameInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".filename}");

		final Element sizeInput = new Element(ElementConstants.HTML_INPUT);
		sizeInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		sizeInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-size");
		sizeInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".size}");

		final Element ctypeInput = new Element(ElementConstants.HTML_INPUT);
		ctypeInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_HIDDEN);
		ctypeInput.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-upload-type");
		ctypeInput.setAttribute(AttributeConstants.ATTR_TH_FIELD, "*{" + newField + ".contentType}");

		final Element oldInput = new Element(ElementConstants.HTML_INPUT);
		oldInput.setAttribute(AttributeConstants.ATTR_TYPE, AttributeConstants.ATTR_TYPE_FILE);
		oldInput.setAttribute(AttributeConstants.ATTR_NAME, file.getAttributeValue(AttributeConstants.ATTR_NAME));
		oldInput.setAttribute(AttributeConstants.ATTR_ID, file.getAttributeValue(AttributeConstants.ATTR_NAME));
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD))) {
			oldInput.setAttribute(AttributeConstants.ATTR_TH_FIELD,
					file.getAttributeValue(AttributeConstants.ATTR_TH_FIELD));
			file.removeAttribute(AttributeConstants.ATTR_TH_FIELD);
		}
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(StyleConstants.STYLE_ACCEPT))) {
			oldInput.setAttribute(StyleConstants.STYLE_ACCEPT, file.getAttributeValue(StyleConstants.STYLE_ACCEPT));
			file.removeAttribute(StyleConstants.STYLE_ACCEPT);
		}

		fileBtn.addChild(newInput);
		fileBtn.addChild(oldInput);
		fileBtn.addChild(exstsInput);
		fileBtn.addChild(hiddenInput);
		fileBtn.addChild(fnameInput);
		fileBtn.addChild(sizeInput);
		fileBtn.addChild(ctypeInput);
		fileBtn.addChild(contentInput);

		final Element aBtn = new Element(ElementConstants.HTML_ANCHOR);
		aBtn.setAttribute(AttributeConstants.ATTR_HREF, AMPERSAND);
		aBtn.setAttribute(AttributeConstants.ATTR_CLASS, "btn btn-white btn-common fileupload-exists");
		aBtn.setAttribute(AttributeConstants.ATTR_DATA_DISMISS, StyleConstants.STYLE_FILEUPLOAD);
		aBtn.addChild(new Text(messageService.getMessage(MessageDialectConstants.RMV_FILE)));

		divBtn.addChild(fileBtn);
		divBtn.addChild(aBtn);

		file.addChild(div);
		file.addChild(divBtn);
		file.removeAttribute(AttributeConstants.ATTR_NAME);
		file.setRecomputeProcessorsImmediately(true);
		return file;
	}


	/**
	 * Generate File Thumbnail View element
	 *
	 * @param element
	 *             Original element
	 * @return Custom Element
	 */
	public static Element processFileThumbnailView(Element element) {
		Element file = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		String attrClass = file.getAttributeValue(AttributeConstants.ATTR_CLASS);
		List<Node> nodes = file.getChildren();

		attrClass = attrClass + " cursor-pointer file-view ";
		String imgAlign = file.getAttributeValue(AttributeConstants.ATTR_DATA_IMG_ALIGN);
		if (imgAlign != null) {
			attrClass = attrClass + " text-" + imgAlign;
		}

		String imgWidth = file.getAttributeValue(AttributeConstants.ATTR_DATA_IMG_WIDTH);

		final Element div = new Element(ElementConstants.HTML_DIV);
		String prevClass = "fileupload-preview thumbnail ";
		div.setAttribute(AttributeConstants.ATTR_CLASS, imgWidth != null ? prevClass + imgWidth : prevClass);

		final Element img = new Element(ElementConstants.HTML_IMG);
		img.setAttribute(AttributeConstants.ATTR_CLASS, "fileupload-img-thumbnail-view img-thumbnail");
		if (!StringUtils.isEmptyOrWhitespace(file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER))) {
			String docMgtId = file.getAttributeValue(AttributeConstants.ATTR_PLACEHOLDER);
			img.setAttribute(AttributeConstants.ATTR_TH_SRC, "@{/documents/download/content/" + docMgtId + "}");
		} else {
			img.setAttribute(AttributeConstants.ATTR_TH_SRC, NO_IMAGE_SRC);
		}

		div.addChild(img);
		file.addChild(div);

		for (Node node : nodes) {
			file.addChild(node);
		}

		file.removeAttribute(AttributeConstants.ATTR_CLASS);
		file.setAttribute(AttributeConstants.ATTR_CLASS, attrClass);
		file.setRecomputeProcessorsImmediately(true);
		return file;
	}


	/**
	 * Generate widge element
	 *
	 * @param element
	 *             Orginal Element
	 * @return Custom Element
	 */
	public static Element processWidget(Element element) {
		Element inpGrp = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_SECTION,
				false);
		inpGrp.setRecomputeProcessorsImmediately(true);
		if (element.hasAttribute(AttributeConstants.ATTR_DATA_GRID)) {
			Integer size = Integer.valueOf(element.getAttributeValue(AttributeConstants.ATTR_DATA_GRID));
			if (size > 0 && size <= 12) {
				final Element colDiv = new Element(ElementConstants.HTML_DIV);
				colDiv.setAttribute(AttributeConstants.ATTR_CLASS,
						"col-lg-" + size + " col-md-" + size + " col-sm-12 col-xs-12 p-widget ");
				colDiv.addChild(inpGrp);
				return colDiv;
			}
		}
		return inpGrp;
	}


	/**
	 * Generate widget header element
	 *
	 * @param element
	 *             Original Element
	 * @return Custom Element
	 */
	public static Element processWidgetHeader(Element element) {
		Element widget = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_DIV, false);
		widget.setRecomputeProcessorsImmediately(true);
		return widget;
	}

}
