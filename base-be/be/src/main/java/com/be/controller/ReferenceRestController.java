/**
 * Copyright 2019
 */
package com.be.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.core.AbstractRestController;
import com.be.sdk.constants.BeUrlConstants;

/**
 * @author mary.jane
 * @since Oct 25, 2018
 */
@Lazy
@RestController
@RequestMapping(BeUrlConstants.REFERENCE_BE)
public class ReferenceRestController extends AbstractRestController {

}