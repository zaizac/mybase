/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.model;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mary.jane
 * @since Nov 28, 2018
 */
public enum IcaoInfo {

	SINGLE_FACE("singleFace", "SingleFace"),
	FACE_DETECTED("faceDetected", "FaceDetected"),
	IMAGE_INTERPOLATED("imageInterpolated", "IsPortraitImageInterpolated"),
	FULL_FRONTAL("constraintsFullFrontal", "GeometricConstraintsFullFrontalOk"),
	PASSPORT("constraintsPassport", "GeometricConstraintsPassportOk"),
	RESOLUTION("resolution", "ResolutionOk"),
	POSE("pose", "PoseOk"),
	GAZE_FRONTAL("gazeFrontal", "GazeFrontal"),
	MOUTH_CLOSED("mouthClosed", "MouthClosed"),
	NON_OCCLUDED("nonOccluded", "NonOccluded"),
	BACKGROUND("background", "BackgroundOk"),
	FOCUS("focus", "FocusOk"),
	FACE_SHADOW("faceShadow", "FaceShadowOk"),
	NOT_HOTSPOTS("notHotspots", "NoHotspots"),
	EXPOSURE("exposure", "ExposureOk"),
	EXPRESSION_NEUTRAL("expressionNeutral", "ExpressionNeutral"),
	EYES_OPEN("eyesOpen", "EyesOpen"),
	COLOUR("colour", "ColourOk"),
	NO_REFLECTIONS("noReflections", "NoReflection"),
	NO_GLASSES("noGlasses", "NoGlasses");

	private final String criteria;

	private final String config;

	private static final Logger LOGGER = LoggerFactory.getLogger(IcaoInfo.class);


	private IcaoInfo(String criteria, String config) {
		this.criteria = criteria;
		this.config = config;
	}


	public String getCriteria() {
		return criteria;
	}


	public String getConfig() {
		return config;
	}


	public static List<IcaoInfo> findListByCriteria(List<String> criteria) {
		List<IcaoInfo> icaoInfo = new ArrayList<>();
		for (String crit : criteria) {
			for (IcaoInfo v : IcaoInfo.values()) {
				LOGGER.info("findListByCriteria >> {} - {}", v.getCriteria(), crit);
				if (v.getCriteria().equals(crit)) {
					icaoInfo.add(v);
					break;
				}
			}
		}
		LOGGER.info("{}", icaoInfo.size());

		return icaoInfo;
	}
}
