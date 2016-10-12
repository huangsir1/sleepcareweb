/**
 * 
 */
package taiyi.web.controller.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import taiyi.web.AndroidToWebAdapter.DataOfAndroidToWebAdapter;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.Status;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.thread.GenerateReportThread;
import taiyi.web.utils.GZipUtils;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.api
 *
 *         2016年7月8日
 */
@Controller
@RequestMapping("/api")
public class ThirdPartyController extends APIExceptionHandlerController {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	private WebService webService;
	@Autowired
	private DataOfAndroidToWebAdapter dataOfAndroidToWebAdapter;
	@Autowired
	private UserService userService;

	/**
	 * 上传数据文件并生成报告
	 * @param file 文件
	 * @param macAddress mac地址
	 * @param userId 用户id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/report/fileOnly/upload")
	@ResponseBody
	public Status uploadReportOnlyFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(required = false) String macAddress, String userId, HttpServletRequest request) {
//		if (StringUtils.isEmpty(macAddress)) {
//			return Status.getFailed();
//		}
		return uploadReportFile(file, macAddress, userId, request);
	}
	/**
	 * 上传数据文件并生成报告
	 * @param file 文件
	 * @param macAddress mac地址
	 * @param userId 用户id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/third/report/file/upload")
	@ResponseBody
	public Status uploadReportFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(required = false) String macAddress, String userId, HttpServletRequest request) {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		String reportId = UUID.randomUUID().toString();
		String language = request.getHeader("language");
		if (language == null) {
			language = "zh";
		}
		try {
			if (userService.selectByPrimaryKey(userId) == null) {
				return Status.USER_UNREGISTER;
			}
			String fileName = WebProperties.getReportFileName(userId, reportId);
			File targetFile = new File(fileName);
			if (!targetFile.exists()) {
				boolean isMkdir = targetFile.getParentFile().mkdirs();
				if (!isMkdir) {
					return Status.FAILD_TO_MKDIR;
				}
			}
			// 保存
			file.transferTo(targetFile);
			BaseReport baseReport;

			try (FileInputStream fileInputStream = new FileInputStream(targetFile);
					FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".taiir")) {
				GZipUtils.decompress(fileInputStream, fileOutputStream);
				fileOutputStream.flush();
				File file2 = new File(fileName + ".taiir");
				baseReport = dataOfAndroidToWebAdapter.originalFileToBaseReport(file2);
				if (file2.exists()) {
					file2.delete();
				}
			} catch (Exception e) {
				baseReport = dataOfAndroidToWebAdapter.originalFileToBaseReport(targetFile);
				e.printStackTrace();
			}

			baseReport.setId(reportId);
			baseReport.setUserId(userId);
			baseReport.setUploadDate(new Date());
			baseReport.setMacAddress(macAddress);
			webService.insertReport(baseReport);
			userService.updateLatestDateToNow(userId);
			logger.info("报告"+reportId+"上传成功");
			if (webService.isReportAllReady(reportId)) {
				new GenerateReportThread(webService, reportId, basePath, servletRailPath,new Locale(language)).start();
				// return Status.FILE_UPLOAD_SUCCESSED_AND_GENERATE_IT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Status.getFailed();
		}
		return new Status(Status.SUCCESSED_CODE, reportId);

	}

	/**
	 * 杜恩特殊文件上传
	 */
	@RequestMapping(value = "/third/reportForDawn/file/upload")
	@ResponseBody
	public Status uploadReportForDawnFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(required = false) String macAddress, HttpServletRequest request) {
		return uploadReportFile(file, macAddress, "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF", request);
	}
}
