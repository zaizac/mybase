/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.cmn.controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class CaptchaGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaGenerator.class);
	
	public static final String CPTCHVAL = "CPTCHVAL";

	public static final String CPTCHIMG = "CPTCHIMG";

	char chars[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's',
			't', 'v', 'w', 'x', 'y', 'z' };

	char digits[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private TextLayout textLayout;

	private int width = 400;

	private int height = 120;

	String captchaVal;

	private String fontType = "Arial";

	private int fontStyle = Font.BOLD;

	private int fontSize = 72;


	public CaptchaGenerator() {
	}


	public CaptchaGenerator(String fontType, Integer fontStyle, Integer fontSize) {
		if (!BaseUtil.isObjNull(fontType)) {
			this.fontType = fontType;
		}
		if (!BaseUtil.isObjNull(fontStyle)) {
			this.fontStyle = fontStyle;
		}
		if (!BaseUtil.isObjNull(fontSize)) {
			this.fontSize = fontSize;
		}
	}


	private char[] getText(int digit) {
		char selected[] = new char[digit];
		for (int i = 0; i < digit; i++) {
			int selectArr = (int) Math.floor(Math.random() * digit);
			if (selectArr > 5) {
				int selectDigit = (int) Math.floor(Math.random() * digit);
				selectDigit = (selectDigit == 9) ? 8 : selectDigit;
				selected[i] = digits[selectDigit];
			} else {
				int selectChar = (int) Math.floor(Math.random() * (chars.length - 1));
				selected[i] = chars[selectChar];
			}
		}
		return selected;
	}


	public Map<String, Object> getCaptchaShadowedText(int digit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CPTCHIMG, createImage("shadow", digit));
		LOGGER.debug("captchaVal: {}", captchaVal);
		map.put(CPTCHVAL, captchaVal);
		return map;
	}


	public Map<String, Object> getCaptchaSplitTwo(int digit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CPTCHIMG, createImage(digit));
		LOGGER.debug("captchaVal: {}", captchaVal);
		map.put(CPTCHVAL, captchaVal);
		return map;
	}


	private BufferedImage createImage(int digit) {
		captchaVal = BaseConstants.EMPTY_STRING;

		Font font = new Font(fontType, Font.ITALIC, 36);

		char selected[] = new char[digit];

		for (int i = 0; i < digit; i++) {
			int selectArr = (int) Math.floor(Math.random() * digit);
			if (selectArr > 5) {
				int selectDigit = (int) Math.floor(Math.random() * digit);
				selectDigit = (selectDigit == 9) ? 8 : selectDigit;
				selected[i] = digits[selectDigit];
			} else {
				int selectChar = (int) Math.floor(Math.random() * (chars.length - 1));
				selected[i] = chars[selectChar];
			}
		}

		int length = 8;
		int firstLength = (int) ((Math.random() * (length - 3))) + 3;
		firstLength = ((length - firstLength) < 3) ? 3 : firstLength;

		int w = 190;
		int h = 95;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = image.createGraphics();
		g.setColor(new Color(170, 178, 189));
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		g.setFont(font);

		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.PI / 8.0);
		g.transform(at);
		at.setToTranslation(-20.0f, 50.0f);
		g.transform(at);

		font = new Font("Arial", Font.ITALIC, 48);
		g.setFont(font);

		for (int i = 0; i < firstLength; i++) {
			int size = (int) Math.random() * 48 + 20;
			font = new Font("Arial", Font.ITALIC, size);
			g.setFont(font);
			g.drawString(String.valueOf(selected[i]) + "  ", (float) 30 + i * 13, 10 + (i + 5));
			captchaVal = captchaVal + String.valueOf(selected[i]);
		}

		for (int i = firstLength; i < length; i++) {
			int size = (int) Math.random() * 50 + 20;
			font = new Font("Arial", Font.ITALIC, size);
			g.setFont(font);
			g.drawString(String.valueOf(selected[i]) + " ", (float) 30 + i * 13, 50);
			captchaVal = captchaVal + String.valueOf(selected[i]);
		}
		return image;
	}


	private BufferedImage createImage(String type, int digit) {
		int x = 50;
		int y = 80;

		captchaVal = BaseConstants.EMPTY_STRING;

		char selected[] = getText(digit);

		for (int i = 0; i < selected.length; i++) {
			captchaVal = captchaVal + String.valueOf(selected[i]);
		}

		Font font = new Font(fontType, fontStyle, fontSize);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g1 = image.createGraphics();
		setRenderingHints(g1);

		textLayout = new TextLayout(captchaVal, font, g1.getFontRenderContext());
		g1.setPaint(Color.LIGHT_GRAY);
		g1.fillRect(0, 0, width, height);
		g1.setPaint(new Color(150, 150, 150));
		textLayout.draw(g1, x + 5, y + 5);
		g1.dispose();

		float[] kernel = { 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f };

		ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel), ConvolveOp.EDGE_NO_OP, null);
		BufferedImage image2 = op.filter(image, null);

		Graphics2D g2 = image2.createGraphics();
		setRenderingHints(g2);
		g2.setPaint(Color.BLACK);
		textLayout.draw(g2, x, y);

		return image2;
	}


	private void setRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}
	
}