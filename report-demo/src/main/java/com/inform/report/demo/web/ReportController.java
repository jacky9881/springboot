package com.inform.report.demo.web;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.FileHtmlResourceHandler;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReportController {
	
	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	private static final String TEMPLATE_PATH = "jaspertemplate/";
	private static final String TEMPLATE_FILE_EXTENTION = ".jasper";
	private static final String REPORT_NAME = "reportName";
	private static final String FILE_FORMAT = "format";
	
	@Autowired
	private DataSource datasource;
	
	/**
	 * 直接使用模板文件中的sql语句得到报表
	 * 
	 * @param reportName 报表名称
	 * @param params 参数Map
	 * @return
	 */
	@GetMapping("report/{reportName}")
	public void showReportByParam(@PathVariable(REPORT_NAME) final String reportName, @RequestParam Map<String, Object> params, HttpServletResponse response) {
		
		logger.info("showReportByParam ==> reportName:{},params.size:{}",reportName, params.keySet().size());
		
		String format = (String)params.get(FILE_FORMAT);
		
		String jasperPath = ReportController.class.getResource("/").getPath() + TEMPLATE_PATH + reportName + TEMPLATE_FILE_EXTENTION;
		//InputStream inputStream = ReportController.class.getResourceAsStream("/" + TEMPLATE_PATH + reportName + TEMPLATE_FILE_EXTENTION);
		Connection conn = null;
		
		response.setCharacterEncoding("UTF-8");
				
		try {
			conn = datasource.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, params, conn);
			String generateFileName = reportName + "." + format;
			
			if ("pdf".equalsIgnoreCase(format)) {
				
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
						response.getOutputStream()));
				
				exporter.exportReport();
				
			} else if("xls".equalsIgnoreCase(format)) {
				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment; filename="
						+ URLEncoder.encode(generateFileName,"utf8"));
				
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setOnePagePerSheet(Boolean.FALSE);
				configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
				configuration.setWhitePageBackground(Boolean.FALSE);
				
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setConfiguration(configuration);
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				//exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
						response.getOutputStream()));
				
				exporter.exportReport();
				
			} else if("xlsx".equalsIgnoreCase(format)) {
				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment; filename="
						+ URLEncoder.encode(generateFileName,"utf8"));
				
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setOnePagePerSheet(Boolean.FALSE);
				configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
				configuration.setWhitePageBackground(Boolean.FALSE);
				
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setConfiguration(configuration);
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				//exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
						response.getOutputStream()));
				
				exporter.exportReport();
				
			} else if("docx".equalsIgnoreCase(format)) {
				
				response.setContentType("application/vnd.ms-word");
				response.setHeader("Content-disposition", "attachment; filename="
						+ URLEncoder.encode(generateFileName,"utf8"));

				JRDocxExporter exporter = new JRDocxExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				//exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
						response.getOutputStream()));
				exporter.exportReport();
			} else {
			//	JasperRunManager.runReportToHtmlFile(jasperPath, parameters);
				HtmlExporter exporter = new HtmlExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				
				SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(
						response.getWriter());
				FileHtmlResourceHandler handler = new FileHtmlResourceHandler(new File(ReportController.class.getResource("/").getPath()
						+ TEMPLATE_PATH));
				
				output.setImageHandler(handler);
				exporter.setExporterOutput(output);
				
				exporter.exportReport();
	
			}
			
		} catch (Exception e) {
			logger.error("exception message:{},detail:{}", e.getMessage(), e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("exception message:{},detail:{}", e.getMessage(), e);
				}
			}
		}
	}
}
