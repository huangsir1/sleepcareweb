/**
 * 
 */
package taiyi.web.controller.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	private WebService webService;
	@Autowired
	private DataOfAndroidToWebAdapter dataOfAndroidToWebAdapter;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/report/fileOnly/upload")
	@ResponseBody
	public Status uploadReportOnlyFile(@RequestParam(value = "file") MultipartFile file,@RequestParam(required = false) String macAddress, String userId,
			HttpServletRequest request)  {
		return uploadReportFile(file, macAddress, userId, request);
	}
	
	@RequestMapping(value = "/third/report/file/upload")
	@ResponseBody
	public Status uploadReportFile(@RequestParam(value = "file") MultipartFile file,@RequestParam(required = false) String macAddress, String userId,
			HttpServletRequest request) {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		String reportId = UUID.randomUUID().toString();
		try {
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
			FileInputStream fileInputStream = new FileInputStream(targetFile);
			FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".taiir");
			try {
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
			webService.insertReport(baseReport);
			userService.updateLatestDateToNow(userId);
			System.out.println(fileName + " 保存成功");
			if (webService.isReportAllReady(reportId)) {
				new GenerateReportThread(webService, reportId, basePath, servletRailPath).start();
				// return Status.FILE_UPLOAD_SUCCESSED_AND_GENERATE_IT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}
		return Status.FILE_UPLOAD_SUCCESSED;

	}

	@RequestMapping(value = "/third/reportForDawn/file/upload")
	@ResponseBody
	public Status uploadReportForDawnFile(@RequestParam(value = "file") MultipartFile file,@RequestParam(required = false) String macAddress,
			HttpServletRequest request) {
		return uploadReportFile(file, macAddress, "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF", request);
	}
}
