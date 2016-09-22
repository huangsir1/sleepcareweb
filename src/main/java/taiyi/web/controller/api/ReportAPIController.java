/**
 * 
 */
package taiyi.web.controller.api;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import net.sf.cglib.core.Local;
import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.Status;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.thread.GenerateReportThread;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.api
 *
 *         2016年3月9日
 */
@Controller
@RequestMapping("/api")
public class ReportAPIController extends APIExceptionHandlerController {
	Logger logger = Logger.getLogger(ReportAPIController.class);

	// TODO 所有接口未验证用户合法性
	@Autowired
	private UserService userService;
	@Autowired
	private SleepReportService sleepReportService;
	@Autowired
	private BreatheReportService breatheReportService;
	@Autowired
	private SubReportService subReportService;
	@Autowired
	private WebService webService;

	@RequestMapping(value = "/sleepReport/upload", consumes = "application/json")
	@ResponseBody
	@Deprecated
	public Status uploadSleepReport(@RequestBody SleepReport sleepReport) {
		// TODO 验证用户合法性
		if (sleepReport.getUserId() == null || userService.selectByPrimaryKey(sleepReport.getUserId()) == null) {
			Status userUnregister = Status.USER_UNREGISTER;
			userUnregister.setMessage(userUnregister.getMessage() + "用户id" + sleepReport.getUserId());
			return Status.USER_UNREGISTER;
		}
		if (sleepReportService.selectByPrimaryKey(sleepReport.getId()) != null) {
			return Status.REPORT_EXSIT;
		}
		sleepReport.setUploadDate(new Date());
		sleepReportService.insert(sleepReport);
		return Status.SUCCESSED;
	}

	@RequestMapping(value = "/breatheReport/upload", consumes = "application/json")
	@ResponseBody
	@Deprecated
	public Status breatheSleepReport(@RequestBody BreatheReport breatheReport) {
		// TODO 验证用户合法性
		if (breatheReport.getUserId() == null || userService.selectByPrimaryKey(breatheReport.getUserId()) == null) {
			return Status.USER_UNREGISTER;
		}
		if (sleepReportService.selectByPrimaryKey(breatheReport.getId()) != null) {
			return Status.REPORT_EXSIT;
		}
		breatheReport.setUploadDate(new Date());
		breatheReportService.insert(breatheReport);
		return Status.SUCCESSED;
	}

	@RequestMapping(value = "/subReport/upload", consumes = "application/json")
	@ResponseBody
	@Deprecated
	public Status subReportReport(@RequestBody SubReport subReport) {
		// TODO 验证用户合法性
		if (subReportService.selectByPrimaryKey(subReport.getId()) != null) {
			return Status.REPORT_EXSIT;
		}
		subReportService.insert(subReport);
		return Status.SUCCESSED;
	}

	/**
	 * 上传所有报告字段
	 * @param baseReport 报告类
	 * @param request
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/baseReport/upload", consumes = "application/json")
	@ResponseBody
	public Status baseReport(@RequestBody BaseReport baseReport, HttpServletRequest request) throws Exception {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		// check user
		String language = request.getHeader("language");
		if (language == null) {
			language = "zh";
		}
		
		if (baseReport.getUserId() == null || userService.selectByPrimaryKey(baseReport.getUserId()) == null) {
			return Status.USER_UNREGISTER;
		}
		if (sleepReportService.selectByPrimaryKey(baseReport.getId()) != null) {
			webService.updateReport(baseReport);
			return Status.SUCCESSED_RECOVER_REPORT;
		}
		logger.warn("baseReport   " + JSON.toJSON(baseReport));
		webService.insertReport(baseReport);
		if (webService.isReportAllReady(baseReport.getId())) {
			new GenerateReportThread(webService, baseReport.getId(), basePath, servletRailPath, new Locale(language)).start();
			return Status.SUCCESSED_UPLOAD_REPORT_AND_GENERATE_IT;
		}
		return Status.SUCCESSED_UPLOAD_REPORT;
	}

	/**
	 * 上传医生建议
	 * @param subReport
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/advice/upload", consumes = "application/json")
	@ResponseBody
	public Status uploadReportAdvice(@RequestBody SubReport subReport, HttpServletRequest request) throws Exception {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		if (subReportService.selectByPrimaryKey(subReport.getId()) == null) {
			return Status.REPORT_NOT_EXSIT;
		}
		logger.warn("baseReport   " + JSON.toJSON(subReport));
		subReportService.updateByPrimaryKeySelective(subReport);
		if (webService.isReportAllReady(subReport.getId())) {
			new GenerateReportThread(webService, subReport.getId(), basePath, servletRailPath).start();
			return Status.SUCCESSED_UPLOAD_REPORT_AND_GENERATE_IT;
		}
		return Status.SUCCESSED_UPLOAD_REPORT;
	}

	/**
	 * 上传报告数据文件
	 * @param file 文件
	 * @param reportId 报告id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/report/file/upload")
	@ResponseBody
	public Status uploadReportFile(@RequestParam(value = "file") MultipartFile file, String reportId,
			HttpServletRequest request) {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		SleepReport sReport = sleepReportService.selectByPrimaryKey(reportId);
		if (sReport == null) {
			return Status.REPORT_NOT_EXSIT;
		}
		String language = request.getHeader("language");
		if (language == null) {
			language = "zh";
		}
		
		try {
			String fileName = WebProperties.getReportFileName(sReport.getUserId(), reportId);
			File targetFile = new File(fileName);
			if (!targetFile.exists()) {
				boolean isMkdir = targetFile.getParentFile().mkdirs();
				if (!isMkdir) {
					return Status.FAILD_TO_MKDIR;
				}
			}
			// 保存
			file.transferTo(targetFile);
			System.out.println(fileName + " 保存成功");
			if (webService.isReportAllReady(reportId)) {
				new GenerateReportThread(webService, reportId, basePath, servletRailPath,new Locale(language)).start();
				return Status.FILE_UPLOAD_SUCCESSED_AND_GENERATE_IT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}
		return Status.FILE_UPLOAD_SUCCESSED;

	}

	// @RequestMapping("/renewPdf/{reportId}")
	public void renewPdf(@PathVariable String reportId, HttpServletRequest request) throws Exception {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		webService.generatePdfByReportId(reportId, basePath, servletRailPath);
	}

	/**
	 * 获取报告pdf
	 * @param reportId 报告id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getPdf/{reportId}")
	public void showPdf(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		String header = request.getHeader("language");
		Locale locale = null;
		if (header != null) {
			locale = new Locale(header);
		} else {
			locale = Locale.CHINA;
		}
		if (webService.isReportAllReady(reportId)) {
			 
			if (webService.isReportPdfFileExist(reportId,locale)) {
				webService.flushPdf(request, response, reportId,locale);
			} else {
				webService.generatePdfByReportId(reportId, basePath, servletRailPath,locale);
				webService.flushPdf(request, response, reportId,locale);
			}
		}
	}
	
	/**
	 * 获取报告pdf
	 * @param reportId 报告id
	 * @param language 语言
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getPdfI18N/{reportId}/{language}")
	public void showPdf(@PathVariable String reportId,@PathVariable String language ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		if (language == null) {
			language = "zh";
		}
		if (webService.isReportAllReady(reportId)) {
			Locale locale = new Locale(language);
			 
			if (webService.isReportPdfFileExist(reportId,locale)) {
				webService.flushPdf(request, response, reportId,locale);
			} else {
				webService.generatePdfByReportId(reportId, basePath, servletRailPath,locale);
				webService.flushPdf(request, response, reportId,locale);
			}
		}
	}

	/**
	 * 获取报告数据
	 * @param reportId 报告id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/report/get/{reportId}")
	@ResponseBody
	public Status getReport(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseReport baseReport = webService.selectById(reportId);
		if (baseReport != null) {
			return new Status(Status.SUCCESSED_CODE, JSON.toJSONString(baseReport));
		}
		return Status.REPORT_NOT_EXSIT;

	}

}
