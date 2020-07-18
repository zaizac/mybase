/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.be.sdk.constants.IdmRoleConstants;
import com.idm.sdk.model.IdmConfigDto;
import com.portal.constants.PageConstants;
import com.portal.constants.PageTemplate;
import com.portal.web.util.helper.ThemeHelper;
import com.util.MediaType;
import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
@Controller
public class StaticContentController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticContentController.class);

	private static final String COMPONENTS = "components";
	
	private static final String SCRIPT_HTML = "dashboard-script";


	@Autowired
	ThemeHelper themeHelper;


	@GetMapping(value = PageConstants.PAGE_SRC)
	public ModelAndView home(@RequestParam(value = "portal", required = false) String portal, HttpSession session) {
		ModelAndView mav = new ModelAndView(PageTemplate.TEMP_DASHBOARD);
		if (portal == null) {
			portal = messageService.getMessage(BaseConfigConstants.PORTAL_TYPE);
		}

		if (isUserAuthenticated()) {
			return new ModelAndView("redirect:" + PageConstants.PAGE_HOME);
		}
		
		return new ModelAndView("redirect:" + PageConstants.PAGE_HOME);
	}
	
	@PreAuthorize("hasAuthority('" + IdmRoleConstants.SYSTEM_ADMIN + "')")
	@PostMapping(value = "/updateTheme", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody boolean components(@RequestBody List<IdmConfigDto> idmConfigDtoLst) {
		boolean isUpdate = false;
		try {
			themeHelper.processObject(idmConfigDtoLst);
			staticData.addIdmConfig(idmConfigDtoLst);
			isUpdate = true;
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
		return isUpdate;
	}


	@GetMapping(value = "/favicon.ico")
	public String favicon() {
		return "forward:/images/favicon.ico";
	}
	
	@GetMapping(value = PageConstants.PAGE_HOME)
	public ModelAndView landingPage() {
		return new ModelAndView(PageTemplate.TEMP_HOME);
	}
	
	@GetMapping(value = PageConstants.PAGE_DASHBOARD)
	public ModelAndView dashboard() {
		return  getDefaultMav(PageTemplate.TEMP_DASHBOARD, PageTemplate.TEMP_DASHBOARD, PageTemplate.TEMP_DASHBOARD, SCRIPT_HTML);
	}
	
	@GetMapping(value = PageConstants.PAGE_POLICY)
	public ModelAndView policyPage() {
		ModelAndView mav = new ModelAndView(PageTemplate.TEMP_POLICY);
		mav.addObject("portalModule", "policy");
		return mav;
	}

	@GetMapping(value = PageConstants.PAGE_CONTACT)
	public ModelAndView contactPage() {
		ModelAndView mav = new ModelAndView(PageTemplate.CONTACT);
		mav.addObject("portalModule", "contact");
		return mav;
	}
	
	@GetMapping(value = PageConstants.PAGE_DOWNLOAD)
	public ModelAndView faqPage() {
		ModelAndView mav = new ModelAndView(PageTemplate.FAQ);
		mav.addObject("portalModule", "faq");
		return mav;
	}
	
}