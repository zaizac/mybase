/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.dialect.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Aug 26, 2015
 */
public enum ElementEnum {

	ALERT("alert", ElementConstants.HTML_DIV, "alert alert-default"),
	ALERT_INFO("alert-info", ElementConstants.HTML_DIV, "alert alert-info"),
	ALERT_DANGER("alert-danger", ElementConstants.HTML_DIV, "alert alert-danger"),
	ALERT_SUCCESS("alert-success", ElementConstants.HTML_DIV, "alert alert-success"),
	ALERT_WARN("alert-warn", ElementConstants.HTML_DIV, "alert alert-warning"),

	ALERT_INFO_RD("alert-info-rd", ElementConstants.HTML_DIV, "alert alert-info alert-rounded"),
	ALERT_DANGER_RD("alert-danger-rd", ElementConstants.HTML_DIV, "alert alert-danger alert-rounded"),
	ALERT_SUCCESS_RD("alert-success-rd", ElementConstants.HTML_DIV, "alert alert-success alert-rounded"),
	ALERT_WARN_RD("alert-warn-rd", ElementConstants.HTML_DIV, "alert alert-warning alert-rounded"),

	BADGE("badge", ElementConstants.HTML_SPAN, "badge badge-default"),
	BADGE_PRIMARY("badge-primary", ElementConstants.HTML_SPAN, "badge badge-primary"),
	BADGE_SUCCESS("badge-success", ElementConstants.HTML_SPAN, "badge badge-success"),
	BADGE_DANGER("badge-danger", ElementConstants.HTML_SPAN, "badge badge-danger"),
	BADGE_WARN("badge-warn", ElementConstants.HTML_SPAN, "badge badge-warning"),
	BADGE_INFO("badge-info", ElementConstants.HTML_SPAN, "badge badge-info"),

	BADGE_PILL("badge-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-default"),
	BADGE_PRIMARY_PILL("badge-primary-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-primary"),
	BADGE_SUCCESS_PILL("badge-success-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-success"),
	BADGE_DANGER_PILL("badge-danger-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-danger"),
	BADGE_WARN_PILL("badge-warn-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-warning"),
	BADGE_INFO_PILL("badge-info-pill", ElementConstants.HTML_SPAN, "badge badge-pill badge-info"),

	BUTTON_GROUP("btn-group", ElementConstants.HTML_DIV, "button-group"),

	BUTTON_DEFAULT("btn", ElementConstants.HTML_BUTTON, "btn btn-default"),
	BUTTON_PRIMARY("btn-primary", ElementConstants.HTML_BUTTON, "btn btn-primary"),
	BUTTON_SECONDARY("btn-secondary", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_SECONDARY),
	BUTTON_DANGER("btn-danger", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_DANGER),
	BUTTON_INFO("btn-info", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_INFO),
	BUTTON_SUCCESS("btn-success", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_SUCCESS),
	BUTTON_WARN("btn-warn", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_WARNING),
	BUTTON_BOX("btn-box", ElementConstants.HTML_BUTTON, "button-box"),
	BUTTON_CLOSE_DEFAULT("btn-close-df", ElementConstants.HTML_BUTTON, "btn close btn-default"),
	BUTTON_CLOSE_PRIMARY("btn-close-primary", ElementConstants.HTML_BUTTON, "btn close btn-primary"),
	BUTTON_INVERSE("btn-inverse", ElementConstants.HTML_BUTTON, "btn btn-inverse"),

	BUTTON_FACEBOOK("btn-facebook", ElementConstants.HTML_BUTTON, "btn btn-facebook waves-effect waves-light"),
	BUTTON_TWITTER("btn-twitter", ElementConstants.HTML_BUTTON, "btn btn-twitter waves-effect waves-light"),
	BUTTON_GOOGLE("btn-google", ElementConstants.HTML_BUTTON, "btn btn-googleplus waves-effect waves-light"),
	BUTTON_LINKEDIN("btn-linkedin", ElementConstants.HTML_BUTTON, "btn btn-linkedin waves-effect waves-light"),
	BUTTON_INSTAGRAM("btn-ig", ElementConstants.HTML_BUTTON, "btn btn-instagram waves-effect waves-light"),
	BUTTON_PINTEREST("btn-pinterest", ElementConstants.HTML_BUTTON, "btn btn-pinterest waves-effect waves-light"),
	BUTTON_DRIBBBLE("btn-dribbble", ElementConstants.HTML_BUTTON, "btn btn-dribbble waves-effect waves-light"),
	BUTTON_YOUTUBE("btn-youtube", ElementConstants.HTML_BUTTON, "btn btn-youtube waves-effect waves-light"),

	BUTTON_PRIMARY_CIRCLE("btn-primary-cl", ElementConstants.HTML_BUTTON, "btn btn-primary btn-circle"),
	BUTTON_SECONDARY_CIRCLE("btn-secondary-cl", ElementConstants.HTML_BUTTON, "btn btn-secondary btn-circle"),
	BUTTON_DANGER_CIRCLE("btn-danger-cl", ElementConstants.HTML_BUTTON, "btn btn-danger btn-circle"),
	BUTTON_INFO_CIRCLE("btn-info-cl", ElementConstants.HTML_BUTTON, "btn btn-info btn-circle"),
	BUTTON_SUCCESS_CIRCLE("btn-success-cl", ElementConstants.HTML_BUTTON, "btn btn-success btn-circle"),
	BUTTON_WARN_CIRCLE("btn-warn-cl", ElementConstants.HTML_BUTTON, "btn btn-warning btn-circle"),

	BUTTON_PRIMARY_DROPDOWN("btn-primary-dp", ElementConstants.HTML_BUTTON, "btn btn-primary dropdown-toggle"),
	BUTTON_SECONDARY_DROPDOWN("btn-secondary-dp", ElementConstants.HTML_BUTTON, "btn btn-secondary dropdown-toggle"),
	BUTTON_DANGER_DROPDOWN("btn-danger-dp", ElementConstants.HTML_BUTTON, "btn btn-danger dropdown-toggle"),
	BUTTON_INFO_DROPDOWN("btn-info-dp", ElementConstants.HTML_BUTTON, "btn btn-info dropdown-toggle"),
	BUTTON_SUCCESS_DROPDOWN("btn-success-dp", ElementConstants.HTML_BUTTON, "btn btn-success dropdown-toggle"),
	BUTTON_WARN_DROPDOWN("btn-warn-dp", ElementConstants.HTML_BUTTON, "btn btn-warning dropdown-toggle"),

	BUTTON_PRIMARY_WAVE("btn-primary-wave", ElementConstants.HTML_BUTTON, "btn btn-primary waves-effect waves-light"),
	BUTTON_SECONDARY_WAVE("btn-secondary-wave", ElementConstants.HTML_BUTTON,
			"btn btn-secondary waves-effect waves-light"),
	BUTTON_DANGER_WAVE("btn-danger-wave", ElementConstants.HTML_BUTTON, "btn btn-danger waves-effect waves-light"),
	BUTTON_INFO_WAVE("btn-info-wave", ElementConstants.HTML_BUTTON, "btn btn-info waves-effect waves-light"),
	BUTTON_WARN_WAVE("btn-warn-wave", ElementConstants.HTML_BUTTON, "btn btn-warning waves-effect waves-light"),

	BUTTON_PRIMARY_WAVE_OL("btn-primary-wave-ol", ElementConstants.HTML_BUTTON,
			"btn btn-outline-primary waves-effect waves-light"),
	BUTTON_SECONDARY_WAVE_OL("btn-secondary-wave-ol", ElementConstants.HTML_BUTTON,
			"btn btn-outline-secondary waves-effect waves-light"),
	BUTTON_DANGER_WAVE_OL("btn-danger-wave-ol", ElementConstants.HTML_BUTTON,
			"btn btn-outline-danger waves-effect waves-light"),
	BUTTON_INFO_WAVE_OL("btn-info-wave-ol", ElementConstants.HTML_BUTTON,
			"btn btn-outline-info waves-effect waves-light"),
	BUTTON_WARN_WAVE_OL("btn-warn-wave-ol", ElementConstants.HTML_BUTTON,
			"btn btn-outline-warning waves-effect waves-light"),

	BUTTON_PRIMARY_OUTLINE("btn-primary-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-primary"),
	BUTTON_SECONDARY_OUTLINE("btn-secondary-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-secondary"),
	BUTTON_DANGER_OUTLINE("btn-danger-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-danger"),
	BUTTON_INFO_OUTLINE("btn-info-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-info"),
	BUTTON_SUCCESS_OUTLINE("btn-success-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-success"),
	BUTTON_WARN_OUTLINE("btn-warn-outline", ElementConstants.HTML_BUTTON, "btn btn-outline-warning"),

	BUTTON_PRIMARY_RD("btn-primary-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-primary"),
	BUTTON_SECONDARY_RD("btn-secondary-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-secondary"),
	BUTTON_DANGER_RD("btn-danger-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-danger"),
	BUTTON_INFO_RD("btn-info-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-info"),
	BUTTON_SUCCESS_RD("btn-success-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-success"),
	BUTTON_WARN_RD("btn-warn-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-warning"),

	BUTTON_PRIMARY_RD_OUTLINE("btn-primary-rd-outline", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-outline-primary"),
	BUTTON_SECONDARY_RD_OUTLINE("btn-secondary-rd-outline", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-outline-secondary"),
	BUTTON_DANGER_RD_OUTLINE("btn-danger-rd-outline", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-outline-danger"),
	BUTTON_INFO_RD_OUTLINE("btn-info-rd-outline", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-outline-info"),
	BUTTON_SUCCESS_RD_OUTLINE("btn-success-rd-outline", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-outline-success"),
	BUTTON_WARN_RD_OUTLINE("btn-warn-rd-outline", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-outline-warning"),

	BUTTON_XS("btn-xs", ElementConstants.HTML_BUTTON, "btn btn-xs btn-default"),
	BUTTON_XS_PRIMARY("btn-xs-primary", ElementConstants.HTML_BUTTON, "btn btn-xs btn-primary"),
	BUTTON_XS_SECONDARY("btn-xs-secondary", ElementConstants.HTML_BUTTON, "btn btn-xs btn-secondary"),
	BUTTON_XS_INFO("btn-xs-info", ElementConstants.HTML_BUTTON, "btn btn-xs btn-info"),
	BUTTON_XS_DANGER("btn-xs-danger", ElementConstants.HTML_BUTTON, "btn btn-xs btn-danger"),
	BUTTON_XS_SUCCESS("btn-xs-success", ElementConstants.HTML_BUTTON, "btn btn-xs btn-success"),
	BUTTON_XS_WARN("btn-xs-warn", ElementConstants.HTML_BUTTON, "btn btn-xs btn-warning"),

	BUTTON_SM("btn-sm", ElementConstants.HTML_BUTTON, "btn btn-sm btn-default"),
	BUTTON_SM_PRIMARY("btn-sm-primary", ElementConstants.HTML_BUTTON, "btn btn-sm btn-primary"),
	BUTTON_SM_SECONDARY("btn-sm-secondary", ElementConstants.HTML_BUTTON, "btn btn-sm btn-secondary"),
	BUTTON_SM_INFO("btn-sm-info", ElementConstants.HTML_BUTTON, "btn btn-sm btn-info"),
	BUTTON_SM_DANGER("btn-sm-danger", ElementConstants.HTML_BUTTON, "btn btn-sm btn-danger"),
	BUTTON_SM_SUCCESS("btn-sm-success", ElementConstants.HTML_BUTTON, "btn btn-sm btn-success"),
	BUTTON_SM_WARN("btn-sm-warn", ElementConstants.HTML_BUTTON, "btn btn-sm btn-warning"),

	BUTTON_MD_PRIMARY("btn-md-primary", ElementConstants.HTML_BUTTON, "btn btn-md btn-primary"),
	BUTTON_MD_SECONDARY("btn-md-secondary", ElementConstants.HTML_BUTTON, "btn btn-md btn-secondary"),
	BUTTON_MD_INFO("btn-md-info", ElementConstants.HTML_BUTTON, "btn btn-md btn-info"),
	BUTTON_MD_DANGER("btn-md-danger", ElementConstants.HTML_BUTTON, "btn btn-md btn-danger"),
	BUTTON_MD_SUCCESS("btn-md-success", ElementConstants.HTML_BUTTON, "btn btn-md btn-success"),
	BUTTON_MD_WARN("btn-md-warn", ElementConstants.HTML_BUTTON, "btn btn-md btn-warning"),

	BUTTON_LG_PRIMARY("btn-lg-primary", ElementConstants.HTML_BUTTON, "btn btn-lg btn-primary"),
	BUTTON_LG_SECONDARY("btn-lg-secondary", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_SECONDARY),
	BUTTON_LG_INFO("btn-lg-info", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_INFO),
	BUTTON_LG_DANGER("btn-lg-danger", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_DANGER),
	BUTTON_LG_SUCCESS("btn-lg-success", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_SUCCESS),
	BUTTON_LG_WARN("btn-lg-warn", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_WARNING),

	BUTTON_XS_RD("btn-xs-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-default"),
	BUTTON_XS_PRIMARY_RD("btn-xs-primary-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-primary"),
	BUTTON_XS_SECONDARY_RD("btn-xs-secondary-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-secondary"),
	BUTTON_XS_INFO_RD("btn-xs-info-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-info"),
	BUTTON_XS_DANGER_RD("btn-xs-danger-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-danger"),
	BUTTON_XS_SUCCESS_RD("btn-xs-success-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-success"),
	BUTTON_XS_WARN_RD("btn-xs-warn-rd", ElementConstants.HTML_BUTTON, "btn btn-xs btn-warning"),

	BUTTON_SM_RD("btn-sm-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-default"),
	BUTTON_SM_PRIMARY_RD("btn-sm-primary-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-primary"),
	BUTTON_SM_SECONDARY_RD("btn-sm-secondary-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-secondary"),
	BUTTON_SM_INFO_RD("btn-sm-info-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-info"),
	BUTTON_SM_DANGER_RD("btn-sm-danger-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-danger"),
	BUTTON_SM_SUCCESS_RD("btn-sm-success-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-sm btn-success"),
	BUTTON_SM_WARN_RD("btn-sm-warn-rd", ElementConstants.HTML_BUTTON, "btn btn-sm btn-warning"),

	BUTTON_MD_PRIMARY_RD("btn-md-primary-rd", ElementConstants.HTML_BUTTON, "btn btn-md btn-primary"),
	BUTTON_MD_SECONDARY_RD("btn-md-secondary-rd", ElementConstants.HTML_BUTTON, "btn btn-md btn-secondary"),
	BUTTON_MD_INFO_RD("btn-md-info-rd", ElementConstants.HTML_BUTTON, "btn btn-md btn-info"),
	BUTTON_MD_DANGER_RD("btn-md-danger-rd", ElementConstants.HTML_BUTTON, "btn btn-md btn-danger"),
	BUTTON_MD_SUCCESS_RD("btn-md-success-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-sm btn-success"),
	BUTTON_MD_WARN_RD("btn-md-warn-rd", ElementConstants.HTML_BUTTON, "btn btn-md btn-warning"),

	BUTTON_LG_PRIMARY_RD("btn-lg-primary-rd", ElementConstants.HTML_BUTTON, "btn btn-lg btn-rounded btn-primary"),
	BUTTON_LG_SECONDARY_RD("btn-lg-secondary-rd", ElementConstants.HTML_BUTTON,
			StyleConstants.STYLE_FORM_BTN_SECONDARY),
	BUTTON_LG_INFO_RD("btn-lg-info-rd", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_INFO),
	BUTTON_LG_DANGER_RD("btn-lg-danger-rd", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_DANGER),
	BUTTON_LG_SUCCESS_RD("btn-lg-success-rd", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_SUCCESS),
	BUTTON_LG_WARN_RD("btn-lg-warn-rd", ElementConstants.HTML_BUTTON, StyleConstants.STYLE_FORM_BTN_WARNING),

	BUTTON_LG_BLK_PRIMARY("btn-lg-blk-primary", ElementConstants.HTML_BUTTON, "btn btn-block btn-lg btn-primary"),
	BUTTON_LG_BLK_SECONDARY("btn-lg-blk-secondary", ElementConstants.HTML_BUTTON,
			"btn btn-block btn-lg btn-secondary"),
	BUTTON_LG_BLK_INFO("btn-lg-blk-info", ElementConstants.HTML_BUTTON, "btn btn-block btn-lg btn-info"),
	BUTTON_LG_BLK_DANGER("btn-lg-blk-danger", ElementConstants.HTML_BUTTON, "btn btn-block btn-lg btn-danger"),
	BUTTON_LG_BLK_SUCCESS("btn-lg-blk-success", ElementConstants.HTML_BUTTON, "btn btn-block btn-lg btn-success"),
	BUTTON_LG_BLK_WARN("btn-lg-blk-warn", ElementConstants.HTML_BUTTON, "btn btn-block btn-lg btn-warning"),

	BUTTON_BLOCK_PRIMARY("btn-block-primary", ElementConstants.HTML_BUTTON, "btn btn-block btn-primary"),
	BUTTON_BLOCK_SECONDARY("btn-block-secondary", ElementConstants.HTML_BUTTON, "btn btn-block btn-secondary"),
	BUTTON_BLOCK_DANGER("btn-block-danger", ElementConstants.HTML_BUTTON, "btn btn-block btn-danger"),
	BUTTON_BLOCK_INFO("btn-block-info", ElementConstants.HTML_BUTTON, "btn btn-block btn-info"),
	BUTTON_BLOCK_SUCCESS("btn-block-success", ElementConstants.HTML_BUTTON, "btn btn-block btn-success"),
	BUTTON_BLOCK_WARN("btn-block-warn", ElementConstants.HTML_BUTTON, "btn btn-block btn-warning"),

	BUTTON_BLOCK_PRIMARY_RD("btn-block-primary-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-primary"),
	BUTTON_BLOCK_SECONDARY_RD("btn-block-secondary-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-secondary"),
	BUTTON_BLOCK_DANGER_RD("btn-block-danger-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-danger"),
	BUTTON_BLOCK_INFO_RD("btn-block-info-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-block btn-info"),
	BUTTON_BLOCK_SUCCESS_RD("btn-block-success-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-success"),
	BUTTON_BLOCK_WARN_RD("btn-block-warn-rd", ElementConstants.HTML_BUTTON, "btn btn-rounded btn-block btn-warning"),

	BUTTON_BLOCK_PRIMARY_OL("btn-block-primary-ol", ElementConstants.HTML_BUTTON, "btn btn-block btn-outline-primary"),
	BUTTON_BLOCK_SECONDARY_OL("btn-block-secondary-ol", ElementConstants.HTML_BUTTON,
			"btn btn-block btn-outline-secondary"),
	BUTTON_BLOCK_DANGER_OL("btn-block-danger-ol", ElementConstants.HTML_BUTTON, "btn btn-block btn-outline-danger"),
	BUTTON_BLOCK_INFO_OL("btn-block-info-ol", ElementConstants.HTML_BUTTON, "btn btn-block btn-outline-info"),
	BUTTON_BLOCK_SUCCESS_OL("btn-block-success-ol", ElementConstants.HTML_BUTTON, "btn btn-block btn-outline-success"),
	BUTTON_BLOCK_WARN_OL("btn-block-warn-ol", ElementConstants.HTML_BUTTON, "btn btn-block btn-outline-warning"),

	BUTTON_BLOCK_PRIMARY_OL_RD("btn-block-primary-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-primary"),
	BUTTON_BLOCK_SECONDARY_OL_RD("btn-block-secondary-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-secondary"),
	BUTTON_BLOCK_DANGER_OL_RD("btn-block-danger-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-danger"),
	BUTTON_BLOCK_INFO_OL_RD("btn-block-info-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-info"),
	BUTTON_BLOCK_SUCCESS_OL_RD("btn-block-success-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-success"),
	BUTTON_BLOCK_WARN_OL_RD("btn-block-warn-ol-rd", ElementConstants.HTML_BUTTON,
			"btn btn-rounded btn-block btn-outline-warning"),

	CARD("card", ElementConstants.HTML_DIV, "card"),
	CARD_HEADER("card-header", ElementConstants.HTML_DIV, "card"),
	CARD_BODY("card-body", ElementConstants.HTML_DIV, "card-body"),
	CARD_TITLE("card-title", ElementConstants.HTML_H4, "card-title"),
	CARD_SUBTITLE("card-subtitle", ElementConstants.HTML_H6, "card-subtitle"),

	CAPTCHA("captcha", ElementConstants.HTML_DIV, StyleConstants.STYLE_FORM_CONTROL + " captcha"),

	CHECKBOX_GROUP("checkbox-group", ElementConstants.HTML_DIV, "form-check"),
	CHECKBOX("checkbox", ElementConstants.HTML_INPUT, "checkbox check-success checkbox-circle"),
	CHECKBOX_INLINE("checkbox-inline", ElementConstants.HTML_INPUT, "checkbox-inline"),

	ERROR("error", ElementConstants.HTML_SPAN, "errors"),

	FORM("form", ElementConstants.HTML_FORM, "form"),

	FILE("file", ElementConstants.HTML_DIV, "fileinput fileinput-new input-group"),
	FILE_BUTTON("btn-file", ElementConstants.HTML_DIV, "fileinput"),
	FILE_VIEW("file-view", ElementConstants.HTML_DIV, "cursor-pointer"),

	FILE_THUMBNAIL("file-thumbnail", ElementConstants.HTML_DIV, StyleConstants.STYLE_FILEUPLOAD),
	FILE_THUMB_VIEW("file-thumbnail-view", ElementConstants.HTML_DIV, StyleConstants.STYLE_FILEUPLOAD),
	FILE_SLIDER("file-slider", ElementConstants.HTML_DIV, StyleConstants.STYLE_FILEUPLOAD),

	INPUT("input", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_AMOUNT("input-amount", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_CONTACT("input-contact", ElementConstants.HTML_INPUT,
			StyleConstants.STYLE_FORM_CONTROL + " input-contact limit-number"),
	INPUT_NUMBER("input-number", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_DATE("input-date", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_DATE_RANGE("input-date-range", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_TIME("input-time", ElementConstants.HTML_INPUT,
			StyleConstants.STYLE_FORM_CONTROL + " bootstrap-timepicker timepicker"),
	INPUT_HIDDEN("input-hidden", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_FILE("input-file", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_PASSWORD("input-password", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_TEXT("input-text", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),
	INPUT_DANGER("input-danger", ElementConstants.HTML_INPUT,
			StyleConstants.STYLE_FORM_CONTROL + " form-control-danger"),

	LABEL("label", ElementConstants.HTML_LABEL, "control-label"),

	MENU("menu", ElementConstants.HTML_UL, "in"),

	OTP("otp", ElementConstants.HTML_DIV, ""),
	OTP_EMAIL("otp-email", ElementConstants.HTML_DIV, StyleConstants.STYLE_FORM_CONTROL + " otp-email"),

	PANEL("panel", ElementConstants.HTML_DIV, StyleConstants.STYLE_FORM_CONTROL),

	PORTLET("portlet", ElementConstants.HTML_DIV, StyleConstants.STYLE_ROW_FLUID),
	PORTLET_DANGER("portlet-danger", ElementConstants.HTML_DIV, StyleConstants.STYLE_ROW_FLUID),
	PORTLET_SECOND("portlet-secondary", ElementConstants.HTML_DIV, StyleConstants.STYLE_ROW_FLUID),
	PORTLET_ICHECK("portlet-icheck", ElementConstants.HTML_DIV, StyleConstants.STYLE_ROW_FLUID),

	RADIO_GROUP("radio-group", ElementConstants.HTML_DIV, "form-check"),
	RADIO("radio", ElementConstants.HTML_INPUT, "custom-control custom-radio"),

	TABLE_LIGHT("table-light", ElementConstants.HTML_TABLE, "table color-table table-responsive"),
	TABLE_DARK("table-dark", ElementConstants.HTML_TABLE, "table color-table dark-table table-responsive"),
	TABLE_PRIMARY("table-primary", ElementConstants.HTML_TABLE, "table color-table primary-table table-responsive"),
	TABLE_SUCCESS("table-success", ElementConstants.HTML_TABLE, "table color-table success-table table-responsive"),
	TABLE_INFO("table-info", ElementConstants.HTML_TABLE, "table color-table info-table table-responsive"),
	TABLE_WARNING("table-warning", ElementConstants.HTML_TABLE, "table color-table warning-table table-responsive"),
	TABLE_DANGER("table-danger", ElementConstants.HTML_TABLE, "table color-table danger-table table-responsive"),
	TABLE_RED("table-red", ElementConstants.HTML_TABLE, "table color-table red-table table-responsive"),
	TABLE_PURPLE("table-purple", ElementConstants.HTML_TABLE, "table color-table purple-table table-responsive"),
	TABLE_MUTED("table-muted", ElementConstants.HTML_TABLE, "table color-table muted-table table-responsive"),

	TABLE_BD_LIGHT("table-bd-light", ElementConstants.HTML_TABLE, "table color-bordered-table table-responsive"),
	TABLE_BD_DARK("table-bd-dark", ElementConstants.HTML_TABLE, "table color-bordered-table dark-bordered-table table-responsive"),
	TABLE_BD_PRIMARY("table-bd-primary", ElementConstants.HTML_TABLE,
			"table color-bordered-table primary-bordered-table table-responsive"),
	TABLE_BD_SUCCESS("table-bd-success", ElementConstants.HTML_TABLE,
			"table color-bordered-table success-bordered-table"),
	TABLE_BD_INFO("table-bd-info", ElementConstants.HTML_TABLE, "table color-bordered-table info-bordered-table table-responsive"),
	TABLE_BD_WARNING("table-bd-warning", ElementConstants.HTML_TABLE,
			"table color-bordered-table warning-bordered-table table-responsive"),
	TABLE_BD_DANGER("table-bd-danger", ElementConstants.HTML_TABLE,
			"table color-bordered-table danger-bordered-table table-responsive"),
	TABLE_BD_RED("table-bd-red", ElementConstants.HTML_TABLE, "table color-bordered-table red-bordered-table table-responsive"),
	TABLE_BD_PURPLE("table-bd-purple", ElementConstants.HTML_TABLE,
			"table color-bordered-table purple-bordered-table table-responsive"),
	TABLE_BD_MUTED("table-bd-muted", ElementConstants.HTML_TABLE, "table color-bordered-table muted-bordered-table table-responsive"),

	TABLE_FULL_LIGHT("table-full-light", ElementConstants.HTML_TABLE, "table full-color-table table-responsive"),
	TABLE_FULL_DARK("table-full-dark", ElementConstants.HTML_TABLE, "table full-color-table full-dark-table table-responsive"),
	TABLE_FULL_PRIMARY("table-full-primary", ElementConstants.HTML_TABLE, "table full-color-table full-primary-table table-responsive"),
	TABLE_FULL_SUCCESS("table-full-success", ElementConstants.HTML_TABLE, "table full-color-table full-success-table table-responsive"),
	TABLE_FULL_INFO("table-full-info", ElementConstants.HTML_TABLE, "table full-color-table full-info-table table-responsive"),
	TABLE_FULL_WARNING("table-full-warning", ElementConstants.HTML_TABLE, "table full-color-table full-warning-table table-responsive"),
	TABLE_FULL_DANGER("table-full-danger", ElementConstants.HTML_TABLE, "table full-color-table full-danger-table table-responsive"),
	TABLE_FULL_RED("table-full-red", ElementConstants.HTML_TABLE, "table full-color-table full-red-table table-responsive"),
	TABLE_FULL_PURPLE("table-full-purple", ElementConstants.HTML_TABLE, "table full-color-table full-purple-table table-responsive"),
	TABLE_FULL_MUTED("table-full-muted", ElementConstants.HTML_TABLE, "table full-color-table full-muted-table table-responsive"),

	TAB_H("tab", ElementConstants.HTML_DIV, null),
	TAB_H_NAV("tab-ul", ElementConstants.HTML_UL, "nav nav-tabs"),
	TAB_H_NAV_LI("tab-li", ElementConstants.HTML_LI, StyleConstants.STYLE_NAV_LINK),
	TAB_H_PANES("tab-panes", ElementConstants.HTML_DIV, "tab-content tabcontent-border"),
	TAB_H_PANE("tab-pane", ElementConstants.HTML_DIV, StyleConstants.STYLE_TAB_PANE),

	TAB_V("vtab", ElementConstants.HTML_DIV, "vtabs"),
	TAB_V_NAV("vtab-ul", ElementConstants.HTML_UL, "nav nav-tabs tabs-vertical"),
	TAB_V_NAV_LI("vtab-li", ElementConstants.HTML_LI, StyleConstants.STYLE_NAV_LINK),
	TAB_V_NAV_LI_NEW("vtab-li", ElementConstants.HTML_LI, StyleConstants.STYLE_NAV_LINK),
	TAB_V_PANES("vtab-panes", ElementConstants.HTML_DIV, "tab-content"),
	TAB_V_PANE("vtab-pane", ElementConstants.HTML_DIV, StyleConstants.STYLE_TAB_PANE),

	TAB_PILL("tab-pills", ElementConstants.HTML_DIV, null),
	TAB_PILL_NAV("tab-pills-ul", ElementConstants.HTML_UL, "nav nav-pills"),
	TAB_PILL_NAV_LI("tab-pills-li", ElementConstants.HTML_LI, StyleConstants.STYLE_NAV_LINK),
	TAB_PILL_PANES("tab-pills-panes", ElementConstants.HTML_DIV, "tab-content br-n pn"),
	TAB_PILL_PANE("tab-pills-pane", ElementConstants.HTML_DIV, StyleConstants.STYLE_TAB_PANE),

	TEXTAREA("textarea", ElementConstants.HTML_INPUT, StyleConstants.STYLE_FORM_CONTROL),

	SCRIPT("script", ElementConstants.HTML_SCRIPT, StyleConstants.STYLE_FORM_CONTROL),

	SELECT("select", ElementConstants.HTML_SELECT, StyleConstants.STYLE_FORM_CONTROL + "select-filter"),
	SELECT_MULTIPLE("multi-select", ElementConstants.HTML_SELECT, StyleConstants.STYLE_FORM_CONTROL + "form-control"),

	SLABEL_PRIMARY("slbl-primary", ElementConstants.HTML_SPAN, "label label-primary"),
	SLABEL_INFO("slbl-info", ElementConstants.HTML_SPAN, "label label-info"),
	SLABEL_DANGER("slbl-danger", ElementConstants.HTML_SPAN, "label label-danger"),
	SLABEL_SUCCESS("slbl-success", ElementConstants.HTML_SPAN, "label label-success"),
	SLABEL_INVERSE("slbl-inverse", ElementConstants.HTML_SPAN, "label label-inverse"),
	SLABEL_WARN("slbl-warn", ElementConstants.HTML_SPAN, "label label-warning"),

	SLABEL_PRIMARY_LIGHT("slbl-primary-light", ElementConstants.HTML_SPAN, "label label-light-primary"),
	SLABEL_INFO_LIGHT("slbl-info-light", ElementConstants.HTML_SPAN, "label label-light-info"),
	SLABEL_DANGER_LIGHT("slbl-danger-light", ElementConstants.HTML_SPAN, "label label-light-danger"),
	SLABEL_SUCCESS_LIGHT("slbl-success-light", ElementConstants.HTML_SPAN, "label label-light-success"),
	SLABEL_INVERSE_LIGHT("slbl-inverse-light", ElementConstants.HTML_SPAN, "label label-light-inverse"),
	SLABEL_WARN_LIGHT("slbl-warn-light", ElementConstants.HTML_SPAN, "label label-light-warning"),

	SLABEL_PRIMARY_RD("slbl-primary-rd", ElementConstants.HTML_SPAN, "label label-rounded label-primary"),
	SLABEL_INFO_RD("slbl-info-rd", ElementConstants.HTML_SPAN, "label label-rounded label-info"),
	SLABEL_DANGER_RD("slbl-danger-rd", ElementConstants.HTML_SPAN, "label label-rounded label-danger"),
	SLABEL_SUCCESS_RD("slbl-success-rd", ElementConstants.HTML_SPAN, "label label-rounded label-success"),
	SLABEL_INVERSE_RD("slbl-inverse-rd", ElementConstants.HTML_SPAN, "label label-rounded label-inverse"),
	SLABEL_WARN_RD("slbl-warn-rd", ElementConstants.HTML_SPAN, "label label-rounded label-warning"),

	WIDGET("widget", ElementConstants.HTML_DIV, "card"),
	WIDGET_HEADER("widget-header", ElementConstants.HTML_DIV, "card-header"),
	WIDGET_HEADER_ACCORDION("widget-header-acc", ElementConstants.HTML_DIV, "accordion"),
	WIDGET_TITLE("widget-title", ElementConstants.HTML_DIV, "card-title"),
	WIDGET_BODY("widget-body", ElementConstants.HTML_DIV, "card-body"),
	WIDGET_FOOTER("widget-footer", ElementConstants.HTML_DIV, "card-footer"),

	;

	private final String name;

	private final String processor;

	private final String style;


	private ElementEnum(String name, String processor, String style) {
		this.name = name;
		this.processor = processor;
		this.style = style;
	}


	public String getName() {
		return name;
	}


	public String getProcessor() {
		return processor;
	}


	public String getStyle() {
		return style;
	}


	public static String findProcessorByName(String name) {
		for (ElementEnum v : ElementEnum.values()) {
			if (v.getName().equals(name)) {
				return v.getProcessor();
			}
		}

		return null;
	}


	public static String findStyleByName(String name) {
		for (ElementEnum v : ElementEnum.values()) {
			if (v.getName().equals(name)) {
				return v.getStyle();
			}
		}

		return null;
	}
}