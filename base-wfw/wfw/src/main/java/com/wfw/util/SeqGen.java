package com.wfw.util;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.util.constants.BaseConstants;


public class SeqGen {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	public static synchronized String getSequenceNo() {
		String randomNum = String.valueOf((new Random().nextInt(9) + 1));
		String id = Long.toString(System.nanoTime());

		StringBuilder sb = new StringBuilder();
		sb.append(randomNum);
		sb.append(id);

		return StringUtils.trimAllWhitespace(sb.toString()).replaceAll("-", BaseConstants.EMPTY_STRING);
	}

}
