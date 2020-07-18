/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.report.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.baseboot.report.sdk.constants.ReportConstants;
import com.baseboot.report.sdk.constants.ReportEnum;
import com.baseboot.report.sdk.util.BaseUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
@Component
public class ReportUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtil.class);

    public static byte[] exportToXlsStream(Map<String, Object> jasperParams, Map<String, Object> reportParams, JRDataSource dataSource, ReportEnum report) throws Exception {
		String realPath = BaseUtil.getStr(reportParams.get("RPT_REAL_PATH"));
	
		// Add report parameters
		StringBuffer rptPath = new StringBuffer();
		rptPath.append(realPath);
		rptPath.append(report.getPath());
		rptPath.append(report.getFnamePdf() + ".jrxml");
	
		LOGGER.info("exportToPdf: " + BaseUtil.getStr(rptPath));
	
		// Compile design to JasperReport
		JasperReport jr = JasperCompileManager.compileReport(rptPath.toString());
	
		// Create the JasperPrint object
		LOGGER.info("realPath: " + realPath);
		jasperParams.putAll(getDefaultSettings(realPath));
		jasperParams.put("SUBREPORT_DIR", realPath + report.getPath());
	
		JasperPrint jp = null;
		if (!BaseUtil.isObjNull(dataSource)) {
		    jp = JasperFillManager.fillReport(jr, jasperParams, dataSource);
		} else {
		    jp = JasperFillManager.fillReport(jr, jasperParams);
		}
	
		OutputStream os = new ByteArrayOutputStream();
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jp));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
		exporter.setConfiguration(new SimpleXlsExporterConfiguration());
		exporter.exportReport();
		return ((ByteArrayOutputStream) os).toByteArray();
    }

    public static byte[] exportToPdfStream(Map<String, Object> jasperParams, Map<String, Object> reportParams, JRDataSource dataSource, ReportEnum report) throws Exception {
		String realPath = BaseUtil.getStr(reportParams.get("RPT_REAL_PATH"));
	
		// Add report parameters
		StringBuffer rptPath = new StringBuffer();
		rptPath.append(realPath);
		rptPath.append(report.getPath());
		rptPath.append(report.getFnamePdf() + ".jrxml");
		
		for(Entry<String, Object> entry : jasperParams.entrySet()) {
			if(entry.getValue() instanceof String) {
				String dirty = BaseUtil.getStr(entry.getValue());
				if(!BaseUtil.isObjNull(dirty)) {
					jasperParams.put(entry.getKey(), StringEscapeUtils.escapeHtml(dirty));
				}
			}
		}
	
		LOGGER.info("exportToPdf: " + BaseUtil.getStr(rptPath));
	
		// Compile design to JasperReport
		JasperReport jr = JasperCompileManager.compileReport(rptPath.toString());
	
		// Create the JasperPrint object
		LOGGER.info("realPath: " + realPath);
		jasperParams.putAll(getDefaultSettings(realPath));
		jasperParams.put("SUBREPORT_DIR", realPath + report.getPath());
	
		System.err.println("jasperParamsssss>>>>>>>>>>"+jasperParams);
		System.err.println("SUBREPORT_DIR"+ realPath + report.getPath());

		
		JasperPrint jp = null;
		if (!BaseUtil.isObjNull(dataSource)) {
		    jp = JasperFillManager.fillReport(jr, jasperParams, dataSource);
		} else {
		    jp = JasperFillManager.fillReport(jr, jasperParams);
		}
	
		LOGGER.info("[JasperPrint :" + jp + "]");
		return JasperExportManager.exportReportToPdf(jp);
    }

    public static Map<String, Object> getDefaultSettings(String realPath) {
		Map<String, Object> jasperParams = new HashMap<String, Object>();
		jasperParams.put("IMAGE_DIR", realPath + ReportConstants.RPT_IMG_PATH);
		jasperParams.put("RPT_LOGO", ReportConstants.RPT_IMG_LOGO_FDHP);
		jasperParams.put("REPORT_DIR", realPath + ReportConstants.PATH_REPORT);
		return jasperParams;
    }

    public static HttpHeaders getHttpHeadersPDF(String fileName, int contentLength) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("application/pdf"));
		header.setContentDispositionFormData(fileName, fileName);
		header.setContentLength(contentLength);
		header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		header.setExpires(0);
		header.setPragma("");
		return header;
    }

    public static HttpHeaders getHttpHeadersExcel(String fileName, int contentLength) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "inline;filename=" + fileName + "." + "xls");
		header.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		header.setContentLength(contentLength);
		header.setCacheControl("no-cache");
		header.setExpires(0);
		header.setPragma("");
		return header;
    }

}