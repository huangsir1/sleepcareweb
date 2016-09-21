/**
 * 
 */
package taiyi.web.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.itextpdf.text.DocumentException;

import taiyi.web.model.BreatheReport;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.EssUser;
import taiyi.web.model.Hostipal;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.User;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.DiseaseHistoryDto;
import taiyi.web.model.dto.ReportPreviewDto;
import taiyi.web.model.dto.UserEssAndDHDto;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.HostipalService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.SystemUserService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.utils.BarChartImageUtil;
import taiyi.web.utils.BeanUtilsForAndroid;
import taiyi.web.utils.FileOperateUtils;
import taiyi.web.utils.LineChartImageUtil;
import taiyi.web.utils.PDFUtils;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service.Impl
 *
 *         2016年3月30日
 */
@Service
@Transactional
public class WebServiceImpl implements WebService {
	Logger logger = Logger.getLogger(WebServiceImpl.class);

	@Autowired
	private UserService userService;
	@Autowired
	private BreatheReportService breatheReportService;;
	@Autowired
	private SleepReportService sleepReportService;
	@Autowired
	private SubReportService subReportService;
	@Autowired
	private HostipalService hostipalService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private EssUserService essUserService;
	@Autowired
	private DiseaseHistoryUserService diseaseHistoryUserService;
	

	public Map<String, Integer[]> getReportNumber(String reportId) {
		Map<String, Integer[]> maps = new HashMap<String, Integer[]>();
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(reportId);
		Integer[] seconds = new Integer[6];
		Integer[] times = new Integer[6];
		seconds[0] = breatheReport.getOxygenSaturationLessthanFiftyPercentHyponea() / 60;
		seconds[1] = breatheReport.getOxygenSaturationFiftyToFiftyNinePercentHyponea() / 60;
		seconds[2] = breatheReport.getOxygenSaturationSixtyToSixtyNinePercentHyponea() / 60;
		seconds[3] = breatheReport.getOxygenSaturationSeventyToSeventyNinePercentHyponea() / 60;
		seconds[4] = breatheReport.getOxygenSaturationEightyToEightyNinePercentHyponea() / 60;
		seconds[5] = breatheReport.getOxygenSaturationNinetyToHundredPercentHyponea() / 60;

		times[0] = breatheReport.getOxygenSaturationLessthanFiftyPercentTimes();
		times[1] = breatheReport.getOxygenSaturationFiftyToFiftyNinePercentTimes();
		times[2] = breatheReport.getOxygenSaturationSixtyToSixtyNinePercentTimes();
		times[3] = breatheReport.getOxygenSaturationSeventyToSeventyNinePercentTimes();
		times[4] = breatheReport.getOxygenSaturationEightyToEightyNinePercentTimes();
		times[5] = breatheReport.getOxygenSaturationNinetyToHundredPercentTimes();

		maps.put("times", times);
		maps.put("seconds", seconds);
		return maps;
	}

	@Deprecated
	public String generatePdfByReportId_oldVersion(String reportId, String basePath, String servletRailPath)
			throws Exception {

		Map<String, Integer[]> maps = getReportNumber(reportId);
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(reportId);
		User user = userService.selectWithDH(sleepReport.getUserId());
		String fileName = WebProperties.getFilePath() + File.separator + user.getId() + File.separator
				+ sleepReport.getId() + File.separator;
		SubReport sub = subReportService.selectByPrimaryKey(reportId);

		File file = new File(fileName);
		System.out.println(fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
		Map<String, String[]> readAsMap = new HashMap<String, String[]>();
		try {
			readAsMap = FileOperateUtils.readAsMap(WebProperties.getReportFileName(sleepReport.getUserId(), reportId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			filtration(readAsMap);
			readAsMap = doXueyang(readAsMap);
		} catch (Exception e1) {
			e1.printStackTrace();
			readAsMap.put("riqi", new String[0]);
			readAsMap.put("mailv", new String[0]);
			readAsMap.put("xueyang", new String[0]);
		}
		String font = WebProperties.getFilePath() + "/NotoSansCJKsc-Regular.otf";
		String logo = WebProperties.getFilePath() + "/logo.png";
		BarChartImageUtil.generateSecondsOfReducedOxygenImage(maps.get("seconds"), "分", fileName + "ROSeconds.png");
		BarChartImageUtil.generateTimesOfReducedOxygenImage(maps.get("times"), fileName + "ROTimes.png");
		try {
			LineChartImageUtil.generateMailvImage(readAsMap.get("riqi"), readAsMap.get("mailv"), fileName + "ROml.png");
			LineChartImageUtil.generateXueyangImage(readAsMap.get("riqi"), readAsMap.get("xueyang"),
					fileName + "ROxy.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		new PDFUtils(font, logo).createPdf(fileName + "report.pdf", fileName + "ROSeconds.png",
				fileName + "ROTimes.png", fileName + "ROml.png", fileName + "ROxy.png", user, sleepReport,
				breatheReport, sub);
		logger.info("报告生成成功 " + fileName + "report.pdf");
		deleteFile(fileName,null);
		return basePath + "files" + File.separator + user.getId() + File.separator + sleepReport.getId()
				+ File.separator + "report.pdf";
	}

	public String generatePdfByReportId(String reportId, String basePath, String servletRailPath, Locale locale)
			throws Exception {
		Map<String, Integer[]> maps = getReportNumber(reportId);
		BaseReport baseReport = this.selectById(reportId);

		User user = userService.selectWithDH(baseReport.getUserId());
		String fileName = WebProperties.getFilePath() + File.separator + user.getId() + File.separator
				+ baseReport.getId() + File.separator;

		File file = new File(fileName);
		System.out.println(fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
		Map<String, String[]> readAsMap = new HashMap<String, String[]>();
		try {
			readAsMap = FileOperateUtils.readAsMap(WebProperties.getReportFileName(baseReport.getUserId(), reportId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			filtration(readAsMap);
			readAsMap = doXueyang(readAsMap);
		} catch (Exception e1) {
			e1.printStackTrace();
			readAsMap.put("riqi", new String[0]);
			readAsMap.put("mailv", new String[0]);
			readAsMap.put("xueyang", new String[0]);
		}

		PDFUtils pdfUtils = createPdfUtils();
		pdfUtils.setLocal(locale);

		BarChartImageUtil.generateSecondsOfReducedOxygenImage(maps.get("seconds"), "分",
				fileName + "ROSeconds_" + locale.getLanguage() + ".png", pdfUtils.getLocal());
		BarChartImageUtil.generateTimesOfReducedOxygenImage(maps.get("times"),
				fileName + "ROTimes_" + locale.getLanguage() + ".png", pdfUtils.getLocal());
		try {
			LineChartImageUtil.generateMailvImage(readAsMap.get("riqi"), readAsMap.get("mailv"),
					fileName + "ROml_" + locale.getLanguage() + ".png", pdfUtils.getLocal());
			LineChartImageUtil.generateXueyangImage(readAsMap.get("riqi"), readAsMap.get("xueyang"),
					fileName + "ROxy_" + locale.getLanguage() + ".png", pdfUtils.getLocal());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("图片生成失败");
		}
		// create pdf
		createPdf(pdfUtils, user, baseReport);

		logger.info("报告生成成功 " + fileName + "report.pdf");
		deleteFile(fileName,locale);
		return basePath + "files" + File.separator + user.getId() + File.separator + baseReport.getId() + File.separator
				+ "report.pdf";

	}

	/**
	 * @param fileName
	 */
	public void deleteFile(String basePath,Locale locale) {
		File image1 = new File(basePath + "ROSeconds_"+locale.getLanguage()+".png");
		if (image1.exists()) {
			image1.delete();
		}
		File image2 = new File(basePath + "ROTimes_"+locale.getLanguage()+".png");
		if (image2.exists()) {
			image2.delete();
		}
		File image3 = new File(basePath + "ROml_"+locale.getLanguage()+".png");
		if (image3.exists()) {
			image3.delete();
		}
		File image4 = new File(basePath + "ROxy_"+locale.getLanguage()+".png");
		if (image4.exists()) {
			image4.delete();
		}
		logger.info("图片清理成功");
	}
	
	public void deleteFile(String basePath) {
		File image1 = new File(basePath + "ROSeconds.png");
		if (image1.exists()) {
			image1.delete();
		}
		File image2 = new File(basePath + "ROTimes.png");
		if (image2.exists()) {
			image2.delete();
		}
		File image3 = new File(basePath + "ROml.png");
		if (image3.exists()) {
			image3.delete();
		}
		File image4 = new File(basePath + "ROxy.png");
		if (image4.exists()) {
			image4.delete();
		}
		logger.info("图片清理成功");
	}

	/**
	 * @param readAsMap
	 * @return
	 */
	private Map<String, String[]> doXueyang(Map<String, String[]> readAsMap) {
		String[] xueYang = readAsMap.get("xueyang");
		for (int i = 0; i < xueYang.length; i++) {
			try {
				if (Integer.parseInt(xueYang[i]) > 100) {
					xueYang[i] = xueYang[i - 1];
				}
			} catch (Exception e) {
				xueYang[i] = "100";
			}

		}

		return readAsMap;
	}

	private void filtration(Map<String, String[]> readAsMap) {
		String[] mailv = readAsMap.get("mailv");
		for (int i = 0; i < mailv.length; i++) {
			int mailvNumber = Integer.parseInt(mailv[i]);
			try {
				if (mailvNumber <= 0 || mailvNumber >= 255) {
					mailv[i] = mailv[i - 1];
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		String[] xuyang = readAsMap.get("xueyang");
		for (int i = 0; i < xuyang.length; i++) {
			int xuyangNumber = Integer.parseInt(xuyang[i]);
			try {
				if (xuyangNumber <= 25 || xuyangNumber >= 255) {
					mailv[i] = mailv[i - 1];
				}
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public PDFUtils createPdfUtils() throws DocumentException, IOException {
		String filePath = WebProperties.getFilePath();
		String font = filePath + "/NotoSansCJKsc-Regular.otf";
		String logo = filePath + "/logo.png";
		return new PDFUtils(font, logo);
	}

	public void createPdf(PDFUtils pdfUtils, User user, BaseReport baseReport)
			throws DocumentException, IOException, Exception {
		String filePath = WebProperties.getFilePath();
		String fileName = filePath + File.separator + user.getId() + File.separator + baseReport.getId()
				+ File.separator;

		Hostipal hostipal = hostipalService.selectByMacAddress(baseReport.getSubReport().getMacAddress());
		if (hostipal == null) {
			hostipal = new Hostipal();
		}
		pdfUtils.createPdf(fileName + "report_" + pdfUtils.getLocal().getLanguage() + ".pdf",
				fileName + "ROSeconds_" + pdfUtils.getLocal().getLanguage() + ".png",
				fileName + "ROTimes_" + pdfUtils.getLocal().getLanguage() + ".png",
				fileName + "ROml_" + pdfUtils.getLocal().getLanguage() + ".png",
				fileName + "ROxy_" + pdfUtils.getLocal().getLanguage() + ".png", user, baseReport.getSleepReport(),
				baseReport.getBreatheReport(), baseReport.getSubReport(), hostipal.getAlias());
	}

	// private Pulse generatePulse(Map<String, String[]> readAsMap) throws
	// ParseException {
	// Pulse pulse = new Pulse();
	// String[] mailv = readAsMap.get("mailv");
	// String[] riqi = readAsMap.get("riqi");
	// DateFormat in = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
	// Locale.ENGLISH);
	// int sum = 0;
	// int max = Integer.parseInt(mailv[0]);
	// int min = Integer.parseInt(mailv[0]);
	// String maxDate = (riqi[0]);
	// String minDate = (riqi[0]);
	// for (int i = 0; i < riqi.length; i++) {
	// int mailvNumber = Integer.parseInt(mailv[i]);
	// try {
	// if (mailvNumber == 0 || mailvNumber == 255) {
	// mailv[i] = mailv[i - 1];
	// mailvNumber = Integer.parseInt(mailv[i - 1]);
	// }
	// } catch(Exception e) {
	// //do nothing
	// }
	//
	// sum += mailvNumber;
	// if (mailvNumber > max) {
	// max = mailvNumber;
	// maxDate = riqi[i];
	// } else if (mailvNumber < min) {
	// min = mailvNumber;
	// minDate = riqi[i];
	// }
	// }
	// pulse.setAverage(sum / mailv.length);
	// pulse.setMax(max);
	// pulse.setMaxTime(in.parse(maxDate));
	// pulse.setMin(min);
	// pulse.setMinTime(in.parse(minDate));
	// return pulse;
	//
	// }

	/*
	 * @see taiyi.web.service.WebService#generateValidateCode()
	 */
	@Override
	public String generateValidateCode() {
		String code = "" + (int) (Math.random() * 9 + 1) + (int) (Math.random() * 9 + 1) + (int) (Math.random() * 9 + 1)
				+ (int) (Math.random() * 9 + 1);
		return code;
	}

	/*
	 * @see
	 * taiyi.web.service.WebService#insertReport(taiyi.web.model.dto.BaseReport)
	 */
	@Override
	public synchronized void insertReport(BaseReport report) {
		SleepReport sleepReport = report.getSleepReport();
		BreatheReport breatheReport = report.getBreatheReport();
		SubReport subReport = report.getSubReport();
		sleepReportService.insert(sleepReport);
		subReportService.insert(subReport);
		breatheReportService.insert(breatheReport);
		User user = userService.selectByPrimaryKey(report.getUserId());
		user.setLastestDate(new Date());
		userService.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteReport(String id) {
		subReportService.deleteByPrimaryKey(id);
		breatheReportService.deleteByPrimaryKey(id);
		sleepReportService.deleteByPrimaryKey(id);
	}

	@Override
	public boolean isReportAllReady(String reportId) {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		SubReport subReport = subReportService.selectByPrimaryKey(reportId);
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(reportId);
		if (sleepReport != null && subReport != null && breatheReport != null) {
			String userId = sleepReport.getUserId();
			if (new File(WebProperties.getReportFileName(userId, reportId)).exists()) {
				return true;
			}
		}
		return false;

	}

	@Override
	public BaseReport selectById(String reportId) throws IllegalAccessException, InvocationTargetException {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		SubReport subReport = subReportService.selectByPrimaryKey(reportId);
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(reportId);
		if (sleepReport == null || breatheReport == null || subReport == null) {
			return null;
		}
		BaseReport baseReport = new BaseReport(sleepReport, breatheReport, subReport);
		return baseReport;
	}

	@Override
	public Locale isReportPdfExist(String reportId) {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		if (sleepReport != null) {
			String userId = sleepReport.getUserId();
			File[] listFiles = new File(WebProperties.getReportPdfName(userId, reportId)).getParentFile().listFiles();
			Locale[] I23 = new Locale[3];
			for(File file : listFiles) {
				if (file.getName().contains(".pdf")) {
					if (file.getName().contains("zh")) {
						I23[0] = new Locale( file.getName().replaceAll("report_", "").replaceAll(".pdf", ""));
					} else if (file.getName().contains("en")) {
						I23[1] = new Locale( file.getName().replaceAll("report_", "").replaceAll(".pdf", ""));
					} else {
						I23[2] = new Locale( file.getName().replaceAll("report_", "").replaceAll(".pdf", ""));
					}
				}
			}
			for(Locale names : I23) {
				if (names != null) {
					return names;
				}
			}
		}
		return null;

	}
	/* 
	 * @see taiyi.web.service.WebService#isReportPdfFileExist(java.util.Locale)
	 */
	@Override
	public boolean isReportPdfFileExist(String reportId,Locale locale) {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		if (sleepReport != null) {
			String userId = sleepReport.getUserId();
			String reportPdfName = WebProperties.getReportPdfName(userId, reportId,locale);
			if (new File(reportPdfName).exists()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void flushPdf(HttpServletRequest request, HttpServletResponse response, String reportId,Locale locale) throws IOException {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";

		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		String fileName = WebProperties.getFilePath() + File.separator + sleepReport.getUserId() + File.separator
				+ reportId + File.separator + "report_"+locale.getLanguage() +".pdf";
		logger.warn(fileName);
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				String result = this.generatePdfByReportId(reportId, basePath, servletRailPath,locale);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.setContentType("application/pdf");
		FileInputStream fis = null;
		OutputStream out = response.getOutputStream();
		fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		int read = fis.read(b);
		logger.debug("read of pdf" + read);
		out.write(b);
		out.flush();
		fis.close();
	}

	@Override
	public void flushFile(HttpServletRequest request, HttpServletResponse response, String reportId)
			throws IOException {

		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		String fileName = WebProperties.getFilePath() + File.separator + sleepReport.getUserId() + File.separator
				+ reportId + File.separator + "source.txt";
		logger.warn(fileName);
		File file = new File(fileName);
		FileInputStream fis = null;
		OutputStream out = response.getOutputStream();
		fis = new FileInputStream(file);
		byte[] b = new byte[fis.available()];
		int read = fis.read(b);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"data.gz\"");
		logger.debug("flush of file" + read);
		out.write(b);
		out.flush();
		fis.close();
	}

	/*
	 * @see
	 * taiyi.web.service.WebService#updateReport(taiyi.web.model.dto.BaseReport)
	 */
	@Override
	public void updateReport(BaseReport report) {
		SleepReport sleepReport = report.getSleepReport();
		BreatheReport breatheReport = report.getBreatheReport();
		SubReport subReport = report.getSubReport();
		sleepReportService.updateByPrimaryKey(sleepReport);
		subReportService.updateByPrimaryKey(subReport);
		breatheReportService.updateByPrimaryKey(breatheReport);

		User user = userService.selectByPrimaryKey(report.getUserId());
		user.setLastestDate(new Date());
		userService.updateByPrimaryKeySelective(user);

	}

	@Override
	public List<ReportPreviewDto> packagePerviewReportDtoByUserId(String userId) {
		List<SleepReport> sleepReports = sleepReportService.selectByUserId(userId);
		return packagePerviewReportDto(sleepReports);
	}

	public List<ReportPreviewDto> packagePerviewReportDto(List<SleepReport> sleepReports) {
		List<ReportPreviewDto> reportPreviewDtos = Lists.newArrayListWithCapacity(sleepReports.size());
		for (SleepReport s : sleepReports) {
			ReportPreviewDto reportPreviewDto = new ReportPreviewDto();
			BeanUtilsForAndroid.copy(s, reportPreviewDto);
			SubReport subReport = subReportService.selectByPrimaryKey(s.getId());
			reportPreviewDto.setMacAddress(subReport.getMacAddress());
			reportPreviewDto.setVersion(subReport.getAppVersion());
			reportPreviewDtos.add(reportPreviewDto);
		}

		return reportPreviewDtos;
	}

	/* 
	 * @see taiyi.web.service.WebService#generatePdfByReportId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generatePdfByReportId(String reportId, String basePath, String servletRailPath) throws Exception {
		return generatePdfByReportId(reportId, basePath, servletRailPath,Locale.CHINA);
	}

	/* 
	 * @see taiyi.web.service.WebService#deletePdf(java.lang.String)
	 */
	@Override
	public void deletePdf(String reportId) {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(reportId);
		String fileName = WebProperties.getFilePath() + File.separator + sleepReport.getUserId() + File.separator
				+ reportId;
		for(File file : new File(fileName).listFiles()) {
			if (file.getName().contains(".pdf")) {
				file.delete();
			}
		}
	}

	/* 
	 * @see taiyi.web.service.WebService#selectUserEssAndDHByToken(java.lang.String)
	 */
	@Override
	public List<UserEssAndDHDto> selectUserEssAndDHByToken(String token) {
		List<User> userByToken = userService.selectActiveUserByToken(token);
		List<UserEssAndDHDto> userEssAndDHDtos = Lists.newArrayListWithCapacity(3);
		for (User user : userByToken) {
			UserEssAndDHDto userEssAndDHDto = new UserEssAndDHDto();
			BeanUtilsForAndroid.copy(user, userEssAndDHDto);
			List<EssUser> essUsers = essUserService.selectByUserId(user.getId());
			for(EssUser essUser : essUsers) {
				essUser.setId(null);
				essUser.setUserId(null);
			}
			userEssAndDHDto.setEss(essUsers);
			DiseaseHistoryDto diseaseHistories =  diseaseHistoryUserService.selectByUserId(user.getId());
			diseaseHistories.setUserId(null);
			userEssAndDHDto.setDiseaseHistories(diseaseHistories);
			userEssAndDHDtos.add(userEssAndDHDto);
		}
		return userEssAndDHDtos;
	}

	


}
