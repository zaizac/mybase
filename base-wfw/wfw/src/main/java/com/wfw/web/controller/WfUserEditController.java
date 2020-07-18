package com.wfw.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.util.BaseUtil;
import com.util.DateUtil;
import com.wfw.constant.PageConstants;
import com.wfw.core.AbstractController;
import com.wfw.model.WfwUser;
import com.wfw.sdk.model.User;
import com.wfw.util.BeanUtil;
import com.wfw.util.PopupBox;
import com.wfw.util.SessionData;


@Controller
@RequestMapping(value = PageConstants.PAGE_URL_USER_EDIT)
public class WfUserEditController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(WfUserEditController.class);


	@GetMapping()
	public ModelAndView view(@RequestParam("userId") String userId, HttpServletRequest request) {
		logger.info("userId = {}", userId);
		WfwUser userList = getCommonService().findUserByUserId(Integer.parseInt(userId));
		logger.info("userName = {}", userList.getName());
		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, userList);
		mav.addObject("showSave", false);
		mav.addObject("showUpdate", true);
		mav.setViewName(PageConstants.PAGE_CONST_USER_EDIT);
		return mav;
	}


	@PostMapping(params = "create")
	public ModelAndView create(User user) {
		logger.info("CREATE NEW USER");

		ModelAndView mav = new ModelAndView();
		user.setSex("M");
		user.setIsActive("1");
		mav.addObject(PageConstants.DATA_LIST_OBJ, user);
		mav.addObject("showSave", true);
		mav.addObject("showUpdate", false);
		mav.setViewName(PageConstants.PAGE_CONST_USER_EDIT);
		return mav;
	}


	@PostMapping(params = "save")
	public ModelAndView createUser(User user, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		user.setCreateId(SessionData.getCurrentUserId());
		user.setCreateDt(DateUtil.getSQLTimestamp());
		user.setUpdateDt(DateUtil.getSQLTimestamp());
		user.setUpdateId(BaseUtil.getStr(SessionData.getCurrentUserId()));
		if (BaseUtil.isObjNull(user.getName()) || BaseUtil.isObjNull(user.getUserName())
				|| BaseUtil.isObjNull(user.getUserRole()) || BaseUtil.isObjNull(user.getPassword())) {
			mav.addObject(PageConstants.DATA_LIST_OBJ, user);
			mav.setViewName(PageConstants.PAGE_CONST_USER_EDIT);
			mav.addObject("showSave", true);
			mav.addObject("showUpdate", false);
			if (BaseUtil.isObjNull(user.getName())) {
				mav.addObject("error", "Name cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getUserName())) {
				mav.addObject("error", "User Name cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getUserRole())) {
				mav.addObject("error", "User Role cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getPassword())) {
				mav.addObject("error", "Password cannot be blank.");
			}
		} else {
			WfwUser wfwUser = new WfwUser();
			BeanUtil.copyProperties(user, wfwUser);
			boolean isCreated = getCommonService().addWfUser(wfwUser);
			logger.info("isCreated = {}", isCreated);

			List<WfwUser> userList = getCommonService().findAllWfUser();

			mav.addObject(PageConstants.DATA_LIST_OBJ, userList);
			mav.setViewName(PageConstants.PAGE_CONST_USER_LIST);
			if (isCreated) {
				mav.addAllObjects(PopupBox.success("User", "User", " User has successfully saved."));
			} else {
				mav.addAllObjects(PopupBox.error("Error!", "User",
						"User has not saved, please try again or contact with admin."));
			}
		}

		return mav;

	}


	@PostMapping(params = "update")
	public ModelAndView updateUser(User user, BindingResult result) {
		logger.info("User Id = {}", user.getWfUserId());
		logger.info("Create Id = {}", user.getCreateId());

		ModelAndView mav = new ModelAndView();

		user.setCreateId(user.getCreateId());
		user.setCreateDt(user.getCreateDt());
		user.setUpdateDt(DateUtil.getSQLTimestamp());
		user.setUpdateId(BaseUtil.getStr(SessionData.getCurrentUserId()));

		if (BaseUtil.isObjNull(user.getName()) || BaseUtil.isObjNull(user.getUserName())
				|| BaseUtil.isObjNull(user.getUserRole()) || BaseUtil.isObjNull(user.getPassword())) {
			mav.addObject(PageConstants.DATA_LIST_OBJ, user);
			mav.setViewName(PageConstants.PAGE_CONST_USER_EDIT);
			mav.addObject("showSave", false);
			mav.addObject("showUpdate", true);
			if (BaseUtil.isObjNull(user.getName())) {
				mav.addObject("error", "Name cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getUserName())) {
				mav.addObject("error", "User Name cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getUserRole())) {
				mav.addObject("error", "User Role cannot be blank.");
			} else if (BaseUtil.isObjNull(user.getPassword())) {
				mav.addObject("error", "Password cannot be blank.");
			}
		} else {
			WfwUser wfwUser = new WfwUser();
			BeanUtil.copyProperties(user, wfwUser);
			boolean isUpdated = getCommonService().editWfUser(wfwUser);
			logger.info("isUpdated = {}", isUpdated);

			List<WfwUser> userList = getCommonService().findAllWfUser();

			mav.addObject(PageConstants.DATA_LIST_OBJ, userList);
			mav.setViewName(PageConstants.PAGE_CONST_USER_LIST);

			if (isUpdated) {
				mav.addAllObjects(PopupBox.success("User", "User", " User has successfully updated."));
			} else {
				mav.addAllObjects(PopupBox.error("Error!", "User",
						"User has not updated, please try again or contact with admin."));
			}
		}

		return mav;

	}


	@PostMapping(params = "cancel")
	public ModelAndView cancel() {
		List<WfwUser> userList = getCommonService().findAllWfUser();
		ModelAndView mav = new ModelAndView();
		mav.addObject(PageConstants.DATA_LIST_OBJ, userList);
		mav.setViewName(PageConstants.PAGE_CONST_USER_LIST);
		return mav;
	}

}
