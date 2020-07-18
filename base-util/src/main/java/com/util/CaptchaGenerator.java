/**
 * Copyright 2019. 
 */
package com.util;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.constants.BaseConstants;


/**
 * The Class CaptchaGenerator.
 *
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class CaptchaGenerator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaGenerator.class);

	/** The Constant CPTCHVAL. */
	public static final String CPTCHVAL = "CPTCHVAL";

	/** The Constant CPTCHIMG. */
	public static final String CPTCHIMG = "CPTCHIMG";

	/** The captcha val. */
	StringBuilder captchaVal;

	/** The font type. */
	private String fontType = "Arial";

	/** The font style. */
	private int fontStyle = Font.BOLD;

	/** The font size. */
	private int fontSize = 72;

	/** The width. */
	private int width = 400;

	/** The height. */
	private int height = 120;


	/**
	 * Instantiates a new captcha generator.
	 */
	public CaptchaGenerator() {
		// DO NOTHING
	}


	/**
	 * Instantiates a new captcha generator.
	 *
	 * @param fontType the font type
	 * @param fontStyle the font style
	 * @param fontSize the font size
	 */
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


	/**
	 * Gets the captcha shadowed text.
	 *
	 * @param digit the digit
	 * @return the captcha shadowed text
	 */
	public Map<String, Object> getCaptchaShadowedText(int digit) {
		Map<String, Object> map = new HashMap<>();
		map.put(CPTCHIMG, createShadowImage(digit));
		LOGGER.debug("captchaVal: {}", captchaVal);
		map.put(CPTCHVAL, captchaVal.toString());
		return map;
	}


	/**
	 * Creates the shadow image.
	 *
	 * @param digit the digit
	 * @return the buffered image
	 */
	private BufferedImage createShadowImage(int digit) {
		int x = 50;
		int y = 80;

		captchaVal = new StringBuilder(BaseConstants.EMPTY_STRING);
		captchaVal.append(RandomStringUtils.randomAlphanumeric(digit));

		Font font = new Font(fontType, fontStyle, fontSize);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g1 = image.createGraphics();
		setRenderingHints(g1);

		TextLayout textLayout = new TextLayout(captchaVal.toString(), font, g1.getFontRenderContext());
		g1.setPaint(Color.LIGHT_GRAY);
		g1.fillRect(0, 0, width, height);
		g1.setPaint(new Color(150, 150, 150));
		textLayout.draw(g1, (float) x + 5, (float) y + 5);
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


	/**
	 * Sets the rendering hints.
	 *
	 * @param g the new rendering hints
	 */
	private void setRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}

}