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
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

import com.alibaba.fastjson.JSON;
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
import taiyi.web.model.dto.SystemUserRoleDto;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.PermissionService;
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
 *         网页端管理员所使用的接口
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

	@Autowired
	private PermissionService permissionService;

	/**
	 * 上传用户的接口
	 * 
	 * @param birth
	 *            生日
	 * @param user
	 *            用户
	 * @param qx
	 *            病史id
	 * @return 结果对象
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:insert", "doctor:insert" })
	@RequestMapping("/saveUser")
	@ResponseBody
	public Status saveUser(@DateTimeFormat(pattern = "yyyy-MM-dd") Date birth, User user,
			@RequestParam(required = false) Integer[] qx) {
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

	/**
	 * 获取所有用户，可分页
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每页行数
	 * @return 用户信息的json
	 */
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

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户id
	 * @return 结果
	 */
	@RequiresPermissions(value = { "user:delete", "doctor:delete" }, logical = Logical.OR)
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Status deleteUser(String userId) {
		try {
			diseaseHistoryUserService.deleteByUserId(userId);
			essUesrService.deleteByUserId(userId);
			userService.deleteByPrimaryKey(userId);
			return Status.getSuccess();
		} catch (Exception e) {
			return Status.CANNOT_DELETE_USER;
		}

	}

	/**
	 * 
	 * @param userId
	 *            用户id
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 报告详细内容
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("/getReportByUserId/{userId}")
	@ResponseBody
	public HashMap<String, Object> getReportByUserId(@PathVariable String userId,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<SleepReport> sleepReports = sleepReportService.selectByUserId(userId);
		List<ReportPreviewDto> reportPreviewDtos = webService.packagePerviewReportDto(sleepReports);
		PageInfo<SleepReport> pageInfo = new PageInfo<SleepReport>(sleepReports);
		PageModel pageModel = new PageModel(pageInfo.getTotal(), reportPreviewDtos);
		return pageModel.get();
	}

	/**
	 * 根据id获取报告
	 * 
	 * @param id
	 *            报告id
	 * @param request
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view", "hostipal:view" })
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

	/**
	 * 增加报告
	 * 
	 * @param id
	 *            报告id
	 * @param request
	 * @return
	 */
	@RequiresPermissions("user:insert")
	@RequestMapping("/addReport/{id}")
	public String addReport(@PathVariable String id, HttpServletRequest request) {
		User user = userService.selectWithDH(id);
		request.setAttribute("user", user);
		return "admin/addReport";
	}

	/**
	 * 删除报告
	 * 
	 * @param 报告id
	 * @return
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:delete" }) // ,
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

	/**
	 * 手动插入报告
	 */
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

	/**
	 * 之前供前段获取数据的接口，现在已废弃
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view" })
	@RequestMapping("/getHyponeaData/{id}")
	@ResponseBody
	public Map<String, Integer[]> getData(@PathVariable String id) {
		return webService.getReportNumber(id);
	}

	/**
	 * 报告生成pdf
	 * 
	 * @param reportId
	 *            报告id
	 * @param request
	 * @return 状态
	 * @throws IOException
	 * @throws DocumentException
	 */
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

	/**
	 * 显示pdf
	 * 
	 * @param reportId
	 *            报告id
	 * @param language
	 *            语言偏好
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view", "hospital:view" })
	@RequestMapping("/showPdf/{reportId}/{language}")
	public void showPdf(@PathVariable String reportId, @PathVariable String language, HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {

		if (language == null) {
			language = "zh";
		}
		Locale locale = new Locale(language);
		webService.flushPdf(request, response, reportId, locale);
	}

	/**
	 * 显示pdf
	 * 
	 * @param reportId
	 *            报告id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view", "hospital:view" })
	@RequestMapping("/showPdf/{reportId}")
	public void showPdf(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DocumentException {
		showPdf(reportId, null, request, response);
	}

	/**
	 * 下载数据文件
	 * 
	 * @param reportId
	 *            报告id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "system:view" })
	@RequestMapping("/showFile/{reportId}")
	public void showFile(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DocumentException {
		webService.flushFile(request, response, reportId);
	}

	/**
	 * 删除文件
	 * 
	 * @param reportId
	 *            报告id
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequiresPermissions(logical = Logical.OR, value = { "system:delete" })
	@RequestMapping("/deleteFile/{reportId}")
	@ResponseBody
	public Status showFile(@PathVariable String reportId, HttpServletResponse response)
			throws IOException, DocumentException {
		webService.deletePdf(reportId);
		return Status.getSuccess();
	}

	/**
	 * 搜索用户
	 * 
	 * @param user
	 *            用户
	 * @param birthdays
	 *            生日
	 * @param page
	 *            页数
	 * @param rows
	 *            每页条数
	 * @return
	 */
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
			user.setName(user.getName().trim() + "%");
		}
		if (!StringUtils.isEmpty(user.getGender())) {
			user.setGender(user.getGender().trim() + "%");
		}
		if (!StringUtils.isEmpty(user.getPhone())) {
			user.setPhone(user.getPhone().trim() + "%");
		}
		if (!StringUtils.isEmpty(user.getAddress())) {
			user.setAddress(user.getAddress().trim() + "%");
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

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件
	 * @param id
	 *            报告id
	 * @param request
	 * @return
	 */
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

	// @RequestMapping("login")
	// public String login() {
	// return "adminLogin";
	// }

	/**
	 * 修改密码
	 * 
	 * @param original
	 *            原密码
	 * @param newp
	 *            新密码
	 * @param confirm
	 *            确认新密码
	 * @return
	 */
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

	/**
	 * 获取医院管理员帐户
	 * 
	 * @param hospitalId
	 *            医院id
	 * @return
	 */
	@RequestMapping("/getHospitalAdmin/{hospitalId}")
	@RequiresPermissions("system:view")
	@ResponseBody
	public List<SystemUserRoleDto> getHospitalAdmin(@PathVariable int hospitalId) {
		return systemUserService.selectWithRoleByHostipal(hospitalId);
	}

	/**
	 * 注册医院管理员
	 * 
	 * @param systemUser
	 *            管理员信息
	 * @param hospitalId
	 *            医院id
	 * @param systemRoles
	 *            权限
	 * @param password
	 *            密码
	 * @param confirm
	 *            确认密码
	 * @return
	 */
	@RequestMapping("/saveHospitalAdmin")
	@RequiresPermissions("system:insert")
	@ResponseBody
	public Status saveHospitalAdmin(SystemUser systemUser, int hospitalId, String[] systemRoles, String password,
			String confirm) {
		if (!password.equals(confirm)) {
			return Status.PASSWORD_NOT_MATCH;
		}
		if (systemUserService.selectByUsername(systemUser.getUsername()) != null) {
			return Status.USER_EXIST;
		}
		systemUser.setHostipalId(hospitalId);
		systemUser.setId(UUID.randomUUID().toString());
		systemUserService.saveHospitalAdmin(systemUser, systemRoles, password);
		return Status.getSuccess();
	}

	/**
	 * 修改医院
	 * 
	 * @param systemUser
	 *            管理员信息
	 * @param systemRoles
	 *            权限
	 * @return
	 */
	@RequestMapping("/editHospitalAdmin")
	@RequiresPermissions("system:update")
	@ResponseBody
	public Status editHospitalAdmin(SystemUser systemUser, String[] systemRoles) {
		Set<String> roles = permissionService.selectRoleStringsByUserId(systemUser.getId());
		if (roles.contains("admin")) {
			return Status.getFailed("无法修改管理员信息！！");
		}
		if (systemUserService.selectByUsername(systemUser.getUsername()) != null) {
			if (!systemUserService.selectByPrimaryKey(systemUser.getId()).getName().equals(systemUser.getName())) {
				return Status.getFailed("用户名已存在");
			}
		}
		systemUserService.editHospitalAdmin(systemUser, systemRoles);
		return Status.getSuccess();
	}

	/**
	 * 修改密码
	 * 
	 * @param id
	 *            管理员id
	 * @param password
	 *            密码
	 * @param confirm
	 *            确认密码
	 * @return
	 */
	@RequestMapping("/updateHospitalAdminPassword")
	@RequiresPermissions("system:update")
	@ResponseBody
	public Status updateHospitalAdminPassword(String id, String password, String confirm) {
		Set<String> roles = permissionService.selectRoleStringsByUserId(id);
		if (roles.contains("admin")) {
			return Status.getFailed("无法修改管理员信息！！");
		}
		if (!password.equals(confirm)) {
			return Status.PASSWORD_NOT_MATCH;
		}
		SystemUser systemUser = new SystemUser();
		systemUser.setId(id);
		systemUser.setPassword(EncryptUtils.encryptOriginalTaiirPassword(password));
		systemUserService.updateByPrimaryKeySelective(systemUser);
		return Status.getSuccess();
	}

	/**
	 * 删除医院管理员
	 * 
	 * @param id
	 *            管理员id
	 * @return
	 */
	@RequestMapping("/deleteHospitalAdmin")
	@RequiresPermissions("system:delete")
	@ResponseBody
	public Status deleteHospitalAdmin(String id) {
		Set<String> roles = permissionService.selectRoleStringsByUserId(id);
		if (roles.contains("admin")) {
			return Status.getFailed("无法删除管理员！！");
		}
		return systemUserService.deleteSystemUser(id);
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

	/**
	 * 输入时间换算成秒
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return 总计秒数
	 */
	public int calculateSeconds(String hour, String minute, String second) {
		return Integer.parseInt(hour) * 3600 + Integer.parseInt(minute) * 60 + Integer.parseInt(second);
	}
}
