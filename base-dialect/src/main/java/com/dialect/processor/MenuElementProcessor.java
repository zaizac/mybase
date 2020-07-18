/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.dialect.processor;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.dialect.AbstractBstElement;
import com.dialect.MessageService;
import com.dialect.constants.AttributeConstants;
import com.dialect.constants.ElementConstants;
import com.dialect.constants.ElementEnum;
import com.dialect.constants.StyleConstants;
import com.dialect.iam.CustomUserDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.idm.sdk.client.IdmServiceClient;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserMenu;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.UidGenerator;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;
import com.util.model.LangDesc;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class MenuElementProcessor extends AbstractBstElement {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuElementProcessor.class);


	/**
	 * Menu Element Processor
	 */
	public MenuElementProcessor() {
		super("menu");
	}


	/**
	 * Menu element Processor
	 *
	 * @param elementName
	 *             Element Name
	 */
	public MenuElementProcessor(String elementName) {
		super(elementName);
	}


	private IdmServiceClient getIdmService(Arguments arguments) {
		final ApplicationContext appCtx = ((SpringWebContext) arguments.getContext()).getApplicationContext();
		IdmServiceClient idmService = appCtx.getBean(IdmServiceClient.class);
		MessageService messageService = appCtx.getBean(MessageService.class);
		if (!BaseUtil.isObjNull(messageService) && !BaseUtil.isObjNull(idmService)) {
			String authToken = getAuthToken();
			if (BaseUtil.isObjNull(idmService)) {
				idmService = new IdmServiceClient(messageService.getMessage(BaseConfigConstants.SVC_IDM_URL));
			}
			if (authToken != null) {
				idmService.setToken(authToken);
			} else {
				idmService.setToken(messageService.getMessage(BaseConfigConstants.SVC_IDM_SKEY));
			}
			idmService.setClientId(messageService.getMessage(BaseConfigConstants.SVC_IDM_CLIENT));
			idmService.setMessageId(UidGenerator.getMessageId());
		}
		return idmService;
	}


	private static boolean checkException(String intErrCode, String intErrMsg) {
		if (BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM113.name(), intErrCode)
				|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.I404IDM115.name(), intErrCode)) {
			LOGGER.debug("checkTokenError: IdmException: {} - {} ", intErrCode, intErrMsg);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!BaseUtil.isObjNull(auth) && auth.isAuthenticated()) {
				CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
				cud.setForceLogout(true);
				cud.setErrorCode(intErrCode);
			}
			return true;
		}
		return false;
	}


	@Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
		String elementNm = getElementName(element);
		Element newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_UL,
				false);
		if (elementNm.equalsIgnoreCase(ElementEnum.MENU.getName())) {
			processMenuUL(newElement, arguments, elementNm);
			element.getParent().insertAfter(element, newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.TAB_H_NAV.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_V_NAV.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_PILL_NAV.getName())) {
			newElement.setRecomputeProcessorsImmediately(true);
			String attrClass = element.getAttributeValue(AttributeConstants.ATTR_CLASS);
			attrClass = ElementEnum.findStyleByName(elementNm)
					+ (!BaseUtil.isObjNull(attrClass) ? StyleConstants.SPACE + attrClass
							: BaseConstants.EMPTY_STRING);
			newElement.setAttribute(AttributeConstants.ATTR_CLASS, attrClass);
			newElement.setAttribute(AttributeConstants.ATTR_ROLE, "tablist");
			element.getParent().insertAfter(element, newElement);
		} else if (elementNm.equalsIgnoreCase(ElementEnum.TAB_H_NAV_LI.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_V_NAV_LI.getName())
				|| elementNm.equalsIgnoreCase(ElementEnum.TAB_PILL_NAV_LI.getName())) {
			newElement = element.cloneElementNodeWithNewName(element.getParent(), ElementConstants.HTML_ANCHOR,
					false);
			newElement.setRecomputeProcessorsImmediately(true);
			String attrClass = element.getAttributeValue(AttributeConstants.ATTR_CLASS);
			attrClass = ElementEnum.findStyleByName(elementNm)
					+ (!BaseUtil.isObjNull(attrClass) ? " " + attrClass : "");
			newElement.setAttribute(AttributeConstants.ATTR_CLASS, attrClass);
			newElement.setAttribute(AttributeConstants.ATTR_DATA_TOGGLE, "tab");
			newElement.setAttribute(AttributeConstants.ATTR_ROLE, "tab");

			final Element liItem = new Element(ElementConstants.HTML_LI);
			liItem.setAttribute(AttributeConstants.ATTR_CLASS, StyleConstants.STYLE_NAV_ITEM);
			liItem.insertChild(0, newElement);

			element.getParent().insertAfter(element, liItem);
		}
		element.getParent().removeChild(element);
		return ProcessorResult.OK;
	}


	@Override
	public int getPrecedence() {
		return 0;
	}


	private void processMenuUL(Element newElement, Arguments arguments, String elementNm) {
		String lang = getLocale(arguments, newElement);
		newElement.setRecomputeProcessorsImmediately(true);
		newElement.setAttribute(AttributeConstants.ATTR_ID, "sidebarnav");
		newElement.setAttribute(AttributeConstants.ATTR_CLASS, ElementEnum.findStyleByName(elementNm));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			if (!BaseUtil.isEqualsCaseIgnore(cud.getProfile().getStatus(), BaseConstants.USER_FIRST_TIME)) {
				UserProfile up = cud.getProfile();
				if (!BaseUtil.isObjNull(up) && !BaseUtil.isListNull(up.getMenus())
						&& !BaseUtil.isListZero(up.getMenus())) {
					List<UserMenu> menuList = getMenuList(up, arguments);
					processMenu(newElement, menuList, 1, lang);
				}
			}
		}
	}


	private List<UserMenu> getMenuList(UserProfile up, Arguments arguments) {
		List<UserMenu> menuList = null;
		try {
			IdmServiceClient idmService = getIdmService(arguments);
			menuList = idmService.findAllMenusByUserId(up.getUserId());
		} catch (IdmException e) {
			if (checkException(e.getInternalErrorCode(), e.getMessage())
					|| BaseUtil.isEqualsCaseIgnore(IdmErrorCodeEnum.E503IDM000.name(), e.getInternalErrorCode())) {
				throw new TemplateProcessingException(e.getInternalErrorCode());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			menuList = up.getMenus();
		}
		return menuList;
	}


	private Element subMenuElement(UserMenu menuNode) {
		Element li = new Element(ElementConstants.HTML_UL);
		li.setAttribute(AttributeConstants.ATTR_CLASS, "collapse");
		li.setAttribute(AttributeConstants.ATTR_ARIA_EXPANDED, "false");
		LOGGER.debug("Menu: {} - {}", menuNode.getMenuCode(), menuNode.getMenuDesc());
		return li;
	}


	private Element labelMenuElement(UserMenu menuNode, String lang) {
		Element a = new Element(ElementConstants.HTML_ANCHOR);

		// Menu Icon
		if (!BaseUtil.isObjNull(menuNode.getMenuIcon())) {
			Element i = new Element(ElementConstants.HTML_I);
			i.setAttribute(AttributeConstants.ATTR_CLASS, menuNode.getMenuIcon());
			i.setAttribute(AttributeConstants.ATTR_ARIA_HIDDEN, "true");
			a.addChild(i);
		}

		int chldCnt = !BaseUtil.isObjNull(menuNode.getMenuChild()) ? menuNode.getMenuChild().size() : 0;

		if (chldCnt > 0) {
			a.setAttribute(AttributeConstants.ATTR_CLASS, "has-arrow");
			a.setAttribute(AttributeConstants.ATTR_ARIA_EXPANDED, "false");
			Element spanArrow = new Element(ElementConstants.HTML_SPAN);
			spanArrow.setAttribute(AttributeConstants.ATTR_CLASS, "arrow");
			a.addChild(spanArrow);
		}

		if (!BaseUtil.isObjNull(menuNode.getMenuUrlCd())) {
			String url = "@{" + menuNode.getMenuUrlCd() + "}";
			a.setAttribute(AttributeConstants.ATTR_TH_HREF, url);
		} else {
			a.setAttribute(AttributeConstants.ATTR_HREF, "#");
		}

		// Menu Label
		Element spanLbl = new Element(ElementConstants.HTML_SPAN);
		spanLbl.setAttribute(AttributeConstants.ATTR_CLASS, "hide-menu");
		spanLbl.addChild(new Text(getDesc(lang, menuNode.getMenuDesc())));
		a.addChild(spanLbl);

		return a;
	}


	private String getDesc(String lang, LangDesc langDesc) {
		String desc = "";

		if (!BaseUtil.isObjNull(lang) && !BaseUtil.isObjNull(langDesc)) {
			try {
				JsonNode jn = JsonUtil.toJsonNode(langDesc);
				desc = jn.get(lang).textValue();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		return desc;
	}


	private String getLocale(Arguments arguments, Element element) {
		final Configuration configuration = arguments.getConfiguration();

		/*
		 * Obtain the attribute value
		 */
		final String attributeValue = element.getAttributeValue("data-lang");

		/*
		 * Obtain the Thymeleaf Standard Expression parser
		 */
		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		/*
		 * Parse the attribute value as a Thymeleaf Standard Expression
		 */
		final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);

		/*
		 * Execute the expression just parsed
		 */
		final String lang = String.valueOf(expression.execute(configuration, arguments));
		LOGGER.debug("Menu Locale: {}", lang);
		element.removeAttribute("data-lang");
		return lang;
	}


	private Element processMenu(Element element, List<UserMenu> menuLst, int level, String lang) {
		if (!BaseUtil.isListNull(menuLst) && !BaseUtil.isListZero(menuLst)) {
			LOGGER.debug("Menu Locale: {}", menuLst);
			for (UserMenu lvlOne : menuLst) {
				if (lvlOne.getMenuLevel() == level) {
					int totChild = !BaseUtil.isObjNull(lvlOne.getMenuChild()) ? lvlOne.getMenuChild().size() : 0;
					List<UserMenu> lvlOneChild = lvlOne.getMenuChild();
					final Element liOne = new Element(ElementConstants.HTML_LI);
					liOne.addChild(labelMenuElement(lvlOne, lang));
					if (totChild > 0) {
						final Element lvlOneElement = subMenuElement(lvlOne);
						processMenu(lvlOneElement, lvlOneChild, lvlOne.getMenuLevel() + 1, lang);
						liOne.addChild(lvlOneElement);
					}
					element.addChild(liOne);
				}
			}
		}
		return element;
	}

}
