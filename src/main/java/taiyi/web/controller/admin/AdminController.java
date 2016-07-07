/**
 * 
 */
package taiyi.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.DocumentException;

import taiyi.web.controller.ExceptionHandlerController;
import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.DiseaseHistoryDto;
import taiyi.web.model.dto.PageModel;
import taiyi.web.model.dto.ReportPreviewDto;
import taiyi.web.model.dto.Status;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.SystemUserService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.utils.EncryptUtils;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.admin
 *
 *         2016年3月10日
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends ExceptionHandlerController {
	Logger logger = Logger.getLogger(getClass());
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
	@Autowired
	private DiseaseHistoryUserService diseaseHistoryUserService;
	@Autowired
	private EssUserService essUesrService;
	@Autowired
	private SystemUserService systemUserService;
	

	@RequiresPermissions(logical = Logical.OR, value = { "user:insert", "doctor:insert" })
	@RequestMapping("/saveUser")
	@ResponseBody
	public Status saveUser(@DateTimeFormat(pattern = "yyyy-MM-dd") Date birth, User user,@RequestParam(required=false) Integer[] qx) {
		user.setId(UUID.randomUUID().toString());
		user.setBirthday(birth);
		DiseaseHistoryDto diseaseHistoryDto = new DiseaseHistoryDto();
		diseaseHistoryDto.setUserId(user.getId());
		qx = qx == null ? new Integer[0] : qx;
		diseaseHistoryDto.setDiseaseHistoryIds(qx);
		Subject subject = SecurityUtils.getSubject();
		logger.debug("-------" + subject.getSession().getAttribute("user") + "-------");
		if (subject.getSession().getAttribute("user") != null) {
			SystemUser systemUser = (SystemUser) subject.getSession().getAttribute("user");
			user.setDoctorId(systemUser.getId());
		}
		try {
			userService.insert(user);
			diseaseHistoryUserService.insertDHUs(diseaseHistoryDto);
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}

		return Status.SUCCESSED;
	}

	@RequiresPermissions("user:view")
	@RequestMapping("/getAllUsers")
	@ResponseBody
	public Map<String, Object> getAllUsers(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<User> users = userService.getAllUsers();
		PageInfo<User> pageinfo = new PageInfo<User>(users);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), users);
		return pageModel.get();
	}
	

	

	@RequiresPermissions(value = { "user:delete", "doctor:delete" }, logical = Logical.OR)
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Status deleteUser(String userId) {
		try {
			diseaseHistoryUserService.deleteByUserId(userId);
			essUesrService.deleteByUserId(userId);
			userService.deleteByPrimaryKey(userId);
			return Status.SUCCESSED;
		} catch (Exception e) {
			return Status.CANNOT_DELETE_USER;
		}

	}

	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("/getReportByUserId/{userId}")
	@ResponseBody
	public List<ReportPreviewDto> getReportByUserId(@PathVariable String userId) {
		return webService.packagePerviewReportDtoByUserId(userId);
	}

	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view","hostipal:view" })
	@RequestMapping("/showReport/{id}")
	public String showReport(@PathVariable String id, HttpServletRequest request) {
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(id);
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(id);
		String fileName = WebProperties.getReportFileName(sleepReport.getUserId(), sleepReport.getId());

		User user = userService.selectWithDH(sleepReport.getUserId());
		request.setAttribute("breatheReport", breatheReport);
		request.setAttribute("sleepReport", sleepReport);
		request.setAttribute("user", user);
		request.setAttribute("file", new File(fileName).exists());
		return "admin/report";
	}

	@RequiresPermissions("user:insert")
	@RequestMapping("/addReport/{id}")
	public String addReport(@PathVariable String id, HttpServletRequest request) {
		User user = userService.selectWithDH(id);
		request.setAttribute("user", user);
		return "admin/addReport";
	}

	@RequiresPermissions(logical = Logical.OR, value = { "user:delete"})//, "doctor:delete" })
	@RequestMapping("/deleteReport")
	@ResponseBody
	public Status deleteReport(String id) {
		try {
			webService.deleteReport(id);
			return Status.SUCCESSED;
		} catch (Exception e) {
			return Status.CANNOT_DELETE_USER;
		}

	}

	@RequiresPermissions("user:insert")
	@RequestMapping("/postReport")
	public String postReport(@RequestParam(required = false) MultipartFile dataText, BreatheReport breatheReport,
			SleepReport sleepReport, String maxHyponeaSecondsH, String maxHyponeaSecondsM, String maxHyponeaSecondsS,
			String l050H, String l050M, String l050S, String l5060H, String l5060M, String l5060S, String l6070H,
			String l6070M, String l6070S, String l7080H, String l7080M, String l7080S, String l8090H, String l8090M,
			String l8090S, String l90100H, String l90100M, String l90100S, String totalSecondsH, String totalSecondsM,
			String totalSecondsD, String deepSleepSecondsH, String deepSleepSecondsM, String deepSleepSecondsS,
			String lightSleepSecondsH, String lightSleepSecondsM, String lightSleepSecondsS, String sleep_score,
			String breathe_score, String jianceriqi, String kaishishijian, String jieshushij,
			HttpServletRequest request, String hyponeaHappenDateString, String baseDate, Double xueyangweihaizhishu,
			String maxhuxizantingH, String maxhuxizantingM, String maxhuxizantingS, String maxYangjiangtimesH,
			String maxYangjiangtimesM, String maxYangjiangtimesS, String longestYangjiangtimesH,
			String longestYangjiangtimesM, String longestYangjiangtimesS, Double aveagePulse, Double minPulse,
			Double maxPulse, String minPulseDate, String maxPulseDate, String maxYangjiangDateString,
			String longestYangjiangDateString, String maxhuxizantingDateString, Double yangjianzhishu)
			throws ParseException {
		try {
			SubReport subReport = new SubReport();
			String id = UUID.randomUUID().toString();
			int l050 = calculateSeconds(l050H, l050M, l050S);
			int l5060 = calculateSeconds(l5060H, l5060M, l5060S);
			int l6070 = calculateSeconds(l6070H, l6070M, l6070S);
			int l7080 = calculateSeconds(l7080H, l7080M, l7080S);
			int l8090 = calculateSeconds(l8090H, l8090M, l8090S);
			int l90100 = calculateSeconds(l90100H, l90100M, l90100S);
			int totalSeconds = calculateSeconds(totalSecondsH, totalSecondsM, totalSecondsD);
			int deepSleepSeconds = calculateSeconds(deepSleepSecondsH, deepSleepSecondsM, deepSleepSecondsS);
			int lightSleepSeconds = calculateSeconds(lightSleepSecondsH, lightSleepSecondsM, lightSleepSecondsS);
			int maxHyponeaSeconds = calculateSeconds(maxHyponeaSecondsH, maxHyponeaSecondsM, maxHyponeaSecondsS);
			int maxhuxizanting = calculateSeconds(maxhuxizantingH, maxhuxizantingM, maxhuxizantingS);
			int maxYangjiangtimes = calculateSeconds(maxYangjiangtimesH, maxYangjiangtimesM, maxYangjiangtimesS);
			int longestYangjiangtimes = calculateSeconds(longestYangjiangtimesH, longestYangjiangtimesM,
					longestYangjiangtimesS);

			breatheReport.setId(id);
			sleepReport.setId(id);
			subReport.setId(id);

			breatheReport.setOxygenSaturationLessthanFiftyPercentHyponea(l050);
			breatheReport.setOxygenSaturationFiftyToFiftyNinePercentHyponea(l5060);
			breatheReport.setOxygenSaturationSixtyToSixtyNinePercentHyponea(l6070);
			breatheReport.setOxygenSaturationSeventyToSeventyNinePercentHyponea(l7080);
			breatheReport.setOxygenSaturationEightyToEightyNinePercentHyponea(l8090);
			breatheReport.setOxygenSaturationNinetyToHundredPercentHyponea(l90100);
			sleepReport.setTotalSeconds(totalSeconds);
			sleepReport.setDeepSleepSeconds(deepSleepSeconds);
			sleepReport.setLightSleepSeconds(lightSleepSeconds);
			breatheReport.setMaxHyponeaSeconds(maxHyponeaSeconds);
			breatheReport.setScore(Integer.parseInt(breathe_score));
			sleepReport.setScore(Integer.parseInt(sleep_score));
			final SimpleDateFormat yyyyMMDDHHmmssFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sleepReport.setStartTime(yyyyMMDDHHmmssFormat.parse(kaishishijian));
			sleepReport.setEndTime(yyyyMMDDHHmmssFormat.parse(jieshushij));
			sleepReport.setUploadDate(new Date());
			breatheReport.setUploadDate(new Date());
			breatheReport.setHyponeaHappenDate(yyyyMMDDHHmmssFormat.parse(hyponeaHappenDateString));
			subReport.setLongestApneaSeconds(maxhuxizanting);
			subReport.setLongestApneaTime(yyyyMMDDHHmmssFormat.parse(maxhuxizantingDateString));
			subReport.setMaxOxygenReduceSeconds(maxYangjiangtimes);
			subReport.setMaxOxygenReduceTime(yyyyMMDDHHmmssFormat.parse(maxYangjiangDateString));
			subReport.setLongestOxygenReduceSeconds(longestYangjiangtimes);
			subReport.setLongestOxygenReduceTime(yyyyMMDDHHmmssFormat.parse(longestYangjiangDateString));
			subReport.setBloodOxygenHazardIndex(xueyangweihaizhishu);
			subReport.setMaxPulse(maxPulse);
			subReport.setMaxPulseTime(yyyyMMDDHHmmssFormat.parse(maxPulseDate));
			subReport.setMinPulse(minPulse);
			subReport.setMinPulseTime(yyyyMMDDHHmmssFormat.parse(minPulseDate));
			subReport.setAveragePulse(aveagePulse);
			subReport.setOxygenReductionIndex(yangjianzhishu);

			subReportService.insert(subReport);
			sleepReportService.insert(sleepReport);
			breatheReportService.insert(breatheReport);

			File targetFile = new File(WebProperties.getReportFileName(sleepReport.getUserId(), sleepReport.getId()));
			if (!targetFile.exists()) {
				targetFile.getParentFile().mkdirs();
			}

			// 保存
			try {
				dataText.transferTo(targetFile);
				System.out.println("保存成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/admin/showReport/" + sleepReport.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin";
		}
	}

	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("/getHyponeaData/{id}")
	@ResponseBody
	public Map<String, Integer[]> getData(@PathVariable String id) {
		return webService.getReportNumber(id);
	}

	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("/generatePdf/{reportId}")
	@ResponseBody
	public Status generatePdf(@PathVariable String reportId, HttpServletRequest request)
			throws IOException, DocumentException {
		String servletRailPath = request.getServletContext().getRealPath("/");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		try {
			String result = webService.generatePdfByReportId(reportId, basePath, servletRailPath);
			return new Status(Status.SUCCESSED_CODE, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(Status.FAILED_CODE, "生成失败");
		}
	}
	
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view","hospital:view" })
	@RequestMapping("/showPdf/{reportId}")
	public void showPdf(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DocumentException {
		webService.flushPdf(request, response, reportId);

	}
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("searchUsers")
	@ResponseBody
	public HashMap<String, Object> searchUsers(User user, @RequestParam(required = false) String birthdays,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer rows) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			user.setBirthday(simpleDateFormat.parse(birthdays));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!StringUtils.isEmpty(user.getName())) {
			user.setName(user.getName() + "%");
		}
		if (!StringUtils.isEmpty(user.getGender())) {
			user.setGender(user.getGender() + "%");
		}
		if (!StringUtils.isEmpty(user.getPhone())) {
			user.setPhone(user.getPhone() + "%");
		}
		if (!StringUtils.isEmpty(user.getAddress())) {
			user.setAddress(user.getAddress() + "%");
		}

		Session session = SecurityUtils.getSubject().getSession();
		if (session.getAttribute("user") != null) {
			SystemUser systemUser = (SystemUser) session.getAttribute("user");
			if (!SecurityUtils.getSubject().hasRole("admin")) {
				user.setDoctorId(systemUser.getId());
			}
		}

		PageModel pageModel = userService.searchUsersByPage(user, page, rows);
		return pageModel.get();

	}

	@RequiresPermissions("user:insert")
	@RequestMapping("uploadFile")
	public String uploadFile(MultipartFile file, String id, HttpServletRequest request) {
		String header = request.getHeader("Referer");
		try {
			SleepReport sleepReport = sleepReportService.selectByPrimaryKey(id);
			String path = WebProperties.getReportFileName(sleepReport.getUserId(), sleepReport.getId());
			File targetFile = new File(path);
			if (!targetFile.exists()) {
				targetFile.getParentFile().mkdirs();
			}
			System.out.println(file);
			// 保存
			file.transferTo(targetFile);
			System.out.println("保存成功");
			System.out.println(path);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + header;
		}
		return "redirect:" + header;
	}

	@RequiresPermissions("user:view")
	@RequestMapping("showUser")
	public String showUser() {
		return "admin/showUser";
	}

	@RequiresPermissions("user:view")
	@RequestMapping("searchUser")
	public String searchUser() {
		return "admin/searchUser";
	}

	@RequiresPermissions("user:insert")
	@RequestMapping("addUser")
	public String addUser() {
		return "admin/addUser";
	}
	
	
//	@RequestMapping("login") 
//	public String login() {
//		return "adminLogin";
//	} 

	@RequestMapping("changePassword")
	@ResponseBody
	public Status changePassword(String original, String newp, String confirm) {
		if (!newp.equals(confirm)) {
			return Status.PASSWORD_NOT_MATCH;
		}
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		logger.info(username + "准备修改密码");
		SystemUser selectByUsername = systemUserService.selectByUsername(username);
		if (EncryptUtils.checkOriginalTaiirPassword(original, selectByUsername.getPassword())) {
			if (original.equals(newp)) {
				return Status.PASSWORD_SAME;
			} else {
				selectByUsername.setPassword(EncryptUtils.encryptOriginalTaiirPassword(newp));
				systemUserService.updateByPrimaryKeySelective(selectByUsername);
				logger.info(username + "成功修改了密码");
				return Status.SUCCESSED;
			}
		} else {
			return Status.PASSWORD_NOT_MATCH;
		}
	}

	// @RequestMapping("/generateFullPdf/{reportId}")
	// @ResponseBody
	// public Status generatePdf(@PathVariable String reportId,
	// HttpServletRequest request,String result, String advice,String
	// maxhuxizanting,String maxhuxizantingDate,String maxYangjiangtimes,String
	// maxYangjiangDate,String xueyangweihaizhishu)
	// throws IOException, DocumentException {
	// try {
	// String r = webService.generatePdfByReportId(reportId,
	// request,result,advice,maxhuxizanting,maxhuxizantingDate,maxYangjiangtimes,maxYangjiangDate,xueyangweihaizhishu);
	// return new Status(Status.SUCCESSED_CODE, r);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return new Status(Status.FAILED_CODE, "生成失败");
	// }
	// }

	public int calculateSeconds(String hour, String minute, String second) {
		return Integer.parseInt(hour) * 3600 + Integer.parseInt(minute) * 60 + Integer.parseInt(second);
	}
}
