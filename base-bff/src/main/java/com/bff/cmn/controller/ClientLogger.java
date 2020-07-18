package com.bff.cmn.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bff.core.AbstractController;
import com.bff.util.constants.PageConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.MediaType;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ClientLogger extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(ClientLogger.class);

	private static final String LINE_NUMBER = "lineNumber";

	private static final String FILENAME = "fileName";

	private static final String TIMESTAMP = "timestamp";

	private static final String LEVEL = "level";

	private static final String ADDITIONAL = "additional";

	private static final String MESSAGE = "message";


	/**
	 * POST Post client-side error log to server.
	 */
	@PostMapping(value = PageConstants.LOGS, consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	public ResponseEntity<Boolean> logClientSideError(@RequestBody Object request) {
		JsonNode jn = JsonUtil.toJsonNode(request);
		if ( !BaseUtil.isObjNull(jn)) {
			
			String level = "";
			switch (jn.get(LEVEL).asInt()) {
			case 0:
				level = "TRACE";
				break;
			case 1:
				level = "DEBUG";
				break;
			case 2:
				level = "INFO";
				break;
			case 3:
				level = "LOG";
				break;
			case 4:
				level = "WARN";
				break;
			case 5:
				level = "ERROR";
				break;
			case 6:
				level = "FATAL";
				break;
			case 7:
				level = "OFF";
				break;
			default:
				break;
			}

			if(!BaseUtil.isStringNull(level)) {
				MDC.put(LEVEL, level);
				MDC.put(TIMESTAMP, jn.get(TIMESTAMP).asText(""));
				MDC.put(MESSAGE, jn.get(MESSAGE).asText(""));
				MDC.put(FILENAME, jn.get(FILENAME).asText(""));
				MDC.put(LINE_NUMBER, jn.get(LINE_NUMBER).asText(""));
				MDC.put(ADDITIONAL, jn.get(ADDITIONAL).asText(""));
				log.info("");
				MDC.remove(LEVEL);
				MDC.remove(TIMESTAMP);
				MDC.remove(MESSAGE);
				MDC.remove(FILENAME);
				MDC.remove(LINE_NUMBER);
				MDC.remove(ADDITIONAL);
			}
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
