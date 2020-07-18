package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwType;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_WF_CONFIG)
public class WfConfigController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(WfConfigController.class);


	@GetMapping
	public ModelAndView view(@RequestParam(value = "typeId", required = false) String typeId) {
		ModelAndView mav = new ModelAndView();
		if (BaseUtil.isObjNull(typeId)) {
			List<WfwType> list = getTypeList();
			if (!BaseUtil.isListNull(list)) {
				typeId = list.get(0).getTypeId();
			}
		}

		logger.info("typeId = {}", typeId);
		mav.addObject(PageConstants.DATA_LIST_OBJ, getCommonService().generateTypeList(typeId));
		logger.info("type = {}", getCommonService().generateTypeList(typeId).size());

		mav.setViewName(PageConstants.PAGE_CONST_WF_CONFIG);
		return mav;
	}


	@ModelAttribute("typeList")
	public List<WfwType> getTypeList() {
		return getCommonService().findAllWfType();
	}
}