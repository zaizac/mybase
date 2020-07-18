package com.report.controller;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.core.AbstractRestController;
import com.report.sdk.constants.ReportConstants;


@Transactional
@RestController
@RequestMapping(value = ReportConstants.REPORT_URL)
public class ReportRestController extends AbstractRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportRestController.class);

}
