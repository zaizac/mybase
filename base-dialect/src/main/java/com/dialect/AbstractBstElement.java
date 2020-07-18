/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.dialect;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.util.StringUtils;

import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementEnum;
import com.dialect.constants.StyleConstants;
import com.dialect.iam.CustomUserDetails;
import com.idm.sdk.model.Token;
import com.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public abstract class AbstractBstElement extends AbstractElementProcessor {

	protected AbstractBstElement(String elementName) {
		super(elementName);
	}


	protected String getElementName(Element element) {
		return StringUtils.substringAfter(element.getOriginalName(), "bst:");
	}


	protected String getAuthToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			Token token = getToken(auth);
			if (token != null) {
				return token.getAccessToken();
			}
		}
		return null;
	}


	private Token getToken(Authentication auth) {
		if (auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			return cud.getAuthToken();
		}
		return null;
	}


	protected String classAttrValue(String elementNm, Element element) {
		String attrClass = ElementEnum.findStyleByName(elementNm);

		if (!BaseUtil.isObjNull(element.getAttributeValue(AttributeConstants.ATTR_CLASS))) {
			attrClass = element.getAttributeValue(AttributeConstants.ATTR_CLASS) + " " + attrClass;
		} else if (!BaseUtil.isObjNull(element.getAttributeValue(AttributeConstants.ATTR_TH_CLASSAPPEND))) {
			attrClass = attrClass + " " + element.getAttributeValue(AttributeConstants.ATTR_TH_CLASSAPPEND);
		} else {
			attrClass = ElementEnum.findStyleByName(elementNm);
		}

		if (StringUtils.equals(elementNm, ElementEnum.INPUT_AMOUNT.getName())) {
			attrClass += StyleConstants.SPACE + "input-amount";
		}

		if (StringUtils.equals(elementNm, ElementEnum.INPUT_NUMBER.getName())) {
			attrClass += StyleConstants.SPACE + "limit-number";
		}

		return attrClass;
	}
}
