/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.portal.web.util.helper;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idm.sdk.model.IdmConfigDto;
import com.portal.web.util.StaticData;
import com.util.BaseUtil;
import com.util.constants.BaseConfigConstants;


/**
 * @author mary.jane
 * @since Feb 20, 2019
 */
@Component
public class ThemeHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeHelper.class);

	@Autowired
	StaticData staticData;

	@Value("${" + BaseConfigConstants.PORTAL_UI_LAYOUT + "}")
	private String configLayout;

	@Value("${" + BaseConfigConstants.PORTAL_UI_THEME + "}")
	private String configTheme;

	@Value("${" + BaseConfigConstants.PORTAL_UI_MODE + "}")
	private String configMode;

	@Value("${" + BaseConfigConstants.PORTAL_UI_TEXTURE + "}")
	private String configTexture;

	@Value("${" + BaseConfigConstants.PORTAL_UI_CONTENT + "}")
	private String configContent;

	@Value("${" + BaseConfigConstants.PORTAL_UI_PAGE + "}")
	private String configPage;


	public String baseStyle(String folder, String cssFile) {
		LOGGER.debug("Folder: {} - CSS File: {}", folder, cssFile);
		String uiLayout = "horizontal";
		String uiTheme = "blue";
		String uiMode = "light";
		String uiTexture = "blue";
		String uiContent = "light";
		String uiPage = "default";

		String location = BaseConfigConstants.MONSTER_UI_URL;
		if (BaseUtil.isEqualsCaseIgnore("sidemode", cssFile)) {
			location += folder + uiMode + BaseConfigConstants.CSS_SFX;
		} else {
			location += uiLayout + folder + (BaseUtil.isEqualsCaseIgnore("theme", cssFile) ? uiTheme : cssFile)
					+ BaseConfigConstants.CSS_SFX;
		}
		LOGGER.debug("Style Location: {}", location);
		return location;
	}


	public String baseScript(String folder, String scriptFile) {
		LOGGER.debug("Folder: {} - Script File: {}", folder, scriptFile);
		String uiLayout = staticData.idmConfig(configLayout);

		if (BaseUtil.isObjNull(uiLayout)) {
			uiLayout = "horizontal";
		}

		String location = BaseConfigConstants.MONSTER_UI_URL + uiLayout + folder + scriptFile;
		LOGGER.debug("Script Location: {}", location);
		return location;

	}


	private void checkValue(String uiMode, String uiLayout, String uiTheme, String uiTexture, String uiContent,
			String uiPage) {
		List<IdmConfigDto> idmConfigLst = new ArrayList<>();
		idmConfigLst.add(new IdmConfigDto(configMode, configMode, uiMode));
		idmConfigLst.add(new IdmConfigDto(configLayout, configLayout, uiLayout));
		idmConfigLst.add(new IdmConfigDto(configTheme, configTheme, uiTheme));
		idmConfigLst.add(new IdmConfigDto(configTexture, configTexture, uiTexture));
		idmConfigLst.add(new IdmConfigDto(configContent, configContent, uiContent));
		idmConfigLst.add(new IdmConfigDto(configPage, configPage, uiPage));
		staticData.addIdmConfig(idmConfigLst);
	}


	public void processObject(List<IdmConfigDto> confLst) {
		for (IdmConfigDto conf : confLst) {
			if (BaseUtil.isEqualsCaseIgnore("theme", conf.getConfigCode())) {
				conf.setConfigCode(configTheme);
				conf.setConfigDesc(configTheme);
			} else if (BaseUtil.isEqualsCaseIgnore("layout", conf.getConfigCode())) {
				conf.setConfigCode(configLayout);
				conf.setConfigDesc(configLayout);
			} else if (BaseUtil.isEqualsCaseIgnore("mode", conf.getConfigCode())) {
				conf.setConfigCode(configMode);
				conf.setConfigDesc(configMode);
			}
		}
	}

}