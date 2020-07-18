package com.util;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * The Class QRGenerator.
 *
 * @author Taran
 * @since March 15, 2017
 */
public class QRGenerator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(QRGenerator.class);


	/**
	 * Instantiates a new QR generator.
	 */
	private QRGenerator() {
		// DO NOTHING
	}


	/**
	 * Generate QR code.
	 *
	 * @param secretKey the secret key
	 * @param qrcontent the qrcontent
	 * @return the string
	 */
	public static String generateQRCode(String secretKey, String... qrcontent) {
		return CryptoUtil.encrypt(getQrContent(qrcontent), secretKey);
	}


	/**
	 * Gets the qr content.
	 *
	 * @param qrcontent the qrcontent
	 * @return the qr content
	 */
	private static String getQrContent(String... qrcontent) {
		StringBuilder qrContentS = new StringBuilder();
		int count = 0;
		for (String content : qrcontent) {
			count++;
			if (count == 1) {
				qrContentS.append(content);
			} else {
				qrContentS.append(qrContentS);
				qrContentS.append("\n");
				qrContentS.append(content);
			}
		}
		return qrContentS.toString();
	}


	/**
	 * Generate QR code.
	 *
	 * @param size the size
	 * @param qrcontent the qrcontent
	 * @return the buffered image
	 */
	public static BufferedImage generateQRCode(int size, String... qrcontent) {
		try {
			EnumMap<EncodeHintType, ErrorCorrectionLevel> hintMap = new EnumMap<>(EncodeHintType.class);
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(getQrContent(qrcontent), BarcodeFormat.QR_CODE, size, size,
					hintMap);
			int matrixWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			return image;
		} catch (WriterException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

}