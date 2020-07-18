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
import com.wfw.constant.PageConstants;
import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractController;
import com.wfw.form.SearchForm;
import com.wfw.model.WfwInbox;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_INBOX_LIST)
public class WfInboxListController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(WfInboxListController.class);

	@Autowired
	@Qualifier(QualifierConstants.SEARCH_FORM_VALIDATOR)
	private Validator validator;


	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		binder.setValidator(validator);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}


	@GetMapping()
	public ModelAndView view(SearchForm searchForm, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("searchForm", new SearchForm());
		mav.addObject(PageConstants.DATA_LIST_OBJ, null);
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_LIST);
		return mav;
	}


	@PostMapping()
	public ModelAndView search(@ModelAttribute(value = "searchForm") SearchForm searchForm, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		log.info("officer id= {}", searchForm.getOfficerId());
		log.info("aplication id= {}", searchForm.getApplicationId());

		WfwInbox twfWfInbox = new WfwInbox();
		twfWfInbox.setRefNo(BaseUtil.getStr(searchForm.getApplicationId()));
		twfWfInbox.setOfficerId(BaseUtil.getStr(searchForm.getOfficerId()));
		if (BaseUtil.isObjNull(searchForm.getApplicationId()) && BaseUtil.isObjNull(searchForm.getOfficerId())) {
			mav.addObject("error", "Officer ID and Application ID both cannot be blank.");
		} else {
			List<WfwInbox> inboxList = getCommonService().searchWfInbox(twfWfInbox);
			mav.addObject(PageConstants.DATA_LIST_OBJ, inboxList);
			if (BaseUtil.isListNull(inboxList)) {
				mav.addObject("msg", "No result found.");
			} else {
				mav.addObject("msg", inboxList.size() + " result(s) found.");
			}
		}
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_LIST);
		return mav;
	}


	@GetMapping(params = "reset")
	public ModelAndView reset() {
		log.info("testing .......... reset");
		List<WfwInbox> inboxList = getCommonService().findAllWfInbox();

		log.info("inboxList size = {}", inboxList.size());

		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, inboxList);
		mav.setViewName(PageConstants.PAGE_CONST_INBOX_LIST);
		return mav;
	}
}