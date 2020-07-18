package com.wfw.web.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.wfw.constant.BeanQualifier;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.SearchForm;
import com.wfw.model.WfwInboxMstr;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_TASK_LIST)
public class WfTaskListController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfTaskListController.class);

	@Autowired
	@Qualifier(BeanQualifier.SEARCH_FORM_VALIDATOR)
	private Validator validator;


	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}


	@GetMapping
	public ModelAndView view(SearchForm searchForm, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, null);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}


	@PostMapping
	public ModelAndView search(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {

		ModelAndView mav = new ModelAndView();
		log.info("officer id={}", searchForm.getOfficerId());
		log.info("aplication id={}", searchForm.getApplicationId());

		WfwInboxMstr twfWfInboxMstr = new WfwInboxMstr();
		twfWfInboxMstr.setRefNo(BaseUtil.getStr(searchForm.getApplicationId()));
		twfWfInboxMstr.setOfficerId(BaseUtil.getStr(searchForm.getOfficerId()));
		if (BaseUtil.isObjNull(searchForm.getApplicationId()) && BaseUtil.isObjNull(searchForm.getOfficerId())) {
			mav.addObject("error", "Officer ID and Application ID both cannot be blank.");
		} else {
			List<WfwInboxMstr> inboxMstrList = getCommonService().searchWfInboxMstr(twfWfInboxMstr);
			mav.addObject(PageConstants.DATA_LIST_OBJ, inboxMstrList);
			if (BaseUtil.isListNull(inboxMstrList)) {
				mav.addObject("msg", "No task found.");
			} else {
				mav.addObject("msg", inboxMstrList.size() + " task(s) found.");
			}
		}
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}


	@PostMapping(params = "newtask")
	public String newTask() {
		return PageConstants.PAGE_REDIRECT + PageConstants.PAGE_URL_TASK_CREATE + "?taskId=";
	}


	@GetMapping(params = "reset")
	public ModelAndView reset() {
		log.info("testing .......... reset");
		List<WfwInboxMstr> twfWfInboxMstr = getCommonService().findAllWfInboxMstr();

		log.info("inboxList size = {}", twfWfInboxMstr.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, twfWfInboxMstr);
		mav.setViewName(PageConstants.PAGE_CONST_TASK_LIST);
		return mav;
	}

}