package com.icao.sdk.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IcaoResponse {

	private String result;

	private Boolean singleFace;

	private Boolean faceDetected;

	private Boolean imageInterpolated;

	private Boolean constraintsFullFrontal;

	private Boolean constraintsPassport;

	private Boolean resolution;

	private Boolean pose;

	private Boolean gazeFrontal;

	private Boolean mouthClosed;

	private Boolean nonOccluded;

	private Boolean background;

	private Boolean focus;

	private Boolean faceShadow;

	private Boolean notHotspots;

	private Boolean exposure;

	private Boolean expressionNeutral;

	private Boolean eyesOpen;

	private Boolean colour;

	private Boolean noReflections;

	private Boolean noGlasses;

	private String responseCode;

	private String responseMessage;


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public Boolean isSingleFace() {
		return singleFace;
	}


	public void setSingleFace(Boolean singleFace) {
		this.singleFace = singleFace;
	}


	public Boolean isFaceDetected() {
		return faceDetected;
	}


	public void setFaceDetected(Boolean faceDetected) {
		this.faceDetected = faceDetected;
	}


	public Boolean isImageInterpolated() {
		return imageInterpolated;
	}


	public void setImageInterpolated(Boolean imageInterpolated) {
		this.imageInterpolated = imageInterpolated;
	}


	public Boolean isConstraintsFullFrontal() {
		return constraintsFullFrontal;
	}


	public void setConstraintsFullFrontal(Boolean constraintsFullFrontal) {
		this.constraintsFullFrontal = constraintsFullFrontal;
	}


	public Boolean isConstraintsPassport() {
		return constraintsPassport;
	}


	public void setConstraintsPassport(Boolean constraintsPassport) {
		this.constraintsPassport = constraintsPassport;
	}


	public Boolean isResolution() {
		return resolution;
	}


	public void setResolution(Boolean resolution) {
		this.resolution = resolution;
	}


	public Boolean isPose() {
		return pose;
	}


	public void setPose(Boolean pose) {
		this.pose = pose;
	}


	public Boolean isGazeFrontal() {
		return gazeFrontal;
	}


	public void setGazeFrontal(Boolean gazeFrontal) {
		this.gazeFrontal = gazeFrontal;
	}


	public Boolean isMouthClosed() {
		return mouthClosed;
	}


	public void setMouthClosed(Boolean mouthClosed) {
		this.mouthClosed = mouthClosed;
	}


	public Boolean isNonOccluded() {
		return nonOccluded;
	}


	public void setNonOccluded(Boolean nonOccluded) {
		this.nonOccluded = nonOccluded;
	}


	public Boolean isBackground() {
		return background;
	}


	public void setBackground(Boolean background) {
		this.background = background;
	}


	public Boolean isFocus() {
		return focus;
	}


	public void setFocus(Boolean focus) {
		this.focus = focus;
	}


	public Boolean isFaceShadow() {
		return faceShadow;
	}


	public void setFaceShadow(Boolean faceShadow) {
		this.faceShadow = faceShadow;
	}


	public Boolean isNotHotspots() {
		return notHotspots;
	}


	public void setNotHotspots(Boolean notHotspots) {
		this.notHotspots = notHotspots;
	}


	public Boolean isExposure() {
		return exposure;
	}


	public void setExposure(Boolean exposure) {
		this.exposure = exposure;
	}


	public Boolean isExpressionNeutral() {
		return expressionNeutral;
	}


	public void setExpressionNeutral(Boolean expressionNeutral) {
		this.expressionNeutral = expressionNeutral;
	}


	public Boolean isEyesOpen() {
		return eyesOpen;
	}


	public void setEyesOpen(Boolean eyesOpen) {
		this.eyesOpen = eyesOpen;
	}


	public Boolean isColour() {
		return colour;
	}


	public void setColour(Boolean colour) {
		this.colour = colour;
	}


	public Boolean isNoReflections() {
		return noReflections;
	}


	public void setNoReflections(Boolean noReflections) {
		this.noReflections = noReflections;
	}


	public Boolean isNoGlasses() {
		return noGlasses;
	}


	public void setNoGlasses(Boolean noGlasses) {
		this.noGlasses = noGlasses;
	}


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	public String getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
