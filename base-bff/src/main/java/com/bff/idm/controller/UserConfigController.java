package com.bff.idm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bff.core.AbstractController;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.model.IdmConfigDto;
import com.util.MediaType;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = PageConstants.PAGE_IDM_USER_CONFIG)
public class UserConfigController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserConfigController.class);
	
	ObjectMapper objMap = new ObjectMapper();
	
	@GetMapping(value = "/allUserConfig", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public List<IdmConfigDto> findAllUserConfig(){
		return getIdmService().findAllUserConfig();
	}
	
	@PostMapping(value = "/updateUserConfig", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody IdmConfigDto updateUserConfig(@RequestBody IdmConfigDto userCfg, HttpServletRequest request) {
		LOGGER.info("UserConfig: {}", objMap.valueToTree(userCfg));
		return getIdmService().updateUserConfig(userCfg);
	}
	
	@GetMapping(value = "/configCode", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public IdmConfigDto findByConfigCode(@RequestParam(value = "configCode", required = true) String configCode) {
		LOGGER.info("configCode: {}", objMap.valueToTree(configCode));
		return getIdmService().findUsrCfgByConfigCode(configCode);
	}
}
