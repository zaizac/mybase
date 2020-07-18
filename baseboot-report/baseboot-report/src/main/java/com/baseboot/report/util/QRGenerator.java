package com.baseboot.report.util;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.util.CryptoUtil;
import com.baseboot.report.sdk.constants.BaseConstants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018 
 */
public class QRGenerator {

	private static final Logger logger = LoggerFactory.getLogger(QRGenerator.class);


	public static BufferedImage generateQRCode(int size, String secretKey, String... qrcontent) {
		String qrContentS = BaseConstants.EMPTY_STRING;

		int count = 0;
		for (String content : qrcontent) {
			count++;
			if (count == 1) {
				qrContentS = content;
			} else {
				qrContentS = qrContentS + "\n" + content;
			}
		}

		qrContentS = CryptoUtil.encrypt(qrContentS, secretKey);

		try {
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(qrContentS, BarcodeFormat.QR_CODE, size, size, hintMap);
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
			logger.error("WriterException: {}", e.getMessage());
			return null;
		}
	}

}